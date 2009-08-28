package org.mitre.schemastore.warehouse.client;

import java.rmi.RemoteException;
import java.util.List;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.warehouse.common.DataWarning;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;
import org.mitre.schemastore.warehouse.common.ViewDTOArray;

public interface InstanceDatabaseInterface 
{
	/** Create and populate all instance tables for the excel file that this client refers to 
	 * @throws NoDataFoundException, RemoteException */
	public void createInstanceDatabaseTables() throws NoDataFoundException, RemoteException;
	
	/** Create table for given Entity 
	 * @throws NoDataFoundException, RemoteException */
	public void createEntityTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	/** Populate table for given Entity 
	 * @throws NoDataFoundException, RemoteException */
	public void populateEntityTable(Entity entity, Integer numberOfRows) throws NoDataFoundException, RemoteException;
	
	/** Create table for given Attribute 
	 * @throws NoDataFoundException, RemoteException */
	public void createAttributeTable(Attribute attribute, String sqlType, Entity entity) throws NoDataFoundException, RemoteException;
	
	/** Populate table for given Attribute with Numeric data 
	 * @throws NoDataFoundException, RemoteException */
	public void populateNumericAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException;
	
	/** Populate table for given Attribute with String data 
	 * @throws NoDataFoundException, RemoteException */
	public void populateStringAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException;
	
	/** Populate table for given Attribute with Boolean data 
	 * @throws NoDataFoundException, RemoteException */
	public void populateBooleanAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException;
	
	/** Populate table for given Attribute with Date data 
	 * @throws NoDataFoundException, RemoteException */
	public void populateDateAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException;
	
	/** Create table for a Containment 
	 * @throws NoDataFoundException, RemoteException */
	public void createContainmentTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	/** Populate table for a Containment 
	 * @throws NoDataFoundException, RemoteException */
	public void populateContainmentTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	/** Create table for a Relationship 
	 * @throws NoDataFoundException, RemoteException */
	public void createRelationshipTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	/** Populate table for a Relationship 
	 * @throws NoDataFoundException, RemoteException */
	public void populateRelationshipTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	/** Create views representing each of the sheets in the given excel file 
	 * @throws NoDataFoundException, RemoteException */
	public void createInstanceDatabaseViews(ViewDTOArray v) throws NoDataFoundException, RemoteException;
	
	/** Release resources 
	 * @throws RemoteException */
	public void releaseResources() throws RemoteException;
	
	/** Undo all changes made in the current transaction 
	 *  @throws NoDataFoundException */
	public void rollback() throws NoDataFoundException;
	
	/** Get the list of warnings generated when data is read	
	 *  returns null if no such list has been created */
	public List<DataWarning> getListOfDataWarning();
}
