// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane;

import java.util.ArrayList;

/** Interface for storing the selected groups for the group selection tree */
public interface SelectedGroups
{
	/** Refreshes the selected/inferred groups */
	public void refresh();
	
	/** Adds a selected group */
	public void add(Integer groupID);

	/** Removes a selected group */
	public void remove(Integer groupID);
	
    /** Indicates if the specified group is selected */
    public boolean isSelected(Integer groupID);
     
    /** Indicates if the specified group is inferred */
    public boolean isInferred(Integer groupID);

    /** Returns the list of selected groups */
    public ArrayList<Integer> getSelectedGroups();
}
