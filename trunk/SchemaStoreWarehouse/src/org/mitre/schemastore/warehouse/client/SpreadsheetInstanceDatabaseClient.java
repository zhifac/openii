package org.mitre.schemastore.warehouse.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.mitre.schemastore.client.Repository;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;
import org.mitre.schemastore.warehouse.common.DataWarning;
import org.mitre.schemastore.warehouse.common.InstanceRepository;
import org.mitre.schemastore.warehouse.common.NoDataFoundException;
import org.mitre.schemastore.warehouse.common.ViewDTO;
import org.mitre.schemastore.warehouse.common.ViewDTOArray;
import org.mitre.schemastore.warehouse.servlet.InstanceDatabase;

/**
 * Handles all communications to the SchemaStoreClient and to the instance database
 * @author STANDON
 *
 */
public class SpreadsheetInstanceDatabaseClient implements InstanceDatabaseInterface
{
	/** Instance variables */
	private Schema schema = null;
	private HSSFWorkbook wb = null;
	private SchemaStoreClient schemastoreClient = null;
	private String userSpecifiedName = null;
	private Repository currentRepository = null;
	private SchemaInfo schemaInfo = null;
	private InstanceRepository instanceRepository = null;
	private Object instanceDatabase = null;
	private boolean createNewDatabase = false;
	private Integer newDataSourceId = null;
	
	/** Constants used for naming instance tables */
	private static final String ENTITY = "E";
	private static final String ATTRIBUTE = "A";
	private static final String CONTAINMENT = "C";
	private static final String RELATIONSHIP = "R";
	
	/** Constant used to identify a cell of type date/time */
	private static final int CELL_TYPE_DATETIME = 1024;
	
	/** List of warnings generated when data is read from the .xls file	*/
	List<DataWarning> listOfDataWarning = null;
	
