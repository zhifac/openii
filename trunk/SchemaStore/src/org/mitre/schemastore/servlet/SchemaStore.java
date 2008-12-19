// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.servlet;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import org.mitre.schemastore.data.DataSources;
import org.mitre.schemastore.data.Groups;
import org.mitre.schemastore.data.Mappings;
import org.mitre.schemastore.data.SchemaElements;
import org.mitre.schemastore.data.SchemaRelationships;
import org.mitre.schemastore.data.Schemas;
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
import org.mitre.schemastore.model.graph.Graph;

/**
 * Web service for retrieving schema information from SchemaStore
 * @author CWOLF
 */
public class SchemaStore
{
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
	
	//---------------------------
	// Handles schema operations 
	//---------------------------
	
	/** Web service to retrieve the list of schemas */
	public Schema[] getSchemas()
		{ return Schemas.getSchemas().toArray(new Schema[0]); }

	/** Web service to retrieve the specified schema */
	public Schema getSchema(int schemaID)
		{ return Schemas.getSchema(schemaID); }
	
	/** Web service to add the specified schema */
	public int addSchema(Schema schema)
		{ return Schemas.addSchema(schema); }
	
	/** Web service to extend the specified schema */
	public Schema extendSchema(int schemaID)
		{ return Schemas.extendSchema(schemaID); }

	/** Web service to update the specified schema */
	public boolean updateSchema(Schema schema)
		{ return Schemas.updateSchema(schema); }

	/** Web service to unlock the specified schema */
	public boolean unlockSchema(int schemaID)
		{ return Schemas.unlockSchema(schemaID); }
	
	/** Web service to lock the specified schema */
	public boolean lockSchema(int schemaID)
		{ return Schemas.lockSchema(schemaID); }

	/** Indicates that the schema is able to be deleted */
	public boolean isDeletable(int schemaID)
		{ return Schemas.isDeletable(schemaID); }

	/** Get deletable schemas */
	public int[] getDeletableSchemas()
		{ return convertArray(Schemas.getDeletableSchemas()); }
	
	/** Web service to delete the specified schema */
	public boolean deleteSchema(int schemaID)
		{ return Schemas.deleteSchema(schemaID); }

	/** Web service to retrieve the synonym list for the specified schema */
	public String[] getSynonyms(int schemaID)
		{ return Schemas.getSynonyms(schemaID).toArray(new String[0]); }
	
	//---------------------------------
	// Handles schema group operations
	//---------------------------------

	/** Web service to get the list of schema groups */
	public Group[] getGroups()
		{ return Groups.getGroups().toArray(new Group[0]); }

	/** Web service to add a group */
	public int addGroup(Group group)
		{ return Groups.addGroup(group); }

	/** Web service to update a group */
	public boolean updateGroup(Group group)
		{ return Groups.updateGroup(group); }
	
	/** Web service to delete a group */
	public boolean deleteGroup(int groupID)
		{ return Groups.deleteGroup(groupID); }
	
	/** Web service to get list of schemas unassigned to any group */
	public int[] getUnassignedSchemas()
		{ return convertArray(Groups.getUnassignedSchemas()); }
	
	/** Web service to get list of schemas associated with group */
	public int[] getGroupSchemas(int groupID)
		{ return convertArray(Groups.getGroupSchemas(groupID)); }	
	
	/** Web service to get list of groups associated with schema */
	public int[] getSchemaGroups(int schemaID)
		{ return convertArray(Groups.getSchemaGroups(schemaID)); }
		
	/** Web service to add a group to a schema */
	public boolean addGroupToSchema(int schemaID, int groupID)
		{ return Groups.addGroupToSchema(schemaID, groupID); }
	
	/** Web service to remove a group from a schema */
	public boolean removeGroupFromSchema(int schemaID, int groupID)
		{ return Groups.removeGroupFromSchema(schemaID, groupID); }
	
