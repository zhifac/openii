// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.selectedInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.mitre.harmony.model.AbstractManager;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.FiltersListener;
import org.mitre.harmony.model.filters.Focus;
import org.mitre.harmony.model.mapping.MappingCellListener;
import org.mitre.harmony.model.mapping.MappingListener;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Tracks selected info within Harmony
 * @author CWOLF
 */
public class SelectedInfoManager extends AbstractManager<SelectedInfoListener> implements MappingListener, MappingCellListener, FiltersListener
{
	// Constants for defining how to adjust the selected items
	static private final int ADD = 0;
	static private final int REMOVE = 1;
	static private final int REPLACE = 2;
	
	/** Stores the displayed left schemas */
	private ArrayList<Integer> leftSchemaIDs = new ArrayList<Integer>();

	/** Stores the displayed right schemas */
	private ArrayList<Integer> rightSchemaIDs = new ArrayList<Integer>();
	
	/** Stores the displayed left element */
	private Integer displayedLeftElement = null;
	
	/** Stores the displayed right element */
	private Integer displayedRightElement = null;
	
	/** Stores the selected left elements */
	private HashSet<Integer> selectedLeftElements = new HashSet<Integer>();

	/** Stores the selected right elements */
	private HashSet<Integer> selectedRightElements = new HashSet<Integer>();

	/** Stores the selected mapping cells */
	private HashSet<Integer> selectedMappingCells = new HashSet<Integer>();
	
	/** Returns the specified list of selected elements */
	private HashSet<Integer> getSelectedElementSet(Integer role)
		{ return role==HarmonyConsts.LEFT ? selectedLeftElements : selectedRightElements; }
	
	/** Returns the valid schemaIDs from the specified list of schemas */
	private ArrayList<Integer> getValidSchemaIDs(ArrayList<Integer> schemaIDs)
	{
		ArrayList<Integer> validSchemaIDs = getModel().getMappingManager().getSchemas();
		for(Integer schemaID : new ArrayList<Integer>(schemaIDs))
			if(!validSchemaIDs.contains(schemaID))
				schemaIDs.remove(schemaID);
		return schemaIDs;
	}
	
	/** Constructor used to initialize the selected info */
	public SelectedInfoManager(HarmonyModel harmonyModel)
	{
		super(harmonyModel);
		harmonyModel.getMappingManager().addListener(this);
		harmonyModel.getMappingCellManager().addListener(this);
	}
	
	//--------------------- Returns the various selected pieces of info ---------------------
	
	/** Returns the schemas displayed on the left side of Harmony */
	public ArrayList<Integer> getSchemas(Integer side)
		{ return new ArrayList<Integer>(side.equals(HarmonyConsts.LEFT) ? leftSchemaIDs : rightSchemaIDs); }

	/** Returns all elements displayed on the specified side of Harmony */
	public HashSet<SchemaElement> getSchemaElements(Integer side)
	{
		HashSet<SchemaElement> elements = new HashSet<SchemaElement>();
		for(Integer schemaID : getSchemas(side))
			elements.addAll(getModel().getSchemaManager().getGraph(schemaID).getGraphElements());
		return elements;
	}

	/** Returns all element IDs displayed on the specified side of Harmony */
	public HashSet<Integer> getSchemaElementIDs(Integer side)
	{
		HashSet<Integer> elementIDs = new HashSet<Integer>();
		for(SchemaElement element : getSchemaElements(side))
			elementIDs.add(element.getId());
		return elementIDs;
	}
	
	/** Get the displayed element */
	public Integer getDisplayedElement(Integer side)
		{ return side==HarmonyConsts.LEFT ? displayedLeftElement : displayedRightElement; }
	
	/** Get the selected elements */
	public List<Integer> getSelectedElements(Integer side)
		{ return new ArrayList<Integer>(getSelectedElementSet(side)); }

	/** Get the selected mapping cells */
	public List<Integer> getSelectedMappingCells()
		{ return new ArrayList<Integer>(selectedMappingCells); }

	/** Indicates if the specified element is selected */
	public boolean isElementSelected(Integer element, Integer side)
		{ return getSelectedElementSet(side).contains(element); }
	
	/** Indicates if the specified link is selected */
	public boolean isMappingCellSelected(Integer mappingCellID)
		{ return selectedMappingCells.contains(mappingCellID); }
	
	// ------------ Handles the selecting of the various pieces of information -------------
	
	/** Set the list of selected schemas */
	public void setSelectedSchemas(ArrayList<Integer> leftSchemaIDs, ArrayList<Integer> rightSchemaIDs)
	{
		// Only update selected schemas if changed from original
		if(!leftSchemaIDs.equals(getSchemas(HarmonyConsts.LEFT)) || !rightSchemaIDs.equals(getSchemas(HarmonyConsts.RIGHT)))
		{
			this.leftSchemaIDs = getValidSchemaIDs(leftSchemaIDs);
			this.rightSchemaIDs = getValidSchemaIDs(rightSchemaIDs);
			for(SelectedInfoListener listener : getListeners())
				listener.selectedSchemasModified();
		}
	}