	/** Constructor 
	 * @throws NoDataFoundException 
	 * @throws RemoteException
	 */
	public SpreadsheetInstanceDatabaseClient(File clientFile, Schema schema, SchemaStoreClient schemastoreClient, String userSpecifiedName, Repository currentRepository) 
													throws NoDataFoundException, RemoteException
	{
		System.out.println("Spreadsheet Instance Database Client initiated");
		
		/* Allocate references to the instance variables */
		
		/* Get a handle on the excel workbook selected by the client */
		System.out.println("File selected by client: " + clientFile.getPath());
		try
		{
			// Create an open InputStream for a file-based system
			InputStream inputStream = new FileInputStream(clientFile);
			
			// Get a handle on the excel file (workbook)
			wb = new HSSFWorkbook(inputStream);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace(System.out);
			throw new NoDataFoundException("A file with the specified pathname either does not exist or it exists and is inaccessible.");
		}
		catch (IOException e)
		{
			e.printStackTrace(System.out);
			throw new NoDataFoundException("An I/O error occurred, or the InputStream did not provide a compatible POIFS data structure.");
		}		
		
		/* Store reference to the schema selected by the user */
		this.schema = schema;
		System.out.println("Schema selected by client: " + schema.getName());
		
		/* Store reference to the SchemaStoreClient - talks to the structural database storing the M3 model */
		this.schemastoreClient = schemastoreClient;
		System.out.println("SchemaStoreClient selected by client: " + schemastoreClient.toString());
		
		/* Get the schema info */
		try 
		{
			this.schemaInfo = schemastoreClient.getSchemaInfo(schema.getId());
		} 
		catch (RemoteException e) 
		{
			System.out.println("Problem occured while getting the SchemaInfo object from the client");
			e.printStackTrace(System.out);
		}
		
		/*	Instantiate the class for creating a new database in the same place where the structural database is located
		 *  It is here that database details are passed from the client to the server
		 */
		// First, the repository that SchemaStoreClient is using is detected
		this.currentRepository = currentRepository;
		
		// Second, the relevant information needed for creating the new instance database is extracted
		this.userSpecifiedName = userSpecifiedName;
		instanceRepository = getInstanceRepository(currentRepository);
		
		if(currentRepository.getType().equals(Repository.SERVICE))
		{
			// Connect to web service - client accesses the server remotely
			//instanceDatabase = new InstanceDatabaseProxy("http://localhost:8080/SchemaStoreWarehouse/services/InstanceDatabase");
		}
		else
		{
			// Client and server are in the same place
			// Instantiate the InstanceDatabase class
			instanceDatabase = new InstanceDatabase(instanceRepository);
			/*
			Class<?> types[] = new Class[] {InstanceRepository.class};
			Object args[] = new Object[] {instanceRepository};
			Constructor<?> constructor;
			try 
			{
				constructor = InstanceDatabase.class.getConstructor(types);
				instanceDatabase = constructor.newInstance(args);
			} 
			catch (Exception e) 
			{
				e.printStackTrace(System.out);
				throw new RemoteException("***Failed to connect to InstanceDatabase***" + e.getMessage());
			} 
			*/
		}
	}//end of constructor
	
	
	/** Method to create and populate all instance tables for the excel file that this client refers to 
	 * @throws NoDataFoundException, RemoteException */
	public void createInstanceDatabaseTables() throws NoDataFoundException, RemoteException
	{
		/* Check if connection is available */
		Boolean isConnected = null;
		/*
		try 
		{
			isConnected = (Boolean) callMethod("isNewDbConnected", new Object[] {});*/
			isConnected = ((InstanceDatabase) instanceDatabase).isNewDbConnected();
			/*
		} 
		catch (RemoteException e) 
		{
			e.printStackTrace();
			throw new RemoteException("Unable to access the remote server");
		}
		*/
		
		/*if(!(((InstanceDatabase) instanceDatabase).isNewDbConnected()))*/
		if(!isConnected)
		{
			// Undo all changes made to structural repository
			deleteDataSourceIfNeeded();
			
			// Since client is deciding to throw an exception -
			// need to tell server to roll back all changes made to database
			rollback();
			
			// Throw an exception and terminate this process
			throw new NoDataFoundException("Connection to instance database not available...Try Again!!!");
		}
		
		// Create a list of warnings generated when data is read from the .xls file
		listOfDataWarning = new ArrayList<DataWarning>();
		
		// Create a list of transfer objects necessary for creating views representing each sheet in the .xls file
		List<ViewDTO> listOfViewDTO = new ArrayList<ViewDTO>();
		
		// Get the database-specific SQL data types used for creating tables and inserting data
		// Get all types in the following order - Numeric, String, Boolean, Date, Null
		String[] arrayOfTypes = ((InstanceDatabase) instanceDatabase).getAllTypes();
		String TYPE_NUMERIC = arrayOfTypes[0];
		String TYPE_STRING = arrayOfTypes[1];
		String TYPE_BOOLEAN = arrayOfTypes[2];
		String TYPE_DATE = arrayOfTypes[3];
		String TYPE_NULL = arrayOfTypes[4];
		
		/* Iterate through each sheet in the excel file */ 
		for(int i=0; i<wb.getNumberOfSheets(); i++)
		{
			System.out.println("-----------------------------------");
			System.out.println("Creating instance data for Sheet " + (i+1));
			System.out.println("-----------------------------------");
			
			/* Create instance table corresponding to this sheet */
			
			// 1.	Get the Entity that this sheet is associated with
			Entity entity;
			try 
			{
				entity = getEntityCorrespondingToSheetIndex(i);
			} 
			catch (NoDataFoundException e) 
			{
				// Corresponding Entity could not be found - log it
				e.printStackTrace(System.err);
				
				// Undo all changes made to structural repository
				deleteDataSourceIfNeeded();
				
				// Since client is deciding to throw an exception -
				// need to tell server to roll back all changes made to database
				rollback();
				
				// Propagate the exception and terminate this process
				throw e;
			}
			
			System.out.println("Entity ID= " + entity.getId() + " Entity name: " + entity.getName() + " Entity description: " + entity.getDescription());
			
			// 2.	Create instance table corresponding to this entity
			if(createNewDatabase)
			{
				try 
				{
					createEntityTable(entity);
				} 
				catch (NoDataFoundException e) 
				{
					// Entity table could not be created - log it
					e.printStackTrace(System.err);
					
					// Undo all changes made to structural repository
					deleteDataSourceIfNeeded();
					
					// This exception has come from the server - 
					// Changes to instance repository have been rolled back by the server
					
					// Propagate the exception and terminate this process
					throw e;
				}
			}
			
			// 3.	Calculate number of rows of data in this sheet
			int numberOfRowsOfData;
			try 
			{
				numberOfRowsOfData = calculateRowsOfData(i);
			} 
			catch (NoDataFoundException e) 
			{
				// Log the exception
				e.printStackTrace(System.err);
				
				// Undo all changes made to structural repository
				deleteDataSourceIfNeeded();
				
				// This exception is generated by client - need to tell server to roll back all changes made to database
				rollback();
				
				// propagate the exception and terminate this process
				throw e;
			}
			
			// 4.	Insert data in the above table - serial numbers from 1...numberOfRowsOfData
			try 
			{
				populateEntityTable(entity, numberOfRowsOfData);
			} 
			catch (NoDataFoundException e) 
			{
				// Entity table could not be populated - log it
				e.printStackTrace(System.err);
				
				// Undo all changes made to structural repository
				deleteDataSourceIfNeeded();
				
				// This exception has come from the server - 
				// Changes to instance repository have been rolled back by the server
				
				// propagate the exception and terminate this process
				throw e;
			}
			
			// 5.	Get the list of Attributes associated with this Entity
			ArrayList<Attribute> listOfAttributes = schemaInfo.getAttributes(entity.getId());
			
			
			/* Create instance table corresponding to each column in this sheet */
			
			// 1.	Get the list of cells in the first row
			// Get the first row of the sheet
			HSSFRow firstRow = wb.getSheetAt(i).getRow(0);
			
			// Iterate through each defined value in the first row and obtain a list of cell objects
			List<HSSFCell> listOfFirstRowCells = new ArrayList<HSSFCell>();
			
			int minColumnIndex = firstRow.getFirstCellNum();
			if(minColumnIndex == -1)
			{
				// Undo all changes made to structural repository
				deleteDataSourceIfNeeded();
				
				// Since client is deciding to throw an exception -
				// need to tell server to roll back all changes made to database
				rollback();
				
				// Throw an exception and terminate this process
				throw new NoDataFoundException("No data found in the spreadsheet - no title row found");
			}
			int maxColumnIndex = firstRow.getLastCellNum();
			
			for(int j=minColumnIndex; j<maxColumnIndex; j++)
			{
				HSSFCell cell = firstRow.getCell(j);
				listOfFirstRowCells.add(cell);
			}
			
			// 2.	Iterate through each cell in the above list of cell objects
			for(HSSFCell cell : listOfFirstRowCells)
			{
				if(cell == null)
				{
					DataWarning w1 = new DataWarning("Data for column " + (listOfFirstRowCells.indexOf(cell)+1) + " in Sheet " + (i+1) + " not persisted in database. " +
							"Reason - First row in this column not defined");
					listOfDataWarning.add(w1);
					continue;
				}
				
				/* 3.	Get the value in this cell - title of the column (= Attribute name)
				   Assumption is that the title of the column is a String.
				   Any other value is not accepted
				*/ 
				String cellValue = null;
				if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
					cellValue = cell.getStringCellValue();
				else
				{
					DataWarning w2 = new DataWarning("Data for column " + (listOfFirstRowCells.indexOf(cell)+1) + " in Sheet " + (i+1) + " not persisted in database. " +
							"Reason - First row in this column is not a String/Text");
					listOfDataWarning.add(w2);
					continue;
				}
				
				if(cellValue.isEmpty())
				{
					DataWarning w3 = new DataWarning("Data for column " + (listOfFirstRowCells.indexOf(cell)+1) + " in Sheet " + (i+1) + " not persisted in database. " +
							"Reason - First row in this column is defined but has no contents (i.e. is empty)");
					listOfDataWarning.add(w3);
					continue;
				}
				
				// 4.	Get the Attribute that this cell is associated with
				Attribute attribute;
				try 
				{
					attribute = getAttributeCorrespondingToCell(cell, listOfAttributes);
				} 
				catch (NoDataFoundException e) 
				{
					// Corresponding Attribute could not be found - log it
					e.printStackTrace(System.err);
					
					// Undo all changes made to structural repository
					deleteDataSourceIfNeeded();
					
					// Since client is deciding to throw an exception -
					// need to tell server to roll back all changes made to database
					rollback();
					
					// Propagate the exception and terminate this process
					throw e;
				}
				
				// 5.	Find the type of values associated with this attribute
				
				/* Find the domain associated with this attribute (as loaded by the importer)
				   The present importer uses following set of Domain names:
				   {INTEGER, REAL, STRING, DATETIME, BOOLEAN}
				*/
				Integer domainID = attribute.getDomainID();
				SchemaElement domainElement = schemaInfo.getElement(domainID);
				Domain domain = (Domain) domainElement;
				String attributeDomainName = domain.getName();
				
				/*  Find the HSSFCell data type that correspond to the above domain
				 *  This data type should be found in the cells below the title row
				 */
				int expectedCellType = 10;  // domain name is not one of the values in the above set
				if(attributeDomainName.trim().equalsIgnoreCase("INTEGER"))
					expectedCellType = HSSFCell.CELL_TYPE_NUMERIC;
				else if(attributeDomainName.trim().equalsIgnoreCase("REAL"))
					expectedCellType = HSSFCell.CELL_TYPE_NUMERIC;
				else if(attributeDomainName.trim().equalsIgnoreCase("STRING"))
					expectedCellType = HSSFCell.CELL_TYPE_STRING;
				else if(attributeDomainName.trim().equalsIgnoreCase("DATETIME"))
					expectedCellType = CELL_TYPE_DATETIME;
				else if(attributeDomainName.trim().equalsIgnoreCase("BOOLEAN"))
					expectedCellType = HSSFCell.CELL_TYPE_BOOLEAN;
				
				// zero-based column index of this cell
				int columnIndexOfCell = cell.getColumnIndex();
				
				// zero-based row index of this cell
				int rowIndexOfCell = cell.getRowIndex();
				
				/* Find the appropriate SQL data type to be used when creating the instance
				 * table for this Attribute object
				 */
				String type = null;
				switch (expectedCellType) 
				{
					case HSSFCell.CELL_TYPE_NUMERIC:
						// creates a column in which numeric values of any precision and scale can be stored,
						// up to the implementation limit on precision
						type = TYPE_NUMERIC;	//Need to choose the type based on the type of database
						break;
					case HSSFCell.CELL_TYPE_STRING:
						// creates a column in which variable-length strings can be stored,
						// with no limit on the number of characters
						type = TYPE_STRING;		// max. length of the String is 30 characters for Derby
						break;	
					case HSSFCell.CELL_TYPE_BOOLEAN:
						// creates a column in which one of the two states "true" or "false" can be stored,
						// a "null" value represents the unknown state
						type = TYPE_BOOLEAN;	//Need to choose the type based on the type of database
						break;	
					case CELL_TYPE_DATETIME:
						// creates a column in which date is stored in the database-specific format
						type = TYPE_DATE;
						break;	
					default:
						// Undo all changes made to structural repository
						deleteDataSourceIfNeeded();
						
						// Since client is deciding to throw an exception -
						// need to tell server to roll back all changes made to database
						rollback();
						
						// Throw an exception and terminate this process
						throw new NoDataFoundException("Valid data type could not be found for attribute" + attribute.getName());
				}

				// 6.	Create instance table corresponding to this attribute
				if(createNewDatabase)
				{
					try 
					{
						createAttributeTable(attribute, type, entity);
					} 
					catch (NoDataFoundException e) 
					{
						// Attribute table could not be created - log it
						e.printStackTrace(System.err);
						
						// Undo all changes made to structural repository
						deleteDataSourceIfNeeded();
						
						// This exception has come from the server - 
						// Changes to instance repository have been rolled back by the server
						
						// Propagate the exception and terminate this process
						throw e;
					}
				}
				
				// 7.	Create a list/array of all the column values under this cell
				List<String> columnDataList = new ArrayList<String>();
				for(int n=1; n<=numberOfRowsOfData; n++)
				{
					HSSFCell nextCellInColumn = wb.getSheetAt(i).getRow(n).getCell(columnIndexOfCell);
					int actualCellType = nextCellInColumn.getCellType();
					
					// If any of the cell in this column is blank, store a NULL value					
					if(actualCellType == HSSFCell.CELL_TYPE_BLANK)
					{	
						columnDataList.add(TYPE_NULL);
						
						DataWarning w4 = new DataWarning("Data in cell at row " + (n+1) + " and column " + (columnIndexOfCell+1) + " in Sheet " + (i+1) + " not persisted in database. " +
						"Reason - Cell is blank");
						listOfDataWarning.add(w4);
					}
					
					
					// If any of the data in this column is found to be not of the same type as the domain,
					// store a NULL value
					else if(expectedCellType == CELL_TYPE_DATETIME && actualCellType != HSSFCell.CELL_TYPE_NUMERIC)
					{
						columnDataList.add(TYPE_NULL);
						
						DataWarning w5 = new DataWarning("Data in cell at row " + (n+1) + " and column " + (columnIndexOfCell+1) + " in Sheet " + (i+1) + " not persisted in database. " +
						"Reason - Valid data not found in the cell");
						listOfDataWarning.add(w5);
					}
					
					else if(expectedCellType != CELL_TYPE_DATETIME && expectedCellType != HSSFCell.CELL_TYPE_STRING && expectedCellType != actualCellType)
					{
						columnDataList.add(TYPE_NULL);
						
						DataWarning w6 = new DataWarning("Data in cell at row " + (n+1) + " and column " + (columnIndexOfCell+1) + " in Sheet " + (i+1) + " not persisted in database. " +
						"Reason - Valid data not found in the cell");
						listOfDataWarning.add(w6);
					}
					
					// If the value is as expected	
					else
					{
						String valueAsString = null;
						switch (expectedCellType) 
						{
							case HSSFCell.CELL_TYPE_NUMERIC:
								if(DateUtil.isCellDateFormatted(nextCellInColumn)) 
					        	{	
									//If a date is stored in the cell
									valueAsString = TYPE_NULL; 
									
									DataWarning w7 = new DataWarning("Data in cell at row " + (n+1) + " and column " + (columnIndexOfCell+1) + " in Sheet " + (i+1) + " not persisted in database. " +
									"Reason - Valid data not found in the cell");
									listOfDataWarning.add(w7);
								}	
					        	else 
					        	{
					                double valueAsNumber = nextCellInColumn.getNumericCellValue();
					                valueAsString = Double.toString(valueAsNumber);
					        	}
							break;
							case HSSFCell.CELL_TYPE_STRING:
								// All types of values are accepted
								switch(actualCellType)
								{
									case HSSFCell.CELL_TYPE_NUMERIC:
										{
											// If the value is a date from 1/1/1900 and beyond
											if(DateUtil.isCellDateFormatted(nextCellInColumn)) 
											{	
												Date d = nextCellInColumn.getDateCellValue();
												SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
												valueAsString = dateFormatter.format(d);
											}
											// If the value is a number
											else 
											{
												double valueAsNumber = nextCellInColumn.getNumericCellValue();
												valueAsString = Double.toString(valueAsNumber);
											}
										}
									break;
									case HSSFCell.CELL_TYPE_BOOLEAN: 
										{
											// If value is a boolean
											boolean b = nextCellInColumn.getBooleanCellValue();
											valueAsString = Boolean.toString(b);
										}
									break;
									case HSSFCell.CELL_TYPE_STRING:
										// If the value is a string or a date before 1/1/1900
										valueAsString = nextCellInColumn.getStringCellValue();
									break;	
									default:
										DataWarning w8 = new DataWarning("Data in cell at row " + (n+1) + " and column " + (columnIndexOfCell+1) + " in Sheet " + (i+1) + " not persisted in database. " +
										"Reason - Valid data not found in the cell");
										listOfDataWarning.add(w8);
								}
							break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								boolean b = nextCellInColumn.getBooleanCellValue();
								valueAsString = Boolean.toString(b);
							break;	
							case CELL_TYPE_DATETIME:
								if(DateUtil.isCellDateFormatted(nextCellInColumn))
								{
									Date d = nextCellInColumn.getDateCellValue();
									SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
									valueAsString = dateFormatter.format(d);
								}
								else
								{
									//If a number is stored in the cell
									valueAsString = TYPE_NULL;	
									
									DataWarning w9 = new DataWarning("Data in cell at row " + (n+1) + " and column " + (columnIndexOfCell+1) + " in Sheet " + (i+1) + " not persisted in database. " +
									"Reason - Valid data not found in the cell");
									listOfDataWarning.add(w9);
								}
							break;
							default:
								// Unexpected cell type obtained from M3 model
								// Undo all changes made to structural repository
								deleteDataSourceIfNeeded();
								
								// Since client is deciding to throw an exception -
								// need to tell server to roll back all changes made to database
								rollback();
								
								// Throw an exception and terminate this process
								throw new NoDataFoundException("Domain obtained from M3 model cannot be processed by this application");
						}
						columnDataList.add(valueAsString);
					}
				}//end of for loop iterating through all column values under the cell
				String[] columnDataArray = columnDataList.toArray(new String[columnDataList.size()]); 
				
				// 8.	Populate attribute instance table, created above, with column data
				switch (expectedCellType) 
				{
					case HSSFCell.CELL_TYPE_NUMERIC:
						try 
						{
							populateNumericAttributeTable(attribute, columnDataArray);
						} 
						catch (NoDataFoundException e) 
						{
							// Attribute table could not be populated - log it
							e.printStackTrace(System.err);
							
							// Undo all changes made to structural repository
							deleteDataSourceIfNeeded();
							
							// This exception has come from the server - 
							// Changes to instance repository have been rolled back by the server
							
							// Propagate the exception and terminate this process
							throw e;
						}
						break;
					case HSSFCell.CELL_TYPE_STRING:
						try 
						{
							populateStringAttributeTable(attribute, columnDataArray); // max. length of the String is 30 characters for Derby
						} 
						catch (NoDataFoundException e) 
						{
							// Attribute table could not be populated - log it
							e.printStackTrace(System.err);
							
							// Undo all changes made to structural repository
							deleteDataSourceIfNeeded();
							
							// This exception has come from the server - 
							// Changes to instance repository have been rolled back by the server
							
							// Propagate the exception and terminate this process
							throw e;
						}
						break;	
					case HSSFCell.CELL_TYPE_BOOLEAN:
						try 
						{
							populateBooleanAttributeTable(attribute, columnDataArray); //Need to choose the type based on the type of database
						} 
						catch (NoDataFoundException e) 
						{
							// Attribute table could not be populated - log it
							e.printStackTrace(System.err);
							
							// Undo all changes made to structural repository
							deleteDataSourceIfNeeded();
							
							// This exception has come from the server - 
							// Changes to instance repository have been rolled back by the server
							
							// Propagate the exception and terminate this process
							throw e;
						}
						break;
					case CELL_TYPE_DATETIME:
						try 
						{
							populateDateAttributeTable(attribute, columnDataArray);
						} 
						catch (NoDataFoundException e) 
						{
							// Attribute table could not be populated - log it
							e.printStackTrace(System.err);
							
							// Undo all changes made to structural repository
							deleteDataSourceIfNeeded();
							
							// This exception has come from the server - 
							// Changes to instance repository have been rolled back by the server
							
							// Propagate the exception and terminate this process
							throw e;
						}
						break;
					default:
						// Undo all changes made to structural repository
						deleteDataSourceIfNeeded();
						
						// Since client is deciding to throw an exception -
						// need to tell server to roll back all changes made to database
						rollback();
						
						// Throw an exception and terminate this process
						throw new NoDataFoundException("Valid data type could not be found for attribute" + attribute.getName());
				}
			}//end of iteration over list of first row cells and creation of instance tables for each column
			
			/* Now create transfer object necessary for creating the View corresponding to this Entity */
			
			// 1.	Create a list of all Attribute table names associated with this Entity
			List<String> listOfAttributeTableNames = new ArrayList<String>();
			for(Attribute attribute : listOfAttributes)
				listOfAttributeTableNames.add(ATTRIBUTE + attribute.getId());
			
			// 2.	Create a list of all Column names associated with this Entity/Sheet
			List<String> listOfColumnNames = new ArrayList<String>();
			for(Attribute attribute : listOfAttributes)
				// Store the column name
				listOfColumnNames.add(attribute.getName());
			
			// 3.	Create transfer object containing information necessary for creating view for this Entity
			ViewDTO detailsOfOneView = new ViewDTO(entity.getName(), ENTITY + entity.getId(), 
					listOfAttributeTableNames.toArray(new String[listOfAttributeTableNames.size()]), 
					listOfColumnNames.toArray(new String[listOfColumnNames.size()]));
			
			// 4.	Add it to the list of all the view transfer objects for this excel file
			listOfViewDTO.add(detailsOfOneView);
		}//end of iteration over sheets in the workbook
		
		/* Create views for all the sheets in the workbook */
		ViewDTO[] arrayOfViewDetails = listOfViewDTO.toArray(new ViewDTO[listOfViewDTO.size()]);
		ViewDTOArray v = new ViewDTOArray(arrayOfViewDetails);
		try 
		{
			createInstanceDatabaseViews(v);
		} 
		catch (NoDataFoundException e) 
		{
			// Attribute table could not be created - log it
			e.printStackTrace(System.err);
				
			// Undo all changes made to structural repository
			deleteDataSourceIfNeeded();
			
			// This exception has come from the server - 
			// Changes to instance repository have been rolled back by the server
				
			// Propagate the exception and terminate this process
			throw e;
		}
		
		// Release resources
		releaseResources();
		
		// Print out all the warnings, if there are any
		if(!(listOfDataWarning.isEmpty()))
		{
			System.out.println("\n----- Data Warnings -----");
			for(DataWarning w : listOfDataWarning)
				System.out.println("Warning " + (listOfDataWarning.indexOf(w)+1) + ": " + w.getMessage() + "\n");
		}

	}//end of method createInstanceDatabaseTables
	
	
	/** Method to create entity table for a sheet in the given spreadsheet, given the zero-based index of the sheet 
	 * @throws NoDataFoundException, RemoteException */
	public void createEntityTable(int sheetIndex) throws NoDataFoundException, RemoteException
	{
		// Get the Entity that this sheet is associated with
		Entity entity = getEntityCorrespondingToSheetIndex(sheetIndex);
		
		System.out.println("Entity ID= " + entity.getId() + " Entity name: " + entity.getName() + " Entity description: " + entity.getDescription());
		
		// Create instance table corresponding to this entity
		createEntityTable(entity);
	}
	
	
	/** Implementing method of InstanceDatabaseInterface - creates table for given Entity 
	 * @throws NoDataFoundException, RemoteException */
	public void createEntityTable(Entity entity) throws NoDataFoundException, RemoteException
	{
		String entityTableName =  ENTITY + entity.getId();
		((InstanceDatabase) instanceDatabase).createEntityTable(entityTableName);
		/*callMethod("createEntityTable", new Object[] {entityTableName});*/
	}
	
	
	/** Method to populate entity table for a sheet in the given spreadsheet, given the zero-based index of the sheet 
	 * @throws NoDataFoundException, RemoteException */
	public void populateEntityTable(int sheetIndex) throws NoDataFoundException, RemoteException
	{
		// Get the Entity that this sheet is associated with
		Entity entity = getEntityCorrespondingToSheetIndex(sheetIndex);
		
		// Calculate number of rows of data in this sheet
		int numberOfRowsOfData = calculateRowsOfData(sheetIndex);
		
		// Insert data in the above table - serial numbers from 1...numberOfRowsOfData
		populateEntityTable(entity, numberOfRowsOfData);
	}
	
	
	/** Implementing method of InstanceDatabaseInterface - populates table for given Entity 
	 * @throws NoDataFoundException, RemoteException */
	public void populateEntityTable(Entity entity, Integer numberOfRows) throws NoDataFoundException, RemoteException
	{
		String entityTableName = ENTITY + entity.getId();
		((InstanceDatabase) instanceDatabase).populateEntityTable(entityTableName, numberOfRows);
		/*callMethod("populateEntityTable", new Object[] {entityTableName, numberOfRows});*/
	}
	