	//----------------------------------------
	// Handles schema relationship operations
	//----------------------------------------
	
	/** Web service to get the parent schemas for the specified schema */
	public int[] getParentSchemas(int schemaID)
		{ return convertArray(SchemaRelationships.getParents(schemaID)); }
	
	/** Web service to get the child schemas for the specified schema */
	public int[] getChildSchemas(int schemaID)
		{ return convertArray(SchemaRelationships.getChildren(schemaID)); }

	/** Web service to get the ancestor schemas for the specified schema */
	public int[] getAncestorSchemas(int schemaID)
		{ return convertArray(SchemaRelationships.getAncestors(schemaID)); }

	/** Web service to get the descendant schemas for the specified schema */
	public int[] getDescendantSchemas(int schemaID)
		{ return convertArray(SchemaRelationships.getDescendants(schemaID)); }

	/** Web service to get the associated schemas of the specified schema */
	public int[] getAssociatedSchemas(int schemaID)
		{ return convertArray(SchemaRelationships.getAssociatedSchemas(schemaID)); }

	/** Web service to get the root schema of the two specified schemas */
	public int getRootSchema(int schema1ID, int schema2ID)
		{ return SchemaRelationships.getRootSchema(schema1ID, schema2ID); }	
	
	/** Web service to get the schema path between the specified root and schema */
	public int[] getSchemaPath(int rootID, int schemaID)
		{ return convertArray(SchemaRelationships.getSchemaPath(rootID, schemaID)); }	
	
	/** Web service to set the parent schemas for the specified schema */
	public boolean setParentSchemas(int schemaID, int[] parentIDs)
	{
		ArrayList<Integer> parentIDArray = new ArrayList<Integer>();
		if(parentIDs!=null) parentIDArray = convertArray(parentIDs);
		return SchemaRelationships.setParents(schemaID,parentIDArray);
	}
	
	//-----------------------------------
	// Handles Schema Element Operations
	//-----------------------------------

	/** Web service to add the specified entity */
	public int addEntity(Entity entity)
		{ return SchemaElements.addSchemaElement(entity); }

	/** Web service to add the specified attribute */
	public int addAttribute(Attribute attribute)
		{ return SchemaElements.addSchemaElement(attribute); }

	/** Web service to add the specified domain */
	public int addDomain(Domain domain)
		{ return SchemaElements.addSchemaElement(domain); }

	/** Web service to add the specified domain value */
	public int addDomainValue(DomainValue domainValue)
		{ return SchemaElements.addSchemaElement(domainValue); }

	/** Web service to add the specified relationship */
	public int addRelationship(Relationship relationship)
		{ return SchemaElements.addSchemaElement(relationship); }

	/** Web service to add the specified containment */
	public int addContainment(Containment containment)
		{ return SchemaElements.addSchemaElement(containment); }

	/** Web service to add the specified subtype */
	public int addSubtype(Subtype subtype)
		{ return SchemaElements.addSchemaElement(subtype); }

	/** Web service to add the specified alias */
	public int addAlias(Alias alias)
		{ return SchemaElements.addSchemaElement(alias); }
	
	/** Web service to update the specified entity */
	public boolean updateEntity(Entity entity)
		{ return SchemaElements.updateSchemaElement(entity); }

	/** Web service to update the specified attribute */
	public boolean updateAttribute(Attribute attribute)
		{ return SchemaElements.updateSchemaElement(attribute); }

	/** Web service to update the specified domain */
	public boolean updateDomain(Domain domain)
		{ return SchemaElements.updateSchemaElement(domain); }

	/** Web service to update the specified domain value */
	public boolean updateDomainValue(DomainValue domainValue)
		{ return SchemaElements.updateSchemaElement(domainValue); }

	/** Web service to update the specified relationship */
	public boolean updateRelationship(Relationship relationship)
		{ return SchemaElements.updateSchemaElement(relationship); }

