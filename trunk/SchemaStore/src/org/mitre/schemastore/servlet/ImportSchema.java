// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.schemastore.data.database.Database;
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
 * Handles the importation of a schema to the schema store web service
 * @author CWOLF
 */
public class ImportSchema
{
	/** Stores hash map to all IDs */
	static private HashMap<Integer,ArrayList<SchemaElement>> elementRefs = new HashMap<Integer,ArrayList<SchemaElement>>();
	
	/** Private class for sorting schema elements before importing */
	static private class SchemaElementComparator implements Comparator<SchemaElement>
	{
		public int compare(SchemaElement so1, SchemaElement so2)
		{
			if(so1.getClass().equals(so2.getClass())) return so1.getId().compareTo(so2.getId());
			if(so1.getClass()==Entity.class) return -1; if(so2.getClass()==Entity.class) return 1;
			if(so1.getClass()==Domain.class) return -1; if(so2.getClass()==Domain.class) return 1;
			if(so1.getClass()==Attribute.class) return -1; if(so2.getClass()==Attribute.class) return 1;
			if(so1.getClass()==DomainValue.class) return -1; if(so2.getClass()==DomainValue.class) return 1;
			if(so1.getClass()==Relationship.class) return -1; if(so2.getClass()==Relationship.class) return 1;
			if(so1.getClass()==Subtype.class) return -1; if(so2.getClass()==Subtype.class) return 1;
			if(so1.getClass()==Containment.class) return -1; if(so2.getClass()==Containment.class) return 1;		
			if(so1.getClass()==Alias.class) return -1; if(so2.getClass()==Alias.class) return 1;		
			return 1;
		}
	}
	
	/** Generate element references */
	static private void generateElementRefs(ArrayList<SchemaElement> elements)
	{
		// Clear out old element references
		elementRefs.clear();
		
		// Search through all schema elements to retrieve references
		for(SchemaElement element : elements)
		{
			// Get list of IDs to which this element is associated
			ArrayList<Integer> elementIDs = new ArrayList<Integer>();
			elementIDs.add(element.getId());
			elementIDs.addAll(element.getReferencedIDs());

			// Store element references
			for(Integer elementID : elementIDs)
			{
				ArrayList<SchemaElement> elementList = elementRefs.get(elementID);
				if(elementList==null) elementRefs.put(elementID,elementList = new ArrayList<SchemaElement>());
				elementList.add(element);
			}
		}		
	}
	
	/** Updates the id of an element in the element list */
	static private void updateElementID(ArrayList<SchemaElement> elements, Integer oldID, Integer newID)
	{
		// Only update element if ID changed
		if(oldID.equals(newID)) return;
		
		// Shift the ID of any elements that conflict with this updated ID
		if(elementRefs.containsKey(newID))
			updateElementID(elements,newID,newID+10000);
		
		// Replace all references to old ID with new ID
		for(SchemaElement schemaElement : elementRefs.get(oldID))
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
		
		// Move element reference to new ID
		elementRefs.put(newID, elementRefs.get(oldID));
		elementRefs.remove(oldID);
	}
	
	/** Imports the specified schema into the web services */
	static Integer importSchema(SchemaStore client, Schema schema, ArrayList<SchemaElement> elements) throws RemoteException
	{		
		Integer schemaID = 0;
		try
		{
			// Adds the schema to the web service
			schema.setLocked(false);
			schemaID = client.addSchema(schema);
			if(schemaID==null) throw new RemoteException("Failed to import schema "+schema.getName());
	
			// Generate the element references
			generateElementRefs(elements);
			
			// Replace all default domain schema elements
			for(SchemaElement element : elements)
				if(element.getClass()==Domain.class)
				{
					// Identify if the domain is default
					Domain domain = (Domain)element;
					String domainName = domain.getName().toLowerCase();
					Integer newID = null;
					if(domainName.equals("integer")) newID = -1;
					if(domainName.equals("real")) newID = -2;
					if(domainName.equals("string")) newID = -3;
					if(domainName.equals("timestamp")) newID = -4;
					if(domainName.equals("boolean")) newID = -5;
					if(domainName.equals("any")) newID = -6;
		
					// Identify all default domain elements
					if(newID!=null)
						if(!newID.equals(domain.getId()))
							updateElementID(elements,domain.getId(), newID);
				}

			// Filter elements before adding to database
			ArrayList<SchemaElement> tempElements = new ArrayList<SchemaElement>();
			for(SchemaElement element : elements)
			{
				element.setBase(schemaID);
				if(element.getId()>0) tempElements.add(element);
			}
			elements = tempElements;
			
			// Sort the schema elements to prevent dependency issues
			Collections.sort(elements,new SchemaElementComparator());

			// Assign universal IDs to all elements
			Integer elementID = Database.getUniversalIDs(elements.size());
			for(SchemaElement element : elements)
				updateElementID(elements,element.getId(),elementID++);
			
			// Add schema elements to the web service
			if(!Database.addSchemaElements(elements)) throw new Exception();
		}
		catch(Exception e) { if(schemaID>0) client.deleteSchema(schemaID); schemaID=0; }
		return schemaID;
	}
}