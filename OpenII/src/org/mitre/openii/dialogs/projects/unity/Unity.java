package org.mitre.openii.dialogs.projects.unity;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.mitre.openii.dialogs.projects.unity.DisjointSetForest.ContainerMethod;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingCellInput;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;
import org.mitre.schemastore.porters.projectExporters.MatchMakerExporter;
import org.mitre.schemastore.porters.projectExporters.matchmaker.ClusterNode;
import org.mitre.schemastore.porters.projectExporters.matchmaker.Synset;
import org.mitre.schemastore.porters.projectExporters.matchmaker.SynsetTerm;
import org.mitre.schemastore.porters.vocabularyExporters.CompleteVocabExporter;

public class Unity {

	private Project project;
	private Vocabulary vocabulary;
	private int authoritySchemaId = 0;

	// private ArrayList<Synset> synsetList = null;

	public Unity(Project project) {
		this.project = project;
		this.vocabulary = OpenIIManager.getVocabulary(project.getId());
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	/**
	 * Uses hierarchical clustering algorithm as provided by MatchMakerExporter
	 * to cluster NxN mappings into synsets (or clusters). Give each synset a
	 * connonical term and creates a vocabulary
	 * 
	 * @param inputMappings
	 * @return vocabulary object
	 * @throws RemoteException
	 */
	public Vocabulary unify(ArrayList<Mapping> inputMappings) throws RemoteException {
		// Generate synsets
		ArrayList<Synset> synsetList = generateSynsets(project, inputMappings);
		// Generate vocabulary terms
		vocabulary = new Vocabulary(project.getId(), generateVocabTerms(synsetList));

		// save new vocab
		OpenIIManager.saveVocabulary(vocabulary);
		return vocabulary;
	}

	/**
	 * Use DisjointSetForest to
	 * 
	 * @param inputMappings
	 * @return
	 */
	public Vocabulary DSFUnify(ArrayList<Mapping> inputMappings) {
		long start = System.currentTimeMillis();

		// Find participating schemas the mappings
		final HashSet<Integer> schemaIDs = new HashSet<Integer>();
		final HashMap<Mapping, ArrayList<MappingCell>> mappingCellHash = new HashMap<Mapping, ArrayList<MappingCell>>();
		for (Mapping m : inputMappings) {
			schemaIDs.add(m.getSourceId());
			schemaIDs.add(m.getTargetId());
			mappingCellHash.put(m, OpenIIManager.getMappingCells(m.getId()));
		}

		// Create synsetTerm for each schema element
		final HashMap<String, SynsetTerm> synsetTerms = new HashMap<String, SynsetTerm>();
		Iterator<Integer> sItr = schemaIDs.iterator();
		while (sItr.hasNext()) {
			Integer sID = sItr.next();
			SchemaModel schemaModel = project.getSchemaModel(sID);
			HierarchicalSchemaInfo schemaInfo = new HierarchicalSchemaInfo(OpenIIManager.getSchemaInfo(sID), schemaModel);
			for (SchemaElement ele : schemaInfo.getHierarchicalElements()) {
				SynsetTerm newSynsetTerm = new SynsetTerm(sID, ele.getId(), ele.getName());
				synsetTerms.put(new String(sID + "-" + ele.getId()), newSynsetTerm);
			}
		}

		// The containers are the schemas
		ContainerMethod<SynsetTerm> method = new ContainerMethod<SynsetTerm>() {
			public int getContainerFor(SynsetTerm v) {
				return v.schemaId;
			}
		};

		// Disjoint Set Forest
		DisjointSetForest<SynsetTerm> dsf = new DisjointSetForest<SynsetTerm>(synsetTerms.values().toArray(new SynsetTerm[0]), method, schemaIDs.size());
		for (Mapping mapping : inputMappings) {
			Integer inputSchema = mapping.getSourceId();
			Integer outputSchema = mapping.getTargetId();

			ArrayList<MappingCell> mcList = mappingCellHash.get(mapping);

			// First sort the mapping cells from high score to low
			MappingCell[] mcArray = mcList.toArray(new MappingCell[0]);
			Arrays.sort(mcArray, new Comparator<MappingCell>() {
				public int compare(MappingCell mc1, MappingCell mc2) {
					if (mc1.isValidated() && mc2.isValidated())
						return -(mc1.getScore().compareTo(mc2.getScore()));
					else if (mc1.isValidated())
						return 1;
					else if (mc2.isValidated())
						return -1;
					else
						return -(mc1.getScore().compareTo(mc2.getScore()));
				}
			});

			// Then call DisjointSetForest merge on the mapping cells.
			for (MappingCell mc : mcArray) {
				for (MappingCellInput input : mc.getInputs()) {
					boolean merged = dsf.merge(synsetTerms.get(new String(inputSchema + "-" + input.getElementID())), synsetTerms.get(new String(outputSchema + "-" + mc.getOutput())), mc.isValidated());
					System.out.println((merged ? "merge " : "not merged ") + " = " + inputSchema + "-" + input.getElementID() + " && " + outputSchema + "-" + mc.getOutput());
				}
			}
		}

		// Now that DSF is done, reorganize the data into synsets
		HashMap<Integer, Synset> synsets = new HashMap<Integer, Synset>();
		for (SynsetTerm term : synsetTerms.values()) {
			int root = dsf.find(term);
			Synset termSet = synsets.get(new Integer(root));
			if (termSet == null) {
				termSet = new Synset(term);
				synsets.put(new Integer(root), termSet);
			} else
				termSet.add(term);
		}

		// Normalize synsets for NxN mappings, ie synsets with N>1 elements from
		// one schema
//		ArrayList<Synset> synsetList = new ArrayList<Synset>();
//		for (Synset synset : synsets.values())
//			synsetList.addAll(normalizeSet(synset));

		long endDSF = System.currentTimeMillis();
		// Generate connonical terms for each synset
		vocabulary = new Vocabulary(project.getId(), generateVocabTerms(new ArrayList<Synset>(synsets.values())));

		// Use the following line if normalization is used. 
		//		vocabulary = new Vocabulary(project.getId(), generateVocabTerms(synsetList));
		
		// Save new vocab
		OpenIIManager.saveVocabulary(vocabulary);

		long afterSave = System.currentTimeMillis();

		System.out.println("DSF time " + (endDSF - start) / new Double(1000) / new Double(60) + " minutes");
		System.out.println("Save time " + (afterSave - endDSF) / new Double(1000) / new Double(60) + " minutes ");
		return vocabulary;
	}

	/**
	 * FAILED FIRST ATTEMPT Synsets at the end of DSF may have more than one
	 * element from one schema participating. This normalization function
	 * creates multiple synset for added elements.
	 * 
	 * @param synset
	 * @return
	 */
	private ArrayList<Synset> normalizeSet(Synset synset) {
		// Build a hash of schema ID to corresponding synset term list

		HashMap<Integer, ArrayList<SynsetTerm>> schemaTermHash = new HashMap<Integer, ArrayList<SynsetTerm>>();
		for (SynsetTerm term : synset.terms) {
			if (!schemaTermHash.containsKey(term.schemaId))
				schemaTermHash.put(term.schemaId, new ArrayList<SynsetTerm>());
			schemaTermHash.get(term.schemaId).add(term);
		}

		ArrayList<Integer> schemas = new ArrayList<Integer>(schemaTermHash.keySet());

		// Calculate the total number of synsets
		int totalSynsets = 1;
		for (ArrayList<SynsetTerm> termList : schemaTermHash.values())
			totalSynsets *= termList.size();

		ArrayList<Synset> result = new ArrayList<Synset>(totalSynsets);
		if (totalSynsets == 1) {
			result.add(synset);
			return result;
		}

		// Fill term in a total of (# of synset / # terms in schema) synsets.
		for (int i = 0; i < totalSynsets; i++)
			result.add(i, new Synset());

		for (int schemaIDX = 0; schemaIDX < schemas.size(); schemaIDX++) {

			ArrayList<SynsetTerm> termList = schemaTermHash.get(schemas.get(schemaIDX));

			int offset = 1;
			for (int s = schemaIDX; s < schemas.size() - 1; s++)
				offset *= schemaTermHash.get(schemas.get(s + 1)).size();

			int termIDX = 0;
			int j = 0;
			for (int i = 0; i < result.size(); i++) {
				result.get(i).add(termList.get(termIDX));
				j++;
				if (j >= offset) {
					j = 0;
					termIDX++;
					if (termIDX >= termList.size())
						termIDX = 0;
				}
			}
		}

		return result;
	}

	void printVocab() {
		for (Term t : vocabulary.getTerms()) {
			System.out.print(t.getName() + ", ");
			for (AssociatedElement e : t.getElements())
				System.out.print(e.getSchemaID() + "-" + e.getElementID() + ", ");
			System.out.println();
		}
	}

	private Term[] generateVocabTerms(ArrayList<Synset> list) {
		Term[] termArray = new Term[list.size()];

		// Create a canonical term for each synset
		for (int t = 0; t < list.size(); t++)
			termArray[t] = generateConnicalTerm(list.get(t));
		return termArray;
	}

	/**
	 * Use one default vocabulary exporter to export the vocabulary to file
	 * 
	 * @param vocabulary
	 * @param file
	 * @throws IOException
	 */
	public static void exportVocabulary(Vocabulary vocabulary, File file) throws IOException {
		CompleteVocabExporter exporter = new CompleteVocabExporter();
		exporter.setClient(RepositoryManager.getClient());
		exporter.exportVocabulary(vocabulary, file);
	}

	public Integer getAuthoritativeSchemaId() {
		if (authoritySchemaId == 0 && project.getSchemaIDs().length > 0)
			authoritySchemaId = project.getSchemaIDs()[0];
		return authoritySchemaId;
	}

	public void setAuthoritativeSchemaId(Integer id) {
		authoritySchemaId = id;
	}

	/**
	 * Generate synsets from all matches by using MatchMaker's cluster
	 * (Implemented as part of the MatchMakerExporter
	 * 
	 * @throws RemoteException
	 **/
	public static ArrayList<org.mitre.schemastore.porters.projectExporters.matchmaker.Synset> generateSynsets(Project project, ArrayList<Mapping> inputMappings) throws RemoteException {
		HashMap<Mapping, ArrayList<MappingCell>> mappingCellHash = new HashMap<Mapping, ArrayList<MappingCell>>();
		for (Mapping mapping : inputMappings)
			mappingCellHash.put(mapping, OpenIIManager.getMappingCells(mapping.getId()));

		MatchMakerExporter matchMaker = new MatchMakerExporter();
		matchMaker.setClient(RepositoryManager.getClient());
		matchMaker.initialize(project, mappingCellHash);

		System.out.println(" Cluster all matches...");
		ClusterNode cluster = matchMaker.cluster();
		System.out.println(" Cluster complete ");
		return cluster.synsets;
	}

	/** generates a canonical term from the synset **/
	protected Term generateConnicalTerm(Synset synset) {
		Term resultTerm = null;
		AssociatedElement[] assocElements = new AssociatedElement[synset.terms.size()];
		SchemaElement baseElement = null;
		try {
			for (int i = 0; i < synset.terms.size(); i++) {
				SynsetTerm synsetTerm = synset.terms.get(i);

				// Create associated elements
				assocElements[i] = new AssociatedElement(synsetTerm.schemaId, synsetTerm.elementId, synsetTerm.elementName);

				// Use authority schema term if available
				if (synsetTerm.schemaId.equals(getAuthoritativeSchemaId()) && synsetTerm.elementName.length() > 0)
					baseElement = RepositoryManager.getClient().getSchemaElement(synsetTerm.elementId);
			}

			// Use a random term name if cannot find a name
			if (baseElement == null) {
				for (SynsetTerm t : synset.terms)
					if (t.elementName.length() > 0) {
						baseElement = RepositoryManager.getClient().getSchemaElement(t.elementId);
						break;
					}
			}
		} catch (RemoteException e1) {
			// Do nothing
		}

		resultTerm = (baseElement == null) ? new Term(null, new String(), new String(), assocElements) : new Term(null, baseElement.getName(), baseElement.getDescription(), assocElements);
		return resultTerm;
	}

	public void exportVocabulary(File file) throws IOException {
		Unity.exportVocabulary(this.vocabulary, file);
	}
}
