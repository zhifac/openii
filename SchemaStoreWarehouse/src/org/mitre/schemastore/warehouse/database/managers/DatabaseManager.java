package org.mitre.schemastore.warehouse.database.managers;

import java.io.InputStream;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;

import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;
import org.mitre.schemastore.warehouse.database.RepositoryXMLParser;

public abstract class DatabaseManager 
{
	/** Instance variables */
	protected InstanceRepository existingDatabaseInfo = null;
	protected ArrayList<InstanceRepository> allRepositories = null;
	private InputStream configStream = null;
	
	/** Constructor - called when its concrete subclass is instantiated	
	 * @throws NoDataFoundException */
	protected DatabaseManager(InstanceRepository existingDatabaseInfo) throws NoDataFoundException
	{
		System.out.println(getClass().getName() + " constructor called");
		
		// Store the new database information received from the client
		this.existingDatabaseInfo = existingDatabaseInfo;
		
		// Get the list of all available repositories where the new database could be created
		configStream = getClass().getResourceAsStream("/repository.xml");
		RepositoryXMLParser repositoryParser = new RepositoryXMLParser(configStream);
		allRepositories = repositoryParser.getAvailableRepositories();
	} // end of constructor
	
	
	/** Method for deleting the database that this Data Manager is created for 
	 * @throws NoDataFoundException */
	public abstract boolean deleteInstanceDatabase() throws NoDataFoundException;
	
	
	/** Print details of a SQLException chain */
	protected void printSQLException(SQLException e)
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
