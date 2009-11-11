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
	static private Integer currVersion = 6;

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

	/** Renames the specified table (different versions for Derby and Postgres) */
	static private void renameTable(Statement stmt, String table, String newTableName) throws SQLException
	{
		try { stmt.executeUpdate("ALTER TABLE " + table + " RENAME TO " + newTableName); }
		catch(Exception e) { stmt.executeUpdate("RENAME TABLE " + table + " TO " + newTableName); }
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

	/**
	 *  Installs version 4 updates.  These changes relate to adding functions to the mappings..as a result, the mapping_cell table is split into 3.
     *  One for proposed mappings and two for validated mappings.  The second is needed to support multiple inputs that functions can take in.
     */
	static private void version4Updates(Connection connection) throws SQLException
	{
		// Create the new mapping cell tables
		Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE proposed_mapping_cell (id integer NOT NULL, mapping_id integer, input_id integer, output_id integer, score numeric(6,3), author character varying(100), modification_date date, notes character varying(4096))");
        stmt.executeUpdate("CREATE TABLE validated_mapping_cell (id integer NOT NULL, mapping_id integer, function_class character varying(100), output_id integer, author character varying(100), modification_date date, notes character varying(4096) ) ");
        stmt.executeUpdate("CREATE TABLE mapping_input (cell_id integer, input_id integer, input_order integer ) ");

        // Set up the new primary keys
        stmt.executeUpdate("ALTER TABLE proposed_mapping_cell ADD CONSTRAINT pmappingcell_pkey PRIMARY KEY (id)");
        stmt.executeUpdate("ALTER TABLE validated_mapping_cell ADD CONSTRAINT vmappingcell_pkey PRIMARY KEY (id)");
        stmt.executeUpdate("ALTER TABLE proposed_mapping_cell ADD CONSTRAINT pmappingcell_mapping_id_fkey FOREIGN KEY (mapping_id) REFERENCES mapping(id)");
        stmt.executeUpdate("ALTER TABLE validated_mapping_cell ADD CONSTRAINT vmappingcell_mapping_id_fkey FOREIGN KEY (mapping_id) REFERENCES mapping(id)");

        // Update the notes field to include the transform notes
        stmt.executeUpdate("UPDATE mapping_cell SET notes = notes || '<transform>' || transform || '</transform>' WHERE transform IS NOT NULL AND transform!=''");
        
        // Move all data to the new mapping cell table
        stmt.executeUpdate("INSERT INTO proposed_mapping_cell (id, mapping_id, input_id, output_id, score, author, modification_date, notes) select id, mapping_id, element1_id, element2_id, score, author, modification_date, notes from mapping_cell where validated = 'f'");
        stmt.executeUpdate("INSERT INTO validated_mapping_cell (id, mapping_id, function_class, output_id, author, modification_date, notes) select id, mapping_id, 'org.mitre.schemastore.mapfunctions.NullFunction', element2_id, author, modification_date, notes from mapping_cell where validated = 't'");
        stmt.executeUpdate("INSERT INTO mapping_input (cell_id, input_id, input_order) select id, element1_id, 1 from mapping_cell where validated = 't'");

        // Drop the old mapping cell table
        stmt.executeUpdate("DROP TABLE mapping_cell");

        // Increase the version id
		stmt.executeUpdate("UPDATE version SET id=4");
		stmt.close(); connection.commit();
	}

	/** Installs version 5 updates */
	static private void version5Updates(Connection connection) throws SQLException
	{
		// Increase the size of the notes field in the proposed_mapping_cell table
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("ALTER TABLE proposed_mapping_cell ADD COLUMN temp_notes CHARACTER VARYING(4096)");
		stmt.executeUpdate("UPDATE proposed_mapping_cell SET temp_notes=notes");
		stmt.executeUpdate("ALTER TABLE proposed_mapping_cell DROP COLUMN notes");
		renameColumn(stmt,"proposed_mapping_cell","temp_notes","notes");

		// Increase the size of the notes field in the validated_mapping_cell table
		stmt.executeUpdate("ALTER TABLE validated_mapping_cell ADD COLUMN temp_notes CHARACTER VARYING(4096)");
		stmt.executeUpdate("UPDATE validated_mapping_cell SET temp_notes=notes");
		stmt.executeUpdate("ALTER TABLE validated_mapping_cell DROP COLUMN notes");
		renameColumn(stmt,"validated_mapping_cell","temp_notes","notes");

		// Increase the version id
		stmt.executeUpdate("UPDATE version SET id=5");
		stmt.close(); connection.commit();
	}

	/** Installs version 6 updates */
	static private void version6Updates(Connection connection) throws SQLException
	{
		Statement stmt = connection.createStatement();

		// Remove contraints referencing tables and columns referencing "group"
		stmt.executeUpdate("ALTER TABLE schema_group DROP CONSTRAINT schemagroup_schema_fkey");
		stmt.executeUpdate("ALTER TABLE schema_group DROP CONSTRAINT schemagroup_groups_fkey");
		stmt.executeUpdate("ALTER TABLE groups DROP CONSTRAINT groups_groups_fkey");
		stmt.executeUpdate("ALTER TABLE groups DROP CONSTRAINT groups_pkey");
		stmt.executeUpdate("DROP INDEX schema_group_idx");
		
		// Rename group to tag on database tables and columns
		renameTable(stmt,"groups","tags");
		renameTable(stmt,"schema_group","schema_tag");
		renameColumn(stmt,"schema_tag","group_id","tag_id");

		// Add constraints back to reference renamed tables and columns
		stmt.executeUpdate("ALTER TABLE tags ADD CONSTRAINT tags_pkey PRIMARY KEY (id)");
		stmt.executeUpdate("ALTER TABLE tags ADD CONSTRAINT tags_tags_fkey FOREIGN KEY (parent_id) REFERENCES tags(id)");		
		stmt.executeUpdate("ALTER TABLE schema_tag ADD CONSTRAINT schematag_tags_fkey FOREIGN KEY (tag_id) REFERENCES tags(id)");
		stmt.executeUpdate("ALTER TABLE schema_tag ADD CONSTRAINT schematag_schema_fkey FOREIGN KEY (schema_id) REFERENCES \"schema\"(id)");
		stmt.executeUpdate("CREATE UNIQUE INDEX schema_tag_idx ON schema_tag (schema_id, tag_id)");
		
		// Increase the version id
		stmt.executeUpdate("UPDATE version SET id=6");
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
			if(version<4) { version4Updates(connection); version=4; }
			if(version<5) { version5Updates(connection); version=5; }
			if(version<6) { version6Updates(connection); version=6; }
			if(version>currVersion) throw new Exception("(E) Software must be updated to handle database version " + version);
		}
		catch (Exception e)
			{ connection.rollback(); throw new SQLException("Failed to fully update database\n" + e.getMessage()); }
	}
}