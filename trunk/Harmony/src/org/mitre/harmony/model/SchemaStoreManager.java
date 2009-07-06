package org.mitre.harmony.model;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.mappingExporters.MappingExporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/**
 * Handles all communications to the database (via servlets)
 * @author CWOLF
 */
public class SchemaStoreManager
{
	/** Stores the client used for accessing the database */
	static private SchemaStoreClient client = null;
	
	/** Checks if the schema store connection was successful */
	private static boolean isConnected()
	{
		try { return client.getSchemaElement(-1)!=null; } catch(Exception e) {}
		return false;
	}
	
	/** Sets the connection using the provided service address */
	public static boolean setConnection(Repository repository)
	{
		try { client = new SchemaStoreClient(repository); }
		catch(Exception e) { System.out.println("(E)SchemaStoreManager.setConnection - " + e.getMessage()); }
		return isConnected();
	}
	
	/** Sets the connection using the provided SchemaStoreClient object */
	public static boolean setConnection(SchemaStoreClient clientID)
	{
		client = clientID;
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
	static HierarchicalGraph getGraph(Integer schemaID) throws RemoteException
		{ return new HierarchicalGraph(client.getGraph(schemaID),null); }

	/** Retrieves the specified schema element from the web service */
	static SchemaElement getSchemaElement(Integer schemaElementID) throws RemoteException
		{ return client.getSchemaElement(schemaElementID); }
	
	//-------------------
	// Mapping Functions
	//-------------------

	static ArrayList<Mapping> getMappingList() throws RemoteException
		{ return client.getMappings(); }
	
	/** Retrieves the specified mapping from the web service */
	public static Mapping getMapping(Integer mappingID) throws RemoteException
		{ return client.getMapping(mappingID); }
	
	/** Saves the specified mapping to the web service */
	public static Integer saveMapping(Mapping mapping, ArrayList<MappingCell> mappingCells) throws RemoteException
		{ return client.saveMapping(mapping, mappingCells); }
	
	/** Deletes the specified mapping from the web service */
	public static Boolean deleteMapping(Integer mappingID) throws RemoteException
		{ return client.deleteMapping(mappingID); }
	
	/** Retrieves the mapping cells for the specified mapping */
	public static ArrayList<MappingCell> getMappingCells(Integer mappingID) throws RemoteException
		{ return client.getMappingCells(mappingID); }
	
	//--------------------
	// Importer Functions
	//--------------------
	
	/** Gets the list of available schema importers */
	public static ArrayList<SchemaImporter> getSchemaImporters()
		{ return new PorterManager(client).getSchemaImporters(); }

	/** Gets the list of available mapping exporters */
	public static ArrayList<MappingExporter> getMappingExporters()
		{ return new PorterManager(client).getMappingExporters(); }
}