// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.data.database.Database;
import org.mitre.schemastore.data.database.Database.SchemaGroup;
import org.mitre.schemastore.model.Group;

/** Class for managing the groups in the schema repository */
public class Groups
{
	/** Stores the validation number */
	static private Integer validationNumber = 0;
	
	/** Stores a mapping of schema groups */
	static private HashMap<Integer,ArrayList<Integer>> schemaGroups = new HashMap<Integer,ArrayList<Integer>>();
	
	/** Stores a mapping of group schemas */
	static private HashMap<Integer,ArrayList<Integer>> groupSchemas = new HashMap<Integer,ArrayList<Integer>>();
	
	/** Refreshes the schema groups */
	static private void recacheAsNeeded()
	{
		// Check to see if the schema groups have changed any
		Integer newValidationNumber = Database.getSchemaGroupValidationNumber();
		if(!newValidationNumber.equals(validationNumber))
		{
			validationNumber = newValidationNumber;
			
			// Clears the cached schema groups
			schemaGroups.clear();
			groupSchemas.clear();
			
			// Caches the schema groups
			for(SchemaGroup schemaGroup : Database.getSchemaGroups())
			{
				// Place in schema group hash
				ArrayList<Integer> schemaGroupArray = schemaGroups.get(schemaGroup.getSchemaID());
				if(schemaGroupArray==null) schemaGroups.put(schemaGroup.getSchemaID(),schemaGroupArray = new ArrayList<Integer>());
				schemaGroupArray.add(schemaGroup.getGroupID());
				
				// Place in group schema hash
				ArrayList<Integer> groupSchemaArray = groupSchemas.get(schemaGroup.getGroupID());
				if(groupSchemaArray==null) groupSchemas.put(schemaGroup.getGroupID(),groupSchemaArray = new ArrayList<Integer>());
				groupSchemaArray.add(schemaGroup.getSchemaID());
			}
		}
	}
	
	/** Returns a listing of all groups */
	static public ArrayList<Group> getGroups()
		{ return Database.getGroups(); }

	/** Returns the specified group */
	static public Group getGroup(Integer groupID)
		{ return Database.getGroup(groupID); }
	
	/** Returns the listing of subgroups for the specified group */
	static public ArrayList<Group> getSubgroups(Integer groupID)
		{ return Database.getSubgroups(groupID); }
	
	/** Adds the specified group */
	static public Integer addGroup(Group group)
		{ return Database.addGroup(group); }
	
	/** Updates the specified group */
	static public boolean updateGroup(Group group)
		{ return Database.updateGroup(group); }
	
	/** Removes the specified group (and all subgroups) */
	static public boolean deleteGroup(Integer groupID)
	{
		for(Group subgroup : getSubgroups(groupID))
			if(!deleteGroup(subgroup.getId())) return false;
		return Database.deleteGroup(groupID);
	}
	
	/** Returns the list of group schemas */
	static public ArrayList<Integer> getGroupSchemas(Integer groupID)
	{
		recacheAsNeeded();
		if(groupSchemas.get(groupID)!=null)
			return groupSchemas.get(groupID);
		return new ArrayList<Integer>();
	}
	
	/** Returns the list of schema groups */
	static public ArrayList<Integer> getSchemaGroups(Integer schemaID)
	{
		recacheAsNeeded();
		if(schemaGroups.get(schemaID)!=null)
			return schemaGroups.get(schemaID);
		return new ArrayList<Integer>();
	}
	
	/** Adds a group to the specified schema */
	static public Boolean addGroupToSchema(Integer schemaID, Integer groupID)
		{ return Database.addGroupToSchema(schemaID, groupID); }

	/** Removes a group from the specified schema */
	static public Boolean removeGroupFromSchema(Integer schemaID, Integer groupID)
		{ return Database.removeGroupFromSchema(schemaID, groupID); }
}
