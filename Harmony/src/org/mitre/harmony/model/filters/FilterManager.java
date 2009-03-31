// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.filters;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.mitre.harmony.model.AbstractManager;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.harmony.model.selectedInfo.SelectedInfoListener;
import org.mitre.schemastore.model.MappingCell;

/**
 * Tracks link filters used in Harmony
 * @author CWOLF
 */
public class FilterManager extends AbstractManager<FiltersListener> implements SelectedInfoListener
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
	
	/** Sets the assertion values */
	public void setAssertions(boolean[] newAssertions)
	{
		assertions = newAssertions;
		if(assertions[BEST]) { if(elementConfidences==null) elementConfidences = new ElementConfHashTable(getModel()); }
		else { if(elementConfidences!=null) elementConfidences=null; }
		for(FiltersListener listener : getListeners()) listener.assertionsChanged();
	}

	/** Sets the confidence threshold */
	public void setConfidence(double minConfThresholdIn, double maxConfThresholdIn)
	{
		minConfThreshold = minConfThresholdIn;
		maxConfThreshold = maxConfThresholdIn;
		for(FiltersListener listener : getListeners()) listener.confidenceChanged();
	}
	
	/** Adds a focused element to the specified side */
	public void addFocus(Integer side, Focus focus)
	{
		ArrayList<Focus> foci = side==HarmonyConsts.LEFT ? leftFoci : rightFoci;
		foci.add(focus);
		for(FiltersListener listener : getListeners()) listener.focusAdded(side,focus);
	}

	/** Removes a focused element from the specified side */
	public void removeFocus(Integer side, Focus focus)
	{
		ArrayList<Focus> foci = side==HarmonyConsts.LEFT ? leftFoci : rightFoci;
		foci.remove(focus);
		for(FiltersListener listener : getListeners()) listener.focusRemoved(side,focus);
	}
	
	/**
	 * Sets the depth; only links that originate from a node above
	 * this depth are to be displayed.
	 */
	public void setDepth(Integer role, int newMinDepth, int newMaxDepth)
	{
		if(role==HarmonyConsts.LEFT) { minLeftDepth = newMinDepth; maxLeftDepth = newMaxDepth; }
		else { minRightDepth = newMinDepth; maxRightDepth = newMaxDepth; }
		for(FiltersListener listener : getListeners()) listener.depthChanged();
	}
	
	/** Returns the current assertions */
	public boolean[] getAssertions() { return assertions; }

	/** Returns minimum confidence threshold */
	public double getMinConfThreshold() { return minConfThreshold; }
	
	/** Returns maximum confidence threshold */
	public double getMaxConfThreshold() { return maxConfThreshold; }

	/** Return the node from this tree currently in focus */
	public ArrayList<Focus> getFoci(Integer role)
		{ return role==HarmonyConsts.LEFT ? leftFoci : rightFoci; }
	
	/** Returns the focus associated with the specified element */
	public Focus getFocus(Integer role, Integer elementID)
	{
		for(Focus focus : getFoci(role))
			if(focus.getElementID().equals(elementID)) return focus;
		return null;
	}
	
	/** Indicates if the specified element is in focus */
	public boolean inFocus(Integer role, Integer element)
	{
		ArrayList<Focus> foci = getFoci(role);
		if(foci.size()==0) return true;
		else for(Focus focus : foci)
			if(focus.contains(element)) return true;
		return false;
	}
	
	/** Returns the minimum depth */
	public int getMinDepth(Integer role)
		{ return role==HarmonyConsts.LEFT ? minLeftDepth : minRightDepth; }
	
	/** Returns the maximum depth */
	public int getMaxDepth(Integer role)
		{ return role==HarmonyConsts.LEFT ? maxLeftDepth : maxRightDepth; }

	/** Determines if a path is visible or not */
	public boolean visiblePath(Integer role, TreePath path)
		{ return visibleNode(role, (DefaultMutableTreeNode) path.getLastPathComponent()); }

	/** Determines if a node is visible or not */
	public boolean visibleNode(Integer role, DefaultMutableTreeNode node)
	{
		// Check that the element is within focus
		Object elementID = node.getUserObject();
		if(elementID instanceof Integer && !inFocus(role, (Integer)elementID)) return false;
		
		// Check that the element is within depth
		int depth = node.getPath().length-2;
		int minDepth = role.equals(HarmonyConsts.LEFT) ? minLeftDepth : minRightDepth;
		int maxDepth = role.equals(HarmonyConsts.LEFT) ? maxLeftDepth : maxRightDepth;
		return depth >= minDepth && depth <= maxDepth;
	}
	
	/** Determines if a link should be displayed or not */
	public boolean visibleMappingCell(Integer mappingCellID)
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
		ArrayList<Integer> leftSchemas = getModel().getSelectedInfo().getSchemas(HarmonyConsts.LEFT);
		for(Focus focus : new ArrayList<Focus>(leftFoci))
			if(!leftSchemas.contains(focus.getSchemaID()))
				removeFocus(HarmonyConsts.LEFT, focus);

		// Adjust the right focus as needed
		ArrayList<Integer> rightSchemas = getModel().getSelectedInfo().getSchemas(HarmonyConsts.RIGHT);
		for(Focus focus : new ArrayList<Focus>(rightFoci))
			if(!rightSchemas.contains(focus.getSchemaID()))
				removeFocus(HarmonyConsts.RIGHT, focus);
	}
	
	// Unused event listeners
	public void displayedElementModified(Integer role) {}
	public void selectedElementsModified(Integer role) {}
	public void selectedMappingCellsModified() {}

	/** Inform listeners that the max confidence has changed */
	void fireMaxConfidenceChanged(Integer schemaObjectID)
		{ for(FiltersListener listener : getListeners()) listener.maxConfidenceChanged(schemaObjectID); }
}