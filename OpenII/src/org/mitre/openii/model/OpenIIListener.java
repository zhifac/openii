package org.mitre.openii.model;

import java.util.ArrayList;

/** Interface used by objects listening to the OpenII model */
public interface OpenIIListener
{
	/** Informs the listener that the repository has been reset */
	public void repositoryReset();

	/** Informs the listener that the specified schema has been added */
	public void schemaAdded(Integer schemaID);

	/** Informs the listener that the specified schema has been modified */
	public void schemaModified(Integer schemaID);
	
	/** Informs the listener that the specified schema has been deleted */
	public void schemaDeleted(Integer schemaID);

	/** Informs the listener that the specified data source has been added */
	public void dataSourceAdded(Integer dataSourceID);
	
	/** Informs the listener that the specified data source has been deleted */
	public void dataSourceDeleted(Integer dataSourceID);
	
	/** Informs the listener that the specified tag has been added */
	public void tagAdded(Integer tagID);

	/** Informs the listener that the specified tag has been modified */
	public void tagModified(Integer tagID);
	
	/** Informs the listener that the specified tag has been deleted */
	public void tagDeleted(Integer tagID);
	
	/** Informs the listener that the specified project has been added */
	public void projectAdded(Integer projectID);
	
	/** Informs the listener that the specified project has been modified */
	public void projectModified(Integer projectID);
	
	/** Informs the listener that the specified project has been deleted */
	public void projectDeleted(Integer projectID);

	/** Informs the listener that the specified projects have been merged together */
	public void projectsMerged(ArrayList<Integer> projectIDs, Integer mergedProjectID);
	
	/** Informs the listener that the specified mapping has been added */
	public void mappingAdded(Integer mappingID);

	/** Informs the listener that the specified mapping has been modified */
	public void mappingModified(Integer mappingID);
	
	/** Informs the listener that the specified mapping has been deleted */
	public void mappingDeleted(Integer mappingID);
	
	/** Informs the listener that the specified vocabulary has been added */
	public void vocabularyAdded(Integer projectID);
	
	/** Informs the listener that the specified vocabulary has been saved */
	public void vocabularyModified(Integer projectID);
	
	/** Informs the listener that the specified vocabulary has been deleted */
	public void vocabularyDeleted(Integer projectID);
}
