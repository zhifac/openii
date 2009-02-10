package org.mitre.openii.model;

import java.io.File;

import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.client.SchemaStoreClient;

/**
 * Generates the schema store connection for use by all components
 * @author CWOLF
 */
public class SchemaStoreConnection
{
	/** Stores the client used for accessing the database */
	static private SchemaStoreClient client = null;
	static
	{
		try {
			File file = new File(OpenIIActivator.getBundleFile(),"SchemaStore.jar");
			if(!file.exists()) file = new File(OpenIIActivator.getBundleFile(),"lib/SchemaStore.jar");
			client = new SchemaStoreClient(file.getAbsolutePath());
		}
		catch(Exception e) { System.out.println("(E) SchemaStoreConnection - " + e.getMessage()); }
	}
	
	/** Returns the schema store connection */
	public static SchemaStoreClient getConnection()
		{ return client; }
}