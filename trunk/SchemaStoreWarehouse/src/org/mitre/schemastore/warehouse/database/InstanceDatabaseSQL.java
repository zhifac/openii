package org.mitre.schemastore.warehouse.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
public abstract class InstanceDatabaseSQL
{
	// Constants used for naming instance tables
	protected static final String ENTITY = "E";
	protected static final String ATTRIBUTE = "A";
	protected static final String CONTAINMENT = "C";
	protected static final String RELATIONSHIP = "R";
	
	// Resources used for accessing the new database
	protected Connection connection = null;
	
	// Constructor - called when its concrete subclass is instantiated
	protected InstanceDatabaseSQL(Connection connection)
	{
		System.out.println("InstanceDatabaseSQL constructor called");
		this.connection = connection;
	}
	
	//---------------------------------------
	// Returns the constants specifying types 
	//---------------------------------------
	public abstract String getTypeNumeric();
	
	public abstract String getTypeString();
	
	public abstract String getTypeBoolean();
	
	//-----------------------------------
	// Drops table for a Entity 
	//-----------------------------------
	
	/** Drop instance table for the given Entity */
	protected abstract void dropTable(String tableName);
	
	
	//---------------------------------------------------------------------------------
	// Creates table that maintains the max. value that was ever inserted for an entity 
	//---------------------------------------------------------------------------------
	
	/** Create instance table for the given Entity */
	private void createMaxIdTable(String entityTableName)
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
			System.out.println("Table for Entity with id " + entityTableName.substring(1) + " could not be created.");
			printSQLException(e);
		}
	}
	
	
	//-------------------------------------------------------------------------------------------
	// Insert data into table that maintains the max. value that was ever inserted for an entity 
	//-------------------------------------------------------------------------------------------
	
	/** Insert data into table that maintains the max. value for an entity */
	private void insertMaxIdData(String entityTableName, int value)
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
	
	
	//-----------------------------------
	// Creates table for a Entity 
	//-----------------------------------
	
	/** Create instance table for the given Entity */
	public void createEntityTable(String entityTableName)
	{
		// Drop the table if it exists
		//dropTable(entityTableName);
		
		StringBuffer sb = new StringBuffer("CREATE TABLE " + entityTableName);
		sb.append("(");
		sb.append("id integer NOT NULL,");
		sb.append("CONSTRAINT " + entityTableName + "_pk PRIMARY KEY (id)");
		sb.append(")");
		
		String query = sb.toString();
		
		try 
		{
			System.out.println("Creating Table for Entity");
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result == 0)
				System.out.println("Created Table " + entityTableName);
			stmt.close();
			
			// Commit all changes
			connection.commit();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Table for Entity with id " + entityTableName.substring(1) + " could not be created.");
			printSQLException(e);
		}
		
		createMaxIdTable(entityTableName);
	}
	
	//-----------------------------------
	// Insert data into Entity table 
	//-----------------------------------
	
	/** Insert data into the table created for Entity */
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
	
	
	//-------------------------------------------------------------------------------------------
	// Get the value of the next valid id that can be used for appending data in existing tables 
	//-------------------------------------------------------------------------------------------
	
	/** Obtains value of the next valid id */
	public int getCurrentAbsoluteMaxId(String entityTableName)
	{
		int currentAbsoluteMaxId = 0;
		
		// Get the max value that was ever put in the id column of this table
		// All values should be given in upper case
		StringBuffer sb = new StringBuffer("SELECT C.autoincrementvalue - C.autoincrementinc ");
		sb.append("FROM sys.syscolumns C, sys.systables T ");
		sb.append("WHERE T.tablename = 'MAX_VAL_" + entityTableName + "' ");
		sb.append("AND T.tableid = C.referenceid ");
		sb.append("AND C.columnname = 'ID_AUTO'");
		
		String query = sb.toString();
		try 
		{
			System.out.println("Getting value of next valid id");
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
			System.out.println("Value of next valid id for Entity " + entityTableName.substring(1) + " could not be obtained");
			printSQLException(e);
		}
		
		return currentAbsoluteMaxId;
	}
	
	//-----------------------------------
	// Creates table for a Attribute 
	//-----------------------------------
	
	/** Create instance table for the given Attribute */
	public void createAttributeTable(String attributeTableName, String type, String entityTableName)
	{
		// Drop the table if it exists
		//dropTable(attributeTableName);
		
		StringBuffer sb = new StringBuffer("CREATE TABLE " + attributeTableName);
		sb.append("(");
		sb.append("id integer NOT NULL, ");
		sb.append("val " + type + ", ");
		sb.append("CONSTRAINT " + attributeTableName + "_pk PRIMARY KEY (id), ");
		sb.append("CONSTRAINT " + attributeTableName + "_fk FOREIGN KEY (id) REFERENCES " + entityTableName + "(id)");
		sb.append(")");
				
		String query = sb.toString();
		System.out.println(query);
		
		try 
		{
			System.out.println("Creating Table for Attribute");
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result == 0)
				System.out.println("Created Table " + attributeTableName);
			stmt.close();
			
			// Commit all changes
			connection.commit();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Table for Attribute with id " + attributeTableName.substring(1) + " could not be created.");
			printSQLException(e);
		}
	}
	
	
	//-----------------------------------
	// Insert data into Attribute table 
	//-----------------------------------
	
	/** Insert data into the table created for Attribute */
	public void insertStringAttributeData(String attributeTableName, Integer rowNumber, String data)
	{
		String query = null;
		
		StringBuffer sb = new StringBuffer("INSERT INTO " + attributeTableName + " (id, val)");
		sb.append(" VALUES (" + rowNumber + ", '" + data + "')");
			
		query = sb.toString();
		System.out.println(query);
		try 
		{
			System.out.println("Inserting data into attribute table");
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result != 0)
				System.out.println("Inserted value " + data + " into " + attributeTableName);
			stmt.close();
				
			// Commit all changes
			connection.commit();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Value could not be inserted into table for Attribute " + attributeTableName);
			printSQLException(e);
		}
	}
	
	
	/** Insert data into the table created for Attribute */
	public void insertNumericAttributeData(String attributeTableName, Integer rowNumber, String data)
	{
		String query = null;
		
		StringBuffer sb = new StringBuffer("INSERT INTO " + attributeTableName + " (id, val)");
		sb.append(" VALUES (" + rowNumber + ", " + data + ")");
			
		query = sb.toString();
		System.out.println(query);
		try 
		{
			System.out.println("Inserting data into attribute table");
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result != 0)
				System.out.println("Inserted value " + data + " into " + attributeTableName);
			stmt.close();
				
			// Commit all changes
			connection.commit();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Value could not be inserted into table for Attribute " + attributeTableName);
			printSQLException(e);
		}
	}
	
	
	/** Insert data into the table created for Attribute 
	 *  This function is necessary for Derby database that does not support boolean types
	 */
	public abstract void insertBooleanAttributeData(String attributeTableName, Integer rowNumber, String data);

	
	//--------------------------------------
	// Print details of a SQLException chain
	//--------------------------------------
	protected static void printSQLException(SQLException e)
	{
		// Unwraps the entire exception chain to unveil the real cause of the Exception
		while(e != null)
		{
			System.err.println("\n----- SQLException -----");
			System.err.println("  SQL State: " + e.getSQLState());
			System.err.println("  Error Code: " + e.getErrorCode());
			System.err.println("  Message: " + e.getMessage());
			e.printStackTrace(System.err);
			
			e = e.getNextException();
		}
	}
}
