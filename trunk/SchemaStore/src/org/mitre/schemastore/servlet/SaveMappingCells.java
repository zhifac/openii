// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.mitre.schemastore.data.DataManager;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.mappingInfo.MappingCellHash;

/**
 * Handles the saving of a mapping to the schema store web service
 * @author CWOLF
 */
public class SaveMappingCells
{	
	/** Saves the specified mapping into the web services */
	static Boolean saveMappingCells(DataManager manager, Integer mappingID, ArrayList<MappingCell> mappingCells) throws RemoteException
	{		
		// Get old mapping cells
		ArrayList<MappingCell> oldMappingCells = manager.getProjectCache().getMappingCells(mappingID);
		
		// Build reference tables for the old and new mapping cells
		MappingCellHash oldMappingCellRefs = new MappingCellHash(oldMappingCells);
		MappingCellHash mappingCellRefs = new MappingCellHash(mappingCells);

		// Align mapping cell IDs with old mapping cell IDs
		for(MappingCell mappingCell : mappingCells)
		{
			MappingCell oldMappingCell = oldMappingCellRefs.get(mappingCell.getElementInputIDs(),mappingCell.getOutput());
			mappingCell.setId(oldMappingCell==null ? null : oldMappingCell.getId());
		}
			
		// Keeps track of success
		boolean success = true;
		
		// Update the mapping
		try
		{
			// Removes all mapping cells that are no longer used
			ArrayList<Integer> unusedMappingCellIDs = new ArrayList<Integer>();
			for(MappingCell oldMappingCell : oldMappingCells)
				if(!mappingCellRefs.contains(oldMappingCell.getElementInputIDs(),oldMappingCell.getOutput()))
					unusedMappingCellIDs.add(oldMappingCell.getId());
			success &= manager.getProjectCache().deleteMappingCells(unusedMappingCellIDs);
					
			// Identify the new and modified mapping cells
			ArrayList<MappingCell> newMappingCells = new ArrayList<MappingCell>();
			ArrayList<MappingCell> modifiedMappingCells = new ArrayList<MappingCell>();
			for(MappingCell mappingCell : mappingCells)
			{
				mappingCell.setMappingId(mappingID);
				if(mappingCell.getId()==null) newMappingCells.add(mappingCell);
				else modifiedMappingCells.add(mappingCell);
			}
				
			// Updates the mapping cells
			success &= manager.getProjectCache().addMappingCells(newMappingCells)!=null;
			success &= manager.getProjectCache().updateMappingCells(modifiedMappingCells);
			if(success==false) throw new Exception();
		}
		
		// Reverts back to the old mapping if a failure occurred
		catch(Exception e)
		{
			// Identify the old and new mapping cells
			ArrayList<MappingCell> originalMappingCells = new ArrayList<MappingCell>();
			ArrayList<Integer> newMappingCells = new ArrayList<Integer>();			
			for(MappingCell mappingCell : manager.getProjectCache().getMappingCells(mappingID))
			{
				MappingCell oldMappingCell = oldMappingCellRefs.get(mappingCell.getElementInputIDs(),mappingCell.getOutput());
				if(oldMappingCell==null) newMappingCells.add(mappingCell.getId());
				else originalMappingCells.add(oldMappingCell);
			}
			
			// Revert the mapping cells to their original condition
			manager.getProjectCache().deleteMappingCells(newMappingCells);
			manager.getProjectCache().updateMappingCells(originalMappingCells);
		}
		
		return success;
	}
}