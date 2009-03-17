// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
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
			for (Relationship rel : graph.getRelationships(entity.getId())){
				if (rel.getRightID().equals(entity.getId())) {
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
		
		SchemaElement element = graph.getElement(elementID);
		
		// If attribute, return entity as parent
		if(element instanceof Attribute)
			parentElements.add(graph.getEntity(elementID));
		
		// If an entity, return its super-types as parents.
		if(element instanceof Entity) {
			for ( Relationship rel : graph.getRelationships(element.getId())){
				if (rel.getRightID().equals(element.getId()))
					parentElements.add(rel);
			}
		}
		
		if(element instanceof Relationship) {
			parentElements.add(graph.getElement(((Relationship)element).getLeftID()));
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

			// Retrieve FK relationships as children.
			for (Relationship rel : graph.getRelationships(element.getId()))
				if(elementID.equals(rel.getLeftID()))
					childElements.add(rel);
				
		}

		if (element instanceof Relationship){
			childElements.add(graph.getElement(((Relationship)element).getRightID()));
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