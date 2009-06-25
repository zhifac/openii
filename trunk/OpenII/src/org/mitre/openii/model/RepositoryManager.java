package org.mitre.openii.model;

import java.net.URI;
import java.util.ArrayList;

import org.eclipse.core.runtime.Preferences;
import org.mitre.openii.application.OpenIIActivator;
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

	/** Loads in any saved repositories */
	private static void loadRepositories()
	{
		Preferences preferences = OpenIIActivator.getDefault().getPluginPreferences();
		Integer count = preferences.getInt("RepositoryCount");
		for(int i=0; i<count; i++)
			try {
				String name = preferences.getString("Repository"+i+"Name");
				String typeString = preferences.getString("Repository"+i+"Type");
				Integer type = typeString.equals("service")?Repository.SERVICE:typeString.equals("postgres")?Repository.POSTGRES:Repository.DERBY;
				URI uri = null; try { uri = new URI(preferences.getString("Repository"+i+"URI")); } catch(Exception e) {}
				String database = preferences.getString("Repository"+i+"Database");
				String user = preferences.getString("Repository"+i+"User");
				String password = preferences.getString("Repository"+i+"Password");
				repositories.add(new Repository(name,type,uri,database,user,password));
			} catch(Exception e) {}
	}

	/** Saves the current set of repositories */
	private static void saveRepositories()
	{
		Preferences preferences = OpenIIActivator.getDefault().getPluginPreferences();
		preferences.setValue("RepositoryCount",repositories.size());
		for(int i=0; i<repositories.size(); i++)
		{
			Repository repository = repositories.get(i);
			preferences.setValue("Repository"+i+"Name", repository.getName());
			preferences.setValue("Repository"+i+"Type", repository.getType().equals(Repository.SERVICE)?"service":repository.getType().equals(Repository.POSTGRES)?"postgres":"derby");
			preferences.setValue("Repository"+i+"URI", repository.getURI().toString());
			preferences.setValue("Repository"+i+"Database", repository.getDatabaseName());
			preferences.setValue("Repository"+i+"User", repository.getDatabaseUser());
			preferences.setValue("Repository"+i+"Password", repository.getDatabasePassword());
		}
		OpenIIActivator.getDefault().savePluginPreferences();
	}
	
	/** Initializes the schema store repositories */
	static
	{
		// Load in the stored repositories
		loadRepositories();
		if(repositories.size()==0)
			try {
				repositories.add(new Repository("Local Repository",Repository.DERBY,new URI("."),"schemastore","postgres","postgres"));
				saveRepositories();
			} catch(Exception e) {}

		// Set a default repository
		try { client = new SchemaStoreClient(repositories.get(0)); }
		catch(Exception e) { System.out.println("(E) RepositoryManager - " + e.getMessage()); }
	}	
	
	/** Returns the list of available repositories */
	public static ArrayList<Repository> getRepositories()
		{ return new ArrayList<Repository>(repositories); }
	
	/** Adds a repository to the list of repositories */
	public static void addRepository(Repository repository)
	{
		repositories.add(repository);
		saveRepositories();
		for(RepositoryListener listener : listeners.get())
			listener.repositoryAdded(repository);
	}
	
	/** Deletes the specified repository */
	public static void deleteRepository(Repository repository)
	{
		repositories.remove(repository);
		saveRepositories();
		for(RepositoryListener listener : listeners.get())
			listener.repositoryDeleted(repository);
	}
	
	/** Returns the schema store connection */
	public static SchemaStoreClient getClient()
		{ return client; }

	/** Stores listeners to the Repository Manager */
	static private ListenerGroup<RepositoryListener> listeners = new ListenerGroup<RepositoryListener>();
	static public void addListener(RepositoryListener listener) { listeners.add(listener); }
	static public void removeListener(RepositoryListener listener) { listeners.remove(listener); }
}