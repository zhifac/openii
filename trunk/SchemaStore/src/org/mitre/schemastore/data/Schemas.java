// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;

import org.mitre.schemastore.model.Schema;

/** Class for managing the current list of schemas in the schema repository */
public class Schemas extends DataCache
{
	/** Constructs the schema elements cache */
	Schemas(DataManager manager)
		{ super(manager); }
	
	/** Returns a listing of all schemas */
	public ArrayList<Schema> getSchemas()
		{ return getDatabase().getSchemas(); }
	
	/** Returns the specified schema */
	public Schema getSchema(Integer schemaID)
		{ return getDatabase().getSchema(schemaID); }

	/** Adds the specified schema */
	public Integer addSchema(Schema schema)
		{ return getDatabase().addSchema(schema); }
	
	/** Extends the specified schema */
	public Schema extendSchema(Integer schemaID)
	{
		Schema oldSchema = getSchema(schemaID);		
		if(oldSchema!=null)
			return getDatabase().extendSchema(getSchema(schemaID));
		return null;
	}
	
	/** Updates the specified schema */
	public boolean updateSchema(Schema schema)
	{
		Schema oldSchema = getSchema(schema.getId());
		if(oldSchema==null) return false;
		return getDatabase().updateSchema(schema);
	}
	
	/** Unlocks the specified schema */
	public boolean unlockSchema(Integer schemaID)
	{
		if(getDatabase().getSchemaMappingIDs(schemaID).size()>0) return false;
		if(!getSchema(schemaID).getType().equals("Manual")) return false;
		for(Integer childSchemaID : getManager().getSchemaRelationships().getChildren(schemaID))
			if(getSchema(childSchemaID).getLocked()) return false;
		return getDatabase().lockSchema(schemaID,false);
	}
	
	/** Locks the specified schema */
	public boolean lockSchema(Integer schemaID)
		{ return getDatabase().lockSchema(schemaID,true); }
	
	/** Indicates that the schema is able to be deleted */
	public boolean isDeletable(Integer schemaID)
	{
		Integer children = getManager().getSchemaRelationships().getChildren(schemaID).size();
		Integer dataSources = getManager().getDataSources().getDataSources(schemaID).size();
		Integer mappings = getDatabase().getSchemaMappingIDs(schemaID).size();
		return children==0 && dataSources==0 && mappings==0;
	}
	
	/** Returns the list of deletable schemas */
	public ArrayList<Integer> getDeletableSchemas()
		{ return getDatabase().getDeletableSchemas(); }
	
	/** Removes the specified schema */
	public boolean deleteSchema(Integer schemaID)
	{
		if(isDeletable(schemaID))
			return getDatabase().deleteSchema(schemaID);
		return false;
	}
}