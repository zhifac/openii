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
		if(domainCount.equals(totalCount)) model = new DomainGraphModel(this);
		else if(containmentCount>0) model = new ContainmentGraphModel(this);
		else model = new RelationalGraphModel(this);
	}

	/** Sets the graph model */
	public void setModel(GraphModel model)
		{ this.model = model; }
	
	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements()
		{ return model.getRootElements(); }
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(Integer elementID)
		{ return model.getParentElements(elementID); }
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(Integer elementID)
		{ return model.getChildElements(elementID); }
	
	/** Returns the ancestors of the specified element in this graph */
	private void getAncestorElements(Integer elementID, HashSet<SchemaElement> ancestors)
	{
		for(SchemaElement parentElement : getParentElements(elementID))
			if(!ancestors.contains(parentElement))
			{
				ancestors.add(parentElement);
				getAncestorElements(parentElement.getId(),ancestors);
			}
	}
	
	/** Returns the ancestors of the specified element in this graph */
	public ArrayList<SchemaElement> getAncestorElements(Integer elementID)
	{
		HashSet<SchemaElement> ancestors = new HashSet<SchemaElement>();
		getAncestorElements(elementID,ancestors);
		return new ArrayList<SchemaElement>(ancestors);
	}

	/** Returns the descendants of the specified element in this graph */
	private void getDescendantElements(Integer elementID, HashSet<SchemaElement> descendants)
	{
		for(SchemaElement childElement : getChildElements(elementID))
			if(!descendants.contains(childElement))
			{
				descendants.add(childElement);
				getDescendantElements(childElement.getId(),descendants);
			}
	}
	
	/** Returns the descendants of the specified element in this graph */
	public ArrayList<SchemaElement> getDescendantElements(Integer elementID)
	{
		HashSet<SchemaElement> descendants = new HashSet<SchemaElement>();
		getDescendantElements(elementID,descendants);
		return new ArrayList<SchemaElement>(descendants);		
	}
	
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

	/** Returns the paths from the root element to the partially built path */
	private ArrayList<ArrayList<SchemaElement>> getPaths(ArrayList<SchemaElement> partialPath)
	{
		ArrayList<ArrayList<SchemaElement>> paths = new ArrayList<ArrayList<SchemaElement>>();
		ArrayList<SchemaElement> parentElements = getParentElements(partialPath.get(0).getId());
		
		// Handles case where root element has been reached
		if(parentElements.size()==0)
			paths.add(new ArrayList<SchemaElement>(partialPath));
		
		// Handles case where root element is still deeper
		for(SchemaElement element : parentElements)
			if(!partialPath.contains(element))
			{
				partialPath.add(0,element);
				paths.addAll(getPaths(partialPath));
				partialPath.remove(0);
			}
		return paths;
	}
	
	/** Returns the various paths from the root element to the specified element */
	public ArrayList<ArrayList<SchemaElement>> getPaths(Integer elementID)
	{
		// Don't proceed if element doesn't exist in the graph
		if(getElement(elementID)==null)
			return new ArrayList<ArrayList<SchemaElement>>();
		
		// Retrieve all of the paths associated with the specified schema element
		ArrayList<SchemaElement> partialPath = new ArrayList<SchemaElement>();
		partialPath.add(getElement(elementID));
		return getPaths(partialPath);
	}
	
	/** Returns the various depths of the specified element in the graph */
	public ArrayList<Integer> getDepths(Integer elementID)
	{
		HashSet<Integer> depths = new HashSet<Integer>();
		for(ArrayList<SchemaElement> paths : getPaths(elementID))
			depths.add(paths.size());
		return new ArrayList<Integer>(depths);
	}
}
