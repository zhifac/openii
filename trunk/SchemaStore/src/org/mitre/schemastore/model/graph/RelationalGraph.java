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
	public ArrayList<SchemaElement> getParentElements(SchemaElement element)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If attribute, return entity as parent
		if(element instanceof Attribute)
			parentElements.add(getEntity(element.getId()));
		
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(SchemaElement element)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// If entity, return attributes as children
		if(element instanceof Entity)
			for(Attribute value : getAttributes(element.getId()))
				childElements.add(value);

		return childElements;
	}
	
	/** Returns the domains of the specified element in this graph */
	public Domain getDomain(SchemaElement element)
	{
		if(element instanceof Attribute)
			return getDomain(element.getId());
		return null;
	}
}