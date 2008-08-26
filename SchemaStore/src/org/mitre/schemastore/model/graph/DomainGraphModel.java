// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;

/**
 *  Class for displaying domain hierarchy
 */
public class DomainGraphModel extends GraphModel
{
	/** Constructs the domain graph */
	public DomainGraphModel(HierarchicalGraph graph)
		{ super(graph); }

	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements()
		{ return graph.getElements(Domain.class); }
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If domain value, return domain as parent
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof DomainValue)
			parentElements.add((Domain)graph.getElement(((DomainValue)element).getDomainID()));
		
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(Integer elementID)
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
	public Domain getDomainForElement(Integer elementID)
		{ return null; }
	
	/** Returns the elements referenced by the specified domain */
	public ArrayList<SchemaElement> getElementsForDomain(Integer domainID)
		{ return new ArrayList<SchemaElement>(); }
}