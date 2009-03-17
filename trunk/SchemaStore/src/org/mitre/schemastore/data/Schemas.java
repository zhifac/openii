// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;

import org.mitre.schemastore.data.database.Database;
import org.mitre.schemastore.model.Schema;

/** Class for managing the current list of schemas in the schema repository */
public class Schemas
{
	/** Returns a listing of all schemas */
	static public ArrayList<Schema> getSchemas()
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
	
	/** Indicates that the schema is able to be deleted */
	static public boolean isDeletable(Integer schemaID)
	{
		Integer children = SchemaRelationships.getChildren(schemaID).size();
		Integer dataSources = DataSources.getDataSources(schemaID).size();
		Integer mappings = Database.getSchemaMappingIDs(schemaID).size();
		return children==0 && dataSources==0 && mappings==0;
	}
	
	/** Returns the list of deletable schemas */
	static public ArrayList<Integer> getDeletableSchemas()
		{ return Database.getDeletableSchemas(); }
	
	/** Removes the specified schema */
	static public boolean deleteSchema(Integer schemaID)
	{
		if(isDeletable(schemaID))
			return Database.deleteSchema(schemaID);
		return false;
	}
}