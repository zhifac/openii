package org.mitre.openii.views.manager;

import org.mitre.schemastore.model.Schema;

/** Class for storing a group schema */
public class GroupSchema
{
	/** Stores the group id to which this schema is associated */
	private Integer groupID = null;
	
	/** Stores the schema */
	private Schema schema = null;

	/** Constructs the group schema */
	GroupSchema(Integer groupID, Schema schema)
		{ this.groupID = groupID; this.schema = schema; }

	/** Returns the group id to which this schema is associated */
	public Integer getGroupID() { return groupID; }
	
	/** Returns the schema */
	public Schema getSchema() { return schema; }
	
	/** Returns the string representation of this group schema */
	public String toString() { return schema.toString(); }
}
