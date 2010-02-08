package org.mitre.harmony.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.project.MappingManager;
import org.mitre.harmony.model.project.ProjectMapping;
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
	
	/** Deletes the specified mapping cells */
	static public void deleteMappingCells(HarmonyModel harmonyModel, ArrayList<MappingCell> mappingCells)
	{
		// Sort the mapping cells by the mapping they are associated with
		HashMap<Integer,ArrayList<MappingCell>> mappingCellMap = new HashMap<Integer,ArrayList<MappingCell>>();
		for(MappingCell mappingCell : mappingCells)
		{
			ArrayList<MappingCell> mappingCellList = mappingCellMap.get(mappingCell.getMappingId());
			if(mappingCellList==null) mappingCellMap.put(mappingCell.getMappingId(),mappingCellList=new ArrayList<MappingCell>());
			mappingCellList.add(mappingCell);
		}

		// Delete the mapping cells from their respective mappings
		MappingManager manager = harmonyModel.getMappingManager();
		for(Integer mappingID : mappingCellMap.keySet())
			manager.getMapping(mappingID).deleteMappingCells(mappingCellMap.get(mappingID));
	}
	
	/** Deletes hidden mapping cells from the area currently in focus */
	static public void deleteHiddenMappingCells(HarmonyModel harmonyModel)
	{
		ArrayList<MappingCell> hiddenMappingCells = new ArrayList<MappingCell>();
		for(MappingCell mappingCell : FocusController.getFocusedMappingCells(harmonyModel))    			
			if(!harmonyModel.getFilters().isVisibleMappingCell(mappingCell.getId()))
				hiddenMappingCells.add(mappingCell);
		deleteMappingCells(harmonyModel,hiddenMappingCells);		
	}
	
	/** Deletes all mapping cells from the area currently in focus */
	static public void deleteAllMappingCells(HarmonyModel harmonyModel)
	{
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>(FocusController.getFocusedMappingCells(harmonyModel));
		deleteMappingCells(harmonyModel,mappingCells);		
	}
}
