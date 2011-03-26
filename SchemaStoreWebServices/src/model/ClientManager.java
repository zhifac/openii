package model;

import java.io.File;
import java.net.URI;
import java.rmi.RemoteException;

import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;

/**
 * Handles access to the database
 * @author CWOLF
 */
public class ClientManager
{	
	/** Connection to the client */
	static private SchemaStoreClient client = null;
	
	/** Retrieve the client  */
	static public SchemaStoreClient getClient() throws RemoteException
	{
		if(client==null)
		{
			try {
				Repository repository = new Repository(Repository.POSTGRES,new URI("localhost"),"supplies","postgres","postgres");
//				Repository repository = new Repository(Repository.DERBY,new File("C:\\chris\\projects\\Flexidata\\Repositories").toURI(),"supplies","postgres","postgres");
				client = new SchemaStoreClient(repository);
			} catch(Exception e) { throw new RemoteException(e.getMessage()); }
		}
		return client;
	}
}
