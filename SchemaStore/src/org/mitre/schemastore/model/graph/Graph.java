// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;

/**
 * Class for storing the graph of schema elements 
 * @author MDMORSE, DBURDICK
 */
public class Graph implements Serializable
{
	/** Stores the schema associated with this graph */
	private Schema schema;

	/** Stores the schema elements associated with this graph */
	private HashMap<Integer,SchemaElement> graphHash = new HashMap<Integer,SchemaElement>();
	
	/** Stores graph listeners */
	private ArrayList<GraphListener> listeners = new ArrayList<GraphListener>();
	
	/** Constructs the base graph */
	public Graph(Schema schema, ArrayList<SchemaElement> elements)
	{
		/** Class for defining the proper order for inserting elements */
		class ElementComparator implements Comparator<SchemaElement>
		{
			public int compare(SchemaElement e1, SchemaElement e2)
			{
				if(e1 instanceof Domain) return -1; if(e2 instanceof Domain) return 1;
				if(e1 instanceof DomainValue) return -1; if(e2 instanceof DomainValue) return 1;
				if(e1 instanceof Entity) return -1; if(e2 instanceof Entity) return 1;
				if(e1 instanceof Attribute) return -1; if(e2 instanceof Attribute) return 1;
				if(!(e1 instanceof Alias)) return -1; if(!(e2 instanceof Alias)) return 1;
				return 0;
			}		
		}
		
		// Populates the graph with elements
		this.schema = schema;
		Collections.sort(elements, new ElementComparator());
		for(SchemaElement element : elements)
			addElement(element);
	}

	/** Copy the base graph */
	public Graph(Graph graph)
	{
		this.schema = graph.schema;
		this.graphHash = graph.graphHash;
	}

	/** Returns the schema referenced by this graph */
	public Schema getSchema()
		{ return schema; }
	
	/** Returns the size of the graph */
	public Integer size()
		{ return graphHash.size(); }
	
	/** Returns the specified schema element */
	public SchemaElement getElement(Integer elementID)
		{ return graphHash.get(elementID); }
	
	/** Indicates if the schema contains the specified schema element */
	public boolean containsElement(Integer elementID)
		{ return getElement(elementID)!=null; }
	
	/** Returns the schema elements associated with the specified type */
	public ArrayList<SchemaElement> getElements(Class type)
	{	
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
		
		// Filter out schemas of the specified type
		for(SchemaElement element : graphHash.values())
			if(type==null || type.isInstance(element))
				elements.add(element);
		
		return elements;		
	}

	/** Returns the base schema elements associated with the specified type */
	public ArrayList<SchemaElement> getBaseElements(Class type)
	{
		ArrayList<SchemaElement> baseElements = new ArrayList<SchemaElement>();
		
		// Filter out base schema elements of the specified type
		for(SchemaElement element : getElements(type))
			if(element.getBase().equals(schema.getId()))
				baseElements.add(element);
		
		return baseElements;
	}
	
	/** Returns the entity associated with the specified attribute */
	public Entity getEntity(Integer attributeID)
	{
		Attribute attribute = (Attribute)getElement(attributeID);
		return (Entity)getElement(attribute.getEntityID());
	}
	
	/** Returns the attributes associated with the specified entity */
	public ArrayList<Attribute> getAttributes(Integer elementID)
	{
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
	
		// Identify all attributes associated with the specified schema element
		for(SchemaElement element: getElements(Attribute.class))
		{
			Attribute attribute = (Attribute)element;
			if(attribute.getEntityID().equals(elementID) || attribute.getDomainID().equals(elementID))
				attributes.add(attribute);
		}
		
		return attributes;
	}
	
	/** Returns the containments associated with the specified schema element */
	public ArrayList<Containment> getContainments(Integer elementID)
	{
		ArrayList<Containment> containments = new ArrayList<Containment>();
		
		// Identify all containments associated with the specified schema element
		for(SchemaElement containment : getElements(Containment.class))
		{
			Integer parentID = ((Containment)containment).getParentID();
			Integer childID = ((Containment)containment).getChildID();
			if(parentID.equals(elementID) || childID.equals(elementID))
			   containments.add((Containment)containment);
		}
		
		return containments;
	}
	
	/** Returns the sub-type relationships for a given entity */
	public ArrayList<Subtype> getSubTypes(Integer elementID)
	{
		ArrayList<Subtype> subtypes = new ArrayList<Subtype>();
		
		// Identify all subtype relationships for which the indicated element is the child element.
		for(SchemaElement se : getElements(Subtype.class))
		{
			Subtype subtype = (Subtype)se;
			Integer parentID = subtype.getParentID();
			Integer childID = subtype.getChildID();
			if(parentID.equals(elementID) || childID.equals(elementID))
				subtypes.add(subtype);
		}
		
		return subtypes;
	}
	
	/** Returns the domain values associated with the specified domain */
	public ArrayList<DomainValue> getDomainValuesForDomain(Integer domainID)
	{
		ArrayList<DomainValue> domainValues = new ArrayList<DomainValue>();
		for(SchemaElement domainValue : getElements(DomainValue.class))
			if(((DomainValue)domainValue).getDomainID().equals(domainID))
				domainValues.add((DomainValue)domainValue);
		return domainValues;
	}

	/** Returns the alias associated with the specified element */
	public Alias getAlias(Integer elementID)
	{
		for(SchemaElement element : getElements(Alias.class))
			if(((Alias)element).getElementID().equals(elementID))
				return (Alias)element;
		return null;
	}
	
