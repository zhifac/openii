// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

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
public class Graph
{
	/** Stores the schema associated with this graph */
	private Integer schemaID;

	/** Stores the schema elements associated with this graph */
	private HashMap<Integer,SchemaElement> graphHash = new HashMap<Integer,SchemaElement>();
	
	/** Stores graph listeners */
	private ArrayList<GraphListener> listeners = new ArrayList<GraphListener>();
	
	/** Constructs the base graph */
	public Graph(Integer schemaID, ArrayList<SchemaElement> schemaElements)
	{
		this.schemaID = schemaID;
		for(SchemaElement schemaElement : schemaElements)
			addElement(schemaElement);
	}

	/** Copy the base graph */
	protected Graph(Graph graph)
	{
		this.schemaID = graph.schemaID;
		this.graphHash = graph.graphHash;
	}

	/** Returns all schema elements */
	public ArrayList<SchemaElement> getSchemaElements()
		{ return getSchemaElements(null); }
	
	/** Returns the schema elements associated with the specified type */
	public ArrayList<SchemaElement> getSchemaElements(Class type)
	{	
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
		
		// Filter out schemas of the specified type
		for(SchemaElement schemaElement : graphHash.values())
			if(type==null || type.isInstance(schemaElement))
				schemaElements.add(schemaElement);
		
		return schemaElements;		
	}

	/** Returns the specified schema element */
	public SchemaElement getSchemaElement(Integer id)
		{ return graphHash.get(id); }
	
	/** Returns the entity associated with the specified attribute */
	public Entity getEntity(Integer attributeID)
	{
		Attribute attribute = (Attribute)getSchemaElement(attributeID);
		return (Entity)getSchemaElement(attribute.getEntityID());
	}
	
	/** Returns the attributes associated with the specified entity */
	public ArrayList<Attribute> getAttributes(Integer entityID)
	{
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		
		// Identify all attributes associated with the specified entity
		for(SchemaElement attribute : getSchemaElements(Attribute.class))
			if(((Attribute)attribute).getEntityID().equals(entityID))
			   attributes.add((Attribute)attribute);
		
		return attributes;
	}
	
	/** Returns the containments associated with the specified schema element */
	public ArrayList<Containment> getContainments(Integer elementID)
	{
		ArrayList<Containment> containments = new ArrayList<Containment>();
		
		// Identify all containments associated with the specified schema element
		for(SchemaElement containment : getSchemaElements(Containment.class))
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
		DomainValue domainValue = (DomainValue)getSchemaElement(domainValueID);
		return (Domain)getSchemaElement(domainValue.getDomainID());
	}
	
	/** Returns the domain values associated with the specified domain */
	public ArrayList<DomainValue> getDomainValues(Integer domainID)
	{
		ArrayList<DomainValue> domainValues = new ArrayList<DomainValue>();
		for(SchemaElement domainValue : getSchemaElements(DomainValue.class))
			if(((DomainValue)domainValue).getDomainID().equals(domainID))
				domainValues.add((DomainValue)domainValue);
		return domainValues;
	}

	/** Returns the name associated with the specified element */
	public String getName(Integer elementID)
	{
		// Return aliased name if one exists
		for(SchemaElement schemaElement : getSchemaElements(Alias.class))
			if(((Alias)schemaElement).getElementID().equals(elementID))
				return schemaElement.getName();
		
		// Otherwise, return element name
		SchemaElement element = getSchemaElement(elementID);
		return element.getName()==null ? "" : element.getName();
	}

	/** Adds a list of elements to the graph */
	public void addElement(SchemaElement schemaElement)
	{
		// Add element to the graph
		graphHash.put(schemaElement.getId(),schemaElement);

		// Inform listeners of the added element
		for(GraphListener listener : listeners)
			listener.schemaElementAdded(schemaElement);
	}
	
	/** Removes a list of elements from the graph */
	public void deleteElement(Integer schemaID)
	{
		// Remove element from graph
		SchemaElement schemaElement = graphHash.get(schemaID);
		graphHash.remove(schemaID);
		
		// Inform listeners of the removed element
		for(GraphListener listener : listeners)
			listener.schemaElementRemoved(schemaElement);
	}
	
	/** Adds a graph listener */
	public void addGraphListener(GraphListener listener)
		{ listeners.add(listener); }
	
	/** Removes a graph listener */
	public void removeGraphListener(GraphListener listener)
		{ listeners.remove(listener); }
}