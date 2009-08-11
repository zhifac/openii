package org.mitre.schemastore.warehouse.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  This class handles access to the Derby database
 * 
 *  Tables are created under the schema name same as 
 *  the user name that you are currently logged in as.
 *  This is different from the M3 model schema name found in
 *  the structural repository to the which the schema elements belong to
 * @author STANDON
 */
public class InstanceDatabaseDerbySQL extends InstanceDatabaseSQL
{
	// Constructor
	public InstanceDatabaseDerbySQL(Connection connection) 
	{	
		super(connection);
		System.out.println("InstanceDatabaseDerby constructor called");
	}

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

	
	//------------------------
	// Drop an instance table 
	//------------------------
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
	
	
	//----------------------------------------------------------------------------------------
	// Create table that maintains max. value of id ever used for an Entity and its Attributes 
	//----------------------------------------------------------------------------------------
	protected void createMaxIdTable(String entityTableName)
	{
		// Drop the table if it exists
		//dropTable("max_val_" + entityTableName.substring(1));
		
		StringBuffer sb = new StringBuffer("CREATE TABLE max_val_" + entityTableName);
		sb.append("(");
		sb.append("id_auto INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),");
		sb.append("id_value INTEGER");
		sb.append(")");
		
		String query = sb.toString();
		
		try 
		{
			System.out.println("Creating Max. Value Table for Entity" + entityTableName.substring(1));
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result == 0)
				System.out.println("Created Table max_val_" + entityTableName);
			stmt.close();
			
			// Commit all changes
			connection.commit();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Max. Value Table for Entity with id " + entityTableName.substring(1) + " could not be created.");
			printSQLException(e);
		}
	}
	
	
	//-----------------------------------
	// Insert data into Entity table 
	//-----------------------------------
	public void insertEntityData(String entityTableName, Integer numberOfRowsOfData, Integer currentAbsoluteMaxId)
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
				
				insertMaxIdData(entityTableName, i);
				
				// Commit all changes
				connection.commit();
			} 
			catch(SQLException e) 
			{ 
				System.out.println("Values could not be inserted in Entity " + entityTableName.substring(1) + " tables");
				printSQLException(e);
			}
		}
	}
	
	
	//--------------------------------------------------------------------------------------------------
	// Insert data into table that maintains max. value of id ever used for an Entity and its Attributes 
	//--------------------------------------------------------------------------------------------------
	protected void insertMaxIdData(String entityTableName, int value)
	{
		StringBuffer sb = new StringBuffer("INSERT INTO max_val_" + entityTableName + " (id_value)");
		sb.append(" VALUES (" + value + ")");
		
		String query = sb.toString();
		
		try 
		{
			System.out.println("Inserting data into max. value table");
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result != 0)
				System.out.println("Inserted id value " + value + " into max_val_" + entityTableName);
			stmt.close();
			
			// Commit all changes
			//connection.commit();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Data could not be inserted into table max_val_" + entityTableName);
			printSQLException(e);
		}
	}
	
	
	//------------------------------------------------------------------------------
	// Get max. value of id that was ever inserted for an Entity and its Attributes  
	//------------------------------------------------------------------------------
	public int getCurrentAbsoluteMaxId(String entityTableName)
	{
		int currentAbsoluteMaxId = 0;
		
		// Get the max value that was ever put in the id column of this table
		// All values inside the quotes should be given in upper case - requirement of Derby
		StringBuffer sb = new StringBuffer("SELECT C.autoincrementvalue - C.autoincrementinc ");
		sb.append("FROM sys.syscolumns C, sys.systables T ");
		sb.append("WHERE T.tablename = 'MAX_VAL_" + entityTableName + "' ");
		sb.append("AND T.tableid = C.referenceid ");
		sb.append("AND C.columnname = 'ID_AUTO'");
		
		String query = sb.toString();
		try 
		{
			System.out.println("Getting value of current max. id");
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) 
				currentAbsoluteMaxId = rs.getInt(1);
			stmt.close();
			
			// Commit all changes
			connection.commit();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Value of current max. id for Entity " + entityTableName.substring(1) + " could not be obtained");
			printSQLException(e);
		}
		
		return currentAbsoluteMaxId;
	}
	
	//-----------------------------------
	// Insert data into Attribute table 
	//-----------------------------------
	
	/** Insert data into table created for Boolean type Attribute 
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