	/** Implementing method of InstanceDatabaseInterface - creates table for given Attribute 
	 * @throws NoDataFoundException, RemoteException */
	public void createAttributeTable(Attribute attribute, String sqlType, Entity entity) throws NoDataFoundException, RemoteException
	{
		String attributeTableName = ATTRIBUTE + attribute.getId();
		String entityTableName = ENTITY + entity.getId();
		((InstanceDatabase) instanceDatabase).createAttributeTable(attributeTableName, sqlType, entityTableName);
		/*callMethod("createAttributeTable", new Object[] {attributeTableName, sqlType, entityTableName});*/
	}
	
	/** Implementing method of InstanceDatabaseInterface - populates table for given Attribute with Numeric data 
	 * @throws NoDataFoundException, RemoteException */
	public void populateNumericAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException
	{
		String attributeTableName = ATTRIBUTE + attribute.getId();
		((InstanceDatabase) instanceDatabase).populateNumericAttributeTable(attributeTableName, values);
		/*callMethod("populateNumericAttributeTable", new Object[] {attributeTableName, values});*/
	}
	
	/** Implementing method of InstanceDatabaseInterface - populates table for given Attribute with String data 
	 * @throws NoDataFoundException, RemoteException */
	public void populateStringAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException
	{
		String attributeTableName = ATTRIBUTE + attribute.getId();
		((InstanceDatabase) instanceDatabase).populateStringAttributeTable(attributeTableName, values);
		/*callMethod("populateStringAttributeTable", new Object[] {attributeTableName, values});*/
	}
	
