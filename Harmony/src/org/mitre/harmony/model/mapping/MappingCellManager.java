package org.mitre.harmony.model.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.mitre.harmony.model.AbstractManager;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.preferences.PreferencesListener;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.mapfunctions.IdentityFunction;

/**
 * Class for managing the current Harmony mapping cells
 * @author CWOLF
 */
public class MappingCellManager extends AbstractManager<MappingCellListener> implements MappingListener, PreferencesListener
{
	/** Minimum mapping cell score */
	public static final double MIN_CONFIDENCE = 0;

	/** Maximum mapping cell score */
	public static final double MAX_CONFIDENCE = 1.0;
	
	/** Stores the mapping cells */
	protected HashMap<Integer,MappingCell> mappingCellHash = new HashMap<Integer,MappingCell>();

	/** Stores the mapping cells by key */
	protected HashMap<String,Integer> mappingCellsByKey = new HashMap<String,Integer>();
	
	/** Stores the mapping cells by element reference */
	protected HashMap<Integer,ArrayList<Integer>> mappingCellsByElement = new HashMap<Integer,ArrayList<Integer>>();
	
	/** Stores the last mapping cell id handed out */
	private Integer maxID = 0;
	
	/** Returns the key associated with the specified element IDs */
	private String getKey(Integer[] inputIDs, Integer outputID)
	{
		// Sort the list of elements
		List<Integer> sortedInputIDs = Arrays.asList(inputIDs);
		Collections.sort(sortedInputIDs);

		// Retrieve the mapping cell using the key
		StringBuffer key = new StringBuffer();
		for(Integer sortedInputID : sortedInputIDs)
			key.append(sortedInputID + " ");
		key.append(outputID);
		return key.toString();
	}
	
	/** Retrieve the mapping cells for the specified mapping cell IDs */
	private List<MappingCell> getMappingCellsByID(List<Integer> mappingCellIDs)
	{
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		for(Integer mappingCellID : mappingCellIDs)
			mappingCells.add(mappingCellHash.get(mappingCellID));
		return mappingCells;
	}
	
	/** Constructor for the Mapping Cell manager */
	public MappingCellManager(HarmonyModel harmonyModel)
		{ super(harmonyModel); }
	
	/** Returns a list of all mapping cells */
	public ArrayList<MappingCell> getMappingCells()
		{ return new ArrayList<MappingCell>(mappingCellHash.values()); }

	/** Returns the requested mapping cell */
	public MappingCell getMappingCell(Integer mappingCellID)
		{ return mappingCellHash.get(mappingCellID).copy(); }
	
	/** Returns the requested mapping cell ID */
	public Integer getMappingCellID(Integer inputID, Integer outputID)
		{ return mappingCellsByKey.get(getKey(new Integer[] {inputID},outputID)); }

	/** Returns the requested mapping cell ID */
	public Integer getMappingCellID(Integer[] inputIDs, Integer outputID)
		{ return mappingCellsByKey.get(getKey(inputIDs,outputID)); }	
	
	/** Returns a list of all mapping cells linked to the specified element */
	public ArrayList<Integer> getMappingCellsByElement(Integer elementID)
	{
		ArrayList<Integer> mappingCellIDs = mappingCellsByElement.get(elementID);
		if(mappingCellIDs==null) mappingCellIDs = new ArrayList<Integer>();
		return mappingCellIDs;
	}
	
	/** Sets the mapping cell */
	public void setMappingCells(List<MappingCell> mappingCells)
	{
		// Sort out new and already created mapping cells
		ArrayList<MappingCell> newMappingCells = new ArrayList<MappingCell>();
		ArrayList<MappingCell> existingMappingCells = new ArrayList<MappingCell>();
		for(MappingCell mappingCell : mappingCells)
		{
			if(mappingCell.getId()==null) newMappingCells.add(mappingCell);
			else existingMappingCells.add(mappingCell);
		}
		
		// Handles the addition of new mapping cells
		if(newMappingCells.size()>0)
		{
			for(MappingCell mappingCell : newMappingCells)
			{
				// Get information about the mapping cell to be stored
				Integer mappingCellID = ++maxID;
				Integer inputIDs[] = mappingCell.getInput();
				Integer outputID = mappingCell.getOutput();
				mappingCell.setId(mappingCellID);
				mappingCell.setModificationDate(Calendar.getInstance().getTime());
	
				// Store the mapping cell info in the hash and by key
				mappingCellHash.put(mappingCellID, mappingCell);
				mappingCellsByKey.put(getKey(inputIDs,outputID),mappingCellID);
				
				// Stores the mapping cell info be element reference
				ArrayList<Integer> elementIDs = new ArrayList<Integer>(Arrays.asList(inputIDs));
				elementIDs.add(outputID);
				for(Integer elementID : elementIDs)
				{
					ArrayList<Integer> mappingCellIDs = mappingCellsByElement.get(elementID);
					if(mappingCellIDs==null) mappingCellsByElement.put(elementID, mappingCellIDs = new ArrayList<Integer>());
					mappingCellIDs.add(mappingCellID);
				}
			}
			for(MappingCellListener listener : getListeners()) listener.mappingCellsAdded(newMappingCells);
		}
		
		// Handles the modification of existing mapping cells
		if(existingMappingCells.size()>0)
		{
			ArrayList<MappingCell> oldMappingCells = new ArrayList<MappingCell>();
			for(MappingCell mappingCell : existingMappingCells)
			{
				// Update the mapping cell
				MappingCell oldMappingCell = mappingCellHash.get(mappingCell.getId());
				oldMappingCells.add(oldMappingCell);
				mappingCellHash.put(mappingCell.getId(), mappingCell);
				
				// Set the modification date if the score has been modified
				if(mappingCell.getScore().equals(oldMappingCell.getScore()))
					mappingCell.setModificationDate(Calendar.getInstance().getTime());
				else mappingCell.setModificationDate(oldMappingCell.getModificationDate());
			}
			for(MappingCellListener listener : getListeners()) listener.mappingCellsModified(oldMappingCells,existingMappingCells);
		}
	}
	
