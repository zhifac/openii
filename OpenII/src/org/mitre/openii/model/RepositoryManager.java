package org.mitre.openii.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	/** Stores information on if the various repositories are valid */
	static private HashMap<Repository,Boolean> validRepositories = new HashMap<Repository,Boolean>();

	/** Stores the currently selected repository */
	static private Repository selectedRepository = null;
	
	/** Stores listeners to the Repository Manager */
	static private ListenerGroup<RepositoryListener> listeners = new ListenerGroup<RepositoryListener>();
	
	/** Stores the current schema store client */
	static private SchemaStoreClient client = null;

	/** Loads in any saved repositories */
	private static void loadRepositories()
	{
		Preferences preferences = OpenIIActivator.getDefault().getPluginPreferences();
	
		// Retrieves the stored repositories
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
				addRepository(new Repository(name,type,uri,database,user,password));
			} catch(Exception e) {}
			
		// Retrieves the selected repository
		Integer loc = preferences.getInt("SelectedRepository");
		if(repositories.size()>loc && isValidRepository(repositories.get(loc)))
			setSelectedRepository(repositories.get(loc));
	}

	/** Saves the current set of repositories */
	private static void saveRepositories()
	{
		Preferences preferences = OpenIIActivator.getDefault().getPluginPreferences();
		
		// Stores the repositories
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

		// Stores the selected repository
		Integer loc = repositories.indexOf(selectedRepository);
		if(loc>=0) preferences.setValue("SelectedRepository", loc);
		
		OpenIIActivator.getDefault().savePluginPreferences();
	}
	
	/** Initializes the schema store repositories */
	static
	{
		// Load in the stored repositories
		loadRepositories();
		
		// If no repositories were loaded, load in default repository
		if(repositories.size()==0)
			try {
				repositories.add(new Repository("Local Repository",Repository.DERBY,new URI("."),"schemastore","postgres","postgres"));
				saveRepositories();
			} catch(Exception e) {}

		// If no repository was selected, pick first repository
		if(getSelectedRepository()==null)
			setSelectedRepository(repositories.get(0));
	}	
	
	/** Returns the list of available repositories */
	public static ArrayList<Repository> getRepositories()
		{ return new ArrayList<Repository>(repositories); }
	
	/** Indicates if the specified repository is valid */
	public static boolean isValidRepository(Repository repository)
		{ return validRepositories.get(repository); }
	
	/** Returns the selected repository */
	public static Repository getSelectedRepository()
		{ return selectedRepository; }
	
	/** Sets the selected repository */
	public static void setSelectedRepository(Repository repository)
	{
		selectedRepository = repository;
		saveRepositories();
		
		// Set a default repository
		try { client = new SchemaStoreClient(getSelectedRepository()); }
		catch(Exception e) { System.out.println("(E) RepositoryManager - " + e.getMessage()); }

		// Rest the OpenII manager
		OpenIIManager.reset();
	}
	
	/** Adds a repository to the list of repositories */
	public static void addRepository(Repository repository)
	{
		// Store the repository
		repositories.add(repository);
		saveRepositories();
		
		// Determine the validity of the repository
		boolean valid = false;
		try {
			new SchemaStoreClient(repository); valid = true;
		} catch(Exception e) {}
		validRepositories.put(repository, valid);
	
		// Inform all listeners of the added repository	
		for(RepositoryListener listener : listeners.get())
			listener.repositoryAdded(repository);
	}
	
	/** Deletes the specified repository */
	public static void deleteRepository(Repository repository)
	{
		repositories.remove(repository);
		saveRepositories();
		validRepositories.remove(repository);
		for(RepositoryListener listener : listeners.get())
			listener.repositoryDeleted(repository);
	}
	
	/** Returns the schema store connection */
	public static SchemaStoreClient getClient()
		{ return client; }

	/** Stores listeners to the Repository Manager */
	static public void addListener(RepositoryListener listener) { listeners.add(listener); }
	static public void removeListener(RepositoryListener listener) { listeners.remove(listener); }
}