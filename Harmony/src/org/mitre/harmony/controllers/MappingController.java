package org.mitre.harmony.controllers;

import java.util.ArrayList;
import java.util.HashSet;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.schemastore.model.MappingCell;

/** Class for various mapping controls */
public class MappingController
{
	/** Mark visible mapping cells associated with the specified node as finished */
	static public void markAsFinished(HarmonyModel harmonyModel, Integer elementID)
	{
		// Retrieve the focused mapping cells
		HashSet<MappingCell> focusedMappingCells = FocusController.getFocusedMappingCells(harmonyModel);
		
		// Identify the visible and hidden links
		MappingCellManager manager = harmonyModel.getMappingCellManager();
		ArrayList<MappingCell> visibleMappingCells = new ArrayList<MappingCell>();
		ArrayList<MappingCell> hiddenMappingCells = new ArrayList<MappingCell>();
		for(Integer mappingCellID : manager.getMappingCellsByElement(elementID))
		{
			MappingCell mappingCell = manager.getMappingCell(mappingCellID);
			if(!mappingCell.getValidated())
			{
				if(!focusedMappingCells.contains(mappingCell) || !harmonyModel.getFilters().isVisibleMappingCell(mappingCellID))
					hiddenMappingCells.add(mappingCell);
				else visibleMappingCells.add(mappingCell);
			}
		}		
		
		// Mark all visible links as user selected and all others as rejected
		manager.validateMappingCells(visibleMappingCells);
		manager.deleteMappingCells(hiddenMappingCells);
	}
	
	/** Deletes hidden mapping cells from the area currently in focus */
	static public void deleteHiddenMappingCells(HarmonyModel harmonyModel)
	{
		ArrayList<MappingCell> hiddenMappingCells = new ArrayList<MappingCell>();
		for(MappingCell mappingCell : FocusController.getFocusedMappingCells(harmonyModel))    			
			if(!harmonyModel.getFilters().isVisibleMappingCell(mappingCell.getId()))
				hiddenMappingCells.add(mappingCell);
		harmonyModel.getMappingCellManager().deleteMappingCells(hiddenMappingCells);		
	}
	
	/** Deletes all mapping cells from the area currently in focus */
	static public void deleteAllMappingCells(HarmonyModel harmonyModel)
	{
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>(FocusController.getFocusedMappingCells(harmonyModel));
		harmonyModel.getMappingCellManager().deleteMappingCells(mappingCells);		
	}
}
