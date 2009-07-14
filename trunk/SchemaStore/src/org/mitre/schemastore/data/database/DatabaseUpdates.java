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
	/** Stores the current version */
	static private Integer currVersion = 3;
	
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
				stmt.addBatch("INSERT INTO version(id) VALUES("+currVersion+")");
				stmt.executeBatch();
				
				// Commit all changes
				connection.commit();
			}
			catch (Exception e)
				{ connection.rollback(); throw new SQLException("Failed to initialize database\n" + e.getMessage()); }
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

	/** Installs version 2 updates */
	static private void version2Updates(Connection connection) throws SQLException
	{
		// Add model and side fields to mapping schema table
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("ALTER TABLE mapping_schema ADD COLUMN model CHARACTER VARYING(256)");
		stmt.executeUpdate("ALTER TABLE mapping_schema ADD COLUMN side CHARACTER");
		stmt.executeUpdate("DELETE FROM annotation WHERE attribute='SchemaModelsForMapping'");
		stmt.executeUpdate("DELETE FROM annotation WHERE attribute='SchemaSidesForMapping'");
		
		// Increase the version id
		stmt.executeUpdate("UPDATE version SET id=2");
		stmt.close(); connection.commit();
	}
	
	/** Installs version 3 updates */
	static private void version3Updates(Connection connection) throws SQLException
	{
		// Increase the size of the notes field in the mapping_cell table
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("ALTER TABLE mapping_cell ADD COLUMN temp_notes CHARACTER VARYING(4096)");
		stmt.executeUpdate("UPDATE mapping_cell SET temp_notes=notes");
		stmt.executeUpdate("ALTER TABLE mapping_cell DROP COLUMN notes");
		renameColumn(stmt,"mapping_cell","temp_notes","notes");
		
		// Increase the version id
		stmt.executeUpdate("UPDATE version SET id=3");
		stmt.close(); connection.commit();
	}
	
	/** Updates the database as needed */
	static void updateDatabase(Connection connection) throws SQLException
	{
		try {
			Integer version = getVersion(connection);
			if(version<1) { version1Updates(connection); version=1; }
			if(version<2) { version2Updates(connection); version=2; }
			if(version<3) { version3Updates(connection); version=3; }
			if(version>currVersion) throw new Exception("(E) Software must be updated to handle database version " + version);
		}
		catch (Exception e)
			{ connection.rollback(); throw new SQLException("Failed to fully update database\n" + e.getMessage()); }		
	}
}