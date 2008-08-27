// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import model.Groups;

import org.mitre.schemastore.model.Group;

/** Interface for storing the selected groups for the group selection tree */
public class SelectedGroupsWithInferredParents implements SelectedGroups
{
	/** Hash tree to store selected groups */
	private HashSet<Integer> selectedGroups = new HashSet<Integer>();

	/** Hash tree to store inferred groups */
	private HashMap<Integer,Integer> inferredGroups = new HashMap<Integer,Integer>();

    /** Marks the specified group as inferred (and do likewise for all parents) */
    private void markAsInferred(Integer groupID, boolean addMode)
    {
    	// Increment the group count
    	Integer count = inferredGroups.get(groupID);
    	if(addMode)
    	{
    		if(selectedGroups.contains(groupID)) remove(groupID);
    		inferredGroups.put(groupID, count==null?1:count+1);
    	}
    	else if(count>1) inferredGroups.put(groupID, count-1);
    	else inferredGroups.remove(groupID);
    	
    	// Update the parent group count
    	Group group = Groups.getGroup(groupID);
    	if(group.getParentId()!=null) markAsInferred(group.getParentId(),addMode);
    }
	
	/** Constructs the selected groups class */
	public SelectedGroupsWithInferredParents(ArrayList<Integer> selectedGroups)
	{
		this.selectedGroups = new HashSet<Integer>(selectedGroups);
		refresh();
	}

	/** Refresh the selected/inferred groups */
	public void refresh()
	{
		inferredGroups.clear();
		for(Integer selectedGroup : selectedGroups)
		{
			Group group = Groups.getGroup(selectedGroup);
			if(group.getParentId()!=null) markAsInferred(group.getParentId(),true);
		}
	}

	/** Adds a selected group */
	public void add(Integer groupID)
	{
		selectedGroups.add(groupID);
		Group group = Groups.getGroup(groupID);
		if(group.getParentId()!=null) markAsInferred(group.getParentId(),true);
	}

	/** Removes a selected group */
	public void remove(Integer groupID)
	{
		selectedGroups.remove(groupID);
		Group group = Groups.getGroup(groupID);
		if(group.getParentId()!=null) markAsInferred(group.getParentId(),false);
	}
	
    /** Indicates if the specified group is selected */
    public boolean isSelected(Integer groupID)
    	{ return selectedGroups.contains(groupID); }
    
    /** Indicates if the specified group is inferred */
    public boolean isInferred(Integer groupID)
    	{ return inferredGroups.containsKey(groupID); }

    /** Returns the list of selected groups */
    public ArrayList<Integer> getSelectedGroups()
    	{ return new ArrayList<Integer>(selectedGroups); }
}
