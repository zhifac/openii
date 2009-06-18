package org.mitre.openii.model;

import java.net.URI;
import java.util.ArrayList;

import org.mitre.schemastore.client.Repository;
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
		// Make a connection to schema store
		try {
			repositories.add(new Repository("Local Repository",Repository.DERBY,new URI("."),"schemastore","",""));
			repositories.add(new Repository("SchemaStore",Repository.SERVICE,new URI("http://ygg:8080/SchemaStoreForDemo/services/SchemaStore"),"","",""));
			client = new SchemaStoreClient(repositories.get(1));
		} catch(Exception e) { System.out.println("(E) RepositoryManager - " + e.getMessage()); }
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