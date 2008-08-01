// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;
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

/** Class for managing the current list of schema elements in the schema repository */
public class SchemaElements
{
	/** Returns the alias to use for the specified schema */
	static private Alias getAlias(Integer schemaID, ArrayList<Alias> aliases)
	{
		// First, check to see if alias at this level
		Alias pickedAlias = null;
		for(Alias alias : aliases)
			if(alias.getBase().equals(schemaID) && (pickedAlias==null || alias.getId()<pickedAlias.getId()))
				pickedAlias = alias;
		if(pickedAlias!=null) return pickedAlias;
				
		// Next, determine alias based on parents
		ArrayList<Integer> parentSchemaIDs = SchemaRelationships.getParentsInternal(schemaID);
		if(parentSchemaIDs.size()==0) return null;
		for(Integer parentSchemaID : parentSchemaIDs)
		{
			Alias parentAlias = getAlias(parentSchemaID, aliases);
			if(parentAlias==null) return null;
			if(pickedAlias==null || parentAlias.getId()<pickedAlias.getId())
				pickedAlias = parentAlias;
		}
		return pickedAlias;
	}
	
	/** Filter out conflicting aliases from the element list */
	static private ArrayList<SchemaElement> filterOutConflictingAliases(Integer schemaID, ArrayList<SchemaElement> elements)
	{
		// Generate a hash map to store all aliases
		HashMap<Integer,ArrayList<Alias>> aliasMap = new HashMap<Integer,ArrayList<Alias>>();
		for(SchemaElement element : elements)
			if(element instanceof Alias)
			{
				Integer elementID = ((Alias)element).getElementID();
				ArrayList<Alias> aliases = aliasMap.get(elementID);
				if(aliases==null) aliasMap.put(elementID, aliases = new ArrayList<Alias>());
				aliases.add((Alias)element);
			}

		// Only perform filtering if aliases exist
		for(Integer elementID : aliasMap.keySet())
		{
			ArrayList<Alias> aliases = aliasMap.get(elementID);
			Alias chosenAlias = getAlias(schemaID,aliases);
			for(Alias alias : aliases)
				if(chosenAlias==null || !chosenAlias.equals(alias))
					elements.remove(alias);
		}
		
		// Return filtered elements
		return elements;
	}
	
	/** Adds a schema element */
	static public Integer addSchemaElement(SchemaElement schemaElement)
	{
		Schema schema = Schemas.getSchema(schemaElement.getBase());
		if(schema!=null && !schema.getLocked())
			return Database.addSchemaElement(schemaElement);
		return 0;
	}
	
	/** Updates a schema element */
	static public boolean updateSchemaElement(SchemaElement schemaElement)
	{
		Schema schema = Schemas.getSchema(schemaElement.getBase());
		if(schema!=null && !schema.getLocked())
			return Database.updateSchemaElement(schemaElement);
		return false;
	}
	
	/** Removes a schema element */
	static public boolean deleteSchemaElement(Integer schemaElementID)
	{
		SchemaElement schemaElement = getSchemaElement(schemaElementID);
		Schema schema = Schemas.getSchema(schemaElement.getBase());
		if(schema!=null && !schema.getLocked())
			return Database.deleteSchemaElement(schemaElementID);
		return false;
	}

	/** Retrieves the specified schema element */
	static public SchemaElement getSchemaElement(Integer schemaElementID)
		{ return Database.getSchemaElement(schemaElementID); }

	/** Retrieves the schema element count for the specified schema */
	static public Integer getSchemaElementCount(Integer schemaID)
	{
		Integer elementCount = Database.getBaseElementCount(schemaID);
		for(Integer ancestorID : SchemaRelationships.getAncestors(schemaID))
			elementCount += Database.getBaseElementCount(ancestorID);
		elementCount += Database.getDefaultDomainCount();
		return elementCount;
	}
	
	/** Retrieves the specified schema elements */
	static public ArrayList<SchemaElement> getSchemaElements(Integer schemaID)
	{
		// Collect all schema elements
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
		elements.addAll(Database.getBaseElements(schemaID));
		for(Integer ancestorID : SchemaRelationships.getAncestors(schemaID))
			elements.addAll(Database.getBaseElements(ancestorID));
		
		// Add in all used default domains
		for(Domain domain : Database.getDefaultDomains())
			for(SchemaElement element : elements)
				if(element instanceof Attribute && ((Attribute)element).getDomainID().equals(domain.getId()))
					{ elements.add(domain); break; }

		// Filter out conflicting aliases as needed
		SchemaRelationships.recacheAsNeeded();
		elements = filterOutConflictingAliases(schemaID, elements);
		
		// Return the schema elements
		return elements;
	}
	
	/** Retrieves the specified schema element type */
	static public String getSchemaElementType(Integer schemaElementID)
	{
		SchemaElement schemaElement = getSchemaElement(schemaElementID);
		if(schemaElement instanceof Entity) return "Entity";
		else if(schemaElement instanceof Attribute) return "Attribute";
		else if(schemaElement instanceof Domain) return "Domain";
		else if(schemaElement instanceof DomainValue) return "DomainValue";
		else if(schemaElement instanceof Relationship) return "Relationship";
		else if(schemaElement instanceof Containment) return "Containment";
		else if(schemaElement instanceof Subtype) return "Subtype";
		else if(schemaElement instanceof Alias) return "Alias";
		return "";
	}
}