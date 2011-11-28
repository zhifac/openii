/*
 *  Copyright 2010 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
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

package org.mitre.affinity.model.schemas;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.PorterType;
import org.mitre.schemastore.porters.mappingExporters.MappingExporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/**
 * Handles all communications to the database (via servlets).
 * 
 * @author CWOLF
 */
public class AffinitySchemaStoreManager
{
	/** Stores the client used for accessing the database */
	static private SchemaStoreClient client = null;

	/** Stores the porter manager */
	static private PorterManager porterManager = null;
	
	/** Checks if the schema store connection was successful */
	private static boolean isConnected() {		
		try { return client.getSchemaElement(-1)!=null; } catch(Exception e) {}
		return false;
	}
	
	/** Sets the connection using the provided repository */
	public static boolean setConnection(Repository repository) {
		try { client = new SchemaStoreClient(repository); }
		catch(Exception e) { System.out.println("(E)SchemaStoreManager.setConnection - " + e.getMessage()); }
		return isConnected();
	}
	
	/** Sets the connection using the provided SchemaStoreClient object */
	public static boolean setConnection(SchemaStoreClient client) {
		AffinitySchemaStoreManager.client = client;
		return isConnected();
	}
		
	//------------------
	// Schema Functions
	//------------------
	
	/** Gets the list of schemas from the web service */
	static ArrayList<Schema> getSchemas() throws RemoteException
		{ return client.getSchemas(); }
	
	/** Gets the list of deletable schemas from the web service */
	static ArrayList<Integer> getDeletableSchemas() throws RemoteException
		{ return client.getDeletableSchemas(); }
	
	/** Gets the specified schema from the web service */
	static Schema getSchema(Integer schemaID) throws RemoteException
		{ return client.getSchema(schemaID); }

	/** Deletes the specified schema from the web service */
	static boolean deleteSchema(Integer schemaID) throws RemoteException
		{ return client.deleteSchema(schemaID); }
	
	//--------------------------
	// Schema Element Functions
	//--------------------------

	/** Retrieves the schema elements for the specified schema and type from the web service */
	public static HierarchicalSchemaInfo getGraph(Integer schemaID) throws RemoteException
		{ return new HierarchicalSchemaInfo(client.getSchemaInfo(schemaID),null); }

	/** Retrieves the specified schema element from the web service */
	static SchemaElement getSchemaElement(Integer schemaElementID) throws RemoteException
		{ return client.getSchemaElement(schemaElementID); }
	
	//--------------------
	// Importer Functions
	//--------------------
	
	/** Gets the list of available schema importers */
	public static ArrayList<SchemaImporter> getSchemaImporters() {
		if(porterManager==null) porterManager = new PorterManager(client);
		return porterManager.getPorters(PorterType.SCHEMA_IMPORTERS);
	}

	/** Gets the list of available mapping exporters */
	public static ArrayList<MappingExporter> getMappingExporters() {
		if(porterManager==null) porterManager = new PorterManager(client);
		return porterManager.getPorters(PorterType.MAPPING_EXPORTERS);
	}
}