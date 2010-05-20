package org.mitre.openii.views.manager.projects.unity;

import java.util.ArrayList;

import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.porters.projectExporters.matchmaker.Synset;

public class Vocabulary {

	private SchemaInfo core = null;
	private ArrayList<Synset> synsetList = null;
	static public String _VOCABULARY_TAG = "_Vocabulary";
	static private Tag vocabTag = getVocabTag();

	public Vocabulary(Project project) {
	// TODO Given a project from the database, get all of the schema, schema
	// elements, core schema, and recreate the synsets
	}

	public SchemaInfo getCore() {
		return core;
	}

	/**
	 * From existing project, generate the synsets.
	 * 
	 * @return
	 */
	public ArrayList<Synset> getSynsetList() {
		return synsetList;
	}
	
	public static Tag getVocabTag() {
		if (vocabTag != null ) return vocabTag; 
		
		for (Tag t : OpenIIManager.getTags())
			if (t.getName().equals(Vocabulary._VOCABULARY_TAG)) {
				vocabTag = t;
				break;
			}
		
		if ( vocabTag == null ) {
			vocabTag = new Tag(null, Vocabulary._VOCABULARY_TAG, null); 
			vocabTag.setId( OpenIIManager.addTag(vocabTag) ); 
		}
		System.out.println("vocabTag: " + vocabTag.getId());
		return vocabTag;
	}

	public void setCore(Schema core) {
		this.core = OpenIIManager.getSchemaInfo(core.getId());
		
		// Tag core with vocabulary tag until we have a permanent vocabulary object in the database;
		ArrayList<Integer> coreIds = new ArrayList<Integer>();
		coreIds.add(core.getId());
		OpenIIManager.setTagSchemas(vocabTag.getId(), coreIds); 
	}

	public void setSynsetList(ArrayList<Synset> synsetList) {
		this.synsetList = synsetList;
	}

	public Integer getCoreSchemaId() {
		return core.getSchema().getId();
	}
	
	 
}
