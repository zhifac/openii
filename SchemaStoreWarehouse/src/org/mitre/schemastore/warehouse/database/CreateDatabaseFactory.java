package org.mitre.schemastore.warehouse.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;

/**
 * This class is a factory for creating new databases for instance tables
 * in the same repository as being used by SchemaStoreClient
 * @author STANDON
 *
 */
public abstract class CreateDatabaseFactory 
{
	/** Resources used for creating the new database */
	protected InstanceRepository newDatabaseInfo = null;
	protected ArrayList<InstanceRepository> allRepositories = null;
	private InputStream configStream = null;
	
	/** Resources used for accessing the new database */
	protected InstanceDatabaseSQL instanceDbSql = null;
	
	/** Flag that tells whether connection to new database is open or closed */
	protected boolean isConnected = false;
	
	/** Constructor - called when its concrete subclass is instantiated	
	 * @throws NoDataFoundException */
	protected CreateDatabaseFactory(InstanceRepository newDatabaseInfo) throws NoDataFoundException
	{
		System.out.println(getClass().getName() + " constructor called");
		
		// Store the new database information received from the client
		this.newDatabaseInfo = newDatabaseInfo;
		
		// Get the list of all available repositories where the new database could be created
		configStream = getClass().getResourceAsStream("/repository.xml");
		RepositoryXMLParser repositoryParser = new RepositoryXMLParser(configStream);
		allRepositories = repositoryParser.getAvailableRepositories();
	}
	
	/** Get the class for issuing SQL commands through JDBC */
	public InstanceDatabaseSQL getInstanceDatabaseSQL()
	{
		if(instanceDbSql == null)
			instanceDbSql = createInstanceDatabaseSQL();
		return instanceDbSql;
	}
	
	/** Creates the class for issuing SQL commands through JDBC */
	protected abstract InstanceDatabaseSQL createInstanceDatabaseSQL();
	
	/** Creates a new database and returns a connection to it */
	protected abstract Connection createConnectionToNewDatabase();
	
	/** Release resources */
	public abstract void releaseResources();
	
	/** This object has a specific "life cycle", such that SQL commands must be issued
	  * through the InstanceDatabaseSQL object before calling releaseResources().
	  * This method allows users to determine status of connection to new database
	  */
	public boolean isNewDbConnected()
	{	return isConnected;		}
	
	
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
