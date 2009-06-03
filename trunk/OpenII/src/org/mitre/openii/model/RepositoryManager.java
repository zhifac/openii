package org.mitre.openii.model;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.client.SchemaStoreClient;

/**
 * Manages the schema store repositories for use within OpenII
 * @author CWOLF
 */
public class RepositoryManager
{	
	/** Stores the list of available schema store repositories */
	static private ArrayList<Repository> repositories = new ArrayList<Repository>();
	
	/** Stores the current schema store client */
	static private SchemaStoreClient client = null;
	
	/** Initializes the schema store repositories */
	static
	{
		// Create a connection to the local schema store jar
		try {
			File file = new File(OpenIIActivator.getBundleFile(),"SchemaStore.jar");
			if(!file.exists()) file = new File(OpenIIActivator.getBundleFile(),"../../SchemaStore.jar");
			repositories.add(new Repository("Local Repository",Repository.DERBY,file.toURI(),"schemastore","",""));
		} catch(Exception e) {}
			
		// Creates a connection to the demo server
		try {
			repositories.add(new Repository("SchemaStore",Repository.SERVICE,new URI("http://ygg:8080/SchemaStoreForDemo/services/SchemaStore"),"","",""));
		} catch(Exception e) {}
			
		// Make a connection to schema store
		try { client = new SchemaStoreClient(repositories.get(0).getURI().toString()); }
		catch(Exception e) { System.out.println("(E) RepositoryManager - " + e.getMessage()); }
	}	
	
	/** Returns the list of available repositories */
	public static ArrayList<Repository> getRepositories()
		{ return new ArrayList<Repository>(repositories); }
	
	/** Deletes the specified repository */
	public static void deleteRepository(Repository repository)
	{
		System.out.println("Deletes the repository '" + repository.getName()+ "'");
	}
	
	/** Returns the schema store connection */
	public static SchemaStoreClient getClient()
		{ return client; }
}