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
public class RelationalGraph extends HierarchicalGraph
{
	/** Constructs the relational graph */
	public RelationalGraph(Graph graph)
		{ super(graph); }

	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements()
		{ return getElements(Entity.class); }
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If attribute, return entity as parent
		SchemaElement element = getElement(elementID);
		if(element instanceof Attribute)
			parentElements.add(getEntity(elementID));
		
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// If entity, return attributes as children
		SchemaElement element = getElement(elementID);
		if(element instanceof Entity)
			for(Attribute value : getAttributes(elementID))
				childElements.add(value);

		return childElements;
	}
	
	/** Returns the domains of the specified element in this graph */
	public Domain getDomainForElement(Integer elementID)
	{
		SchemaElement element = getElement(elementID);
		if(element instanceof Attribute)
			return (Domain)getElement(((Attribute)element).getDomainID());
		return null;
	}
}