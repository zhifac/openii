package org.mitre.openii.dialogs.projects.unity;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.projectExporters.MatchMakerExporter;
import org.mitre.schemastore.porters.projectExporters.matchmaker.ClusterNode;
import org.mitre.schemastore.porters.projectExporters.matchmaker.ClusterRenderer;
import org.mitre.schemastore.porters.projectExporters.matchmaker.Synset;
import org.mitre.schemastore.porters.projectExporters.matchmaker.SynsetTerm;

public class Unity {

	private Project project;
	private Vocabulary vocabulary;
	private int authoritySchemaId = 0;

	public Unity(Project project) {
		this.project = project;
	}

	public static Vocabulary getProjectVocabulary(Project project) throws Exception {
		Unity unity = new Unity(project);
		Vocabulary vocabulary = new Vocabulary(project);

		if (!vocabulary.exists()) vocabulary = unity.getVocabulary();
		return vocabulary;
	}

	public Vocabulary getVocabulary() throws Exception {
		vocabulary = new Vocabulary(project);
		return generateVocabulary(null, null, null, null);
	}

	public Vocabulary replaceVocabulary(ArrayList<Matcher> matchers, String name, String author, String description) throws Exception {
		vocabulary = new Vocabulary(project);
		if (vocabulary.exists()) vocabulary.deleteVocabularySchema();
		return generateVocabulary(matchers, name, author, description);
	}

	/**
	 * Generate a core for a project. It will make sure that all pairwise mappings are created. Then MatchMaker is used
	 * to cluster the mappings. A new core schema is created with a new schema element added to each synset.
	 * 
	 * @param project
	 * @param matchers
	 *            default matchers will be used if null
	 * @param name
	 *            name of the core
	 * @param author
	 *            author of the core
	 * @param description
	 *            description of the core
	 * @return a vocabulary object
	 * @throws Exception
	 */
	public Vocabulary generateVocabulary(ArrayList<Matcher> matchers, String name, String author, String description) throws Exception {
		vocabulary = new Vocabulary(project);
		if (vocabulary.exists()) return vocabulary;

		if (matchers == null) matchers = MappingProcessor.getDefaultMatchers();
		if (name == null) name = project.getName() + Vocabulary._VOCABULARY_TAG;
		if (author == null) author = System.getProperty("user.name");
		if (description == null) description = "Unity auto-generated vocabulary";

		// Ensure that all pair-wise mappings already exist
		try {
			System.out.println(" Running all matches ... ");
			MappingProcessor.run(project, matchers);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Match all error. " + e.getMessage());
		}
		
		// Run MatchMaker to cluster the matches get synsets
		try {
			System.out.println(" Generating synsets...");
			vocabulary.setSynsetList(generateSynsets(project));
		} catch (RemoteException e) {
			throw new Exception("Error occured while generating synsets. \n" + e.getMessage());
		}

		// Create a vocabulary schema
		System.out.println("Build vocabulary schema...");
		generateCoreSchema(name, author, description);
		System.out.println("new schema id = " + vocabulary.getVocabularyId());

		// Create mappings for each source schema to the vocabulary schema
		System.out.println("Generate mappings for vocabulary schema... ");
		HashMap<Integer, Integer> vocabMappingHash = new HashMap<Integer, Integer>();
		HashMap<Integer, ArrayList<MappingCell>> mappings = new HashMap<Integer, ArrayList<MappingCell>>();
		for (Integer sourceSchemaId : project.getSchemaIDs()) {
			if (sourceSchemaId.equals(vocabulary.getVocabularyId())) continue;

			Mapping newMapping = new Mapping(null, project.getId(), sourceSchemaId, vocabulary.getVocabularyId());
			Integer newMappingId = OpenIIManager.addMapping(newMapping);
			if (newMappingId != null) {
				vocabMappingHash.put(sourceSchemaId, newMappingId);
				mappings.put(newMappingId, new ArrayList<MappingCell>());
				System.out.println("new mapping: " + newMappingId + " added for " + sourceSchemaId);
			} else System.err.println("Add mapping failed for " + sourceSchemaId);
		}

		// Create a new term for each synset and a mapping cell to existing terms
		for (Synset synset : vocabulary.getSynsetList()) {
			// Add a new canonical term to each synset
			SchemaElement vocabElement = createCoreTerm(synset);
			synset.add(new SynsetTerm(vocabulary.getVocabularyId(), vocabElement.getId(), vocabElement.getName()));

			// Create mappingcells between the new term and existing terms in a synset
			for (SynsetTerm sourceTerm : synset.getGroup()) {
				Integer mappingId = vocabMappingHash.get(sourceTerm.schemaId);
				if (mappingId == null) continue;
				MappingCell mappingcell = MappingCell.createIdentityMappingCell(null, mappingId, sourceTerm.elementId, vocabElement.getId(), "unity", new Date(), "");
				mappings.get(mappingId).add(mappingcell);
			}
		}

		// Save mapping cells
		System.out.println("Saving mapping cells... ");
		for (Integer mappingId : mappings.keySet())
			if (!RepositoryManager.getClient().saveMappingCells(mappingId, mappings.get(mappingId))) throw new Exception("Unable to save mappings");

		System.out.println("Complete Vocabulary.");
		return vocabulary;
	}

