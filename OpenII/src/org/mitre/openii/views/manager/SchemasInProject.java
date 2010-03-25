package org.mitre.openii.views.manager;

/** Class for storing a project schema */
public class SchemasInProject
{
	/** Stores the project id to which this schema is associated */
	private Integer projectID = null;
	
	/** Constructs the project schema */
	SchemasInProject(Integer projectID)
		{ this.projectID = projectID; }

	/** Returns the project id to which this schema is associated */
	public Integer getProjectId() { return projectID; }
	
	/** Returns the string representation of this project schema */
	public String toString() { return "Schemas"; }
}
