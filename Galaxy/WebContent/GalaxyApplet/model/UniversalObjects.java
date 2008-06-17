// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model;

/**
 * Class for managing the objects in the schema repository
 * @author CWOLF
 */
public class UniversalObjects
{
	/** Indicates if the object is a schema */
	static public boolean isSchema(Integer objectID)
		{ return Schemas.getSchema(objectID)!=null; }
	
	/** Indicates if the object is a schema element */
	static public boolean isSchemaElement(Integer objectID)
		{ return Schemas.getSchemaElement(objectID)!=null; }

	/** Indicates if the object is a data source */
	static public boolean isDataSource(Integer objectID)
		{ return DataSources.getDataSource(objectID)!=null; }

	/** Indicates if the object is a group */
	static public boolean isGroup(Integer objectID)
		{ return Groups.getGroup(objectID)!=null; }
	
	/** Returns the object associated with the universal ID */
	static public Object getObject(Integer objectID)
	{
		if(isSchema(objectID)) return Schemas.getSchema(objectID);
		if(isSchemaElement(objectID)) return Schemas.getSchemaElement(objectID);
		if(isDataSource(objectID)) return DataSources.getDataSource(objectID);
		if(isGroup(objectID)) return Groups.getGroup(objectID);
		return null;
	}
	
	static public String getName(Integer objectID)
	{
		if(isSchema(objectID)) return Schemas.getSchema(objectID).getName();
		if(isSchemaElement(objectID)) return Schemas.getSchemaElement(objectID).getName();
		if(isDataSource(objectID)) return DataSources.getDataSource(objectID).getName();
		if(isGroup(objectID)) return Groups.getGroup(objectID).getName();
		return null;		
	}
}
