package org.mitre.openii.views.manager.projects.unity;

import java.util.ArrayList;

import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.views.manager.projects.matchmaker.MappingProcessor;
import org.mitre.openii.views.manager.projects.matchmaker.MatchMaker;
import org.mitre.openii.views.manager.projects.matchmaker.Synset;
import org.mitre.openii.views.manager.projects.matchmaker.Term;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

public class Unity {

	private Project project;
	private Vocabulary vocabulary; 
	
	public Unity(Project project) {
		this.project = project;
		this.vocabulary = new Vocabulary(project);
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
		Integer vocabularyID = null;

		// Ensure that all pair-wise mappings already exist
		MappingProcessor.run(project, voters);

		// Run MatchMaker to cluster the matches get synsets
		vocabulary.setSynsetList( new MatchMaker(project).cluster().getSynsets() );

		// Create a core schema and add an empty
		vocabulary.setCore(  new Schema(0, name, author, null, null, description, false) );

		vocabularyID = RepositoryManager.getClient().addSchema(vocabulary.getCore());
		for (Synset s : vocabulary.getSynsetList()) {
			SchemaElement newElement = new SchemaElement(); 
			newElement.setName(createCoreTerm(s)); 
			newElement.setBase(vocabularyID); 
			Integer newElementID = RepositoryManager.getClient().addSchemaElement(newElement);
			newElement.setId(newElementID); 
			s.combineSynsets(new Synset(new Term(vocabularyID, newElementID, newElement.getName()))); 
		}

		return vocabulary;
	}

	/**
	 * TODO currently returning some random schema name.
	 * @param s
	 * @return
	 */
	private String createCoreTerm(Synset s) {
		return s.getGroup().get(0).elementName; 
	}
	
	public Vocabulary getVocabulary(){
		return vocabulary;
	}

}
