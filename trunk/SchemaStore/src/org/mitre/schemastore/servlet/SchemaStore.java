// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import org.mitre.schemastore.data.DataManager;
import org.mitre.schemastore.data.database.DatabaseConnection;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.SchemaElementList;
import org.mitre.schemastore.model.Subtype;

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
	
	/** Constructs SchemaStore */
	public SchemaStore()
	{
		if(connection==null) connection = new DatabaseConnection();
		manager = new DataManager(connection);
	}
	
	/** Constructs SchemaStore to specified database */
	public SchemaStore(Integer type, String uri, String database, String user, String password)
	{
		DatabaseConnection connection = new DatabaseConnection(type,uri,database,user,password);
		manager = new DataManager(connection);
	}
	
	/** Indicates if SchemaStore is connected to a database */
	public boolean isConnected()
		{ return manager.getDatabase().isConnected(); }
	
	//---------------------------
	// Handles schema operations 
	//---------------------------
	
	/** Web service to retrieve the list of schemas */
	public Schema[] getSchemas()
		{ return manager.getSchemas().getSchemas().toArray(new Schema[0]); }

	/** Web service to retrieve the specified schema */
	public Schema getSchema(int schemaID)
		{ return manager.getSchemas().getSchema(schemaID); }
	
	/** Web service to add the specified schema */
	public int addSchema(Schema schema)
		{ return manager.getSchemas().addSchema(schema); }
	
	/** Web service to extend the specified schema */
	public Schema extendSchema(int schemaID)
		{ return manager.getSchemas().extendSchema(schemaID); }

	/** Web service to update the specified schema */
	public boolean updateSchema(Schema schema)
		{ return manager.getSchemas().updateSchema(schema); }

	/** Web service to unlock the specified schema */
	public boolean unlockSchema(int schemaID)
		{ return manager.getSchemas().unlockSchema(schemaID); }
	
	/** Web service to lock the specified schema */
	public boolean lockSchema(int schemaID)
		{ return manager.getSchemas().lockSchema(schemaID); }

	/** Indicates that the schema is able to be deleted */
	public boolean isDeletable(int schemaID)
		{ return manager.getSchemas().isDeletable(schemaID); }

	/** Get deletable schemas */
	public int[] getDeletableSchemas()
		{ return convertArray(manager.getSchemas().getDeletableSchemas()); }
	
	/** Web service to delete the specified schema */
	public boolean deleteSchema(int schemaID)
		{ return manager.getSchemas().deleteSchema(schemaID); }
	
	//---------------------------------
	// Handles schema group operations
	//---------------------------------

	/** Web service to get the list of schema groups */
	public Group[] getGroups()
		{ return manager.getGroups().getGroups().toArray(new Group[0]); }

	/** Web service to get the specified group */
	public Group getGroup(int groupID)
		{ return manager.getGroups().getGroup(groupID); }

	/** Web service to get the subgroups for the specified group */
	public Group[] getSubgroups(int groupID)
		{ return manager.getGroups().getSubgroups(groupID==0 ? null : groupID).toArray(new Group[0]); }
	
	/** Web service to add a group */
	public int addGroup(Group group)
		{ return manager.getGroups().addGroup(group); }

	/** Web service to update a group */
	public boolean updateGroup(Group group)
		{ return manager.getGroups().updateGroup(group); }
	
	/** Web service to delete a group */
	public boolean deleteGroup(int groupID)
		{ return manager.getGroups().deleteGroup(groupID); }
	
	/** Web service to get list of schemas associated with group */
	public int[] getGroupSchemas(int groupID)
		{ return convertArray(manager.getGroups().getGroupSchemas(groupID)); }	
	
	/** Web service to get list of groups associated with schema */
	public int[] getSchemaGroups(int schemaID)
		{ return convertArray(manager.getGroups().getSchemaGroups(schemaID)); }
		
	/** Web service to add a group to a schema */
	public boolean addGroupToSchema(int schemaID, int groupID)
		{ return manager.getGroups().addGroupToSchema(schemaID, groupID); }
	
	/** Web service to remove a group from a schema */
	public boolean removeGroupFromSchema(int schemaID, int groupID)
		{ return manager.getGroups().removeGroupFromSchema(schemaID, groupID); }
	
	//----------------------------------------
	// Handles schema relationship operations
	//----------------------------------------
	
	/** Web service to get the parent schemas for the specified schema */
	public int[] getParentSchemas(int schemaID)
		{ return convertArray(manager.getSchemaRelationships().getParents(schemaID)); }
	
	/** Web service to get the child schemas for the specified schema */
	public int[] getChildSchemas(int schemaID)
		{ return convertArray(manager.getSchemaRelationships().getChildren(schemaID)); }

	/** Web service to get the ancestor schemas for the specified schema */
	public int[] getAncestorSchemas(int schemaID)
		{ return convertArray(manager.getSchemaRelationships().getAncestors(schemaID)); }

	/** Web service to get the descendant schemas for the specified schema */
	public int[] getDescendantSchemas(int schemaID)
		{ return convertArray(manager.getSchemaRelationships().getDescendants(schemaID)); }

	/** Web service to get the associated schemas of the specified schema */
	public int[] getAssociatedSchemas(int schemaID)
		{ return convertArray(manager.getSchemaRelationships().getAssociatedSchemas(schemaID)); }

	/** Web service to get the root schema of the two specified schemas */
	public int getRootSchema(int schema1ID, int schema2ID)
		{ return manager.getSchemaRelationships().getRootSchema(schema1ID, schema2ID); }	
	
	/** Web service to get the schema path between the specified root and schema */
	public int[] getSchemaPath(int rootID, int schemaID)
		{ return convertArray(manager.getSchemaRelationships().getSchemaPath(rootID, schemaID)); }	
	
	/** Web service to set the parent schemas for the specified schema */
	public boolean setParentSchemas(int schemaID, int[] parentIDs)
	{
		ArrayList<Integer> parentIDArray = new ArrayList<Integer>();
		if(parentIDs!=null) parentIDArray = convertArray(parentIDs);
		return manager.getSchemaRelationships().setParents(schemaID,parentIDArray);
	}
	
	//-----------------------------------
	// Handles Schema Element Operations
	//-----------------------------------

	/** Web service to add the specified entity */
	public int addEntity(Entity entity)
		{ return manager.getSchemaElements().addSchemaElement(entity); }

	/** Web service to add the specified attribute */
	public int addAttribute(Attribute attribute)
		{ return manager.getSchemaElements().addSchemaElement(attribute); }

	/** Web service to add the specified domain */
	public int addDomain(Domain domain)
		{ return manager.getSchemaElements().addSchemaElement(domain); }

	/** Web service to add the specified domain value */
	public int addDomainValue(DomainValue domainValue)
		{ return manager.getSchemaElements().addSchemaElement(domainValue); }

	/** Web service to add the specified relationship */
	public int addRelationship(Relationship relationship)
		{ return manager.getSchemaElements().addSchemaElement(relationship); }

	/** Web service to add the specified containment */
	public int addContainment(Containment containment)
		{ return manager.getSchemaElements().addSchemaElement(containment); }

	/** Web service to add the specified subtype */
	public int addSubtype(Subtype subtype)
		{ return manager.getSchemaElements().addSchemaElement(subtype); }

	/** Web service to add the specified alias */
	public int addAlias(Alias alias)
		{ return manager.getSchemaElements().addSchemaElement(alias); }
	
	/** Web service to update the specified entity */
	public boolean updateEntity(Entity entity)
		{ return manager.getSchemaElements().updateSchemaElement(entity); }

	/** Web service to update the specified attribute */
	public boolean updateAttribute(Attribute attribute)
		{ return manager.getSchemaElements().updateSchemaElement(attribute); }

	/** Web service to update the specified domain */
	public boolean updateDomain(Domain domain)
		{ return manager.getSchemaElements().updateSchemaElement(domain); }

	/** Web service to update the specified domain value */
	public boolean updateDomainValue(DomainValue domainValue)
		{ return manager.getSchemaElements().updateSchemaElement(domainValue); }

	/** Web service to update the specified relationship */
	public boolean updateRelationship(Relationship relationship)
		{ return manager.getSchemaElements().updateSchemaElement(relationship); }

	/** Web service to update the specified containment */
	public boolean updateContainment(Containment containment)
		{ return manager.getSchemaElements().updateSchemaElement(containment); }

	/** Web service to update the specified subtype */
	public boolean updateSubtype(Subtype subtype)
		{ return manager.getSchemaElements().updateSchemaElement(subtype); }

	/** Web service to update the specified alias */
	public boolean updateAlias(Alias alias)
		{ return manager.getSchemaElements().updateSchemaElement(alias); }
	
	/** Web service to delete the specified entity */
	public boolean deleteEntity(int entityID)
		{ return manager.getSchemaElements().deleteSchemaElement(entityID); }

	/** Web service to delete the specified attribute */
	public boolean deleteAttribute(int attributeID)
		{ return manager.getSchemaElements().deleteSchemaElement(attributeID); }

	/** Web service to delete the specified domain */
	public boolean deleteDomain(int domainID)
		{ return manager.getSchemaElements().deleteSchemaElement(domainID); }

	/** Web service to delete the specified domain value */
	public boolean deleteDomainValue(int domainValueID)
		{ return manager.getSchemaElements().deleteSchemaElement(domainValueID); }

	/** Web service to delete the specified relationship */
	public boolean deleteRelationship(int relationshipID)
		{ return manager.getSchemaElements().deleteSchemaElement(relationshipID); }

	/** Web service to delete the specified containment */
	public boolean deleteContainment(int containmentID)
		{ return manager.getSchemaElements().deleteSchemaElement(containmentID); }

	/** Web service to delete the specified subtype */
	public boolean deleteSubtype(int subtypeID)
		{ return manager.getSchemaElements().deleteSchemaElement(subtypeID); }

	/** Web service to delete the specified alias */
	public boolean deleteAlias(int aliasID)
		{ return manager.getSchemaElements().deleteSchemaElement(aliasID); }
	
	/** Web service to get the specified entity */
	public Entity getEntity(int entityID)
		{ return (Entity)manager.getSchemaElements().getSchemaElement(entityID); }

	/** Web service to get the specified attribute */
	public Attribute getAttribute(int attributeID)
		{ return (Attribute)manager.getSchemaElements().getSchemaElement(attributeID); }

	/** Web service to get the specified domain */
	public Domain getDomain(int domainID)
		{ return (Domain)manager.getSchemaElements().getSchemaElement(domainID); }

	/** Web service to get the specified domain value */
	public DomainValue getDomainValue(int domainValueID)
		{ return (DomainValue)manager.getSchemaElements().getSchemaElement(domainValueID); }
	
	/** Web service to get the specified relationship */
	public Relationship getRelationship(int relationshipID)
		{ return (Relationship)manager.getSchemaElements().getSchemaElement(relationshipID); }
	
	/** Web service to get the specified containment */
	public Containment getContainment(int containmentID)
		{ return (Containment)manager.getSchemaElements().getSchemaElement(containmentID); }
	
	/** Web service to get the specified subtype */
	public Subtype getSubtype(int subtypeID)
		{ return (Subtype)manager.getSchemaElements().getSchemaElement(subtypeID); }

	/** Web service to get the specified alias */
	public Alias getAlias(int aliasID)
		{ return (Alias)manager.getSchemaElements().getSchemaElement(aliasID); }
	
	/** Web service to get the number of schema elements associated with the specified schema */
	public int getSchemaElementCount(int schemaID)
		{ return manager.getSchemaElements().getSchemaElementCount(schemaID); }
	
	/** Web service to get the schema elements for the specified schema */
	public SchemaElementList getSchemaElements(int schemaID)
		{ return new SchemaElementList(manager.getSchemaElements().getSchemaElements(schemaID).toArray(new SchemaElement[0])); }

	/** Web service to get the schemas elements referencing the specified keyword */
	public SchemaElementList getSchemaElementsForKeyword(String keyword, int[] groupIDs)
	{
		ArrayList<Integer> groupList = convertArray(groupIDs);
		return new SchemaElementList(manager.getSchemaElements().getSchemaElementsForKeyword(keyword,groupList).toArray(new SchemaElement[0]));
	}
	
	/** Web service to get the schema element type */
	public String getSchemaElementType(int schemaElementID)
		{ return manager.getSchemaElements().getSchemaElementType(schemaElementID); }
	
	//--------------------------------
	// Handles Data Source Operations
	//--------------------------------
	
	/** Web service to get a list of all data sources */
	public DataSource[] getAllDataSources()
		{ return manager.getDataSources().getDataSources(null).toArray(new DataSource[0]); }
			
	/** Web service to get a list of data sources for the specified schema */
	public DataSource[] getDataSources(int schemaID)
		{ return manager.getDataSources().getDataSources(schemaID).toArray(new DataSource[0]); }

	/** Web service to get the specified data source */
	public DataSource getDataSource(int dataSourceID)
		{ return manager.getDataSources().getDataSource(dataSourceID); }

	/** Web service to get the data source specified by its URL */
	public DataSource getDataSourceByURL(String url)
		{ return manager.getDataSources().getDataSourceByURL(url); }

	/** Web service to add the specified data source */
	public int addDataSource(DataSource dataSource)
		{ return manager.getDataSources().addDataSource(dataSource); }

	/** Web service to update the specified data source */
	public boolean updateDataSource(DataSource dataSource)
		{ return manager.getDataSources().updateDataSource(dataSource); }

	/** Web service to delete the specified data source */
	public boolean deleteDataSource(int dataSourceID)
		{ return manager.getDataSources().deleteDataSource(dataSourceID); }

	//----------------------------
	// Handles Mapping Operations
	//----------------------------

	/** Web service to retrieve the list of mappings */
	public Mapping[] getMappings()
		{ return manager.getMappings().getMappings().toArray(new Mapping[0]); }
		
	/** Web service to retrieve the specified mapping */
	public Mapping getMapping(int mappingID)
		{ return manager.getMappings().getMapping(mappingID); }
	
	/** Web service to add the specified mapping */
	public int addMapping(Mapping mapping)
		{ return manager.getMappings().addMapping(mapping); }

	/** Web service to update the specified mapping */
	public boolean updateMapping(Mapping mapping)
		{ return manager.getMappings().updateMapping(mapping); }

	/** Web service to delete the specified mapping */
	public boolean deleteMapping(int mappingID)
		{ return manager.getMappings().deleteMapping(mappingID); }
	
	/** Web service to get the mapping cells for the specified schema */
	public MappingCell[] getMappingCells(int mappingID)
		{ return manager.getMappings().getMappingCells(mappingID).toArray(new MappingCell[0]); }

	/** Web service to add the specified mapping cell */
	public int addMappingCell(MappingCell mappingCell)
		{ return manager.getMappings().addMappingCell(mappingCell); }

	/** Web service to update the specified mapping cell */
	public boolean updateMappingCell(MappingCell mappingCell)
		{ return manager.getMappings().updateMappingCell(mappingCell); }
	
	/** Web service to delete the specified mapping cell */
	public boolean deleteMappingCell(int mappingCellID)
		{ return manager.getMappings().deleteMappingCell(mappingCellID); }

	//-------------------------------
	// Handles Annotation Operations
	//-------------------------------

	/** Web service to set an annotation */
	public boolean setAnnotation(int elementID, String attribute, String value)
		{ return manager.getDatabase().setAnnotation(elementID, attribute, value.equals("")?null:value); }
	
	/** Web service to get an annotation */
	public String getAnnotation(int elementID, String attribute)
		{ return manager.getDatabase().getAnnotation(elementID, attribute); }
	
	//--------------------
	// Derived Operations
	//--------------------
	
	/** Web service to import schemas */
	public int importSchema(Schema schema, SchemaElementList schemaElementList) throws RemoteException
	{
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
		for(SchemaElement element : Arrays.asList(schemaElementList.getSchemaElements()))
			elements.add(element.copy());
		return ImportSchema.importSchema(this, manager, schema, elements);
	}
	
	/** Web service to save the mapping and mapping cells */
	public int saveMapping(Mapping mapping, MappingCell[] mappingCells) throws RemoteException
	{
		ArrayList<MappingCell> mappingCellArray = new ArrayList<MappingCell>();
		if(mappingCells!=null)
			for(MappingCell mappingCell : Arrays.asList(mappingCells))
				mappingCellArray.add(mappingCell.copy());
		return SaveMapping.saveMapping(manager,mapping,mappingCellArray);
	}
}