	/** Updates the set as specified */
	private boolean updateSet(HashSet<Integer> set, List<Integer> items, Integer mode)
	{
		// Check to make sure updates are possible
		boolean containsOne=false, containsAll=true;
		for(Integer item : items)
		{
			boolean containsElement = set.contains(item);
			containsOne |= containsElement; containsAll &= containsElement;
		}
		if(mode==ADD && containsAll) return false;
		if(mode==REMOVE && !containsOne) return false;
		if(mode==REPLACE && containsAll && set.size()==items.size()) return false;
			
		// Update the set
		switch(mode)
		{
			case ADD: for(Integer item : items) set.add(item); break;
			case REMOVE: for(Integer item : items) set.remove(item); break;
			case REPLACE: set.clear(); set.addAll(items); break;
		}		
		return true;
	}
	
	/** Sets the selected elements */
	private void setSelectedElements(List<Integer> elements, Integer side, Integer mode)
	{
		// Set the selected elements
		HashSet<Integer> selectedElements = getSelectedElementSet(side);
		if(!updateSet(selectedElements,elements,mode)) return;
		for(SelectedInfoListener listener : getListeners()) listener.selectedElementsModified(side);

		// Identify changes to the mapping cells required by the newly selected elements
		List<Integer> mappingCells = new ArrayList<Integer>();
		List<Integer> leftElements = getSelectedElements(HarmonyConsts.LEFT);
		List<Integer> rightElements = getSelectedElements(HarmonyConsts.RIGHT);
		for(Integer leftElement : leftElements)
			for(Integer rightElement : rightElements)
			{
				Integer mappingCell = getModel().getMappingCellManager().getMappingCellID(leftElement,rightElement);
				if(mappingCell!=null) mappingCells.add(mappingCell);
			}

		// Update the mapping cells
		if(updateSet(selectedMappingCells,mappingCells,REPLACE))
			for(SelectedInfoListener listener : getListeners()) listener.selectedMappingCellsModified();

		// Update the displayed elements
		updateDisplayedElements();
	}

	/** Sets the selected mapping cells */
	private void setSelectedMappingCells(List<Integer> mappingCells, Integer mode)
	{
		// Set the selected mapping cells
		if(!updateSet(selectedMappingCells,mappingCells,mode)) return;
		for(SelectedInfoListener listener : getListeners()) listener.selectedMappingCellsModified();

		// Identify changes to the elements required by the newly selected mapping cells
		ArrayList<Integer> selectedLeftElements = new ArrayList<Integer>();
		ArrayList<Integer> selectedRightElements = new ArrayList<Integer>();		
		HashSet<Integer> leftElements = getSchemaElementIDs(HarmonyConsts.LEFT);
		HashSet<Integer> rightElements = getSchemaElementIDs(HarmonyConsts.RIGHT);
		for(Integer mappingCellID : getSelectedMappingCells())
		{
			// Identify the elements for the mapping cell
			MappingCell mappingCell = getModel().getMappingCellManager().getMappingCell(mappingCellID);
			Integer element1 = mappingCell.getElement1();
			Integer element2 = mappingCell.getElement2();
			
			// Mark selected elements
			if(leftElements.contains(element1) && rightElements.contains(element2))
				{ selectedLeftElements.add(element1); selectedRightElements.add(element2); }
			if(leftElements.contains(element2) && rightElements.contains(element1))
				{ selectedLeftElements.add(element2); selectedRightElements.add(element1); }
		}

		// Update the elements
		Integer sides[] = { HarmonyConsts.LEFT, HarmonyConsts.RIGHT };
		for(Integer side : sides)
		{
			ArrayList<Integer> selectedElements = (side.equals(HarmonyConsts.LEFT)?selectedLeftElements:selectedRightElements);
			if(updateSet(getSelectedElementSet(side),selectedElements,REPLACE))
				for(SelectedInfoListener listener : getListeners())
					listener.selectedElementsModified(side);		
		}
			
		// Update the displayed elements
		updateDisplayedElements();
	}

	/** Updates the displayed elements */
	private void updateDisplayedElements()
	{
		Integer leftElement = null, rightElement = null;

		// Check for easy case of only one item selected on a given side
		List<Integer> leftElements = getSelectedElements(HarmonyConsts.LEFT);
		List<Integer> rightElements = getSelectedElements(HarmonyConsts.RIGHT);
		if(leftElements.size()==1) leftElement = leftElements.get(0);
		if(rightElements.size()==1) rightElement = rightElements.get(0);

		// Otherwise, check for case where mapping cells are crossing
		else if(leftElements.size()==2 && rightElements.size()==2)
			if(leftElements.contains(rightElements.get(0)) && leftElements.contains(rightElements.get(1)))
				{ leftElement = leftElements.get(0); rightElement = leftElements.get(1); }

		// Update displayed elements if needed
		if(displayedLeftElement==null || !displayedLeftElement.equals(leftElement))
		{
			displayedLeftElement=leftElement; 
			for(SelectedInfoListener listener : getListeners())
				listener.displayedElementModified(HarmonyConsts.LEFT);
		}
		if(displayedRightElement==null || !displayedRightElement.equals(rightElement))
		{
			displayedRightElement=rightElement; 
			for(SelectedInfoListener listener : getListeners())
				listener.displayedElementModified(HarmonyConsts.RIGHT);
		}
	}
	
