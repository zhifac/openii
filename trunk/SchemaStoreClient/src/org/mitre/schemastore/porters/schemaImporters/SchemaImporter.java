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
import org.mitre.schemastore.porters.ImporterException;
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
	public static final Integer M3MODEL = 4;
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
	
	/** Initializes the schema structures */
	protected void initialize() throws ImporterException {}
	
	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> generateExtendedSchemaIDs() throws ImporterException { return new ArrayList<Integer>(); }
	
	/** Returns the schema elements for the specified URI */
	protected ArrayList<SchemaElement> generateSchemaElements() throws ImporterException { return new ArrayList<SchemaElement>(); }
	
	/** Generate the schema */
	public Schema getSchema(URI uri) throws ImporterException { return null; }
	
	/** Return the schema elements */
	final public ArrayList<SchemaElement> getSchemaElements(URI uri) throws ImporterException
	{
		// Schema elements can generated separately only for file importers
		if(getURIType()!=FILE)
			throw new ImporterException(ImporterException.PARSE_FAILURE,"Schema elements can only be retrieved for file importers");

		// Generate the schema elements
		this.uri = uri;
		initialize();
		return generateSchemaElements();
	}
	
	/** Imports the specified URI */
	final public Integer importSchema(String name, String author, String description, URI uri) throws ImporterException
	{
		// Initialize the importer
		this.uri = uri;
		initialize();

		// Generate the schema
		Schema schema = getSchema(uri);
		if(schema==null)
			schema = new Schema(nextId(),name,author,uri==null?"":uri.toString(),getName(),description,false);
		
		// Generate the schema elements and extensions
		ArrayList<SchemaElement> schemaElements = generateSchemaElements();
		ArrayList<Integer> extendedSchemaIDs = generateExtendedSchemaIDs();
		
		// Imports the schema
		boolean success = false;
		try {
			// Import the schema
			Integer schemaID = client.importSchema(schema, schemaElements);
			schema.setId(schemaID);
			success = client.setParentSchemas(schema.getId(), extendedSchemaIDs);

			// Lock the schema if needed
			if(success && (getURIType()==M3MODEL || getURIType()==REPOSITORY || getURIType()==FILE))
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
}