	/** Adds a list of elements to the graph */
	public boolean addElement(SchemaElement element)
	{
		// Checks to ensure that referenced elements exist
		if(element instanceof Attribute)
		{
			Attribute attribute = (Attribute)element;
			SchemaElement domain = getElement(attribute.getDomainID());
			SchemaElement entity = getElement(attribute.getEntityID());
			if(domain==null || !(domain instanceof Domain)) return false;
			if(entity==null || !(entity instanceof Entity)) return false;
		}
		if(element instanceof DomainValue)
		{
			DomainValue domainValue = (DomainValue)element;
			SchemaElement domain = getElement(domainValue.getDomainID());
			if(domain==null || !(domain instanceof Domain)) return false;
		}
		if(element instanceof Relationship)
		{
			Relationship relationship = (Relationship)element;
			if(getElement(relationship.getLeftID())==null) return false;
			if(getElement(relationship.getRightID())==null) return false;
		}
		if(element instanceof Containment)
		{
			Containment containment = (Containment)element;
			Integer parentID = containment.getParentID();
			if(parentID!=null && getElement(parentID)==null) return false;
			if(getElement(containment.getChildID())==null) return false;
		}
		if(element instanceof Subtype)
		{
			Subtype subtype = (Subtype)element;
			if(getElement(subtype.getParentID())==null) return false;
			if(getElement(subtype.getChildID())==null) return false;
		}
		if(element instanceof Alias)
		{
			Alias alias = (Alias)element;
			if(getElement(alias.getElementID())==null) return false;
		}
		
		// Add element to the graph
		graphHash.put(element.getId(),element);

		// Inform listeners of the added element
		for(GraphListener listener : listeners)
			listener.schemaElementAdded(element);
		
		return true;
	}
	
	/** Removes an element from the graph */
	public boolean deleteElement(Integer elementID)
	{
		// Checks to ensure that element is not referenced elsewhere
		boolean referenced = false;
		for(SchemaElement element : getElements(null))
		{
			if(element instanceof Attribute)
			{
				Attribute attribute = (Attribute)element;
				referenced |= attribute.getDomainID().equals(elementID);
				referenced |= attribute.getEntityID().equals(elementID);
			}
			if(element instanceof DomainValue)
			{
				DomainValue domainValue = (DomainValue)element;
				referenced |= domainValue.getDomainID().equals(elementID);
			}
			if(element instanceof Relationship)
			{
				Relationship relationship = (Relationship)element;
				referenced |= relationship.getLeftID().equals(elementID);
				referenced |= relationship.getRightID().equals(elementID);
			}
			if(element instanceof Containment)
			{
				Containment containment = (Containment)element;
				Integer parentID = containment.getParentID();
				referenced |= parentID!=null && parentID.equals(elementID);
				referenced |= containment.getChildID().equals(elementID);				
			}
			if(element instanceof Subtype)
			{
				Subtype subtype = (Subtype)element;
				referenced |= subtype.getParentID().equals(elementID);
				referenced |= subtype.getChildID().equals(elementID);				
			}
			if(element instanceof Alias)
			{
				Alias alias = (Alias)element;
				referenced |= alias.getElementID().equals(elementID);
			}
			if(referenced) return false;
		}
		
		// Remove element from graph
		SchemaElement element = graphHash.get(elementID);
		graphHash.remove(elementID);
		
		// Inform listeners of the removed element
		for(GraphListener listener : listeners)
			listener.schemaElementRemoved(element);

		return true;
	}
	
	/** Updates the id of an element in the graph */
	public void updateElementID(Integer oldID, Integer newID)
	{
		// Replace all references to old ID with new ID
		for(SchemaElement schemaElement : getElements(null))
		{
			if(schemaElement.getId().equals(oldID)) schemaElement.setId(newID);
			if(schemaElement instanceof Attribute)
			{
				Attribute attribute = (Attribute)schemaElement;
				if(attribute.getDomainID().equals(oldID)) attribute.setDomainID(newID);
				if(attribute.getEntityID().equals(oldID)) attribute.setEntityID(newID);
			}
			if(schemaElement instanceof DomainValue)
			{
				DomainValue domainValue = (DomainValue)schemaElement;
				if(domainValue.getDomainID().equals(oldID)) domainValue.setDomainID(newID);
			}
			if(schemaElement instanceof Relationship)
			{
				Relationship relationship = (Relationship)schemaElement;
				if(relationship.getLeftID().equals(oldID)) relationship.setLeftID(newID);
				if(relationship.getRightID().equals(oldID)) relationship.setRightID(newID);
			}
			if(schemaElement instanceof Containment)
			{
				Containment containment = (Containment)schemaElement;
				Integer parentID = containment.getParentID();
				if(parentID!=null && parentID.equals(oldID)) containment.setParentID(newID);
				if(containment.getChildID().equals(oldID)) containment.setChildID(newID);				
			}
			if(schemaElement instanceof Subtype)
			{
				Subtype subtype = (Subtype)schemaElement;
				if(subtype.getParentID().equals(oldID)) subtype.setParentID(newID);
				if(subtype.getChildID().equals(oldID)) subtype.setChildID(newID);				
			}
			if(schemaElement instanceof Alias)
			{
				Alias alias = (Alias)schemaElement;
				if(alias.getElementID().equals(oldID)) alias.setElementID(newID);
			}
		}
	}
	
	/** Adds a graph listener */
	public void addGraphListener(GraphListener listener)
		{ listeners.add(listener); }
	
	/** Removes a graph listener */
	public void removeGraphListener(GraphListener listener)
		{ listeners.remove(listener); }
}