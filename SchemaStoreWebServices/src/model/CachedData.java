package model;

import java.rmi.RemoteException;
import java.util.HashMap;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Vocabulary;

/** Stores the cached data */
public class CachedData
{
	/** Stores the SchemaStore client */
	static private SchemaStoreClient client = null;
	
	/** Stores the list of schema names */
	static private HashMap<Integer,String> schemaNames = new HashMap<Integer,String>();

	/** Stores the list of schema elements */
	static private HashMap<Integer,SchemaElement> schemaElements = new HashMap<Integer,SchemaElement>();
	
	/** Stores the list of used project vocabularies */
	static private HashMap<String,Vocabulary> vocabularies = new HashMap<String,Vocabulary>();
	
	/** Refreshes the specified project vocabulary */
	static public Vocabulary refreshVocabulary(String projectName) throws RemoteException
	{	
		// Connect to the repository
		if(client==null) client = ClientManager.getClient();	
		
		// Get the specified project
		Project project = null;
		for(Project currProject : client.getProjects())
			if(currProject.getName().equals(projectName))
				{ project = currProject; break; }
		if(project==null) { vocabularies.remove(projectName); return null; }

		// Clear out all old schema names
		for(Integer schemaID : project.getSchemaIDs())
			schemaNames.remove(schemaID);
		
		// Retrieve the vocabulary
		Vocabulary vocabulary = client.getVocabulary(project.getId());
		vocabularies.put(projectName, vocabulary);
		return vocabulary;
	}
	
	/** Return the specified schema */
	static public String getSchemaName(Integer schemaID)
	{
		if(schemaNames.get(schemaID)==null)
			try { schemaNames.put(schemaID, client.getSchema(schemaID).getName()); } catch(Exception e) {}
		return schemaNames.get(schemaID); }
	
	/** Returns the specified schema element */
	static public SchemaElement getSchemaElement(Integer elementID) throws RemoteException
	{
		if(schemaElements.get(elementID)==null)
			try { schemaElements.put(elementID, client.getSchemaElement(elementID)); } catch(Exception e) {}
		return schemaElements.get(elementID);
	}
	
	/** Get the vocabulary */
	static public Vocabulary getVocabulary(String projectName)
		{ return vocabularies.get(projectName); }
}