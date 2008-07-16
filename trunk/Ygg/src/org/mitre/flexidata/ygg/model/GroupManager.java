// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.model;

import java.util.ArrayList;

import org.mitre.schemastore.model.Group;

/** Manages group information */
public class GroupManager
{
	/** Returns the groups associated with the repository */
	static public ArrayList<Group> getGroups()
		{ try { return SchemaStore.getClient().getGroups(); } catch(Exception e) { return new ArrayList<Group>(); } }
}
