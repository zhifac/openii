// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for storing a mapping
 * @author CWOLF
 */
public class Mapping implements Serializable
{
	/** Stores the mapping id */
	private Integer id;

	/** Stores the mapping name */
	private String name;

	/** Stores the mapping description */
	private String description;

	/** Stores the mapping author */
	private String author;

	/** Stores the mapping schemas */
	private MappingSchema[] schemas;

	/** The left schema */
	private MappingSchema leftSchema = null;

	/** The right schema */
	private MappingSchema rightSchema = null;

	/** Constructs a default mapping */
	public Mapping() {}

	/** Constructs a mapping */
	public Mapping(Integer id, String name, String description, String author, MappingSchema[] schemas)
		{ this.id = id; this.name = name; this.description = description; this.author = author; this.schemas = schemas; }

	/** Copies the mapping */
	public Mapping copy()
		{ return new Mapping(getId(),getName(),getDescription(),getAuthor(),getSchemas()); }

	// Handles all mapping getters
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getDescription() { return description; }
	public String getAuthor() { return author; }
	public MappingSchema[] getSchemas() { return schemas; }
	public MappingSchema getLeftSchema() {
	    if (leftSchema!=null)
        	return leftSchema;
        else {
            for(MappingSchema s : schemas )
            {
                if( s.getSide() == MappingSchema.LEFT )
                {
                    return s;
                }
            }
        }
        return null;
    }
	public MappingSchema getRightSchema() {
        if (rightSchema!=null)
        	return rightSchema;
        else {
            for(MappingSchema s : schemas )
            {
                if( s.getSide() == MappingSchema.RIGHT )
                {
                    return s;
                }
            }
        }
        return null;
    }

	// Handles all mapping setters
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setDescription(String description) { this.description = description; }
	public void setAuthor(String author) { this.author = author; }
	public void setSchemas(MappingSchema[] schemas) { this.schemas = schemas; }
	public void setLeftSchema(MappingSchema s) { this.leftSchema = s; }
	public void setRightSchema(MappingSchema s) { this.rightSchema = s; }

	/** Returns the list of mapping schema IDs */
	public Integer[] getSchemaIDs()
	{
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>();
		if(schemas!=null)
			for(MappingSchema schema : schemas)
				schemaIDs.add(schema.getId());
		return schemaIDs.toArray(new Integer[0]);
	}

	/** Returns the hash code */
	public int hashCode()
		{ return id.hashCode(); }

	/** Indicates that two mappings are equals */
	public boolean equals(Object object)
	{
		if(object instanceof Integer) return ((Integer)object).equals(id);
		if(object instanceof Mapping) return ((Mapping)object).id.equals(id);
		return false;
	}

	/** String representation of the mapping */
	public String toString()
		{ return name; }
}