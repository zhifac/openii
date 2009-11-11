// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.model;

import java.util.ArrayList;
import java.util.HashSet;

import org.mitre.galaxy.model.listeners.SelectedObjectsListener;


/**
 * Class for storing selected objects
 * @author CWOLF
 */
public class SelectedObjects
{	
	/** Stores the selected schema */
	static private Integer selectedSchema = null;
	
	/** Stores the selected comparison schema */
	static private Integer selectedComparisonSchema = null;
	
	/** Stores the selected tags */
	static private HashSet<Integer> selectedTags = new HashSet<Integer>();

	/** Stores the selected tag schemas */
	static private HashSet<Integer> selectedTagSchemas = new HashSet<Integer>();
	
	/** List of listeners monitoring selected object events */
	static private ArrayList<SelectedObjectsListener> selectedObjectListeners = new ArrayList<SelectedObjectsListener>();
	
	/** Returns the selected schema */
	static public Integer getSelectedSchema()
		{ return selectedSchema; }
	
	/** Sets the selected schema */
	static public void setSelectedSchema(Integer schemaID)
	{
		selectedSchema = schemaID;
		setSelectedComparisonSchema(null);
		fireSelectedSchemaChangedEvent();
	}
	
	/** Returns the selected comparison schema */
	static public Integer getSelectedComparisonSchema()
		{ return selectedComparisonSchema; }
	
	/** Sets the selected comparison schema */
	static public void setSelectedComparisonSchema(Integer schemaID)
	{
		selectedComparisonSchema = (schemaID==null || schemaID.equals(selectedComparisonSchema)) ? null : schemaID;
		fireSelectedComparisonSchemaChangedEvent();
	}
	
	/** Returns the selected tags */
	static public HashSet<Integer> getSelectedTags()
		{ return new HashSet<Integer>(selectedTags); }
	
	/** Sets the selected tags */
	static public void setSelectedTags(ArrayList<Integer> tags)
	{		
		// Update the selected tags
		selectedTags = new HashSet<Integer>(tags);
		selectedTagSchemas.clear();
		for(Integer tagID : tags)
			selectedTagSchemas.addAll(Tags.getTagSchemas(tagID));

		// Inform listeners of change to selected tags
		fireSelectedTagsChangedEvent();
	}
	
	/** Indicates if the specified schema is contained in selected tags */
	static public boolean inSelectedTags(Integer schemaID)
	{
		if(selectedTags.size()==0) return true;
		return selectedTagSchemas.contains(schemaID);
	}
	
	/** Returns the selected tag schemas */
	static public HashSet<Integer> getSelectedTagSchemas()
		{ return new HashSet<Integer>(selectedTagSchemas); }
	
	/** Adds a listener monitoring selected schema events */
	static public void addSelectedObjectsListener(SelectedObjectsListener listener)
		{ selectedObjectListeners.add(listener); }
	
	/** Removes a listener monitoring selected schema events */
	static public void removeSelectedObjectsListener(SelectedObjectsListener listener)
		{ selectedObjectListeners.remove(listener); }
	
	/** Inform listeners that the selected schema has been changed */
	static private void fireSelectedSchemaChangedEvent()
	{
		for(SelectedObjectsListener listener : new ArrayList<SelectedObjectsListener>(selectedObjectListeners))
			listener.selectedSchemaChanged();
	}
	
	/** Inform listeners that the selected comparison schema has been changed */
	static private void fireSelectedComparisonSchemaChangedEvent()
	{
		for(SelectedObjectsListener listener : new ArrayList<SelectedObjectsListener>(selectedObjectListeners))
			listener.selectedComparisonSchemaChanged();
	}
	
	/** Inform listeners that the selected tags have changed */
	static private void fireSelectedTagsChangedEvent()
	{
		for(SelectedObjectsListener listener : new ArrayList<SelectedObjectsListener>(selectedObjectListeners))
			listener.selectedTagsChanged();
	}
}
