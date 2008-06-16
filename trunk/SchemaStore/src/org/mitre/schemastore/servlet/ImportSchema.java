// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.mitre.schemastore.database.SchemaElements;
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
	
	/** Alters all references to the specified ID */
	static private void alterID(ArrayList<SchemaElement> schemaElements, Integer oldID, Integer newID)
	{
		// First, determine if new ID is already used (and switch this ID if true)
		for(SchemaElement schemaElement : schemaElements)
			if(schemaElement.getId().equals(newID))
				{ alterID(schemaElements,newID,newID+10000); break; }
		
		// Replace all references to old ID with new ID
		for(SchemaElement schemaElement : schemaElements)
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
				if(containment.getParentID().equals(oldID)) containment.setParentID(newID);
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
	static Integer importSchema(SchemaStore client, Schema schema, ArrayList<SchemaElement> schemaElements) throws RemoteException
	{		
		Integer schemaID = 0;
		try
		{
			// Adds the schema to the web service
			schema.setLocked(false);
			schemaID = client.addSchema(schema);
			if(schemaID==null) throw new RemoteException("Failed to import schema "+schema.getName());
			alterID(schemaElements, schema.getId(), schemaID);
	
			// Replace all default domain schema elements
			for(SchemaElement schemaElement : new ArrayList<SchemaElement>(schemaElements))
				if(schemaElement instanceof Domain)
				{
					// Identify if the domain is default
					Domain domain = (Domain)schemaElement;
					String domainName = domain.getName().toLowerCase();
					Integer newID = null;
					if(domainName.equals("integer")) newID = -1;
					if(domainName.equals("double")) newID = -2;
					if(domainName.equals("string")) newID = -3;
					if(domainName.equals("timestamp")) newID = -4;
					if(domainName.equals("boolean")) newID = -5;
					if(domainName.equals("any")) newID = -6;
	
					// Remove the default domain element
					if(newID!=null)
					{
						schemaElements.remove(schemaElement);
						alterID(schemaElements, domain.getId(), newID);
					}
				}
						
			// Sort the schema elements to prevent dependency issues
			Collections.sort(schemaElements,new SchemaElementComparator());
				
			// Add schema elements to the web service
			for(SchemaElement schemaElement : schemaElements)
			{
				schemaElement.setBase(schemaID);
				Integer schemaElementID = SchemaElements.addSchemaElement(schemaElement);
				if(schemaElementID==null) throw new RemoteException("Failed to import schema element "+schemaElement.getName());
				alterID(schemaElements,schemaElement.getId(),schemaElementID);
			}
	
			// Locks the schema once everything has been imported
			client.lockSchema(schemaID);
		}
		catch(RemoteException e)
		{
			if(schemaID!=null) client.deleteSchema(schemaID);
			throw e;
		}
		return schemaID;
	}
}
