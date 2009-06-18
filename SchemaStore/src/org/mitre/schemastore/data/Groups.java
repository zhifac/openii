// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.data.database.Database.SchemaGroup;
import org.mitre.schemastore.model.Group;

/** Class for managing the groups in the schema repository */
public class Groups extends DataCache
{
	/** Stores the validation number */
	private Integer validationNumber = 0;
	
	/** Stores a mapping of schema groups */
	private HashMap<Integer,ArrayList<Integer>> schemaGroups = new HashMap<Integer,ArrayList<Integer>>();
	
	/** Stores a mapping of group schemas */
	private HashMap<Integer,ArrayList<Integer>> groupSchemas = new HashMap<Integer,ArrayList<Integer>>();
	
	/** Constructs the groups cache */
	Groups(DataManager manager)
		{ super(manager); }
	
	/** Refreshes the schema groups */
	private void recacheAsNeeded()
	{
		// Check to see if the schema groups have changed any
		Integer newValidationNumber = getDatabase().getSchemaGroupValidationNumber();
		if(!newValidationNumber.equals(validationNumber))
		{
			validationNumber = newValidationNumber;
			
			// Clears the cached schema groups
			schemaGroups.clear();
			groupSchemas.clear();
			
			// Caches the schema groups
			for(SchemaGroup schemaGroup : getDatabase().getSchemaGroups())
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
	public ArrayList<Group> getGroups()
		{ return getDatabase().getGroups(); }

	/** Returns the specified group */
	public Group getGroup(Integer groupID)
		{ return getDatabase().getGroup(groupID); }
	
	/** Returns the listing of subgroups for the specified group */
	public ArrayList<Group> getSubgroups(Integer groupID)
		{ return getDatabase().getSubgroups(groupID); }
	
	/** Adds the specified group */
	public Integer addGroup(Group group)
		{ return getDatabase().addGroup(group); }
	
	/** Updates the specified group */
	public boolean updateGroup(Group group)
		{ return getDatabase().updateGroup(group); }
	
	/** Removes the specified group (and all subgroups) */
	public boolean deleteGroup(Integer groupID)
	{
		for(Group subgroup : getSubgroups(groupID))
			if(!deleteGroup(subgroup.getId())) return false;
		return getDatabase().deleteGroup(groupID);
	}
	
	/** Returns the list of group schemas */
	public ArrayList<Integer> getGroupSchemas(Integer groupID)
	{
		recacheAsNeeded();
		if(groupSchemas.get(groupID)!=null)
			return groupSchemas.get(groupID);
		return new ArrayList<Integer>();
	}
	
	/** Returns the list of schema groups */
	public ArrayList<Integer> getSchemaGroups(Integer schemaID)
	{
		recacheAsNeeded();
		if(schemaGroups.get(schemaID)!=null)
			return schemaGroups.get(schemaID);
		return new ArrayList<Integer>();
	}
	
	/** Adds a group to the specified schema */
	public Boolean addGroupToSchema(Integer schemaID, Integer groupID)
		{ return getDatabase().addGroupToSchema(schemaID, groupID); }

	/** Removes a group from the specified schema */
	public Boolean removeGroupFromSchema(Integer schemaID, Integer groupID)
		{ return getDatabase().removeGroupFromSchema(schemaID, groupID); }
}
