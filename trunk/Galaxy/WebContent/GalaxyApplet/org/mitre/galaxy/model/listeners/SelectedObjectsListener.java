// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.model.listeners;

/** Listener class for selected objects */
public interface SelectedObjectsListener
{
	/** Indicates that the selected schema has been changed */
	public void selectedSchemaChanged();
	
	/** Indicates that the selected comparison schema has been changed */
	public void selectedComparisonSchemaChanged();
	
	/** Indicates that the selected groups have changed */
	public void selectedGroupsChanged();
}
