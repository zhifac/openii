// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.Graph;
import org.mitre.schemastore.porters.ImporterException;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/** Importer for copying schemas from other repositories */
public class CopySchemaImporter extends SchemaImporter
{
	/** Private class for sorting schema elements */
	class SchemaElementComparator implements Comparator<SchemaElement>
	{
		public int compare(SchemaElement element1, SchemaElement element2)
			{ return element1.getId().compareTo(element2.getId()); }
	}
	
	/** Stores the repository being accessed */
	private SchemaStoreClient repository = null;

	/** Stores the list of schemas */
	private ArrayList<Integer> extendedSchemaIDs = new ArrayList<Integer>();

	/** Stores the list of schema elements */
	private ArrayList<SchemaElement> schemaElements = null;
	
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
	
	/** Identifies the matching schema in the repository if one exists */
	private Integer getMatchedSchema(ArrayList<Schema> availableSchemas, Schema schema) throws Exception
	{
		Graph graph = null;
		
		// Search through available schemas to find if one matches
		for(Schema possSchema : availableSchemas)
			if(possSchema.getName().equals(schema.getName()))
			{
				// Generate graphs for both schemas
				if(graph==null) graph = repository.getGraph(schema.getId());
				Graph possGraph = client.getGraph(possSchema.getId());
				
				// Check to see if number of elements matches
				ArrayList<SchemaElement> graphElements = graph.getElements(null);
				ArrayList<SchemaElement> possGraphElements = possGraph.getElements(null);
				if(graphElements.size()==possGraphElements.size())
				{
					// Sort the elements in each list
					Collections.sort(graphElements,new SchemaElementComparator());
					Collections.sort(possGraphElements,new SchemaElementComparator());

					// Validate that all of the elements are indeed the same
					boolean match = true;
					for(int i=0; i<graphElements.size(); i++)
					{
						SchemaElement s1 = graphElements.get(i);
						SchemaElement s2 = possGraphElements.get(i);
						if((s1.getClass().equals(s2.getClass()) && s1.getName().equals(s2.getName()))==false)
							{ match = false; break; }
					}
					if(match) return possSchema.getId();					
				}
		}

		// Indicates that no matched schema was found
		return null;
	}

	/** Initialize the importer if needed */
	protected void initialize() throws ImporterException
	{	
		try {
			// Define repository to be copied from
			if(repository==null) repository = new SchemaStoreClient(uri.toString().replaceAll("#.*",""));
			Integer repositorySchemaID = Integer.parseInt(uri.toString().replaceAll(".*#",""));
			
			// Retrieve the repository graph
			Graph repositoryGraph = repository.getGraph(repositorySchemaID);
			
			// Transfer all schemas from which this schema is extended
			ArrayList<Schema> availableSchemas = client.getSchemas();
			for(Integer parentSchemaID : repository.getParentSchemas(repositorySchemaID))
			{
				// Get the repository schema
				Schema repositorySchema = repository.getSchema(parentSchemaID);
				
				// Get the extended schema ID
				Integer schemaID = getMatchedSchema(availableSchemas, repositorySchema);
				if(schemaID==null)
				{
					// Copy over the parent schema
					CopySchemaImporter importer = new CopySchemaImporter();
					importer.repository = repository;
					URI parentURI = new URI(uri.toString().replaceAll("#.*","")+"#"+parentSchemaID);
					schemaID = importer.importSchema(repositorySchema.getName(), repositorySchema.getAuthor(), repositorySchema.getDescription(), parentURI);
				}
				extendedSchemaIDs.add(schemaID);

				// Collect and sort the elements from the two repositories
				ArrayList<SchemaElement> origElements = repository.getGraph(parentSchemaID).getElements(null);
				ArrayList<SchemaElement> newElements = client.getGraph(schemaID).getElements(null);				
				Collections.sort(origElements,new SchemaElementComparator());
				Collections.sort(newElements,new SchemaElementComparator());
				
				// Update the graph to reflect altered element IDs
				for(int i=0; i<origElements.size(); i++)
				{
					Integer origID = origElements.get(i).getId();
					Integer newID = newElements.get(i).getId();
					if(newID>=0) repositoryGraph.updateElementID(newID,origID);
				}
			}
			

			// Throw an exception if schema already exists in repository
			if(getMatchedSchema(availableSchemas, repositoryGraph.getSchema())!=null)
				throw new Exception("Schema already exists in repository");
			
			// Retrieve the schema elements
			schemaElements = repositoryGraph.getBaseElements(null);
		}
		catch(Exception e)
			{ repository=null; throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }

		repository = null;
	}
	
	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> getExtendedSchemaIDs() throws ImporterException
		{ return extendedSchemaIDs; }
	
	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> getSchemaElements() throws ImporterException
		{ return schemaElements; }
}