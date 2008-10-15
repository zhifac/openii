// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model;

import java.util.ArrayList;
import java.util.HashSet;

import model.listeners.SelectedObjectsListener;

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
	
	/** Stores the selected groups */
	static private HashSet<Integer> selectedGroups = new HashSet<Integer>();

	/** Stores the selected group schemas */
	static private HashSet<Integer> selectedGroupSchemas = new HashSet<Integer>();
	
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
	
	/** Returns the selected groups */
	static public ArrayList<Integer> getSelectedGroups()
		{ return new ArrayList<Integer>(selectedGroups); }
	
	/** Sets the selected groups */
	static public void setSelectedGroups(ArrayList<Integer> groups)
	{		
		// Update the selected groups
		selectedGroups = new HashSet<Integer>(groups);
		selectedGroupSchemas.clear();
		for(Integer groupID : groups)
			selectedGroupSchemas.addAll(Groups.getGroupSchemas(groupID));

		// Inform listeners of change to selected groups
		fireSelectedGroupsChangedEvent();
	}
	
	/** Indicates if the specified schema is contained in selected groups */
	static public boolean inSelectedGroups(Integer schemaID)
	{
		if(selectedGroups.size()==0) return true;
		return selectedGroupSchemas.contains(schemaID);
	}
	
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
	
	/** Inform listeners that the selected groups have changed */
	static private void fireSelectedGroupsChangedEvent()
	{
		for(SelectedObjectsListener listener : new ArrayList<SelectedObjectsListener>(selectedObjectListeners))
			listener.selectedGroupsChanged();
	}
}
