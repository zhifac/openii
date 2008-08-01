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

/**
 * Web service for retrieving schema information from SchemaStore
 * @author CWOLF
 */
public class SchemaStore
{	
	//---------------------------
	// Handles schema operations 
	//---------------------------
	
	/** Web service to retrieve the list of schemas */
	public Schema[] getSchemas()
		{ return Schemas.getSchemaList().toArray(new Schema[0]); }

	/** Web service to retrieve the specified schema */
	public Schema getSchema(Integer schemaID)
		{ return Schemas.getSchema(schemaID); }
	
	/** Web service to add the specified schema */
	public Integer addSchema(Schema schema)
		{ return Schemas.addSchema(schema); }
	
	/** Web service to extend the specified schema */
	public Schema extendSchema(Integer schemaID)
		{ return Schemas.extendSchema(schemaID); }

	/** Web service to update the specified schema */
	public Boolean updateSchema(Schema schema)
		{ return Schemas.updateSchema(schema); }

	/** Web service to unlock the specified schema */
	public Boolean unlockSchema(Integer schemaID)
		{ return Schemas.unlockSchema(schemaID); }
	
	/** Web service to lock the specified schema */
	public Boolean lockSchema(Integer schemaID)
		{ return Schemas.lockSchema(schemaID); }

	/** Indicates that the schema is able to be deleted */
	public Boolean isDeletable(Integer schemaID)
		{ return Schemas.isDeletable(schemaID); }
	
	/** Web service to delete the specified schema */
	public Boolean deleteSchema(Integer schemaID)
		{ return Schemas.deleteSchema(schemaID); }

	/** Web service to retrieve the synonym list for the specified schema */
	public String[] getSynonyms(Integer schemaID)
		{ return new String[0]; }
	
	//---------------------------------
	// Handles schema group operations
	//---------------------------------

	/** Web service to get the list of schema groups */
	public Group[] getGroups()
		{ return Groups.getGroups().toArray(new Group[0]); }

	/** Web service to add a group */
	public Integer addGroup(Group group)
		{ return Groups.addGroup(group); }

	/** Web service to update a group */
	public Boolean updateGroup(Group group)
		{ return Groups.updateGroup(group); }
	
	/** Web service to delete a group */
	public Boolean deleteGroup(Integer groupID)
		{ return Groups.deleteGroup(groupID); }
	
	/** Web service to get list of schemas unassigned to any group */
	public Integer[] getUnassignedSchemas()
		{ return Groups.getUnassignedSchemas().toArray(new Integer[0]); }
	
	/** Web service to get list of schemas associated with group */
	public Integer[] getGroupSchemas(Integer groupID)
		{ return Groups.getGroupSchemas(groupID).toArray(new Integer[0]); }	
	
	/** Web service to get list of groups associated with schema */
	public Integer[] getSchemaGroups(Integer schemaID)
		{ return Groups.getSchemaGroups(schemaID).toArray(new Integer[0]); }
		
	/** Web service to add a group to a schema */
	public Boolean addGroupToSchema(Integer schemaID, Integer groupID)
		{ return Groups.addGroupToSchema(schemaID, groupID); }
	
	/** Web service to remove a group from a schema */
	public Boolean removeGroupFromSchema(Integer schemaID, Integer groupID)
		{ return Groups.removeGroupFromSchema(schemaID, groupID); }
	
	//----------------------------------------
	// Handles schema relationship operations
	//----------------------------------------
	
	/** Web service to get the parent schemas for the specified schema */
	public Integer[] getParentSchemas(Integer schemaID)
		{ return SchemaRelationships.getParents(schemaID).toArray(new Integer[0]); }
	
	/** Web service to get the child schemas for the specified schema */
	public Integer[] getChildSchemas(Integer schemaID)
		{ return SchemaRelationships.getChildren(schemaID).toArray(new Integer[0]); }

	/** Web service to get the ancestor schemas for the specified schema */
	public Integer[] getAncestorSchemas(Integer schemaID)
		{ return SchemaRelationships.getAncestors(schemaID).toArray(new Integer[0]); }

	/** Web service to get the descendant schemas for the specified schema */
	public Integer[] getDescendantSchemas(Integer schemaID)
		{ return SchemaRelationships.getDescendants(schemaID).toArray(new Integer[0]); }

	/** Web service to get the associated schemas of the specified schema */
	public Integer[] getAssociatedSchemas(Integer schemaID)
		{ return SchemaRelationships.getAssociatedSchemas(schemaID).toArray(new Integer[0]); }

	/** Web service to get the root schema of the two specified schemas */
	public Integer getRootSchema(Integer schema1ID, Integer schema2ID)
		{ return SchemaRelationships.getRootSchema(schema1ID, schema2ID); }	
	
	/** Web service to get the schema path between the specified root and schema */
	public Integer[] getSchemaPath(Integer rootID, Integer schemaID)
		{ return SchemaRelationships.getSchemaPath(rootID, schemaID).toArray(new Integer[0]); }	
	
