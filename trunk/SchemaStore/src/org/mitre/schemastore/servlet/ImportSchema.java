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
import org.mitre.schemastore.model.graph.Graph;

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
	static private void alterID(Graph graph, Integer oldID, Integer newID)
	{
		// First, determine if new ID is already used (and switch this ID if true)
		if(graph.getElement(newID)!=null)
			{ alterID(graph,newID,newID+10000); }
		
		// Replace all references to old ID with new ID
		graph.updateElementID(oldID, newID);
	}
	
	/** Imports the specified schema into the web services */
	static Integer importSchema(SchemaStore client, Schema schema, Graph graph) throws RemoteException
	{		
		Integer schemaID = 0;
		try
		{
			// Adds the schema to the web service
			schema.setLocked(false);
			schemaID = client.addSchema(schema);
			if(schemaID==null) throw new RemoteException("Failed to import schema "+schema.getName());
	
			// Replace all default domain schema elements
			for(SchemaElement schemaElement : graph.getElements(Domain.class))
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
					graph.deleteElement(schemaElement.getId());
					alterID(graph, domain.getId(), newID);
				}
			}
						
			// Sort the schema elements to prevent dependency issues
			ArrayList<SchemaElement> elements = graph.getElements(null);
			Collections.sort(elements,new SchemaElementComparator());
				
			// Add schema elements to the web service
			for(SchemaElement element : elements)
			{
				element.setBase(schemaID);
				Integer schemaElementID = SchemaElements.addSchemaElement(element);
				if(schemaElementID==null) throw new RemoteException("Failed to import schema element "+element.getName());
				alterID(graph,element.getId(),schemaElementID);
			}
		}
		catch(RemoteException e)
		{
			if(schemaID!=null) client.deleteSchema(schemaID);
			throw e;
		}
		return schemaID;
	}
}
