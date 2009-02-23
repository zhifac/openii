// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.model.server;

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

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.Graph;

/**
 * Handles all communications to the database (via servlets)
 * @author CWOLF
 */
public class SchemaStoreManager
{
	/** Stores the code base used for accessing the database */
	private static URL codeBase;

	/** Stores the schema store client */
	private static SchemaStoreClient client;
	
	/** Initializes the database for use */
	public static void init(Applet applet)
	{
		try {
			if(applet.getCodeBase().toString().startsWith("file")) codeBase = new URL("http://localhost:8080/Galaxy/");
			else codeBase = applet.getCodeBase();
		} catch(MalformedURLException e) {}
	}

	/** Sets the SchemaStore client for use by this manager */
	public static void setClient(SchemaStoreClient clientIn)
		{ client = clientIn; }
	
	/** Connects to the specified servlet */
	private static URLConnection getServletConnection() throws MalformedURLException, IOException
	{
		URL urlServlet = new URL(codeBase.toString().replaceFirst("GalaxyApplet.*","")+"GalaxyServlet");
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
	
	/** Gets the specified schema from the web service */
	static public Schema getSchema(Integer schemaID)
		{ return (Schema)callFunction("getSchema",new Object[] {schemaID}); }
	
	//------------------------
	// Schema Group Functions
	//------------------------
	
	/** Get the list of schema groups from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Group> getGroups()
		{ return (ArrayList<Group>)callFunction("getGroups",new Object[] {}); }
	
	/** Get list of schemas associated with group in web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getGroupSchemas(Integer groupID)
		{ return (ArrayList<Integer>)callFunction("getGroupSchemas",new Object[] {groupID}); }
	
	/** Get list of groups associated with schema in the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getSchemaGroups(Integer schemaID)
		{ return (ArrayList<Integer>)callFunction("getSchemaGroups",new Object[] {schemaID}); }
	
	//-------------------------------
	// Schema Relationship Functions
	//-------------------------------
	
	/** Gets the list of child schemas for the specified schema from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getChildSchemas(Integer schemaID)
		{ return (ArrayList<Integer>)callFunction("getChildSchemas",new Object[] {schemaID}); }
	
	/** Gets the list of parent schemas for the specified schema from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getParentSchemas(Integer schemaID)
		{ return (ArrayList<Integer>)callFunction("getParentSchemas",new Object[] {schemaID}); }
	
	/** Gets the list of ancestor schemas for the specified schema from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getAncestorSchemas(Integer schemaID)
		{ return (ArrayList<Integer>)callFunction("getAncestorSchemas",new Object[] {schemaID}); }
	
	/** Gets the list of descendant schemas for the specified schema from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getDescendantSchemas(Integer schemaID)
		{ return (ArrayList<Integer>)callFunction("getDescendantSchemas",new Object[] {schemaID}); }
	
	/** Gets the list of schemas associated with the specified schema from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getAssociatedSchemas(Integer schemaID)
		{ return (ArrayList<Integer>)callFunction("getAssociatedSchemas",new Object[] {schemaID}); }

	/** Gets the root schema for the two specified schemas from the web service */
	static public Integer getRootSchema(Integer schema1ID, Integer schema2ID)
		{ return (Integer)callFunction("getRootSchema",new Object[] {schema1ID,schema2ID}); }
	
	/** Gets the schema path between the specified root and schema from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getSchemaPath(Integer rootID, Integer schemaID)
		{ return (ArrayList<Integer>)callFunction("getSchemaPath",new Object[] {rootID,schemaID}); }

	//--------------------------
	// Schema Element Functions
	//--------------------------

	/** Retrieves the schema element count for the specified schema from the web service */
	static public Integer getSchemaElementCount(Integer schemaID)
		{ return (Integer)callFunction("getSchemaElementCount",new Object[] {schemaID}); }
	
	/** Retrieves the schema element graph for the specified schema from the web service */
	static public Graph getGraph(Integer schemaID)
		{ return (Graph)callFunction("getGraph",new Object[] {schemaID}); }	

	/** Retrieves the specified schema element from the web service */
	static public SchemaElement getSchemaElement(Integer schemaElementID)
		{ return (SchemaElement)callFunction("getSchemaElement",new Object[] {schemaElementID}); }	

	/** Retrieves the schema elements which contain the specified keyword from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<SchemaElement> getSchemaElementsForKeyword(String keyword, ArrayList<Integer> groups)
		{ return (ArrayList<SchemaElement>)callFunction("getSchemaElementsForKeyword",new Object[] {keyword, groups}); }

	//-----------------------
	// Data Source Functions
	//-----------------------

	/** Gets the list of data sources from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<DataSource> getDataSourceList(Integer schemaID)
		{ return (ArrayList<DataSource>)callFunction("getDataSources",new Object[] {schemaID}); }
		
	/** Gets the specified data source */ 
	static public DataSource getDataSource(Integer dataSourceID)
		{ return (DataSource)callFunction("getDataSource",new Object[] {dataSourceID}); }
}