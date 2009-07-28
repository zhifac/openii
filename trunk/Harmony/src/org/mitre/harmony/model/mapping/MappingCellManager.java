package org.mitre.harmony.model.mapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.harmony.model.AbstractManager;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.preferences.PreferencesListener;
import org.mitre.schemastore.mapfunctions.NullFunction;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;

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
	protected HashMap<Integer,MappingCell> mappingCells = new HashMap<Integer,MappingCell>();

	/** Stores the mapping cells by element reference */
	protected HashMap<Integer,HashMap<Integer,Integer>> mappingCellsByElement = new HashMap<Integer,HashMap<Integer,Integer>>();
	
	/** Stores the last mapping cell id handed out */
	private Integer maxID = 0;
	
	/** Constructor for the Mapping Cell manager */
	public MappingCellManager(HarmonyModel harmonyModel)
		{ super(harmonyModel); }
	
	/** Returns a list of all mapping cells */
	public ArrayList<MappingCell> getMappingCells()
		{ return new ArrayList<MappingCell>(mappingCells.values()); }
	
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
		{ return mappingCells.get(mappingCellID).copy(); }
	
	/** Sets the mapping cell */
	public void setMappingCell(MappingCell mappingCell)
	{
		// Handles the addition of a new mapping cell
		if(mappingCell.getId()==null)
		{
			// Get information about the mapping cell to be stored
			Integer mappingCellID = ++maxID;
			Integer element1 = mappingCell.getElement1();
			Integer element2 = mappingCell.getElement2();
			mappingCell.setId(mappingCellID);
			mappingCell.setModificationDate(Calendar.getInstance().getTime());

			// Store the mapping cell info
			mappingCells.put(mappingCellID, mappingCell);
			if(mappingCellsByElement.get(element1)==null) mappingCellsByElement.put(element1,new HashMap<Integer,Integer>());
			if(mappingCellsByElement.get(element2)==null) mappingCellsByElement.put(element2,new HashMap<Integer,Integer>());
			mappingCellsByElement.get(element1).put(element2,mappingCellID);
			mappingCellsByElement.get(element2).put(element1,mappingCellID);
			
			// Inform listeners that a mapping cell has been added
			for(MappingCellListener listener : getListeners()) listener.mappingCellAdded(mappingCell);
		}

		// Updates an already stored mapping cell
		else
		{
			// Update the mapping cell
			MappingCell oldMappingCell = mappingCells.get(mappingCell.getId());
			mappingCells.put(mappingCell.getId(), mappingCell);
			
			// Set the modification date if the score has been modified
			if(mappingCell.getScore().equals(oldMappingCell.getScore()))
				mappingCell.setModificationDate(Calendar.getInstance().getTime());
			else mappingCell.setModificationDate(oldMappingCell.getModificationDate());
			
			// Inform listeners that a mapping cell has been modified
			for(MappingCellListener listener : getListeners()) listener.mappingCellModified(oldMappingCell,mappingCell);
		}
	}
	
	/** Validates the mapping cell */
	public void validateMappingCell(Integer mappingCellID)
	{
		MappingCell mappingCell = getMappingCell(mappingCellID);
		
		// Retrieve the various mapping cell fields
		Integer id = mappingCell.getId();
		Integer mappingID = mappingCell.getId();
		Integer inputIDs[] = mappingCell.getInput();
		Integer outputID = mappingCell.getOutput();
		String author = System.getProperty("user.name");
		Date date = Calendar.getInstance().getTime();
		String function = NullFunction.class.toString();
		String notes = mappingCell.getNotes();

		// Generate the validated mapping cell
		MappingCell newMappingCell = MappingCell.createValidatedMappingCell(id, mappingID, inputIDs, outputID, author, date, function, notes);
		mappingCells.put(mappingCell.getId(), newMappingCell);
			
		// Inform listeners that a mapping cell has been modified
		for(MappingCellListener listener : getListeners()) listener.mappingCellModified(mappingCell,newMappingCell);
	}
	
	/** Handles the deletion of a mapping cell */
	public void deleteMappingCell(Integer mappingCellID)
	{
		MappingCell mappingCell = mappingCells.get(mappingCellID);
		mappingCellsByElement.get(mappingCell.getElement1()).remove(mappingCell.getElement2());
		mappingCellsByElement.get(mappingCell.getElement2()).remove(mappingCell.getElement1());
		mappingCells.remove(mappingCellID);
		for(MappingCellListener listener : getListeners()) listener.mappingCellRemoved(mappingCell);		
	}
	
	/** Handles the deletion of all mapping cells */
	public void deleteMappingCells()
	{
		for(Integer mappingCellID : new ArrayList<Integer>(mappingCells.keySet()))
			deleteMappingCell(mappingCellID);
	}

	/** Handles the removal of a schema from the mapping */
	public void schemaRemoved(Integer schemaID)
	{
		HashSet<Integer> elementIDs = getModel().getMappingManager().getSchemaElementIDs(MappingSchema.LEFT);
		elementIDs.addAll(getModel().getMappingManager().getSchemaElementIDs(MappingSchema.RIGHT));
		for(MappingCell cell : getMappingCells())
			if(!elementIDs.contains(cell.getElement1()) || !elementIDs.contains(cell.getElement2()))
				deleteMappingCell(cell.getId());
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