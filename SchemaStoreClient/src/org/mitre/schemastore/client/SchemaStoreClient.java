// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

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
import org.mitre.schemastore.servlet.SchemaStoreProxy;

/**
 * Handles all communications to the schema store web service
 * @author CWOLF
 */
public class SchemaStoreClient
{
	/** Stores the location of the web service */
	private SchemaStoreProxy proxy;

	/** Constructor for the Schema Store Client */
	public SchemaStoreClient(String serviceAddress)
		{ proxy  = new SchemaStoreProxy(serviceAddress); }
	
	//------------------
	// Schema Functions
	//------------------
	
	/** Gets the list of schemas from the web service */
	public ArrayList<Schema> getSchemas() throws RemoteException
	{
		Schema[] schemas = proxy.getSchemas();
		return schemas==null ? new ArrayList<Schema>() : new ArrayList<Schema>(Arrays.asList(schemas));
	}
	
	/** Gets the specified schema from the web service */
	public Schema getSchema(Integer schemaID) throws RemoteException
		{ return proxy.getSchema(schemaID); }
	
	/** Adds the specified schema to the web service */
	public Integer addSchema(Schema schema) throws RemoteException
	{
		Integer schemaID = proxy.addSchema(schema);
		return schemaID==0 ? null : schemaID;
	}
	
	/** Extends the specified schema in the web service */
	public Schema extendSchema(Integer schemaID) throws RemoteException
		{ return proxy.extendSchema(schemaID); }
	
	/** Updates the specified schema in the web service */
	public boolean updateSchema(Schema schema) throws RemoteException
		{ return proxy.updateSchema(schema); }
	
	/** Unlocks the specified schema in the web service */
	public boolean unlockSchema(Integer schemaID) throws RemoteException
		{ return proxy.unlockSchema(schemaID); }
	
	/** Locks the specified schema in the web service */
	public boolean lockSchema(Integer schemaID) throws RemoteException
		{ return proxy.lockSchema(schemaID); }
	
	/** Delete the specified schema from the web service */
	public boolean deleteSchema(Integer schemaID) throws RemoteException
		{ return proxy.deleteSchema(schemaID); }
	
	//------------------------
	// Schema Group Functions
	//------------------------
	
	/** Get the list of schema groups from the web service */
	public ArrayList<Group> getGroups() throws RemoteException
	{
		Group[] groups = proxy.getGroups();
		return groups==null ? new ArrayList<Group>() : new ArrayList<Group>(Arrays.asList(groups));
	}

	/** Add a group to the web service */
	public Integer addGroup(Group group) throws RemoteException
	{
		Integer groupID = proxy.addGroup(group);
		return groupID==0 ? null : groupID;
	}
	
	/** Update a group in the web service */
	public Boolean updateGroup(Group group) throws RemoteException
		{ return proxy.updateGroup(group); }
	
	/** Delete a group from the web service */
	public Boolean deleteGroup(Integer groupID) throws RemoteException
		{ return proxy.deleteGroup(groupID); }
	
	/** Get list of schemas unassigned with a group in web service */
	public ArrayList<Integer> getUnassignedSchemas() throws RemoteException
	{
		ArrayList<Integer> unassignedGroupSchemas = new ArrayList<Integer>();
		int[] unassignedSchemaArray = proxy.getUnassignedSchemas();
		if(unassignedSchemaArray!=null)
			for(Integer unassignedGroupSchema : unassignedSchemaArray)
				unassignedGroupSchemas.add(unassignedGroupSchema);
		return unassignedGroupSchemas;
	}
	
	/** Get list of schemas associated with group in web service */
	public ArrayList<Integer> getGroupSchemas(Integer groupID) throws RemoteException
	{
		ArrayList<Integer> groupSchemas = new ArrayList<Integer>();
		int[] groupSchemaArray = proxy.getGroupSchemas(groupID);
		if(groupSchemaArray!=null)
			for(Integer groupSchema : groupSchemaArray)
				groupSchemas.add(groupSchema);
		return groupSchemas;
	}
	
	/** Get list of groups associated with schema in the web service */
	public ArrayList<Integer> getSchemaGroups(Integer schemaID) throws RemoteException
	{
		ArrayList<Integer> schemaGroups = new ArrayList<Integer>();
		int[] schemaGroupArray = proxy.getSchemaGroups(schemaID);
		if(schemaGroupArray!=null)
			for(Integer schemaGroup : schemaGroupArray)
				schemaGroups.add(schemaGroup);
		return schemaGroups;
	}
		