	public void exportVocabulary(File file) throws IOException {
		// reorganize the schema IDs so that vocabulary appears first
		Integer[] schemaIds = project.getSchemaIDs();
		for (int i = 1; i < schemaIds.length; i++) {
			if (schemaIds[i].equals(vocabulary.getVocabularyId())) {
				Integer idZero = new Integer(schemaIds[0]);
				schemaIds[0] = vocabulary.getVocabularyId();
				schemaIds[i] = idZero;
				break;
			}
		}

		// Render to an xls file
		ClusterRenderer render = new ClusterRenderer(vocabulary.getSynsetList(), RepositoryManager.getClient(), schemaIds);
		render.print(file);
	}

	public Integer getAuthoritativeSchemaId() {
		if (authoritySchemaId == 0 && project.getSchemaIDs().length > 0) authoritySchemaId = project.getSchemaIDs()[0];
		return authoritySchemaId;
	}

	public void setAuthoritativeSchemaId(Integer id) {
		authoritySchemaId = id;
	}

	/** Generate a core schema and set that to be the core vocabulary schema **/
	private void generateCoreSchema(String name, String author, String description) {
		// Create a new schema
		Schema vocabSchema = new Schema(null, name, author, null, null, description, false);
		Integer vocabularyID = OpenIIManager.addSchema(vocabSchema);
		if (vocabularyID != null) {
			vocabSchema.setId(vocabularyID);
			OpenIIManager.updateSchema(vocabSchema);
			vocabulary.setCoreID(vocabularyID);

			// Add vocabulary schema to the project
			ArrayList<ProjectSchema> pSchemas = new ArrayList<ProjectSchema>(Arrays.asList(project.getSchemas()));
			pSchemas.add(new ProjectSchema(vocabSchema.getId(), vocabSchema.getName(), null));
			project.setSchemas(pSchemas.toArray(new ProjectSchema[0]));
			OpenIIManager.updateProject(project);
		} else System.err.println(" Vocabulary schema wasn't added. ");
	}

	/**
	 * Generate synsets from all matches by using MatchMaker's cluster (Implemented as part of the MatchMakerExporter
	 * 
	 * @throws RemoteException
	 **/
	public static ArrayList<Synset> generateSynsets(Project project) throws RemoteException {
		HashMap<Mapping, ArrayList<MappingCell>> mappings = new HashMap<Mapping, ArrayList<MappingCell>>();
		for (Mapping mapping : OpenIIManager.getMappings(project.getId()))
			mappings.put(mapping, OpenIIManager.getMappingCells(mapping.getId()));

		MatchMakerExporter matchMaker = new MatchMakerExporter();
		matchMaker.setClient(RepositoryManager.getClient());
		matchMaker.initialize(project, mappings);

		System.out.println(" Cluster all matches...");
		ClusterNode cluster = matchMaker.cluster();
		System.out.println(" Cluster complete "); 
		return cluster.synsets;
	}

	/**
	 * TODO currently returning some random schema name.
	 * 
	 * @param synset
	 * @return
	 * @throws RemoteException
	 */
	protected SchemaElement createCoreTerm(Synset synset) {
		String name = null;
		String description = null;
		Integer newElementId = 0;
		Entity entity = null;

		SchemaElement sourceTerm = sourceTerm(synset);
		if (sourceTerm == null) name = description = "";
		else {
			name = sourceTerm.getName();
			description = sourceTerm.getDescription();
		}

		// Add term to the repository
		try {
			entity = new Entity(0, name, description, vocabulary.getVocabularyId());
			newElementId = RepositoryManager.getClient().addSchemaElement(entity);
			entity.setId(newElementId);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/** generates a cononical term from the synset **/
	protected SchemaElement sourceTerm(Synset synset) {
		SchemaElement result = null;
		try {
			// Use authority schema term if available
			for (SynsetTerm term : synset.getGroup())
				if (term.schemaId.equals(getAuthoritativeSchemaId()) && term.elementName.length() > 0) {
					SchemaElement e = RepositoryManager.getClient().getSchemaElement(term.elementId);
					if (e != null) {
						result = e.copy();
						result.setDescription(e.getDescription() + "\n(Source schema: " + term.schemaId + ")");
						return result;
					}
				}

			// Use a random non-null term
			for (SynsetTerm term : synset.getGroup())
				if (!term.schemaId.equals(authoritySchemaId) && term.elementName.length() > 0) {
					SchemaElement e = RepositoryManager.getClient().getSchemaElement(term.elementId);
					if (e != null) {
						result = e.copy();
						result.setDescription(e.getDescription() + "\n(Source schema: " + term.schemaId + "; source element: " + term.elementId + ")");
						return result;
					}
				}
		} catch (RemoteException e1) {
			// Do nothing
		}
		return result;
	}

	public void setAuthoritativeSchema(Integer schemaId) {
		this.authoritySchemaId = schemaId;
	}

}
