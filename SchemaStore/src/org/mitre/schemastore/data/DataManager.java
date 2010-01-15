// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import org.mitre.schemastore.data.database.Database;
import org.mitre.schemastore.data.database.DatabaseConnection;

/** Class for managing the SchemaStore data */
public class DataManager
{	
	/** Stores the database */
	private Database database = null;
	
	// Stores the various data caches
	private DataSources dataSources = null;
	private Tags tags = null;
	private Projects projects = null;
	private SchemaElements schemaElements = null;
	private SchemaRelationships schemaRelationships = null;
	private Schemas schemas = null;

	/** Constructs the data manager */
	public DataManager(DatabaseConnection connection)
	{
		// Constructs the database
		this.database = new Database(connection);
		
		// Constructs the data caches
		dataSources = new DataSources(this);
		tags = new Tags(this);
		projects = new Projects(this);
		schemaElements = new SchemaElements(this);
		schemaRelationships = new SchemaRelationships(this);
		schemas = new Schemas(this);
	}

	/** Returns the database */
	public Database getDatabase() { return database; }
	
	// Returns the various data caches
	public DataSources getDataSources() { return dataSources; }
	public Tags getTags() { return tags; }
	public Projects getProjects() { return projects; }
	public SchemaElements getSchemaElements() { return schemaElements; }
	public SchemaRelationships getSchemaRelationships() { return schemaRelationships; }
	public Schemas getSchemas() { return schemas; }
}