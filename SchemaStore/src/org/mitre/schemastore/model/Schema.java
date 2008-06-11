// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;

/**
 * Class storing a single schema from the repository
 * @author CWOLF
 */
public class Schema implements Serializable
{	
	/** Stores the schema id */
	private Integer id;
	
	/** Stores the schema name */
	private String name;
	
	/** Stores the schema description */
	private String description;
	
	/** Indicates if the schema has been committed */
	private boolean committed;
	
	/** Constructs a default schema */
	public Schema() {}
	
	/** Constructs a schema */
	public Schema(Integer id, String name, String description, boolean committed)
		{ this.id = id; this.name = name; this.description = description; this.committed = committed; }
	
	// Handles all schema getters
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getDescription() { return description; }
	public boolean getCommitted() { return committed; }

	// Handles all schema setters
	public void setId(Integer id) { this.id = id; }
	public void setCommitted(boolean committed) { this.committed = committed; }
	public void setDescription(String description) { this.description = description; }
	public void setName(String name) { this.name = name; }
	
	/** Indicates that two schemas are equals */
	public boolean equals(Object schema)
		{ return schema instanceof Schema && ((Schema)schema).id.equals(id); }
	
	/** String representation of the schema */
	public String toString()
		{ return name; }
}