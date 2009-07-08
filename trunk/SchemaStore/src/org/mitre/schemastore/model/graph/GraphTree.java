// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;
import java.util.HashMap;

/** Class for representing a graph tree */
public class GraphTree
{
	/** Stores the tree elements */
	private HashMap<Integer, ArrayList<Integer>> treeMap = new HashMap<Integer, ArrayList<Integer>>();
	
	/** Adds child elements to the specified tree element */
	protected void addChildElements(Integer element, ArrayList<Integer> childElements)
		{ treeMap.put(element, childElements); }
	
	/** Returns the child elements for the specified tree element */
	public ArrayList<Integer> getChildElements(Integer element)
	{
		ArrayList<Integer> childElements = treeMap.get(element);
		if(childElements==null) return new ArrayList<Integer>();
		else return childElements;
	}
}