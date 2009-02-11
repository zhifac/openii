// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.selectedInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.MappingCellListener;
import org.mitre.harmony.model.MappingCellManager;
import org.mitre.harmony.model.MappingListener;
import org.mitre.harmony.model.MappingManager;
import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.model.ConfigManager;
import org.mitre.harmony.model.filters.Filters;
import org.mitre.harmony.model.filters.FiltersListener;
import org.mitre.harmony.model.filters.Focus;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Tracks selected info within Harmony
 * @author CWOLF
 */
public class SelectedInfo implements MappingListener, MappingCellListener, FiltersListener
{
	/** Stores the selected left elements */
	static private HashSet<Integer> selectedLeftElements = new HashSet<Integer>();

	/** Stores the selected right elements */
	static private HashSet<Integer> selectedRightElements = new HashSet<Integer>();

	/** Stores the selected links */
	static private HashSet<Integer> selectedLinks = new HashSet<Integer>();
	
	/** Stores selected info listeners */
	static private Vector<SelectedInfoListener> listeners = new Vector<SelectedInfoListener>();

	/** Returns the specified list of selected elements */
	static private HashSet<Integer> getSelectedElements(Integer role)
		{ return role==HarmonyConsts.LEFT ? selectedLeftElements : selectedRightElements; }
	
	/** Returns the valid schemaIDs from the specified list of schemas */
	private ArrayList<Integer> getValidSchemaIDs(ArrayList<Integer> schemaIDs)
	{
		ArrayList<Integer> validSchemaIDs = MappingManager.getSchemas();
		for(Integer schemaID : new ArrayList<Integer>(schemaIDs))
			if(!validSchemaIDs.contains(schemaID))
				schemaIDs.remove(schemaID);
		return schemaIDs;
	}
	
	/** Constructor used to initialize the selected info */
	private SelectedInfo()
	{
		// Filter out selected schemas which don't exist in the repository
		ArrayList<Integer> leftSchemas = getValidSchemaIDs(getSchemas(HarmonyConsts.LEFT));
		ArrayList<Integer> rightSchemas = getValidSchemaIDs(getSchemas(HarmonyConsts.RIGHT));
		setSelectedSchemas(leftSchemas, rightSchemas);
		
		// Add listeners to trigger changes to the selected info
		MappingManager.addListener(this);
		MappingCellManager.addListener(this);
		Filters.addListener(this);
	}
	static { new SelectedInfo(); }
	
	/** Returns the schemas displayed on the left side of Harmony */
	static public ArrayList<Integer> getSchemas(Integer role)
		{ return ConfigManager.getIntegerArray("selectedInfo." + (role.equals(HarmonyConsts.LEFT) ? "left" : "right") + "Schemas"); }

	/** Returns all elements displayed on the left side of Harmony */
	static public HashSet<SchemaElement> getSchemaElements(Integer role)
	{
		HashSet<SchemaElement> elements = new HashSet<SchemaElement>();
		for(Integer schemaID : getSchemas(role))
			elements.addAll(SchemaManager.getGraph(schemaID).getGraphElements());
		return elements;
	}
	
	/** Get the selected elements */
	static public List<Integer> getElements(Integer role)
		{ return new ArrayList<Integer>(getSelectedElements(role)); }

	/** Get the selected links */
	static public List<Integer> getMappingCells()
		{ return new ArrayList<Integer>(selectedLinks); }

	/** Indicates if the specified element is selected */
	static public boolean isElementSelected(Integer element, Integer role)
		{ return getSelectedElements(role).contains(element); }
	
	/** Indicates if the specified link is selected */
	static public boolean isMappingCellSelected(Integer link)
		{ return selectedLinks.contains(link); }
	
	/** Set the list of selected schemas */
	static public void setSelectedSchemas(ArrayList<Integer> leftSchemas, ArrayList<Integer> rightSchemas)
	{
		// Only update selected schemas if changed from original
		if(!leftSchemas.equals(getSchemas(HarmonyConsts.LEFT)) || !rightSchemas.equals(getSchemas(HarmonyConsts.RIGHT)))
		{
			ConfigManager.setParm("selectedInfo.leftSchemas",leftSchemas.toString());
			ConfigManager.setParm("selectedInfo.rightSchemas",rightSchemas.toString());
			for(SelectedInfoListener listener : listeners)
				listener.selectedSchemasModified();
		}
	}
	
	/** Unselects the specified elements */
	static private void unselectElements(List<Integer> elements, Integer role)
	{
		for(Integer element : elements) getSelectedElements(role).remove(element);
		for(SelectedInfoListener listener : listeners) listener.selectedElementsModified(role);
	}
	
