package org.mitre.openii.model;

/** Interface used by objects listening to the OpenII model */
public interface OpenIIListener
{
	/** Informs the listener that the specified schema has been added */
	public void schemaAdded(Integer schemaID);
	
	/** Informs the listener that the specified schema has been removed */
	public void schemaRemoved(Integer schemaID);

	/** Informs the listener that the specified schema has been modified */
	public void schemaModified(Integer schemaID);

	/** Informs the listener that the specified mapping has been added */
	public void mappingAdded(Integer schemaID);
	
	/** Informs the listener that the specified mapping has been removed */
	public void mappingRemoved(Integer schemaID);

	/** Informs the listener that the specified mapping has been modified */
	public void mappingModified(Integer schemaID);

	/** Informs the listener that the focus of OpenII has changed */
	public void focusModified();
}
