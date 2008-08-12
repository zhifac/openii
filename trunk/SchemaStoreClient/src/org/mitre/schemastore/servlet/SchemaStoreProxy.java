package org.mitre.schemastore.servlet;

public class SchemaStoreProxy implements org.mitre.schemastore.servlet.SchemaStore {
  private String _endpoint = null;
  private org.mitre.schemastore.servlet.SchemaStore schemaStore = null;
  
  public SchemaStoreProxy(String serviceAddress) {
	  _endpoint = serviceAddress;
	  _initSchemaStoreProxy();
  }
  
  private void _initSchemaStoreProxy() {
    try {
      SchemaStoreServiceLocator locator = new SchemaStoreServiceLocator();
      locator.setSchemaStoreEndpointAddress(_endpoint);
      schemaStore = locator.getSchemaStore();
      if (schemaStore != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)schemaStore)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)schemaStore)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {serviceException.printStackTrace();}
  }

  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (schemaStore != null)
      ((javax.xml.rpc.Stub)schemaStore)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.mitre.schemastore.servlet.SchemaStore getSchemaStore() {
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore;
  }
  
  public int addAttribute(org.mitre.schemastore.model.Attribute attribute) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addAttribute(attribute);
  }
  
  public org.mitre.schemastore.model.Attribute getAttribute(int attributeID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getAttribute(attributeID);
  }
  
  public int addAlias(org.mitre.schemastore.model.Alias alias) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addAlias(alias);
  }
  
  public org.mitre.schemastore.model.Domain getDomain(int domainID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getDomain(domainID);
  }
  
  public org.mitre.schemastore.model.Schema getSchema(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getSchema(schemaID);
  }
  
  public org.mitre.schemastore.model.Mapping getMapping(int mappingID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getMapping(mappingID);
  }
  
  public int addMapping(org.mitre.schemastore.model.Mapping mapping) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addMapping(mapping);
  }
  
  public int addGroup(org.mitre.schemastore.model.Group group) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addGroup(group);
  }
  
  public org.mitre.schemastore.model.Mapping[] getMappings() throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getMappings();
  }
  
  public org.mitre.schemastore.model.Group[] getGroups() throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getGroups();
  }
  
  public org.mitre.schemastore.model.Relationship getRelationship(int relationshipID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getRelationship(relationshipID);
  }
  
  public org.mitre.schemastore.model.Entity getEntity(int entityID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getEntity(entityID);
  }
  
  public int addSubtype(org.mitre.schemastore.model.Subtype subtype) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addSubtype(subtype);
  }
  
  public int addSchema(org.mitre.schemastore.model.Schema schema) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addSchema(schema);
  }
  
  public org.mitre.schemastore.model.Schema[] getSchemas() throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getSchemas();
  }
  
  public int importSchema(org.mitre.schemastore.model.Schema schema, org.mitre.schemastore.model.SchemaElementList schemaElementList) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.importSchema(schema, schemaElementList);
  }
  
  public org.mitre.schemastore.model.DataSource getDataSource(int dataSourceID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getDataSource(dataSourceID);
  }
  
  public org.mitre.schemastore.model.Schema extendSchema(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.extendSchema(schemaID);
  }
  
  public boolean updateSchema(org.mitre.schemastore.model.Schema schema) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateSchema(schema);
  }
  
  public boolean unlockSchema(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.unlockSchema(schemaID);
  }
  
  public boolean lockSchema(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.lockSchema(schemaID);
  }
  
  public boolean isDeletable(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.isDeletable(schemaID);
  }
  
  public boolean deleteSchema(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteSchema(schemaID);
  }
  
  public java.lang.String[] getSynonyms(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getSynonyms(schemaID);
  }
  
  public boolean updateGroup(org.mitre.schemastore.model.Group group) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateGroup(group);
  }
  
  public boolean deleteGroup(int groupID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteGroup(groupID);
  }
  
  public int[] getUnassignedSchemas() throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getUnassignedSchemas();
  }
  
  public int[] getGroupSchemas(int groupID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getGroupSchemas(groupID);
  }
  
  public int[] getSchemaGroups(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getSchemaGroups(schemaID);
  }
  
  public boolean addGroupToSchema(int schemaID, int groupID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addGroupToSchema(schemaID, groupID);
  }
  
  public boolean removeGroupFromSchema(int schemaID, int groupID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.removeGroupFromSchema(schemaID, groupID);
  }
  
  public int[] getParentSchemas(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getParentSchemas(schemaID);
  }
  
  public int[] getChildSchemas(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getChildSchemas(schemaID);
  }
  
  public int[] getAncestorSchemas(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getAncestorSchemas(schemaID);
  }
  
  public int[] getDescendantSchemas(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getDescendantSchemas(schemaID);
  }
  
  public int[] getAssociatedSchemas(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getAssociatedSchemas(schemaID);
  }
  
  public int getRootSchema(int schema1ID, int schema2ID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getRootSchema(schema1ID, schema2ID);
  }
  
  public int[] getSchemaPath(int rootID, int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getSchemaPath(rootID, schemaID);
  }
  
  public boolean setParentSchemas(int schemaID, int[] parentIDs) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.setParentSchemas(schemaID, parentIDs);
  }
  
  public int addEntity(org.mitre.schemastore.model.Entity entity) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addEntity(entity);
  }
  
  public int addDomain(org.mitre.schemastore.model.Domain domain) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addDomain(domain);
  }
  
  public int addDomainValue(org.mitre.schemastore.model.DomainValue domainValue) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addDomainValue(domainValue);
  }
  
  public int addRelationship(org.mitre.schemastore.model.Relationship relationship) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addRelationship(relationship);
  }
  
  public int addContainment(org.mitre.schemastore.model.Containment containment) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addContainment(containment);
  }
  
  public boolean updateEntity(org.mitre.schemastore.model.Entity entity) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateEntity(entity);
  }
  
  public boolean updateAttribute(org.mitre.schemastore.model.Attribute attribute) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateAttribute(attribute);
  }
  
  public boolean updateDomain(org.mitre.schemastore.model.Domain domain) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateDomain(domain);
  }
  
  public boolean updateDomainValue(org.mitre.schemastore.model.DomainValue domainValue) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateDomainValue(domainValue);
  }
  
  public boolean updateRelationship(org.mitre.schemastore.model.Relationship relationship) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateRelationship(relationship);
  }
  
  public boolean updateContainment(org.mitre.schemastore.model.Containment containment) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateContainment(containment);
  }
  
  public boolean updateSubtype(org.mitre.schemastore.model.Subtype subtype) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateSubtype(subtype);
  }
  
  public boolean updateAlias(org.mitre.schemastore.model.Alias alias) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateAlias(alias);
  }
  
  public boolean deleteEntity(int entityID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteEntity(entityID);
  }
  
  public boolean deleteAttribute(int attributeID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteAttribute(attributeID);
  }
  
  public boolean deleteDomain(int domainID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteDomain(domainID);
  }
  
  public boolean deleteDomainValue(int domainValueID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteDomainValue(domainValueID);
  }
  
  public boolean deleteRelationship(int relationshipID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteRelationship(relationshipID);
  }
  
  public boolean deleteContainment(int containmentID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteContainment(containmentID);
  }
  
  public boolean deleteSubtype(int subtypeID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteSubtype(subtypeID);
  }
  
  public boolean deleteAlias(int aliasID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteAlias(aliasID);
  }
  
  public org.mitre.schemastore.model.DomainValue getDomainValue(int domainValueID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getDomainValue(domainValueID);
  }
  
  public org.mitre.schemastore.model.Containment getContainment(int containmentID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getContainment(containmentID);
  }
  
  public org.mitre.schemastore.model.Subtype getSubtype(int subtypeID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getSubtype(subtypeID);
  }
  
  public org.mitre.schemastore.model.Alias getAlias(int aliasID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getAlias(aliasID);
  }
  
  public int getSchemaElementCount(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getSchemaElementCount(schemaID);
  }
  
  public org.mitre.schemastore.model.SchemaElementList getSchemaElements(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getSchemaElements(schemaID);
  }
  
  public java.lang.String getSchemaElementType(int schemaElementID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getSchemaElementType(schemaElementID);
  }
  
  public org.mitre.schemastore.model.DataSource[] getAllDataSources() throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getAllDataSources();
  }
  
  public org.mitre.schemastore.model.DataSource[] getDataSources(int schemaID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getDataSources(schemaID);
  }
  
  public org.mitre.schemastore.model.DataSource getDataSourceByURL(java.lang.String url) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getDataSourceByURL(url);
  }
  
  public int addDataSource(org.mitre.schemastore.model.DataSource dataSource) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addDataSource(dataSource);
  }
  
  public boolean updateDataSource(org.mitre.schemastore.model.DataSource dataSource) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateDataSource(dataSource);
  }
  
  public boolean deleteDataSource(int dataSourceID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteDataSource(dataSourceID);
  }
  
  public boolean updateMapping(org.mitre.schemastore.model.Mapping mapping) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateMapping(mapping);
  }
  
  public boolean deleteMapping(int mappingID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteMapping(mappingID);
  }
  
  public org.mitre.schemastore.model.MappingCell[] getMappingCells(int mappingID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.getMappingCells(mappingID);
  }
  
  public int addMappingCell(org.mitre.schemastore.model.MappingCell mappingCell) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.addMappingCell(mappingCell);
  }
  
  public boolean updateMappingCell(org.mitre.schemastore.model.MappingCell mappingCell) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.updateMappingCell(mappingCell);
  }
  
  public boolean deleteMappingCell(int mappingCellID) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.deleteMappingCell(mappingCellID);
  }
  
  public int saveMapping(org.mitre.schemastore.model.Mapping mapping, org.mitre.schemastore.model.MappingCell[] mappingCells) throws java.rmi.RemoteException{
    if (schemaStore == null)
      _initSchemaStoreProxy();
    return schemaStore.saveMapping(mapping, mappingCells);
  }
  
  
}