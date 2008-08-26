// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;
import java.util.HashSet;

import org.mitre.schemastore.model.SchemaElement;

/** Class for representing a filtered graph */
public class FilteredGraph extends HierarchicalGraph implements GraphListener
{
	/** Stores the filtered root element */
	private ArrayList<Integer> filteredRootIDs = null;
	
	/** Stores the minimum depth */
	private Integer minDepth = null;
	
	/** Stores the maximum depth */
	private Integer maxDepth = null;
	
	/** Stores a listing of all filtered graph elements */
	private HashSet<Integer> filteredElements = null;
	
	/** Fills in the depth hash for use in identifying the filtered elements */
	private void findFilteredElements(Integer elementID, Integer depth)
	{
		// Stop filling depth hash if already above max depth
		if(maxDepth!=null && depth>maxDepth) return;
		
		// Only fill in depth hash if above min depth
		if(minDepth==null || depth>=minDepth)
			filteredElements.add(elementID);
		
		// Proceed to fill in depth hash with child elements
		for(SchemaElement element : getChildElements(elementID))
			if(!filteredElements.contains(element.getId()))
				findFilteredElements(element.getId(),depth+1);
	}
	
	/** Generates the filtered elements */
	private void generateFilteredElements()
	{
		// Identify filtered roots if none given
		if(filteredRootIDs==null)
		{
			filteredRootIDs = new ArrayList<Integer>();
			for(SchemaElement element : getRootElements())
				filteredRootIDs.add(element.getId());
		}
			
		// Find all of the filtered elements
		filteredElements = new HashSet<Integer>();
		for(Integer elementID : filteredRootIDs)
		{
			Integer depth = getDepth(elementID);
			findFilteredElements(elementID,depth);
		}	
	}
	
	/** Constructs the filtered graph */
	public FilteredGraph(HierarchicalGraph graph)
		{ super(graph); }
	
	/** Sets the filtered root */
	public void setFilteredRoot(Integer filteredRoot)
		{ filteredRootIDs = new ArrayList<Integer>(); filteredRootIDs.add(filteredRoot); }
	
	/** Sets the filter minimum depth */
	public void setMinDepth(Integer minDepth)
		{ this.minDepth = minDepth; }

	/** Sets the filter maximum depth */
	public void setMaxDepth(Integer maxDepth)
		{ this.maxDepth = maxDepth; }	

	/** Returns if the specified element is visible */
	public boolean isVisible(Integer elementID)
	{
		if(filteredElements==null) generateFilteredElements();
		return filteredElements.contains(elementID);
	}
	
	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getFilteredRootElements()
	{
		if(filteredRootIDs==null) generateFilteredElements();
		ArrayList<SchemaElement> filteredRoots = new ArrayList<SchemaElement>();
		for(Integer filteredRootID : filteredRootIDs)
			filteredRoots.add(getElement(filteredRootID));
		return filteredRoots;
	}
	
	/** Returns the filtered parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getFilteredParentElements(Integer elementID)
	{
		ArrayList<SchemaElement> filteredParents = new ArrayList<SchemaElement>();
		for(SchemaElement element : getParentElements(elementID))
			if(isVisible(element.getId())) filteredParents.add(element);
		return filteredParents;
	}
	
	/** Returns the filtered children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getFilteredChildElements(Integer elementID)
	{
		ArrayList<SchemaElement> filteredChildren = new ArrayList<SchemaElement>();
		for(SchemaElement element : getParentElements(elementID))
			if(isVisible(element.getId())) filteredChildren.add(element);
		return filteredChildren;		
	}
	
	/** Returns the list of all filtered elements in this graph */
	public ArrayList<SchemaElement> getFilteredElements()
	{
		ArrayList<SchemaElement> filteredElements = new ArrayList<SchemaElement>();
		for(SchemaElement element : getGraphElements())
			if(isVisible(element.getId())) filteredElements.add(element);
		return filteredElements;
	}

	/** Clear the cached filtered elements if the graph has been modified */
	public void schemaElementAdded(SchemaElement schemaElement)
		{ filteredElements = null; filteredRootIDs = null; }

	/** Clears the cached filtered elements if the graph has been modified */
	public void schemaElementRemoved(SchemaElement schemaElement)
		{ filteredElements = null; filteredRootIDs = null; }
}