// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import org.mitre.schemastore.model.Schema;

/**
 * Class storing schema properties 
 * @author CWOLF
 */
public class SchemaProperties
{	
	/** Stores the schema name */
	private String name;
	
	/** Stores the schema author */
	private String author;
	
	/** Stores the schema description */
	private String description;	
	
	/** Constructs schema properties */
	public SchemaProperties(String name, String author, String description)
		{ this.name = name; this.author = author; this.description = description; }
	
	/** Constructs schema properties from the given schema */
	public SchemaProperties(Schema schema)
		{ this.name = schema.getName(); this.author = schema.getAuthor(); this.description = schema.getDescription(); }
	
	// Handles all schema property getters
	public String getName() { return name; }
	public String getAuthor() { return author; }
	public String getDescription() { return description; }
}