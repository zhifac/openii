// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.listeners;

import org.mitre.schemastore.model.DataSource;

/** Listener class for data sources */
public interface DataSourceListener
{
	/** Indicates that a data source has been added */
	public void dataSourceAdded(DataSource dataSource);
	
	/** Indicates that a data source has been updatead */
	public void dataSourceUpdated(DataSource dataSource);
	
	/** Indicates that a data source has been removed */
	public void dataSourceRemoved(DataSource dataSource);
}
