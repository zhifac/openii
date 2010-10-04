// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.harmony.model;

import java.applet.Applet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.porters.Importer.URIType;
import org.mitre.schemastore.porters.Porter;
import org.mitre.schemastore.porters.PorterManager;
import org.mitre.schemastore.porters.PorterManager.PorterType;
import org.mitre.schemastore.porters.projectImporters.M3ProjectImporter;
import org.mitre.schemastore.porters.schemaImporters.SchemaImporter;

/**
 * Handles all communications to the database (via servlets)
 * @author CWOLF
 */
public class SchemaStoreManager
{
	/** Stores the code base used for accessing the database */
	static private URL codeBase;

	/** Stores the schema store client */
	static private SchemaStoreClient client;
	
	/** Initializes the database for use */
	static public void init(Applet applet)
	{
		try {
			if(applet.getCodeBase().toString().startsWith("file")) codeBase = new URL("http://localhost:8080/Harmony/");
			else codeBase = applet.getCodeBase();
		} catch(MalformedURLException e) {}
	}

	/** Sets the SchemaStore client for use by this manager */
	static public void setClient(Repository repository) throws Exception
		{ setClient(new SchemaStoreClient(repository)); }
	
	/** Sets the SchemaStore client for use by this manager */
	static public void setClient(SchemaStoreClient clientIn)
		{ client = clientIn; MatcherManager.setClient(client); }
	
	/** Connects to the specified servlet */
	static private URLConnection getServletConnection() throws MalformedURLException, IOException
	{
		URL urlServlet = new URL(codeBase.toString().replaceFirst("HarmonyApplet.*","")+"HarmonyServlet");
		URLConnection connection = urlServlet.openConnection();
		connection.setConnectTimeout(10800);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;
	}

	/** Handles the call to the servlet */
	static private Object callServlet(String functionName,Object[] inputs) throws Exception
	{
		Object object = null;
		URLConnection connection = getServletConnection();
		ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
		out.writeObject(functionName);
		out.writeObject(inputs.length);
		for(Object input : inputs) out.writeObject(input);
		out.flush();
		out.close();
		ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
		object = in.readObject();
	    in.close();
		return object;
	}
	
	/** Manages calls to the SchemaStore client method */
	static private Object callClient(String name, Object args[]) throws RemoteException
	{
		// Create an array of types
		Class<?> types[] = new Class[args.length];
		for(int i=0; i<args.length; i++)
			types[i] = (args[i]==null) ? Integer.class : args[i].getClass();
			
		// Calls the SchemaStore method
		try {
			Method method = client.getClass().getDeclaredMethod(name, types);
			return method.invoke(client, args);
		} catch(Exception e) { return new RemoteException("Unable to call method " + name); }
	}

	/** Handles the call to the database (either through servlet or SchemaStore client) */
	static private Object callFunction(String functionName, Object[] inputs) 
	{
		try {
			if(codeBase!=null) return callServlet(functionName,inputs);
			if(client!=null) return callClient(functionName,inputs);
		}
		catch(Exception e)
		{
			System.out.println("(E)SchemaStoreManager.callFunction: Unable to call method " + functionName);
			System.out.println("    " + e.getMessage());
		}
		return null;
	}
	
	//------------------
	// Schema Functions
	//------------------
	
	/** Gets the list of schemas from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Schema> getSchemas()
		{ return (ArrayList<Schema>)callFunction("getSchemas",new Object[] {}); }
	
	/** Gets the descendant schemas from the web service for the specified schema */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getDescendantSchemas(Integer schemaID)
		{ return (ArrayList<Integer>)callFunction("getDescendantSchemas",new Object[] {schemaID}); }
	
	/** Gets the list of deletable schemas from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getDeletableSchemas() throws RemoteException
		{ return (ArrayList<Integer>)callFunction("getDeletableSchemas",new Object[] {}); }
	
	/** Gets the specified schema from the web service */
	static public Schema getSchema(Integer schemaID)
		{ return (Schema)callFunction("getSchema",new Object[] {schemaID}); }

	/** Deletes the specified schema from the web service */
	static public boolean deleteSchema(Integer schemaID) throws RemoteException
		{ return (Boolean)callFunction("deleteSchema",new Object[] {schemaID}); }
	
	//--------------------------
	// Schema Element Functions
	//--------------------------
	
	/** Retrieves the schema info for the specified schema from the web service */
	static public SchemaInfo getSchemaInfo(Integer schemaID)
		{ return (SchemaInfo)callFunction("getSchemaInfo",new Object[] {schemaID}); }	

