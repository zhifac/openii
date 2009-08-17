package org.mitre.schemastore.warehouse.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
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
	
	/** Get the class for issuing SQL commands through JDBC 
	 * @throws NoDataFoundException */
	public InstanceDatabaseSQL getInstanceDatabaseSQL() throws NoDataFoundException
	{
		if(instanceDbSql == null)
			instanceDbSql = createInstanceDatabaseSQL();
		return instanceDbSql;
	}
	
	/** Creates the class for issuing SQL commands through JDBC 
	 * @throws NoDataFoundException */
	protected abstract InstanceDatabaseSQL createInstanceDatabaseSQL() throws NoDataFoundException;
	
	/** Creates a new database and returns a connection to it 
	 * @throws NoDataFoundException */
	protected abstract Connection createConnectionToNewDatabase() throws NoDataFoundException;
	
	
	/** Release resources 
	 * @throws SQLException */
	public abstract void releaseResources() throws SQLException;
	
	
	/** Undo all changes made in the current transaction and 
	 * release any database locks currently held by this connection 
	 * @throws SQLException
	 */
	public abstract void rollback() throws SQLException;
	
	
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
	
	/** Print details of a SQLWarning chain */
	protected void printSQLWarning(SQLWarning w)
	{
		// Unwraps the entire exception chain to unveil the real cause of the Exception
		while(w != null)
		{
			System.err.println("\n----- SQLException -----");
			System.err.println("  SQL State: " + w.getSQLState());
			System.err.println("  Error Code: " + w.getErrorCode());
			System.err.println("  Message: " + w.getMessage());
			w.printStackTrace(System.err);
			
			w = w.getNextWarning();
		}
	}
	
}
