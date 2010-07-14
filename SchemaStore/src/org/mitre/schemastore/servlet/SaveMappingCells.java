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
			MappingCell oldMappingCell = oldMappingCellRefs.get(mappingCell.getInput(),mappingCell.getOutput());
			mappingCell.setId(oldMappingCell==null ? null : oldMappingCell.getId());
		}
			
		// Keeps track of success
		boolean success = true;
		
		// Update the mapping
		try
		{
			// Removes all mapping cells that are no longer used
			for(MappingCell oldMappingCell : oldMappingCells)
				if(!mappingCellRefs.contains(oldMappingCell.getInput(),oldMappingCell.getOutput()))
					if(!manager.getProjectCache().deleteMappingCell(oldMappingCell.getId())) success = false;
					
			// Adds or updates all new mapping cells
			for(MappingCell mappingCell : mappingCells)
			{
				mappingCell.setMappingId(mappingID);

				// Handles new mapping cells
				if(mappingCell.getId()==null)
				{
					mappingCell.setId(manager.getProjectCache().addMappingCell(mappingCell));
					if(mappingCell.getId()==null) success = false;
				}
				
				// Handles updated mapping cells
				else if(!manager.getProjectCache().updateMappingCell(mappingCell)) success = false;
			}
			
			// If not successful, throw exception
			if(success==false) throw new Exception();
		}
		
		// Reverts back to the old mapping if a failure occurred
		catch(Exception e)
		{
			for(MappingCell mappingCell : manager.getProjectCache().getMappingCells(mappingID))
			{
				MappingCell oldMappingCell = oldMappingCellRefs.get(mappingCell.getInput(),mappingCell.getOutput());
				if(oldMappingCell==null) manager.getProjectCache().deleteMappingCell(mappingCell.getId());
				else manager.getProjectCache().updateMappingCell(oldMappingCell);
			}
		}
		
		return success;
	}
}