package org.mitre.schemastore.warehouse.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
	
	
	/** Constructor	
	 * @throws NoDataFoundException */
	public CreateDerbyDatabaseFactory(InstanceRepository newDatabaseInfo) throws NoDataFoundException 
	{
		super(newDatabaseInfo);
		System.out.println(getClass().getName() + " constructor called");
		
		// Create the database connection string
		PATH_TO_EXISTING_DB_FOLDER = newDatabaseInfo.getHost();
		NEW_DB_NAME = newDatabaseInfo.getDatabaseName();
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
			throw new NoDataFoundException("***Could not determine where to create the new database- matching existing repository not found***");
	}//end of constructor

	/** Creates a new database and returns a connection to it */
	protected Connection createConnectionToNewDatabase() 
	{
		Connection newConnection = null;
		
		/* Load (and therefore register) the Driver with java.sql.DriverManager */
		try
		{
			Class.forName(DRIVER_CLASS_NAME);
			System.out.println("***Driver has been loaded***");
		}
		catch (ClassNotFoundException e)
		{
			// Handle an error loading the driver
			System.out.println("Driver could not be loaded");
		}
		
		/* Create the database, if it does not already exist and
		 * Get connection to the new database
		 * A new schema with name = username is also created
		 */
		try
		{
			newConnection = DriverManager.getConnection(DB_CONN_STRING + ";create=true", USER_NAME, PASSWORD);
			System.out.println("Created and connected to database " + NEW_DB_NAME);
			
			// set the flag to show that connection to new database is open
			isConnected = true;
		}
		catch(SQLException e)
		{	printSQLException(e);	}
		
		return newConnection;
	}

	/** Creates the class for issuing SQL commands through JDBC */
	protected InstanceDatabaseSQL createInstanceDatabaseSQL() 
	{
		InstanceDatabaseSQL instanceDb = new InstanceDatabaseDerbySQL(createConnectionToNewDatabase());
		return instanceDb;
	}

	/** Release resources */
	public void releaseResources()
	{
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
				System.out.println("Single database shutdown, Derby engine still running");
				
				// set the flag to show that connection to new database is closed
				isConnected = false;
			}
			else
			{
				// If the error code or SQLState is different, we have an unexpected exception
				// i.e. shutdown failed
				System.out.println("Database did not shutdown normally");
				printSQLException(se);
			}
		}
	}

}
