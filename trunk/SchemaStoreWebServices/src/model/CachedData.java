package model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.terms.Term;
import org.mitre.schemastore.model.terms.VocabularyTerms;

/** Stores the cached data */
public class CachedData
{
	/** Stores the SchemaStore client */
	static private SchemaStoreClient client = null;

	/** Stores the list of vocabularies */
	static private ArrayList<VocabularyTerms> vocabularyTermLists = null;
	
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
		vocabularyTermLists = new ArrayList<VocabularyTerms>();
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
			VocabularyTerms vocabularyTerms = client.getVocabularyTerms(project.getId());
			if(vocabularyTerms!=null) vocabularyTermLists.add(vocabularyTerms);
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
	
	/** Get the list of vocabulary terms */
	static public ArrayList<VocabularyTerms> getVocabularyTermLists() throws RemoteException
	{
		if(vocabularyTermLists==null) refresh();
		return new ArrayList<VocabularyTerms>(vocabularyTermLists);
	}
	
	/** Get the sorted list of terms */
	static public ArrayList<Term> getTerms() throws RemoteException
	{
		// Retrieve the list of vocabulary terms
		ArrayList<Term> terms = new ArrayList<Term>();
		for(VocabularyTerms vocabularyTerms : CachedData.getVocabularyTermLists())
			terms.addAll(Arrays.asList(vocabularyTerms.getTerms()));

		// Sort the list of terms
		class TermComparator implements Comparator<Term>
		{
			public int compare(Term term1, Term term2)
				{ return term1.getName().compareTo(term2.getName()); }
		}
		Collections.sort(terms, new TermComparator());
		
		// Returns the list of terms
		return terms;
	}
}