package org.mitre.schemastore.warehouse.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.mitre.schemastore.data.database.DatabaseConnection;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;

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
public class InstanceDatabaseDerbySQL extends InstanceDatabaseSQL
{
	// Constructor
	public InstanceDatabaseDerbySQL(Connection connection) 
	{	super(connection);	}

	// Constants used in specifying types
	private static final String TYPE_NUMERIC = "decimal(30,10)";
	private static final String TYPE_STRING = "varchar(30)";
	private static final String TYPE_BOOLEAN = "char(1)";
	
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
		String query = "DROP TABLE " + tableName;
		try 
		{
			System.out.println("Dropping Table");
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
	
	//-----------------------------------
	// Insert data into Attribute table 
	//-----------------------------------
	
	/** Insert data into the table created for Attribute 
	 *  This function is necessary for Derby database that does not support boolean types
	 */
	public void insertBooleanAttributeData(String attributeTableName, Integer rowNumber, String data)
	{
		if(data.toString().equals("true") || data.toString().equals("yes"))
			insertStringAttributeData(attributeTableName, rowNumber, "1");
		else if(data.toString().equals("false") || data.toString().equals("no"))
			insertStringAttributeData(attributeTableName, rowNumber, "0");
	}
}
