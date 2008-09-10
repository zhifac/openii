// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Handles the connection to the database
 * @author CWOLF
 */
public class DBConnection
{
	/** Stores the database connection */
	private Connection connection = null;
	
	/** Retrieve the contents of a file as a buffered string */
	private StringBuffer getFileContents(String file) throws IOException
	{
		// Initialize the string buffer
		StringBuffer buffer = new StringBuffer("");

		// Transfer the file to the string buffer
		InputStream fileStream = getClass().getResourceAsStream(file);
		BufferedReader in = new BufferedReader(new InputStreamReader(fileStream));
		String line;
		while((line=in.readLine())!=null)
			if(!line.startsWith("//")) buffer.append(line+"\n");
		in.close();
		
		// Return the string buffer
		return buffer;
	}
	
	/** Initializes the database if needed */
	private void initializeDatabaseIfNeeded() throws SQLException
	{
		// Checks to see if database structure already exists
		boolean exists = true;
		Statement stmt = connection.createStatement();
		try { stmt.executeQuery("SELECT * FROM extensions"); }
		catch(Exception e) { connection.rollback(); exists=false; }
		
		// Initializes the database if it doesn't exist
		if(!exists)
		{
			try {
				// Generate the database structure
				StringBuffer buffer = getFileContents("SchemaStoreStructure.txt");
				String commands[] = buffer.toString().split(";");
				for(String command : commands)
				{
					String text = command.trim().replaceAll("\n","");
					if(text.length()>0) stmt.addBatch(text);
				}
				stmt.executeBatch();
				
				// Generate the database data
				buffer = getFileContents("SchemaStoreData.txt");
				commands = buffer.toString().split("\\n\\s*\\n");
				for(String command : commands)
				{
					// Run through series of insert statements
					String rows[] = command.split("\\n");
					String header = rows[0];
					for(int i=1; i<rows.length; i++)
						stmt.addBatch(header + " VALUES (" + rows[i] + ")");
				}
				stmt.executeBatch();
				
				// Commit all changes
				connection.commit();
			}
			catch (Exception e)
				{ connection.rollback(); System.out.println("(E) Failed to initialize database"); }
		}
		
		stmt.close();
	}
	
	/** Creates a sql statement */
	Statement getStatement() throws SQLException
	{
		// Check to make sure database connection still works
		if(connection!=null)
			try {
				Statement stmt = connection.createStatement();
				stmt.executeQuery("SELECT 1");
				return stmt;
			} catch(Exception e) { connection=null; }
		
		// Attempt to connect to database
		try {
			if(connection==null)
			{				
				// Get environment variables
				Context env = (Context) new	InitialContext().lookup("java:comp/env");
	    		String server = (String)env.lookup("dbServer");
	    		String database = (String)env.lookup("dbDatabase");
	    		String username = (String)env.lookup("dbUsername");
	    		String password = (String)env.lookup("dbPassword");
				
				// Connect to the database
	    		boolean useDerby = server.equals("");
    			Class.forName(useDerby ? "org.apache.derby.jdbc.EmbeddedDriver" : "org.postgresql.Driver");
	    		String dbURL = useDerby ? "jdbc:derby:"+database+";create=true" : "jdbc:postgresql://"+server+":5432/"+database;
    			connection = DriverManager.getConnection(dbURL,username,password);
	    		connection.setAutoCommit(false);

	    		// Initialize the database if needed
				initializeDatabaseIfNeeded();
				
				// Return a sql statement
				return connection.createStatement();
			}
		}
		catch (Exception e)
			{ System.out.println("(E) Failed to connect to database"); }

		// Indicates that a statement failed to be created
		throw new SQLException();
	}

	/** Commits changes to the database */
	void commit() throws SQLException
		{ connection.commit(); }
	
	/** Rolls back changes to the database */
	void rollback() throws SQLException
		{ connection.rollback(); }
}