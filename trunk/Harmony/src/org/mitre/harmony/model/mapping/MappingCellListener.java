// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.mapping;

import java.util.List;

import org.mitre.schemastore.model.MappingCell;

/**
 * Interface used by all Harmony mapping cell listeners
 * @author CWOLF
 */
public interface MappingCellListener
{
	/** Indicates that mapping cells have been added */
	public void mappingCellsAdded(List<MappingCell> mappingCells);

	/** Indicates that mapping cells have been modified */
	public void mappingCellsModified(List<MappingCell> oldMappingCells, List<MappingCell> newMappingCells);
	
	/** Indicates that mapping cells have been removed */
	public void mappingCellsRemoved(List<MappingCell> mappingCells);
}
