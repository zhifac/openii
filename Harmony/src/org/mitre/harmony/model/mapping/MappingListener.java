// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.mapping;

/**
 * Interface used by all Harmony mapping listeners
 * @author CWOLF
 */
public interface MappingListener
{
	/** Indicates that a schema has been added to the mapping */
	public void schemaAdded(Integer schemaID);
	
	/** Indicates that a schema has been removed from the mapping */
	public void schemaRemoved(Integer schemaID);
	
	/** Indicates that the mapping has been modified */
	public void mappingModified();
}
