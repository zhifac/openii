// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;

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
		{ return graph.getElements(Entity.class); }
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If attribute, return entity as parent
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Attribute)
			parentElements.add(graph.getEntity(elementID));
		
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// If entity, return attributes as children
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Entity)
			for(Attribute value : graph.getAttributes(elementID))
				childElements.add(value);

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