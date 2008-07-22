// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.model;

import java.util.ArrayList;

import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

/** Manages schema information */
public class SchemaManager
{
	/** List of listeners monitoring schema events */
	static private ArrayList<SchemaListener> listeners = new ArrayList<SchemaListener>();

	//------------------------
	// Schema Manager Actions
	//------------------------
	
	/** Returns the schemas associated with the repository */
	static public ArrayList<Schema> getSchemas()
		{ try { return SchemaStore.getClient().getSchemas(); } catch(Exception e) { return new ArrayList<Schema>(); } }
	
	/** Imports the specified schema into the repository */
	static public boolean importSchema(Schema schema, ArrayList<Integer> extendedSchemaIDs, ArrayList<SchemaElement> schemaElements)
	{
		boolean success = false;
		try {
			Integer schemaID = SchemaStore.getClient().importSchema(schema, schemaElements);
			schema.setId(schemaID);
			success = SchemaStore.getClient().setParentSchemas(schema.getId(), extendedSchemaIDs);
			for(SchemaListener listener : listeners) listener.schemaAdded(schema);
		} catch(Exception e) {}
		if(!success) deleteSchema(schema);
		return success;
	}

	/** Indicates if the schema is deletable */
	static public boolean isDeletable(Schema schema)
	{
		try { return SchemaStore.getClient().isDeletable(schema.getId()); } catch(Exception e) {}
		return false;
	}
	
	/** Deletes the specified schema */
	static public void deleteSchema(Schema schema)
	{
		try {
			if(SchemaStore.getClient().deleteSchema(schema.getId()))
				for(SchemaListener listener : listeners) listener.schemaRemoved(schema);
		} catch(Exception e) {}
	}
	
	/** Updates the parent schemas for the specified schema */
	static public boolean setParentSchemas(Schema schema, ArrayList<Integer> parentIDs)
	{
		try {
			return SchemaStore.getClient().setParentSchemas(schema.getId(), parentIDs);
		} catch(Exception e) { return false; }
	}
	
	/** Returns the amount of schema elements associated with the specified schema in the repository */
	static public Integer getSchemaElementCount(Integer schemaID)
		{ try { return SchemaStore.getClient().getSchemaElementCount(schemaID); } catch(Exception e) { return null; } }
	
	/** Returns the schema elements associated with the specified schema in the repository */
	static public ArrayList<SchemaElement> getSchemaElements(Integer schemaID)
		{ try { return SchemaStore.getClient().getSchemaElements(schemaID); } catch(Exception e) { return new ArrayList<SchemaElement>(); } }

	/** Returns the data sources associated with the specified schema in the repository */
	static public ArrayList<DataSource> getDataSources(Integer schemaID)
		{ try { return SchemaStore.getClient().getDataSources(schemaID); } catch(Exception e) { return new ArrayList<DataSource>(); } }

	//--------------------------
	// Schema Manager Listeners
	//--------------------------
	
	/** Adds a listener monitoring schema events */
	static public void addSchemaListener(SchemaListener listener)
		{ listeners.add(listener); }
	
	/** Removes a listener monitoring schema events */
	static public void removeSchemasListener(SchemaListener listener)
		{ listeners.remove(listener); }
	
	/** Gets the current schema listeners */
	static public ArrayList<SchemaListener> getSchemasListeners()
		{ return new ArrayList<SchemaListener>(listeners); }
}