	/** Implementing method of InstanceDatabaseInterface - populates table for given Attribute with Boolean data 
	 * @throws NoDataFoundException, RemoteException */
	public void populateBooleanAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException
	{
		String attributeTableName = ATTRIBUTE + attribute.getId();
		((InstanceDatabase) instanceDatabase).populateBooleanAttributeTable(attributeTableName, values);
		/*callMethod("populateBooleanAttributeTable", new Object[] {attributeTableName, values});*/
	}
	
	/** Implementing method of InstanceDatabaseInterface - populates table for given Attribute with Date data 
	 * @throws NoDataFoundException, RemoteException */
	public void populateDateAttributeTable(Attribute attribute, String[] values) throws NoDataFoundException, RemoteException
	{
		String attributeTableName = ATTRIBUTE + attribute.getId();
		((InstanceDatabase) instanceDatabase).populateDateAttributeTable(attributeTableName, values);
		/*callMethod("populateBooleanAttributeTable", new Object[] {attributeTableName, values});*/
	}
	
	public void createContainmentTable(Entity entity) throws NoDataFoundException, RemoteException
	{	}
	
	public void populateContainmentTable(Entity entity) throws NoDataFoundException, RemoteException
	{	}
	
	public void createRelationshipTable(Entity entity) throws NoDataFoundException, RemoteException
	{	}
	
