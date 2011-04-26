// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;

import org.mitre.schemastore.data.database.SchemaDataCalls;
import org.mitre.schemastore.model.Schema;

/** Class for managing the current list of schemas in the schema repository */
public class SchemaCache extends DataCache
{
	/** Enumeration for the type of schema */
	public enum SchemaType {SCHEMA,VOCABULARY,THESAURUS};
	
	/** Stores reference to the schema data calls */
	private SchemaDataCalls dataCalls = null;
	
	/** Indicates if the type is aligned */
	private boolean isAlignedType(String type, SchemaType schemaType)
	{
		if(schemaType!=SchemaType.SCHEMA) return schemaType.toString().equals(type);
		else for(SchemaType value : SchemaType.values())
			if(value!=SchemaType.SCHEMA && value.toString().equals(type)) return false;
		return true;
	}
	
	/** Constructs the schema elements cache */
	SchemaCache(DataManager manager, SchemaDataCalls dataCalls)
		{ super(manager); this.dataCalls=dataCalls; }
	
	/** Returns a listing of all schemas */
	public ArrayList<Schema> getSchemas(SchemaType schemaType)
		{ return dataCalls.getSchemas(schemaType); }
	
	/** Returns the specified schema */
	public Schema getSchema(Integer schemaID)
		{ return dataCalls.getSchema(schemaID); }

	/** Adds the specified schema */
	public Integer addSchema(Schema schema, SchemaType schemaType)
	{
		if(!isAlignedType(schema.getType(),schemaType)) return null;
		return dataCalls.addSchema(schema);
	}

	/** Extends the specified schema */
	public Schema extendSchema(Integer schemaID)
	{
		Schema oldSchema = getSchema(schemaID);		
		if(oldSchema!=null)
			return dataCalls.extendSchema(getSchema(schemaID));
		return null;
	}
	
	/** Updates the specified schema */
	public boolean updateSchema(Schema schema, SchemaType schemaType)
	{
		// Don't update schema if doesn't exist
		Schema oldSchema = getSchema(schema.getId());
		if(oldSchema==null) return false;

		// Don't update schema if type mismatch
		if(!isAlignedType(schema.getType(),schemaType)) return false;

		// Update the schema
		return dataCalls.updateSchema(schema);
	}
	
	/** Unlocks the specified schema */
	public boolean unlockSchema(Integer schemaID)
	{
		if(getManager().getProjectCache().getSchemaProjectIDs(schemaID).size()>0) return false;
		if(!getSchema(schemaID).getType().equals("Manual")) return false;
		for(Integer childSchemaID : getManager().getSchemaRelationshipCache().getChildren(schemaID))
			if(getSchema(childSchemaID).getLocked()) return false;
		return dataCalls.lockSchema(schemaID,false);
	}
	
	/** Locks the specified schema */
	public boolean lockSchema(Integer schemaID)
		{ return dataCalls.lockSchema(schemaID,true); }
	
	/** Indicates that the schema is able to be deleted */
	public boolean isDeletable(Integer schemaID)
		{ return getDeletableSchemas().contains(schemaID); }
	
	/** Returns the list of deletable schemas */
	public ArrayList<Integer> getDeletableSchemas()
		{ return dataCalls.getDeletableSchemas(); }
	
	/** Removes the specified schema */
	public boolean deleteSchema(Integer schemaID)
	{
		if(isDeletable(schemaID))
			return dataCalls.deleteSchema(schemaID);
		return false;
	}
}