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
 */
public class Graph implements Serializable
{		
	/** Private class for caching graph data */
	private class GraphCache implements Serializable
	{
		/** Stores the lists of elements of each type */
		private HashMap<Class,ArrayList<SchemaElement>> typeLists = null;
		private HashMap<Integer,ArrayList<Attribute>> attributeLists = null;
		private HashMap<Integer,ArrayList<Containment>> containmentLists = null;
		private HashMap<Integer,ArrayList<Relationship>> relationshipLists = null;
		private HashMap<Integer,ArrayList<Subtype>> subtypeLists = null;
		private HashMap<Integer,ArrayList<DomainValue>> domainValueLists = null;
		private HashMap<Integer,Alias> aliasList = null;
		
		/** Resets the cache */
		private void reset()
		{
			typeLists = null;
			attributeLists = null;
			containmentLists = null;
			relationshipLists = null;
			subtypeLists = null;
			domainValueLists = null;
			aliasList = null;
		}
		
		/** Adds an element to the list of elements */
		private <S,T> void addElement(S identifier, T element, HashMap<S,ArrayList<T>> lists)
		{
			ArrayList<T> list = lists.get(identifier);
			if(list==null) lists.put(identifier,list = new ArrayList<T>());
			list.add(element);
		}
		
		/** Retrieves the elements of the specified type */
		private ArrayList<SchemaElement> getElements(Class type)
		{
			if(typeLists==null)
			{
				typeLists = new HashMap<Class,ArrayList<SchemaElement>>();
				for(SchemaElement element : graphHash.values())
					addElement(element.getClass(),element,typeLists);
			}
			ArrayList<SchemaElement> typeList = typeLists.get(type);
			return typeList==null ? new ArrayList<SchemaElement>() : typeList;
		}

		/** Retrieves the attributes for the specified schema element */
		private ArrayList<Attribute> getAttributes(Integer elementID)
		{
			if(attributeLists==null)
			{
				attributeLists = new HashMap<Integer,ArrayList<Attribute>>();
				for(SchemaElement element : getElements(Attribute.class))
				{
					Attribute attribute = (Attribute)element;
					addElement(attribute.getEntityID(),attribute,attributeLists);
					addElement(attribute.getDomainID(),attribute,attributeLists);
				}
			}
			ArrayList<Attribute> attributeList = attributeLists.get(elementID);
			return attributeList==null ? new ArrayList<Attribute>() : attributeList;
		}

		/** Retrieves the containments for the specified schema element */
		private ArrayList<Containment> getContainments(Integer elementID)
		{
			if(containmentLists==null)
			{
				containmentLists = new HashMap<Integer,ArrayList<Containment>>();
				for(SchemaElement element : getElements(Containment.class))
				{
					Containment containment = (Containment)element;
					addElement(containment.getParentID(),containment,containmentLists);
					addElement(containment.getChildID(),containment,containmentLists);
				}
			}
			ArrayList<Containment> containmentList = containmentLists.get(elementID);
			return containmentList==null ? new ArrayList<Containment>() : containmentList;
		}
		
		/** Returns relationships associated with a specified entity */
		public ArrayList<Relationship> getRelationships(Integer elementID)
		{
			if(relationshipLists==null)
			{
				relationshipLists = new HashMap<Integer,ArrayList<Relationship>>();
				for(SchemaElement element : getElements(Relationship.class))
				{
					Relationship relationship = (Relationship)element;
					addElement(relationship.getLeftID(),relationship,relationshipLists);
					addElement(relationship.getRightID(),relationship,relationshipLists);
				}
			}
			ArrayList<Relationship> relationshipList = relationshipLists.get(elementID);
			return relationshipList==null ? new ArrayList<Relationship>() : relationshipList;
		}

