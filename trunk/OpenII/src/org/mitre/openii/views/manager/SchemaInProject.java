package org.mitre.openii.views.manager;

import org.mitre.schemastore.model.Schema;

/** Class for storing a project schema */
public class SchemaInProject
{
	/** Stores the project id to which this schema is associated */
	private Integer projectID = null;
	
	/** Stores the schema */
	private Schema schema = null;

	/** Indicates if the schema can be deleted */
	private boolean deletable = true;
	
	/** Constructs the project schema */
	SchemaInProject(Integer projectID, Schema schema, boolean deletable)
		{ this.projectID = projectID; this.schema = schema; this.deletable = deletable; }

	/** Returns the project id to which this schema is associated */
	public Integer getProjectID() { return projectID; }
	
	/** Returns the schema */
	public Schema getSchema() { return schema; }

	/** Indicates if the schema is deletable */
	public boolean isDeletable() { return deletable; }
	
	/** Returns the string representation of this project schema */
	public String toString() { return schema.toString(); }
}
