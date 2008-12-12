// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.importers;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import java.net.URI;
import java.util.ArrayList;

/** Abstract Importer class */
public abstract class Importer
{
	// Defines the various types of URIs that may be requested
	public static final Integer NONE = 0;
	public static final Integer SCHEMA = 1;
	public static final Integer REPOSITORY = 2;
	public static final Integer FILE = 3;
	public static final Integer SCHEMASTOREARCHIVER = 3;
	
	// Defines the various base domain types
	public static final String ANY = "Any";
	public static final String INTEGER = "Integer";
	public static final String DOUBLE = "Double";
	public static final String STRING = "String";
	public static final String DATETIME = "DateTime";
	public static final String BOOLEAN = "Boolean";
	
	/** Stores the URI being imported */
	protected URI uri;
	
	/** Stores the schema store client to which this schema is being imported */
	protected SchemaStoreClient client = null;
	
	/** Stores a auto-increment counter for handing out IDs */
	private static Integer autoIncrementedId = 0;
	
	/** Returns the next available ID */
	public static Integer nextId()
		{ return autoIncrementedId++; }

	/** Set the schema store client */
	void setClient(SchemaStoreClient client)
		{ this.client = client; }
	
	/** Returns the importer name */
	abstract public String getName();
	
	/** Returns the importer description */
	abstract public String getDescription();
	
	/** Returns the importer URI type */
	abstract public Integer getURIType();
	
	/** Returns the importer URI file types (only needed when URI type is FILE) */
	public ArrayList<String> getFileTypes() { return new ArrayList<String>(); }
	
	/** Initializes the importer */
	abstract protected void initialize() throws ImporterException;
	
	/** Returns the list of schemas which this schema extends */
	abstract protected ArrayList<Integer> getExtendedSchemaIDs() throws ImporterException;
	
	/** Returns the schema elements from the specified URI */
	abstract protected ArrayList<SchemaElement> getSchemaElements() throws ImporterException;
	
	/** Generate the schema elements */
	final public ArrayList<SchemaElement> generateSchemaElements(URI uri) throws ImporterException
	{
		// Schema elements can generated separately only for file importers
		if(getURIType()!=FILE)
			throw new ImporterException(ImporterException.PARSE_FAILURE,"Schema elements can only be retrieved for file importers");

		// Generate the schema elements
		this.uri = uri;
		initialize();
		return getSchemaElements();
	}
	
	/** Imports the specified URI */
	final public Integer importSchema(String name, String author, String description, URI uri) throws ImporterException
	{
		// Generate the schema
		Schema schema = new Schema(nextId(),name,author,uri==null?"":uri.toString(),"",description,false);

		// Initialize the importer
		this.uri = uri;
		initialize();

		// Get the schema elements (filter out invalid characters)
		ArrayList<SchemaElement> schemaElements = getSchemaElements();
		for(SchemaElement element : schemaElements)
		{
			element.setName(element.getName().replaceAll("[^\\p{ASCII}]","#"));
			element.setDescription(element.getDescription().replaceAll("[^\\p{ASCII}]","#"));
		}
		
		// Imports the schema
		boolean success = false;
		try {
			// Import the schema
			Integer schemaID = client.importSchema(schema, schemaElements);
			schema.setId(schemaID);
			success = client.setParentSchemas(schema.getId(), getExtendedSchemaIDs());

			// Lock the schema if needed
			if(success && (getURIType()==REPOSITORY || getURIType()==FILE))
				client.lockSchema(schema.getId());
		}
		catch(Exception e) {}

		// Delete the schema if import wasn't totally successful
		if(!success)
		{
			try { client.deleteSchema(schema.getId()); } catch(Exception e) {}
			throw new ImporterException(ImporterException.IMPORT_FAILURE,"A failure occured in transferring the schema to the repository");
		}

		// Returns the id of the imported schema
		return schema.getId();
	}
	
	/** Outputs the importer name */
	public String toString()
		{ return getName(); }
}