// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import org.mitre.schemastore.data.DataManager;
import org.mitre.schemastore.data.database.Database;
import org.mitre.schemastore.data.database.DatabaseConnection;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.SchemaElementList;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.Tag;

/**
 * Web service for retrieving schema information from SchemaStore
 * @author CWOLF
 */
public class SchemaStore
{
	/** Stores a static reference to the database connection for schema store run as a web service */
	static private DatabaseConnection connection = null;
	
	/** Stores the data manager */
	private DataManager manager;
	
	/** Converts Integer array into int array */
	private int[] convertArray(ArrayList<Integer> oldArray)
	{
		int[] array = new int[oldArray.size()];
		for(int i=0; i<oldArray.size(); i++)
			array[i] = oldArray.get(i);
		return array;
	}
	
	/** Converts int array into Integer array */
	private ArrayList<Integer> convertArray(int oldArray[])
	{
		ArrayList<Integer> array = new ArrayList<Integer>();
		if(oldArray!=null)
			for(int i=0; i<oldArray.length; i++)
				array.add(oldArray[i]);
		return array;
	}
	
	/** Gets the manager */
	private DataManager getManager()
	{
		if(manager==null) manager= new DataManager(connection);
		return manager;
	}
	
	/** Constructs SchemaStore */
	public SchemaStore()
		{ if(connection==null) connection = new DatabaseConnection(); }
	
	/** Constructs SchemaStore to specified database */
	public SchemaStore(Integer type, String uri, String database, String user, String password)
		{ connection = new DatabaseConnection(type,uri,database,user,password); }
	
	/** Indicates if SchemaStore is connected to a database */
	public boolean isConnected()
		{ return new Database(connection).isConnected(); }
	
	//---------------------------
	// Handles schema operations 
	//---------------------------
	
	/** Web service to retrieve the list of schemas */
	public Schema[] getSchemas()
		{ return getManager().getSchemas().getSchemas().toArray(new Schema[0]); }

	/** Web service to retrieve the specified schema */
	public Schema getSchema(int schemaID)
		{ return getManager().getSchemas().getSchema(schemaID); }
	
	/** Web service to add the specified schema */
	public int addSchema(Schema schema)
		{ return getManager().getSchemas().addSchema(schema); }
	
	/** Web service to extend the specified schema */
	public Schema extendSchema(int schemaID)
		{ return getManager().getSchemas().extendSchema(schemaID); }

	/** Web service to update the specified schema */
	public boolean updateSchema(Schema schema)
		{ return getManager().getSchemas().updateSchema(schema); }

	/** Web service to unlock the specified schema */
	public boolean unlockSchema(int schemaID)
		{ return getManager().getSchemas().unlockSchema(schemaID); }
	
	/** Web service to lock the specified schema */
	public boolean lockSchema(int schemaID)
		{ return getManager().getSchemas().lockSchema(schemaID); }

	/** Indicates that the schema is able to be deleted */
	public boolean isDeletable(int schemaID)
		{ return getManager().getSchemas().isDeletable(schemaID); }

	/** Get deletable schemas */
	public int[] getDeletableSchemas()
		{ return convertArray(getManager().getSchemas().getDeletableSchemas()); }
	
	/** Web service to delete the specified schema */
	public boolean deleteSchema(int schemaID)
		{ return getManager().getSchemas().deleteSchema(schemaID); }
	
	//-------------------------------
	// Handles schema tag operations
	//-------------------------------

	/** Web service to get the list of schema tags */
	public Tag[] getTags()
		{ return getManager().getTags().getTags().toArray(new Tag[0]); }

	/** Web service to get the specified tag */
	public Tag getTag(int tagID)
		{ return getManager().getTags().getTag(tagID); }

	/** Web service to get the sub-categories for the specified tag */
	public Tag[] getSubcategories(int tagID)
		{ return getManager().getTags().getSubcategories(tagID==0 ? null : tagID).toArray(new Tag[0]); }
	
	/** Web service to add a tag */
	public int addTag(Tag tag)
		{ return getManager().getTags().addTag(tag); }

	/** Web service to update a tag */
	public boolean updateTag(Tag tag)
		{ return getManager().getTags().updateTag(tag); }
	
	/** Web service to delete a tag */
	public boolean deleteTag(int tagID)
		{ return getManager().getTags().deleteTag(tagID); }
	
	/** Web service to get list of schemas associated with tag */
	public int[] getTagSchemas(int tagID)
		{ return convertArray(getManager().getTags().getTagSchemas(tagID)); }	
	
	/** Web service to get list of tags associated with schema */
	public int[] getSchemaTags(int schemaID)
		{ return convertArray(getManager().getTags().getSchemaTags(schemaID)); }
		
