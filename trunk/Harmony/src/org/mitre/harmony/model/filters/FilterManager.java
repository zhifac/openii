// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.filters;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.AbstractManager;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.harmony.model.mapping.MappingListener;
import org.mitre.harmony.view.schemaTree.SchemaTree;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.MappingCell.MappingType;

/**
 * Tracks link filters used in Harmony
 * @author CWOLF
 */
public class FilterManager extends AbstractManager<FiltersListener> implements MappingListener
{	
	// Constants for referencing the assertion array
	static final public int USER = 0;
	static final public int SYSTEM = 1;
	static final public int BEST = 2;
	
	// Stores all assertion filter settings
	private boolean assertions[];
	
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
		assertions = new boolean[3]; assertions[USER]=true; assertions[SYSTEM]=true; assertions[BEST]=false;
		minConfThreshold = MappingCellManager.MIN_CONFIDENCE;
		maxConfThreshold = MappingCellManager.MAX_CONFIDENCE;
		leftFoci = new ArrayList<Focus>();
		rightFoci = new ArrayList<Focus>();
		minLeftDepth = minRightDepth = 1;
		maxLeftDepth = maxRightDepth = Integer.MAX_VALUE;
	}
	
	//--------------------
	// Handles assertions
	//--------------------
	
	/** Sets the assertion values */
	public void setAssertions(boolean[] newAssertions)
	{
		assertions = newAssertions;
		if(assertions[BEST]) { if(elementConfidences==null) elementConfidences = new ElementConfHashTable(getModel()); }
		else { if(elementConfidences!=null) elementConfidences=null; }
		for(FiltersListener listener : getListeners()) listener.assertionsChanged();
	}
	
	/** Returns the current assertions */
	public boolean[] getAssertions() { return assertions; }

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
		for(FiltersListener listener : getListeners()) listener.depthChanged();
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
		focus.addFocus(elementPath);
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
	public boolean inFocus(Integer side, Integer schemaID, DefaultMutableTreeNode node)
	{
		if(!inFocus(side,schemaID)) return false;
		Focus focus = getFocus(side, schemaID);
		return focus==null || focus.contains(node);
	}
	
	//--------------------
	// Handles visibility
	//--------------------

	/** Determines if the specified node is visible or not */
	public boolean isVisibleNode(Integer side, DefaultMutableTreeNode node)
	{
		// Check that the element is within focus
		Integer schemaID = SchemaTree.getSchema(node);
		Object elementID = node.getUserObject();
		if(elementID instanceof Integer && !inFocus(side, schemaID, node)) return false;
		
		// Check that the element is within depth
		int depth = node.getPath().length-2;
		return depth >= getMinDepth(side) && depth <= getMaxDepth(side);
	}
	
	/** Determines if the specified mapping cell is visible or not */
	public boolean isVisibleMappingCell(Integer mappingCellID)
	{
		// Retrieve the mapping cell info
		MappingCell mappingCell = getModel().getMappingCellManager().getMappingCell(mappingCellID);
		Integer element1 = mappingCell.getElement1();
		Integer element2 = mappingCell.getElement2();
		
		// Check if link is within confidence thresholds
		double confidence = mappingCell.getScore();
		if(confidence < minConfThreshold || confidence > maxConfThreshold) return false;
		
		// If BEST is asserted, check to ensure that link is best link for either left or right
		if(getAssertions()[BEST])
		{
			boolean element1Best = confidence == elementConfidences.get(element1);
			boolean element2Best = confidence == elementConfidences.get(element2);
			if(!element1Best && !element2Best) return false;
		}

		// Check that link matches current filters for USER and SYSTEM links		
		boolean validated = mappingCell.getType().equals(MappingType.VALIDATED);
		if(validated && !getAssertions()[USER]) return false;
		if(!validated && !getAssertions()[SYSTEM]) return false;

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