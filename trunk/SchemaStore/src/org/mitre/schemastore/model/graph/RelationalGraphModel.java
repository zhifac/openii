// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;

/**
 *  Class for displaying relationship hierarchy
 */
public class RelationalGraphModel extends GraphModel
{
	/** Constructs the relational graph */
	public RelationalGraphModel(HierarchicalGraph graph)
		{ super(graph); }

	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements()
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
	// PMORK: Allow an entity to return a list of its parents.
	public ArrayList<SchemaElement> getParentElements(Integer elementID)
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

		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	// PMORK: Allow an entity to return a list of its children.
	public ArrayList<SchemaElement> getChildElements(Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// Produce the list of children elements (only entities have children elements)
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Entity)
		{
			// Retrieve entity attributes		
			for(Attribute value : graph.getAttributes(elementID))
				childElements.add(value);

			// Retrieve subtypes as children
			for (Subtype subtype : graph.getSubTypes(element.getId()))
			{
				Integer childID = subtype.getChildID();
				if (!elementID.equals(childID))
					childElements.add(graph.getElement(childID));
			}

			// Retrieve containments as children.
			for (Containment containment : graph.getContainments(element.getId()))
			{
				Integer childID = containment.getChildID();
				if(!elementID.equals(childID) && !containment.getName().equals(""))
					childElements.add(containment);
			}
		}

		return childElements;
	}
	
	/** Returns the domains of the specified element in this graph */
	public Domain getDomainForElement(Integer elementID)
	{
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Attribute)
			return (Domain)graph.getElement(((Attribute)element).getDomainID());
		return null;
	}

	/** Returns the elements referenced by the specified domain */
	public ArrayList<SchemaElement> getElementsForDomain(Integer domainID)
	{
		ArrayList<SchemaElement> domainElements = new ArrayList<SchemaElement>();
		for(Attribute attribute : graph.getAttributes(domainID))
			domainElements.add(attribute);
		return domainElements;
	}
}