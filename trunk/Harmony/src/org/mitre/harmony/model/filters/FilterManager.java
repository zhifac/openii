// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.AbstractManager;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.harmony.model.mapping.MappingListener;
import org.mitre.harmony.view.schemaTree.SchemaTree;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Tracks link filters used in Harmony
 * @author CWOLF
 */
public class FilterManager extends AbstractManager<FiltersListener> implements MappingListener
{	
	// Constants for referencing the available filters
	static final public int USER_FILTER = 0;
	static final public int SYSTEM_FILTER = 1;
	static final public int HIERARCHY_FILTER = 2;
	static final public int BEST_FILTER = 3;
	
	// Stores the various filter settings
	private boolean filters[];
	
	// Stores all confidence filter settings
	private double minConfThreshold;
	private double maxConfThreshold;
	
	// Stores all focus filter settings
	private ArrayList<Focus> leftFoci;
	private ArrayList<Focus> rightFoci;
	
	// Stores all depth filter settings
	private int minLeftDepth;
	private int maxLeftDepth;
	private int minRightDepth;
	private int maxRightDepth;

	/** Tracks element confidences when BEST is set */
	private ElementConfHashTable elementConfidences = null;
	
	/** Constructor used to initializes the filters */
	public FilterManager(HarmonyModel harmonyModel)
	{
		super(harmonyModel);
		
		// Initialize the various filter settings
		filters = new boolean[4];
		filters[USER_FILTER]=true; filters[SYSTEM_FILTER]=true; filters[HIERARCHY_FILTER]=true; filters[BEST_FILTER]=false;
		minConfThreshold = MappingCellManager.MIN_CONFIDENCE;
		maxConfThreshold = MappingCellManager.MAX_CONFIDENCE;
		leftFoci = new ArrayList<Focus>();
		rightFoci = new ArrayList<Focus>();
		minLeftDepth = minRightDepth = 1;
		maxLeftDepth = maxRightDepth = Integer.MAX_VALUE;
	}
	
	//-----------------------------
	// Handles changes to a filter 
	//-----------------------------
	
	/** Sets the filter value */
	public void setFilter(Integer filter, boolean value)
	{
		// Only set filter if value has changed
		if(filters[filter]!=value)
		{
			filters[filter]=value;
			if(filter.equals(BEST_FILTER))
				elementConfidences = value ? new ElementConfHashTable(getModel()) : null;
			for(FiltersListener listener : getListeners()) listener.filterChanged(filter);
		}
	}
	
	/** Returns the current filter value */
	public boolean getFilter(Integer filter) { return filters[filter]; }

	//--------------------
	// Handles confidence
	//--------------------	
	
	/** Sets the confidence threshold */
	public void setConfidence(double minConfThresholdIn, double maxConfThresholdIn)
	{
		minConfThreshold = minConfThresholdIn;
		maxConfThreshold = maxConfThresholdIn;
		for(FiltersListener listener : getListeners()) listener.confidenceChanged();
	}

	/** Returns minimum confidence threshold */
	public double getMinConfThreshold() { return minConfThreshold; }
	
	/** Returns maximum confidence threshold */
	public double getMaxConfThreshold() { return maxConfThreshold; }
	
	//---------------
	// Handles depth
	//---------------
	
	/** Sets the depth; only links that originate from a node above this depth are to be displayed */
	public void setDepth(Integer side, int newMinDepth, int newMaxDepth)
	{
		if(side==MappingSchema.LEFT) { minLeftDepth = newMinDepth; maxLeftDepth = newMaxDepth; }
		else { minRightDepth = newMinDepth; maxRightDepth = newMaxDepth; }
		for(FiltersListener listener : getListeners()) listener.depthChanged(side);
	}
	
	/** Returns the minimum depth */
	public int getMinDepth(Integer side)
		{ return side==MappingSchema.LEFT ? minLeftDepth : minRightDepth; }
	
	/** Returns the maximum depth */
	public int getMaxDepth(Integer side)
		{ return side==MappingSchema.LEFT ? maxLeftDepth : maxRightDepth; }
	
	//---------------
	// Handles focus
	//---------------	
	
	/** Adds a focused element to the specified side */
	public void addFocus(Integer side, Integer schemaID, ElementPath elementPath)
	{
		// Retrieve the focus associated with the specified schema
		Focus focus = getFocus(side,schemaID);
		if(focus==null)
		{
			ArrayList<Focus> foci = side==MappingSchema.LEFT ? leftFoci : rightFoci;
			foci.add(focus = new Focus(schemaID, getModel()));
		}

		// Adds the specified element to the focus
		if(elementPath!=null) focus.addFocus(elementPath);
		for(FiltersListener listener : getListeners()) listener.focusModified(side);
	}

	/** Removes a focused element from the specified side */
	public void removeFocus(Integer side, Integer schemaID, ElementPath elementPath)
	{
		Focus focus = getFocus(side,schemaID);
		if(focus!=null)
		{
			focus.removeFocus(elementPath);
			for(FiltersListener listener : getListeners()) listener.focusModified(side);
		}
	}

	/** Removes all foci from the specified side */
	public void removeFoci(Integer side)
	{
		for(Focus focus : side==MappingSchema.LEFT ? leftFoci : rightFoci)
			focus.removeAllFoci();
		for(FiltersListener listener : getListeners()) listener.focusModified(side);		
	}
	
	/** Hides an element on the specified side */
	public void hideElement(Integer side, Integer schemaID, Integer elementID)
	{
		// Retrieve the focus associated with the specified schema
		Focus focus = getFocus(side,schemaID);
		if(focus==null)
		{
			ArrayList<Focus> foci = side==MappingSchema.LEFT ? leftFoci : rightFoci;
			foci.add(focus = new Focus(schemaID, getModel()));
		}

		// Adds the specified element to the hidden elements
		focus.hideElement(elementID);
		for(FiltersListener listener : getListeners()) listener.focusModified(side);
	}

