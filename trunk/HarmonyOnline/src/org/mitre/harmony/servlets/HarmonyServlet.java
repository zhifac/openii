// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.harmony.servlets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.URI;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;

/**
 * Servlet for returning the information from the database
 * @author CWOLF
 */
public class HarmonyServlet extends HttpServlet
{
	static private SchemaStoreClient client = null;
	
	/** Returns the schemas existent in the database */
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

			// Get the function name
			ObjectInputStream in = new ObjectInputStream(req.getInputStream());
			String functionName = (String)in.readObject();

			// Get the list of function arguments
			Object args[] = new Object[(Integer)in.readObject()];
			for(int i=0; i<args.length; i++)
				args[i] = in.readObject();
			
			// Create an array of types
			Class<?> types[] = new Class[args.length];
			for(int i=0; i<args.length; i++)
				types[i] = (args[i]==null) ? Integer.class : args[i].getClass();
				
			// Calls the SchemaStore method
			try {
				Method method = client.getClass().getDeclaredMethod(functionName, types);
				output = method.invoke(client, args);
			} catch(Exception e) { System.out.println("(E) HarmonyServlet: Unable to call method " + functionName); }

			in.close();
		} catch(Exception e) { System.out.println("(E) HarmonyServlet.doPost - " + e.getMessage()); }
		
		// Returns the output
		try {
			ObjectOutputStream out = new ObjectOutputStream(res.getOutputStream());
			out.writeObject(output);
			out.close();
		} catch(IOException e) { System.out.println("(E) HarmonyServlet.doPost - " + e.getMessage()); }
	}
}