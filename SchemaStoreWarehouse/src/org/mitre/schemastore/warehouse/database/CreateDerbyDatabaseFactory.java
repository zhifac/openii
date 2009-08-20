package org.mitre.schemastore.warehouse.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;

import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;

public class CreateDerbyDatabaseFactory extends CreateDatabaseFactory 
{
	/** Variables storing details of Apache Derby database */
	protected String DRIVER_CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
	private String PROTOCOL = "jdbc:derby:";
	private String PATH_TO_EXISTING_DB_FOLDER = null;
	private String NEW_DB_NAME = null;
	private String DB_CONN_STRING = null;
	private String USER_NAME = null;
	private String PASSWORD = null;
	
	private String FULL_PATH_TO_NEW_DB = null;
	private Connection newDbConnection = null;
	private boolean isNewDatabaseCreated = false;
	
	/** Constructor	
	 * @throws NoDataFoundException */
	public CreateDerbyDatabaseFactory(InstanceRepository newDatabaseInfo) throws NoDataFoundException 
	{
		super(newDatabaseInfo);
		System.out.println(getClass().getName() + " constructor called");
		
		// Obtain path to existing database folder - this is where the new database will be created 
		String FULL_PATH_TO_EXISTING_DB_FOLDER = newDatabaseInfo.getHost();
		String[] parts = FULL_PATH_TO_EXISTING_DB_FOLDER.split(":", 2);
		PATH_TO_EXISTING_DB_FOLDER = parts[1];

		// Obtain the new database name as specified by the user
		NEW_DB_NAME = newDatabaseInfo.getDatabaseName();
		
		// Obtain full path to new database folder - used to delete the folder
		// if database needs to be deleted
		FULL_PATH_TO_NEW_DB = FULL_PATH_TO_EXISTING_DB_FOLDER + NEW_DB_NAME;
		
		// Obtain connection string for jdbc access to the new database
		DB_CONN_STRING = PROTOCOL + PATH_TO_EXISTING_DB_FOLDER + NEW_DB_NAME;
		
		for(InstanceRepository r : allRepositories)
		{
			// Set the user name and password 
			if(r.getType().equals(InstanceRepository.DERBY))
			{
				// Get the user name and password
				newDatabaseInfo.setUsername(r.getUsername());
				USER_NAME = r.getUsername();
				newDatabaseInfo.setPassword(r.getPassword());
				PASSWORD = r.getPassword();
			}
		}
		if(USER_NAME == null && PASSWORD == null)
			throw new NoDataFoundException("Could not determine where to create the new database- matching existing repository not found");
	}//end of constructor

	/** Creates a new database and returns a connection to it 
	 * @throws NoDataFoundException */
	protected Connection createConnectionToNewDatabase() throws NoDataFoundException 
	{
		/* Load (and therefore register) the Driver with java.sql.DriverManager */
		try
		{
			Class.forName(DRIVER_CLASS_NAME);
			System.out.println("***Driver has been loaded***");
		}
		catch (ClassNotFoundException e)
		{
			// Handle an error loading the driver
			e.printStackTrace(System.err);
			throw new NoDataFoundException("Driver for the database could not be loaded");
		}
		
		/* Create the database, if it does not already exist and
		 * Get connection to the new database
		 * A new schema with name = username is also created
		 */
		try
		{
			newDbConnection = DriverManager.getConnection(DB_CONN_STRING + ";create=true", USER_NAME, PASSWORD);
			System.out.println("Created and connected to database " + NEW_DB_NAME);
			
			SQLWarning w = newDbConnection.getWarnings();
			if(w!= null)
			{
				// Database exists
				if((w.getErrorCode() == 10000) && ("01J01".equals(w.getSQLState())))
				{
					System.out.println("Database not created, connection made to existing database instead");
					isNewDatabaseCreated = false;
				}
			}
			else
			{
				// Database does not exist
				System.out.println("Database created, connection made to new database");
				isNewDatabaseCreated = true;
			}
			
			// Control transactions manually
			newDbConnection.setAutoCommit(false);
			
			// Set the flag to show that connection to new database is open
			isConnected = true;
		}
		catch(SQLException e)
		{	
			printSQLException(e);
			throw new NoDataFoundException("Database creation or existing database access error occured");
		}
		
		return newDbConnection;
	}

	/** Creates the class for issuing SQL commands through JDBC 
	 * @throws NoDataFoundException */
	protected InstanceDatabaseSQL createInstanceDatabaseSQL() throws NoDataFoundException 
	{
		InstanceDatabaseSQL instanceDb = new InstanceDatabaseDerbySQL(createConnectionToNewDatabase());
		return instanceDb;
	}

	/** Release resources 
	 * @throws SQLException */
	public void releaseResources() throws SQLException
	{
		// Commit all changes
		newDbConnection.commit();
		
		// Shut-down Derby database so that it can be accessed by another instance of database
		try
		{
			DriverManager.getConnection(DB_CONN_STRING + ";shutdown=true", USER_NAME, PASSWORD);
		}
		catch(SQLException se)
		{
			if((se.getErrorCode() == 45000) && ("08006".equals(se.getSQLState())))
			{
				// We got the expected exception
				System.out.println("Single database shutdown successfully, Derby engine still running");
				
				// set the flag to show that connection to new database is closed
				isConnected = false;
			}
			else
			{
				// If the error code or SQLState is different, we have an unexpected exception
				// i.e. shutdown failed
				printSQLException(se);
				throw se;
			}
		}
	}
	
	
	/** Undo all changes made in the current transaction and 
	 * release any database locks currently held by this connection 
	 * @throws SQLException
	 */
	public void rollback() throws SQLException 
	{
		// Undo all changes
		newDbConnection.rollback();
		
		// Shut-down Derby database so that it can be accessed by another instance of database
		// This is necessary to allow deletion of the database folder (in case where a new database was created).
		try
		{
			DriverManager.getConnection(DB_CONN_STRING + ";shutdown=true", USER_NAME, PASSWORD);
		}
		catch(SQLException se)
		{
			if((se.getErrorCode() == 45000) && ("08006".equals(se.getSQLState())))
			{
				// We got the expected exception
				System.out.println("Single database shutdown successfully, Derby engine still running");
				
				// set the flag to show that connection to new database is closed
				isConnected = false;
			}
			else
			{
				// If the error code or SQLState is different, we have an unexpected exception
				// i.e. shutdown failed
				printSQLException(se);
				throw se;
			}
		}
		
		// If new database is created, then it is deleted so that 
		// a database with same name can be created in future 
		if(isNewDatabaseCreated)
		{
			File newDatabasePath = new File(FULL_PATH_TO_NEW_DB);
			boolean success = deleteFile(newDatabasePath);
			if(success)
			{
				System.out.println("New Database deleted");
				isNewDatabaseCreated = false;
			}
			else
				System.out.println("New Database could not be deleted");
		}
	}
	
	
	/** Method for deleting any File recursively 
	 *  @return success or failure
	 */
	private boolean deleteFile(File file) 
    { 
        if (!file.exists()) 
        	return true;
        
        if (!file.isDirectory()) 
        { 
            return file.delete(); 
        } 

        return deleteDirectory(file); 
    } 


    /** Method for deleting a directory	 
     *  @return success or failure
	 */    
	private boolean deleteDirectory(File directory) 
    { 
        File[] contents = directory.listFiles(); 
        for (int i = 0; i < contents.length; i++) 
        { 
            File next = contents[i]; 
            boolean result = deleteFile(next); 
            if (!result) 
            	return false;
         } 
        return directory.delete(); 
    } 

}
