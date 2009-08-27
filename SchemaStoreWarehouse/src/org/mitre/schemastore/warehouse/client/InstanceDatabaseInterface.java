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
	 * 6.  For every cellType detected as NUMERIC by the POI API, the importer gives the domain name
	 *     "REAL" to the Attribute object.  The domain name "INTEGER" does not seem to be used. 
	 *     
	 * 7.  The importer, as it is set up now, stores the domain name "REAL" for the following data types:
	 *     { integer, numeric, decimal, real } which are all detected as NUMERIC by POI API.  
	 *     
	 * 8.  If the type of data found in a cell is not the same as the domain type stored in the Attribute
	 *     object, a null is inserted in its place.
	 *     
	 * 9. If no value is found in a cell, NULL is inserted into the table.  
	 *     
	 * 11. The maximum length of the string that can be stored in the database is 30 - for Derby database
	 *  
	 * 12. Numeric values can be stored in the database with a max. precision of 30 and scale of 10
	 *      - for Derby database
	 *      
	 * 13. There is no need to close the OpenII window to access the database using some other tool.     
	 * @throws NoDataFoundException, RemoteException 
	 */   
	public void createInstanceDatabaseTables() throws NoDataFoundException, RemoteException;
	
	public void createEntityTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	public void populateEntityTable(Entity entity, Integer numberOfRows) throws NoDataFoundException, RemoteException;
	
	public void createAttributeTable(Attribute attribute, String sqlType, Entity entity) throws NoDataFoundException, RemoteException;
	
	public void populateNumericAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException;
	
	public void populateStringAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException;
	
	public void populateBooleanAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException;
	
	public void populateDateAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException;
	
	public void createContainmentTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	public void populateContainmentTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	public void createRelationshipTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	public void populateRelationshipTable(Entity entity) throws NoDataFoundException, RemoteException;
	
	public void createInstanceDatabaseViews(ViewDTOArray v) throws NoDataFoundException, RemoteException;
	
	public void releaseResources() throws RemoteException;
	
	public void rollback() throws NoDataFoundException;
	
	/** Get the list of warnings generated when data is read	
	 *  returns null if no such list has been created */
	public List<DataWarning> getListOfDataWarning();
}
