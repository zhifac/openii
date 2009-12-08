package org.mitre.harmony.controllers;

import java.util.ArrayList;
import java.util.HashSet;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;

/** Class for various mapping controls */
public class MappingController
{
	/** Retrieves the focused mapping cells */
	static public ArrayList<MappingCell> getFocusedMappingCells(HarmonyModel harmonyModel)
	{
		// Retrieve the visible source and target nodes
		HashSet<Integer> leftIDs = new HashSet<Integer>(harmonyModel.getFilters().getFocusedElementIDs(MappingSchema.LEFT));
		HashSet<Integer> rightIDs = new HashSet<Integer>(harmonyModel.getFilters().getFocusedElementIDs(MappingSchema.RIGHT));	    	
		
		// Create list of all mapping cells in focus
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		for(Integer leftID : leftIDs)
		{
			ArrayList<Integer> mappingCellIDs = harmonyModel.getMappingCellManager().getMappingCellsByElement(leftID);
			MAPPING_CELL_LOOP: for(Integer mappingCellID : mappingCellIDs)
			{
				// Make sure that all elements referenced by the mapping cell do exist
				MappingCell mappingCell = harmonyModel.getMappingCellManager().getMappingCell(mappingCellID);
				for(Integer inputID : mappingCell.getInput())
					if(!leftIDs.contains(inputID)) continue MAPPING_CELL_LOOP;
				if(!rightIDs.contains(mappingCell.getOutput())) continue;
				mappingCells.add(mappingCell);
			}
		}
		return mappingCells;
	}
	
	/** Mark visible mapping cells associated with the specified node as finished */
	static public void markAsFinished(HarmonyModel harmonyModel, Integer elementID)
	{
		// Retrieve the focused mapping cells
		ArrayList<MappingCell> focusedMappingCells = getFocusedMappingCells(harmonyModel);
		
		// Identify the visible and hidden links
		MappingCellManager manager = harmonyModel.getMappingCellManager();
		ArrayList<MappingCell> visibleMappingCells = new ArrayList<MappingCell>();
		ArrayList<MappingCell> hiddenMappingCells = new ArrayList<MappingCell>();
		for(Integer mappingCellID : manager.getMappingCellsByElement(elementID))
		{
			MappingCell mappingCell = manager.getMappingCell(mappingCellID);
			if(!focusedMappingCells.contains(mappingCell) || !harmonyModel.getFilters().isVisibleMappingCell(mappingCellID))
				hiddenMappingCells.add(mappingCell);
			else if(!mappingCell.getValidated())
				visibleMappingCells.add(mappingCell);
		}		
		
		// Mark all visible links as user selected and all others as rejected
		manager.validateMappingCells(visibleMappingCells);
		manager.deleteMappingCells(hiddenMappingCells);
	}
	
	/** Deletes hidden mapping cells from the area currently in focus */
	static public void deleteHiddenMappingCells(HarmonyModel harmonyModel)
	{
		ArrayList<MappingCell> hiddenMappingCells = new ArrayList<MappingCell>();
		for(MappingCell mappingCell : getFocusedMappingCells(harmonyModel))    			
			if(!harmonyModel.getFilters().isVisibleMappingCell(mappingCell.getId()))
				hiddenMappingCells.add(mappingCell);
		harmonyModel.getMappingCellManager().deleteMappingCells(hiddenMappingCells);		
	}
	
	/** Deletes all mapping cells from the area currently in focus */
	static public void deleteAllMappingCells(HarmonyModel harmonyModel)
	{
		ArrayList<MappingCell> mappingCells = getFocusedMappingCells(harmonyModel);
		harmonyModel.getMappingCellManager().deleteMappingCells(mappingCells);		
	}
}
