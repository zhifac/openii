// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph.model;

import java.util.ArrayList;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 *  Class for displaying XML hierarchy
 */
public class XMLnoAttrsGraphModel extends GraphModel
{
	/** Returns the graph model name */
	public String getName()
		{ return "XML-NoAttrs"; }
	
	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements(HierarchicalGraph graph)
	{
		ArrayList<SchemaElement> rootElements = new ArrayList<SchemaElement>();

		// Find all containments whose roots are null
		for(SchemaElement element : graph.getElements(Containment.class))
			if(((Containment)element).getParentID()==null)
				rootElements.add(element);
			
		return rootElements;
	}
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(HierarchicalGraph graph, Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// Identify the parent ID for which containments need to be found
		SchemaElement element = graph.getElement(elementID);
		Integer parentID = null;
		if(element instanceof Containment) parentID = ((Containment)element).getParentID();
		if(element instanceof Attribute) parentID = ((Attribute)element).getEntityID();
		
		// Find all containments one level higher up the graph
		if(parentID!=null)
			for(Containment containment : graph.getContainments(parentID))
				if(containment.getChildID().equals(parentID))
					parentElements.add(containment);
			
		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(HierarchicalGraph graph, Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// Find all containments one level lower on the graph
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Containment)
		{
			Integer childID = ((Containment)element).getChildID();

			if (!(graph.getElement(childID) instanceof Domain)){
			
			
				// Build list of all IDs for super-type entities
				ArrayList<Integer> superTypeIDs = new ArrayList<Integer>();
				ArrayList<Boolean> processedIDs= new ArrayList<Boolean>();
				superTypeIDs.add(childID);
				processedIDs.add(false);
				
				boolean workLeft = true;
				while (workLeft){
					for (int i = 0; i<superTypeIDs.size();i++){
						if (processedIDs.get(i).equals(false)){
							for (Subtype s : graph.getSubTypes(superTypeIDs.get(i))){
								if (s.getChildID().equals(superTypeIDs.get(i))){
									superTypeIDs.add(s.getParentID());
									processedIDs.add(false);
								}
							}
							processedIDs.set(i, true);
						}
					}
					workLeft = false;
					for (int i = 0; i<superTypeIDs.size();i++)
						if (processedIDs.get(i).equals(false))
							workLeft = true;
				}
					
				// Retrieves all containments whose parent is the child ID
				for (Integer id : superTypeIDs)
					for(Containment containment : graph.getContainments(id))
						if(id.equals(containment.getParentID()))
							childElements.add(containment);
			}
		}
			
		return childElements;
	}

	/** Returns the domains of the specified element in this graph */
	public Domain getDomainForElement(HierarchicalGraph graph, Integer elementID)
	{
		// Find the domain attached to this containment
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Containment)
		{
			Integer containmentID = ((Containment)element).getChildID();
			SchemaElement childElement = graph.getElement(containmentID);
			if(childElement instanceof Domain)
				return (Domain)childElement;
		}			
		return null;	
	}
	
	/** Returns the elements referenced by the specified domain */
	public ArrayList<SchemaElement> getElementsForDomain(HierarchicalGraph graph, Integer domainID)
	{
		ArrayList<SchemaElement> domainElements = new ArrayList<SchemaElement>();
		
		// Find all containments that reference this domain
		for(Containment containment : graph.getContainments(domainID))
			if(containment.getChildID().equals(domainID))
				domainElements.add(containment);
			
		return domainElements;		
	}
	
	/** Returns the type name associated with the specified element (or NULL if element has no name) */
	public String getType(HierarchicalGraph graph, Integer elementID)
	{
		SchemaElement element = graph.getElement(elementID);
		SchemaElement childElement = null;
		
		if(element instanceof Containment)
			childElement = graph.getElement(((Containment)element).getChildID());
				
		else if (element instanceof Attribute)
			childElement = graph.getElement(((Attribute)element).getDomainID());
		
		if (childElement != null && childElement.getName() != null && childElement.getName().length() > 0)
			return childElement.getName();

		return null;	
	}
}