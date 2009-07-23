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

package org.mitre.schemastore.porters.mappingImporters;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.Porter;

import java.net.URI;
import java.util.ArrayList;

/** Abstract Mapping Importer class */
public abstract class MappingImporter extends Porter
{
	// Defines the various types of URIs that may be requested
	public static final Integer FILE = 0;
	public static final Integer URI = 1;
	public static final Integer M3MODEL = 2;
	
	/** Stores the URI being imported */
	protected URI uri;
	
	/** Stores the schemas to which the mapping is associated */
	protected ArrayList<MappingSchema> schemas;
	
	/** Returns the importer URI type */
	abstract public Integer getURIType();
	
	/** Returns the importer URI file types (only needed when URI type is FILE) */
	public ArrayList<String> getFileTypes() { return new ArrayList<String>(); }
	
	/** Initializes the importer */
	abstract protected void initialize() throws ImporterException;
	
	/** Returns the schemas from the specified URI */
	abstract protected ArrayList<MappingSchema> getSchemas() throws ImporterException;
	
	/** Returns the mapping cells from the specified URI */
	abstract protected ArrayList<MappingCell> getMappingCells() throws ImporterException;

	/** Return the schemas */
	final public ArrayList<MappingSchema> getSchemas(URI uri) throws ImporterException
		{ this.uri = uri; initialize(); return getSchemas(); }
	
	/** Imports the specified URI */
	final public Integer importMapping(String name, String author, String description, ArrayList<MappingSchema> schemas, URI uri) throws ImporterException
	{
		// Initialize the importer
		this.uri = uri;
		this.schemas = schemas;
		initialize();
		
		// Generate the mapping and mapping cells
		Mapping mapping = new Mapping(null,name,description,author,schemas.toArray(new MappingSchema[0]));
		ArrayList<MappingCell> mappingCells = getMappingCells();
			
		// Imports the mapping
		try {
			Integer mappingID = client.saveMapping(mapping, mappingCells);
			mapping.setId(mappingID);
		}

		// Delete the mapping if import wasn't totally successful
		catch(Exception e) 
		{
			try { client.deleteMapping(mapping.getId()); } catch(Exception e2) {}
			throw new ImporterException(ImporterException.IMPORT_FAILURE,"A failure occured in transferring the mapping to the repository");
		}

		// Returns the id of the imported mapping
		return mapping.getId();
	}
}