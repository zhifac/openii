package org.mitre.harmony.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.harmony.model.preferences.Preferences;
import org.mitre.harmony.model.preferences.PreferencesListener;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 * Class for managing the current Harmony mapping cells
 * @author CWOLF
 */
public class MappingCellManager implements MappingListener, PreferencesListener
{
	/** Minimum mapping cell score */
	public static final double MIN_CONFIDENCE = 0;

	/** Maximum mapping cell score */
	public static final double MAX_CONFIDENCE = 1.0;
	
	/** Stores the mapping cells */
	protected static HashMap<Integer,MappingCell> mappingCells = new HashMap<Integer,MappingCell>();

	/** Stores the mapping cells by element reference */
	protected static HashMap<Integer,HashMap<Integer,Integer>> mappingCellsByElement = new HashMap<Integer,HashMap<Integer,Integer>>();
	
	/** Stores the last mapping cell id handed out */
	static private Integer maxID = 0;
	
	/** Constructor used to monitor changes that might affect the mapping cells */
	protected MappingCellManager()
	{
		MappingManager.addListener(this);
		Preferences.addListener(this);
	}
	static { new MappingCellManager(); }
	
	/** Returns a list of all mapping cells */
	static public ArrayList<MappingCell> getMappingCells()
		{ return new ArrayList<MappingCell>(mappingCells.values()); }
	
	static public boolean isMappingCellFinished(Integer linkID)
		{ return false; }
	
	static public String getMappingCellName(Integer mappingCellID)
		{ return "Name"; }
	
	static public String getMappingCellCreationDate(Integer linkID)
		{ return "Creation Date"; }
	
	static public String getMappingCellAuthor(Integer linkID)
		{ return "Author"; }
	
	static public String getMappingCellTransform(Integer linkID)
		{ return "Transform"; }
	
	static public String getMappingCellNotes(Integer linkID)
		{ return "Notes"; }
	
	static public void setMappingCellName(Integer linkID, String name) {}
	static public void setMappingCellCreationDate(Integer linkID, String name) {}
	static public void setMappingCellAuthor(Integer linkID, String name) {}
	static public void setMappingCellTransform(Integer linkID, String name) {}
	static public void setMappingCellNotes(Integer linkID, String name) {}
	static public void setMappingCellConfidence(Integer linkID, double confidence) {}

	/** Returns the requested mapping cell ID */
	static public Integer getMappingCellID(Integer element1ID, Integer element2ID)
	{
		HashMap<Integer,Integer> elementMappingCells = mappingCellsByElement.get(element1ID);
		return elementMappingCells==null ? null : elementMappingCells.get(element2ID);
	}

	/** Returns a list of all mapping cells linked to the specified element */
	static public ArrayList<Integer> getMappingCellsByElement(Integer elementID)
	{
		if(mappingCellsByElement.get(elementID)==null) return new ArrayList<Integer>();
		return new ArrayList<Integer>(mappingCellsByElement.get(elementID).values());
	}
	
	/** Returns the requested mapping cell */
	static public MappingCell getMappingCell(Integer mappingCellID)
	{
		MappingCell mappingCell = mappingCells.get(mappingCellID);
		return new MappingCell(mappingCell.getId(),mappingCell.getMappingId(),mappingCell.getElement1(),mappingCell.getElement2(),mappingCell.getScore(),mappingCell.getScorer(),mappingCell.getValidated());
	}
	
	/** Handles the creation of a mapping cell */
	static public Integer createMappingCell(Integer node1ID, Integer node2ID)
	{
		// Create the mapping cell
		Integer mappingCellID = ++maxID;
		MappingCell mappingCell = new MappingCell(maxID,0,node1ID,node2ID,0.0,null,false);
		mappingCells.put(mappingCellID,mappingCell);
			
		// Add to the reference table
		if(mappingCellsByElement.get(node1ID)==null) mappingCellsByElement.put(node1ID,new HashMap<Integer,Integer>());
		if(mappingCellsByElement.get(node2ID)==null) mappingCellsByElement.put(node2ID,new HashMap<Integer,Integer>());
		mappingCellsByElement.get(node1ID).put(node2ID,mappingCellID);
		mappingCellsByElement.get(node2ID).put(node1ID,mappingCellID);

		// Inform listeners of added mapping cell
		for(MappingCellListener listener : listeners.get()) listener.mappingCellAdded(mappingCell);

		// Returns the mapping cell ID
		return mappingCellID;
	}
	
