package org.mitre.schemastore.warehouse.servlet;

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
	public void createEntityTable(String entityTableName)
	{
		instanceDbSql.createEntityTable(entityTableName);
	}
	
	
	//-----------------------------------
	// Populates table for Entity 
	//-----------------------------------
	public void populateEntityTable(String entityTableName, Integer numberOfRowsOfData)
	{
		currentAbsoluteMaxId = instanceDbSql.getCurrentAbsoluteMaxId(entityTableName);
		instanceDbSql.insertEntityData(entityTableName, numberOfRowsOfData, new Integer(currentAbsoluteMaxId));
	}
	
	
	//-----------------------------------
	// Creates table for a Attribute 
	//-----------------------------------
	public void createAttributeTable(String attributeTableName, String type, String entityTableName)
	{
		instanceDbSql.createAttributeTable(attributeTableName, type, entityTableName);
	}
	
	
	//-----------------------------------
	// Populates table for a Attribute 
	//-----------------------------------
	public void populateStringAttributeTable(String attributeTableName, String[] values)
	{
		List<String> valuesList = Arrays.asList(values);  
		int numberOfRowsOfData = valuesList.size();
	    
		for(int n=1; n<=numberOfRowsOfData; n++)
	    {
			String data = valuesList.get(n-1);
			instanceDbSql.insertStringAttributeData(attributeTableName, currentAbsoluteMaxId+n, data);
	    }   
	}
	
	public void populateNumericAttributeTable(String attributeTableName, String[] values)
	{
		List<String> valuesList = Arrays.asList(values);  
		int numberOfRowsOfData = valuesList.size();
	    
		for(int n=1; n<=numberOfRowsOfData; n++)
	    {
			String data = valuesList.get(n-1);
			instanceDbSql.insertNumericAttributeData(attributeTableName, currentAbsoluteMaxId+n, data);
	    }   
	}
	
	public void populateBooleanAttributeTable(String attributeTableName, String[] values)
	{
		List<String> valuesList = Arrays.asList(values);  
		int numberOfRowsOfData = valuesList.size();
	    
		for(int n=1; n<=numberOfRowsOfData; n++)
	    {
			String data = valuesList.get(n-1);
			instanceDbSql.insertBooleanAttributeData(attributeTableName, currentAbsoluteMaxId+n, data);
	    }   
	}
	
	
	//-----------------------------------------------
	// Releases resources related to the new database 
	//-----------------------------------------------
	public void releaseResources()
	{
		createDbFactory.releaseResources();
	}

}
