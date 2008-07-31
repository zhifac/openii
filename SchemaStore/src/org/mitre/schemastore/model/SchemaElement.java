// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;

/**
 * Class for storing a schema element
 * @author CWOLF
 */
public class SchemaElement implements Serializable
{
	/** Stores the schema element id */
	private Integer id;
	
	/** Stores the schema element name */
	protected String name;
	
	/** Stores the schema element description */
	private String description;

	/** Stores the schema element base ID */
	private Integer base;
	
	/** Constructs a default schema element */
	public SchemaElement() {}
	
	/** Constructs the schema element */
	protected SchemaElement(Integer id, String name, String description, Integer base)
	{
		this.id = id;
		this.name = name==null ? "" : name;
		this.description = description==null ? "" : description;
		this.base = base;
	}
	
	// Handles all schema element getters
	public Integer getId() { return id; }
	public String getName() { return name; }
	public String getDescription() { return description; }
	public Integer getBase() { return base; }
	
	// Handles all schema element setters
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name==null ? "" : name; }
	public void setDescription(String description) { this.description = description; }
	public void setBase(Integer base) { this.base = base; }
	
	/** Generates a hash code for the match */
	public int hashCode()
		{ return id; }
	
	/** Indicates that two schema elements are equals */
	public boolean equals(Object schemaElement)
		{ return schemaElement instanceof SchemaElement && ((SchemaElement)schemaElement).id.equals(id); }
	
	/** String representation of the schema element */
	public String toString()
		{ return name; }
}
