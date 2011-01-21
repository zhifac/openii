// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.filters;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.view.schemaTree.SchemaTree;

/**
 * Keeps an updated listing of the items in focus
 * @author CWOLF
 */
class FocusHashTable implements FiltersListener
{
	/** Defines the private node hash class */
	private class NodeHash extends HashMap<DefaultMutableTreeNode,Boolean> {};
	
	// Stores all focus filter settings
	private ArrayList<Focus> leftFoci = new ArrayList<Focus>();
	private ArrayList<Focus> rightFoci = new ArrayList<Focus>();
	
	// Stores a hash of nodes and if they are in focus
	private NodeHash leftNodeHash = new NodeHash();
	private NodeHash rightNodeHash = new NodeHash();

	/** Return the list of foci on the specified side */
	ArrayList<Focus> getFoci(Integer side)
		{ return side==HarmonyConsts.LEFT ? leftFoci : rightFoci; }
	
	/** Returns the number of focused elements on the specified side */
	private Integer getFocusCount(Integer side)
	{
		Integer count = 0;
		for(Focus focus : getFoci(side)) count += focus.getFocusedPaths().size();
		return count;
	}
	
	/** Returns the focus associated with the specified schema */
	Focus getFocus(Integer side, Integer schemaID)
	{
		for(Focus focus : getFoci(side))
			if(focus.getSchemaID().equals(schemaID)) return focus;
		return null;
	}
	
	/** Indicates if the specified schema is in focus */
	boolean inFocus(Integer side, Integer schemaID)
	{
		Focus focus = getFocus(side, schemaID);
		if(focus==null || focus.getFocusedPaths().size()==0) return getFocusCount(side)==0;
		return true;
	}
	
	/** Indicates if the specified element is in focus */
	boolean inFocus(Integer side, Integer schemaID, Integer elementID)
	{
		if(!inFocus(side,schemaID)) return false;
		Focus focus = getFocus(side, schemaID);
		return focus==null || focus.contains(elementID);
	}
	
	/** Indicates if the specified node is in focus */
	boolean inFocus(Integer side, DefaultMutableTreeNode node)
	{
		// Check hash first for information
		NodeHash nodeHash = side==HarmonyConsts.LEFT ? leftNodeHash : rightNodeHash;
		Boolean inFocus = nodeHash.get(node);
		if(inFocus!=null) return inFocus;
		
		// Retrieve information manually
		Integer schemaID = SchemaTree.getSchema(node);
		if(!inFocus(side,schemaID)) return false;
		Focus focus = getFocus(side, schemaID);
		inFocus = focus==null || focus.contains(node);
		
		// Store information in hash before returning
		nodeHash.put(node,inFocus);
		return inFocus;
	}
	
	/** Handles a change in focus */
	public void focusChanged(Integer side)
	{
		if(side==HarmonyConsts.LEFT) leftNodeHash.clear();
		else rightNodeHash.clear();
	}
	
	// Unused event listeners
	public void filterChanged(Integer filter) {}
	public void confidenceChanged() {}
	public void depthChanged(Integer side) {}
	public void maxConfidenceChanged(Integer schemaObjectID) {}
}