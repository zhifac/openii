// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers;

import org.mitre.flexidata.ygg.model.SchemaManager;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** Importer for copying schemas from other repositories */
public class CopySchemaImporter extends Importer
{	
	/** Stores the repository */
	private SchemaStoreClient repository = null;

	/** Stores the repository schema ID */
	private Integer repositorySchemaID = null;

	/** Stores the list of schemas */
	private ArrayList<Schema> schemaList = null;
	
	/** Returns the importer name */
	public String getName()
		{ return "Copy Schema"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "This method allows the copying of a schema from one repository to another"; }
		
	/** Returns the importer URI type */
	public Integer getURIType()
		{ return REPOSITORY; }
	
	/** Returns the importer URI file types (only needed when URI type is FILE) */
	public ArrayList<String> getURIFileTypes()
		{ return null; }
	
	/** Returns the ID of parent schemas */
	public ArrayList<Integer> getParents()
		{ return new ArrayList<Integer>(); }
	
	/** Initialize the importer if needed */
	private void initIfNeeded(String uri)
	{
		if(repository==null) repository = new SchemaStoreClient(uri.replaceAll("#.*",""));
		if(repositorySchemaID==null) repositorySchemaID = Integer.parseInt(uri.replaceAll(".*#",""));
	}
	
	/** Identifies the matching schema in the repository if one exists */
	private Integer getMatchedSchema(Schema repositorySchema) throws Exception
	{		
		// Search for schemas with a shared name and the same amount of schema elements
		if(schemaList==null) schemaList = SchemaManager.getSchemas();
		for(Schema schema : schemaList)
			if(schema.getName().equals(repositorySchema.getName()))
				if(SchemaManager.getSchemaElementCount(schema.getId()).equals(repository.getSchemaElementCount(repositorySchemaID)))
				{
					// Get the schema elements for the possible matching schemas
					ArrayList<SchemaElement> schemaElements = SchemaManager.getSchemaElements(schema.getId());
					ArrayList<SchemaElement> repositorySchemaElements = repository.getSchemaElements(repositorySchemaID);

					// Sort the schema elements
					class SchemaComparator implements Comparator<SchemaElement>
						{ public int compare(SchemaElement schemaElement1, SchemaElement schemaElement2)
							{ return schemaElement1.getName().compareTo(schemaElement2.getName()); } }
					Collections.sort(schemaElements,new SchemaComparator());
					Collections.sort(repositorySchemaElements,new SchemaComparator());

					// Check to ensure that all schema elements match
					boolean match = true;
					for(int i=0; i<schemaElements.size(); i++)
					{
						SchemaElement s1 = schemaElements.get(i);
						SchemaElement s2 = repositorySchemaElements.get(i);
						if(!s1.getClass().equals(s2.getClass()) || s1.getName().equals(s2.getName()))
							{ match = false; break; }
					}
					if(match) return schema.getId();
				}
		
		// Indicates that no matched schema was found
		return null;
	}
	
	/** Returns the list of schemas which this schema extends */
	public ArrayList<Integer> getExtendedSchemaIDs(String uri) throws ImporterException
	{
		// Initialize the importer if needed
		initIfNeeded(uri);

		// Get the list of extended schemas
		ArrayList<Integer> extendedSchemas = new ArrayList<Integer>();
		try {
			for(Integer parentSchemaID : repository.getParentSchemas(repositorySchemaID))
			{
				// Get the repository schema
				Schema repositorySchema = repository.getSchema(parentSchemaID);
				
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
			}
		} catch(Exception e) { throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }
		return extendedSchemas;
	}
	
	/** Returns the schema elements from the specified URI with form [SRC SCHEMA REPO]#[SRC SCHEMA NAME]*/
	public ArrayList<SchemaElement> getSchemaElements(Integer schemaID, String uri) throws ImporterException 
	{ 	
		// Initialize the importer if needed
		initIfNeeded(uri);		
		
		// Generate the list of schema elements to import for this schema
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
		try {
			for(SchemaElement schemaElement : repository.getSchemaElements(repositorySchemaID))
				if(schemaElement.getBase().equals(repositorySchemaID))
					schemaElements.add(schemaElement);
		} catch(Exception e) { throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }
		return schemaElements; 	
	}
}