	/** Selects the specified elements */
	static private void selectElements(List<Integer> elements, Integer role)
	{
		for(Integer element : elements) getSelectedElements(role).add(element);
		for(SelectedInfoListener listener : listeners) listener.selectedElementsModified(role);
	}
	
	/** Unselects the specified links */
	static private void unselectLinks(List<Integer> links)
	{
		for(Integer link : links) selectedLinks.remove(link);
		for(SelectedInfoListener listener : listeners) listener.selectedMappingCellsModified();
	}
	
	/** Selects the specified links */
	static private void selectLinks(List<Integer> links)
	{
		for(Integer link : links) selectedLinks.add(link);
		for(SelectedInfoListener listener : listeners) listener.selectedMappingCellsModified();
	}
	
	/** Toggles the selected element */
	static public void setElement(Integer element, Integer role, boolean append)
	{
		// Generate arrays for both the left and right nodes
		List<Integer> elements = new ArrayList<Integer>();
		elements.add(element);
		List<Integer> altElements = getElements(role.equals(HarmonyConsts.LEFT)?HarmonyConsts.RIGHT:HarmonyConsts.LEFT);
		
		// Get all mapping cells associated with the selected node
		List<Integer> mappingCells = new ArrayList<Integer>();
		for(Integer altElement : altElements)
		{
			Integer mappingCell = role==HarmonyConsts.LEFT ? MappingCellManager.getMappingCellID(element,altElement) : MappingCellManager.getMappingCellID(altElement,element);
			if(mappingCell!=null) mappingCells.add(mappingCell);
		}
		
		// Determines if the specified node is already selected
		boolean selected = getSelectedElements(role).contains(element);
			
		// If not appending node, clear out all selected nodes and select specified node
		if(!append)
		{
			selected = selected && getSelectedElements(role).size()==1;

			//Remove all previously selected nodes and associated links
			unselectLinks(getMappingCells());
			unselectElements(getElements(role),role);

			// Add in newly selected nodes and links
			if(!selected)
				{ selectElements(elements,role); selectLinks(mappingCells); }
		}
		
		// Remove nodes if appending and selected
		else if(selected) { unselectLinks(mappingCells); unselectElements(elements,role); }
		
		// Add nodes if appending and not selected
		else { selectElements(elements,role); selectLinks(mappingCells); }
	}
	
	/** Toggles the selected links */
	static public void setMappingCells(List<Integer> mappingCellIDs, boolean append)
	{
		// Determine if the specified links are already selected
		boolean selected = true;
		for(Integer mappingCellID : mappingCellIDs)
			if(!selectedLinks.contains(mappingCellID)) { selected=false; break; }
		
		// If not appending node, clear out all selected links and select specified links
		if(!append)
		{
			selected &= mappingCellIDs.size()==selectedLinks.size();
			if(selectedLinks.size()>0) unselectLinks(getMappingCells());
			if(!selected) selectLinks(mappingCellIDs);
		}

		// Add or remove links if appending
		else if(selected) unselectLinks(mappingCellIDs);
		else selectLinks(mappingCellIDs);
		
		// Get the list of all left and right elements
		HashSet<Integer> leftElementIDs = new HashSet<Integer>(), rightElementIDs = new HashSet<Integer>();
		for(SchemaElement element : getSchemaElements(HarmonyConsts.LEFT)) leftElementIDs.add(element.getId());
		for(SchemaElement element : getSchemaElements(HarmonyConsts.RIGHT)) rightElementIDs.add(element.getId());
		
		// Identify the newly selected elements
		HashSet<Integer> selectedLeftElements = new HashSet<Integer>();
		HashSet<Integer> selectedRightElements = new HashSet<Integer>();		
		for(Integer mappingCellID : mappingCellIDs)
		{
			// Identify the elements for the mapping cell
			MappingCell mappingCell = MappingCellManager.getMappingCell(mappingCellID);
			Integer element1 = mappingCell.getElement1();
			Integer element2 = mappingCell.getElement2();
			
			// Mark selected elements
			if(leftElementIDs.contains(element1) && rightElementIDs.contains(element2))
				{ selectedLeftElements.add(element1); selectedRightElements.add(element2); }
			if(leftElementIDs.contains(element2) && rightElementIDs.contains(element1))
				{ selectedLeftElements.add(element2); selectedRightElements.add(element1); }
		}
		
		// Clear out elements that are no longer selected
		unselectElements(getElements(HarmonyConsts.LEFT), HarmonyConsts.LEFT);
		unselectElements(getElements(HarmonyConsts.RIGHT), HarmonyConsts.RIGHT);		

		// Add in elements that are now selected
		selectElements(new ArrayList<Integer>(selectedLeftElements), HarmonyConsts.LEFT);
		selectElements(new ArrayList<Integer>(selectedRightElements), HarmonyConsts.RIGHT);		
	}
	
