// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;

/**
 *  Class for displaying containment hierarchy
 */
public class RMapGraphModel extends GraphModel
{
	/** Returns the graph model name */
	public String getName()
		{ return "RMap"; }
	
	/** Returns the root elements in this graph -- entities at top level*/
	public ArrayList<SchemaElement> getRootElements(HierarchicalGraph graph)
	{
		ArrayList<SchemaElement> rootElements = new ArrayList<SchemaElement>();
	
		for (SchemaElement se : graph.getElements(Entity.class)){
			Entity entity = (Entity)se;
			boolean isRoot = true;
			for (Containment c : graph.getContainments(entity.getId())){
				if (c.getChildID().equals(entity.getId())) {
					isRoot = false;
				}
			}
			if (isRoot)
				rootElements.add(entity);
		}
		
			
		return rootElements;
	}
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(HierarchicalGraph graph, Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If attribute, return entity as parent
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Attribute)
			parentElements.add(graph.getEntity(elementID));
		
		// If an entity, return its super-types as parents.
		if(element instanceof Entity) {
			for ( Containment containment : graph.getContainments(element.getId())){
				if (containment.getChildID().equals(element.getId()))
					parentElements.add(containment);
			}
		}
		
		// If a containment, return the containing element as a parent.
		if(element instanceof Containment)
		{
			Containment containment = (Containment)element;
			Integer parentID = containment.getParentID();
			parentElements.add(graph.getElement(parentID));
		}

		return parentElements;
	}
	
	/** Returns the children elements of the specified element in this graph */
	public ArrayList<SchemaElement> getChildElements(HierarchicalGraph graph, Integer elementID)
	{
		ArrayList<SchemaElement> childElements = new ArrayList<SchemaElement>();
		
		// Produce the list of children elements (only entities have children elements)
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Entity)
		{
			// Retrieve entity attributes		
			for(Attribute value : graph.getAttributes(elementID))
				childElements.add(value);

			// Retrieve containments as children.
			for (Containment containment : graph.getContainments(element.getId()))
				if(elementID.equals(containment.getParentID()) && !containment.getName().equals(""))
					childElements.add(containment);
				
		}

		if (element instanceof Containment){
			childElements.add(graph.getElement(((Containment)element).getChildID()));
		}
			
		return childElements;
		
		
		
	}

	/** Returns the domains of the specified element in this graph */
	public Domain getDomainForElement(HierarchicalGraph graph, Integer elementID)
	{
		
		SchemaElement element = graph.getElement(elementID);
		
		// Find attribute domain values
		if(element instanceof Attribute)
			return (Domain)graph.getElement(((Attribute)element).getDomainID());
		
		return null;
	}		
		
		
	
	/** Returns the elements referenced by the specified domain */
	public ArrayList<SchemaElement> getElementsForDomain(HierarchicalGraph graph, Integer domainID)
	{
		ArrayList<SchemaElement> domainElements = new ArrayList<SchemaElement>();

		// Find all attributes associated with the domain
		for(Attribute attribute : graph.getAttributes(domainID))
			domainElements.add(attribute);
		
		return domainElements;	
		
	}
}