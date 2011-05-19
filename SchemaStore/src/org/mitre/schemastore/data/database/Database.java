// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mitre.schemastore.model.Annotation;

/**
 * Handles access to the database
 * @author CWOLF
 */
public class Database
{
	/** Sets up a database connection */
	private DatabaseConnection connection = null;

	// Stores the various data call classes
	private SchemaDataCalls schemaDataCalls = null;
	private SchemaRelationshipsDataCalls schemaRelationshipsDataCalls = null;
	private SchemaElementDataCalls schemaElementDataCalls = null;
	private TagDataCalls tagDataCalls = null;
	private DataSourceDataCalls dataSourceDataCalls = null;
	private ProjectDataCalls projectDataCalls = null;
	private FunctionDataCalls functionDataCalls = null;
	
	/** Constructs the database class */
	public Database(DatabaseConnection connection)
	{
		this.connection = connection;
		schemaDataCalls = new SchemaDataCalls(connection);
		schemaRelationshipsDataCalls = new SchemaRelationshipsDataCalls(connection);
		schemaElementDataCalls = new SchemaElementDataCalls(connection);
		tagDataCalls = new TagDataCalls(connection);
		dataSourceDataCalls = new DataSourceDataCalls(connection);
		projectDataCalls = new ProjectDataCalls(connection);
		functionDataCalls = new FunctionDataCalls(connection);
	}

	// Data call getters
	public SchemaDataCalls getSchemaDataCalls() { return schemaDataCalls; }
	public SchemaRelationshipsDataCalls getSchemaRelationshipsDataCalls() { return schemaRelationshipsDataCalls; }
	public SchemaElementDataCalls getSchemaElementDataCalls() { return schemaElementDataCalls; }
	public TagDataCalls getTagDataCalls() { return tagDataCalls; }
	public DataSourceDataCalls getDataSourceDataCalls() { return dataSourceDataCalls; }
	public ProjectDataCalls getProjectDataCalls() { return projectDataCalls; }
	public FunctionDataCalls getFunctionDataCalls() { return functionDataCalls; }
	
    /** Indicates if the database is properly connected */
 	public boolean isConnected()
 		{ try { connection.getStatement(); return true; } catch(SQLException e) { return false; } }
 
	/** Retrieves a universal id */
	public Integer getUniversalIDs(int count) throws SQLException
	{
		Statement stmt = connection.getStatement();
		stmt.executeUpdate("LOCK TABLE universal_id IN exclusive MODE");
		stmt.executeUpdate("UPDATE universal_id SET id = id+"+count);
		ResultSet rs = stmt.executeQuery("SELECT id FROM universal_id");
		rs.next();
		Integer universalID = rs.getInt("id")-count;
		stmt.close();
		connection.commit();
		return universalID;
	}
 	
 	/** Compresses the database */
 	public boolean compress()
 	{
		try {
    		Statement stmt = connection.getStatement();

    		// Compresses a derby database
    		if(connection.getDatabaseType().equals(DatabaseConnection.DERBY))
        	{
    			// Retrieve the tables to compress
    			ArrayList<String> tableNames = new ArrayList<String>();
    			ResultSet rs = stmt.executeQuery("SELECT tablename FROM SYS.SYSTABLES WHERE TABLETYPE='T'");
    			while(rs.next())
    				tableNames.add(rs.getString("tablename"));
    			
    			// Compress the tables
				String schema = connection.getDatabaseUser().toUpperCase();
				for(String tableName : tableNames)
    				stmt.execute("CALL SYSCS_UTIL.SYSCS_COMPRESS_TABLE('"+schema+"','"+tableName+"',0)");
        	}
			
			// Compresses a postgres database
			else
			{
				connection.setAutoCommit(true);
				stmt.execute("VACUUM FULL");
				connection.setAutoCommit(false);
			}
			
			stmt.close();
			connection.commit();
			return true;
		}
  		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:compress: "+e.getMessage());
	  		return false;
		}
 	}

	//-------------------------------------
	// Handles Annotations in the Database
	//-------------------------------------

	/** Sets the specified annotation in the database */
	public boolean setAnnotation(int elementID, Integer groupID, String attribute, String value)
	{
		boolean success = false;
		try {
			Statement stmt = connection.getStatement();
			
			// Delete old annotation
			stmt.executeUpdate("DELETE FROM annotation WHERE element_id="+elementID +
					  		   " AND attribute='"+attribute+"'" +
					  		   " AND group_id " + (groupID==null ? "IS NOT NULL" : "="+groupID));

			// Insert the new annotation
			stmt.executeUpdate("INSERT INTO annotation(element_id,group_id,attribute,value)" +
							   " VALUES("+elementID+","+groupID+",'"+attribute+"','"+value+"')");

			stmt.close();
			connection.commit();
			success = true;
		}
		catch(SQLException e)
		{
			try { connection.rollback(); } catch(SQLException e2) {}
			System.out.println("(E) Database:setAnnotation: "+e.getMessage());
		}
		return success;
	}

	/** Gets the specified annotation in the database */
	public String getAnnotation(int elementID, String attribute)
	{
		String value = null;
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT value FROM annotation WHERE element_id="+elementID+" AND attribute='"+attribute+"'");
			if(rs.next()) value = rs.getString("value");
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getAnnotation: "+e.getMessage()); }
		return value;
	}

	/** Gets the annotations for the specified group */
	public ArrayList<Annotation> getAnnotations(int groupID, String attribute)
	{
		ArrayList<Annotation> annotations = new ArrayList<Annotation>();
		try {
			Statement stmt = connection.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT element_id, value FROM annotation WHERE group_id="+groupID+" AND attribute='"+attribute+"'");
			while(rs.next())
				annotations.add(new Annotation(rs.getInt("element_id"),rs.getString("value")));
			stmt.close();
		} catch(SQLException e) { System.out.println("(E) Database:getAnnotation: "+e.getMessage()); }
		return annotations;
	}
}