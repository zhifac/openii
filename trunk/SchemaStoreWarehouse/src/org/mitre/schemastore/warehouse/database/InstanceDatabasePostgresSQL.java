package org.mitre.schemastore.warehouse.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  This class handles access to PostgreSQL database
 * 
 *  Tables are created under the default schema 'public' 
 *  and are owned by the user name that you are currently logged-in as.
 *  This is different from the M3 model schema name found in
 *  the structural repository to the which the schema elements belong to
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

	
	//------------------------
	// Drop an instance table 
	//------------------------
	protected void dropTable(String tableName) throws SQLException
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
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Table " + tableName + " could not be dropped.");
			printSQLException(e);
			throw e;
		}
	}
	
	//----------------------------------------------------------------------------------------
	// Create table that maintains max. value of id ever used for an Entity and its Attributes 
	//----------------------------------------------------------------------------------------
	protected void createMaxIdTable(String entityTableName) throws SQLException
	{
		String query = "CREATE SEQUENCE max_val_seq_" + entityTableName.toLowerCase() + " MINVALUE 0";
		try 
		{
			System.out.println("Creating Max. Value Table for Entity" + entityTableName.substring(1));
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result == 0)
				System.out.println("Created Table max_val_seq_" + entityTableName);
			stmt.close();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Max. Value Table for Entity with id " + entityTableName.substring(1) + " could not be created.");
			printSQLException(e);
			throw e;
		}
	}
	
	//-----------------------------------
	// Insert data into Entity table 
	//-----------------------------------
	public void insertEntityData(String entityTableName, Integer numberOfRowsOfData, Integer currentAbsoluteMaxId) throws SQLException
	{
		String query = null;
		for(int i=(currentAbsoluteMaxId+1); i<=(currentAbsoluteMaxId+numberOfRowsOfData); i++)
		{
			StringBuffer sb = new StringBuffer("INSERT INTO " + entityTableName + " (id)");
			sb.append(" VALUES (" + i + ")");
			
			query = sb.toString();
			try 
			{
				System.out.println("Inserting data into entity table");
				Statement stmt = connection.createStatement();
				int result = stmt.executeUpdate(query);
				if(result != 0)
					System.out.println("Inserted id value " + i + " into " + entityTableName);
				stmt.close();
			} 
			catch(SQLException e) 
			{ 
				System.out.println("Values could not be inserted in Entity " + entityTableName.substring(1) + " table");
				printSQLException(e);
				throw e;
			}
		}
		
		int newAbsoluteMaxId = currentAbsoluteMaxId+numberOfRowsOfData;
		insertMaxIdData(entityTableName, newAbsoluteMaxId);
	}
	
	
	//--------------------------------------------------------------------------------------------------
	// Insert data into table that maintains max. value of id ever used for an Entity and its Attributes 
	//--------------------------------------------------------------------------------------------------
	protected void insertMaxIdData(String entityTableName, int value) throws SQLException
	{
		String query = "SELECT setval('max_val_seq_" + entityTableName.toLowerCase() + "', " + value + ", false)";
		try 
		{
			System.out.println("Inserting data into max. value table");
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) 
			{
				int currentAbsoluteMaxId = rs.getInt(1);
				if(currentAbsoluteMaxId == value)
					System.out.println("Inserted new max. value " + value + " into max_val_seq_" + entityTableName.toLowerCase());
			}
			rs.close();
			stmt.close();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Data could not be inserted into table max_val_seq_" + entityTableName.toLowerCase());
			printSQLException(e);
			throw e;
		}
	}
	
	
	//------------------------------------------------------------------------------
	// Get max. value of id that was ever inserted for an Entity and its Attributes  
	//------------------------------------------------------------------------------
	public int getCurrentAbsoluteMaxId(String entityTableName) throws SQLException
	{
		int currentAbsoluteMaxId = 0;
		
		// Get the max value that was ever put in the id column of instance tables
		// All values should be given in lower case - requirement of PostgreSQL		
		String query = "SELECT nextval('max_val_seq_" + entityTableName.toLowerCase() + "')";
		try 
		{
			System.out.println("Getting value of current max. id");
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) 
				currentAbsoluteMaxId = rs.getInt(1);
			rs.close();
			stmt.close();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Value of current max. id for Entity " + entityTableName.substring(1) + " could not be obtained");
			printSQLException(e);
			throw e;
		}
		return currentAbsoluteMaxId;
	}
	
	
	//-----------------------------------
	// Insert data into Attribute table 
	//-----------------------------------
	
	/** Insert data into table created for Boolean type Attribute	
	 * @throws SQLException */
	public void insertBooleanAttributeData(String attributeTableName, Integer rowNumber, String data) throws SQLException
	{
		insertStringAttributeData(attributeTableName, rowNumber, data);
	}
}
