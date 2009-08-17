package org.mitre.schemastore.warehouse.servlet;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;
import org.mitre.schemastore.warehouse.database.CreateDatabaseFactory;
import org.mitre.schemastore.warehouse.database.CreateDerbyDatabaseFactory;
import org.mitre.schemastore.warehouse.database.CreatePostgresDatabaseFactory;
import org.mitre.schemastore.warehouse.database.InstanceDatabaseSQL;

/**
 * This class handles all communication from the client regarding creation of instance tables
 * It implements methods that are called by the client
 * These methods are responsible for creating and accessing databases, creating tables and populating tables
 * @author STANDON
 *
 */
public class InstanceDatabase 
{
	/** Instance variables*/
	private InstanceRepository newDatabaseInfo = null;
	private CreateDatabaseFactory createDbFactory = null;
	private InstanceDatabaseSQL instanceDbSql = null;
	private int currentAbsoluteMaxId = 0;
	
	/** No-arg constructor */
	public InstanceDatabase()	{}
	
	/** Constructor 
	 * @throws NoDataFoundException */
	public InstanceDatabase(InstanceRepository instanceRepository) throws NoDataFoundException
	{
		/* Get the new repository information */
		this.newDatabaseInfo = instanceRepository;
		
		/* Create a new database next to the structural (M3 model) repository
		 * Obtain an instance of the class that talks to the new database
		 */
		// Get the type of repository - helps decide what type of classes to instantiate
		int instanceRepositoryType = newDatabaseInfo.getType();
		switch(instanceRepositoryType)
		{
			case 1:
				this.createDbFactory = new CreatePostgresDatabaseFactory(newDatabaseInfo);
				break;
			case 2:
				this.createDbFactory = new CreateDerbyDatabaseFactory(newDatabaseInfo);
				break;
			default:
				System.out.println("Database type is not currently supported by this application");
				throw new NoDataFoundException("Database type is not currently supported by this application");
		}
		this.instanceDbSql = createDbFactory.getInstanceDatabaseSQL();
	}// end of constructor
	
	
	//-------------------------------------------------------------------------------
	// Determines the status of connection to new database. 
	// This object has a specific "life cycle", such that SQL commands must be issued
	// through the InstanceDatabaseSQL object before calling releaseResources().
	// This method allows users to keep track of the life cycle.
	//-------------------------------------------------------------------------------
	public Boolean isNewDbConnected()
	{
		Boolean b = new Boolean(this.createDbFactory.isNewDbConnected());
		return b;
	}
	
	
	//---------------------------------------
	// Returns the constants specifying types 
	//---------------------------------------
	public String getTypeNumeric()
	{
		return instanceDbSql.getTypeNumeric();
	}
	
	public String getTypeString()
	{
		return instanceDbSql.getTypeString();
	}
	
