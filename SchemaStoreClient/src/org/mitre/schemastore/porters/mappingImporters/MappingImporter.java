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

import java.net.URI;
import java.util.ArrayList;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.porters.Importer;
import org.mitre.schemastore.porters.ImporterException;

/** Abstract Mapping Importer class */
public abstract class MappingImporter extends Importer
{
	/** Stores the aligned source schema */
	protected ProjectSchema source;
	
	/** Stores the aligned target schema */
	protected ProjectSchema target;
	
	/** Initializes the importer */
	abstract protected void initialize() throws ImporterException;

	/** Returns the source schema in the mapping */
	abstract public ProjectSchema getSourceSchema() throws ImporterException;

	/** Returns the target schema in the mapping */
	abstract public ProjectSchema getTargetSchema() throws ImporterException;
	
	/** Returns the mapping cells from the specified URI */
	abstract public ArrayList<MappingCell> getMappingCells() throws ImporterException;

	/** Initializes the importer for the specified URI */
	final public void initialize(URI uri) throws ImporterException
		{ this.uri = uri; initialize(); }

	/** Sets the source and target schemas */
	final public void setSchemas(Integer sourceID, Integer targetID) throws ImporterException
	{
		source = getSourceSchema(); if(sourceID!=null) source.setId(sourceID);
		target = getTargetSchema(); if(targetID!=null) target.setId(targetID);
	}
	
	/** Imports the specified URI */
	final public Integer importMapping(Project project, Integer sourceID, Integer targetID) throws ImporterException
	{
		// Set the schema information
		setSchemas(sourceID, targetID);
		
		// Generate the mapping and mapping cells
		Mapping mapping = new Mapping(null,project.getId(),sourceID,targetID);
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