	/** Web service to set the parent schemas for the specified schema */
	public Boolean setParentSchemas(Integer schemaID, Integer[] parentIDs)
	{
		ArrayList<Integer> parentIDArray = new ArrayList<Integer>();
		if(parentIDs!=null) parentIDArray = new ArrayList<Integer>(Arrays.asList(parentIDs));		
		return SchemaRelationships.setParents(schemaID,parentIDArray);
	}
	
	//-----------------------------------
	// Handles Schema Element Operations
	//-----------------------------------

	/** Web service to add the specified entity */
	public Integer addEntity(Entity entity)
		{ return SchemaElements.addSchemaElement(entity); }

	/** Web service to add the specified attribute */
	public Integer addAttribute(Attribute attribute)
		{ return SchemaElements.addSchemaElement(attribute); }

	/** Web service to add the specified domain */
	public Integer addDomain(Domain domain)
		{ return SchemaElements.addSchemaElement(domain); }

	/** Web service to add the specified domain value */
	public Integer addDomainValue(DomainValue domainValue)
		{ return SchemaElements.addSchemaElement(domainValue); }

	/** Web service to add the specified relationship */
	public Integer addRelationship(Relationship relationship)
		{ return SchemaElements.addSchemaElement(relationship); }

	/** Web service to add the specified containment */
	public Integer addContainment(Containment containment)
		{ return SchemaElements.addSchemaElement(containment); }

	/** Web service to add the specified subtype */
	public Integer addSubtype(Subtype subtype)
		{ return SchemaElements.addSchemaElement(subtype); }

	/** Web service to add the specified alias */
	public Integer addAlias(Alias alias)
		{ return SchemaElements.addSchemaElement(alias); }
	
	/** Web service to update the specified entity */
	public Boolean updateEntity(Entity entity)
		{ return SchemaElements.updateSchemaElement(entity); }

	/** Web service to update the specified attribute */
	public Boolean updateAttribute(Attribute attribute)
		{ return SchemaElements.updateSchemaElement(attribute); }

	/** Web service to update the specified domain */
	public Boolean updateDomain(Domain domain)
		{ return SchemaElements.updateSchemaElement(domain); }

	/** Web service to update the specified domain value */
	public Boolean updateDomainValue(DomainValue domainValue)
		{ return SchemaElements.updateSchemaElement(domainValue); }

	/** Web service to update the specified relationship */
	public Boolean updateRelationship(Relationship relationship)
		{ return SchemaElements.updateSchemaElement(relationship); }

	/** Web service to update the specified containment */
	public Boolean updateContainment(Containment containment)
		{ return SchemaElements.updateSchemaElement(containment); }

	/** Web service to update the specified subtype */
	public Boolean updateSubtype(Subtype subtype)
		{ return SchemaElements.updateSchemaElement(subtype); }

	/** Web service to update the specified alias */
	public Boolean updateAlias(Alias alias)
		{ return SchemaElements.updateSchemaElement(alias); }
	
	/** Web service to delete the specified entity */
	public Boolean deleteEntity(Integer entityID)
		{ return SchemaElements.deleteSchemaElement(entityID); }

	/** Web service to delete the specified attribute */
	public Boolean deleteAttribute(Integer attributeID)
		{ return SchemaElements.deleteSchemaElement(attributeID); }

	/** Web service to delete the specified domain */
	public Boolean deleteDomain(Integer domainID)
		{ return SchemaElements.deleteSchemaElement(domainID); }

	/** Web service to delete the specified domain value */
	public Boolean deleteDomainValue(Integer domainValueID)
		{ return SchemaElements.deleteSchemaElement(domainValueID); }

	/** Web service to delete the specified relationship */
	public Boolean deleteRelationship(Integer relationshipID)
		{ return SchemaElements.deleteSchemaElement(relationshipID); }

	/** Web service to delete the specified containment */
	public Boolean deleteContainment(Integer containmentID)
		{ return SchemaElements.deleteSchemaElement(containmentID); }

	/** Web service to delete the specified subtype */
	public Boolean deleteSubtype(Integer subtypeID)
		{ return SchemaElements.deleteSchemaElement(subtypeID); }

	/** Web service to delete the specified alias */
	public Boolean deleteAlias(Integer aliasID)
		{ return SchemaElements.deleteSchemaElement(aliasID); }
	
	/** Web service to get the specified entity */
	public Entity getEntity(Integer entityID)
		{ return (Entity)SchemaElements.getSchemaElement(entityID); }

	/** Web service to get the specified attribute */
	public Attribute getAttribute(Integer attributeID)
		{ return (Attribute)SchemaElements.getSchemaElement(attributeID); }

	/** Web service to get the specified domain */
	public Domain getDomain(Integer domainID)
		{ return (Domain)SchemaElements.getSchemaElement(domainID); }

	/** Web service to get the specified domain value */
	public DomainValue getDomainValue(Integer domainValueID)
		{ return (DomainValue)SchemaElements.getSchemaElement(domainValueID); }
	
