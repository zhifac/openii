// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;

/**
 * Class for storing a schema group
 * @author CWOLF
 */
public class Group implements Serializable
{
	/** Stores the group id */
	private Integer id;
	
	/** Stores the group name */
	private String name;
	
	/** Stores the parent category for this group */
	private Integer parentID;
	
	/** Constructs a default group */
	public Group() {}
	
	/** Constructs a mapping */
	public Group(Integer id, String name, Integer parentID)
		{ this.id = id; this.name = name; this.parentID = (parentID==null || parentID==0) ? null : parentID; }
	
	// Handles all mapping getters
	public Integer getId() { return id; }
	public String getName() { return name; }
	public Integer getParentId() { return parentID; }
	
	// Handles all mapping setters
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setParentId(Integer parentID) { this.parentID = (parentID==null || parentID==0) ? null : parentID; }
	
	/** String representation of the group */
	public String toString()
		{ return name; }
}