		/** Retrieves the subtypes for the specified schema element */
		private ArrayList<Subtype> getSubtypes(Integer elementID)
		{
			if(subtypeLists==null)
			{
				subtypeLists = new HashMap<Integer,ArrayList<Subtype>>();
				for(SchemaElement element : getElements(Subtype.class))
				{
					Subtype subtype = (Subtype)element;
					addElement(subtype.getParentID(),subtype,subtypeLists);
					addElement(subtype.getChildID(),subtype,subtypeLists);
				}
			}
			ArrayList<Subtype> subtypeList = subtypeLists.get(elementID);
			return subtypeList==null ? new ArrayList<Subtype>() : subtypeList;
		}

		/** Retrieves the domain values for the specified schema element */
		private ArrayList<DomainValue> getDomainValues(Integer elementID)
		{
			if(domainValueLists==null)
			{
				domainValueLists = new HashMap<Integer,ArrayList<DomainValue>>();
				for(SchemaElement element : getElements(DomainValue.class))
				{
					DomainValue domainValue = (DomainValue)element;
					addElement(domainValue.getDomainID(),domainValue,domainValueLists);
				}
			}
			ArrayList<DomainValue> domainValueList = domainValueLists.get(elementID);
			return domainValueList==null ? new ArrayList<DomainValue>() : domainValueList;
		}

		/** Retrieves the alias for the specified schema element */
		private Alias getAlias(Integer elementID)
		{
			if(aliasList==null)
			{
				aliasList = new HashMap<Integer,Alias>();
				for(SchemaElement element : getElements(Alias.class))
					aliasList.put(elementID,(Alias)element);
			}
			return aliasList.get(elementID);
		}
	}
	private GraphCache cache = new GraphCache();
	
	/** Stores the schema associated with this graph */
	private Schema schema;

	/** Stores the schema elements associated with this graph */
	private HashMap<Integer,SchemaElement> graphHash = new HashMap<Integer,SchemaElement>();
	
	/** Stores graph listeners */
	private ArrayList<GraphListener> listeners = new ArrayList<GraphListener>();
	
	/** Constructs the graph */
	public Graph(Schema schema, ArrayList<SchemaElement> elements)
		{ this.schema = schema; addElements(elements); }

	/** Copy the graph */
	public Graph(Graph graph)
		{ this.schema = graph.schema; this.graphHash = graph.graphHash; }

	/** Copy the graph */
	public Graph copy()
	{
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
		for(SchemaElement element : graphHash.values())
			elements.add(element.copy());
		return new Graph(schema.copy(),elements);
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
		if(type==null) return new ArrayList<SchemaElement>(graphHash.values());
		return new ArrayList<SchemaElement>(cache.getElements(type));
	}

	/** Returns the base schema elements associated with the specified type */
	public ArrayList<SchemaElement> getBaseElements(Class type)
	{
		ArrayList<SchemaElement> baseElements = new ArrayList<SchemaElement>();
		
		// Identify base schema elements of the specified type
		for(SchemaElement element : getElements(type))
			if(schema.getId().equals(element.getBase()))
				baseElements.add(element);
		
		// Identify default domains first used by this schema
		if(type==null || type.equals(Domain.class))
		{
			// Cycle through all default domain elements
			for(SchemaElement domainElement : getElements(Domain.class))
				if(domainElement.getId()<0)
				{
					// Add default domain element if only referenced by base elements
					boolean baseReferencesOnly = true;
					for(SchemaElement referencingElement : getReferencingElements(domainElement.getId()))
						if(!schema.getId().equals(referencingElement.getBase()))
							{ baseReferencesOnly = false; break; }
					if(baseReferencesOnly) baseElements.add(domainElement);
				}
		}
			
		return baseElements;
	}
	
	/** Returns the entity associated with the specified attribute */
	public Entity getEntity(Integer attributeID)
		{ return (Entity)getElement(((Attribute)getElement(attributeID)).getEntityID()); }
	
