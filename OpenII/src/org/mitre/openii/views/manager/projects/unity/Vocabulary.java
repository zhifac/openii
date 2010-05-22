package org.mitre.openii.views.manager.projects.unity;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.porters.projectExporters.matchmaker.Synset;

public class Vocabulary {

	static public String _VOCABULARY_TAG = "_Vocabulary";
	static private Tag vocabTag = null ;

	private SchemaInfo core = null;
	private ArrayList<Synset> synsetList = null;
	private Project project;

	public Vocabulary(Project project) {
		this.project = project;
		vocabTag = getVocabTag(); 
		synsetList = new ArrayList<Synset>(); 
	}

	public boolean exists() {
		return (getVocabularySchema() != null);
	}

	/** Retrieve core vocabulary from the schema store. if null, none has been generated for it yet **/
	public SchemaInfo getVocabularySchema() {
		if (core == null) {
			// See if there is a vocabulary schema defined already
			ArrayList<Integer> vocabIds = OpenIIManager.getTagSchemas(vocabTag.getId());
			for (Integer pSchemaId : project.getSchemaIDs())
				if (vocabIds.contains(pSchemaId)) {
					core = OpenIIManager.getSchemaInfo(pSchemaId);
					break;
				}
		}
		return core;
	}

	/**
	 * From existing project, generate the synsets.
	 * 
	 * @return
	 */
	public ArrayList<Synset> getSynsetList() {
		if (exists() && (synsetList == null || synsetList.size() == 0)) buildSynsets();
		return synsetList;
	}
	
	/** Returns an array of source schema id without the vocabulary id **/
	public Integer[] getSourceSchemaIds() {
		Integer[] schemaIds = project.getSchemaIDs(); 
		if ( !exists() ) return schemaIds; 
		
		Integer[] newIds = new Integer[schemaIds.length-1];
		int i = 0; 
		for ( Integer s : schemaIds ) 
			if (!s.equals(getVocabularyId()) ) 
				newIds[i++] = s;
		return newIds; 
	}

	/** From an existing vocabulary schema and a project of mappings, generate the synsets **/
	void buildSynsets() {
		try {
			synsetList = Unity.generateSynsets(project);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static Tag getVocabTag() {
		if (vocabTag != null) return vocabTag;

		// See if the tag already exists
		for (Tag t : OpenIIManager.getTags())
			if (t.getName().equals(Vocabulary._VOCABULARY_TAG)) {
				vocabTag = t;
				break;
			}

		// Create the vocabTag if it's not already existing
		if (vocabTag == null) {
			vocabTag = new Tag(null, Vocabulary._VOCABULARY_TAG, null);
			vocabTag.setId(OpenIIManager.addTag(vocabTag));
			OpenIIManager.fireTagAdded(vocabTag);
		}
		return vocabTag;
	}

	/** Sets a schema as teh core vocabulary **/
	public void setVocabularySchema(Schema newCore) {
		// If a core vocab already exists, remove vocab tag on that schema first
		if (exists() && getVocabularyId() != newCore.getId()) deleteVocabularySchema();

		this.core = OpenIIManager.getSchemaInfo(newCore.getId());

		// Tag core with vocabulary tag until we have a permanent vocabulary object in the database;
		ArrayList<Integer> coreIds = OpenIIManager.getTagSchemas(vocabTag.getId());
		if (!coreIds.contains(newCore.getId())) coreIds.add(newCore.getId());
		OpenIIManager.setTagSchemas(getVocabTag().getId(), coreIds);
	}
	

	public void setSynsetList(ArrayList<Synset> synsetList) {
		this.synsetList = synsetList;
	}

	public Integer getVocabularyId() {
		return (getVocabularySchema() == null) ? null : getVocabularySchema().getSchema().getId();
	}

	/**
	 * Deleting a vocabulary is the same as untagging a vocabulary schema, and removing it from the current project. The
	 * previous mappings will be saved still.
	 */
	public void deleteVocabularySchema() {
		System.out.println( " Delete vocabulary schema ... " + getVocabularyId()); 
		
		// Remove the schema from the project
		ArrayList<ProjectSchema> schemas = new ArrayList<ProjectSchema>(Arrays.asList(project.getSchemas()));
		for (ProjectSchema s : schemas)
			if (s.getId().equals(getVocabularyId())) {
				schemas.remove(s);
				break;
			}
		project.setSchemas((ProjectSchema[]) schemas.toArray()); 

		// Remove the vocabulary schema from the vocab tag
		ArrayList<Integer> currVocabIds = OpenIIManager.getTagSchemas(vocabTag.getId());
		if (!currVocabIds.contains(getVocabularyId())) return;
		currVocabIds.remove(getVocabularyId());
		OpenIIManager.setTagSchemas(vocabTag.getId(), currVocabIds);
	}

}
