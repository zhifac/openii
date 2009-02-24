package org.mitre.openii.model;

/** Interface used by objects listening to the OpenII model */
public interface OpenIIListener
{
	/** Informs the listener that the specified schema has been added */
	public void schemaAdded(Integer schemaID);
	
	/** Informs the listener that the specified schema has been removed */
	public void schemaDeleted(Integer schemaID);

	/** Informs the listener that the specified group has been added */
	public void groupAdded(Integer groupID);
	
	/** Informs the listener that the specified group has been removed */
	public void groupDeleted(Integer groupID);

	/** Informs the listener that the specified group has been modified */
	public void groupModified(Integer groupID);
	
	/** Informs the listener that the specified mapping has been added */
	public void mappingAdded(Integer mappingID);
	
	/** Informs the listener that the specified mapping has been removed */
	public void mappingRemoved(Integer mappingID);

	/** Informs the listener that the specified mapping has been modified */
	public void mappingModified(Integer mappingID);
}
