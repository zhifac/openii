// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.data.DataManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;

/**
 * Handles the saving of a mapping to the schema store web service
 * @author CWOLF
 */
public class SaveMapping
{	
	/** Private class for referencing mapping cell IDs */
	static private class MappingCellHashMap extends HashMap<String,MappingCell>
	{
		/** Generates a reference table key */
		private String getKey(MappingCell mappingCell)
		{
			StringBuffer key = new StringBuffer("");
			for(Integer input : mappingCell.getInput())
				key.append(input + " ");
			key.append(mappingCell.getOutput());
			return key.toString();
		}
			
		/** Constructs the mapping cell reference table */
		private MappingCellHashMap(ArrayList<MappingCell> mappingCells)
			{ for(MappingCell mappingCell : mappingCells) put(getKey(mappingCell),mappingCell); }

		/** Retrieves the ID for the specified mapping cell */
		private MappingCell getMappingCell(MappingCell mappingCell)
			{ return get(getKey(mappingCell)); }

		/** Indicates if the hash map contains the specified mapping cell */
		private boolean contains(MappingCell mappingCell)
			{ return containsKey(getKey(mappingCell)); }
	}
	
	/** Saves the specified mapping into the web services */
	static Integer saveMapping(DataManager manager, Mapping mapping, ArrayList<MappingCell> mappingCells) throws RemoteException
	{
		Integer mappingID = null;
		
		// Stores the old mapping information
		ArrayList<MappingCell> oldMappingCells = new ArrayList<MappingCell>();
		
		// Get old mapping information
		if(mapping.getId()!=null)
		{
			mappingID = mapping.getId();
			oldMappingCells = manager.getProjects().getMappingCells(mappingID);
		}
		
		// Build reference tables for the old and new mapping cells
		MappingCellHashMap oldMappingCellRefs = new MappingCellHashMap(oldMappingCells);
		MappingCellHashMap mappingCellRefs = new MappingCellHashMap(mappingCells);

		// Align mapping cell IDs with old mapping cell IDs
		for(MappingCell mappingCell : mappingCells)
		{
			MappingCell oldMappingCell = oldMappingCellRefs.getMappingCell(mappingCell);
			mappingCell.setId(oldMappingCell==null ? null : oldMappingCell.getId());
		}
			
		// Keeps track of success
		boolean success = true;
		
		// Update the mapping
		try
		{
			// Adds the mapping if needed
			if(mappingID==null)
			{
				mappingID = manager.getProjects().addMapping(mapping);
				mapping.setId(mappingID);
			}
			
			// Removes all mapping cells that are no longer used
			for(MappingCell oldMappingCell : oldMappingCells)
				if(!mappingCellRefs.contains(oldMappingCell))
					if(!manager.getProjects().deleteMappingCell(oldMappingCell.getId())) success = false;
					
			// Adds or updates all new mapping cells
			for(MappingCell mappingCell : mappingCells)
			{
				mappingCell.setMappingId(mapping.getId());

				// Handles new mapping cells
				if(mappingCell.getId()==null)
				{
					mappingCell.setId(manager.getProjects().addMappingCell(mappingCell));
					if(mappingCell.getId()==null) success = false;
				}
				
				// Handles updated mapping cells
				else if(!manager.getProjects().updateMappingCell(mappingCell)) success = false;
			}
			
			// If not successful, throw exception
			if(success==false) throw new Exception();
		}
		
		// Reverts back to the old mapping if a failure occurred
		catch(Exception e)
		{
			mappingID = null;
			
			// Resets a newly created mapping
			if(mapping.getId()==null && mapping.getId()!=null)
			{
				manager.getProjects().deleteProject(mapping.getId());
				mapping.setId(null);
			}
			
			// Resets to the original mapping
			else
			{
				for(MappingCell mappingCell : manager.getProjects().getMappingCells(mapping.getId()))
				{
					MappingCell oldMappingCell = oldMappingCellRefs.getMappingCell(mappingCell);
					if(oldMappingCell==null) manager.getProjects().deleteMappingCell(mappingCell.getId());
					else manager.getProjects().updateMappingCell(oldMappingCell);
				}
			}
		}
		
		return mappingID==null ? 0 : mappingID;
	}
}