	/** Web service to get the specified relationship */
	public Relationship getRelationship(Integer relationshipID)
		{ return (Relationship)SchemaElements.getSchemaElement(relationshipID); }
	
	/** Web service to get the specified containment */
	public Containment getContainment(Integer containmentID)
		{ return (Containment)SchemaElements.getSchemaElement(containmentID); }
	
	/** Web service to get the specified subtype */
	public Subtype getSubtype(Integer subtypeID)
		{ return (Subtype)SchemaElements.getSchemaElement(subtypeID); }

	/** Web service to get the specified alias */
	public Alias getAlias(Integer aliasID)
		{ return (Alias)SchemaElements.getSchemaElement(aliasID); }
	
	/** Web service to get the number of schema elements associated with the specified schema */
	public Integer getSchemaElementCount(Integer schemaID)
		{ return SchemaElements.getSchemaElementCount(schemaID); }
	
	/** Web service to get the schema elements for the specified schema */
	public SchemaElementList getSchemaElements(Integer schemaID)
		{ return new SchemaElementList(SchemaElements.getSchemaElements(schemaID).toArray(new SchemaElement[0])); }

	/** Web service to get the schema element type */
	public String getSchemaElementType(Integer schemaElementID)
		{ return SchemaElements.getSchemaElementType(schemaElementID); }

	//--------------------------------
	// Handles Data Source Operations
	//--------------------------------
	
	/** Web service to get a list of all data sources */
	public DataSource[] getAllDataSources()
		{ return DataSources.getDataSources(null).toArray(new DataSource[0]); }
			
	/** Web service to get a list of data sources for the specified schema */
	public DataSource[] getDataSources(Integer schemaID)
		{ return DataSources.getDataSources(schemaID).toArray(new DataSource[0]); }

	/** Web service to get the specified data source */
	public DataSource getDataSource(Integer dataSourceID)
		{ return DataSources.getDataSource(dataSourceID); }

	/** Web service to get the data source specified by its URL */
	public DataSource getDataSourceByURL(String url)
		{ return DataSources.getDataSourceByURL(url); }

	/** Web service to add the specified data source */
	public Integer addDataSource(DataSource dataSource)
		{ return DataSources.addDataSource(dataSource); }

	/** Web service to update the specified data source */
	public Boolean updateDataSource(DataSource dataSource)
		{ return DataSources.updateDataSource(dataSource); }

	/** Web service to delete the specified data source */
	public Boolean deleteDataSource(Integer dataSourceID)
		{ return DataSources.deleteDataSource(dataSourceID); }

	//----------------------------
	// Handles Mapping Operations
	//----------------------------

	/** Web service to retrieve the list of mappings */
	public Mapping[] getMappings()
		{ return Mappings.getMappings().toArray(new Mapping[0]); }
		
	/** Web service to retrieve the specified mapping */
	public Mapping getMapping(Integer mappingID)
		{ return Mappings.getMapping(mappingID); }
	
	/** Web service to add the specified mapping */
	public Integer addMapping(Mapping mapping)
		{ return Mappings.addMapping(mapping); }

	/** Web service to update the specified mapping */
	public Boolean updateMapping(Mapping mapping)
		{ return Mappings.updateMapping(mapping); }

	/** Web service to delete the specified mapping */
	public Boolean deleteMapping(Integer mappingID)
		{ return Mappings.deleteMapping(mappingID); }
	
	/** Web service to get the mapping cells for the specified schema */
	public MappingCell[] getMappingCells(Integer mappingID)
		{ return Mappings.getMappingCells(mappingID).toArray(new MappingCell[0]); }

	/** Web service to add the specified mapping cell */
	public Integer addMappingCell(MappingCell mappingCell)
		{ return Mappings.addMappingCell(mappingCell); }

	/** Web service to update the specified mapping cell */
	public Boolean updateMappingCell(MappingCell mappingCell)
		{ return Mappings.updateMappingCell(mappingCell); }
	
	/** Web service to delete the specified mapping cell */
	public Boolean deleteMappingCell(Integer mappingCellID)
		{ return Mappings.deleteMappingCell(mappingCellID); }

	//--------------------
	// Derived Operations
	//--------------------
	
	/** Web service to import schemas */
	public Integer importSchema(Schema schema, SchemaElementList schemaElementList) throws RemoteException
	{
		ArrayList<SchemaElement> schemaElements = new ArrayList<SchemaElement>(Arrays.asList(schemaElementList.getSchemaElements()));
		return ImportSchema.importSchema(this, schema, schemaElements);
	}
	
	/** Web service to save the mapping and mapping cells */
	public Integer saveMapping(Mapping mapping, MappingCell[] mappingCells) throws RemoteException
	{
		ArrayList<MappingCell> mappingCellArray = new ArrayList<MappingCell>();
		if(mappingCells!=null) mappingCellArray = new ArrayList<MappingCell>(Arrays.asList(mappingCells));
		return SaveMapping.saveMapping(mapping,mappingCellArray);
	}
}