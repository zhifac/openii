package org.mitre.harmony.model.mapping;

import java.util.ArrayList;
import java.util.Calendar;
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

	/** Stores the mapping cells by element reference */
	protected HashMap<Integer,HashMap<Integer,Integer>> mappingCellsByElement = new HashMap<Integer,HashMap<Integer,Integer>>();
	
	/** Stores the last mapping cell id handed out */
	private Integer maxID = 0;
	
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
	
	public boolean isMappingCellFinished(Integer linkID)
		{ return false; }

	/** Returns the requested mapping cell ID */
	public Integer getMappingCellID(Integer element1ID, Integer element2ID)
	{
		HashMap<Integer,Integer> elementMappingCells = mappingCellsByElement.get(element1ID);
		return elementMappingCells==null ? null : elementMappingCells.get(element2ID);
	}

	/** Returns a list of all mapping cells linked to the specified element */
	public ArrayList<Integer> getMappingCellsByElement(Integer elementID)
	{
		if(mappingCellsByElement.get(elementID)==null) return new ArrayList<Integer>();
		return new ArrayList<Integer>(mappingCellsByElement.get(elementID).values());
	}
	
	/** Returns the requested mapping cell */
	public MappingCell getMappingCell(Integer mappingCellID)
		{ return mappingCellHash.get(mappingCellID).copy(); }
	
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
				Integer element1 = mappingCell.getElement1();
				Integer element2 = mappingCell.getElement2();
				mappingCell.setId(mappingCellID);
				mappingCell.setModificationDate(Calendar.getInstance().getTime());
	
				// Store the mapping cell info
				mappingCellHash.put(mappingCellID, mappingCell);
				if(mappingCellsByElement.get(element1)==null) mappingCellsByElement.put(element1,new HashMap<Integer,Integer>());
				if(mappingCellsByElement.get(element2)==null) mappingCellsByElement.put(element2,new HashMap<Integer,Integer>());
				mappingCellsByElement.get(element1).put(element2,mappingCellID);
				mappingCellsByElement.get(element2).put(element1,mappingCellID);
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
			mappingCellsByElement.get(mappingCell.getElement1()).remove(mappingCell.getElement2());
			mappingCellsByElement.get(mappingCell.getElement2()).remove(mappingCell.getElement1());
			mappingCells.remove(mappingCell.getId());
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
		HashSet<Integer> elementIDs = getModel().getMappingManager().getSchemaElementIDs(MappingSchema.LEFT);
		elementIDs.addAll(getModel().getMappingManager().getSchemaElementIDs(MappingSchema.RIGHT));

		// Identify all mapping cells for which elements are no longer available
		ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
		for(MappingCell mappingCell : getMappingCells())
			if(!elementIDs.contains(mappingCell.getElement1()) || !elementIDs.contains(mappingCell.getElement2()))
				mappingCells.add(mappingCell);
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