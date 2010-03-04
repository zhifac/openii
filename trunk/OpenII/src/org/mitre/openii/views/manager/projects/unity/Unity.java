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
	private ArrayList<Synset> synsets; 
	private Schema vocabulary;

	public Unity(Project project) {
		this.project = project;
	}

	/**
	 * Generate a vocabulary for a project. It will make sure that all pairwise
	 * mappings are created. Then MatchMaker is used to cluster the mappings. A
	 * new vocabulary schema is created with a new schema element added to each
	 * synset.
	 * 
	 * @param project
	 * @param voters
	 *            default voters will be used if null
	 * @param name
	 *            name of the vocabulary
	 * @param author
	 *            author of the vocabulary
	 * @param description
	 *            description of the vocabulary
	 * @return
	 * @throws Exception
	 */
	public Schema generateVocabulary(ArrayList<MatchVoter> voters, String name, String author, String description) throws Exception {
		Integer vocabularyID = null;

		// Ensure that all pair-wise mappings already exist
		MappingProcessor.run(project, voters);

		// Run MatchMaker to cluster the matches get synsets
		synsets = new MatchMaker(project).cluster().getSynsets();

		// Create a vocabulary schema and add an empty
		vocabulary = new Schema(0, name, author, null, null, description, false);

		vocabularyID = RepositoryManager.getClient().addSchema(vocabulary);
		for (Synset s : synsets) {
			SchemaElement newElement = new SchemaElement(); 
			newElement.setName(createVocabTerm(s)); 
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
	private String createVocabTerm(Synset s) {
		return s.getGroup().get(0).elementName; 
	}
	
	public Schema getVocabulary() {
		return vocabulary;
	}
	
	public ArrayList<Synset> getSynsets() {
		return synsets;
	}

}