	/** Web service to add a tag to a schema */
	public boolean addTagToSchema(int schemaID, int tagID)
		{ return getManager().getTags().addTagToSchema(schemaID, tagID); }
	
	/** Web service to remove a tag from a schema */
	public boolean removeTagFromSchema(int schemaID, int tagID)
		{ return getManager().getTags().removeTagFromSchema(schemaID, tagID); }
	
	//----------------------------------------
	// Handles schema relationship operations
	//----------------------------------------
	
	/** Web service to get the parent schemas for the specified schema */
	public int[] getParentSchemas(int schemaID)
		{ return convertArray(getManager().getSchemaRelationships().getParents(schemaID)); }
	
	/** Web service to get the child schemas for the specified schema */
	public int[] getChildSchemas(int schemaID)
		{ return convertArray(getManager().getSchemaRelationships().getChildren(schemaID)); }

	/** Web service to get the ancestor schemas for the specified schema */
	public int[] getAncestorSchemas(int schemaID)
		{ return convertArray(getManager().getSchemaRelationships().getAncestors(schemaID)); }

	/** Web service to get the descendant schemas for the specified schema */
	public int[] getDescendantSchemas(int schemaID)
		{ return convertArray(getManager().getSchemaRelationships().getDescendants(schemaID)); }

	/** Web service to get the associated schemas of the specified schema */
	public int[] getAssociatedSchemas(int schemaID)
		{ return convertArray(getManager().getSchemaRelationships().getAssociatedSchemas(schemaID)); }

	/** Web service to get the root schema of the two specified schemas */
	public int getRootSchema(int schema1ID, int schema2ID)
		{ return getManager().getSchemaRelationships().getRootSchema(schema1ID, schema2ID); }	
	
	/** Web service to get the schema path between the specified root and schema */
	public int[] getSchemaPath(int rootID, int schemaID)
		{ return convertArray(getManager().getSchemaRelationships().getSchemaPath(rootID, schemaID)); }	
	
	/** Web service to set the parent schemas for the specified schema */
	public boolean setParentSchemas(int schemaID, int[] parentIDs)
	{
		ArrayList<Integer> parentIDArray = new ArrayList<Integer>();
		if(parentIDs!=null) parentIDArray = convertArray(parentIDs);
		return getManager().getSchemaRelationships().setParents(schemaID,parentIDArray);
	}
	
	//-----------------------------------
	// Handles Schema Element Operations
	//-----------------------------------

	/** Web service to add the specified entity */
	public int addEntity(Entity entity)
		{ return getManager().getSchemaElements().addSchemaElement(entity); }

	/** Web service to add the specified attribute */
	public int addAttribute(Attribute attribute)
		{ return getManager().getSchemaElements().addSchemaElement(attribute); }

	/** Web service to add the specified domain */
	public int addDomain(Domain domain)
		{ return getManager().getSchemaElements().addSchemaElement(domain); }

	/** Web service to add the specified domain value */
	public int addDomainValue(DomainValue domainValue)
		{ return getManager().getSchemaElements().addSchemaElement(domainValue); }

	/** Web service to add the specified relationship */
	public int addRelationship(Relationship relationship)
		{ return getManager().getSchemaElements().addSchemaElement(relationship); }

	/** Web service to add the specified containment */
	public int addContainment(Containment containment)
		{ return getManager().getSchemaElements().addSchemaElement(containment); }

	/** Web service to add the specified subtype */
	public int addSubtype(Subtype subtype)
		{ return getManager().getSchemaElements().addSchemaElement(subtype); }

	/** Web service to add the specified alias */
	public int addAlias(Alias alias)
		{ return getManager().getSchemaElements().addSchemaElement(alias); }
	
	/** Web service to update the specified entity */
	public boolean updateEntity(Entity entity)
		{ return getManager().getSchemaElements().updateSchemaElement(entity); }

	/** Web service to update the specified attribute */
	public boolean updateAttribute(Attribute attribute)
		{ return getManager().getSchemaElements().updateSchemaElement(attribute); }

	/** Web service to update the specified domain */
	public boolean updateDomain(Domain domain)
		{ return getManager().getSchemaElements().updateSchemaElement(domain); }

	/** Web service to update the specified domain value */
	public boolean updateDomainValue(DomainValue domainValue)
		{ return getManager().getSchemaElements().updateSchemaElement(domainValue); }

	/** Web service to update the specified relationship */
	public boolean updateRelationship(Relationship relationship)
		{ return getManager().getSchemaElements().updateSchemaElement(relationship); }

