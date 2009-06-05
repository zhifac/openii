// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handles initialization and updates to the database
 * @author CWOLF
 */
public class DatabaseUpdates
{
	/** Retrieve the contents of a file as a buffered string */
	static private StringBuffer getFileContents(String file) throws IOException
	{
		// Initialize the string buffer
		StringBuffer buffer = new StringBuffer("");

		// Transfer the file to the string buffer
		InputStream fileStream = DatabaseUpdates.class.getResourceAsStream(file);
		BufferedReader in = new BufferedReader(new InputStreamReader(fileStream));
		String line;
		while((line=in.readLine())!=null)
			if(!line.startsWith("//")) buffer.append(line+"\n");
		in.close();
		
		// Return the string buffer
		return buffer;
	}
	
	/** Initializes the database if needed */
	static void initializeDatabase(Connection connection) throws SQLException
	{
		// Checks to see if database structure already exists
		boolean exists = true;
		Statement stmt = connection.createStatement();
		try { stmt.executeQuery("SELECT * FROM extensions"); }
		catch(Exception e) { exists=false; }
		
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
				{ connection.rollback(); System.out.println("(E) Failed to initialize database - " + e.getMessage()); }
		}
		
		stmt.close();
	}
	
	/** Retrieves the current version */
	static private Integer getVersion(Connection connection) throws SQLException
	{
		// Retrieves the version number
		Integer version = 0;
		Statement stmt = connection.createStatement();
		try {
			ResultSet rs = stmt.executeQuery("SELECT id FROM version");
			if(rs.next()) version = rs.getInt("id");
		}

		// Construct table for storing version number if it doesn't exist
		catch(Exception e)
		{
			stmt.close(); connection.rollback();
			stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE version (id integer NOT NULL)");
			stmt.executeUpdate("INSERT INTO version(id) VALUES(0)");
			connection.commit();
		}
		
		// Return the current version
		stmt.close();
		return version;
	}
	
	/** Renames the specified column (different versions for Derby and Postgres) */
	static private void renameColumn(Statement stmt, String table, String oldColumnName, String newColumnName) throws SQLException
	{
		try { stmt.executeUpdate("ALTER TABLE " + table + " RENAME COLUMN " + oldColumnName + " TO " + newColumnName); }
		catch(Exception e) { stmt.executeUpdate("RENAME COLUMN " + table + "." + oldColumnName + " TO " + newColumnName); }
	}
	
	/** Installs version 1 updates */
	static private void version1Updates(Connection connection) throws SQLException
	{
		// Rename object_id to element_id in the annotations table
		Statement stmt = connection.createStatement();
		renameColumn(stmt,"annotation","object_id","element_id");
		
		// Increase the size of the value field in the annotations table
		stmt.executeUpdate("ALTER TABLE annotation ADD COLUMN temp_value CHARACTER VARYING(4096)");
		stmt.executeUpdate("UPDATE annotation SET temp_value=value");
		stmt.executeUpdate("ALTER TABLE annotation DROP COLUMN value");
		renameColumn(stmt,"annotation","temp_value","value");
		
		// Increase the version id
		stmt.executeUpdate("UPDATE version SET id=1");
		stmt.close(); connection.commit();
	}
	
	/** Updates the database as needed */
	static void updateDatabase(Connection connection) throws SQLException
	{
		try {
			Integer version = getVersion(connection);
			if(version<1) { version1Updates(connection); version++; }
		}
		catch (Exception e)
			{ connection.rollback(); System.out.println("(E) Failed to fully update database - " + e.getMessage()); }		
	}
}