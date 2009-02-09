// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;

/**
 * Class used to manage the current project
 * @author CWOLF
 */
public class MappingManager
{		
	/** Stores the current mapping */
	static private Mapping mapping = new Mapping();	
	
	/** Returns the list of valid schemas */
	static private ArrayList<Integer> validateSchemaIDs(ArrayList<Integer> schemaIDs)
	{
		ArrayList<Integer> availSchemaIDs = SchemaManager.getSchemaIDs();
		for(Integer schemaID : new ArrayList<Integer>(schemaIDs))
			if(!availSchemaIDs.contains(schemaID))
				schemaIDs.remove(schemaID);
		return schemaIDs;
	}
	
	/** Returns a list of all available mappings */
	static public ArrayList<Mapping> getAvailableMappings()
	{
		ArrayList<Mapping> mappingList = new ArrayList<Mapping>();
		try {
			mappingList = SchemaStoreManager.getMappingList();
		} catch(Exception e) {
			System.err.println("Error getting Mapping List from SchemaStoreConnection");
			e.printStackTrace();
		}
		return mappingList;
	}
	
	/** Gets the mapping info */
	static public Mapping getMapping()
		{ return mapping.copy(); }

	/** Gets the mapping schemas */
	static public ArrayList<Integer> getSchemas()
	{
		if(mapping.getSchemas()==null) return new ArrayList<Integer>();
		return new ArrayList<Integer>(Arrays.asList(mapping.getSchemas()));
	}
	
	/** Sets the mapping */
	static void setMapping(Mapping mappingIn)
	{
		if(mappingIn.getSchemas()!=null)
		{
			ArrayList<Integer> validSchemas = validateSchemaIDs(new ArrayList<Integer>(Arrays.asList(mappingIn.getSchemas())));
			mappingIn.setSchemas(validSchemas.toArray(new Integer[0]));
		}
		mapping = mappingIn;
	}
	
	/** Sets the mapping info */
	static public void setMappingInfo(String name, String author, String description)
	{
		// Only save information if changes made
		if(!name.equals(mapping.getName()) || !author.equals(mapping.getAuthor()) || !description.equals(mapping.getDescription()))
		{
			// Sets the mapping
			mapping.setName(name);
			mapping.setAuthor(author);
			mapping.setDescription(description);
			
			// Indicates that the mapping has been modified
			for(MappingListener listener : listeners)
				listener.mappingModified();
		}
	}

	/** Sets the mapping schemas */
	static public void setSchemas(ArrayList<Integer> schemaIDs)
	{
		// Get the list of old and new schemas
		ArrayList<Integer> oldSchemaIDs = getSchemas();
		ArrayList<Integer> newSchemaIDs = validateSchemaIDs(schemaIDs);
		Collections.sort(oldSchemaIDs);
		Collections.sort(newSchemaIDs);

		// Only proceed if schemas have been modified
		if(!oldSchemaIDs.equals(newSchemaIDs))
		{
			// Set the mapping schemas
			mapping.setSchemas(newSchemaIDs.toArray(new Integer[0]));
			
			// Inform listeners of any schemas that were selected
			for(Integer newSelSchemaID : newSchemaIDs)
				if(!oldSchemaIDs.contains(newSelSchemaID))
					for(MappingListener listener : listeners)
						listener.schemaAdded(newSelSchemaID);
					
			// Inform listeners of any schemas that were unselected
			for(Integer selSchemaID : oldSchemaIDs)
				if(!newSchemaIDs.contains(selSchemaID))
					for(MappingListener listener : listeners)
						listener.schemaRemoved(selSchemaID);
			
			// Inform listeners that the mapping has been modified
			for(MappingListener listener : listeners)
				listener.mappingModified();
		}
	}
	
	/** Creates a new mapping */
	static public void newMapping()
	{
		MappingCellManager.deleteMappingCells();
		setSchemas(new ArrayList<Integer>());
		mapping = new Mapping();
		ProjectManager.setSavedToRepository(true);
	}
	
	/** Loads the specified mapping */
	static public boolean loadMapping(Integer mappingID)
	{
		boolean success = false;
		
		// Gets new mapping information
		Mapping newMapping = null;
		ArrayList<MappingCell> newMappingCells = null;
		try {
			newMapping = SchemaStoreManager.getMapping(mappingID);
			newMappingCells = SchemaStoreManager.getMappingCells(mappingID);
			success = true;
		}
		catch(Exception e) { System.out.println("(E) MappingManager:loadMapping - " + e.getMessage()); }
	
		// Load new mapping if collection of information was successful
		if(success)
		{
			MappingCellManager.deleteMappingCells();
			mapping = newMapping;
			setSchemas(new ArrayList<Integer>(Arrays.asList(mapping.getSchemas())));
			for(MappingCell mappingCell : newMappingCells)
			{
				Integer mappingCellID = MappingCellManager.createMappingCell(mappingCell.getElement1(), mappingCell.getElement2());
				MappingCellManager.modifyMappingCell(mappingCellID, mappingCell.getScore(), mappingCell.getScorer(), mappingCell.getValidated());
			}
			for(MappingListener listener : listeners)
				listener.mappingModified();
		}

		// Returns if successful
		if(success) ProjectManager.setSavedToRepository(true);
		return success;
	}
	
	/** Saves the current mapping */
	static public boolean saveMapping()
		{ return saveMapping(); }
	
	/** Saves the specified mapping */
	static public boolean saveMapping(Mapping mapping)
	{
		boolean success = false;
	
		// Attempts to save the mapping
		try {
			Integer mappingID = SchemaStoreManager.saveMapping(mapping, MappingCellManager.getMappingCells());
			if(mappingID!=null) { mapping.setId(mappingID); setMapping(mapping); success = true; }
		}
		catch(Exception e) { System.out.println("(E) MappingManager.saveMapping - " + e.getMessage()); }

		// Indicates that the mapping has been modified
		if(success)
		{
			for(MappingListener listener : listeners) listener.mappingModified();
			ProjectManager.setSavedToRepository(true);
		}
		return success;
	}
	
	/** Deletes the specified mapping */
	static public boolean deleteMapping(Integer mappingID)
	{
		// Delete the mapping 
		try {
			SchemaStoreManager.deleteMapping(mappingID);
			return true;
		}
		catch(Exception e) { System.out.println("(E) MappingManager:deleteMapping - " + e.getMessage()); }
		return false;
	}

	//-----------------------------------------------------------
	// Purpose: Allows classes to listen to the Harmony mappings
	//-----------------------------------------------------------
	static private Vector<MappingListener> listeners = new Vector<MappingListener>();
	static public void addListener(MappingListener obj) { listeners.add(obj); }
	static public void removeListener(MappingListener obj) { listeners.remove(obj); }
}