	/** Web service to update the specified containment */
	public boolean updateContainment(Containment containment)
		{ return getManager().getSchemaElements().updateSchemaElement(containment); }

	/** Web service to update the specified subtype */
	public boolean updateSubtype(Subtype subtype)
		{ return getManager().getSchemaElements().updateSchemaElement(subtype); }

	/** Web service to update the specified alias */
	public boolean updateAlias(Alias alias)
		{ return getManager().getSchemaElements().updateSchemaElement(alias); }
	
	/** Web service to delete the specified entity */
	public boolean deleteEntity(int entityID)
		{ return getManager().getSchemaElements().deleteSchemaElement(entityID); }

	/** Web service to delete the specified attribute */
	public boolean deleteAttribute(int attributeID)
		{ return getManager().getSchemaElements().deleteSchemaElement(attributeID); }

	/** Web service to delete the specified domain */
	public boolean deleteDomain(int domainID)
		{ return getManager().getSchemaElements().deleteSchemaElement(domainID); }

	/** Web service to delete the specified domain value */
	public boolean deleteDomainValue(int domainValueID)
		{ return getManager().getSchemaElements().deleteSchemaElement(domainValueID); }

	/** Web service to delete the specified relationship */
	public boolean deleteRelationship(int relationshipID)
		{ return getManager().getSchemaElements().deleteSchemaElement(relationshipID); }

	/** Web service to delete the specified containment */
	public boolean deleteContainment(int containmentID)
		{ return getManager().getSchemaElements().deleteSchemaElement(containmentID); }

	/** Web service to delete the specified subtype */
	public boolean deleteSubtype(int subtypeID)
		{ return getManager().getSchemaElements().deleteSchemaElement(subtypeID); }

	/** Web service to delete the specified alias */
	public boolean deleteAlias(int aliasID)
		{ return getManager().getSchemaElements().deleteSchemaElement(aliasID); }
	
	/** Web service to get the specified entity */
	public Entity getEntity(int entityID)
		{ return (Entity)getManager().getSchemaElements().getSchemaElement(entityID); }

	/** Web service to get the specified attribute */
	public Attribute getAttribute(int attributeID)
		{ return (Attribute)getManager().getSchemaElements().getSchemaElement(attributeID); }

	/** Web service to get the specified domain */
	public Domain getDomain(int domainID)
		{ return (Domain)getManager().getSchemaElements().getSchemaElement(domainID); }

	/** Web service to get the specified domain value */
	public DomainValue getDomainValue(int domainValueID)
		{ return (DomainValue)getManager().getSchemaElements().getSchemaElement(domainValueID); }
	
	/** Web service to get the specified relationship */
	public Relationship getRelationship(int relationshipID)
		{ return (Relationship)getManager().getSchemaElements().getSchemaElement(relationshipID); }
	
	/** Web service to get the specified containment */
	public Containment getContainment(int containmentID)
		{ return (Containment)getManager().getSchemaElements().getSchemaElement(containmentID); }
	
	/** Web service to get the specified subtype */
	public Subtype getSubtype(int subtypeID)
		{ return (Subtype)getManager().getSchemaElements().getSchemaElement(subtypeID); }

	/** Web service to get the specified alias */
	public Alias getAlias(int aliasID)
		{ return (Alias)getManager().getSchemaElements().getSchemaElement(aliasID); }
	
	/** Web service to get the number of schema elements associated with the specified schema */
	public int getSchemaElementCount(int schemaID)
		{ return getManager().getSchemaElements().getSchemaElementCount(schemaID); }
	
	/** Web service to get the schema elements for the specified schema */
	public SchemaElementList getSchemaElements(int schemaID)
		{ return new SchemaElementList(getManager().getSchemaElements().getSchemaElements(schemaID).toArray(new SchemaElement[0])); }

	/** Web service to get the schemas elements referencing the specified keyword */
	public SchemaElementList getSchemaElementsForKeyword(String keyword, int[] tagIDs)
	{
		ArrayList<Integer> tagList = convertArray(tagIDs);
		return new SchemaElementList(getManager().getSchemaElements().getSchemaElementsForKeyword(keyword,tagList).toArray(new SchemaElement[0]));
	}
	
	/** Web service to get the schema element type */
	public String getSchemaElementType(int schemaElementID)
		{ return getManager().getSchemaElements().getSchemaElementType(schemaElementID); }
	
	//--------------------------------
	// Handles Data Source Operations
	//--------------------------------
	
	/** Web service to get a list of all data sources */
	public DataSource[] getAllDataSources()
		{ return getManager().getDataSources().getDataSources(null).toArray(new DataSource[0]); }
			
