// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers;

import org.mitre.flexidata.ygg.model.SchemaManager;
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
	
	// Defines the various base domain types
	public static final String ANY = "Any";
	public static final String INTEGER = "Integer";
	public static final String DOUBLE = "Double";
	public static final String STRING = "String";
	public static final String DATETIME = "DateTime";
	public static final String BOOLEAN = "Boolean";
	
	/** Stores the URI being imported */
	protected URI uri;
	
	/** Stores a auto-increment counter for handing out IDs */
	private static Integer autoIncrementedId = 0;
	
	/** Returns the next available ID */
	public static Integer nextId()
		{ return autoIncrementedId++; }

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

		// Generate the schema components
		this.uri = uri;
		initialize();
		ArrayList<Integer> extendedSchemaIDs = getExtendedSchemaIDs();
		ArrayList<SchemaElement> schemaElements = getSchemaElements();
		
		for (SchemaElement e : schemaElements) {
			System.out.println(e.getClass() + "::" + e.getName());
		}
		
		// Import the schema into the repository
		if(!SchemaManager.importSchema(schema, extendedSchemaIDs, schemaElements))
			throw new ImporterException(ImporterException.IMPORT_FAILURE,"A failure occured in transferring the schema to the repository");
		if(getURIType()==REPOSITORY || getURIType()==FILE)
			SchemaManager.lockSchema(schema.getId());
		return schema.getId();
	}
	
	/** Outputs the importer name */
	public String toString()
		{ return getName(); }
}