// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.mitre.schemastore.model.DataSource;

import model.listeners.DataSourceListener;
import model.server.ServletConnection;

/**
 * Class for managing the data sources in the schema repository
 * @author CWOLF
 */
public class DataSources
{
	/** Caches data sources to avoid constant talk between the servers */
	static private HashMap<Integer,DataSource> dataSources = new HashMap<Integer,DataSource>();

	/** List of listeners monitoring data source events */
	static private ArrayList<DataSourceListener> listeners = new ArrayList<DataSourceListener>();

	/** Returns a list of all data sources */
	static public ArrayList<DataSource> getDataSources()
		{ return ServletConnection.getDataSourceList(null); }
	
	/** Returns the list of data sources for the specified schema */
	static public ArrayList<DataSource> getDataSources(Integer schemaID)
		{ return ServletConnection.getDataSourceList(schemaID); }
	
	/** Returns the specified data source */
	static public DataSource getDataSource(Integer dataSourceID)
	{
		if(dataSources.get(dataSourceID)==null) 
			dataSources.put(dataSourceID, ServletConnection.getDataSource(dataSourceID));
		return dataSources.get(dataSourceID);
	}

	/** Adds the specified data source to the database */
	static public boolean addDataSource(DataSource dataSource)
	{
		Integer dataSourceID = ServletConnection.addDataSource(dataSource);
		if(dataSourceID!=null)
		{
			dataSource.setId(dataSourceID);
			dataSources.put(dataSource.getId(),dataSource);
			for(DataSourceListener listener : listeners)
				listener.dataSourceAdded(dataSource);
			return true;
		}
		return false;
	}
	
	/** Updates the specified data source in the database */
	static public boolean updateDataSource(DataSource dataSource)
	{
		if(ServletConnection.updateDataSource(dataSource))
		{
			dataSources.put(dataSource.getId(),dataSource);
			for(DataSourceListener listener : listeners)
				listener.dataSourceUpdated(dataSource);
			return true;
		}
		return false;
	}
	
	/** Deletes the specified data source from the database */
	static public boolean deleteDataSource(DataSource dataSource)
	{
		if(ServletConnection.deleteDataSource(dataSource.getId()))
		{
			dataSources.remove(dataSource.getId());
			for(DataSourceListener listener : listeners)
				listener.dataSourceRemoved(dataSource);
			return true;
		}
		return false;
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
	
	/** Adds a listener monitoring data source events */
	static public void addDataSourceListener(DataSourceListener listener)
		{ listeners.add(listener); }
	
	/** Removes a listener monitoring data source events */
	static public void removeDataSourceListener(DataSourceListener listener)
		{ listeners.remove(listener); }
}
