package org.mitre.openii.views.manager.projects.unity;

import java.util.ArrayList;

import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.projects.matchmaker.Synset;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

public class Vocabulary  {

	private SchemaInfo core = null;
	private ArrayList<Synset> synsetList = null;

	 public Vocabulary(Project project) {
	 // TODO Given a project from the database, get all of the schema, schema
	 // elements, core schema, and recreate the synsets
	 }

	public SchemaInfo getCore() {
		return core;
	}

	/**
	 * From existing project, generate the synsets. 
	 * @return
	 */
	public ArrayList<Synset> getSynsetList() {
		return synsetList;
	}

	public void setCore(Schema core) {
		this.core = OpenIIManager.getSchemaInfo(core.getId());
	}

	public void setSynsetList(ArrayList<Synset> synsetList) {
		this.synsetList = synsetList;
	}
	
	public Integer getId() {
		return core.getSchema().getId(); 
	}
}