	/** Add a group to a schema in the web service */
	public Boolean addGroupToSchema(Integer schemaID, Integer groupID) throws RemoteException
		{ return proxy.addGroupToSchema(schemaID, groupID); }
	
	/** Remove a group from a schema in the web service */
	public Boolean removeGroupFromSchema(Integer schemaID, Integer groupID) throws RemoteException
		{ return proxy.removeGroupFromSchema(schemaID, groupID); }
	
	//-------------------------------
	// Schema Relationship Functions
	//-------------------------------
	
	/** Gets the list of parent schemas for the specified schema from the web service */
	public ArrayList<Integer> getParentSchemas(Integer schemaID) throws RemoteException
	{
		ArrayList<Integer> parentSchemas = new ArrayList<Integer>();
		int[] parentSchemaArray = proxy.getParentSchemas(schemaID);
		if(parentSchemaArray!=null)
			for(Integer parentSchema : parentSchemaArray)
				parentSchemas.add(parentSchema);
		return parentSchemas;
	}
		
	/** Gets the list of child schemas for the specified schema from the web service */
	public ArrayList<Integer> getChildSchemas(Integer schemaID) throws RemoteException
	{
		ArrayList<Integer> childSchemas = new ArrayList<Integer>();
		int[] childSchemaArray = proxy.getChildSchemas(schemaID);
		if(childSchemaArray!=null)
			for(Integer childSchema : childSchemaArray)
				childSchemas.add(childSchema);
		return childSchemas;
	}
	
	/** Gets the list of ancestor schemas for the specified schema from the web service */
	public ArrayList<Integer> getAncestorSchemas(Integer schemaID) throws RemoteException
	{
		ArrayList<Integer> ancestorSchemas = new ArrayList<Integer>();
		int[] ancestorSchemaArray = proxy.getAncestorSchemas(schemaID);
		if(ancestorSchemaArray!=null)
			for(Integer ancestorSchema : ancestorSchemaArray)
				ancestorSchemas.add(ancestorSchema);
		return ancestorSchemas;
	}
	
	/** Gets the list of descendant schemas for the specified schema from the web service */
	public ArrayList<Integer> getDescendantSchemas(Integer schemaID) throws RemoteException
	{
		ArrayList<Integer> decendantSchemas = new ArrayList<Integer>();
		int[] decendantSchemaArray = proxy.getDescendantSchemas(schemaID);
		if(decendantSchemaArray!=null)
			for(Integer decendantSchema : decendantSchemaArray)
				decendantSchemas.add(decendantSchema);
		return decendantSchemas;
	}
	
	/** Gets the list of schemas associated with the specified schema from the web service */
	public ArrayList<Integer> getAssociatedSchemas(Integer schemaID) throws RemoteException
	{
		ArrayList<Integer> associatedSchemas = new ArrayList<Integer>();
		int[] associatedSchemaArray = proxy.getAssociatedSchemas(schemaID);
		if(associatedSchemaArray!=null)
			for(Integer associatedSchema : associatedSchemaArray)
				associatedSchemas.add(associatedSchema);
		return associatedSchemas;
	}
	
	/** Gets the root schema for the two specified schemas from the web service */
	public Integer getRootSchema(Integer schema1ID, Integer schema2ID) throws RemoteException
		{ return proxy.getRootSchema(schema1ID, schema2ID); }
	
	/** Gets the schema path between the specified root and schema from the web service */
	public ArrayList<Integer> getSchemaPath(Integer rootID, Integer schemaID) throws RemoteException
	{
		ArrayList<Integer> schemaPath = new ArrayList<Integer>();
		int[] schemaPathArray = proxy.getSchemaPath(rootID, schemaID);
		if(schemaPathArray!=null)
			for(Integer schemaPathItem : schemaPathArray)
				schemaPath.add(schemaPathItem);
		return schemaPath;
	}
	
	/** Sets the parent schemas for the specified schema from the web service */
	public boolean setParentSchemas(Integer schemaID, ArrayList<Integer> parentIDs) throws RemoteException
	{
		int[] parentIDArray = new int[parentIDs.size()];
		for(int i=0; i<parentIDs.size(); i++)
			parentIDArray[i] = parentIDs.get(i);
		return proxy.setParentSchemas(schemaID, parentIDArray);
	}
	
	//-------------------------
	// Schema Object Functions
	//-------------------------

