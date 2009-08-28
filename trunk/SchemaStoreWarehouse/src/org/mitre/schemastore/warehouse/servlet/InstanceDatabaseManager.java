package org.mitre.schemastore.warehouse.servlet;

import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;

import org.mitre.schemastore.warehouse.database.managers.DatabaseManager;
import org.mitre.schemastore.warehouse.database.managers.DerbyDatabaseManager;
import org.mitre.schemastore.warehouse.database.managers.PostgresDatabaseManager;

public class InstanceDatabaseManager 
{
	/** Instance variables */
	private DatabaseManager dm = null;
	
	/** No-arg constructor */
	public InstanceDatabaseManager()	{}
	
	public InstanceDatabaseManager(InstanceRepository existingDatabaseInfo) throws NoDataFoundException
	{
		// Get the type of repository - helps decide what type of classes to instantiate
		int instanceRepositoryType = existingDatabaseInfo.getType();
		switch(instanceRepositoryType)
		{
			case 1:
				dm = new PostgresDatabaseManager(existingDatabaseInfo);
				break;
			case 2:
				dm = new DerbyDatabaseManager(existingDatabaseInfo);
				break;
			default:
				System.out.println("Database type is not currently supported by this application");
				throw new NoDataFoundException("Database type is not currently supported by this application");
		}
	}
	
	public Boolean deleteInstanceDatabase(InstanceRepository existingDatabaseInfo) throws NoDataFoundException
	{
		boolean success = dm.deleteInstanceDatabase();
		
		return new Boolean(success);
	}

}
