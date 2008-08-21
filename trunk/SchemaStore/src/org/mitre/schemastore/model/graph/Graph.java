// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Class for storing the graph of schema elements 
 * @author MDMORSE, DBURDICK
 */
public class Graph implements Serializable
{
	/** Stores the schema associated with this graph */
	private Integer schemaID;

	/** Stores the schema elements associated with this graph */
	private HashMap<Integer,SchemaElement> graphHash = new HashMap<Integer,SchemaElement>();
	
	/** Stores graph listeners */
	private ArrayList<GraphListener> listeners = new ArrayList<GraphListener>();
	
	/** Constructs the base graph */
	public Graph(Integer schemaID, ArrayList<SchemaElement> elements)
	{
		this.schemaID = schemaID;
		for(SchemaElement element : elements)
			addElement(element);
	}

	/** Copy the base graph */
	protected Graph(Graph graph)
	{
		this.schemaID = graph.schemaID;
		this.graphHash = graph.graphHash;
	}

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
			if(element.getBase().equals(schemaID))
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
	
	/** Returns the domain associated with the specified domain value */
	public Domain getDomain(Integer domainValueID)
	{
		DomainValue domainValue = (DomainValue)getElement(domainValueID);
		return (Domain)getElement(domainValue.getDomainID());
	}
	
	/** Returns the domain values associated with the specified domain */
	public ArrayList<DomainValue> getDomainValues(Integer domainID)
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
	public void addElement(SchemaElement element)
	{
		// Add element to the graph
		graphHash.put(element.getId(),element);

		// Inform listeners of the added element
		for(GraphListener listener : listeners)
			listener.schemaElementAdded(element);
	}
	
	/** Removes a list of elements from the graph */
	public void deleteElement(Integer schemaID)
	{
		// Remove element from graph
		SchemaElement element = graphHash.get(schemaID);
		graphHash.remove(schemaID);
		
		// Inform listeners of the removed element
		for(GraphListener listener : listeners)
			listener.schemaElementRemoved(element);
	}
	
	/** Adds a graph listener */
	public void addGraphListener(GraphListener listener)
		{ listeners.add(listener); }
	
	/** Removes a graph listener */
	public void removeGraphListener(GraphListener listener)
		{ listeners.remove(listener); }
}