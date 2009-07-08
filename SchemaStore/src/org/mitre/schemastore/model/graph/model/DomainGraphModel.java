// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph.model;

import java.util.ArrayList;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 *  Class for displaying domain hierarchy
 */
public class DomainGraphModel extends GraphModel
{
	/** Returns the graph model name */
	public String getName()
		{ return "Domain"; }
	
	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements(HierarchicalGraph graph)
		{ return graph.getElements(Domain.class); }
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(HierarchicalGraph graph, Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If domain value, return domain as parent
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof DomainValue)
			parentElements.add((Domain)graph.getElement(((DomainValue)element).getDomainID()));
		
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(HierarchicalGraph graph, Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// If domain, return domain values as children
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Domain)
			for(DomainValue value : graph.getDomainValuesForDomain(elementID))
				childElements.add(value);

		return childElements;
	}
	
	/** Returns the domains of the specified element in this graph */
	public Domain getDomainForElement(HierarchicalGraph graph, Integer elementID)
		{ return null; }
	
	/** Returns the elements referenced by the specified domain */
	public ArrayList<SchemaElement> getElementsForDomain(HierarchicalGraph graph, Integer domainID)
		{ return new ArrayList<SchemaElement>(); }
	
	/** Returns the type name associated with the specified element (or NULL if element has no name) */
	public SchemaElement getType(HierarchicalGraph graph, Integer elementID)
		{ return null; }
}