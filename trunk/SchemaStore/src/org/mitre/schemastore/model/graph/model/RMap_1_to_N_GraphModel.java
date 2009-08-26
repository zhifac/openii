// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 *  Class for displaying containment hierarchy
 */
public class RMap_1_to_N_GraphModel extends GraphModel
{
	/** Returns the graph model name */
	public String getName()
		{ return "RMap - 1 to N"; }
	
	/** Returns the root elements in this graph -- entities at top level*/
	public ArrayList<SchemaElement> getRootElements(HierarchicalGraph graph)
	{
		ArrayList<SchemaElement> rootElements = new ArrayList<SchemaElement>();
	
		for (SchemaElement se : graph.getElements(Entity.class)){
			Entity entity = (Entity)se;
			boolean isRoot = true;
			for (Relationship rel : orderRelationshipsByName(graph.getRelationships(entity.getId()))){
				// convention:  LEFT --> FK --> RIGHT
				Integer relFKSourceId = rel.getLeftID();
				if ((rel.getRightMax() == null && rel.getRightMax() != 1) 
						&& (rel.getLeftMax() != null && rel.getLeftMax() == 1))
					relFKSourceId = rel.getRightID();
			
				if (relFKSourceId.equals(entity.getId())) 
					isRoot = false;
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
		
		if(element instanceof Entity) {
			for ( Relationship rel : orderRelationshipsByName(graph.getRelationships(element.getId()))){
				
				Integer relFKSourceId = rel.getLeftID();
				if ((rel.getRightMax() == null && rel.getRightMax() != 1) 
						&& (rel.getLeftMax() != null && rel.getLeftMax() == 1))
					relFKSourceId = rel.getRightID();
				if (relFKSourceId.equals(element.getId()))
					parentElements.add(rel);
			}
		}
		
		if(element instanceof Relationship) {
			Relationship rel = (Relationship)element;
			Integer relFKTargetId = rel.getRightID();
			if (rel.getRightMax() == null || rel.getRightMax() != 1) 
				relFKTargetId = rel.getLeftID();
			parentElements.add(graph.getElement(relFKTargetId));
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
			for(Attribute value : orderAttributesByName(graph.getAttributes(elementID)))
				childElements.add(value);
			
			// Retrieve FK relationships as children.
			for (Relationship rel : orderRelationshipsByName(graph.getRelationships(element.getId()))){
				Integer relFKTargetId = rel.getRightID();
				if (rel.getRightMax() == null || rel.getRightMax() != 1) 
					relFKTargetId = rel.getLeftID();
				if(elementID.equals(relFKTargetId))
					childElements.add(rel);
			}
				
		}

		if (element instanceof Relationship){
			Relationship rel = (Relationship)element;
			Integer relFKSourceId = rel.getLeftID();
			if ((rel.getRightMax() == null && rel.getRightMax() != 1) 
					&& (rel.getLeftMax() != null && rel.getLeftMax() == 1))
				relFKSourceId = rel.getRightID();
			childElements.add(graph.getElement(relFKSourceId));
			
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
		for(Attribute attribute : orderAttributesByName(graph.getAttributes(domainID)))
			domainElements.add(attribute);
		
		return domainElements;	
		
	}
	
	/** Returns the type name associated with the specified element (or NULL if element has no name) */
	public SchemaElement getType(HierarchicalGraph graph, Integer elementID)
		{ return null; }
	
	/** Retrieves the attributes for the specified schema element */
	@SuppressWarnings("unchecked")
	private ArrayList<Attribute> orderAttributesByName(ArrayList<Attribute> attributes)
	{
		Collections.sort(attributes, byNameComparator);
		return attributes;
	}
	

	
	/** Retrieves the attributes for the specified schema element */
	@SuppressWarnings("unchecked")
	private ArrayList<Relationship> orderRelationshipsByName(ArrayList<Relationship> relationships)
	{
		Collections.sort(relationships, byNameComparator);
		return relationships;
	}
	
    public Comparator byNameComparator = new Comparator() {
    	public int compare(Object o1, Object o2) {
                SchemaElement item1 = (SchemaElement) o1;
                SchemaElement item2 = (SchemaElement) o2;
                
                if (item1 == null && item2 == null) { return 0; }
                else if (item2 == null) { return 1; }
                else if (item1 == null) { return -1; }
                
                String name1 = item1.getName();
                String name2 = item2.getName();
                
                if (name1 == null) { name1 = ""; }
                if (name2 == null) { name2 = ""; }
                if (name1 == null || name2 == null) System.out.println("name was null");
                return name1.toUpperCase().compareTo(name2.toUpperCase());
    	}
    };
	
	
}