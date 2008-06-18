// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.database;

import java.util.ArrayList;

import org.mitre.schemastore.model.Schema;

/** Class for managing the current list of schemas in the schema repository */
public class Schemas
{
	/** Returns a listing of all schemas */
	static public ArrayList<Schema> getSchemaList()
		{ return Database.getSchemas(); }
	
	/** Returns the specified schema */
	static public Schema getSchema(Integer schemaID)
		{ return Database.getSchema(schemaID); }

	/** Adds the specified schema */
	static public Integer addSchema(Schema schema)
		{ return Database.addSchema(schema); }
	
	/** Extends the specified schema */
	static public Schema extendSchema(Integer schemaID)
	{
		Schema oldSchema = getSchema(schemaID);		
		if(oldSchema!=null)
			return Database.extendSchema(getSchema(schemaID));
		return null;
	}
	
	/** Updates the specified schema */
	static public boolean updateSchema(Schema schema)
	{
		Schema oldSchema = getSchema(schema.getId());
		if(oldSchema!=null && !oldSchema.getLocked())
			return Database.updateSchema(schema);
		return false;
	}
	
	/** Unlocks the specified schema */
	static public boolean unlockSchema(Integer schemaID)
	{
		if(Database.getSchemaMappingIDs(schemaID).size()>0) return false;
		if(!getSchema(schemaID).getType().equals("Manual")) return false;
		for(Integer childSchemaID : SchemaRelationships.getChildren(schemaID))
			if(getSchema(childSchemaID).getLocked()) return false;
		return Database.lockSchema(schemaID,false);
	}
	
	/** Locks the specified schema */
	static public boolean lockSchema(Integer schemaID)
		{ return Database.lockSchema(schemaID,true); }
	
	/** Removes the specified schema */
	static public boolean deleteSchema(Integer schemaID)
	{
		if(DataSources.getDataSources(schemaID).size()==0 && SchemaRelationships.getChildren(schemaID).size()==0 && Database.getSchemaMappingIDs(schemaID).size()==0)
			return Database.deleteSchema(schemaID);
		return false;
	}
}
