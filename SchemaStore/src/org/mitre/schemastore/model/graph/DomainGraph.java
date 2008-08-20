// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;

/**
 *  Class for displaying domain hierarchy
 */
public class DomainGraph extends HierarchicalGraph
{
	/** Constructs the domain graph */
	public DomainGraph(Graph graph)
		{ super(graph); }

	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements()
		{ return getSchemaElements(Domain.class); }
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(SchemaElement element)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If domain value, return domain as parent
		if(element instanceof DomainValue)
			parentElements.add(getDomain(element.getId()));
		
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(SchemaElement element)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// If domain, return domain values as children
		if(element instanceof Domain)
			for(DomainValue value : getDomainValues(element.getId()))
				childElements.add(value);

		return childElements;
	}
	
	/** Returns the domains of the specified element in this graph */
	public Domain getDomain(SchemaElement element)
		{ return null; }
}