	/** Web service to update the specified containment */
	public boolean updateContainment(Containment containment)
		{ return SchemaElements.updateSchemaElement(containment); }

	/** Web service to update the specified subtype */
	public boolean updateSubtype(Subtype subtype)
		{ return SchemaElements.updateSchemaElement(subtype); }

	/** Web service to update the specified alias */
	public boolean updateAlias(Alias alias)
		{ return SchemaElements.updateSchemaElement(alias); }
	
	/** Web service to delete the specified entity */
	public boolean deleteEntity(int entityID)
		{ return SchemaElements.deleteSchemaElement(entityID); }

	/** Web service to delete the specified attribute */
	public boolean deleteAttribute(int attributeID)
		{ return SchemaElements.deleteSchemaElement(attributeID); }

	/** Web service to delete the specified domain */
	public boolean deleteDomain(int domainID)
		{ return SchemaElements.deleteSchemaElement(domainID); }

	/** Web service to delete the specified domain value */
	public boolean deleteDomainValue(int domainValueID)
		{ return SchemaElements.deleteSchemaElement(domainValueID); }

	/** Web service to delete the specified relationship */
	public boolean deleteRelationship(int relationshipID)
		{ return SchemaElements.deleteSchemaElement(relationshipID); }

	/** Web service to delete the specified containment */
	public boolean deleteContainment(int containmentID)
		{ return SchemaElements.deleteSchemaElement(containmentID); }

	/** Web service to delete the specified subtype */
	public boolean deleteSubtype(int subtypeID)
		{ return SchemaElements.deleteSchemaElement(subtypeID); }

	/** Web service to delete the specified alias */
	public boolean deleteAlias(int aliasID)
		{ return SchemaElements.deleteSchemaElement(aliasID); }
	
	/** Web service to get the specified entity */
	public Entity getEntity(int entityID)
		{ return (Entity)SchemaElements.getSchemaElement(entityID); }

	/** Web service to get the specified attribute */
	public Attribute getAttribute(int attributeID)
		{ return (Attribute)SchemaElements.getSchemaElement(attributeID); }

	/** Web service to get the specified domain */
	public Domain getDomain(int domainID)
		{ return (Domain)SchemaElements.getSchemaElement(domainID); }

	/** Web service to get the specified domain value */
	public DomainValue getDomainValue(int domainValueID)
		{ return (DomainValue)SchemaElements.getSchemaElement(domainValueID); }
	
	/** Web service to get the specified relationship */
	public Relationship getRelationship(int relationshipID)
		{ return (Relationship)SchemaElements.getSchemaElement(relationshipID); }
	
	/** Web service to get the specified containment */
	public Containment getContainment(int containmentID)
		{ return (Containment)SchemaElements.getSchemaElement(containmentID); }
	
	/** Web service to get the specified subtype */
	public Subtype getSubtype(int subtypeID)
		{ return (Subtype)SchemaElements.getSchemaElement(subtypeID); }

	/** Web service to get the specified alias */
	public Alias getAlias(int aliasID)
		{ return (Alias)SchemaElements.getSchemaElement(aliasID); }
	
	/** Web service to get the number of schema elements associated with the specified schema */
	public int getSchemaElementCount(int schemaID)
		{ return SchemaElements.getSchemaElementCount(schemaID); }
	
	/** Web service to get the schema elements for the specified schema */
	public SchemaElementList getSchemaElements(int schemaID)
		{ return new SchemaElementList(SchemaElements.getSchemaElements(schemaID).toArray(new SchemaElement[0])); }

	/** Web service to get the schemas elements referencing the specified keyword */
	public SchemaElementList getSchemaElementsForKeyword(String keyword, int[] groupIDs)
	{
		ArrayList<Integer> groupList = convertArray(groupIDs);
		return new SchemaElementList(SchemaElements.getSchemaElementsForKeyword(keyword,groupList).toArray(new SchemaElement[0]));
	}
	
