// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.mitre.schemastore.data.SchemaElements;
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
	
	/** Updates the id of an element in the element list */
	static private void updateElementID(ArrayList<SchemaElement> elements, Integer oldID, Integer newID)
	{
		// Only update element if ID changed
		if(oldID.equals(newID)) return;
		
		// Shift the ID of any elements that conflict with this updated ID
		for(SchemaElement element : elements)
			if(element.getId().equals(newID))
				{ updateElementID(elements,newID,newID+10000); }
		
		// Replace all references to old ID with new ID
		for(SchemaElement schemaElement : elements)
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
	
			// Replace all default domain schema elements
			for(SchemaElement element : elements)
				if(element.getClass()==Domain.class)
				{
					// Identify if the domain is default
					Domain domain = (Domain)element;
					String domainName = domain.getName().toLowerCase();
					Integer newID = null;
					if(domainName.equals("integer")) newID = -1;
					if(domainName.equals("double")) newID = -2;
					if(domainName.equals("string")) newID = -3;
					if(domainName.equals("timestamp")) newID = -4;
					if(domainName.equals("boolean")) newID = -5;
					if(domainName.equals("any")) newID = -6;
		
					// Identify all default domain elements
					if(newID!=null)
						if(!newID.equals(domain.getId()))
							updateElementID(elements,domain.getId(), newID);
				}
						
			// Sort the schema elements to prevent dependency issues
			Collections.sort(elements,new SchemaElementComparator());
				
			// Add schema elements to the web service
			for(SchemaElement element : elements)
				if(element.getId()>=0)
				{
					element.setBase(schemaID);
					Integer schemaElementID = SchemaElements.addSchemaElement(element);
					if(schemaElementID.equals(0)) throw new RemoteException("Failed to import schema element "+element.getName());
					updateElementID(elements,element.getId(),schemaElementID);
				}
		}
		catch(RemoteException e) { if(schemaID>0) client.deleteSchema(schemaID); schemaID=0; }
		return schemaID;
	}
}