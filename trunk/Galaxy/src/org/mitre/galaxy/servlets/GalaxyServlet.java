// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.servlets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;

/**
 * Servlet for returning the data sources existent in the database
 * @author CWOLF
 */
public class GalaxyServlet extends HttpServlet
{
	static private SchemaStoreClient client = null;
	
	/** Returns the schemas existent in the database */ @SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
	{
		Object output = null;

		// Performs the specified action
		try {
			// Initialize the schema store client
			if(client==null)
			{
				// Retrieve the repository information
				String typeParm = req.getSession().getServletContext().getInitParameter("connectionType");
				String locationParm = req.getSession().getServletContext().getInitParameter("connectionLocation");
				String databaseParm = req.getSession().getServletContext().getInitParameter("connectionDatabase");
				String usernameParm = req.getSession().getServletContext().getInitParameter("connectionUsername");
				String passwordParm = req.getSession().getServletContext().getInitParameter("connectionPassword");
				
				// Create a repository connection
				Integer type = typeParm.equals("service")?Repository.SERVICE:typeParm.equals("postgres")?Repository.POSTGRES:Repository.DERBY;
				URI uri = null;	try { uri = new URI(locationParm); } catch(Exception e) {}
				Repository repository = new Repository(type,uri,databaseParm,usernameParm,passwordParm);
				
				// Connects to the client
				client = new SchemaStoreClient(repository);
			}

			// Determine the specified action
			ObjectInputStream in = new ObjectInputStream(req.getInputStream());
			String action = (String)in.readObject();

			//-------------------------
			// Handles schema requests 
			//-------------------------
			
			// Returns a listing of all schemas
			if(action.equals("getSchemas"))
				output = client.getSchemas();
			
			// Returns the specified schema
			if(action.equals("getSchema"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.getSchema(schemaID);
			}
			
			//-----------------------------
			// Handles schema tag requests
			//-----------------------------
			
			/** Get the list of schema tags from the web service */
			if(action.equals("getTags"))
			{
				output = client.getTags();
			}
			
			/** Get list of schemas associated with tag in web service */
			if(action.equals("getTagSchemas"))
			{
				Integer tagID = (Integer)in.readObject();
				output = client.getTagSchemas(tagID);				
			}
			
			/** Get list of tags associated with schema in the web service */
			if(action.equals("getSchemaTags"))
			{
				Integer tagID = (Integer)in.readObject();
				output = client.getSchemaTags(tagID);				
			}
			
			//--------------------------------------
			// Handles schema relationship requests
			//--------------------------------------

			// Returns a listing of the parent schemas for the specified schema
			if(action.equals("getParentSchemas"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.getParentSchemas(schemaID);				
			}
			
			// Returns a listing of the child schemas for the specified schema
			if(action.equals("getChildSchemas"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.getChildSchemas(schemaID);				
			}

			// Returns a listing of the ancestor schemas for the specified schema
			if(action.equals("getAncestorSchemas"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.getAncestorSchemas(schemaID);				
			}

			// Returns a listing of the descendant schemas for the specified schema
			if(action.equals("getDescendantSchemas"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.getDescendantSchemas(schemaID);				
			}

			// Returns a listing of the schemas associated with the specified schema
			if(action.equals("getAssociatedSchemas"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.getAssociatedSchemas(schemaID);				
			}

			// Returns the root schema of the two specified schemas
			if(action.equals("getRootSchema"))
			{
				Integer schema1ID = (Integer)in.readObject();
				Integer schema2ID = (Integer)in.readObject();
				output = client.getRootSchema(schema1ID, schema2ID);				
			}

			// Returns a schema path between the specified root and schema
			if(action.equals("getSchemaPath"))
			{
				Integer rootID = (Integer)in.readObject();
				Integer schemaID = (Integer)in.readObject();
				output = client.getSchemaPath(rootID, schemaID);				
			}
			
			//---------------------------------
			// Handles schema element requests
			//---------------------------------
			
			// Retrieves the schema element count for the specified schema
			if(action.equals("getSchemaElementCount"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.getSchemaElementCount(schemaID);
			}
			
			// Retrieves the schema elements for the specified schema and type
			if(action.equals("getSchemaInfo"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.getSchemaInfo(schemaID);
			}
			
			// Retrieves the specified schema element
			if(action.equals("getSchemaElement"))
			{
				Integer schemaElementID = (Integer)in.readObject();
				output = client.getSchemaElement(schemaElementID);
			}
			
			// Retrieves the base schema elements for the specified schema
			if(action.equals("getSchemaElementsForKeyword"))
			{
				String keyword = (String)in.readObject();
				ArrayList<Integer> tags = (ArrayList<Integer>)in.readObject();
				output = client.getSchemaElementsForKeyword(keyword, tags);
			}

			//------------------------------
			// Handles data source requests
			//------------------------------
			
			// Returns a listing of all data sources (for the specified schema if given)
			if(action.equals("getDataSources"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.getDataSources(schemaID);
			}
			
			// Returns the specified data source
			if(action.equals("getDataSource"))
			{
				Integer dataSourceID = (Integer)in.readObject();
				output = client.getDataSource(dataSourceID);
			}
			
			in.close();
		} catch(Exception e) { System.out.println("(E) GalaxyServlet.doPost - " + e.getMessage()); }
		
		// Returns the output
		try {
			ObjectOutputStream out = new ObjectOutputStream(res.getOutputStream());
			out.writeObject(output);
			out.close();
		} catch(IOException e) { System.out.println("(E) GalaxyServlet.doPost - " + e.getMessage()); }
	}
}