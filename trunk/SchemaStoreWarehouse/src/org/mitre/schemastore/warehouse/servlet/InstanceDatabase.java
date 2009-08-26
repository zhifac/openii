package org.mitre.schemastore.warehouse.servlet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;
import org.mitre.schemastore.warehouse.common.ViewDTO;
import org.mitre.schemastore.warehouse.common.ViewDTOArray;
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
	
	public String getTypeDate()
	{
		return instanceDbSql.getTypeDate();
	}
	
	public String getTypeNull()
	{
		return instanceDbSql.getTypeNull();
	}
	
	// Get all types in the following order -
	// Numeric, String, Boolean, Date, Null
	public String[] getAllTypes()
	{
		List<String> listOfTypes = instanceDbSql.getAllTypes();
		String[] arrayOfTypes = listOfTypes.toArray(new String[listOfTypes.size()]);
		
		return arrayOfTypes;
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
		catch (SQLException e) 
		{
			// Roll back all changes to instance database
			rollback();

			// Inform the client
			throw new NoDataFoundException("Table for Entity with id " + entityTableName.substring(1) + " could not be created.");
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
		catch (SQLException e) 
		{
			// Roll back all changes to instance database
			rollback();
			
			// Inform the client
			throw new NoDataFoundException("Values could not be inserted in Entity " + entityTableName.substring(1) + " table");
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
		catch (SQLException e) 
		{
			// Roll back all changes to instance database
			rollback();
			
			// Inform the client
			throw new NoDataFoundException("Table for Attribute with id " + attributeTableName.substring(1) + " could not be created.");
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
			catch (SQLException e) 
			{
				// Roll back all changes to instance database
				rollback();
				
				// Inform the client
				throw new NoDataFoundException("Value could not be inserted into table for Attribute " + attributeTableName.substring(1));
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
			catch (SQLException e) 
			{
				// Roll back all changes to instance database
				rollback();
				
				// Inform the client
				throw new NoDataFoundException("Value could not be inserted into table for Attribute " + attributeTableName.substring(1));
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
				// Roll back all changes to instance database
				rollback();
				
				// Inform the client
				throw new NoDataFoundException("Value could not be inserted into table for Attribute " + attributeTableName.substring(1));
			}
	    }   
	}
	
	public void populateDateAttributeTable(String attributeTableName, String[] values) throws NoDataFoundException
	{
		List<String> valuesList = Arrays.asList(values);  
		int numberOfRowsOfData = valuesList.size();
	    
		for(int n=1; n<=numberOfRowsOfData; n++)
	    {
			String data = valuesList.get(n-1);
			try 
			{
				instanceDbSql.insertDateAttributeData(attributeTableName, currentAbsoluteMaxId+n, data);
			} 
			catch (SQLException e) 
			{
				// Roll back all changes to instance database
				rollback();
				
				// Inform the client
				throw new NoDataFoundException("Value could not be inserted into table for Attribute " + attributeTableName.substring(1));
			}
	    }   
	}
	
	
	//-----------------------------------
	// Creates view for all Entities 
	//-----------------------------------
	public void createAllViews(ViewDTOArray v) throws NoDataFoundException
	{
		ViewDTO[] arrayOfViewDetails = v.getArrayOfViewDetails();
		for(int i=0; i<arrayOfViewDetails.length; i++)
		{
			ViewDTO detailsOfOneView = arrayOfViewDetails[i];
			try 
			{
				instanceDbSql.createView(detailsOfOneView);
			} 
			catch (SQLException e) 
			{
				// Roll back all changes to instance database
				rollback();

				// Inform the client
				throw new NoDataFoundException("View for sheet " + detailsOfOneView.getViewName() + " could not be created.");
			}
		}
	}
	
	
	//-----------------------------------------------------------------------
	// Commits all changes and Releases resources related to the new database 
	//-----------------------------------------------------------------------
	public void releaseResources()
	{
		try 
		{
			createDbFactory.releaseResources();
		} 
		catch (SQLException e) 
		{
			// If there is a problem releasing resources, it is logged 
			// But, the exception is not propagated to the client
			e.printStackTrace(System.err);
		}
	}
	
	
	//--------------------------------------------------
	// Undo all changes made in the current transaction 
	//--------------------------------------------------
	public void rollback() throws NoDataFoundException
	{
		try 
		{
			createDbFactory.rollback();
		} 
		catch (SQLException e) 
		{
			// Roll back failed - Need to manually roll back all changes
			// Log the error
			e.printStackTrace(System.err);
			
			// Inform the client
			throw new NoDataFoundException("Instance database got corrupted - manually rollback all changes " +
									"or try again with a new database name");
		}
	}
	
	

}
