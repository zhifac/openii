package org.mitre.openii.views.manager.projects.unity;

import java.util.ArrayList;

import org.mitre.openii.views.manager.projects.matchmaker.Synset;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;

public class Vocabulary {
	private Schema core = null;
	private ArrayList<Synset> synsetList = null;

	public Vocabulary(Project project) {
	// TODO Given a project from the database, get all of the schema, schema
	// elements, core schema, and recreate the synsets
	}

	public Schema getCore() {
		return core;
	}

	public ArrayList<Synset> getSynsetList() {
		return synsetList;
	}

	public void setCore(Schema core) {
		this.core = core;
	}

	public void setSynsetList(ArrayList<Synset> synsetList) {
		this.synsetList = synsetList;
	}
}
