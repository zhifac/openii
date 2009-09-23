// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.filters;

/**
 * Interface used by all Harmony filter listeners
 * @author CWOLF
 */
public interface FiltersListener
{
	/** Indicates change to the specified filter */
	public void filterChanged(Integer filter);
	
	/** Indicates change to confidence filters */
	public void confidenceChanged();
	
	/** Indicates the addition of a focus */
	public void focusModified(Integer side);
	
	/** Indicates change to depth filter */
	public void depthChanged();
	
	/** Indicates change to max confidence filter */
	public void maxConfidenceChanged(Integer schemaObjectID);
}
