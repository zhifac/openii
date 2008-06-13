/**
 * SchemaStore.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.mitre.schemastore.servlet;

public interface SchemaStore extends java.rmi.Remote {
    public int addAttribute(org.mitre.schemastore.model.Attribute attribute) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Attribute getAttribute(int attributeID) throws java.rmi.RemoteException;
    public int addAlias(org.mitre.schemastore.model.Alias alias) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Domain getDomain(int domainID) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Schema getSchema(int schemaID) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Mapping getMapping(int mappingID) throws java.rmi.RemoteException;
    public int addMapping(org.mitre.schemastore.model.Mapping mapping) throws java.rmi.RemoteException;
    public int addGroup(org.mitre.schemastore.model.Group group) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Mapping[] getMappings() throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Group[] getGroups() throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Relationship getRelationship(int relationshipID) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Entity getEntity(int entityID) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Alias getAlias(int aliasID) throws java.rmi.RemoteException;
    public int importSchema(org.mitre.schemastore.model.Schema schema, org.mitre.schemastore.model.SchemaElementList schemaElementList) throws java.rmi.RemoteException;
    public int addSubtype(org.mitre.schemastore.model.Subtype subtype) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.DataSource getDataSource(int dataSourceID) throws java.rmi.RemoteException;
    public int addSchema(org.mitre.schemastore.model.Schema schema) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Schema[] getSchemas() throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Schema extendSchema(int schemaID) throws java.rmi.RemoteException;
    public boolean updateSchema(org.mitre.schemastore.model.Schema schema) throws java.rmi.RemoteException;
    public boolean commitSchema(int schemaID) throws java.rmi.RemoteException;
    public boolean deleteSchema(int schemaID) throws java.rmi.RemoteException;
    public boolean updateGroup(org.mitre.schemastore.model.Group group) throws java.rmi.RemoteException;
    public boolean deleteGroup(int groupID) throws java.rmi.RemoteException;
    public int[] getUnassignedSchemas() throws java.rmi.RemoteException;
    public int[] getGroupSchemas(int groupID) throws java.rmi.RemoteException;
    public int[] getSchemaGroups(int schemaID) throws java.rmi.RemoteException;
    public boolean addGroupToSchema(int schemaID, int groupID) throws java.rmi.RemoteException;
    public boolean removeGroupFromSchema(int schemaID, int groupID) throws java.rmi.RemoteException;
    public int[] getParentSchemas(int schemaID) throws java.rmi.RemoteException;
    public int[] getChildSchemas(int schemaID) throws java.rmi.RemoteException;
    public int[] getAncestorSchemas(int schemaID) throws java.rmi.RemoteException;
    public int[] getDescendantSchemas(int schemaID) throws java.rmi.RemoteException;
    public int[] getAssociatedSchemas(int schemaID) throws java.rmi.RemoteException;
    public int getRootSchema(int schema1ID, int schema2ID) throws java.rmi.RemoteException;
    public int[] getSchemaPath(int rootID, int schemaID) throws java.rmi.RemoteException;
    public boolean setParentSchemas(int schemaID, int[] parentIDs) throws java.rmi.RemoteException;
    public int addEntity(org.mitre.schemastore.model.Entity entity) throws java.rmi.RemoteException;
    public int addDomain(org.mitre.schemastore.model.Domain domain) throws java.rmi.RemoteException;
    public int addDomainValue(org.mitre.schemastore.model.DomainValue domainValue) throws java.rmi.RemoteException;
    public int addRelationship(org.mitre.schemastore.model.Relationship relationship) throws java.rmi.RemoteException;
    public int addContainment(org.mitre.schemastore.model.Containment containment) throws java.rmi.RemoteException;
    public boolean updateEntity(org.mitre.schemastore.model.Entity entity) throws java.rmi.RemoteException;
    public boolean updateAttribute(org.mitre.schemastore.model.Attribute attribute) throws java.rmi.RemoteException;
    public boolean updateDomain(org.mitre.schemastore.model.Domain domain) throws java.rmi.RemoteException;
    public boolean updateDomainValue(org.mitre.schemastore.model.DomainValue domainValue) throws java.rmi.RemoteException;
    public boolean updateRelationship(org.mitre.schemastore.model.Relationship relationship) throws java.rmi.RemoteException;
    public boolean updateContainment(org.mitre.schemastore.model.Containment containment) throws java.rmi.RemoteException;
    public boolean updateSubtype(org.mitre.schemastore.model.Subtype subtype) throws java.rmi.RemoteException;
    public boolean updateAlias(org.mitre.schemastore.model.Alias alias) throws java.rmi.RemoteException;
    public boolean deleteEntity(int entityID) throws java.rmi.RemoteException;
    public boolean deleteAttribute(int attributeID) throws java.rmi.RemoteException;
    public boolean deleteDomain(int domainID) throws java.rmi.RemoteException;
    public boolean deleteDomainValue(int domainValueID) throws java.rmi.RemoteException;
    public boolean deleteRelationship(int relationshipID) throws java.rmi.RemoteException;
    public boolean deleteContainment(int containmentID) throws java.rmi.RemoteException;
    public boolean deleteSubtype(int subtypeID) throws java.rmi.RemoteException;
    public boolean deleteAlias(int aliasID) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.DomainValue getDomainValue(int domainValueID) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Containment getContainment(int containmentID) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.Subtype getSubtype(int subtypeID) throws java.rmi.RemoteException;
    public int getSchemaElementCount(int schemaID) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.SchemaElementList getSchemaElements(int schemaID) throws java.rmi.RemoteException;
    public java.lang.String getSchemaElementType(int schemaElementID) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.DataSource[] getAllDataSources() throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.DataSource[] getDataSources(int schemaID) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.DataSource getDataSourceByURL(java.lang.String url) throws java.rmi.RemoteException;
    public int addDataSource(org.mitre.schemastore.model.DataSource dataSource) throws java.rmi.RemoteException;
    public boolean updateDataSource(org.mitre.schemastore.model.DataSource dataSource) throws java.rmi.RemoteException;
    public boolean deleteDataSource(int dataSourceID) throws java.rmi.RemoteException;
    public boolean updateMapping(org.mitre.schemastore.model.Mapping mapping) throws java.rmi.RemoteException;
    public boolean deleteMapping(int mappingID) throws java.rmi.RemoteException;
    public org.mitre.schemastore.model.MappingCell[] getMappingCells(int mappingID) throws java.rmi.RemoteException;
    public int addMappingCell(org.mitre.schemastore.model.MappingCell mappingCell) throws java.rmi.RemoteException;
    public boolean updateMappingCell(org.mitre.schemastore.model.MappingCell mappingCell) throws java.rmi.RemoteException;
    public boolean deleteMappingCell(int mappingCellID) throws java.rmi.RemoteException;
    public int saveMapping(org.mitre.schemastore.model.Mapping mapping, org.mitre.schemastore.model.MappingCell[] mappingCells) throws java.rmi.RemoteException;
}
