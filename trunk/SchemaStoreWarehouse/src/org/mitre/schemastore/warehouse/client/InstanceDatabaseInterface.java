package org.mitre.schemastore.warehouse.client;

import java.rmi.RemoteException;

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;

public interface InstanceDatabaseInterface 
{
	/**
	 * This method creates instance database tables
	 * @throws NoDataFoundException
	 * 
	 * This method assumes the following:
	 * 1.  The file is stored on the same machine as the place from where OpenII application is being run.
	 * 
	 * 2.  The first row of a spreadsheet is the title and second row second onwards contain the data.
	 * 
	 * 3.  Each sheet name in the spreadsheet is unique - only then would the Entity names in the schema 
	 *     be unique - this is important because entity is searched based on Entity name.
	 *     
	 * 4.  Each cell in the first (title) row has a unique value within the sheet - this is important
	 *     because attributes are searched based on these values.
	 *     
	 * 5.  Each cell in the first (title) row has a String value.  For cases where a cell of the first row
	 *     is blank, or its value is of any other type, the entire column under it is ignored 
	 *     i.e. data in that column is not stored in the database.
	 * 
	 * 6.  Excel treats dates as numeric.  The POI API only detects the following cell types:
	 *     { NUMERIC, STRING, FORMULA, BLANK, BOOLEAN, ERROR }.  The importer creates the domain type
	 *     "DATETIME", but, this type is not loaded into any Attribute object created because
	 *     the POI API does not detect "dates" separately from numbers.
	 *     
	 * 7.  For every cellType detected as NUMERIC by the POI API, the importer gives the domain name
	 *     "REAL" to the Attribute object.  The domain name "INTEGER" does not seem to be used. 
	 *     
	 * 8.  The importer, as it is set up now, stores the domain name "REAL" for the following data types:
	 *     { integer, numeric, decimal, real, date, time } which are all detected as NUMERIC by POI API.  
	 *     
	 * 9.  If the type of data found in a cell is not the same as the domain type stored in the Attribute
	 *     object, an exception is thrown -- is this valid since we are allowing creation of instance 
	 *     tables for not just the .xls from which the entity was originally created but also we are allowing
	 *     the user to select any .xls file.
	 *     
	 * 10. If no value is found in a cell, NULL is inserted into the table.  The only SQL data types used
	 *     to insert values into the table are "numeric", "varchar", "boolean".
	 *     
	 * 11. The maximum length of the string that can be stored in the database is 30 - can be changed by 
	 *  	changes in the code
	 *  
	 * 12. Numeric values can be stored in the database with a max. precision of 30 and scale of 10
	 *      - can be changed by changes in the code
	 *      
	 * 13. There is no need to close the OpenII window to access the database using some other tool.     
	 * @throws RemoteException 
	 */   
	public void createInstanceDatabaseTables() throws NoDataFoundException, RemoteException;
	
	public void createEntityTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	public void populateEntityTable(Entity entity, Integer numberOfRows) throws NoDataFoundException, RemoteException;
	
	public void createAttributeTable(Attribute attribute, String sqlType, Entity entity) throws NoDataFoundException, RemoteException;
	
	public void populateNumericAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException;
	
	public void populateStringAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException;
	
	public void populateBooleanAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException;
	
	public void createContainmentTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	public void populateContainmentTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	public void createRelationshipTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	public void populateRelationshipTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	public void releaseResources() throws RemoteException;
}