	/** Validates the specified mapping cells */
	public void validateMappingCells(List<MappingCell> mappingCells)
	{
		ArrayList<MappingCell> validatedMappingCells = new ArrayList<MappingCell>();
		for(MappingCell mappingCell : mappingCells)
		{
			// Retrieve the various mapping cell fields
			Integer id = mappingCell.getId();
			Integer mappingID = mappingCell.getId();
			Integer inputIDs[] = mappingCell.getInput();
			Integer outputID = mappingCell.getOutput();
			String author = System.getProperty("user.name");
			Date date = Calendar.getInstance().getTime();
			String function = IdentityFunction.class.getCanonicalName();			
			String notes = mappingCell.getNotes();
	
			// Generate the validated mapping cell
			MappingCell validatedMappingCell = MappingCell.createValidatedMappingCell(id, mappingID, inputIDs, outputID, author, date, function, notes);
			validatedMappingCells.add(validatedMappingCell);
			mappingCellHash.put(validatedMappingCell.getId(), validatedMappingCell);
		}
		
		// Inform listeners that a mapping cell has been modified
		for(MappingCellListener listener : getListeners()) listener.mappingCellsModified(mappingCells,validatedMappingCells);
	}
	
	/** Validates the specified mapping cells by ID */
	public void validateMappingCellsByID(List<Integer> mappingCellIDs)
		{ validateMappingCells(getMappingCellsByID(mappingCellIDs)); }
	
	/** Deletes the specified mapping cells */
	public void deleteMappingCells(List<MappingCell> mappingCells)
	{
		for(MappingCell mappingCell : mappingCells)
		{
			// Remove the mapping cell from the hash and key caches
			mappingCellHash.remove(mappingCell.getId());
			mappingCellsByKey.remove(getKey(mappingCell.getInput(),mappingCell.getOutput()));

			// Remove the mapping cell from the element reference
			ArrayList<Integer> elementIDs = new ArrayList<Integer>(Arrays.asList(mappingCell.getInput()));
			elementIDs.add(mappingCell.getOutput());
			for(Integer elementID : elementIDs)
			{
				ArrayList<Integer> mappingCellIDs = mappingCellsByElement.get(elementID);
				if(mappingCellIDs.size()>1) mappingCellIDs.remove(mappingCell.getId());
				else mappingCellsByElement.remove(elementID);
			}
		}
		for(MappingCellListener listener : getListeners()) listener.mappingCellsRemoved(mappingCells);		
	}
	
	/** Deletes the specified mapping cells by ID */
	public void deleteMappingCellsByID(List<Integer> mappingCellIDs)
		{ deleteMappingCells(getMappingCellsByID(mappingCellIDs)); }
	
	/** Handles the deletion of all mapping cells */
	public void deleteAllMappingCells()
		{ deleteMappingCells(new ArrayList<MappingCell>(mappingCellHash.values())); }

	/** Handles the removal of a schema from the mapping */
	public void schemaRemoved(Integer schemaID)
	{	
		// Identify all elements associated with remaining schemas
		HashSet<Integer> leftIDs = getModel().getMappingManager().getSchemaElementIDs(MappingSchema.LEFT);
		HashSet<Integer> rightIDs = getModel().getMappingManager().getSchemaElementIDs(MappingSchema.RIGHT);

		// Identify all mapping cells for which elements are no longer available
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		for(MappingCell mappingCell : getMappingCells())
		{
			boolean exists = true;
			for(Integer inputID : mappingCell.getInput())
				exists &= leftIDs.contains(inputID);
			exists &= rightIDs.contains(mappingCell.getOutput());
			if(!exists) mappingCells.add(mappingCell);
		}				
		deleteMappingCells(mappingCells);
	}
	
	// Unused event listener
	public void schemaAdded(Integer schemaID) {}
	public void schemaModified(Integer schemaID) {}
	public void mappingModified() {}
	public void displayedViewChanged() {}
	public void showSchemaTypesChanged() {}
	public void elementsMarkedAsFinished(Integer schemaID, HashSet<Integer> elementIDs) {}
	public void elementsMarkedAsUnfinished(Integer schemaID, HashSet<Integer> elementIDs) {}
}