	/** Unhides an element on the specified side */
	public void unhideElement(Integer side, Integer schemaID, Integer elementID)
	{
		Focus focus = getFocus(side,schemaID);
		if(focus!=null)
		{
			focus.unhideElement(elementID);
			for(FiltersListener listener : getListeners()) listener.focusModified(side);
		}
	}
	
	/** Return the list of foci on the specified side */
	public ArrayList<Focus> getFoci(Integer side)
		{ return new ArrayList<Focus>(side==MappingSchema.LEFT ? leftFoci : rightFoci); }
	
	/** Returns the number of focused elements on the specified side */
	public Integer getFocusCount(Integer side)
	{
		Integer count = 0;
		for(Focus focus : getFoci(side)) count += focus.getFocusedPaths().size();
		return count;
	}
	
	/** Returns the focus associated with the specified schema */
	public Focus getFocus(Integer side, Integer schemaID)
	{
		for(Focus focus : getFoci(side))
			if(focus.getSchemaID().equals(schemaID)) return focus;
		return null;
	}
	
	/** Indicates if the specified schema is in focus */
	public boolean inFocus(Integer side, Integer schemaID)
	{
		Focus focus = getFocus(side, schemaID);
		if(focus==null || focus.getFocusedPaths().size()==0) return getFocusCount(side)==0;
		return true;
	}
	
	/** Indicates if the specified element is in focus */
	public boolean inFocus(Integer side, Integer schemaID, Integer elementID)
	{
		if(!inFocus(side,schemaID)) return false;
		Focus focus = getFocus(side, schemaID);
		return focus==null || focus.contains(elementID);
	}

	/** Indicates if the specified node is in focus */
	public boolean inFocus(Integer side, DefaultMutableTreeNode node)
	{
		Integer schemaID = SchemaTree.getSchema(node);
		if(!inFocus(side,schemaID)) return false;
		Focus focus = getFocus(side, schemaID);
		return focus==null || focus.contains(node);
	}
	
	/** Identifies all elements in focus on the specified side */
	public HashSet<Integer> getFocusedElementIDs(Integer side)
	{
		HashSet<Integer> focusedElements = new HashSet<Integer>();
		for(Integer schemaID : getModel().getMappingManager().getSchemaIDs(side))
			if(inFocus(side,schemaID))
			{
				Focus focus = getFocus(side, schemaID);
				if(focus==null)
				{
					HierarchicalSchemaInfo schema = getModel().getSchemaManager().getSchemaInfo(schemaID);
					for(SchemaElement element : schema.getElements(null))
						focusedElements.add(element.getId());
				}
				else focusedElements.addAll(focus.getElementIDs());
			}
		return focusedElements;
	}
	
	//--------------------
	// Handles visibility
	//--------------------

	/** Determines if the specified node is visible or not */
	public boolean isVisibleNode(Integer side, DefaultMutableTreeNode node)
	{
		// Check that the element is within focus
		if(!inFocus(side, node)) return false;
		
		// Check that the element is within depth
		int depth = node.getPath().length-2;
		return depth >= getMinDepth(side) && depth <= getMaxDepth(side);
	}
	
	/** Determines if the specified mapping cell is visible or not */
	public boolean isVisibleMappingCell(Integer mappingCellID)
	{
		// Retrieve the mapping cell info
		MappingCell mappingCell = getModel().getMappingCellManager().getMappingCell(mappingCellID);
		
		// Check if link is within confidence thresholds
		double confidence = mappingCell.getScore();
		if(confidence < minConfThreshold || confidence > maxConfThreshold) return false;
		
		// If BEST is asserted, check to ensure that link is best link for either left or right
		if(getFilter(BEST_FILTER))
		{
			// Gather up all elements associated with the mapping cell
			ArrayList<Integer> elementIDs = new ArrayList<Integer>(mappingCell.getOutput());
			elementIDs.addAll(Arrays.asList(mappingCell.getInput()));

			// Determine if considered a "best" mapping cell
			boolean best = false;
			for(Integer elementID : elementIDs)
				if(confidence == elementConfidences.get(elementID)) { best=true; break; }
			if(!best) return false;
		}

		// Check that link matches current filters for USER and SYSTEM links		
		boolean validated = mappingCell.getValidated();
		if(validated && !getFilter(USER_FILTER)) return false;
		if(!validated && !getFilter(SYSTEM_FILTER)) return false;

		// Indicates that the mapping cell is visible
		return true;
	}
	
	/** Remove focus filters to schemas that are no longer being viewed */
	public void mappingModified()
	{
		// Remove foci from left and right side based on modification to selected schema
		Integer sides[] = { MappingSchema.LEFT, MappingSchema.RIGHT };
		for(Integer side : sides)
		{
			ArrayList<Integer> schemas = getModel().getMappingManager().getSchemaIDs(side);
			ArrayList<Focus> foci = side==MappingSchema.LEFT ? leftFoci : rightFoci;
			for(Focus focus : new ArrayList<Focus>(foci))
				if(!schemas.contains(focus.getSchemaID()))
				{
					foci.remove(focus);
					for(FiltersListener listener : getListeners()) listener.focusModified(side);
				}
		}
	}
	
	// Unused event listeners
	public void schemaAdded(Integer schemaID) {}
	public void schemaModified(Integer schemaID) {}
	public void schemaRemoved(Integer schemaID) {}

	/** Inform listeners that the max confidence has changed */
	void fireMaxConfidenceChanged(Integer schemaObjectID)
		{ for(FiltersListener listener : getListeners()) listener.maxConfidenceChanged(schemaObjectID); }
}