// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.groupPane;

import java.util.ArrayList;
import java.util.HashSet;


import org.mitre.galaxy.model.Groups;
import org.mitre.schemastore.model.Group;

/** Interface for storing the selected groups for the group selection tree */
public class SelectedGroups
{
	/** Hash tree to store selected groups */
	protected HashSet<Integer> selectedGroups = new HashSet<Integer>();

	/** Hash tree to store inferred groups */
	protected HashSet<Integer> inferredGroups = new HashSet<Integer>();

    /** Marks the specified group as inferred (and do likewise for all children) */
    private void markAsInferred(Integer groupID, boolean addMode)
    {
    	// Add or removed group from list
    	if(addMode)
    	{
    		if(selectedGroups.contains(groupID)) remove(groupID);
    		inferredGroups.add(groupID);
    	}
    	else inferredGroups.remove(groupID);
    	
    	// Mark children as inferred
    	for(Group group : Groups.getSubGroups(groupID))
    		markAsInferred(group.getId(),addMode);
    }
	
	/** Constructs the selected groups class */
	public SelectedGroups(ArrayList<Integer> selectedGroups)
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
	    	for(Group subgroup : Groups.getSubGroups(group.getId()))
	    		markAsInferred(subgroup.getId(),true);
		}		
	}
	
	/** Adds a selected group */
	public void add(Integer groupID)
	{
		// Add group to selection list
		selectedGroups.add(groupID);
		Group group = Groups.getGroup(groupID);
    	for(Group subgroup : Groups.getSubGroups(group.getId()))
    		markAsInferred(subgroup.getId(),true);
	}

	/** Removes a selected group */
	public void remove(Integer groupID)
	{
		selectedGroups.remove(groupID);
		Group group = Groups.getGroup(groupID);
    	for(Group subgroup : Groups.getSubGroups(group.getId()))
    		markAsInferred(subgroup.getId(),false);
	}
	
    /** Indicates if the specified group is selected */
    public boolean isSelected(Integer groupID)
    	{ return selectedGroups.contains(groupID); }
    
    /** Indicates if the specified group is inferred */
    public boolean isInferred(Integer groupID)
    	{ return inferredGroups.contains(groupID); }

    /** Returns the list of selected groups */
    public ArrayList<Integer> getSelectedGroups()
    	{ return new ArrayList<Integer>(selectedGroups); }
}
