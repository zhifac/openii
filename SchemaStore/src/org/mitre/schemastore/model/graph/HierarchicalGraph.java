// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;
import java.util.HashSet;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Abstract class for generating graph hierarchy 
 */
abstract public class HierarchicalGraph extends Graph
{
	/** Constructs the abstract graph */
	public HierarchicalGraph(Graph graph)
		{ super(graph); }

	/** Returns the root elements in this graph */
	abstract public ArrayList<SchemaElement> getRootElements();
	
	/** Returns the parent elements of the specified element in this graph */
	abstract public ArrayList<SchemaElement> getParentElements(Integer elementID);
	
	/** Returns the children elements of the specified element in this graph */
	abstract public ArrayList<SchemaElement> getChildElements(Integer elementID);
	
	/** Returns the domain of the specified element in this graph */
	abstract public Domain getDomainForElement(Integer elementID);

	/** Returns the domain values associated with the specified element in this graph */
	public ArrayList<DomainValue> getDomainValuesForElement(Integer elementID)
	{
		ArrayList<DomainValue> domainValues = new ArrayList<DomainValue>();
		Domain domain = getDomainForElement(elementID);
		if(domain!=null)
			domainValues.addAll(getDomainValuesForDomain(domain.getId()));
		return domainValues;
	}
	
	/** Private class for displaying elements */
	private StringBuffer displayElement(SchemaElement schemaElement, int depth, HashSet<Integer> displayedElements)
	{
		StringBuffer out = new StringBuffer();

		// Prevent cycles from occurring
		if(!displayedElements.contains(schemaElement.getId()))
		{
			displayedElements.add(schemaElement.getId());
			for(int i=0; i<depth; i++) out.append("  ");
			out.append(schemaElement.getName() + "("+schemaElement.getId()+")\n");
			for(SchemaElement childElement : getChildElements(schemaElement.getId()))
				out.append(displayElement(childElement,depth+1,displayedElements));
		}
		
		return out;
	}
	
	/** Outputs the graph */
	public String toString()
	{		
		StringBuffer out = new StringBuffer();
		HashSet<Integer> displayedElements = new HashSet<Integer>();
		for(SchemaElement schemaElement : getRootElements())
			out.append(displayElement(schemaElement,0,displayedElements));
		return out.toString();
	}
}