	/** Adds the specified schema element to the web service */
	public Integer addSchemaElement(SchemaElement schemaElement) throws RemoteException
	{
		Integer schemaElementID = 0;
		if(schemaElement instanceof Entity) schemaElementID = proxy.addEntity((Entity)schemaElement);
		else if(schemaElement instanceof Attribute) schemaElementID = proxy.addAttribute((Attribute)schemaElement);
		else if(schemaElement instanceof Domain) schemaElementID = proxy.addDomain((Domain)schemaElement);
		else if(schemaElement instanceof DomainValue) schemaElementID = proxy.addDomainValue((DomainValue)schemaElement);
		else if(schemaElement instanceof Relationship) schemaElementID = proxy.addRelationship((Relationship)schemaElement);
		else if(schemaElement instanceof Containment) schemaElementID = proxy.addContainment((Containment)schemaElement);
		else if(schemaElement instanceof Subtype) schemaElementID = proxy.addSubtype((Subtype)schemaElement);
		else if(schemaElement instanceof Alias) schemaElementID = proxy.addAlias((Alias)schemaElement);
		return schemaElementID==0 ? null : schemaElementID;
	}
	
	/** Updates the specified schema element on the web service */
	public Boolean updateSchemaElement(SchemaElement schemaElement) throws RemoteException
	{
		if(schemaElement instanceof Entity) return proxy.updateEntity((Entity)schemaElement);
		else if(schemaElement instanceof Attribute) return proxy.updateAttribute((Attribute)schemaElement);
		else if(schemaElement instanceof Domain) return proxy.updateDomain((Domain)schemaElement);
		else if(schemaElement instanceof DomainValue) return proxy.updateDomainValue((DomainValue)schemaElement);
		else if(schemaElement instanceof Relationship) return proxy.updateRelationship((Relationship)schemaElement);
		else if(schemaElement instanceof Containment) return proxy.updateContainment((Containment)schemaElement);
		else if(schemaElement instanceof Subtype) return proxy.updateSubtype((Subtype)schemaElement);
		else if(schemaElement instanceof Alias) return proxy.updateAlias((Alias)schemaElement);
		return null;
	}

	/** Deletes the specified schema element from the web service */
	public Boolean deleteSchemaElement(Integer schemaElementID) throws RemoteException
	{
		String type = proxy.getSchemaElementType(schemaElementID);
		if(type.equals("Entity")) return proxy.deleteEntity(schemaElementID);
		else if(type.equals("Attribute")) return proxy.deleteAttribute(schemaElementID);
		else if(type.equals("Domain")) return proxy.deleteDomain(schemaElementID);
		else if(type.equals("DomainValue")) return proxy.deleteDomainValue(schemaElementID);
		else if(type.equals("Relationship")) return proxy.deleteRelationship(schemaElementID);
		else if(type.equals("Containment")) return proxy.deleteContainment(schemaElementID);
		else if(type.equals("Subtype")) return proxy.deleteSubtype(schemaElementID);
		else if(type.equals("Alias")) return proxy.deleteAlias(schemaElementID);
		return null;
	}

	/** Retrieves the specified schema element from the web service */
	public SchemaElement getSchemaElement(Integer schemaElementID) throws RemoteException
	{
		String type = proxy.getSchemaElementType(schemaElementID);
		if(type.equals("Entity")) return proxy.getEntity(schemaElementID);
		else if(type.equals("Attribute")) return proxy.getAttribute(schemaElementID);
		else if(type.equals("Domain")) return proxy.getDomain(schemaElementID);
		else if(type.equals("DomainValue")) return proxy.getDomainValue(schemaElementID);
		else if(type.equals("Relationship")) return proxy.getRelationship(schemaElementID);
		else if(type.equals("Containment")) return proxy.getContainment(schemaElementID);
		else if(type.equals("Subtype")) return proxy.getSubtype(schemaElementID);
		else if(type.equals("Alias")) return proxy.getAlias(schemaElementID);
		return null;
	}

	/** Retrieves the number of schema elements for the specified schema from the web service */
	public Integer getSchemaElementCount(Integer schemaID) throws RemoteException
		{ return proxy.getSchemaElementCount(schemaID); }
	
	/** Retrieves the schema elements for the specified schema from the web service */
	public ArrayList<SchemaElement> getSchemaElements(Integer schemaID) throws RemoteException
	{
		SchemaElement[] schemaElements = proxy.getSchemaElements(schemaID).getSchemaElements();
		return schemaElements==null ? new ArrayList<SchemaElement>() : new ArrayList<SchemaElement>(Arrays.asList(schemaElements));
	}
	
