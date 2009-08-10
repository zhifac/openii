package org.mitre.schemastore.warehouse.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.mitre.schemastore.model.Attribute;

/**
 *  This class creates tables under the schema name same as 
 *  the user name that you are currently logged in as.
 *  This is different from the M3 model schema name found in
 *  the repository to the which the schema elements belong to
 */

/**
 * Handles access to the database
 * @author STANDON
 */
public class InstanceDatabasePostgresSQL extends InstanceDatabaseSQL
{
	// Constructor
	protected InstanceDatabasePostgresSQL(Connection connection) 
	{	
		super(connection);
		System.out.println("InstanceDatabasePostgres constructor called");
	}

	// Constants used in specifying types
	private static final String TYPE_NUMERIC = "decimal";
	private static final String TYPE_STRING = "text";
	private static final String TYPE_BOOLEAN = "boolean";
	

	//---------------------------------------
	// Returns the constants specifying types 
	//---------------------------------------
	public String getTypeNumeric()
	{	return TYPE_NUMERIC;	}
	
	public String getTypeString()
	{	return TYPE_STRING;	}
	
	public String getTypeBoolean()
	{	return TYPE_BOOLEAN;	}
	
	//-----------------------------------
	// Drops a table 
	//-----------------------------------
	
	/** Drop a instance table */
	protected void dropTable(String tableName)
	{
		String query = "DROP TABLE " + tableName + " CASCADE";
		try 
		{
			System.out.println("Dropping Table for Entity");
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result == 0)
				System.out.println("Dropped Table " + tableName);
			stmt.close();
			
			// Commit all changes
			connection.commit();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Table " + tableName + " could not be dropped.");
			printSQLException(e);
		}
	}
	
	
	
	/** Insert data into the table created for Attribute */
	public void insertBooleanAttributeData(String attributeTableName, Integer rowNumber, String data)
	{
		insertStringAttributeData(attributeTableName, rowNumber, data);
	}
}
