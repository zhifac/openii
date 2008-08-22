// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.SchemaElement;

/**
 *  Class for displaying containment hierarchy
 */
public class ContainmentGraph extends HierarchicalGraph
{
	/** Constructs the containment graph */
	public ContainmentGraph(Graph graph)
		{ super(graph); }

	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements()
	{
		ArrayList<SchemaElement> rootElements = new ArrayList<SchemaElement>();

		// Find all containments whose root is null
		for(SchemaElement element : getElements(Containment.class))
			if(getElement(((Containment)element).getParentID())==null)
				rootElements.add(element);
			
		return rootElements;
	}
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// Find all containments one level higher up the graph
		SchemaElement element = getElement(elementID);
		if(element instanceof Containment)
		{
			Integer containmentID = ((Containment)element).getParentID();
			if(containmentID!=null)
				for(Containment containment : getContainments(containmentID))
					if(containment.getChildID().equals(containmentID))
						parentElements.add(containment);
		}
			
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// Find all containments one level lower on the graph
		SchemaElement element = getElement(elementID);
		if(element instanceof Containment)
		{
			Integer containmentID = ((Containment)element).getChildID();
			for(Containment containment : getContainments(containmentID))
				if(containment.getParentID().equals(containmentID))
					childElements.add(containment);
		}
			
		return childElements;
	}

	/** Returns the domains of the specified element in this graph */
	public Domain getDomainForElement(Integer elementID)
	{
		// Find the domain attached to this containment
		SchemaElement element = getElement(elementID);
		if(element instanceof Containment)
		{
			Integer containmentID = ((Containment)element).getChildID();
			SchemaElement childElement = getElement(containmentID);
			if(childElement instanceof Domain)
				return (Domain)childElement;
		}			
		return null;	
	}
}