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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.porters.mappingExporters.MappingExporter;
import org.mitre.schemastore.porters.mappingImporters.MappingImporter;
import org.mitre.schemastore.porters.schemaExporters.SchemaExporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/** Class for managing the various porters */
public class PorterManager
{
	// Constants for references the various porter lists
	static public final Integer SCHEMA_IMPORTERS = 0;
	static public final Integer SCHEMA_EXPORTERS = 1;
	static public final Integer MAPPING_IMPORTERS = 2;
	static public final Integer MAPPING_EXPORTERS = 3;
	
	/** Stores listings of the porters */
	private ArrayList<SchemaImporter> schemaImporters;
	private ArrayList<SchemaExporter> schemaExporters;
	private ArrayList<MappingImporter> mappingImporters;
	private ArrayList<MappingExporter> mappingExporters;
	
	/** Constructs the porter manager class */
	public PorterManager(SchemaStoreClient client)
	{
		// Retrieve porter configuration file
		StringBuffer buffer = new StringBuffer("");
		try {
			InputStream configStream = PorterList.class.getResourceAsStream("/porters.xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(configStream));
			String line; while((line=in.readLine())!=null) buffer.append(line);
			in.close();
		}
		catch(IOException e)
			{ System.out.println("(E)PorterManager - porters.xml has failed to load!\n"+e.getMessage()); }
		
		// Parse out the various importers and exporters
		schemaImporters = new PorterList<SchemaImporter>(buffer,"schemaImporter",client);
		schemaExporters = new PorterList<SchemaExporter>(buffer,"schemaExporter",client);
		mappingImporters = new PorterList<MappingImporter>(buffer,"mappingImporter",client);
		mappingExporters = new PorterList<MappingExporter>(buffer,"mappingExporter",client);
	}
	
	/** Returns the list of schema importers */
	public ArrayList<SchemaImporter> getSchemaImporters()
		{ return new ArrayList<SchemaImporter>(schemaImporters); }
	
	/** Returns the list of schema exporters */
	public ArrayList<SchemaExporter> getSchemaExporters()
		{ return new ArrayList<SchemaExporter>(schemaExporters); }
	
	/** Returns the list of mapping importers */
	public ArrayList<MappingImporter> getMappingImporters()
		{ return new ArrayList<MappingImporter>(mappingImporters); }
	
	/** Returns the list of mapping exporters */
	public ArrayList<MappingExporter> getMappingExporters()
		{ return new ArrayList<MappingExporter>(mappingExporters); }
	
	/** Returns the specified porter */
	public Porter getPorter(Class porterClass)
	{
		// Generate a list of all porters
		ArrayList<Porter> porters = new ArrayList<Porter>();
		porters.addAll(schemaImporters);
		porters.addAll(schemaExporters);
		porters.addAll(mappingImporters);
		porters.addAll(mappingExporters);
		
		// Returns the specified porter
		for(Porter porter : porters)
			if(porter.getClass().equals(porterClass)) return porter;
		return null;
	}
}