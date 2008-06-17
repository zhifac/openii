// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.listeners;

/** Listener class for groups */
public interface GroupsListener
{
	/** Indicates that a group has been added */
	public void groupAdded(Integer groupID);

	/** Indicates that a group has been updated */
	public void groupUpdated(Integer groupID);
	
	/** Indicates that a group has been removed */
	public void groupRemoved(Integer groupID);
	
	/** Indicates that a schema group has been added */
	public void schemaGroupAdded(Integer schemaID, Integer groupID);

	/** Indicates that a schema group has been removed */
	public void schemaGroupRemoved(Integer schemaID, Integer groupID);
}
