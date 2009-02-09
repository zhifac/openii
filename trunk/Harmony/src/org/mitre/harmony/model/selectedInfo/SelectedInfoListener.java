// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.selectedInfo;

/**
 * Interface used by all Harmony selected info listeners
 * @author CWOLF
 */
public interface SelectedInfoListener
{
	/** Indicates that the selected schemas have been modified */
	public void selectedSchemasModified();

	/** Indicates that the selected nodes have been modified */
	public void selectedElementsModified(Integer role);
	
	/** Indicates that the selected links have been modified */
	public void selectedMappingCellsModified();
}