	/** Eliminate the selection of a link if the node goes out of focus */
	public void focusChanged()
	{
		// Clear out any selected left elements that are now out of focus
		Focus leftFocus = Filters.getFocus(HarmonyConsts.LEFT);
		if(leftFocus!=null)
			for(Integer elementID : getElements(HarmonyConsts.LEFT))
				if(!leftFocus.contains(elementID))
					setElement(elementID,HarmonyConsts.LEFT,true);
			
		// Clear out any selected right elements that are now out of focus
		Focus rightFocus = Filters.getFocus(HarmonyConsts.RIGHT);
		if(rightFocus!=null)
			for(Integer elementID : getElements(HarmonyConsts.RIGHT))
				if(!rightFocus.contains(elementID))
					setElement(elementID,HarmonyConsts.RIGHT,true);
	}
	
	/** Eliminate the selection of a link if the node goes out of depth */
	public void depthChanged()
	{
		// Clear out any selected left elements that are now out of depth
		int leftMinDepth = Filters.getMinDepth(HarmonyConsts.LEFT);
		int leftMaxDepth = Filters.getMaxDepth(HarmonyConsts.LEFT);
		for(Integer elementID : getElements(HarmonyConsts.LEFT))
		{
			boolean visible = false;
			for(Integer schemaID : getSchemas(HarmonyConsts.LEFT))
				for(Integer depth : SchemaManager.getDepths(schemaID, elementID))
					if(depth>=leftMinDepth && depth<=leftMaxDepth)
						{ visible=true; break; }
			if(!visible) setElement(elementID,HarmonyConsts.LEFT,true);
		}

		// Clear out any selected right elements that are now out of depth
		int rightMinDepth = Filters.getMinDepth(HarmonyConsts.RIGHT);
		int rightMaxDepth = Filters.getMaxDepth(HarmonyConsts.RIGHT);
		for(Integer elementID : getElements(HarmonyConsts.RIGHT))
		{
			boolean visible = false;
			for(Integer schemaID : getSchemas(HarmonyConsts.RIGHT))
				for(Integer depth : SchemaManager.getDepths(schemaID, elementID))
					if(depth>=rightMinDepth && depth<=rightMaxDepth)
						{ visible=true; break; }
			if(!visible) setElement(elementID,HarmonyConsts.RIGHT,true);
		}
	}
	
	/** Unselect all elements associated with the deleted schema */
	public void schemaRemoved(Integer schemaID)
	{
		// Eliminate selected schemas
		ArrayList<Integer> leftSchemas = getSchemas(HarmonyConsts.LEFT);
		ArrayList<Integer> rightSchemas = getSchemas(HarmonyConsts.RIGHT);
		leftSchemas.remove(schemaID); rightSchemas.remove(schemaID);
		setSelectedSchemas(leftSchemas, rightSchemas);
		
		// Check for eliminated selected elements in both roles
		Integer roles[] = { HarmonyConsts.LEFT, HarmonyConsts.RIGHT };
		for(Integer role : roles)
		{
			// Find all of the eliminated selected elements
			ArrayList<Integer> removedElements = new ArrayList<Integer>();
			for(Integer selElement : getElements(role))
			{
				for(SchemaElement schemaElement : SchemaManager.getSchemaElements(schemaID,null))
					if(selElement.equals(schemaElement)) break;
				removedElements.add(selElement);
			}
	
			// Remove the eliminated selected elements
			unselectElements(removedElements,role);
		}
	}
		
	/** Unselect links that have been removed */
	public void mappingCellRemoved(MappingCell mappingCell)
		{ selectedLinks.remove(mappingCell.getId()); }
	
	// Unused action events
	public void mappingModified() {}
	public void schemaAdded(Integer schemaID) {}
	public void mappingCellAdded(MappingCell mappingCell) {}
	public void mappingCellModified(MappingCell oldMappingCell, MappingCell newMappingCell) {}
	public void assertionsChanged() {}
	public void confidenceChanged() {}
	public void maxConfidenceChanged(Integer schemaObjectID) {}
	
	/** Adds a selected info listener */
	static public void addListener(SelectedInfoListener obj)
		{ listeners.add(obj); }

	/** Removes a selected info listener */
	static public void removeListener(SelectedInfoListener obj)
		{ listeners.remove(obj); }
}
