// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph.model;

import java.util.ArrayList;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 *  Class for displaying relationship hierarchy
 */
public class RelationalGraphModel extends GraphModel
{
	/** Returns the graph model name */
	public String getName()
		{ return "Relational"; }
	
	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements(HierarchicalGraph graph)
	{
		ArrayList<SchemaElement> result = graph.getElements(Entity.class);
		for (SchemaElement schemaElement : graph.getElements(Subtype.class))
		{
			Subtype subtype = (Subtype)schemaElement;
			result.remove(graph.getElement(subtype.getChildID()));
		}
		return result;
	}
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(HierarchicalGraph graph, Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If attribute, return entity as parent
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Attribute)
			parentElements.add(graph.getEntity(elementID));
		
		// If an entity, return its super-types as parents.
		if(element instanceof Entity) {
			for (Subtype subtype : graph.getSubTypes(element.getId())) {
				Integer parentID = subtype.getParentID();
				if (!elementID.equals(parentID)) {
					parentElements.add(graph.getElement(parentID));
				}
			}
		}
		
		// If a containment, return the containing element as a parent.
		if(element instanceof Containment)
		{
			Containment containment = (Containment)element;
			Integer parentID = containment.getParentID();
			parentElements.add(graph.getElement(parentID));
		}

		// If element is relationship, return 
		if (element instanceof Relationship)
		{
			parentElements.add(graph.getElement(((Relationship)element).getLeftID()));
			parentElements.add(graph.getElement(((Relationship)element).getRightID()));
		}
		
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(HierarchicalGraph graph, Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// Produce the list of children elements (only entities have children elements)
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Entity)
		{
			// Retrieve entity attributes		
			for(Attribute value : graph.getAttributes(elementID))
				childElements.add(value);
	
			// Retrieve containments as children.
			for (Containment containment : graph.getContainments(element.getId()))
				if(elementID.equals(containment.getParentID()) && !containment.getName().equals(""))
					childElements.add(containment);
			
			// Retrieve relationships as children
			for (Relationship rel : graph.getRelationships(elementID))
				childElements.add(rel);
			
			// Retrieve subtypes as children
			for (Subtype subtype : graph.getSubTypes(element.getId()))
			{
				Integer childID = subtype.getChildID();
				if (!elementID.equals(childID))
					childElements.add(graph.getElement(childID));
			}
		}

		return childElements;
	}
	
	/** Returns the domains of the specified element in this graph */
	public Domain getDomainForElement(HierarchicalGraph graph, Integer elementID)
	{
		SchemaElement element = graph.getElement(elementID);
		
		// Find attribute domain values
		if(element instanceof Attribute)
			return (Domain)graph.getElement(((Attribute)element).getDomainID());

		// Find containment domain values
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

		// Find all attributes associated with the domain
		for(Attribute attribute : graph.getAttributes(domainID))
			domainElements.add(attribute);

		// Find all containments associated with the domain
		for(Containment containment : graph.getContainments(domainID))
			if(containment.getChildID().equals(domainID))
				domainElements.add(containment);
		
		return domainElements;
	}

	/** Returns the type name associated with the specified element (or NULL if element has no name) */
	public SchemaElement getType(HierarchicalGraph graph, Integer elementID)
	{
		SchemaElement element = graph.getElement(elementID);
		SchemaElement childElement = null;
		
		if(element instanceof Containment)
			childElement = graph.getElement(((Containment)element).getChildID());
				
		else if (element instanceof Attribute)
			childElement = graph.getElement(((Attribute)element).getDomainID());
		
		if (childElement != null && childElement.getName() != null && childElement.getName().length() > 0)
			return childElement;

		return null;	
	}
}