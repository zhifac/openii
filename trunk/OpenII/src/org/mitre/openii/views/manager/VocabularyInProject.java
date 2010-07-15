package org.mitre.openii.views.manager;


/** Class for storing a marker for a project vocabulary */
public class VocabularyInProject
{
	/** Stores the project id to which this vocabulary is associated */
	private Integer projectID = null;
	
	/** Constructs the project vocabulary */
	VocabularyInProject(Integer projectID)
		{ this.projectID = projectID; }

	/** Returns the project id to which this vocabulary is associated */
	public Integer getProjectID() { return projectID; }
	
	/** Returns the string representation of this project vocabulary */
	public String toString() { return "Vocabulary"; }
}
