package org.mitre.schemastore.warehouse.database.managers;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;

public class PostgresDatabaseManager extends DatabaseManager 
{
	/** Variables storing details of PostgreSQL database */
	protected String DRIVER_CLASS_NAME = "org.postgresql.Driver";
	private String PROTOCOL = "jdbc:postgresql:";
	private String HOSTNAME = null;
	private String PORT = "5432";
	private String DEFAULT_DB_NAME = null;
	private String EXISTING_DB_NAME = null;
	private String DB_CONN_STRING = null;
	private String USER_NAME = null;
	private String PASSWORD = null;
	
	private Connection defaultDbConnection = null;
	
	
	/** Constructor	
	 * @throws NoDataFoundException */
	public PostgresDatabaseManager(InstanceRepository existingDatabaseInfo) throws NoDataFoundException 
	{
		super(existingDatabaseInfo);
		System.out.println(getClass().getName() + " constructor called");

		// Create the database connection string
		HOSTNAME = existingDatabaseInfo.getHost();
		EXISTING_DB_NAME = existingDatabaseInfo.getDatabaseName();
		
		for(InstanceRepository r : allRepositories)
		{
			// Set the user name and password 
			if(r.getType().equals(InstanceRepository.POSTGRES) && r.getHost().equals(HOSTNAME))
			{
				// Get default database name
				DEFAULT_DB_NAME = r.getDatabaseName();
				
				// Get the username and password
				USER_NAME = r.getUsername();
				PASSWORD = r.getPassword();
			}
		}
		if(USER_NAME == null && PASSWORD == null)
			throw new NoDataFoundException("Could not determine a valid repository.");
	}//end of constructor
	
	/** Method for deleting the Instance Database existing in PostgreSQL that this Data Manager is created for
	 * @throws NoDataFoundException */
	public boolean deleteInstanceDatabase() throws NoDataFoundException
	{
		defaultDbConnection = null;
		
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
		 * A default schema "public" is also created
		 */
		boolean success;
		try
		{
			//Connect to default database "postgres" as default user "postgres" who has the right to drop databases
			DB_CONN_STRING = PROTOCOL + "//" + HOSTNAME + ":" + PORT + "/" + DEFAULT_DB_NAME;
			defaultDbConnection = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);
			System.out.println("***Connection has been established to default database as the super user***");
			
			// Drop the new database whose owner is "postgres"
			System.out.println("Dropping database " + EXISTING_DB_NAME);
			String query = "DROP DATABASE " + EXISTING_DB_NAME;
			Statement stmt = defaultDbConnection.createStatement();
			int result = stmt.executeUpdate(query);
			if(result == 0)
			{
				System.out.println("Dropped Database " + EXISTING_DB_NAME);
				success = true;
			}
			else
				success = false;

			stmt.close();
			defaultDbConnection.close();
			
			return success;
		}
		catch(SQLException e)
		{
			printSQLException(e);
			throw new NoDataFoundException("Database could not be deleted.");
		}

	}

}
