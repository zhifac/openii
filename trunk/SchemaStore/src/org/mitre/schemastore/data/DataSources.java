// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;

import org.mitre.schemastore.model.DataSource;

/** Class for managing the data sources in the schema repository */
public class DataSources extends DataCache
{	
	/** Constructs the data sources cache */
	DataSources(DataManager manager)
		{ super(manager); }
	
	/** Get a list of all data sources */
	public ArrayList<DataSource> getAllDataSources()
		{ return getDatabase().getDataSources(null); }
			
	/** Get a list of data sources for the specified schema */
	public ArrayList<DataSource> getDataSources(Integer schemaID)
		{ return getDatabase().getDataSources(schemaID); }

	/** Get the specified data source */
	public DataSource getDataSource(Integer dataSourceID)
		{ return getDatabase().getDataSource(dataSourceID); }

	/** Add the specified data source */
	public Integer addDataSource(DataSource dataSource)
		{ return getDatabase().addDataSource(dataSource); }

	/** Update the specified data source */
	public Boolean updateDataSource(DataSource dataSource)
		{ return getDatabase().updateDataSource(dataSource); }

	/** Delete the specified data source */
	public Boolean deleteDataSource(Integer dataSourceID)
		{ return getDatabase().deleteDataSource(dataSourceID); }
}