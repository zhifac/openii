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

package org.mitre.schemastore.porters;

import java.util.ArrayList;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.porters.mappingExporters.MappingExporter;
import org.mitre.schemastore.porters.schemaExporters.SchemaExporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/** Class for managing the various porters */
public class PorterManager
{
	// Constants for references the various porter lists
	static public final Integer SCHEMA_IMPORTERS = 0;
	static public final Integer SCHEMA_EXPORTERS = 1;
	static public final Integer MAPPING_EXPORTERS = 2;
	
	/** Stores listings of the porters */
	private ArrayList<SchemaImporter> schemaImporters;
	private ArrayList<SchemaExporter> schemaExporters;
	private ArrayList<MappingExporter> mappingExporters;
	
	/** Constructs the porter manager class */
	public PorterManager(SchemaStoreClient client)
	{
		schemaImporters = new PorterList<SchemaImporter>("schemaImporter",client);
		schemaExporters = new PorterList<SchemaExporter>("schemaExporter",client);
		mappingExporters = new PorterList<MappingExporter>("mappingExporter",client);
	}
	
	/** Returns the list of schema importers */
	public ArrayList<SchemaImporter> getSchemaImporters()
		{ return new ArrayList<SchemaImporter>(schemaImporters); }
	
	/** Returns the list of schema exporters */
	public ArrayList<SchemaExporter> getSchemaExporters()
		{ return new ArrayList<SchemaExporter>(schemaExporters); }
	
	/** Returns the list of mapping exporters */
	public ArrayList<MappingExporter> getMappingExporters()
		{ return new ArrayList<MappingExporter>(mappingExporters); }
}