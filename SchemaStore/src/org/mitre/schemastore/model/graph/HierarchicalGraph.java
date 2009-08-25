// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.SchemaElement;

import org.mitre.schemastore.model.graph.model.*;

/**
 * Class for generating graph hierarchy 
 */
public class HierarchicalGraph extends Graph
{
	// Pattern used to extract graph models
	static private Pattern graphModelPattern = Pattern.compile("<graphModel>(.*?)</graphModel>");
	
	/** Stores the list of available graph models */
	static private ArrayList<GraphModel> graphModels = null;
	
	/** Stores the current model being used to interpret the graph */
	private GraphModel model;
	
	/** Initializes the graph models */
	static private void initGraphModels()
	{
		graphModels = new ArrayList<GraphModel>();
		
		// Retrieve graph models from file
		try {			
			// Pull the entire file into a string
			InputStream configStream = HierarchicalGraph.class.getResourceAsStream("/graphmodels.xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(configStream));
			StringBuffer buffer = new StringBuffer("");
			String line; while((line=in.readLine())!=null) buffer.append(line);
			in.close();
			
			// Parse out the graph models
			Matcher graphModelMatcher = graphModelPattern.matcher(buffer);
			while(graphModelMatcher.find())
				try {
					GraphModel graphModel = (GraphModel)Class.forName(graphModelMatcher.group(1)).newInstance();
					graphModels.add(graphModel);
				} catch(Exception e) { System.out.println(e.getMessage()); }
		}
		catch(IOException e)
			{ System.out.println("(E)HierarchicalGraph - schemastore.xml has failed to load!\n"+e.getMessage()); }
	}

	/** Constructs the hierarchical graph with the specified model */
	public HierarchicalGraph(Graph graph, GraphModel model)
		{ super(graph); setModel(model); }
	
	/** Get the available list of graph models */
	static public ArrayList<GraphModel> getGraphModels()
	{
		if(graphModels==null) initGraphModels();
		return graphModels;
	}
	
	/** Get the current graph model */
	public GraphModel getModel()
		{ return model; }
	
	/** Sets the graph model */
	public void setModel(GraphModel model)
	{
		// If no model given, automatically determine the default graph model
		if(model==null)
		{
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
			if(domainCount.equals(totalCount)) model = new DomainGraphModel();
			else if(containmentCount>0) model = new XMLGraphModel();
			else model = new RelationalGraphModel();
		}
		this.model = model;
	}
	
	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements()
		{ return model.getRootElements(this); }
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(Integer elementID)
		{ return model.getParentElements(this,elementID); }
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(Integer elementID)
		{ return model.getChildElements(this,elementID); }

	/** Adds children to the graph tree */
	public ArrayList<SchemaElement> getChildElements(Integer elementID, HashSet<Integer> pathIDs)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		for(SchemaElement childElement : getChildElements(elementID))
		{
			// Don't add children if element already in branch
			if(pathIDs.contains(childElement.getId())) continue;
			
			// Don't add children if type already in branch
			SchemaElement type = getModel().getType(this, childElement.getId());
			if(type!=null)
			{
				boolean duplicatedType = false;
				for(Integer pathID : pathIDs)
					if(type.equals(getModel().getType(this, pathID)))
						{ duplicatedType = true; break; }
				if(duplicatedType) continue;
			}
				
			// Adds the child element to the list of children elements
			childElements.add(childElement);
		}
			
		// Retrieve child elements from the hierarchical graph
		return childElements;
	}
	
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
		{ return model.getDomainForElement(this,elementID); }

	/** Returns the elements referenced by the specified domain */
	public ArrayList<SchemaElement> getElementsForDomain(Integer domainID)
		{ return model.getElementsForDomain(this,domainID); }
	
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
	
	/** Returns the various IDs associated with the specified partial path */
	private ArrayList<Integer> getPathIDs(Integer elementID, List<String> path)
	{
		ArrayList<Integer> pathIDs = new ArrayList<Integer>();
		
		// Get name information
		String elementName = getElement(elementID).getName();
		String displayedName = getDisplayName(elementID);
		String pathName = path.get(0);

		// Check to see if element in partial path
		if(elementName.equalsIgnoreCase(pathName) || displayedName.equalsIgnoreCase(pathName))
		{
			if(path.size()>1)
			{
				List<String> subPath = path.subList(1, path.size());
				for(SchemaElement element : getChildElements(elementID))
					pathIDs.addAll(getPathIDs(element.getId(), subPath));
			}
			else pathIDs.add(elementID);
		}
		
		return pathIDs;
	}
	
	/** Returns the various IDs associated with the specified path */
	public ArrayList<Integer> getPathIDs(ArrayList<String> path)
	{
		ArrayList<Integer> pathIDs = new ArrayList<Integer>();
		for(SchemaElement element : getRootElements())
			pathIDs.addAll(getPathIDs(element.getId(),path));
		return pathIDs;
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