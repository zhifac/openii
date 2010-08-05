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
import org.mitre.schemastore.porters.projectExporters.matchmaker.ClusterRenderer;
import org.mitre.schemastore.porters.projectExporters.matchmaker.Synset;
import org.mitre.schemastore.porters.projectExporters.matchmaker.SynsetTerm;
import org.mitre.schemastore.porters.vocabularyExporters.CompleteVocabExporter;

public class Unity {

	private Project project;
	private Vocabulary vocabulary;
	private int authoritySchemaId = 0;
	private ArrayList<Synset> synsetList = null;

	public Unity(Project project) {
		this.project = project;
		this.vocabulary = OpenIIManager.getVocabulary(project.getId());
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	public Vocabulary unify(ArrayList<Mapping> inputMappings)
			throws RemoteException {
		// Generate synsets
		synsetList = generateSynsets(project, inputMappings);
		// Generate vocabulary terms
		vocabulary = new Vocabulary(project.getId(), generateVocabTerms());

		// save new vocab
		OpenIIManager.saveVocabulary(vocabulary);
		return vocabulary;
	}

	private Term[] generateVocabTerms() {
		Term[] termArray = new Term[synsetList.size()];

		// Create a canonical term for each synset
		for (int t = 0; t < synsetList.size(); t++)
			termArray[t] = generateConnicalTerm(synsetList.get(t));
		return termArray;
	}

	// TODO hook this up with one of the Vocabulary exporters
	public void exportVocabulary(File file) throws IOException {
		CompleteVocabExporter exporter = new CompleteVocabExporter();
		exporter.setClient(RepositoryManager.getClient());
		exporter.exportVocabulary(vocabulary, file);
	}

	// Render synsets without the vocabulary terms to
	public void exportSynsets(File file) throws IOException {
		ClusterRenderer render = new ClusterRenderer(synsetList,
				RepositoryManager.getClient(), project.getSchemaIDs());
		render.print(file);
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
	public static ArrayList<Synset> generateSynsets(Project project,
			ArrayList<Mapping> inputMappings) throws RemoteException {
		HashMap<Mapping, ArrayList<MappingCell>> mappingCellHash = new HashMap<Mapping, ArrayList<MappingCell>>();
		for (Mapping mapping : inputMappings)
			mappingCellHash.put(mapping, OpenIIManager.getMappingCells(mapping
					.getId()));

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
		AssociatedElement[] assocElements = new AssociatedElement[synset.nodes
				.size()];
		SchemaElement baseElement = null;
		try {
			for (int i = 0; i < synset.nodes.size(); i++) {
				SynsetTerm synsetTerm = synset.nodes.get(i);

				// Create associated elements
				assocElements[i] = new AssociatedElement(synsetTerm.schemaId,
						synsetTerm.elementId, synsetTerm.name);

				// Use authority schema term if available
				if (synsetTerm.schemaId.equals(getAuthoritativeSchemaId())
						&& synsetTerm.elementName.length() > 0)
					baseElement = RepositoryManager.getClient()
							.getSchemaElement(synsetTerm.elementId);
			}

			// Use a random term name if cannot find a name
			if (baseElement == null) {
				for (SynsetTerm t : synset.nodes)
					if (t.elementName.length() > 0) {
						baseElement = RepositoryManager.getClient()
								.getSchemaElement(t.elementId);
						break;
					}
			}
		} catch (RemoteException e1) {
			// Do nothing
		}

		resultTerm = (baseElement == null) ? new Term(null, new String(),
				new String(), assocElements) : new Term(null, baseElement
				.getName(), baseElement.getDescription(), assocElements);

		return resultTerm;
	}
}
