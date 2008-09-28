// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers;

import org.mitre.flexidata.ygg.model.SchemaManager;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/** Importer for copying schemas from other repositories */
public class CopySchemaImporter extends Importer
{		
	/** Stores the repository */
	private SchemaStoreClient repository = null;

	/** Stores the repository schema ID */
	private Integer repositorySchemaID = null;

	/** Stores the list of schemas */
	private ArrayList<Schema> schemaList = null;
	
	/** Store the mapping of oldID --> newID for schemaElements */
	private HashMap<Integer, Integer> IDmapping;
	
	/** Returns the importer name */
	public String getName()
		{ return "Copy Schema"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "This method allows the copying of a schema from one repository to another"; }
		
	/** Returns the importer URI type */
	public Integer getURIType()
		{ return REPOSITORY; }
	
	/** Returns the ID of parent schemas */
	public ArrayList<Integer> getParents()
		{ return new ArrayList<Integer>(); }
	
	/** Initialize the importer if needed */
	protected void initialize()
	{
		if(repository==null) repository = new SchemaStoreClient(uri.toString().replaceAll("#.*",""));
		if(repositorySchemaID==null) repositorySchemaID = Integer.parseInt(uri.toString().replaceAll(".*#",""));
	}
	
	/** Identifies the matching schema in the repository if one exists */
	private Integer getMatchedSchema(Schema repositorySchema) throws Exception
	{		
		// Search for schemas with a shared name and the same amount of schema elements
		if(schemaList==null) schemaList = SchemaManager.getSchemas();
		
		for(Schema schema : schemaList)
		{
			
			if(schema.getName().equals(repositorySchema.getName()))
			{
				
				// check whether the two schemas have the same number of elements
				// isolate the elements with the base for each schema
				ArrayList<SchemaElement> repoSchemaElementsWithThisBase = new ArrayList<SchemaElement>();
				for (SchemaElement se : repository.getGraph(repositorySchema.getId()).getElements(null))
				{
					if (se.getBase() != null && se.getBase().equals(repositorySchema.getId()))
					{
						repoSchemaElementsWithThisBase.add(se);
					}
				}
				ArrayList<SchemaElement> schemaElementsWithThisBase = new ArrayList<SchemaElement>();
				for (SchemaElement se : SchemaManager.getGraph(schema.getId()).getElements(null))
				{
					if (se.getBase() != null && se.getBase().equals(schema.getId()))
					{
						schemaElementsWithThisBase.add(se);
					}
				}
			
				// check the two schemas have the same number of elements
				if(schemaElementsWithThisBase.size() == repoSchemaElementsWithThisBase.size())
				{
					// Sort the schema elements
					class SchemaComparator implements Comparator<SchemaElement>
						{ public int compare(SchemaElement schemaElement1, SchemaElement schemaElement2)
							{ return schemaElement1.getName().compareTo(schemaElement2.getName()); } }
					Collections.sort(schemaElementsWithThisBase,new SchemaComparator());
					Collections.sort(repoSchemaElementsWithThisBase,new SchemaComparator());

					// Check to ensure that all schema elements match
					boolean match = true;
					for(int i=0; i<schemaElementsWithThisBase.size(); i++)
					{
						SchemaElement s1 = schemaElementsWithThisBase.get(i);
						SchemaElement s2 = repoSchemaElementsWithThisBase.get(i);
						if( (s1.getClass().equals(s2.getClass()) && s1.getName().equals(s2.getName())) == false)
							{ match = false; break; }
					}
					if(match) return schema.getId();
				}
			}
		}
		// Indicates that no matched schema was found
		return null;
	}
	
	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> getExtendedSchemaIDs() throws ImporterException
	{
		// Get the list of extended schemas
		ArrayList<Integer> extendedSchemas = new ArrayList<Integer>();
		try {
			 
			IDmapping = new HashMap<Integer,Integer>();
			HashMap<String,Integer> elementToOldID = new HashMap<String,Integer>();
			
			for(Integer parentSchemaID : repository.getParentSchemas(repositorySchemaID))
			{
				// Get the repository schema
				Schema repositorySchema = repository.getSchema(parentSchemaID);
			
				// Create a mapping of these elements to 
				for (SchemaElement se : repository.getGraph(parentSchemaID).getElements(null))
				{	elementToOldID.put(new String(se.getName().toString()+se.getClass().toString()),se.getId());}
				
				// Get the extended schema ID
				Integer schemaID = getMatchedSchema(repositorySchema);
				if(schemaID==null)
				{
					CopySchemaImporter importer = new CopySchemaImporter();
					importer.repository = repository;
					importer.repositorySchemaID = parentSchemaID;
					importer.schemaList = schemaList;
					schemaID = importer.importSchema(repositorySchema.getName(), repositorySchema.getAuthor(), repositorySchema.getDescription(), uri);
				}
				extendedSchemas.add(schemaID);		
				
				// get the schemaElements for the 
				for (SchemaElement se : SchemaManager.getGraph(schemaID).getElements(null))
				{
					IDmapping.put(elementToOldID.get(new String(se.getName().toString()+se.getClass().toString())), se.getId());	
				}
			}
			
			
		} catch(Exception e) { throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }
		return extendedSchemas;
	}
	
	/** Returns the schema elements from the specified URI with form [SRC SCHEMA REPO]#[SRC SCHEMA NAME]*/
	public ArrayList<SchemaElement> getSchemaElements() throws ImporterException 
	{ 	
		// Generate the list of schema elements to import for this schema
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
		try {
			Graph graph = repository.getGraph(repositorySchemaID);
			for(SchemaElement schemaElement : graph.getElements(null))
			{
				if(schemaElement.getBase() != null && schemaElement.getBase().equals(repositorySchemaID))
				{
					schemaElements.add(schemaElement);
				}
			}
			// replace each OLD ID with NEW ID assigned during parent's import to schemaStore
			for (Integer oldID : IDmapping.keySet())
				graph.updateElementID(oldID, IDmapping.get(oldID));
			
		} catch(Exception e) {throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }
		return schemaElements; 	
	}	
}