	/** Web service to get a list of data sources for the specified schema */
	public DataSource[] getDataSources(int schemaID)
		{ return getManager().getDataSources().getDataSources(schemaID).toArray(new DataSource[0]); }

	/** Web service to get the specified data source */
	public DataSource getDataSource(int dataSourceID)
		{ return getManager().getDataSources().getDataSource(dataSourceID); }

	/** Web service to get the data source specified by its URL */
	public DataSource getDataSourceByURL(String url)
		{ return getManager().getDataSources().getDataSourceByURL(url); }

	/** Web service to add the specified data source */
	public int addDataSource(DataSource dataSource)
		{ return getManager().getDataSources().addDataSource(dataSource); }

	/** Web service to update the specified data source */
	public boolean updateDataSource(DataSource dataSource)
		{ return getManager().getDataSources().updateDataSource(dataSource); }

	/** Web service to delete the specified data source */
	public boolean deleteDataSource(int dataSourceID)
		{ return getManager().getDataSources().deleteDataSource(dataSourceID); }

	//----------------------------
	// Handles Project Operations
	//----------------------------

	/** Web service to retrieve the list of projects */
	public Project[] getProjects()
		{ return getManager().getProjects().getProjects().toArray(new Project[0]); }
		
	/** Web service to retrieve the specified project */
	public Project getProject(int projectID)
		{ return getManager().getProjects().getProject(projectID); }
	
	/** Web service to add the specified project */
	public int addProject(Project project)
		{ return getManager().getProjects().addProject(project); }

	/** Web service to update the specified project */
	public boolean updateProject(Project project)
		{ return getManager().getProjects().updateProject(project); }

	/** Web service to delete the specified project */
	public boolean deleteProject(int projectID)
		{ return getManager().getProjects().deleteProject(projectID); }

	/** Web service to retrieve the list of mappings for the specified project */
	public Mapping[] getMappings(int projectID)
		{ return getManager().getProjects().getMappings(projectID).toArray(new Mapping[0]); }
		
	/** Web service to retrieve the specified mapping */
	public Mapping getMapping(int mappingID)
		{ return getManager().getProjects().getMapping(mappingID); }
	
	/** Web service to add the specified mapping */
	public int addMapping(Mapping mapping)
		{ return getManager().getProjects().addMapping(mapping); }

	/** Web service to delete the specified mapping */
	public boolean deleteMapping(int mappingID)
		{ return getManager().getProjects().deleteMapping(mappingID); }
	
	/** Web service to get the mapping cells for the specified mapping */
	public MappingCell[] getMappingCells(int mappingID)
		{ return getManager().getProjects().getMappingCells(mappingID).toArray(new MappingCell[0]); }

	/** Web service to add the specified mapping cell */
	public int addMappingCell(MappingCell mappingCell)
		{ return getManager().getProjects().addMappingCell(mappingCell); }

	/** Web service to update the specified mapping cell */
	public boolean updateMappingCell(MappingCell mappingCell)
		{ return getManager().getProjects().updateMappingCell(mappingCell); }
	
	/** Web service to delete the specified mapping cell */
	public boolean deleteMappingCell(int mappingCellID)
		{ return getManager().getProjects().deleteMappingCell(mappingCellID); }

	//-------------------------------
	// Handles Annotation Operations
	//-------------------------------

	/** Web service to set an annotation */
	public boolean setAnnotation(int elementID, String attribute, String value)
		{ return getManager().getDatabase().setAnnotation(elementID, attribute, value.equals("")?null:value); }
	
	/** Web service to get an annotation */
	public String getAnnotation(int elementID, String attribute)
		{ return getManager().getDatabase().getAnnotation(elementID, attribute); }
	
	//--------------------
	// Derived Operations
	//--------------------
	
	/** Web service to import schemas */
	public int importSchema(Schema schema, SchemaElementList schemaElementList) throws RemoteException
	{
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
		for(SchemaElement element : Arrays.asList(schemaElementList.geetSchemaElements()))
			elements.add(element.copy());
		return ImportSchema.importSchema(this, getManager(), schema, elements);
	}
	
	/** Web service to save the mapping and mapping cells */
	public boolean saveMappingCells(int mappingID, MappingCell[] mappingCells) throws RemoteException
	{
		ArrayList<MappingCell> mappingCellArray = new ArrayList<MappingCell>();
		if(mappingCells!=null)
			for(MappingCell mappingCell : Arrays.asList(mappingCells))
				mappingCellArray.add(mappingCell.copy());
		return SaveMappingCells.saveMappingCells(getManager(),mappingID,mappingCellArray);
	}
}