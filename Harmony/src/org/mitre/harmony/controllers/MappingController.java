package org.mitre.harmony.controllers;

import java.util.ArrayList;
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
		// Cycle through all mappings in the project
		for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
		{
			// Identify all computer generated links
			ArrayList<MappingCell> mappingCellsToDelete = new ArrayList<MappingCell>();
			for(Integer mappingCellID : mapping.getMappingCellsByElement(elementID))
			{
				MappingCell mappingCell = mapping.getMappingCell(mappingCellID);
				if(!mappingCell.getValidated()) mappingCellsToDelete.add(mappingCell);
			}		
			
			// Delete all Mark all visible links as user selected and all others as rejected
			mapping.deleteMappingCells(mappingCellsToDelete);
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
