// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.tagPane;

import java.util.ArrayList;
import java.util.HashSet;


import org.mitre.galaxy.model.Tags;
import org.mitre.schemastore.model.Tag;

/** Interface for storing the selected tags for the tag selection tree */
public class SelectedTags
{
	/** Hash tree to store selected tags */
	protected HashSet<Integer> selectedTags = new HashSet<Integer>();

	/** Hash tree to store inferred tags */
	protected HashSet<Integer> inferredTags = new HashSet<Integer>();

    /** Marks the specified tag as inferred (and do likewise for all children) */
    private void markAsInferred(Integer tagID, boolean addMode)
    {
    	// Add or removed tag from list
    	if(addMode)
    	{
    		if(selectedTags.contains(tagID)) remove(tagID);
    		inferredTags.add(tagID);
    	}
    	else inferredTags.remove(tagID);
    	
    	// Mark children as inferred
    	for(Tag tag : Tags.getSubcategories(tagID))
    		markAsInferred(tag.getId(),addMode);
    }
	
	/** Constructs the selected tags class */
	public SelectedTags(HashSet<Integer> selectedTags)
		{ this.selectedTags = selectedTags; refresh(); }

	/** Refresh the selected/inferred tags */
	public void refresh()
	{
		inferredTags.clear();
		for(Integer selectedTag : selectedTags)
		{
			Tag tag = Tags.getTag(selectedTag);
	    	for(Tag subcategory : Tags.getSubcategories(tag.getId()))
	    		markAsInferred(subcategory.getId(),true);
		}		
	}
	
	/** Adds a selected tag */
	public void add(Integer tagID)
	{
		// Add tag to selection list
		selectedTags.add(tagID);
		Tag tag = Tags.getTag(tagID);
    	for(Tag subcategory : Tags.getSubcategories(tag.getId()))
    		markAsInferred(subcategory.getId(),true);
	}

	/** Removes a selected tag */
	public void remove(Integer tagID)
	{
		selectedTags.remove(tagID);
		Tag tag = Tags.getTag(tagID);
    	for(Tag subcategory : Tags.getSubcategories(tag.getId()))
    		markAsInferred(subcategory.getId(),false);
	}
	
    /** Indicates if the specified tag is selected */
    public boolean isSelected(Integer tagID)
    	{ return selectedTags.contains(tagID); }
    
    /** Indicates if the specified tag is inferred */
    public boolean isInferred(Integer tagID)
    	{ return inferredTags.contains(tagID); }

    /** Returns the list of selected tags */
    public ArrayList<Integer> getSelectedTags()
    	{ return new ArrayList<Integer>(selectedTags); }
}
