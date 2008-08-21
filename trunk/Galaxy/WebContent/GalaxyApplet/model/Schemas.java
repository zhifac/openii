// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

import model.listeners.SchemasListener;
import model.server.ServletConnection;

/**
 * Class for managing the schemas in the schema repository
 * @author CWOLF
 */
public class Schemas
{
	/** Caches schema information */
	static private HashMap<Integer,Schema> schemas = null;
	
	/** Caches schema element count information */
	static private HashMap<Integer,Integer> schemaElementCounts = new HashMap<Integer,Integer>();
	
	/** Caches schema elements */
	static private HashMap<Integer,SchemaElement> schemaElements = new HashMap<Integer,SchemaElement>();
	
	/** Caches schema elements associated with schemas */
	static private HashMap<Integer,HierarchicalGraph> schemaGraphs = new HashMap<Integer,HierarchicalGraph>();
	
	/** List of listeners monitoring schema events */
	static private ArrayList<SchemasListener> listeners = new ArrayList<SchemasListener>();
		
	//----------------
	// Schema Actions
	//----------------
	
	/** Initializes the schema list */
	static private void initSchemas()
	{
		schemas = new HashMap<Integer,Schema>();
		for(Schema schema : ServletConnection.getSchemas())
			if(ServletConnection.getSchemaElementCount(schema.getId())<5000)
			{
				schemas.put(schema.getId(),schema);
				for(SchemasListener listener : listeners)
					listener.schemaAdded(schema);
			}
	}
	
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
	
	/** Locks the specified schema */
	static public void lockSchema(Integer schemaID)
	{
		if(ServletConnection.lockSchema(schemaID))
		{
			Schema schema = schemas.get(schemaID);
			Schema newSchema = new Schema(schema.getId(),schema.getName(),schema.getAuthor(),schema.getSource(),schema.getType(),schema.getDescription(),true);
			schemas.put(schemaID, newSchema);
			for(SchemasListener listener : listeners)
				listener.schemaLocked(newSchema);
		}
	}
	
	/** Extends the specified schema */
	static public void extendSchema(Integer schemaID)
	{
		Schema extendedSchema = ServletConnection.extendSchema(schemaID);
		if(extendedSchema!=null)
		{
			// Update Galaxy to contain extended schema
			schemas.put(extendedSchema.getId(), extendedSchema);
			for(SchemasListener listener : listeners)
				listener.schemaAdded(extendedSchema);
			SelectedObjects.setSelectedSchema(extendedSchema.getId());

			// Set schema groups to match base schema
			Groups.setSchemaGroups(extendedSchema.getId(), Groups.getSchemaGroups(schemaID));
		}
	}
	
	/** Updates the specified schema */
	static public void updateSchema(Schema schema)
	{
		if(ServletConnection.updateSchema(schema))
		{
			schemas.put(schema.getId(), schema);
			for(SchemasListener listener : listeners)
				listener.schemaUpdated(schema);
		}
	}
	
	/** Delete the specified schema */
	static public void deleteSchema(Schema schema)
	{
		// Delete all data sources associated with the schema
		for(DataSource dataSource : DataSources.getDataSources(schema.getId()))
			DataSources.deleteDataSource(DataSources.getDataSource(dataSource.getId()));

		// Delete all schema group associations
		Groups.setSchemaGroups(schema.getId(),new ArrayList<Integer>());

		// Identify the parent schema ID
		Integer parentSchemaID = null;
		ArrayList<Integer> parentSchemas = getParentSchemas(schema.getId());
		if(parentSchemas.size()>0)
			parentSchemaID = parentSchemas.get(0);

		// Delete the schema
		if(ServletConnection.deleteSchema(schema.getId()))
		{
			schemas.remove(schema.getId());
			for(SchemasListener listener : listeners)
				listener.schemaRemoved(schema);
			
			// Modify the selected schema
			if(parentSchemaID!=null) SelectedObjects.setSelectedSchema(parentSchemaID);
			else if(schemas.size()>0) SelectedObjects.setSelectedSchema(schemas.get(0).getId());
		}
	}
	
