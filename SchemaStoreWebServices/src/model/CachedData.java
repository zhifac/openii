package model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Vocabulary;

/** Stores the cached data */
public class CachedData
{
	/** Stores the SchemaStore client */
	static private SchemaStoreClient client = null;

	/** Stores the list of vocabularies */
	static private ArrayList<Vocabulary> vocabularies = null;
	
	/** Stores the list of schema names */
	static private HashMap<Integer,Schema> schemas = null;

	/** Stores the list of schema elements */
	static private HashMap<Integer,SchemaElement> schemaElements = new HashMap<Integer,SchemaElement>();
	
	/** Refreshes the specified project vocabulary */
	static public void refresh() throws RemoteException
	{	
		// Connect to the repository (if needed)
		if(client==null) client = ClientManager.getClient();

		// Clear out the old cached data
		vocabularies = new ArrayList<Vocabulary>();
		schemas = new HashMap<Integer,Schema>();
		schemaElements = new HashMap<Integer,SchemaElement>();
		
		// Cache all schemas in the repository
		for(Schema schema : client.getSchemas())
			schemas.put(schema.getId(),schema);
			
		// Identify the projects to use
		ArrayList<Project> projects = new ArrayList<Project>();
		PROJECT_LOOP: for(Project project : client.getProjects())
		{
			for(Integer schemaID : project.getSchemaIDs())
				if(!schemas.get(schemaID).getType().equals("Rescue Supplies Importer"))
					continue PROJECT_LOOP;
			projects.add(project);
		}
		
		// Cache the vocabularies to be used in this search
		for(Project project : projects)
		{
			Vocabulary vocabulary = client.getVocabulary(project.getId());
			if(vocabulary!=null) vocabularies.add(vocabulary);
		}
	}
	
	/** Return the specified schema */
	static public Schema getSchema(Integer schemaID) throws RemoteException
	{
		if(schemas==null) refresh();
		return schemas.get(schemaID);
	}
	
	/** Returns the specified schema element */
	static public SchemaElement getSchemaElement(Integer elementID) throws RemoteException
	{
		if(schemaElements.get(elementID)==null)
			try { schemaElements.put(elementID, client.getSchemaElement(elementID)); } catch(Exception e) {}
		return schemaElements.get(elementID);
	}
	
	/** Get the list of vocabularies */
	static public ArrayList<Vocabulary> getVocabularies() throws RemoteException
	{
		if(vocabularies==null) refresh();
		return new ArrayList<Vocabulary>(vocabularies);
	}
}