	//-----------------------
	// Data Source Functions
	//-----------------------

	/** Gets the list of data sources from the web service */
	public ArrayList<DataSource> getDataSources(Integer schemaID) throws RemoteException
	{
		DataSource[] dataSources = (schemaID==null ? proxy.getAllDataSources() : proxy.getDataSources(schemaID));
		return dataSources==null ? new ArrayList<DataSource>() : new ArrayList<DataSource>(Arrays.asList(dataSources));
	}
	
	/** Gets the specified data source from the web service */ 
	public DataSource getDataSource(Integer dataSourceID) throws RemoteException
		{ return proxy.getDataSource(dataSourceID); }
	
	/** Gets the specified data source based on the specified url from the web service */
	public DataSource getDataSourceByURL(String url) throws RemoteException
		{ return proxy.getDataSourceByURL(url); }
	
	/** Adds the specified data source to the web service */
	public Integer addDataSource(DataSource dataSource) throws RemoteException
	{
		Integer dataSourceID = proxy.addDataSource(dataSource);
		return dataSourceID==0 ? null : dataSourceID;
	}
		
	/** Updates the specified data source in the web service */
	public boolean updateDataSource(DataSource dataSource) throws RemoteException
		{ return proxy.updateDataSource(dataSource); }
	
	/** Deletes the specified data source from the web service */
	public boolean deleteDataSource(Integer dataSourceID) throws RemoteException
		{ return proxy.deleteDataSource(dataSourceID); }
	
	//-------------------
	// Mapping Functions
	//-------------------

	/** Gets the list of mappings from the web service */
	public ArrayList<Mapping> getMappings() throws RemoteException
	{
		Mapping[] mappings = proxy.getMappings();
		return mappings==null ? new ArrayList<Mapping>() : new ArrayList<Mapping>(Arrays.asList(mappings));
	}
	
	/** Gets the specified mapping from the web service */
	public Mapping getMapping(Integer mappingID) throws RemoteException
		{ return proxy.getMapping(mappingID); }
	
	/** Adds the specified mapping to the web service */
	public Integer addMapping(Mapping mapping) throws RemoteException
	{
		Integer mappingID = proxy.addMapping(mapping);
		return mappingID==0 ? null : mappingID;
	}
		
	/** Updates the specified mapping in the web service */
	public boolean updateMapping(Mapping mapping) throws RemoteException
		{ return proxy.updateMapping(mapping); }
	
	/** Deletes the specified mapping from the web service */
	public boolean deleteMapping(Integer mappingID) throws RemoteException
		{ return proxy.deleteMapping(mappingID); }

	/** Gets the list of mapping cells for the specified mapping from the web service */
	public ArrayList<MappingCell> getMappingCells(Integer mappingID) throws RemoteException
	{
		MappingCell[] mappingCells = proxy.getMappingCells(mappingID);
		return mappingCells==null ? new ArrayList<MappingCell>() : new ArrayList<MappingCell>(Arrays.asList(mappingCells));
	}
	
	/** Adds the specified mapping cell to the web service */
	public Integer addMappingCell(MappingCell mappingCell) throws RemoteException
	{
		Integer mappingCellID = proxy.addMappingCell(mappingCell);
		return mappingCellID==0 ? null : mappingCellID;
	}
		
	/** Updates the specified mapping cell in the web service */
	public boolean updateMappingCell(MappingCell mappingCell) throws RemoteException
		{ return proxy.updateMappingCell(mappingCell); }
	
	/** Deletes the specified mapping from the web service */
	public boolean deleteMappingCell(Integer mappingCellID) throws RemoteException
		{ return proxy.deleteMappingCell(mappingCellID); }

	//-------------------
	// Derived Functions
	//-------------------
	
	/** Imports the specified schema into the web services */
	public Integer importSchema(Schema schema, ArrayList<SchemaElement> schemaElements) throws RemoteException
		{ return proxy.importSchema(schema, new SchemaElementList(schemaElements.toArray(new SchemaElement[0]))); }
		
	/** Saves the mapping and mapping cells to the web service */
	public Integer saveMapping(Mapping mapping, ArrayList<MappingCell> mappingCells) throws RemoteException
	{
		Integer mappingID = proxy.saveMapping(mapping,mappingCells.toArray(new MappingCell[0]));
		return mappingID==0 ? null : mappingID;
	}
}