	public void populateRelationshipTable(Entity entity) throws NoDataFoundException, RemoteException
	{	}
	
	/** Implementing method of InstanceDatabaseInterface -
	 *  creates views representing each of the sheets in the given excel file 
	 * @throws RemoteException 
	 * @throws NoDataFoundException */
	public void createInstanceDatabaseViews(ViewDTOArray v) throws NoDataFoundException, RemoteException
	{
		((InstanceDatabase) instanceDatabase).createAllViews(v);
		/*callMethod("createAllViews", new Object[] {v});*/
	}
	
	/** Release resources 
	 * @throws RemoteException */
	public void releaseResources() throws RemoteException
	{
		((InstanceDatabase) instanceDatabase).releaseResources();
		/*callMethod("releaseResources", new Object[] {});*/
	}
	
	
	/** Undo all changes made in the current transaction */
	public void rollback() throws NoDataFoundException
	{
		((InstanceDatabase) instanceDatabase).rollback();
	}

	
	/** Create an instance of InstanceRepository from the given Repository object 
	 * @throws RemoteException */
	private InstanceRepository getInstanceRepository(Repository structuralRepository) throws RemoteException
	{
		InstanceRepository instanceRepository = new InstanceRepository();
		
		// Get the type of repository
		int structuralRepositoryType = structuralRepository.getType();
		
		// Set the type and host 
		switch(structuralRepositoryType)
		{
			case 0:
				instanceRepository.setType(instanceRepository.SERVICE);
				break;
			case 1:
				instanceRepository.setType(instanceRepository.POSTGRES);
				instanceRepository.setHost(structuralRepository.getURI().toString());
				break;
			case 2:
				instanceRepository.setType(instanceRepository.DERBY);
				String host = null; 
				if(structuralRepository.getURI().getScheme().equals("file"))
					host = structuralRepository.getURI().getPath();
				instanceRepository.setHost(host);
				break;
			default:
				instanceRepository.setType(instanceRepository.DERBY);
				String host2 = null;
				if(structuralRepository.getURI().getScheme().equals("file"))
					host2 = structuralRepository.getURI().getPath();
				instanceRepository.setHost(host2);
			break;
		}
		
		// Get the name of the new instance database that will be created
		instanceRepository.setDatabaseName(getInstanceDatabaseName(structuralRepository));
		
		return instanceRepository;
	}//end of method getInstanceRepository
	
	
	/** Method used to obtain the name of the new database to be created 
	 * @throws RemoteException */
	private String getInstanceDatabaseName(Repository structuralRepository) throws RemoteException
	{
		// Remove all except alpha-numeric characters - in PostgreSQL, new database cannot be created with any except "_"
		// In PostgreSQL, name of database, as used in connection string, is case-sensitive - so only lower-case is used
		userSpecifiedName = userSpecifiedName.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
		
		/* Find if a dataSource with the userSpecifiedName exists for this schema */
		ArrayList<DataSource> listOfDSforSchema = schemastoreClient.getDataSources(schema.getId());
		List<DataSource> listOfDSwithSpecificName = new ArrayList<DataSource>();
		for(DataSource ds : listOfDSforSchema)
			if(ds.getName().equals(userSpecifiedName))
				listOfDSwithSpecificName.add(ds);
		
		/* If a dataSource with the userSpecifiedName does not exist */
		if(listOfDSwithSpecificName.isEmpty())
		{
			/* Store information about the new instance database in the structural repository
			 * Create a new data source object specifying the new database being created
			 * Name of this data source = name entered by user in the dialog
			 * The new instance database is being created in the same place as the existing
			 * structural database - so the URL is the same
			 */
			DataSource dataSource = new DataSource();
			dataSource.setName(userSpecifiedName);
			dataSource.setSchemaID(schema.getId());
			dataSource.setUrl(structuralRepository.getURI().toString()); 
			newDataSourceId = schemastoreClient.addDataSource(dataSource);
			
			// Set the flag that shows that a new database needs to be created
			createNewDatabase = true;
			
			//String schemaName = schema.getName().toLowerCase();
			//schemaName = schemaName.replaceAll("[^a-zA-Z0-9]", "");
			//String newDatabaseName = userSpecifiedName;/* + "_" + schemaName + "_" + uniqueId;*/ 
		}
		return userSpecifiedName;
	}//end of method getNewInstanceDatabaseName
	
	
	/** Method to delete a data source - used when an exception is thrown 
	 *  while the database is being created for the first time ever.
	 *  This is important so that a database with the same name can be created again 
	 * @throws RemoteException 
	 */
	private void deleteDataSourceIfNeeded() throws RemoteException
	{
		if(createNewDatabase)
			schemastoreClient.deleteDataSource(newDataSourceId);
	}

	
	/** Manages calls to the InstanceDatabase methods */
	private Object callMethod(String name, Object args[]) throws RemoteException
	{
		// Create an array of types
		Class<?> types[] = new Class[args.length];
		for(int i=0; i<args.length; i++)
		{
			Class<?> type = args[i].getClass();
			types[i] = type;
		}
		
		// Calls the InstanceDatabase method
		try 
		{
			Method method = instanceDatabase.getClass().getDeclaredMethod(name, types);
			return method.invoke(instanceDatabase, args);
		} 
		catch(Exception e) { return new RemoteException("Unable to call method " + name); }
	}
	
	
	/** Get the entity that corresponds to the sheet at the given index 
	 * @throws NoDataFoundException */
	private Entity getEntityCorrespondingToSheetIndex(int sheetIndex) throws NoDataFoundException
	{
		// Stores the entity to be found
		Entity correspondingEntity = null;
			
		// Find the entity that the sheet at the given index corresponds to 
		for(Entity entity : listEntitiesOfSchema())
			if(entity.getName().equals(wb.getSheetName(sheetIndex)))
			{
				System.out.println("Sheet name = " + wb.getSheetName(sheetIndex));
				correspondingEntity = entity;
			}
		
		// If corresponding entity is not found
		if(correspondingEntity == null)
			throw new NoDataFoundException("Entity corresponding to Sheet with name '" 
					+ wb.getSheetName(sheetIndex) + "' not found");
		
		// If corresponding entity is found
		System.out.println("Corresponding Entity object for the above sheet:");
		System.out.println("Entity name = " + correspondingEntity.getName());
			
		return correspondingEntity;
	}
	
