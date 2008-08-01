// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.data;

import java.util.ArrayList;

import org.mitre.schemastore.data.database.Database;
import org.mitre.schemastore.model.DataSource;

/** Class for managing the data sources in the schema repository */
public class DataSources
{	
	/** Get a list of all data sources */
	static public ArrayList<DataSource> getAllDataSources()
		{ return Database.getDataSources(null); }
			
	/** Get a list of data sources for the specified schema */
	static public ArrayList<DataSource> getDataSources(Integer schemaID)
		{ return Database.getDataSources(schemaID); }

	/** Get the specified data source */
	static public DataSource getDataSource(Integer dataSourceID)
		{ return Database.getDataSource(dataSourceID); }

	/** Get the data source specified by its URL */
	static public DataSource getDataSourceByURL(String url)
		{ return Database.getDataSourceByURL(url); }

	/** Add the specified data source */
	static public Integer addDataSource(DataSource dataSource)
		{ return Database.addDataSource(dataSource); }

	/** Update the specified data source */
	static public Boolean updateDataSource(DataSource dataSource)
		{ return Database.updateDataSource(dataSource); }

	/** Delete the specified data source */
	static public Boolean deleteDataSource(Integer dataSourceID)
		{ return Database.deleteDataSource(dataSourceID); }
}