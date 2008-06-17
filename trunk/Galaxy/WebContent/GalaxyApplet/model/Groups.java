// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.schemastore.model.Group;

import model.listeners.GroupsListener;
import model.server.ServletConnection;

/**
 * Class for managing the groups in the schema repository
 * @author CWOLF
 */
public class Groups
{
	/** Caches schema information */
	static private HashMap<Integer,Group> groups = null;
	
	/** Caches group schemas */
	static private HashMap<Integer,ArrayList<Integer>> groupSchemaHash = new HashMap<Integer,ArrayList<Integer>>();
	
	/** Caches schema groups */
	static private HashMap<Integer,ArrayList<Integer>> schemaGroupHash = new HashMap<Integer,ArrayList<Integer>>();
	
	/** List of listeners monitoring group events */
	static private ArrayList<GroupsListener> listeners = new ArrayList<GroupsListener>();
		
	//---------------
	// Group Actions
	//---------------
	
	/** Initializes the groups list */
	static private void initGroups()
	{
		groups = new HashMap<Integer,Group>();
		for(Group group : ServletConnection.getGroups())
			groups.put(group.getId(),group);	
	}

	/** Adds the specified group to the database */
	static public boolean addGroup(Group group)
	{
		Integer groupID = ServletConnection.addGroup(group);
		if(groupID!=null)
		{
			group.setId(groupID);
			groups.put(group.getId(),group);
			for(GroupsListener listener : listeners)
				listener.groupAdded(groupID);
			return true;
		}
		return false;
	}
	
	/** Updates the specified group in the database */
	static public boolean updateGroup(Group group)
	{
		// Don't update group in database if no changes made
		Group oldGroup = getGroup(group.getId());
		if(oldGroup.getName().equals(group.getName()))
			return true;
		
		// Update group in database
		if(ServletConnection.updateGroup(group))
		{
			groups.put(group.getId(),group);
			for(GroupsListener listener : listeners)
				listener.groupUpdated(group.getId());
			return true;
		}
		return false;
	}
	
	/** Deletes the specified group from the database */
	static public boolean deleteGroup(Group group)
	{
		if(ServletConnection.deleteGroup(group.getId()))
		{
			groups.remove(group.getId());
			for(GroupsListener listener : listeners)
				listener.groupRemoved(group.getId());
			return true;
		}
		return false;
	}
	
	/** Returns a list of base groups */
	static public ArrayList<Group> getBaseGroups()
	{
		if(groups==null) initGroups();
		ArrayList<Group> baseGroups = new ArrayList<Group>();
		for(Group group : groups.values())
			if(group.getParentId()==null)
				baseGroups.add(group);
		return baseGroups;
	}
	
	/** Returns a list of sub groups for the specified group */
	static public ArrayList<Group> getSubGroups(Integer groupID)
	{
		if(groups==null) initGroups();
		ArrayList<Group> subGroups = new ArrayList<Group>();
		for(Group group : groups.values())
			if(group.getParentId()!=null && group.getParentId().equals(groupID))
				subGroups.add(group);
		return subGroups;		
	}
	
	/** Returns the specified group */
	static public Group getGroup(Integer groupID)
	{
		if(groups==null) initGroups(); 
		return groups.get(groupID);
	}
	
	/** Returns a list of unassigned schemas */
	static public ArrayList<Integer> getUnassignedSchemas()
		{ return Schemas.filter(ServletConnection.getUnassignedSchemas()); }	

	/** Returns a list of schemas associated with the specified group */
	static public ArrayList<Integer> getGroupSchemas(Integer groupID)
	{
		ArrayList<Integer> groupSchemas = groupSchemaHash.get(groupID);
		if(groupSchemas==null)
			groupSchemaHash.put(groupID, groupSchemas=ServletConnection.getGroupSchemas(groupID));
		return groupSchemas;
	}
	
