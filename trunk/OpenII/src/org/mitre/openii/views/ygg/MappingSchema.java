package org.mitre.openii.views.ygg;

import org.mitre.schemastore.model.Schema;

/** Class for storing a mapping schema */
public class MappingSchema
{
	/** Stores the mapping id to which this schema is associated */
	private Integer mappingID = null;
	
	/** Stores the schema */
	private Schema schema = null;

	/** Constructs the mapping schema */
	MappingSchema(Integer mappingID, Schema schema)
		{ this.mappingID = mappingID; this.schema = schema; }

	/** Returns the mapping id to which this schema is associated */
	public Integer getMappingID() { return mappingID; }
	
	/** Returns the schema */
	public Schema getSchema() { return schema; }
	
	/** Returns the string representation of this mapping schema */
	public String toString() { return schema.toString(); }
}
