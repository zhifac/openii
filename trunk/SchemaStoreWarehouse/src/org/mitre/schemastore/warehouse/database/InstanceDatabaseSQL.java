package org.mitre.schemastore.warehouse.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class handles access to the databases
 * 
 * Tables are created under schema names that are 
 * different from the M3 model schema name found in
 * the structural repository to the which the schema elements belong to
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
	
	//------------------------
	// Drop an instance table 
	//------------------------
	protected abstract void dropTable(String tableName) throws SQLException;
	
	
	//----------------------------------------------------------------------------------------
	// Create table that maintains max. value of id ever used for an Entity and its Attributes 
	//----------------------------------------------------------------------------------------
	protected abstract void createMaxIdTable(String entityTableName) throws SQLException;
	
	
	//-----------------------------------
	// Creates table for a Entity 
	//-----------------------------------
	/** Create instance table for the given Entity 
	 * @throws SQLException */
	public void createEntityTable(String entityTableName) throws SQLException
	{
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
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Table for Entity with id " + entityTableName.substring(1) + " could not be created.");
			printSQLException(e);
			throw e;
		}
		
		createMaxIdTable(entityTableName);
	}
	
	//-----------------------------------
	// Insert data into Entity table 
	//-----------------------------------
	public abstract void insertEntityData(String entityTableName, Integer numberOfRowsOfData, Integer currentAbsoluteMaxId) throws SQLException;

	
	//--------------------------------------------------------------------------------------------------
	// Insert data into table that maintains max. value of id ever used for an Entity and its Attributes 
	//--------------------------------------------------------------------------------------------------
	protected abstract void insertMaxIdData(String entityTableName, int value) throws SQLException;
	
	
	//------------------------------------------------------------------------------
	// Get max. value of id that was ever inserted for an Entity and its Attributes  
	//------------------------------------------------------------------------------
	public abstract int getCurrentAbsoluteMaxId(String entityTableName) throws SQLException;

	
	//---------------------------------------
	// Creates instance table for a Attribute 
	//---------------------------------------
	public void createAttributeTable(String attributeTableName, String type, String entityTableName) throws SQLException
	{
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
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Table for Attribute with id " + attributeTableName.substring(1) + " could not be created.");
			printSQLException(e);
			throw e;
		}
	}
	
	
	//-----------------------------------
	// Insert data into Attribute table 
	//-----------------------------------
	
	/** Insert data into table created for String type Attribute 
	 * @throws SQLException */
	public void insertStringAttributeData(String attributeTableName, Integer rowNumber, String data) throws SQLException
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
				System.out.println("Inserted value " + data + " into table " + attributeTableName);
			stmt.close();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Value could not be inserted into table for Attribute " + attributeTableName.substring(1));
			printSQLException(e);
			throw e;
		}
	}
	
	
	/** Insert data into table created for Numeric type Attribute 
	 * @throws SQLException */
	public void insertNumericAttributeData(String attributeTableName, Integer rowNumber, String data) throws SQLException
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
				System.out.println("Inserted value " + data + " into table " + attributeTableName);
			stmt.close();
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Value could not be inserted into table for Attribute " + attributeTableName.substring(1));
			printSQLException(e);
			throw e;
		}
	}
	
	
	/** Insert data into table created for Boolean type Attribute 
	 *  This function is specialized for Derby database that does not support boolean types
	 * @throws SQLException 
	 */
	public abstract void insertBooleanAttributeData(String attributeTableName, Integer rowNumber, String data) throws SQLException;
	
	
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
