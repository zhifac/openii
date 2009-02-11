// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.filters;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.ListenerGroup;
import org.mitre.harmony.model.MappingCellManager;
import org.mitre.harmony.model.selectedInfo.SelectedInfo;
import org.mitre.harmony.model.selectedInfo.SelectedInfoListener;
import org.mitre.schemastore.model.MappingCell;

/**
 * Tracks link filters used in Harmony
 * @author CWOLF
 */
public class Filters implements SelectedInfoListener
{	
	// Constants for referencing the assertion array
	static final public int USER = 0;		// Location of user assertion in array
	static final public int SYSTEM = 1;		// Location of system assertion in array
	static final public int BEST = 2;		// Location of best assertion in array
	
	// Stores all assertion filter settings
	static private boolean assertions[];	// Current assertion settings
	
	// Stores all confidence filter settings
	static private double minConfThreshold;	// Current min confidence setting
	static private double maxConfThreshold;	// Current max confidence setting
	
	// Stores all focus filter settings
	static private Focus leftFocus;		// Currently selected left focus
	static private Focus rightFocus;	// Currently selected right focus
	
	// Stores all depth filter settings
	static private int minLeftDepth;		// Only links whose left element's depth is above this are shown
	static private int maxLeftDepth;		// Only links whose left element's depth is below this are shown
	static private int minRightDepth;		// Only links whose right element's depth is above this are shown
	static private int maxRightDepth;		// Only links whose right element's depth is below this are shown

	/** Tracks element confidences when BEST is set */
	static private ElementConfHashTable elementConfidences = null;

	/** Stores the filter listeners */
	static private ListenerGroup<FiltersListener> listeners = new ListenerGroup<FiltersListener>();
	
	/** Constructor used to initializes the filters */
	private Filters()
	{
		// Initialize the various filter settings
		assertions = new boolean[3]; assertions[USER]=true; assertions[SYSTEM]=true; assertions[BEST]=false;
		minConfThreshold = MappingCellManager.MIN_CONFIDENCE;
		maxConfThreshold = MappingCellManager.MAX_CONFIDENCE;
		leftFocus = rightFocus = null;
		minLeftDepth = minRightDepth = 1;
		maxLeftDepth = maxRightDepth = Integer.MAX_VALUE;
		
		// Add listeners to trigger changes to the selected info
		SelectedInfo.addListener(this);
	}
	static { new Filters(); }

	/** Sets the assertion values */
	static public void setAssertions(boolean[] newAssertions)
	{
		assertions = newAssertions;
		if(assertions[BEST]) { if(elementConfidences==null) elementConfidences = new ElementConfHashTable(); }
		else { if(elementConfidences!=null) elementConfidences=null; }			
		for(FiltersListener listener : listeners.get()) listener.assertionsChanged();
	}

	/** Sets the confidence threshold */
	static public void setConfidence(double minConfThresholdIn, double maxConfThresholdIn)
	{
		minConfThreshold = minConfThresholdIn;
		maxConfThreshold = maxConfThresholdIn;
		for(FiltersListener listener : listeners.get()) listener.confidenceChanged();
	}
	
	/**
	 * Sets the focus such that only links that originate from this node,
	 * or its descendants, are to be displayed.
	 */
	static public void setFocus(Integer role, Focus newFocus)
	{
		if(role==HarmonyConsts.LEFT) leftFocus = newFocus;
		else rightFocus = newFocus;
		for(FiltersListener listener : listeners.get()) listener.focusChanged();
	}
	
	/**
	 * Sets the depth; only links that originate from a node above
	 * this depth are to be displayed.
	 */
	static public void setDepth(Integer role, int newMinDepth, int newMaxDepth)
	{
		if(role==HarmonyConsts.LEFT) { minLeftDepth = newMinDepth; maxLeftDepth = newMaxDepth; }
		else { minRightDepth = newMinDepth; maxRightDepth = newMaxDepth; }
		for(FiltersListener listener : listeners.get()) listener.depthChanged();
	}
	
	/** Returns the current assertions */
	static public boolean[] getAssertions() { return assertions; }

	/** Returns minimum confidence threshold */
	static public double getMinConfThreshold() { return minConfThreshold; }
	
	/** Returns maximum confidence threshold */
	static public double getMaxConfThreshold() { return maxConfThreshold; }

	/** Return the node from this tree currently in focus */
	static public Focus getFocus(Integer role)
		{ return role==HarmonyConsts.LEFT ? leftFocus : rightFocus; }
	
	/** Returns the minimum depth */
	static public int getMinDepth(Integer role)
		{ return role==HarmonyConsts.LEFT ? minLeftDepth : minRightDepth; }
	
	/** Returns the maximum depth */
	static public int getMaxDepth(Integer role)
		{ return role==HarmonyConsts.LEFT ? maxLeftDepth : maxRightDepth; }

	/** Determines if a path is visible or not */
	static public boolean visiblePath(Integer role, TreePath path)
		{ return visibleNode(role, (DefaultMutableTreeNode) path.getLastPathComponent()); }

	/** Determines if a node is visible or not */
	static public boolean visibleNode(Integer role, DefaultMutableTreeNode node)
	{
		// Check that the element is within focus
		Focus focus = role.equals(HarmonyConsts.LEFT) ? leftFocus : rightFocus;
		if(focus!=null && !focus.contains(node)) return false;
		
		// Check that the element is within depth
		int depth = node.getPath().length-2;
		int minDepth = role.equals(HarmonyConsts.LEFT) ? minLeftDepth : minRightDepth;
		int maxDepth = role.equals(HarmonyConsts.LEFT) ? maxLeftDepth : maxRightDepth;
		return depth >= minDepth && depth <= maxDepth;
	}
	
	/** Determines if a link should be displayed or not */
	static public boolean visibleMappingCell(Integer mappingCellID)
	{
		// Retrieve the mapping cell info
		MappingCell mappingCell = MappingCellManager.getMappingCell(mappingCellID);
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
		boolean validated = mappingCell.getValidated();
		if(validated && !getAssertions()[USER]) return false;
		if(!validated && !getAssertions()[SYSTEM]) return false;

		// Indicates that the mapping cell is visible
		return true;
	}

	/** Remove focus filters to schemas that are no longer being viewed */
	public void selectedSchemasModified()
	{
		// Adjust the left focus as needed
		if(leftFocus!=null)
			if(!SelectedInfo.getSchemas(HarmonyConsts.LEFT).contains(leftFocus.getSchemaID()))
				setFocus(HarmonyConsts.LEFT,null);

		// Adjust the right focus as needed
		if(rightFocus!=null)
			if(!SelectedInfo.getSchemas(HarmonyConsts.RIGHT).contains(rightFocus.getSchemaID()))
				setFocus(HarmonyConsts.RIGHT,null);
	}
	
	// Unused event listeners
	public void selectedElementsModified(Integer role) {}
	public void selectedMappingCellsModified() {}
	
	//-----------------------------------------------------
	// Purpose: Allows classes to listen for filter changes
	//-----------------------------------------------------
	static public void addListener(FiltersListener listener) { listeners.add(listener); }
	static void fireMaxConfidenceChanged(Integer schemaObjectID)
		{ for(FiltersListener listener : listeners.get()) listener.maxConfidenceChanged(schemaObjectID); }
}