	/** Returns the attributes associated with the specified entity */
	public ArrayList<Attribute> getAttributes(Integer elementID)
		{ return cache.getAttributes(elementID); }
	
	/** Returns the containments associated with the specified schema element */
	public ArrayList<Containment> getContainments(Integer elementID)
		{ return cache.getContainments(elementID); }
	
	/** Returns the containments associated with the specified schema element */
	public ArrayList<Relationship> getRelationships(Integer elementID)
		{ return cache.getRelationships(elementID); }
	
	/** Returns the sub-type relationships for a given entity */
	public ArrayList<Subtype> getSubTypes(Integer elementID)
		{ return cache.getSubtypes(elementID); }
	
	/** Returns the domain values associated with the specified domain */
	public ArrayList<DomainValue> getDomainValuesForDomain(Integer domainID)
		{ return cache.getDomainValues(domainID); }

	/** Returns the alias associated with the specified element */
	public Alias getAlias(Integer elementID)
		{ return cache.getAlias(elementID); }

	/** Returns the display name for the specified element in this graph */
	public String getDisplayName(Integer elementID)
	{
		// Returns the element name
		SchemaElement element = getElement(elementID);
		Alias alias = getAlias(elementID);
		String name = alias!=null ? alias.getName() : element.getName();
		if(name.length()>0) return name;

		// Otherwise, returns the name of the parent containment element
		if(element instanceof Containment)
		{
			Integer childID = ((Containment)element).getChildID();
			return "[" + getDisplayName(childID) + "]";
		}

		// Otherwise, find name of containment associated with element
		for(Containment containment : getContainments(elementID))
			if(containment.getChildID().equals(elementID) && containment.getName().length()>0)
				return "[" + getDisplayName(containment.getId()) + "]";

		// Otherwise, return nothing
		return "";
	}
	
	/** Adds a list of elements to the graph */
	public boolean addElements(ArrayList<SchemaElement> elements)
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
		boolean success = true;
		Collections.sort(elements, new ElementComparator());
		for(SchemaElement element : elements)
			success &= addElement(element);
		return success;
	}
	
	/** Returns the list of schema elements referencing the specified element within the graph */
	public ArrayList<SchemaElement> getReferencingElements(Integer elementID)
	{
		// Checks to ensure that element is not referenced elsewhere
		ArrayList<SchemaElement> referencingElements = new ArrayList<SchemaElement>();
		for(SchemaElement element : getElements(null))
			for(int referencedID : element.getReferencedIDs())
				if(referencedID==elementID) { referencingElements.add(element); break; }
		return referencingElements;
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
		graphHash.put(element.getId(),element.copy());

		// Inform listeners of the added element
		for(GraphListener listener : listeners)
			listener.schemaElementAdded(element);
		
		cache.reset();
		return true;
	}
	
	/** Removes an element from the graph */
	public boolean deleteElement(Integer elementID)
	{
		// Don't proceed with deleting element, if referenced by other elements
		if(getReferencingElements(elementID).size()>0) return false;
		
		// Remove element from graph
		SchemaElement element = graphHash.get(elementID);
		graphHash.remove(elementID);
		
		// Inform listeners of the removed element
		for(GraphListener listener : listeners)
			listener.schemaElementRemoved(element);
		
		cache.reset();
		return true;
	}
	
	/** Updates the id of an element in the graph */
	public void updateElementID(Integer oldID, Integer newID)
	{
		// Only update element if ID changed
		if(oldID.equals(newID)) return;
		
		// Shift the ID of any elements that conflict with this updated ID
		if(getElement(newID)!=null)
			{ updateElementID(newID,newID+10000); }
		
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
		
		// Resets the cache
		cache.reset();
	}
	
	/** Adds a graph listener */
	public void addGraphListener(GraphListener listener)
		{ listeners.add(listener); }
	
	/** Removes a graph listener */
	public void removeGraphListener(GraphListener listener)
		{ listeners.remove(listener); }
}