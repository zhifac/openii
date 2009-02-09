// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.schemastore.model.DataSource;

import model.server.SchemaStoreManager;

/**
 * Class for managing the data sources in the schema repository
 * @author CWOLF
 */
public class DataSources
{
	/** Caches data sources to avoid constant talk between the servers */
	static private HashMap<Integer,DataSource> dataSources = new HashMap<Integer,DataSource>();

	/** Returns a list of all data sources */
	static public ArrayList<DataSource> getDataSources()
	{
		ArrayList<DataSource> dataSources = SchemaStoreManager.getDataSourceList(null);
		return dataSources==null ? new ArrayList<DataSource>() : SchemaStoreManager.getDataSourceList(null);
	}
	
	/** Returns the list of data sources for the specified schema */
	static public ArrayList<DataSource> getDataSources(Integer schemaID)
		{ return SchemaStoreManager.getDataSourceList(schemaID); }
	
	/** Returns the specified data source */
	static public DataSource getDataSource(Integer dataSourceID)
	{
		if(dataSources.get(dataSourceID)==null) 
			dataSources.put(dataSourceID, SchemaStoreManager.getDataSource(dataSourceID));
		return dataSources.get(dataSourceID);
	}
	
	/** Sorts the list of data sources */
	static public ArrayList<DataSource> sort(ArrayList<DataSource> dataSources)
	{
		final class DataSourceComparator implements Comparator<DataSource>
		{
			public int compare(DataSource dataSource1, DataSource dataSource2)
				{ return dataSource1.getName().compareTo(dataSource2.getName()); }
		}
		Collections.sort(dataSources,new DataSourceComparator());
		return dataSources;
	}
}
