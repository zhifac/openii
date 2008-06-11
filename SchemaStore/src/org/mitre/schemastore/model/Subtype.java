// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

/**
 * Class for storing a subtype relationship
 * @author CWOLF
 */
public class Subtype extends SchemaElement
{
	/** Stores the subtype's parent id */
	private Integer parentID;
	
	/** Stores the subtype's child id */
	private Integer childID;
	
	/** Constructs a default subtype relationship */
	public Subtype() {}

	/** Constructs the subtype relationship */
	public Subtype(Integer id, Integer parentID, Integer childID, Integer base)
		{ super(id,"","",base); this.parentID=parentID; this.childID=childID; }
	
	// Handles all containment getters
	public Integer getParentID() { return parentID; }
	public Integer getChildID() { return childID; }
	
	// Handles all containment setters
	public void setParentID(Integer parentID) { this.parentID = parentID; }
	public void setChildID(Integer childID) { this.childID = childID; }	
}
