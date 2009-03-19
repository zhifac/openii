// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.mapping;

import org.mitre.schemastore.model.MappingCell;

/**
 * Interface used by all Harmony mapping cell listeners
 * @author CWOLF
 */
public interface MappingCellListener
{
	/** Indicates that a mapping cell has been added */
	public void mappingCellAdded(MappingCell mappingCell);

	/** Indicates that a mapping cell has been modified */
	public void mappingCellModified(MappingCell oldMappingCell, MappingCell newMappingCell);
	
	/** Indicates that a mapping cell has been removed */
	public void mappingCellRemoved(MappingCell mappingCell);
}