	/** Toggles the selected element */
	public void setElement(Integer element, Integer side, boolean append)
	{
		// Place the specified element into a list
		List<Integer> elements = Arrays.asList(new Integer[] {element});
		
		// Determines if the specified element should be treated as selected
		boolean selected = getSelectedElementSet(side).contains(element);
		if(!append) selected &= getSelectedElementSet(side).size()==1;
		
		// Handles selection of elements
		if(!append) setSelectedElements(selected ? new ArrayList<Integer>() : elements,side,REPLACE);
		else setSelectedElements(elements,side,selected?REMOVE:ADD);
	}
	
	/** Toggles the selected mapping cells */
	public void setMappingCells(List<Integer> mappingCells, boolean append)
	{
		// Determine if the specified mapping cells should be treated as selected
		boolean selected = true;		
		for(Integer mappingCell : mappingCells)
			if(!selectedMappingCells.contains(mappingCell)) { selected=false; break; }
		if(!append) selected &= mappingCells.size()==selectedMappingCells.size();
		
		// Handles the case where the specified mapping cells should replace the old selected mapping cells
		if(!append) setSelectedMappingCells(selected ? new ArrayList<Integer>() : mappingCells, REPLACE);
		else setSelectedMappingCells(mappingCells,selected?REMOVE:ADD);
	}
	
	//------------ Updates the selected information based on the occurrence of events ------------
	
	/** Unselect elements that are out of focus */
	public void focusRemoved(Integer side, Focus focus)
	{
		// Identify all of the elements that are no longer visible
		ArrayList<Integer> removedElements = new ArrayList<Integer>();
		for(Integer elementID : getSelectedElements(side))
			if(!getModel().getFilters().inFocus(side, elementID))
				removedElements.add(elementID);
		
		// Remove the eliminated selected elements
		if(removedElements.size()>0)
			setSelectedElements(removedElements,side,REMOVE);
	}
	
	/** Unselect elements that are out of depth */
	public void depthChanged()
	{
		Integer sides[] = { HarmonyConsts.LEFT, HarmonyConsts.RIGHT };
		for(Integer side : sides)
		{
			// Get the minimum and maximum depth for the specified side
			int minDepth = getModel().getFilters().getMinDepth(side);
			int maxDepth = getModel().getFilters().getMaxDepth(side);
			
			// Identify all of the elements that are no longer within the specified depths
			ArrayList<Integer> removedElements = new ArrayList<Integer>();			
			for(Integer elementID : getSelectedElements(side))
			{
				boolean visible = false;
				for(Integer schemaID : getSchemas(side))
					for(Integer depth : getModel().getSchemaManager().getDepths(schemaID, elementID))
						if(depth>=minDepth && depth<=maxDepth)
							{ visible=true; break; }
				if(!visible) removedElements.add(elementID);
			}
			
			// Remove the eliminated selected elements
			if(removedElements.size()>0)
				setSelectedElements(removedElements,side,REMOVE);
		}
	}
	
	/** Unselect schemas and elements associated with the deleted schema */
	public void schemaRemoved(Integer schemaID)
	{
		// Eliminate selected schemas
		ArrayList<Integer> leftSchemas = getSchemas(HarmonyConsts.LEFT);
		ArrayList<Integer> rightSchemas = getSchemas(HarmonyConsts.RIGHT);
		leftSchemas.remove(schemaID); rightSchemas.remove(schemaID);
		setSelectedSchemas(leftSchemas, rightSchemas);
		
		// Check for eliminated selected elements in both sides
		Integer sides[] = { HarmonyConsts.LEFT, HarmonyConsts.RIGHT };
		for(Integer side : sides)
		{
			// Gets the list of all schema elements which still exist
			HashSet<SchemaElement> schemaElements = getSchemaElements(side);
			
			// Find all of the eliminated selected elements
			ArrayList<Integer> removedElements = new ArrayList<Integer>();
			for(Integer selectedElement : getSelectedElements(side))
				if(!schemaElements.contains(selectedElement))
					removedElements.add(selectedElement);
	
			// Remove the eliminated selected elements
			if(removedElements.size()>0)
				setSelectedElements(removedElements,side,REMOVE);
		}
	}
		
	/** Unselect mapping cells that have been removed */
	public void mappingCellRemoved(MappingCell mappingCell)
	{
		List<Integer> mappingCells = Arrays.asList(new Integer[] {mappingCell.getId()});
		setSelectedMappingCells(mappingCells,REMOVE);
	}

	// Unused action events
	public void mappingModified() {}
	public void schemaAdded(Integer schemaID) {}
	public void mappingCellAdded(MappingCell mappingCell) {}
	public void mappingCellModified(MappingCell oldMappingCell, MappingCell newMappingCell) {}
	public void focusAdded(Integer side, Focus focus) {}
	public void assertionsChanged() {}
	public void confidenceChanged() {}
	public void maxConfidenceChanged(Integer schemaObjectID) {}
}
