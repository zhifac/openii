// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.SchemaElement;

/**
 *  Class for displaying containment hierarchy
 */
public class ContainmentGraphModel implements GraphModel
{
	/** Returns the graph model name */
	public String getName()
		{ return "Containment Model"; }
	
	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements(HierarchicalGraph graph)
	{
		ArrayList<SchemaElement> rootElements = new ArrayList<SchemaElement>();

		// Find all containments whose roots are null
		for(SchemaElement element : graph.getElements(Containment.class))
			if(((Containment)element).getParentID()==null)
				rootElements.add(element);
			
		return rootElements;
	}
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(HierarchicalGraph graph, Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// Find all containments one level higher up the graph
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Containment)
		{
			Integer containmentID = ((Containment)element).getParentID();
			if(containmentID!=null)
				for(Containment containment : graph.getContainments(containmentID))
					if(containment.getChildID().equals(containmentID))
						parentElements.add(containment);
		}
			
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(HierarchicalGraph graph, Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// Find all containments one level lower on the graph
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Containment)
		{
			Integer containmentID = ((Containment)element).getChildID();
			for(Containment containment : graph.getContainments(containmentID))
				if(containmentID.equals(containment.getParentID()))
					childElements.add(containment);
		}
			
		return childElements;
	}

	/** Returns the domains of the specified element in this graph */
	public Domain getDomainForElement(HierarchicalGraph graph, Integer elementID)
	{
		// Find the domain attached to this containment
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Containment)
		{
			Integer containmentID = ((Containment)element).getChildID();
			SchemaElement childElement = graph.getElement(containmentID);
			if(childElement instanceof Domain)
				return (Domain)childElement;
		}			
		return null;	
	}
	
	/** Returns the elements referenced by the specified domain */
	public ArrayList<SchemaElement> getElementsForDomain(HierarchicalGraph graph, Integer domainID)
	{
		ArrayList<SchemaElement> domainElements = new ArrayList<SchemaElement>();
		
		// Find all containments that reference this domain
		for(Containment containment : graph.getContainments(domainID))
			if(containment.getChildID().equals(domainID))
				domainElements.add(containment);
			
		return domainElements;		
	}
}