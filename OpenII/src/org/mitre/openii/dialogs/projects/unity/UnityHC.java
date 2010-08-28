package org.mitre.openii.dialogs.projects.unity;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.AssociatedElement;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Term;
import org.mitre.schemastore.model.Vocabulary;
import org.mitre.schemastore.porters.projectExporters.MatchMakerExporter;
import org.mitre.schemastore.porters.projectExporters.matchmaker.ClusterNode;
import org.mitre.schemastore.porters.projectExporters.matchmaker.Synset;
import org.mitre.schemastore.porters.projectExporters.matchmaker.SynsetTerm;
import org.mitre.schemastore.porters.vocabularyExporters.CompleteVocabExporter;

public class UnityHC {

	private Project project;
	private Vocabulary vocabulary;
	private int authoritySchemaId = 0;

	// private ArrayList<Synset> synsetList = null;

	public UnityHC(Project project) {
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
		long start = System.currentTimeMillis();
		
		// Generate synsets
		ArrayList<Synset> synsetList = generateSynsets(project, inputMappings);
		long endUnify = System.currentTimeMillis();
		System.out.println("Cluster time " + (endUnify - start) / new Double(1000) / new Double(60) + " minutes");
		
		// Generate vocabulary terms
		vocabulary = new Vocabulary(project.getId(), generateVocabTerms(synsetList));

		// save new vocab
		OpenIIManager.saveVocabulary(vocabulary);
		
		long afterSave = System.currentTimeMillis();
		System.out.println("Save time " + (afterSave - endUnify) / new Double(1000) / new Double(60) + " minutes ");
		return vocabulary;
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
	private static ArrayList<Synset> generateSynsets(Project project, ArrayList<Mapping> inputMappings) throws RemoteException {
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
		UnityHC.exportVocabulary(this.vocabulary, file);
	}
}
