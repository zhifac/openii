// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.server;

import java.applet.Applet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 * Handles all commuications to the database (via servlets)
 * @author CWOLF
 */
public class ServletConnection
{
	/** Stores the codebase used for accessing the database */
	private static URL codeBase;
	
	/** Initializes the database for use */
	public static void init(Applet applet)
	{
		try {
			if(applet.getCodeBase().toString().startsWith("file")) codeBase = new URL("http://localhost:8080/Galaxy/");
			else codeBase = applet.getCodeBase();
		} catch(MalformedURLException e) {}
	}

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
	static private Object callFunction(String functionName,Object[] inputs)
	{
		Object object = null;
		try {
			URLConnection connection = getServletConnection();
			ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
			out.writeObject(functionName);
			for(Object input : inputs) out.writeObject(input);
			out.flush();
			out.close();
			ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
			object = in.readObject();
		    in.close();
		} catch(Exception e) { System.out.println(e.getMessage()); }
	    return object;
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
	
	/** Extends the specified schema in the web service */
	static public Schema extendSchema(Integer schemaID)
		{ return (Schema)callFunction("extendSchema",new Object[] {schemaID}); }
	
	/** Updates the specified schema in the web service */
	static public boolean updateSchema(Schema schema)
		{ return (Boolean)callFunction("updateSchema",new Object[] {schema}); }

	/** Locks the specified schema in the web service */
	static public boolean lockSchema(Integer schemaID)
		{ return (Boolean)callFunction("lockSchema",new Object[] {schemaID}); }
	
	/** Delete the specified schema from the web service */
	static public boolean deleteSchema(Integer schemaID)
		{ return (Boolean)callFunction("deleteSchema",new Object[] {schemaID}); }
	
	//------------------------
	// Schema Group Functions
	//------------------------
	
	/** Get the list of schema groups from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Group> getGroups()
		{ return (ArrayList<Group>)callFunction("getGroups",new Object[] {}); }

	/** Add a group to the web service */
	static public Integer addGroup(Group group)
		{ return (Integer)callFunction("addGroup",new Object[] {group}); }
	
	/** Update a group on the web service */
	static public Boolean updateGroup(Group group)
		{ return (Boolean)callFunction("updateGroup",new Object[] {group}); }
	
	/** Delete a group from the web service */
	static public Boolean deleteGroup(Integer groupID)
		{ return (Boolean)callFunction("deleteGroup",new Object[] {groupID}); }
	
	/** Get list of schemas unassigned with a group in the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getUnassignedSchemas()
		{ return (ArrayList<Integer>)callFunction("getUnassignedSchemas",new Object[] {}); }
	
	/** Get list of schemas associated with group in web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getGroupSchemas(Integer groupID)
		{ return (ArrayList<Integer>)callFunction("getGroupSchemas",new Object[] {groupID}); }
	
	/** Get list of groups associated with schema in the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<Integer> getSchemaGroups(Integer schemaID)
		{ return (ArrayList<Integer>)callFunction("getSchemaGroups",new Object[] {schemaID}); }
		
	/** Add a group to a schema in the web service */
	static public Boolean addGroupToSchema(Integer schemaID, Integer groupID)
		{ return (Boolean)callFunction("addGroupToSchema",new Object[] {schemaID, groupID}); }
	
	/** Remove a group from a schema in the web service */
	static public Boolean removeGroupFromSchema(Integer schemaID, Integer groupID)
		{ return (Boolean)callFunction("removeGroupFromSchema",new Object[] {schemaID, groupID}); }
	
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
	
	/** Sets the parent schemas for the specified schema from the web service */
	static public boolean setParentSchemas(Integer schemaID, ArrayList<Integer> parentIDs)
		{ return (Boolean)callFunction("setParentSchemas",new Object[] {schemaID, parentIDs}); }

	//--------------------------
	// Schema Element Functions
	//--------------------------

	/** Retrieves the schema element count for the specified schema from the web service */
	static public Integer getSchemaElementCount(Integer schemaID)
		{ return (Integer)callFunction("getSchemaElementCount",new Object[] {schemaID}); }
	
	/** Retrieves the schema element graph for the specified schema from the web service */
	static public HierarchicalGraph getSchemaElementGraph(Integer schemaID)
		{ return (HierarchicalGraph)callFunction("getSchemaElementGraph",new Object[] {schemaID}); }	

	/** Retrieves the specified schema element from the web service */
	static public SchemaElement getSchemaElement(Integer schemaElementID)
		{ return (SchemaElement)callFunction("getSchemaElement",new Object[] {schemaElementID}); }	
	
	/** Adds the specified schema element to the web service */
	static public Integer addSchemaElement(SchemaElement schemaElement)
		{ return (Integer)callFunction("addSchemaElement",new Object[] {schemaElement}); }	
	
	/** Updates the specified schema element on the web service */
	static public boolean updateSchemaElement(SchemaElement schemaElement)
		{ return (Boolean)callFunction("updateSchemaElement",new Object[] {schemaElement}); }	
	
	/** Deletes the specified schema element from the web service */
	static public boolean deleteSchemaElement(Integer schemaElementID)
		{ return (Boolean)callFunction("deleteSchemaElement",new Object[] {schemaElementID}); }	

	//-----------------------
	// Data Source Functions
	//-----------------------

	/** Gets the list of data sources from the web service */ @SuppressWarnings("unchecked")
	static public ArrayList<DataSource> getDataSourceList(Integer schemaID)
		{ return (ArrayList<DataSource>)callFunction("getDataSources",new Object[] {schemaID}); }
		
	/** Gets the specified data source */ 
	static public DataSource getDataSource(Integer dataSourceID)
		{ return (DataSource)callFunction("getDataSource",new Object[] {dataSourceID}); }
	
	/** Adds the specified data source to the web service */
	static public Integer addDataSource(DataSource dataSource)
		{ return (Integer)callFunction("addDataSource",new Object[] {dataSource}); }
	
	/** Updates the specified data source in the web service */
	static public boolean updateDataSource(DataSource dataSource)
		{ return (Boolean)callFunction("updateDataSource",new Object[] {dataSource}); }
	
	/** Deletes the specified data source from the web service */
	static public boolean deleteDataSource(Integer dataSourceID)
		{ return (Boolean)callFunction("deleteDataSource",new Object[] {dataSourceID}); }
}