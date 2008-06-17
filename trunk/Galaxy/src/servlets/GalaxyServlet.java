// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package servlets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

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
				String webService = req.getSession().getServletContext().getInitParameter("webServiceHost");
				client = new SchemaStoreClient(webService);
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
			
			// Extends the specified schema
			if(action.equals("extendSchema"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.extendSchema(schemaID);
			}
				
			// Updates the specified schema
			if(action.equals("updateSchema"))
			{
				Schema schema = (Schema)in.readObject();
				output = client.updateSchema(schema);
			}
				
			// Locks the specified schema
			if(action.equals("lockSchema"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.lockSchema(schemaID);			
			}
			
			// Deletes the specified schema
			if(action.equals("deleteSchema"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.deleteSchema(schemaID);
			}
			
			//-------------------------------
			// Handles schema group requests
			//-------------------------------
			
			/** Get the list of schema groups from the web service */
			if(action.equals("getGroups"))
			{
				output = client.getGroups();
			}
			
			/** Add a group to the web service */
			if(action.equals("addGroup"))
			{
				Group group = (Group)in.readObject();
				output = client.addGroup(group);
			}
			
			/** Update a group on the web service */
			if(action.equals("updateGroup"))
			{
				Group group = (Group)in.readObject();
				output = client.updateGroup(group);
			}
			
			/** Delete a group from the web service */
			if(action.equals("deleteGroup"))
			{
				Integer groupID = (Integer)in.readObject();
				output = client.deleteGroup(groupID);
			}
			
			/** Get list of schemas unassigned to a group in web service */
			if(action.equals("getUnassignedSchemas"))
			{
				output = client.getUnassignedSchemas();				
			}
			
			/** Get list of schemas associated with group in web service */
			if(action.equals("getGroupSchemas"))
			{
				Integer groupID = (Integer)in.readObject();
				output = client.getGroupSchemas(groupID);				
			}
			
			/** Get list of groups associated with schema in the web service */
			if(action.equals("getSchemaGroups"))
			{
				Integer groupID = (Integer)in.readObject();
				output = client.getSchemaGroups(groupID);				
			}
				
			/** Add a group to a schema in the web service */
			if(action.equals("addGroupToSchema"))
			{
				Integer schemaID = (Integer)in.readObject();
				Integer groupID = (Integer)in.readObject();
				output = client.addGroupToSchema(schemaID,groupID);				
			}
			
			/** Remove a group from a schema in the web service */
			if(action.equals("removeGroupFromSchema"))
			{
				Integer schemaID = (Integer)in.readObject();
				Integer groupID = (Integer)in.readObject();
				output = client.removeGroupFromSchema(schemaID,groupID);				
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

			// Sets the parent schemas for the specified schema
			if(action.equals("setParentSchemas"))
			{
				Integer schemaID = (Integer)in.readObject();
				ArrayList<Integer> parentIDs = (ArrayList<Integer>)in.readObject();
				output = client.setParentSchemas(schemaID,parentIDs);				
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
			if(action.equals("getSchemaElements"))
			{
				Integer schemaID = (Integer)in.readObject();
				output = client.getSchemaElements(schemaID);
			}
			
			// Retrieves the specified schema element
			if(action.equals("getSchemaElement"))
			{
				Integer schemaElementID = (Integer)in.readObject();
				output = client.getSchemaElement(schemaElementID);
			}
			
			// Add a schema element to the specified schema
			if(action.equals("addSchemaElement"))
			{
				SchemaElement schemaElement = (SchemaElement)in.readObject();
				output = client.addSchemaElement(schemaElement);
			}
			
			// Update a schema element in the specified schema
			if(action.equals("updateSchemaElement"))
			{
				SchemaElement schemaElement = (SchemaElement)in.readObject();
				output = client.updateSchemaElement(schemaElement);
			}
			
			// Delete a schema element from the specified schema
			if(action.equals("deleteSchemaElement"))
			{
				Integer schemaElementID = (Integer)in.readObject();
				output = client.deleteSchemaElement(schemaElementID);
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
				
			// Add a data source to the specified schema
			if(action.equals("addDataSource"))
			{
				DataSource dataSource = (DataSource)in.readObject();
				output = client.addDataSource(dataSource);
			}
			
			// Update a data source in the specified schema
			if(action.equals("updateDataSource"))
			{
				DataSource dataSource = (DataSource)in.readObject();
				output = client.updateDataSource(dataSource);
			}
			
			// Delete a data source from the specified schema
			if(action.equals("deleteDataSource"))
			{
				Integer dataSourceID = (Integer)in.readObject();
				output = client.deleteDataSource(dataSourceID);
			}
			
			in.close();
		} catch(Exception e) { System.out.println(e.getMessage()); }
		
		// Returns the output
		try {
			ObjectOutputStream out = new ObjectOutputStream(res.getOutputStream());
			out.writeObject(output);
			out.close();
		} catch(IOException e) {}
	}
}