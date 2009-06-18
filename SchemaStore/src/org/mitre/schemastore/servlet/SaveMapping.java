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
	/** Private class for storing a mapping cell reference table */
	static private class MappingCellRefTable
	{
		/** Stores a hash map of all mapping cells */
		private HashMap<String,MappingCell> refTable = new HashMap<String,MappingCell>();
		
		/** Generates a reference table key */
		private String getKey(Integer element1, Integer element2)
		{
			if(element1 < element2) return element1 + " " + element2;
			return element2 + " " + element1;
		}
			
		/** Constructs the mapping cell reference table */
		private MappingCellRefTable(ArrayList<MappingCell> mappingCells)
		{
			for(MappingCell mappingCell : mappingCells)
				refTable.put(getKey(mappingCell.getElement1(),mappingCell.getElement2()),mappingCell);
		}

		/** Retrieves the mapping cell for the specified elements */
		private MappingCell getMappingCell(Integer element1, Integer element2)
			{ return refTable.get(getKey(element1,element2)); }
	}
	
	/** Saves the specified mapping into the web services */
	static Integer saveMapping(DataManager manager, Mapping mapping, ArrayList<MappingCell> mappingCells) throws RemoteException
	{
		Integer mappingID = null;
		
		// Stores the old mapping information
		Mapping oldMapping = null;
		ArrayList<MappingCell> oldMappingCells = new ArrayList<MappingCell>();
		
		// Get old mapping information
		if(mapping.getId()!=null)
		{
			mappingID = mapping.getId();
			oldMapping = manager.getMappings().getMapping(mappingID);
			oldMappingCells = manager.getMappings().getMappingCells(mappingID);
		}
		
		// Build reference tables for the old and new mapping cells
		MappingCellRefTable oldMappingCellRefs = new MappingCellRefTable(oldMappingCells);
		MappingCellRefTable mappingCellRefs = new MappingCellRefTable(mappingCells);

		// Align mapping cell IDs with old mapping cell IDs
		for(MappingCell mappingCell : mappingCells)
		{
			MappingCell oldMappingCell = oldMappingCellRefs.getMappingCell(mappingCell.getElement1(), mappingCell.getElement2());
			if(oldMappingCell!=null)
				mappingCell.setId(oldMappingCell.getId());
			else mappingCell.setId(null);
		}

		// Keeps track of success
		boolean success = true;
		
		// Update the mapping
		try
		{
			// Adds or updates the mapping
			if(mappingID==null)
			{
				mappingID = manager.getMappings().addMapping(mapping);
				mapping.setId(mappingID);
			}
			else if(!manager.getMappings().updateMapping(mapping)) success = false;
			
			// Removes all mapping cells that are no longer used
			for(MappingCell oldMappingCell : oldMappingCells)
				if(mappingCellRefs.getMappingCell(oldMappingCell.getElement1(), oldMappingCell.getElement2())==null)
					if(!manager.getMappings().deleteMappingCell(oldMappingCell.getId())) success = false;
					
			// Adds or updates all new mapping cells
			for(MappingCell mappingCell : mappingCells)
			{
				mappingCell.setMappingId(mapping.getId());

				// Handles new mapping cells
				if(mappingCell.getId()==null)
				{
					mappingCell.setId(manager.getMappings().addMappingCell(mappingCell));
					if(mappingCell.getId()==null) success = false;
				}
				
				// Handles updated mapping cells
				else if(!manager.getMappings().updateMappingCell(mappingCell)) success = false;
			}
			
			// If not successful, throw exception
			if(success==false) throw new Exception();
		}
		
		// Reverts back to the old mapping if a failure occured
		catch(Exception e)
		{
			mappingID = null;
			
			// Resets a newly created mapping
			if(oldMapping==null && mapping.getId()!=null)
			{
				manager.getMappings().deleteMapping(mapping.getId());
				mapping.setId(null);
			}
			
			// Resets to the original mapping
			else
			{
				manager.getMappings().updateMapping(oldMapping);
				for(MappingCell mappingCell : manager.getMappings().getMappingCells(oldMapping.getId()))
				{
					MappingCell oldMappingCell = oldMappingCellRefs.getMappingCell(mappingCell.getElement1(), mappingCell.getElement2());
					if(oldMappingCell!=null) manager.getMappings().deleteMappingCell(mappingCell.getId());
					else manager.getMappings().updateMappingCell(oldMappingCell);
				}
			}
		}
		
		return mappingID==null ? 0 : mappingID;
	}
}