	/** Filter the list of schemas to only include schemas existent in Galaxy (<=1000) */
	static public ArrayList<Integer> filter(ArrayList<Integer> schemas)
	{
		ArrayList<Integer> filteredSchemas = new ArrayList<Integer>();
		for(Integer schemaID : schemas)
			if(getSchema(schemaID)!=null) filteredSchemas.add(schemaID);
		return filteredSchemas;
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
		{ return ServletConnection.getParentSchemas(schemaID); }

	/** Returns the child schemas */
	static public ArrayList<Integer> getChildSchemas(Integer schemaID)
		{ return ServletConnection.getChildSchemas(schemaID); }

	/** Returns the descendant schemas of the specified schema */
	static public ArrayList<Integer> getDescendantSchemas(Integer schemaID)
		{ return ServletConnection.getDescendantSchemas(schemaID); }

	/** Returns the schemas associated with the specified schema */
	static public ArrayList<Integer> getAssociatedSchemas(Integer schemaID)
		{ return ServletConnection.getAssociatedSchemas(schemaID); }

	/** Returns the root for the two specified schemas */
	static public Integer getRootSchema(Integer schema1ID, Integer schema2ID)
		{ return ServletConnection.getRootSchema(schema1ID, schema2ID); }

	/** Returns the schema path between the specified root and schema */
	static public ArrayList<Integer> getSchemaPath(Integer rootID, Integer schemaID)
		{ return ServletConnection.getSchemaPath(rootID, schemaID); }

	/** Updates the parent schemas for the specified schema */
	static public void setParentSchemas(Schema schema, ArrayList<Integer> parentIDs)
	{
		if(ServletConnection.setParentSchemas(schema.getId(), parentIDs))
		{
			schemaGraphs.remove(schema.getId());
			for(SchemasListener listener : listeners)
				listener.schemaParentsUpdated(schema);		
		}
	}
	
	//------------------------
	// Schema Element Actions
	//------------------------

	/** Returns the schema element count */
	static public int getSchemaElementCount(Integer schemaID)
	{
		// First, check schema element array to calculate size
		HierarchicalGraph graph = schemaGraphs.get(schemaID);
		if(graph!=null) return graph.size();
		
		// If no schema elements exist, get schema element size from database
		Integer schemaElementCount = schemaElementCounts.get(schemaID);
		if(schemaElementCount==null)
		{
			schemaElementCount=ServletConnection.getSchemaElementCount(schemaID);
			schemaElementCounts.put(schemaID, schemaElementCount);
		}
		return schemaElementCount;
	}
		
	/** Gets the specified schema element */
	static public SchemaElement getSchemaElement(Integer elementID)
	{
		if(!schemaElements.containsKey(elementID))
			schemaElements.put(elementID, ServletConnection.getSchemaElement(elementID));
		return schemaElements.get(elementID);
	}
	
	/** Retrieves the schema element graph */
	static public HierarchicalGraph getGraph(Integer schemaID)
	{
		// Retrieve graph if needed
		if(!schemaGraphs.containsKey(schemaID))
		{
			HierarchicalGraph graph = ServletConnection.getSchemaElementGraph(schemaID);
			schemaGraphs.put(schemaID,graph);
			for(SchemaElement element : graph.getElements(null))
				schemaElements.put(element.getId(), element);
		}
		return schemaGraphs.get(schemaID);
	}	
	
	/** Adds the specified schema element */
	static public boolean addSchemaElement(SchemaElement schemaElement)
	{
//		Integer schemaElementID = ServletConnection.addSchemaElement(schemaElement);
//		if(schemaElementID!=null)
//		{
//			schemaElement.setId(schemaElementID);
//			schemaElements.put(schemaElement.getId(), schemaElement);
//			schemaSchemaElements.get(schemaElement.getBase()).add(schemaElement);
//			for(SchemasListener listener : listeners)
//				listener.schemaElementAdded(schemaElement);
//			return true;
//		}
		return false;
	}

	/** Updates the specified schema element */
	static public Boolean updateSchemaElement(SchemaElement schemaElement)
	{
//		if(ServletConnection.updateSchemaElement(schemaElement))
//		{
//			schemaElements.put(schemaElement.getId(), schemaElement);
//			schemaSchemaElements.get(schemaElement.getBase()).remove(schemaElement);
//			schemaSchemaElements.get(schemaElement.getBase()).add(schemaElement);
//			for(SchemasListener listener : listeners)
//			{
//				listener.schemaElementRemoved(schemaElement);
//				listener.schemaElementAdded(schemaElement);
//			}
//			return true;
//		}
		return false;
	}
	
	/** Deletes the specified schema element */
	static public Boolean deleteSchemaElement(SchemaElement schemaElement)
	{
//		if(ServletConnection.deleteSchemaElement(schemaElement.getId()))
//		{
//			schemaElements.remove(schemaElement.getId());
//			schemaSchemaElements.get(schemaElement.getBase()).remove(schemaElement);
//			for(SchemasListener listener : listeners)
//				listener.schemaElementRemoved(schemaElement);
//			return true;
//		}
		return false;
	}

	//------------------
	// Schema Listeners
	//------------------
	
	/** Adds a listener monitoring schema events */
	static public void addSchemasListener(SchemasListener listener)
		{ listeners.add(listener); }
	
	/** Removes a listener monitoring schema events */
	static public void removeSchemasListener(SchemasListener listener)
		{ listeners.remove(listener); }
	
	/** Gets the current schema listeners */
	static public ArrayList<SchemasListener> getSchemasListeners()
		{ return new ArrayList<SchemasListener>(listeners); }
}
