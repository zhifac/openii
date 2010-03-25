package org.mitre.openii.views.manager;

import org.mitre.schemastore.model.Schema;

/** Class for storing a group schema */
public class SchemaInTag
{
	/** Stores the tag id to which this schema is associated */
	private Integer tagID = null;
	
	/** Stores the schema */
	private Schema schema = null;

	/** Constructs the tag schema */
	SchemaInTag(Integer tagID, Schema schema)
		{ this.tagID = tagID; this.schema = schema; }

	/** Returns the tag id to which this schema is associated */
	public Integer getTagID() { return tagID; }
	
	/** Returns the schema */
	public Schema getSchema() { return schema; }
	
	/** Returns the string representation of this tag schema */
	public String toString() { return schema.toString(); }
}
