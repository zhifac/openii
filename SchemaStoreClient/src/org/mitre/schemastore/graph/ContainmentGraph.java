// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.graph;

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
		for(SchemaElement element : getSchemaElements(Containment.class))
			if(getSchemaElement(((Containment)element).getParentID())==null)
				rootElements.add(element);
			
		return rootElements;
	}
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(SchemaElement element)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// Find all containments one level higher up the graph
		if(element instanceof Containment)
		{
			Integer elementID = ((Containment)element).getParentID();
			if(elementID!=null)
				for(Containment containment : getContainments(elementID))
					if(containment.getChildID().equals(elementID))
						parentElements.add(containment);
		}
			
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(SchemaElement element)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// Find all containments one level lower on the graph
		if(element instanceof Containment)
		{
			Integer elementID = ((Containment)element).getChildID();
			for(Containment containment : getContainments(elementID))
				if(containment.getParentID().equals(elementID))
					childElements.add(containment);
		}
			
		return childElements;
	}

	/** Returns the domains of the specified element in this graph */
	public Domain getDomain(SchemaElement element)
	{
		// Find the domain attached to this containment
		if(element instanceof Containment)
		{
			Integer elementID = ((Containment)element).getChildID();
			SchemaElement childElement = getSchemaElement(elementID);
			if(childElement instanceof Domain)
				return (Domain)childElement;
		}			
		return null;	
	}
}