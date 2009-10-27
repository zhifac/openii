package org.mitre.harmony.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.mappingExporters.MappingExporter;
import org.mitre.schemastore.porters.mappingImporters.M3ProjectImporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/**
 * Handles all communications to the database (via servlets)
 * @author CWOLF
 */
public class SchemaStoreManager
{
	/** Stores the client used for accessing the database */
	static private SchemaStoreClient client = null;
	
	/** Sets the connection using the provided service address */
	public static boolean setConnection(Repository repository)
	{
		try { client = new SchemaStoreClient(repository); }
		catch(Exception e) { System.out.println("(E)SchemaStoreManager.setConnection - " + e.getMessage()); return false; }
		return true;
	}
	
	/** Sets the connection using the provided SchemaStoreClient object */
	public static void setConnection(SchemaStoreClient newClient)
		{ client = newClient; }
		
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
	static HierarchicalSchemaInfo getSchemaInfo(Integer schemaID) throws RemoteException
		{ return new HierarchicalSchemaInfo(client.getSchemaInfo(schemaID)); }

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
	{		
		// Sorts the importers alphabetically
		class ImporterComparator implements Comparator<SchemaImporter>
			{ public int compare(SchemaImporter importer1, SchemaImporter importer2)
				{ return importer1.getName().compareTo(importer2.getName()); } }

		// Retrieves the list of available importers
		ArrayList<SchemaImporter> importers = new ArrayList<SchemaImporter>();
		for(SchemaImporter importer : new PorterManager(client).getSchemaImporters())
		{
			Integer uriType = importer.getURIType();
			if(!uriType.equals(SchemaImporter.NONE) && !uriType.equals(SchemaImporter.SCHEMA))
				importers.add(importer);
		}
		Collections.sort(importers, new ImporterComparator());
		return importers;
	}

	/** Gets the M3 Project importer */
	public static M3ProjectImporter getM3ProjectImporter()
		{ return (M3ProjectImporter)new PorterManager(client).getPorter(M3ProjectImporter.class); }

	/** Gets the list of available mapping exporters */
	public static ArrayList<MappingExporter> getMappingExporters()
		{ return new PorterManager(client).getMappingExporters(); }
}