	/** Handles the modifying of a mapping cell to the current mapping */
	static public void modifyMappingCell(Integer mappingCellID, Double score, String scorer, boolean validated)
	{
		MappingCell oldMappingCell = mappingCells.get(mappingCellID);
		MappingCell newMappingCell = new MappingCell(oldMappingCell.getId(),oldMappingCell.getMappingId(),oldMappingCell.getElement1(),oldMappingCell.getElement2(),score,scorer,validated);
		mappingCells.put(mappingCellID,newMappingCell);
		for(MappingCellListener listener : listeners.get()) listener.mappingCellModified(oldMappingCell,newMappingCell);
	}
	
	/** Handles the deletion of a mapping cell */
	static public void deleteMappingCell(Integer mappingCellID)
	{
		MappingCell mappingCell = mappingCells.get(mappingCellID);
		mappingCellsByElement.get(mappingCell.getElement1()).remove(mappingCell.getElement2());
		mappingCellsByElement.get(mappingCell.getElement2()).remove(mappingCell.getElement1());
		mappingCells.remove(mappingCellID);
		for(MappingCellListener listener : listeners.get()) listener.mappingCellRemoved(mappingCell);		
	}
	
	/** Handles the deletion of all mapping cells */
	static public void deleteMappingCells()
	{
		for(Integer mappingCellID : new ArrayList<Integer>(mappingCells.keySet()))
			deleteMappingCell(mappingCellID);
	}

	/** Handles the removal of a schema from the mapping */
	public void schemaRemoved(Integer schemaID)
	{
		HashSet<Integer> validElements = new HashSet<Integer>();
		for(Integer currSchemaID : MappingManager.getSchemas())
			for(SchemaElement element : SchemaManager.getGraph(currSchemaID).getGraphElements())
				validElements.add(element.getId());
		for(MappingCell cell : getMappingCells())
			if(!validElements.contains(cell.getElement1()) || !validElements.contains(cell.getElement2()))
				deleteMappingCell(cell.getId());
	}
	
	/** Remove all non-user defined mapping cells when the model is changed */
	public void schemaGraphModelChanged(Integer schemaID)
	{
		// Get the list of schema elements for the modified schema
		HierarchicalGraph graph = SchemaManager.getGraph(schemaID);
		
		// Delete all non-validated mapping cells associated with the modified schema
		for(Integer mappingCellID : new ArrayList<Integer>(mappingCells.keySet()))
		{
			MappingCell mappingCell = mappingCells.get(mappingCellID);
			if(graph.containsElement(mappingCell.getElement1()) || graph.containsElement(mappingCell.getElement2()))
				if(!mappingCell.getValidated())
					deleteMappingCell(mappingCellID);
		}
	}
	
	// Unused event listener
	public void schemaAdded(Integer schemaID) {}
	public void mappingModified() {}
	public void displayedViewChanged() {}
	public void showSchemaTypesChanged() {}
	public void elementsMarkedAsFinished(Integer schemaID, HashSet<Integer> elementIDs) {}
	public void elementsMarkedAsUnfinished(Integer schemaID, HashSet<Integer> elementIDs) {}
	
	//----------------------------------------------------------------
	// Purpose: Allows classes to listen to the Harmony mapping cells
	//----------------------------------------------------------------
	static private ListenerGroup<MappingCellListener> listeners = new ListenerGroup<MappingCellListener>();
	static public void addListener(MappingCellListener listener) { listeners.add(listener); }
}