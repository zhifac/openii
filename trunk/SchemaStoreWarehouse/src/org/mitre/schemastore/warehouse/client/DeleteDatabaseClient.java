package org.mitre.schemastore.warehouse.client;

import java.rmi.RemoteException;

import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;
import org.mitre.schemastore.warehouse.servlet.InstanceDatabaseManager;

public class DeleteDatabaseClient 
{
	private InstanceRepository existingInstanceRepository = null;
	private String dataSourceName = null;
	private InstanceDatabaseManager instanceDatabaseManager = null;
	
	public DeleteDatabaseClient(String dataSourceName, Repository currentRepository) throws RemoteException, NoDataFoundException
	{
		System.out.println("Delete Database Client initiated");
		
		// Store the name of the database to be deleted
		this.dataSourceName = dataSourceName;
		
		// Relevant information needed for creating the new instance database is extracted
		existingInstanceRepository = getInstanceRepository(currentRepository);
		
		if(currentRepository.getType().equals(Repository.SERVICE))
		{
			// Connect to web service - client accesses the server remotely
			//instanceDatabase = new InstanceDatabaseProxy("http://localhost:8080/SchemaStoreWarehouse/services/InstanceDatabase");
		}
		else
		{
			// Client and server are in the same place
			// Instantiate the InstanceDatabaseManager class
			instanceDatabaseManager = new InstanceDatabaseManager(existingInstanceRepository);
			/*
			Class<?> types[] = new Class[] {InstanceRepository.class};
			Object args[] = new Object[] {instanceRepository};
			Constructor<?> constructor;
			try 
			{
				constructor = InstanceDatabase.class.getConstructor(types);
				instanceDatabase = constructor.newInstance(args);
			} 
			catch (Exception e) 
			{
				e.printStackTrace(System.out);
				throw new RemoteException("***Failed to connect to InstanceDatabase***" + e.getMessage());
			} 
			*/
		}
	}
	
	
	public boolean deleteInstanceDatabase() throws NoDataFoundException
	{
		Boolean success = instanceDatabaseManager.deleteInstanceDatabase(existingInstanceRepository);
		return success.booleanValue();
	}
	
	/** Create an instance of InstanceRepository from the given Repository object 
	 * @throws RemoteException */
	private InstanceRepository getInstanceRepository(Repository structuralRepository) throws RemoteException
	{
		InstanceRepository instanceRepository = new InstanceRepository();
		
		// Get the type of repository
		int structuralRepositoryType = structuralRepository.getType();
		
		// Set the type and host 
		switch(structuralRepositoryType)
		{
			case 0:
				instanceRepository.setType(instanceRepository.SERVICE);
				break;
			case 1:
				instanceRepository.setType(instanceRepository.POSTGRES);
				instanceRepository.setHost(structuralRepository.getURI().toString());
				break;
			case 2:
				instanceRepository.setType(instanceRepository.DERBY);
				String host = null; 
				if(structuralRepository.getURI().getScheme().equals("file"))
					host = structuralRepository.getURI().getPath();
				instanceRepository.setHost(host);
				break;
			default:
				instanceRepository.setType(instanceRepository.DERBY);
				String host2 = null;
				if(structuralRepository.getURI().getScheme().equals("file"))
					host2 = structuralRepository.getURI().getPath();
				instanceRepository.setHost(host2);
			break;
		}
		
		// Get the name of the new instance database that will be created
		instanceRepository.setDatabaseName(dataSourceName);
		
		return instanceRepository;
	}//end of method getInstanceRepository

}