	/** Retrieves the specified schema element from the web service */
	static public SchemaElement getSchemaElement(Integer schemaElementID)
		{ return (SchemaElement)callFunction("getSchemaElement",new Object[] {schemaElementID}); }	
	
	//-------------------
	// Project Functions
	//-------------------

	/** Retrieves the list of all projects from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Project> getProjects() throws RemoteException
		{ return (ArrayList<Project>)callFunction("getProjects",new Object[] {}); }
	
	/** Retrieves the specified project from the web service */
	static public Project getProject(Integer projectID) throws RemoteException
		{ return (Project)callFunction("getProject",new Object[] {projectID}); }
	
	/** Adds the specified project to the web service */
	static public Integer addProject(Project project) throws RemoteException
		{ return (Integer)callFunction("addProject",new Object[] {project}); }
	
	/** Modifies the specified project in the web service */
	static public boolean updateProject(Project project) throws RemoteException
		{ return (Boolean)callFunction("updateProject",new Object[] {project}); }
	
	/** Deletes the specified project from the web service */
	static public boolean deleteProject(Integer projectID) throws RemoteException
		{ return (Boolean)callFunction("deleteProject",new Object[] {projectID}); }
	
	/** Retrieves the list of all mappings for the specified project from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Mapping> getMappings(Integer projectID) throws RemoteException
		{ return (ArrayList<Mapping>)callFunction("getMappings",new Object[] {projectID}); }
	
	/** Adds the specified mapping to the web service */
	static public Integer addMapping(Mapping mapping) throws RemoteException
		{ return (Integer)callFunction("addMapping",new Object[] {mapping}); }
	
	/** Retrieves the mapping cells for the specified mapping from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<MappingCell> getMappingCells(Integer mappingID) throws RemoteException
		{ return (ArrayList<MappingCell>)callFunction("getMappingCells",new Object[] {mappingID}); }
	
	/** Saves the specified mapping to the web service */
	static public boolean saveMappingCells(Integer mappingID, ArrayList<MappingCell> mappingCells) throws RemoteException
		{ return (Boolean)callFunction("saveMappingCells",new Object[] {mappingID,mappingCells}); }
	
	/** Deletes the specified mapping from the web service */
	static public boolean deleteMapping(Integer mappingID) throws RemoteException
		{ return (Boolean)callFunction("deleteMapping",new Object[] {mappingID}); }
	
	//--------------------
	// Function Functions
	//--------------------
	
	/** Retrieves the mapping cell functions from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Function> getFunctions() throws RemoteException
		{ return (ArrayList<Function>)callFunction("getFunctions",new Object[] {}); }	
	
	/** Retrieves the mapping cell data types from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<DataType> getDataTypes() throws RemoteException
		{ return (ArrayList<DataType>)callFunction("getDataTypes",new Object[] {}); }
	
	/** Add a mapping cell functions from the web service */
	static public Integer addFunction(Function function) throws RemoteException
		{ return (Integer)callFunction("addFunction",new Object[] {function}); }

	//--------------------
	// Importer Functions
	//--------------------
	
	/** Gets the list of available schema importers */
	static public ArrayList<SchemaImporter> getSchemaImporters()
	{
		ArrayList<SchemaImporter> importers = new ArrayList<SchemaImporter>();
		if(client!=null)
		{
			// Sorts the importers alphabetically
			class ImporterComparator implements Comparator<SchemaImporter>
				{ public int compare(SchemaImporter importer1, SchemaImporter importer2)
					{ return importer1.getName().compareTo(importer2.getName()); } }

			// Retrieves the list of available importers
			importers = new PorterManager(client).getPorters(PorterType.SCHEMA_IMPORTERS);
			for(SchemaImporter importer : new ArrayList<SchemaImporter>(importers))
			{
				URIType uriType = importer.getURIType();
				if(uriType.equals(URIType.NONE) || uriType.equals(URIType.SCHEMA))
					importers.remove(importer);
			}
			Collections.sort(importers, new ImporterComparator());
		}	
		return importers;
	}

	/** Gets the M3 Project importer */
	static public M3ProjectImporter getM3ProjectImporter()
	{
		if(client!=null)
			return (M3ProjectImporter)new PorterManager(client).getPorter(M3ProjectImporter.class);
		return null;
	}

	/** Returns the specified list of porters */
	static public <T extends Porter> ArrayList<T> getPorters(PorterType type)
	{
		if(client!=null)
			return new PorterManager(client).getPorters(type);
		return null;
	}
}