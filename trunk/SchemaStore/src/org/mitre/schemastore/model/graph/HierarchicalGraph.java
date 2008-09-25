// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;
import java.util.HashSet;

import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Class for generating graph hierarchy 
 */
public class HierarchicalGraph extends Graph
{
	/** Stores the current model being used to interpret the graph */
	private GraphModel model;
	
	/** Constructs the abstract graph */
	public HierarchicalGraph(Graph graph)
	{
		super(graph);
		
		// Determine the makeup of the schema
		Integer totalCount=0, domainCount=0, containmentCount=0;
		for(SchemaElement element : getElements(null))
		{
			if(element instanceof Domain || element instanceof DomainValue) domainCount++;
			if(element instanceof Containment && ((Containment)element).getName().length()>0)
				containmentCount++;
			totalCount++;
		}
		
		// Identify which model to use
//		if(domainCount.equals(totalCount)) model = new DomainGraphModel(this);
//		else if(containmentCount>0) model = new ContainmentGraphModel(this);
//		else model = new RelationalGraphModel(this);
		model = new RelationalGraphModelWithContainments(this);
	}

	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements()
		{ return model.getRootElements(); }
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(Integer elementID)
		{ return model.getParentElements(elementID); }
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(Integer elementID)
		{ return model.getChildElements(elementID); }
	
	/** Returns the domain of the specified element in this graph */
	public Domain getDomainForElement(Integer elementID)
		{ return model.getDomainForElement(elementID); }

	/** Returns the elements referenced by the specified domain */
	public ArrayList<SchemaElement> getElementsForDomain(Integer domainID)
		{ return model.getElementsForDomain(domainID); }
	
	/** Returns the domain values associated with the specified element in this graph */
	public ArrayList<DomainValue> getDomainValuesForElement(Integer elementID)
	{
		ArrayList<DomainValue> domainValues = new ArrayList<DomainValue>();
		Domain domain = getDomainForElement(elementID);
		if(domain!=null)
			domainValues.addAll(getDomainValuesForDomain(domain.getId()));
		return domainValues;
	}
	
	/** Returns the list of all elements in this graph */
	public ArrayList<SchemaElement> getGraphElements()
	{
		// Construct the list of root elements
		HashSet<Integer> usedElements = new HashSet<Integer>();
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>(getRootElements());
		for(SchemaElement element : elements)
			usedElements.add(element.getId());

		// Follow all graph paths
		for(int i=0; i<elements.size(); i++)
			for(SchemaElement childElement : getChildElements(elements.get(i).getId()))
				if(!usedElements.contains(childElement.getId()))
				{
					elements.add(childElement);
					usedElements.add(childElement.getId());
				}
		
		// Return the list of all elements in this graph
		return elements;
	}

	/** Returns the shortest path from the root element to the partially built path */
	private ArrayList<SchemaElement> getPath(ArrayList<SchemaElement> partialPath)
	{
		ArrayList<SchemaElement> path = null;
		ArrayList<SchemaElement> parentElements = getParentElements(partialPath.get(0).getId());
		
		// Handles case where root element has been reached
		if(parentElements.size()==0)
			path = partialPath;
		
		// Handles case where root element is still deeper
		for(SchemaElement element : parentElements)
			if(!partialPath.contains(element))
			{
				partialPath.add(0,element);
				ArrayList<SchemaElement> possPath = getPath(partialPath);
				if(path==null || (possPath!=null && possPath.size()<path.size()))
					path = possPath;
			}
		return path;
	}
	
	/** Returns the shortest path from the root element to the specified element */
	public ArrayList<SchemaElement> getPath(Integer elementID)
	{
		ArrayList<SchemaElement> partialPath = new ArrayList<SchemaElement>();
		partialPath.add(getElement(elementID));
		return getPath(partialPath);
	}
	
	/** Returns the depth of the specified element in the graph */
	public Integer getDepth(Integer element)
		{ return getPath(element).size(); }
}