	public String getTypeBoolean()
	{
		return instanceDbSql.getTypeBoolean();
	}
	
	
	//-----------------------------------
	// Creates table for a Entity 
	//-----------------------------------
	public void createEntityTable(String entityTableName) throws NoDataFoundException
	{
		try 
		{
			instanceDbSql.createEntityTable(entityTableName);
		} 
		catch (SQLException e1) 
		{
			// Roll back all changes to database
			try 
			{
				createDbFactory.rollback();
			} 
			catch (SQLException e2) 
			{
				// Need to manually roll back all changes
				e2.printStackTrace(System.err);
				throw new NoDataFoundException("Instance database got corrupted - manually rollback all changes " +
										"or try again with a new database name");
			}
		}
	}
	
	
	//-----------------------------------
	// Populates table for Entity 
	//-----------------------------------
	public void populateEntityTable(String entityTableName, Integer numberOfRowsOfData) throws NoDataFoundException
	{
		try 
		{
			currentAbsoluteMaxId = instanceDbSql.getCurrentAbsoluteMaxId(entityTableName);
			instanceDbSql.insertEntityData(entityTableName, numberOfRowsOfData, new Integer(currentAbsoluteMaxId));
		} 
		catch (SQLException e1) 
		{
			// Roll back all changes to database
			try 
			{
				createDbFactory.rollback();
			} 
			catch (SQLException e2) 
			{
				// Need to manually roll back all changes
				e2.printStackTrace(System.err);
				throw new NoDataFoundException("Instance database got corrupted - manually rollback all changes " +
										"or try again with a new database name");
			}
		}
		
	}
	
	
	//-----------------------------------
	// Creates table for a Attribute 
	//-----------------------------------
	public void createAttributeTable(String attributeTableName, String type, String entityTableName) throws NoDataFoundException
	{
		try 
		{
			instanceDbSql.createAttributeTable(attributeTableName, type, entityTableName);
		} 
		catch (SQLException e1) 
		{
			// Roll back all changes to database
			try 
			{
				createDbFactory.rollback();
			} 
			catch (SQLException e2) 
			{
				// Need to manually roll back all changes
				e2.printStackTrace(System.err);
				throw new NoDataFoundException("Instance database got corrupted - manually rollback all changes " +
										"or try again with a new database name");
			}
		}
	}
	
	
	//-----------------------------------
	// Populates table for a Attribute 
	//-----------------------------------
	public void populateStringAttributeTable(String attributeTableName, String[] values) throws NoDataFoundException
	{
		List<String> valuesList = Arrays.asList(values);  
		int numberOfRowsOfData = valuesList.size();
	    
		for(int n=1; n<=numberOfRowsOfData; n++)
	    {
			String data = valuesList.get(n-1);
			try 
			{
				instanceDbSql.insertStringAttributeData(attributeTableName, currentAbsoluteMaxId+n, data);
			} 
			catch (SQLException e1) 
			{
				// Roll back all changes to database
				try 
				{
					createDbFactory.rollback();
				} 
				catch (SQLException e2) 
				{
					// Need to manually roll back all changes
					e2.printStackTrace(System.err);
					throw new NoDataFoundException("Instance database got corrupted - manually rollback all changes " +
											"or try again with a new database name");
				}
			}
	    }   
	}
	
	public void populateNumericAttributeTable(String attributeTableName, String[] values) throws NoDataFoundException
	{
		List<String> valuesList = Arrays.asList(values);  
		int numberOfRowsOfData = valuesList.size();
	    
		for(int n=1; n<=numberOfRowsOfData; n++)
	    {
			String data = valuesList.get(n-1);
			try 
			{
				instanceDbSql.insertNumericAttributeData(attributeTableName, currentAbsoluteMaxId+n, data);
			} 
			catch (SQLException e1) 
			{
				// Roll back all changes to database
				try 
				{
					createDbFactory.rollback();
				} 
				catch (SQLException e2) 
				{
					// Need to manually roll back all changes
					e2.printStackTrace(System.err);
					throw new NoDataFoundException("Instance database got corrupted - manually rollback all changes " +
											"or try again with a new database name");
				}
			}
	    }   
	}
	
	public void populateBooleanAttributeTable(String attributeTableName, String[] values) throws NoDataFoundException
	{
		List<String> valuesList = Arrays.asList(values);  
		int numberOfRowsOfData = valuesList.size();
	    
		for(int n=1; n<=numberOfRowsOfData; n++)
	    {
			String data = valuesList.get(n-1);
			try 
			{
				instanceDbSql.insertBooleanAttributeData(attributeTableName, currentAbsoluteMaxId+n, data);
			} 
			catch (SQLException e) 
			{
				// Roll back all changes to database
				try 
				{
					createDbFactory.rollback();
				} 
				catch (SQLException e2) 
				{
					// Need to manually roll back all changes
					e2.printStackTrace(System.err);
					throw new NoDataFoundException("Instance database got corrupted - manually rollback all changes " +
											"or try again with a new database name");
				}
			}
	    }   
	}
	
	
	//-----------------------------------------------
	// Releases resources related to the new database 
	//-----------------------------------------------
	public void releaseResources()
	{
		try 
		{
			createDbFactory.releaseResources();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace(System.err);
		}
	}

}
