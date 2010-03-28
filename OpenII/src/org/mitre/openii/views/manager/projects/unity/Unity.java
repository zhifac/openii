package org.mitre.openii.views.manager.projects.unity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.views.manager.projects.matchmaker.ClusterNode;
import org.mitre.openii.views.manager.projects.matchmaker.MappingProcessor;
import org.mitre.openii.views.manager.projects.matchmaker.MatchMaker;
import org.mitre.openii.views.manager.projects.matchmaker.Synset;
import org.mitre.openii.views.manager.projects.matchmaker.Term;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

public class Unity {

	private Project project;
	private Vocabulary vocabulary;

	public Unity(Project project) {
		this.project = project;
	}

	/**
	 * Generate a core for a project. It will make sure that all pairwise
	 * mappings are created. Then MatchMaker is used to cluster the mappings. A
	 * new core schema is created with a new schema element added to each
	 * synset.
	 * 
	 * @param project
	 * @param voters
	 *            default voters will be used if null
	 * @param name
	 *            name of the core
	 * @param author
	 *            author of the core
	 * @param description
	 *            description of the core
	 * @return
	 * @throws Exception
	 */
	public Vocabulary generateVocabulary(ArrayList<MatchVoter> voters, String name, String author, String description) throws Exception {
		vocabulary = new Vocabulary(project);

		// Ensure that all pair-wise mappings already exist
		MappingProcessor.run(project, voters);
		// Run MatchMaker to cluster the matches get synsets
		ClusterNode cluster  = new MatchMaker(project).cluster();

		// Set vocabulary synset
		vocabulary.setSynsetList(cluster.getSynsets());
		
		// Create a vocabulary schema
		Schema vocabSchema = new Schema(null, name, author, null, null, description, false); 
		Integer vocabularyID = OpenIIManager.addSchema(vocabSchema);
		vocabSchema.setId(vocabularyID);
		vocabulary.setCore(vocabSchema);

		// Add vocabulary schema to the project
		Integer[] sourceSchemaIds = project.getSchemaIDs();
		ArrayList<ProjectSchema> projSchemas = new ArrayList<ProjectSchema>();
		for (Integer schemaId : sourceSchemaIds) {
			projSchemas.add(new ProjectSchema(schemaId, OpenIIManager.getSchema(schemaId).getName(), null));
		}
		projSchemas.add( new ProjectSchema(vocabSchema.getId(), name, null) ); 
		// TODO how to set a schema as a project vocabulary? 
		
		HashMap<Integer, Integer> mappingHash = new HashMap<Integer, Integer>();
		// Add mappings for each source schema to the vocabulary schema
		for (Integer sourceSchemaId : sourceSchemaIds) {
			mappingHash.put(sourceSchemaId,OpenIIManager.addMapping(new Mapping(null, project.getId(), sourceSchemaId, vocabulary.getId())));
		}

		// Create a new term for each synset and mapping to existing terms
		for (Synset synset : vocabulary.getSynsetList()) {
			// Create a new schema element for the new term in schemastore
			SchemaElement newElement = new SchemaElement();
			newElement.setName(createCoreTerm(synset));
			newElement.setBase(vocabulary.getId());
			
			Integer newElementID = RepositoryManager.getClient().addSchemaElement(newElement);
			newElement.setId(newElementID);
			
			// Create mapping cells between the new term and the other term in the synset
			ArrayList<Term> sourceTerm = synset.getGroup(); 
			for ( Term term : sourceTerm ) {
				MappingCell.createValidatedMappingCell(null, mappingHash.get(term.schemaId), Arrays.asList(new Integer(term.elementId)).toArray(new Integer[0])	, newElementID, "unity", new Date(), "map function?", "");
			}

			// Add new term to synset
			synset.combineSynsets(new Synset(new Term(vocabulary.getId(), newElementID, newElement.getName())));

		}
		return vocabulary;
	}

	/**
	 * TODO currently returning some random schema name.
	 * 
	 * @param synset
	 * @return
	 */
	protected String createCoreTerm(Synset synset) {
		for (Term term : synset.getGroup())
			if (term.schemaId.equals(vocabulary.getId())) return term.elementName;

		return null;
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

}
