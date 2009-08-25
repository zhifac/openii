package org.mitre.schemastore.warehouse.common;

import java.io.Serializable;
import java.util.Collection;

public class ViewDTO implements Serializable 
{
	private static final long serialVersionUID = -2435312353099107024L;
	
	/**	Instance variables	*/
	private String viewName = null;
	private String entityTableName = null;
	private String[] attributeTableNames = null;
	private String[] columnNames = null;
	
	/** No-arg constructor */
	public ViewDTO()	{}
	
	/** Constructor accepting all values */
	public ViewDTO(String viewName, String entityTableName, String[] attributeTableNames, String[] columnNames)	
	{	this.viewName=viewName;	this.entityTableName=entityTableName;	
		this.attributeTableNames=attributeTableNames; this.columnNames=columnNames;	
	}
	
	/**	Get methods	*/
	public String getViewName()	{	return viewName;	}
	public String getEntityTableName()	{	return entityTableName;	}
	public String[] getAttributeTableNames()	{	return attributeTableNames;	}
	public String[] getColumnNames()	{	return columnNames;	}
	
	/**	Set methods	*/
	public void setViewName(String viewName)	{	this.viewName = viewName;	}
	public void setEntityTableName(String entityTableName)	{	this.entityTableName = entityTableName;	}
	public void setAttributeTableNames(String[] attributeTableNames)	{	this.attributeTableNames = attributeTableNames;	}
	public void setColumnNames(String[] columnNames)	{	this.columnNames = columnNames;	}

}
