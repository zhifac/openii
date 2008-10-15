// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.schemastore.model.Group;

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
		{ return ServletConnection.getUnassignedSchemas(); }	

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
}
