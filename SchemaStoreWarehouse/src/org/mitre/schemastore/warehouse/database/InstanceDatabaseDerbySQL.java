package org.mitre.schemastore.warehouse.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mitre.schemastore.warehouse.common.ViewDTO;

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
	protected void dropTable(String tableName) throws SQLException
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
				
				insertMaxIdData(entityTableName, i);
			} 
			catch(SQLException e) 
			{ 
				System.out.println("Values could not be inserted in Entity " + entityTableName.substring(1) + " table");
				printSQLException(e);
				throw e;
			}
		}
	}
	
	
	//--------------------------------------------------------------------------------------------------
	// Insert data into table that maintains max. value of id ever used for an Entity and its Attributes 
	//--------------------------------------------------------------------------------------------------
	protected void insertMaxIdData(String entityTableName, int value) throws SQLException
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
		} 
		catch(SQLException e) 
		{ 
			System.out.println("Data could not be inserted into table max_val_" + entityTableName);
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
	 *  This function is necessary for Derby database that does not support boolean types
	 * @throws SQLException 
	 */
	public void insertBooleanAttributeData(String attributeTableName, Integer rowNumber, String data) throws SQLException
	{
		if(data.equalsIgnoreCase("true") || data.equalsIgnoreCase("t") || data.equalsIgnoreCase("yes") || data.equalsIgnoreCase("y") || data.equalsIgnoreCase("1"))
			insertStringAttributeData(attributeTableName, rowNumber, "T");
		else if(data.equalsIgnoreCase("false") || data.equalsIgnoreCase("f") || data.equalsIgnoreCase("no") || data.equalsIgnoreCase("n") || data.equalsIgnoreCase("0"))
			insertStringAttributeData(attributeTableName, rowNumber, "F");
	}
	
	//----------------------------------------
	// Drop view corresponding to an Entity 
	//----------------------------------------
	private void dropView(String viewName) throws SQLException
	{
		String query = "DROP VIEW " + viewName;
		
		try
		{
			System.out.println("Dropping view " + viewName);
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result == 0)
				System.out.println("Dropped view " + viewName);
			stmt.close();
		}
		catch(SQLException e) 
		{
			if((e.getErrorCode() == 20000) && ("X0X05".equals(e.getSQLState())))
			{
				// View is being created for the first time - exception is ignored
				System.out.println("View " + viewName + " is being created for the first time");
			}
			else
			{
				// If the error code or SQLState is different, we have an unexpected exception
				// i.e. an existing view could not be dropped
				System.out.println("View " + viewName + " could not be dropped.");
				printSQLException(e);
				throw e;
			}
		}
	}
	
	//----------------------------------------
	// Create view corresponding to an Entity 
	//----------------------------------------
	public void createView(ViewDTO detailsOfOneView) throws SQLException
	{
		String query = null;
		
		String[] columnNames = detailsOfOneView.getColumnNames();
		String[] attributeTableNames = detailsOfOneView.getAttributeTableNames();
		
		dropView(detailsOfOneView.getViewName());
		
		StringBuffer sb = new StringBuffer("CREATE VIEW \"" + detailsOfOneView.getViewName() + "\" AS ");
		sb.append("SELECT e.id AS id, ");
		
		for(int i=0; i<(columnNames.length-1); i++)
			sb.append("a" + (i+1) + ".val AS \"" + columnNames[i] + "\", ");
		sb.append("a" + (columnNames.length) + ".val AS \"" + columnNames[columnNames.length-1] + "\" ");
		
		sb.append("FROM " + detailsOfOneView.getEntityTableName() + " AS e ");
		
		for(int i=0; i<attributeTableNames.length; i++)
			sb.append("LEFT OUTER JOIN " + attributeTableNames[i] + " AS a" + (i+1) + " ON e.id = a" + (i+1) + ".id ");
			
		query = sb.toString();
		System.out.println(query);
		
		try
		{
			System.out.println("Creating view " + detailsOfOneView.getViewName());
			Statement stmt = connection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result == 0)
				System.out.println("Created view " + detailsOfOneView.getViewName());
			stmt.close();
		}
		catch(SQLException e) 
		{ 
			System.out.println("View " + detailsOfOneView.getViewName() + " could not be created.");
			printSQLException(e);
			throw e;
		}
	}
}
