// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.SchemaElement;

/**
 *  Class for displaying XML hierarchy
 */
public class XMLGraphModel extends GraphModel
{
	/** Returns the graph model name */
	public String getName()
		{ return "XML"; }
	
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
		
		// Identify the parent ID for which containments need to be found
		SchemaElement element = graph.getElement(elementID);
		Integer parentID = null;
		if(element instanceof Containment) parentID = ((Containment)element).getParentID();
		if(element instanceof Attribute) parentID = ((Attribute)element).getEntityID();
		
		// Find all containments one level higher up the graph
		if(parentID!=null)
			for(Containment containment : graph.getContainments(parentID))
				if(containment.getChildID().equals(parentID))
					parentElements.add(containment);
			
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
			Integer childID = ((Containment)element).getChildID();

			// Retrieves all containments whose parent is the child ID
			for(Containment containment : graph.getContainments(childID))
				if(childID.equals(containment.getParentID()))
					childElements.add(containment);

			// Retrieves all attributes whose element is the child ID
			for(Attribute attribute : graph.getAttributes(childID))
				childElements.add(attribute);
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
	
	/** Returns the type name associated with the specified element (or NULL if element has no name) */
	public String getType(HierarchicalGraph graph, Integer elementID)
	{
		SchemaElement element = graph.getElement(elementID);
		SchemaElement childElement = null;
		
		if(element instanceof Containment)
			childElement = graph.getElement(((Containment)element).getChildID());
				
		else if (element instanceof Attribute)
			childElement = graph.getElement(((Attribute)element).getDomainID());
		
		if (childElement != null && childElement.getName() != null && childElement.getName().length() > 0)
			return childElement.getName();

		return null;	
	}
}