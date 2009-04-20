/*
 *  Copyright 2008 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.schemastore.porters.schemaImporters;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.Porter;

import java.net.URI;
import java.util.ArrayList;

/** Abstract Schema Importer class */
public abstract class SchemaImporter extends Porter
{
	// Defines the various types of URIs that may be requested
	public static final Integer NONE = 0;
	public static final Integer SCHEMA = 1;
	public static final Integer REPOSITORY = 2;
	public static final Integer FILE = 3;
	public static final Integer ARCHIVE = 4;
	public static final Integer URI = 5;
	
	// Defines the various base domain types
	public static final String ANY = "Any";
	public static final String INTEGER = "Integer";
	public static final String REAL = "Real";
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
	
	/** Returns the importer URI type */
	abstract public Integer getURIType();
	
	/** Returns the importer URI file types (only needed when URI type is FILE) */
	public ArrayList<String> getFileTypes() { return new ArrayList<String>(); }
	
	/** Initializes the importer */
	abstract protected void initialize() throws SchemaImporterException;
	
	/** Returns the list of schemas which this schema extends */
	abstract protected ArrayList<Integer> getExtendedSchemaIDs() throws SchemaImporterException;
	
	/** Returns the schema elements from the specified URI */
	abstract protected ArrayList<SchemaElement> getSchemaElements() throws SchemaImporterException;
	
	/** Generate the schema */
	final public Schema generateSchema(URI uri) throws SchemaImporterException
	{
		// Schema elements can generated separately only for archive importers
		if(getURIType()!=ARCHIVE)
			throw new SchemaImporterException(SchemaImporterException.PARSE_FAILURE,"Schemas can only be retrieved for archive importers");

		// Generate the schema
		this.uri = uri;
		return ((ArchiveImporter)this).getSchema();
	}
	
	/** Return the schema elements */
	final public ArrayList<SchemaElement> generateSchemaElements(URI uri) throws SchemaImporterException
	{
		// Schema elements can generated separately only for file importers
		if(getURIType()!=FILE)
			throw new SchemaImporterException(SchemaImporterException.PARSE_FAILURE,"Schema elements can only be retrieved for file importers");

		// Generate the schema elements
		this.uri = uri;
		initialize();
		return getSchemaElements();
	}
	
	/** Imports the specified URI */
	final public Integer importSchema(String name, String author, String description, URI uri) throws SchemaImporterException
	{
		// Initialize the importer
		this.uri = uri;
		initialize();

		// Generate the schema
		Schema schema = new Schema(nextId(),name,author,uri==null?"":uri.toString(),"",description,false);
		if(getURIType()==ARCHIVE) schema = generateSchema(uri);
		
		// Imports the schema
		boolean success = false;
		try {
			// Import the schema
			Integer schemaID = client.importSchema(schema, getSchemaElements());
			schema.setId(schemaID);
			success = client.setParentSchemas(schema.getId(), getExtendedSchemaIDs());

			// Lock the schema if needed
			if(success && (getURIType()==ARCHIVE || getURIType()==REPOSITORY || getURIType()==FILE))
				client.lockSchema(schema.getId());
		}
		catch(Exception e) {}

		// Delete the schema if import wasn't totally successful
		if(!success)
		{
			try { client.deleteSchema(schema.getId()); } catch(Exception e) {}
			throw new SchemaImporterException(SchemaImporterException.IMPORT_FAILURE,"A failure occured in transferring the schema to the repository");
		}

		// Returns the id of the imported schema
		return schema.getId();
	}
}