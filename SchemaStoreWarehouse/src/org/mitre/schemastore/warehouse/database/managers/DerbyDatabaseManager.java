package org.mitre.schemastore.warehouse.database.managers;

import java.io.File;

import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;

public class DerbyDatabaseManager extends DatabaseManager 
{
	/** Variables storing details of Apache Derby database */
	protected String DRIVER_CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
	private String PROTOCOL = "jdbc:derby:";
	private String PATH_TO_EXISTING_DB_FOLDER = null;
	private String FULL_PATH_TO_EXISTING_DB = null;
	private String EXISTING_DB_NAME = null;
	private String DB_CONN_STRING = null;
	private String USER_NAME = null;
	private String PASSWORD = null;
	
	/** Constructor	
	 * @throws NoDataFoundException */
	public DerbyDatabaseManager(InstanceRepository existingDatabaseInfo) throws NoDataFoundException 
	{
		super(existingDatabaseInfo);
		System.out.println(getClass().getName() + " constructor called");
		
		// Obtain path to existing database folder - this is where the new database will be created 
		String FULL_PATH_TO_EXISTING_DB_FOLDER = existingDatabaseInfo.getHost();
		String[] parts = FULL_PATH_TO_EXISTING_DB_FOLDER.split(":", 2);
		PATH_TO_EXISTING_DB_FOLDER = parts[1];

		// Obtain the new database name as specified by the user
		EXISTING_DB_NAME = existingDatabaseInfo.getDatabaseName();
		
		// Obtain full path to existing database folder - used to delete the folder
		// if database needs to be deleted
		FULL_PATH_TO_EXISTING_DB = FULL_PATH_TO_EXISTING_DB_FOLDER + EXISTING_DB_NAME;
		
		// Obtain connection string for jdbc access to the new database
		DB_CONN_STRING = PROTOCOL + PATH_TO_EXISTING_DB_FOLDER + EXISTING_DB_NAME;
		
		for(InstanceRepository r : allRepositories)
		{
			// Set the user name and password 
			if(r.getType().equals(InstanceRepository.DERBY))
			{
				// Get the user name and password
				existingDatabaseInfo.setUsername(r.getUsername());
				USER_NAME = r.getUsername();
				existingDatabaseInfo.setPassword(r.getPassword());
				PASSWORD = r.getPassword();
			}
		}
		if(USER_NAME == null && PASSWORD == null)
			throw new NoDataFoundException("Could not determine a valid repository.");
	}//end of constructor
	
	
	/** Method for deleting the Instance Database existing in Derby that this Data Manager is created for */
	public boolean deleteInstanceDatabase()
	{
		File newDatabasePath = new File(FULL_PATH_TO_EXISTING_DB);
		boolean success = deleteFile(newDatabasePath);
		if(success)
		{
			System.out.println("New Database deleted");
		}
		else
			System.out.println("New Database could not be deleted");
		
		return success;
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
