// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers;

import org.mitre.flexidata.ygg.model.SchemaManager;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;

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
		{
			
			if(schema.getName().equals(repositorySchema.getName()))
			{
				
				// check whether the two schemas have the same number of elements
				// isolate the elements with the base for each schema
				ArrayList<SchemaElement> repoSchemaElementsWithThisBase = new ArrayList<SchemaElement>();
				for (SchemaElement se : repository.getSchemaElements(repositorySchema.getId()))
				{
					if (se.getBase() != null && se.getBase().equals(repositorySchema.getId()))
					{
						repoSchemaElementsWithThisBase.add(se);
					}
				}
				ArrayList<SchemaElement> schemaElementsWithThisBase = new ArrayList<SchemaElement>();
				for (SchemaElement se : SchemaManager.getSchemaElements(schema.getId()))
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
	public ArrayList<Integer> getExtendedSchemaIDs(String uri) throws ImporterException
	{
		// Initialize the importer if needed
		initIfNeeded(uri);
	
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
				for (SchemaElement se : repository.getSchemaElements(parentSchemaID))
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
				for (SchemaElement se : SchemaManager.getSchemaElements(schemaID))
				{
					IDmapping.put(elementToOldID.get(new String(se.getName().toString()+se.getClass().toString())), se.getId());	
				}
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
			{
				if(schemaElement.getBase() != null && schemaElement.getBase().equals(repositorySchemaID))
				{
					schemaElements.add(schemaElement);
				}
			}
			// replace each OLD ID with NEW ID assigned during parent's import to schemaStore
			for (Integer oldID : IDmapping.keySet())
			{
				alterID(schemaElements, oldID, IDmapping.get(oldID));
			}
			
		} catch(Exception e) {throw new ImporterException(ImporterException.PARSE_FAILURE,e.getMessage()); }
		return schemaElements; 	
	}
	
	static private void alterID(ArrayList<SchemaElement> schemaElements, Integer oldID, Integer newID)
	{
		// Replace all references to old ID with new ID
		for(SchemaElement schemaElement : schemaElements)
		{
			if(schemaElement.getId().equals(oldID)) schemaElement.setId(newID);
			if(schemaElement instanceof Attribute)
			{
				Attribute attribute = (Attribute)schemaElement;
				if(attribute.getDomainID().equals(oldID)) attribute.setDomainID(newID);
				if(attribute.getEntityID().equals(oldID)) attribute.setEntityID(newID);
			}
			if(schemaElement instanceof DomainValue)
			{
				DomainValue domainValue = (DomainValue)schemaElement;
				if(domainValue.getDomainID().equals(oldID)) domainValue.setDomainID(newID);
			}
			if(schemaElement instanceof Relationship)
			{
				Relationship relationship = (Relationship)schemaElement;
				if(relationship.getLeftID().equals(oldID)) relationship.setLeftID(newID);
				if(relationship.getRightID().equals(oldID)) relationship.setRightID(newID);
			}
			if(schemaElement instanceof Containment)
			{
				Containment containment = (Containment)schemaElement;
				if(containment.getParentID().equals(oldID)) containment.setParentID(newID);
				if(containment.getChildID().equals(oldID)) containment.setChildID(newID);				
			}
			if(schemaElement instanceof Subtype)
			{
				Subtype subtype = (Subtype)schemaElement;
				if(subtype.getParentID().equals(oldID)) subtype.setParentID(newID);
				if(subtype.getChildID().equals(oldID)) subtype.setChildID(newID);				
			}
			if(schemaElement instanceof Alias)
			{
				Alias alias = (Alias)schemaElement;
				if(alias.getElementID().equals(oldID)) alias.setElementID(newID);
			}
		}
	} // end method alterID
	
}