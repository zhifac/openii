// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph.model;

import java.util.*;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 *  Class for displaying relationship hierarchy
 */
public class GSIPGraphModel extends GraphModel
{
	private HashMap<String, ArrayList<DomainValue>> cachedDomainValues = new HashMap<String, ArrayList<DomainValue>>();
	/** Returns the graph model name */
	public String getName()
		{ return "GSIP"; }
	
	/** Returns the root elements in this graph */
	public ArrayList<SchemaElement> getRootElements(HierarchicalGraph graph)
	{

		ArrayList<SchemaElement> result = orderEntitiesByName(graph.getElements(Entity.class));
		for (SchemaElement schemaElement : graph.getElements(Subtype.class))
		{
			Subtype subtype = (Subtype)schemaElement;
			result.remove(graph.getElement(subtype.getChildID()));
		}
		for (SchemaElement schemaElement : graph.getElements(Containment.class))
		{
			Containment containment = (Containment)schemaElement;
			result.remove(graph.getElement(containment.getChildID()));
		}
		return result;
	}
	
	/** Returns the parent elements of the specified element in this graph */
	public ArrayList<SchemaElement> getParentElements(HierarchicalGraph graph, Integer elementID)
	{
		ArrayList<SchemaElement> parentElements = new ArrayList<SchemaElement>();
		
		// If attribute, return entity as parent
		SchemaElement element = graph.getElement(elementID);
		if(element instanceof Attribute)
			parentElements.add(graph.getEntity(elementID));
		
		if (element instanceof DomainValue) {
			DomainValue domainValue = (DomainValue) element;
			Integer domainID = domainValue.getDomainID();
			for (SchemaElement attribute : graph.getElements(Attribute.class)){
				Integer attributeID = ((Attribute) attribute).getDomainID();
				if (attributeID.intValue() ==domainID.intValue()) {
					parentElements.add(attribute);
				}
			} 
		}
		
		
		// If an entity, return its super-types as parents.
		if(element instanceof Entity) {
			for (Subtype subtype : graph.getSubTypes(element.getId())) {
				Integer parentID = subtype.getParentID();
				if (!elementID.equals(parentID)) {
					parentElements.add(graph.getElement(parentID));
				}
			}
			
			// Retrieve parents via containments.
			for (Containment containment : graph.getContainments(element.getId()))
				// might not need second predicate, copied from RelationalGraphModel
				if(elementID.equals(containment.getChildID()) && !containment.getName().equals(""))
					parentElements.add(graph.getElement(containment.getParentID()));

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

			// Retrieve subtypes as children
			for (Subtype subtype : graph.getSubTypes(element.getId()))
			{
				Integer childID = subtype.getChildID();
				if (!elementID.equals(childID))
					childElements.add(graph.getElement(childID));
			}
			
			// Retrieve children via containments.
			for (Containment containment : graph.getContainments(element.getId()))
				// might not need second predicate, copied from RelationalGraphModel
				if(elementID.equals(containment.getParentID()) && !containment.getName().equals(""))
					childElements.add(graph.getElement(containment.getChildID()));
							
		}
		else if (element instanceof Attribute) {
			// Retrieve entity attributes		
			Attribute att = (Attribute) graph.getElement(elementID);
			if (cachedDomainValues.get(att.getDomainID().toString())==null) {
				cachedDomainValues.put(att.getDomainID().toString(), orderDomainValuesByName(graph.getDomainValuesForElement(elementID)));
			}			
			for(DomainValue domainValue : cachedDomainValues.get(att.getDomainID().toString())) {
				childElements.add(domainValue);
			}		
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
	
	/** Returns the domain values associated with the specified element in this graph */
	@SuppressWarnings("unchecked")
	public ArrayList<DomainValue> orderDomainValuesByName(ArrayList<DomainValue> domainValues)
	{
		Collections.sort(domainValues, byNameComparator);
		return domainValues;
	}
	
	/** Retrieves the attributes for the specified schema element */
	@SuppressWarnings("unchecked")
	private ArrayList<Attribute> orderAttributesByName(ArrayList<Attribute> attributes)
	{
		Collections.sort(attributes, byNameComparator);
		return attributes;
	}
	
	/** Retrieves the attributes for the specified schema element */
	@SuppressWarnings("unchecked")
	private ArrayList<SchemaElement> orderEntitiesByName(ArrayList<SchemaElement> entities)
	{
		Collections.sort(entities, byNameComparator);
		return entities;
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
                return name1.toUpperCase().compareTo(name2.toUpperCase());
    	}
    };
	
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