	/** Returns the groups associated with the specified schema */
	static public ArrayList<Integer> getSchemaGroups(Integer schemaID)
	{
		ArrayList<Integer> schemaGroups = schemaGroupHash.get(schemaID);
		if(schemaGroups==null)
			schemaGroupHash.put(schemaID, schemaGroups=ServletConnection.getSchemaGroups(schemaID));
		return schemaGroups;
	}

	/** Returns the number of schemas classified under the specified group */
	static public Integer getGroupSchemaCount(Integer groupID)
	{
		Integer count = getGroupSchemas(groupID).size();
		for(Group subGroup : getSubGroups(groupID))
			count += getGroupSchemaCount(subGroup.getId());
		return count;
	}
	
	/** Adds a group to a schema */
	static private void addGroupToSchema(Integer groupID, Integer schemaID)
	{
		if(ServletConnection.addGroupToSchema(schemaID, groupID))
		{
			groupSchemaHash.remove(groupID);
			schemaGroupHash.remove(schemaID);
			for(GroupsListener listener : listeners)
				listener.schemaGroupAdded(schemaID,groupID);
		}		
	}
	
	/** Removes a group from a schema */
	static private void removeGroupFromSchema(Integer groupID, Integer schemaID)
	{
		if(ServletConnection.removeGroupFromSchema(schemaID, groupID))
		{
			groupSchemaHash.remove(groupID);
			schemaGroupHash.remove(schemaID);
			for(GroupsListener listener : listeners)
				listener.schemaGroupRemoved(schemaID,groupID);
		}		
	}
	
	/** Set the list of schemas associated with the specified group */
	static public void setGroupSchemas (Integer groupID, ArrayList<Integer> schemas)
	{ 
		// Remove schemas from the group as needed
		ArrayList<Integer> currSchemas = getGroupSchemas(groupID);
		for(Integer currSchema : currSchemas)
			if(!schemas.contains(currSchema))
				removeGroupFromSchema(groupID,currSchema);
				
		// Add schemas to the group as needed
		for(Integer schema : schemas)
			if(!currSchemas.contains(schema))
				addGroupToSchema(groupID,schema);
	}
	
	/** Set the list of groups associated with the specified schema */
	static public void setSchemaGroups(Integer schemaID, ArrayList<Integer> groups)
	{ 
		// Remove groups from the schema as needed
		ArrayList<Integer> currGroups = getSchemaGroups(schemaID);
		for(Integer currGroup : currGroups)
			if(!groups.contains(currGroup))
				removeGroupFromSchema(currGroup,schemaID);
					
		// Add groups to the schema as needed
		for(Integer group : groups)
			if(!currGroups.contains(group))
				addGroupToSchema(group,schemaID);
	}
	
	/** Sorts the list of group */
	static public ArrayList<Group> sort(ArrayList<Group> groups)
	{
		final class GroupComparator implements Comparator<Group>
		{
			public int compare(Group group1, Group group2)
				{ return group1.getName().compareTo(group2.getName()); }
		}
		Collections.sort(groups,new GroupComparator());
		return groups;
	}

	/** Sorts the list of groups by ID */
	static public ArrayList<Integer> sortByID(ArrayList<Integer> groups)
	{
		final class GroupComparator implements Comparator<Integer>
		{
			public int compare(Integer group1, Integer group2)
				{ return getGroup(group1).getName().compareTo(getGroup(group2).getName()); }
		}
		Collections.sort(groups,new GroupComparator());
		return groups;
	}
	
	//-----------------
	// Group Listeners
	//-----------------
	
	/** Adds a listener monitoring group events */
	static public void addGroupsListener(GroupsListener listener)
		{ listeners.add(listener); }
	
	/** Removes a listener monitoring group events */
	static public void removeGroupsListener(GroupsListener listener)
		{ listeners.remove(listener); }
	
	/** Gets the current group listeners */
	static public ArrayList<GroupsListener> getGroupsListeners()
		{ return new ArrayList<GroupsListener>(listeners); }
}
