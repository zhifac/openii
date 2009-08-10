package org.mitre.schemastore.warehouse.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;

public class CreatePostgresDatabaseFactory extends CreateDatabaseFactory 
{
	/** Variables storing details of PostgreSQL database */
	protected String DRIVER_CLASS_NAME = "org.postgresql.Driver";
	private String PROTOCOL = "jdbc:postgresql:";
	private String HOSTNAME = null;
	private String PORT = "5432";
	private String DEFAULT_DB_NAME = "postgres";
	private String NEW_DB_NAME = null;
	private String DB_CONN_STRING = null;
	private String USER_NAME = null;
	private String PASSWORD = null;
	private Connection newDbConnection = null;
	
	/** Constructor	
	 * @throws NoDataFoundException */
	public CreatePostgresDatabaseFactory(InstanceRepository newDatabaseInfo) throws NoDataFoundException 
	{
		super(newDatabaseInfo);
		System.out.println(getClass().getName() + " constructor called");
		
		// Create the database connection string
		HOSTNAME = newDatabaseInfo.getHost();
		NEW_DB_NAME = newDatabaseInfo.getDatabaseName();
		
		for(InstanceRepository r : allRepositories)
		{
			// Set the user name and password 
			if(r.getType().equals(InstanceRepository.POSTGRES) && r.getHost().equals(HOSTNAME))
			{
				// Get the username and password
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
		Connection defaultDbConnection = null;
				
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
		 * A default schema "public" is also created
		 */
		boolean isDatabaseCreated = false;
		try
		{
			//Connect to default database "postgres" as default user "postgres" who has the right to create new databases
			DB_CONN_STRING = PROTOCOL + "//" + HOSTNAME + ":" + PORT + "/" + DEFAULT_DB_NAME;
			defaultDbConnection = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);
			System.out.println("***Connection has been established to default database as the super user***");
			
			// Create new database whose owner would be "postgres"
			System.out.println("Creating database " + NEW_DB_NAME);
			String query = "CREATE DATABASE " + NEW_DB_NAME;
			Statement stmt = defaultDbConnection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result == 0)
			{
				System.out.println("Created Database " + NEW_DB_NAME);
				isDatabaseCreated = true;
			}
			stmt.close();
			defaultDbConnection.close();
		}
		catch(SQLException e)
		{
			if((e.getErrorCode() == 0) && ("42P04".equals(e.getSQLState())))
			{
				// Database already exists
				System.out.println("Database " + NEW_DB_NAME + " could not be created because it already exists");
				printSQLException(e);
			}
			else
			{
				// If the error code or SQLState is different, we have an unexpected exception
				System.out.println("Database " + NEW_DB_NAME + " could not be created for some other reason");
				printSQLException(e);
			}
		}
		
		try
		{	
			// Open a connection to the new database for the same user i.e. superuser "postgres"
			DB_CONN_STRING = PROTOCOL + "//" + HOSTNAME + ":" + PORT + "/" + NEW_DB_NAME;
			newDbConnection = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);
			System.out.println("***Connection has been established to new database " + NEW_DB_NAME + " as the super user***");

			// set the flag to show that connection to new database is open
			isConnected = true;
			
			if(isDatabaseCreated)
				System.out.println("Created and connected to new database " + NEW_DB_NAME);
			else
				System.out.println("Connected to existing database " + NEW_DB_NAME);
		}
		catch(SQLException e)
		{	printSQLException(e);	}
		
		return newDbConnection;
	}

	/** Creates the class for issuing SQL commands through JDBC */
	protected InstanceDatabaseSQL createInstanceDatabaseSQL() 
	{
		InstanceDatabaseSQL instanceDb = new InstanceDatabasePostgresSQL(createConnectionToNewDatabase());
		return instanceDb;
	}
	
	/** Release resources */
	public void releaseResources()
	{	try
		{
			// If the method is called more than once, a no-operation occurs 
			if(!isConnected)
				return;
			else
			{
				if(newDbConnection != null)
				{
					newDbConnection.close();
					
					// set the flag to show that connection to new database is closed
					isConnected = false;
				}
			}
		} 
		catch (SQLException e) 
		{
			printSQLException(e);
		}
	}
	
	

}
