// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.galaxy.model.server.SchemaStoreManager;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;


/**
 * Class for managing the schemas in the schema repository
 * @author CWOLF
 */
public class Schemas
{
	/** Caches schema information */
	static private HashMap<Integer,Schema> schemas = null;
	
	/** Caches schema parent info */
	static private HashMap<Integer,ArrayList<Integer>> parentSchemas = new HashMap<Integer,ArrayList<Integer>>();
	
	/** Caches schema children info */
	static private HashMap<Integer,ArrayList<Integer>> childSchemas = new HashMap<Integer,ArrayList<Integer>>();
	
	/** Caches schema element count information */
	static private HashMap<Integer,Integer> schemaElementCounts = new HashMap<Integer,Integer>();
	
	/** Caches schema elements */
	static private HashMap<Integer,SchemaElement> schemaElements = new HashMap<Integer,SchemaElement>();
	
	/** Caches schema elements associated with schemas */
	static private HashMap<Integer,HierarchicalSchemaInfo> schemaInfoList = new HashMap<Integer,HierarchicalSchemaInfo>();
		
	//---------------
	// Reset Actions
	//---------------
	
	/** Reset schema */
	static public void resetSchema(Integer schemaID)
	{
		// Clear schema information from cache
		schemas = null;
		schemaElementCounts.remove(schemaID);
		schemaElements.remove(schemaID);
		schemaInfoList.remove(schemaID);

		// Clear schema associations
		for(Integer associatedSchemaID : SchemaStoreManager.getAssociatedSchemas(schemaID))
			{ parentSchemas.remove(associatedSchemaID); childSchemas.remove(associatedSchemaID); }		
	}

	/** Initializes the schema list */
	static private void initSchemas()
	{
		schemas = new HashMap<Integer,Schema>();
		ArrayList<Schema> serverSchemas = SchemaStoreManager.getSchemas();
		if(serverSchemas!=null)
			for(Schema schema : serverSchemas)
				schemas.put(schema.getId(),schema);
	}
	
	//----------------
	// Schema Actions
	//----------------
	
	/** Returns a list of all schemas */
	static public ArrayList<Schema> getSchemas()
	{
		if(schemas==null) initSchemas();
		return new ArrayList<Schema>(schemas.values());
	}
	
	/** Returns the specified schema */
	static public Schema getSchema(Integer schemaID)
	{
		if(schemas==null) initSchemas(); 
		return schemas.get(schemaID);
	}
	
	/** Sorts the list of schemas */
	static public ArrayList<Schema> sort(ArrayList<Schema> schemas)
	{
		final class SchemaComparator implements Comparator<Schema>
		{
			public int compare(Schema schema1, Schema schema2)
				{ return schema1.getName().compareTo(schema2.getName()); }
		}
		Collections.sort(schemas,new SchemaComparator());
		return schemas;
	}

	/** Sorts the list of schemas by ID */
	static public ArrayList<Integer> sortByID(ArrayList<Integer> schemas)
	{
		final class SchemaComparator implements Comparator<Integer>
		{
			public int compare(Integer schema1, Integer schema2)
				{ return getSchema(schema1).getName().compareTo(getSchema(schema2).getName()); }
		}
		Collections.sort(schemas,new SchemaComparator());
		return schemas;
	}
	
	//-----------------------------
	// Schema Relationship Actions
	//-----------------------------
	
	/** Returns the parent schemas */
	static public ArrayList<Integer> getParentSchemas(Integer schemaID)
	{
		ArrayList<Integer> schemas = parentSchemas.get(schemaID);
		if(schemas==null)
			parentSchemas.put(schemaID, schemas = SchemaStoreManager.getParentSchemas(schemaID));
		return schemas;
	}

	/** Returns the child schemas */
	static public ArrayList<Integer> getChildSchemas(Integer schemaID)
	{
		ArrayList<Integer> schemas = childSchemas.get(schemaID);
		if(schemas==null)
			childSchemas.put(schemaID, schemas = SchemaStoreManager.getChildSchemas(schemaID));
		return schemas;
	}

	/** Returns the descendant schemas of the specified schema */
	static public ArrayList<Integer> getDescendantSchemas(Integer schemaID)
		{ return SchemaStoreManager.getDescendantSchemas(schemaID); }

	/** Returns the schemas associated with the specified schema */
	static public ArrayList<Integer> getAssociatedSchemas(Integer schemaID)
		{ return SchemaStoreManager.getAssociatedSchemas(schemaID); }

	/** Returns the root for the two specified schemas */
	static public Integer getRootSchema(Integer schema1ID, Integer schema2ID)
		{ return SchemaStoreManager.getRootSchema(schema1ID, schema2ID); }

	/** Returns the schema path between the specified root and schema */
	static public ArrayList<Integer> getSchemaPath(Integer rootID, Integer schemaID)
		{ return SchemaStoreManager.getSchemaPath(rootID, schemaID); }
	
	//------------------------
	// Schema Element Actions
	//------------------------

	/** Gets the list of elements with the specified keyword */
	static public ArrayList<SchemaElement> getElementsForKeyword(String keyword, HashSet<Integer> tags)
		{ return SchemaStoreManager.getSchemaElementsForKeyword(keyword, new ArrayList<Integer>(tags)); }

	/** Returns the schema element count */
	static public int getSchemaElementCount(Integer schemaID)
	{
		Integer schemaElementCount = schemaElementCounts.get(schemaID);
		if(schemaElementCount==null)
		{
			schemaElementCount=SchemaStoreManager.getSchemaElementCount(schemaID);
			schemaElementCounts.put(schemaID, schemaElementCount);
		}
		return schemaElementCount;
	}
		
	/** Gets the specified schema element */
	static public SchemaElement getSchemaElement(Integer elementID)
	{
		if(!schemaElements.containsKey(elementID))
			schemaElements.put(elementID, SchemaStoreManager.getSchemaElement(elementID));
		return schemaElements.get(elementID);
	}
	
	/** Retrieves the schema info */
	static public HierarchicalSchemaInfo getSchemaInfo(Integer schemaID)
	{
		// Retrieve schema info if needed
		if(!schemaInfoList.containsKey(schemaID))
		{
			HierarchicalSchemaInfo schemaInfo = new HierarchicalSchemaInfo(SchemaStoreManager.getSchemaInfo(schemaID));
			schemaInfoList.put(schemaID,schemaInfo);
			for(SchemaElement element : schemaInfo.getElements(null))
				schemaElements.put(element.getId(), element);
		}
		return schemaInfoList.get(schemaID);
	}
}
