// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.mitre.harmony.model.AbstractManager;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.SchemaStoreManager;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;

/**
 * Class used to manage the current project
 * @author CWOLF
 */
public class MappingManager extends AbstractManager<MappingListener> implements MappingCellListener
{	
	/** Stores the current mapping */
	protected Mapping mapping = new Mapping();	
	
	/** Indicates if the mapping has been modified */
	private boolean modified = false;
	
	/** Returns the list of valid schemas */
	private ArrayList<Integer> validateSchemaIDs(ArrayList<Integer> schemaIDs)
	{
		ArrayList<Integer> availSchemaIDs = getModel().getSchemaManager().getSchemaIDs();
		for(Integer schemaID : new ArrayList<Integer>(schemaIDs))
			if(!availSchemaIDs.contains(schemaID))
				schemaIDs.remove(schemaID);
		return schemaIDs;
	}
	
	/** Constructs the mapping manager */
	public MappingManager(HarmonyModel harmonyModel)
		{ super(harmonyModel); }
	
	/** Indicates if the mapping has been modified */
	public boolean isModified()
		{ return modified; }
	
	/** Sets the mapping as modified */
	public void setModified(boolean modified)
	{
		this.modified = modified;
		for(MappingListener listener : getListeners())
			listener.mappingModified();
	}
	
	/** Gets the mapping info */
	public Mapping getMapping()
		{ return mapping.copy(); }

	/** Gets the mapping schemas */
	public ArrayList<Integer> getSchemas()
	{
		if(mapping.getSchemas()==null) return new ArrayList<Integer>();
		return new ArrayList<Integer>(Arrays.asList(mapping.getSchemas()));
	}
	
	/** Sets the mapping */
	public void setMapping(Mapping mappingIn)
	{
		if(mappingIn.getSchemas()!=null)
		{
			ArrayList<Integer> validSchemas = validateSchemaIDs(new ArrayList<Integer>(Arrays.asList(mappingIn.getSchemas())));
			mappingIn.setSchemas(validSchemas.toArray(new Integer[0]));
		}
		mapping = mappingIn;
	}
	
	/** Sets the mapping info */
	public void setMappingInfo(String name, String author, String description)
	{
		// Only save information if changes made
		if(!name.equals(mapping.getName()) || !author.equals(mapping.getAuthor()) || !description.equals(mapping.getDescription()))
		{
			// Sets the mapping
			mapping.setName(name);
			mapping.setAuthor(author);
			mapping.setDescription(description);
			
			// Indicates that the mapping has been modified
			for(MappingListener listener : getListeners())
				listener.mappingModified();
		}
	}

	/** Sets the mapping schemas */
	public void setSchemas(ArrayList<Integer> schemaIDs)
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
					for(MappingListener listener : getListeners())
						listener.schemaAdded(newSelSchemaID);
					
			// Inform listeners of any schemas that were unselected
			for(Integer selSchemaID : oldSchemaIDs)
				if(!newSchemaIDs.contains(selSchemaID))
					for(MappingListener listener : getListeners())
						listener.schemaRemoved(selSchemaID);
			
			// Set the mapping as being modified
			setModified(true);
		}
	}
	
	/** Creates a new mapping */
	public void newMapping()
	{
		getModel().getMappingCellManager().deleteMappingCells();
		setSchemas(new ArrayList<Integer>());
		mapping = new Mapping();
		mapping.setAuthor(System.getProperty("user.name"));
		setModified(false);
	}
	
	/** Loads the specified mapping */
	public boolean loadMapping(Integer mappingID)
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
			getModel().getMappingCellManager().deleteMappingCells();
			mapping = newMapping;
			setSchemas(new ArrayList<Integer>(Arrays.asList(mapping.getSchemas())));
			for(MappingCell mappingCell : newMappingCells)
			{
				mappingCell.setId(null);
				getModel().getMappingCellManager().setMappingCell(mappingCell);
			}
			for(MappingListener listener : getListeners())
				listener.mappingModified();
		}

		// Returns if successful
		if(success) setModified(false);
		return success;
	}
	
	/** Saves the current mapping */
	public boolean saveMapping()
		{ return saveMapping(mapping); }
	
	/** Saves the specified mapping */
	public boolean saveMapping(Mapping mapping)
	{
		boolean success = false;
	
		// Attempts to save the mapping
		try {
			Integer mappingID = SchemaStoreManager.saveMapping(mapping, getModel().getMappingCellManager().getMappingCells());
			if(mappingID!=null) { mapping.setId(mappingID); setMapping(mapping); success = true; }
		}
		catch(Exception e) { System.out.println("(E) MappingManager.saveMapping - " + e.getMessage()); }

		// Indicates that the mapping has been modified
		if(success)
		{
			for(MappingListener listener : getListeners()) listener.mappingModified();
			setModified(false);
		}
		return success;
	}
	
	/** Deletes the specified mapping */
	public boolean deleteMapping(Integer mappingID)
	{
		// Delete the mapping 
		try {
			SchemaStoreManager.deleteMapping(mappingID);
			return true;
		}
		catch(Exception e) { System.out.println("(E) MappingManager:deleteMapping - " + e.getMessage()); }
		return false;
	}

	// Mark the mapping as modified whenever a mapping cell is modified
	public void mappingCellAdded(MappingCell mappingCell) { setModified(true); }
	public void mappingCellModified(MappingCell oldMappingCell, MappingCell newMappingCell) { setModified(true); }
	public void mappingCellRemoved(MappingCell mappingCell) { setModified(true); }
}
