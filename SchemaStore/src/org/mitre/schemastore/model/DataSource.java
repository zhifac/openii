// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;

/**
 * Class for storing a data source
 * @author CWOLF
 */
public class DataSource implements Serializable
{
	/** Stores the data source id */
	private Integer id;
	
	/** Stores the data source name */
	private String name;
	
	/** Stores the data source schema ID */
	private Integer schemaID;
	
	/** Stores the data source URL */
	private String url;

	/** Constructs a default data source */
	public DataSource() {} 
	
	/** Constructs a data source object */
	public DataSource(Integer id, String name, Integer schemaID, String url)
		{ this.id = id; this.name = name; this.schemaID = schemaID; this.url = url; }
	
	// Handles all data source getters
	public Integer getId() { return id; }
	public String getName() { return name; }
	public Integer getSchemaID() { return schemaID; }
	public String getUrl() { return url; }

	// Handles all data source setters
	public void setId(Integer id) { this.id = id; }
	public void setUrl(String url) { this.url = url; }
	public void setName(String name) { this.name = name; }
	public void setSchemaID(Integer schemaID) { this.schemaID = schemaID; }

	/** Indicates that two data sources are equals */
	public boolean equals(Object object)
	{
		if(object instanceof Integer) return ((Integer)object).equals(id);
		if(object instanceof DataSource) return ((DataSource)object).id.equals(id);
		return false;
	}
	
	/** String representation of the data source */
	public String toString()
		{ return name; }
}