	/** Web service to get the schema element type */
	public String getSchemaElementType(int schemaElementID)
		{ return SchemaElements.getSchemaElementType(schemaElementID); }
	
	//--------------------------------
	// Handles Data Source Operations
	//--------------------------------
	
	/** Web service to get a list of all data sources */
	public DataSource[] getAllDataSources()
		{ return DataSources.getDataSources(null).toArray(new DataSource[0]); }
			
	/** Web service to get a list of data sources for the specified schema */
	public DataSource[] getDataSources(int schemaID)
		{ return DataSources.getDataSources(schemaID).toArray(new DataSource[0]); }

	/** Web service to get the specified data source */
	public DataSource getDataSource(int dataSourceID)
		{ return DataSources.getDataSource(dataSourceID); }

	/** Web service to get the data source specified by its URL */
	public DataSource getDataSourceByURL(String url)
		{ return DataSources.getDataSourceByURL(url); }

	/** Web service to add the specified data source */
	public int addDataSource(DataSource dataSource)
		{ return DataSources.addDataSource(dataSource); }

	/** Web service to update the specified data source */
	public boolean updateDataSource(DataSource dataSource)
		{ return DataSources.updateDataSource(dataSource); }

	/** Web service to delete the specified data source */
	public boolean deleteDataSource(int dataSourceID)
		{ return DataSources.deleteDataSource(dataSourceID); }

	//----------------------------
	// Handles Mapping Operations
	//----------------------------

	/** Web service to retrieve the list of mappings */
	public Mapping[] getMappings()
		{ return Mappings.getMappings().toArray(new Mapping[0]); }
		
	/** Web service to retrieve the specified mapping */
	public Mapping getMapping(int mappingID)
		{ return Mappings.getMapping(mappingID); }
	
	/** Web service to add the specified mapping */
	public int addMapping(Mapping mapping)
		{ return Mappings.addMapping(mapping); }

	/** Web service to update the specified mapping */
	public boolean updateMapping(Mapping mapping)
		{ return Mappings.updateMapping(mapping); }

	/** Web service to delete the specified mapping */
	public boolean deleteMapping(int mappingID)
		{ return Mappings.deleteMapping(mappingID); }
	
	/** Web service to get the mapping cells for the specified schema */
	public MappingCell[] getMappingCells(int mappingID)
		{ return Mappings.getMappingCells(mappingID).toArray(new MappingCell[0]); }

	/** Web service to add the specified mapping cell */
	public int addMappingCell(MappingCell mappingCell)
		{ return Mappings.addMappingCell(mappingCell); }

	/** Web service to update the specified mapping cell */
	public boolean updateMappingCell(MappingCell mappingCell)
		{ return Mappings.updateMappingCell(mappingCell); }
	
	/** Web service to delete the specified mapping cell */
	public boolean deleteMappingCell(int mappingCellID)
		{ return Mappings.deleteMappingCell(mappingCellID); }

	//--------------------
	// Derived Operations
	//--------------------
	
	/** Web service to import schemas */
	public int importSchema(Schema schema, SchemaElementList schemaElementList) throws RemoteException
	{
		// Generate copied schema element list
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>();
		for(SchemaElement element : schemaElementList.getSchemaElements())
			schemaElements.add(element.copy());

		// Import the schema
		Graph graph = new Graph(schema,schemaElements);
		return ImportSchema.importSchema(this, schema, graph);
	}
	
	/** Web service to save the mapping and mapping cells */
	public int saveMapping(Mapping mapping, MappingCell[] mappingCells) throws RemoteException
	{
		ArrayList<MappingCell> mappingCellArray = new ArrayList<MappingCell>();
		if(mappingCells!=null) mappingCellArray = new ArrayList<MappingCell>(Arrays.asList(mappingCells));
		return SaveMapping.saveMapping(mapping,mappingCellArray);
	}
}