	/** Find all entities in the selected schema 
	 * Before using this method the instance variable "schemaInfo" must be instantiated
	 */
	private ArrayList<Entity> listEntitiesOfSchema()
	{
		// Stores list of Entities of Schema
		ArrayList<Entity> entitiesOfSchema = new ArrayList<Entity>();
		
		// Returns the list of schema elements - Entity
		ArrayList<SchemaElement> elementsEntity = schemaInfo.getElements(Entity.class);
		for(SchemaElement element : elementsEntity)
		{
			Entity entity = (Entity)element;
			entitiesOfSchema.add(entity);
			
			Integer id = entity.getId();
			String name = entity.getName();
			String description = entity.getDescription();
			System.out.println("Entity ID= " + id + " Entity name: " + name + " Entity description: " + description);
		}
		return entitiesOfSchema;
	}
	
	
	/** Calculate number of rows of data for sheet at a particular index */
	private Integer calculateRowsOfData(int sheetIndex) throws NoDataFoundException
	{
		int i = wb.getSheetAt(sheetIndex).getPhysicalNumberOfRows();
		
		if(i == 0)
			// No physically defined rows found
			throw new NoDataFoundException("No data found in the spreadsheet - no title row found");
		if(i == 1)
			// Only one physically defined row found - this is the title row - equivalent to attributes
			throw new NoDataFoundException("No data found in the spreadsheet - only title row found (equivalent to attributes)");
		
		// Return the number of rows minus the title row
		return new Integer(i-1);
	}
	
	
	/** Get the attribute that corresponds to given cell 
	 * @throws NoDataFoundException */
	private Attribute getAttributeCorrespondingToCell(HSSFCell cell, ArrayList<Attribute> listOfAttributes) throws NoDataFoundException
	{
		// Stores the attribute to be found
		Attribute correspondingAttribute = null;
			
		// Find the attribute that the given cell corresponds to 
		for(Attribute attribute : listOfAttributes)
			if(attribute.getName().equals(cell.getStringCellValue()))
			{
				System.out.println("Cell value = " + cell.getStringCellValue());
				correspondingAttribute = attribute;
			}
		
		// If corresponding attribute is not found
		if(correspondingAttribute == null)
			throw new NoDataFoundException("Attribute corresponding to Cell with value '" 
					+ cell.getStringCellValue() + "' not found");
		
		// If corresponding attribute is found
		System.out.println("Corresponding Attribute object for the above Cell:");
		System.out.println("Attribute name = " + correspondingAttribute.getName());
		
		return correspondingAttribute;
	}
	
	
	/** If a new data source got created, it returns the id of the new data source
	 *  else it returns null
	 */
	public Integer getNewDataSourceCreated()
	{	return newDataSourceId;	}
	
	
	/** Implementing method of InstanceDatabaseInterface - 
	 *  Get the list of warnings generated when data is read from the .xls file	
	 *  returns null if no such list has been created */
	public List<DataWarning> getListOfDataWarning()
	{
		return listOfDataWarning;
	}
	
	
}