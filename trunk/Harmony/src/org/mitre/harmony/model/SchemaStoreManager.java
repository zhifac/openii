package org.mitre.harmony.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.DataType;
import org.mitre.schemastore.model.Function;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.porters.Porter;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.Importer.URIType;
import org.mitre.schemastore.porters.PorterManager.PorterType;
import org.mitre.schemastore.porters.projectImporters.M3ProjectImporter;
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
		try { setConnection(new SchemaStoreClient(repository)); }
		catch(Exception e) { System.out.println("(E)SchemaStoreManager.setConnection - " + e.getMessage()); return false; }
		return true;
	}
	
	/** Sets the connection using the provided SchemaStoreClient object */
	public static void setConnection(SchemaStoreClient newClient)
		{ client = newClient; MatcherManager.setClient(client); }
		
	//------------------
	// Schema Functions
	//------------------
	
	/** Gets the list of schemas from the web service */
	public static ArrayList<Schema> getSchemas() throws RemoteException
		{ return client.getSchemas(); }
	
	/** Gets the descendant schemas from the web service for the specified schema */
	public static ArrayList<Integer> getDescendantSchemas(Integer schemaID) throws RemoteException
		{ return client.getDescendantSchemas(schemaID); }
	
	/** Gets the list of deletable schemas from the web service */
	public static ArrayList<Integer> getDeletableSchemas() throws RemoteException
		{ return client.getDeletableSchemas(); }
	
	/** Gets the specified schema from the web service */
	public static Schema getSchema(Integer schemaID) throws RemoteException
		{ return client.getSchema(schemaID); }

	/** Deletes the specified schema from the web service */
	public static boolean deleteSchema(Integer schemaID) throws RemoteException
		{ return client.deleteSchema(schemaID); }
	
	//--------------------------
	// Schema Element Functions
	//--------------------------

	/** Retrieves the schema elements for the specified schema and type from the web service */
	public static HierarchicalSchemaInfo getSchemaInfo(Integer schemaID) throws RemoteException
		{ return new HierarchicalSchemaInfo(client.getSchemaInfo(schemaID)); }

	/** Retrieves the specified schema element from the web service */
	public static SchemaElement getSchemaElement(Integer schemaElementID) throws RemoteException
		{ return client.getSchemaElement(schemaElementID); }
	
	//-------------------
	// Project Functions
	//-------------------

	/** Retrieves the list of all projects from the web service */
	public static ArrayList<Project> getProjects() throws RemoteException
		{ return client.getProjects(); }
	
	/** Retrieves the specified project from the web service */
	public static Project getProject(Integer projectID) throws RemoteException
		{ return client.getProject(projectID); }
	
	/** Adds the specified project to the web service */
	public static Integer addProject(Project project) throws RemoteException
		{ return client.addProject(project); }
	
	/** Modifies the specified project in the web service */
	public static boolean updateProject(Project project) throws RemoteException
		{ return client.updateProject(project); }
	
	/** Deletes the specified project from the web service */
	public static Boolean deleteProject(Integer projectID) throws RemoteException
		{ return client.deleteProject(projectID); }
	
	/** Retrieves the list of all mappings for the specified project from the web service */
	public static ArrayList<Mapping> getMappings(Integer projectID) throws RemoteException
		{ return client.getMappings(projectID); }
	
	/** Adds the specified mapping to the web service */
	public static Integer addMapping(Mapping mapping) throws RemoteException
		{ return client.addMapping(mapping); }
	
	/** Retrieves the mapping cells for the specified mapping from the web service */
	public static ArrayList<MappingCell> getMappingCells(Integer mappingID) throws RemoteException
		{ return client.getMappingCells(mappingID); }
	
	/** Saves the specified mapping to the web service */
	public static boolean saveMappingCells(Integer mappingID, ArrayList<MappingCell> mappingCells) throws RemoteException
		{ return client.saveMappingCells(mappingID, mappingCells); }
	
	/** Deletes the specified mapping from the web service */
	public static Boolean deleteMapping(Integer mappingID) throws RemoteException
		{ return client.deleteMapping(mappingID); }

	//--------------------
	// Function Functions
	//--------------------
	
	/** Retrieves the mapping cell functions from the web service */
	public static ArrayList<Function> getFunctions() throws RemoteException
		{ return client.getFunctions(); }	
	
	/** Retrieves the mapping cell data types from the web service */
	public static ArrayList<DataType> getDataTypes() throws RemoteException
		{ return client.getDataTypes(); }	
	
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
		ArrayList<SchemaImporter> importers = new PorterManager(client).getPorters(PorterType.SCHEMA_IMPORTERS);
		for(SchemaImporter importer : new ArrayList<SchemaImporter>(importers))
		{
			URIType uriType = importer.getURIType();
			if(uriType.equals(URIType.NONE) || uriType.equals(URIType.SCHEMA))
				importers.remove(importer);
		}
		Collections.sort(importers, new ImporterComparator());
		return importers;
	}

	/** Gets the M3 Project importer */
	public static M3ProjectImporter getM3ProjectImporter()
		{ return (M3ProjectImporter)new PorterManager(client).getPorter(M3ProjectImporter.class); }

	/** Returns the specified list of porters */
	public static <T extends Porter> ArrayList<T> getPorters(PorterType type)
		{ return new PorterManager(client).getPorters(type); }
}