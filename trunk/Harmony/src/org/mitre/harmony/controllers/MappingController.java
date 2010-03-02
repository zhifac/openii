package org.mitre.harmony.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.project.ProjectMapping;
import org.mitre.schemastore.model.MappingCell;

/** Class for various mapping controls */
public class MappingController
{
	/** Mark visible mapping cells associated with the specified node as finished */
	static public void markAsFinished(HarmonyModel harmonyModel, Integer elementID)
	{
		// Retrieve the focused mapping cells
		HashSet<MappingCell> focusedMappingCells = harmonyModel.getFilters().getFocusedMappingCells();
		
		// Identify the visible and hidden links
		for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
		{
			ArrayList<MappingCell> visibleMappingCells = new ArrayList<MappingCell>();
			ArrayList<MappingCell> hiddenMappingCells = new ArrayList<MappingCell>();
			for(Integer mappingCellID : mapping.getMappingCellsByElement(elementID))
			{
				MappingCell mappingCell = mapping.getMappingCell(mappingCellID);
				if(!mappingCell.getValidated())
				{
					if(!focusedMappingCells.contains(mappingCell) || !harmonyModel.getFilters().isVisibleMappingCell(mappingCellID))
						hiddenMappingCells.add(mappingCell);
					else visibleMappingCells.add(mappingCell);
				}
			}		
			
			// Mark all visible links as user selected and all others as rejected
			mapping.validateMappingCells(visibleMappingCells);
			mapping.deleteMappingCells(hiddenMappingCells);
		}
	}
	
	/** Translate the list of mapping cells to mapping cell IDs */
	static public ArrayList<Integer> getMappingCellIDs(List<MappingCell> mappingCells)
	{
		ArrayList<Integer> mappingCellIDs = new ArrayList<Integer>();
		for(MappingCell mappingCell : mappingCells) mappingCellIDs.add(mappingCell.getId());
		return mappingCellIDs;
	} 
}