package org.mitre.schemastore.warehouse.common;

import java.io.Serializable;

/**
 * Java bean class that carries information needed for creating a new database
 * @author STANDON
 */
public class InstanceRepository implements Serializable 
{
	private static final long serialVersionUID = 4722918926122430080L;
	
	/**	Instance variables	*/
	private Integer type = null;
	private String host = null;
	private String databaseName = null;	// the new database name
	private String username = null;
	private String password = null;
	//private Boolean isNewRepository = false;
	
	/** Constants defining the various types of repositories */
	public static final Integer SERVICE = 0;
	public static final Integer POSTGRES = 1;
	public static final Integer DERBY = 2;
	
	/** No-arg constructor */
	public InstanceRepository()	{}
	
	/** Constructor */
	public InstanceRepository(Integer type, String host, String databaseName, String username, String password/*, boolean isNewRepository*/)	
	{	this.type=type;	this.host=host;	this.databaseName=databaseName;	
		this.username=username;	this.password=password;	
		//this.isNewRepository=new Boolean(isNewRepository);	
	}
	
	/**	Get methods	*/
	public Integer getType()	{	return type;	}
	public String getHost()	{	return host;	}
	public String getDatabaseName()	{	return databaseName;	}
	public String getUsername()	{	return username;	}
	public String getPassword()	{	return password;	}
	//public boolean getIsNewRepository()	{	return isNewRepository.booleanValue();	}
	
	/**	Set methods	*/
	public void setType(Integer type)	{	this.type = type;	}
	public void setHost(String host)	{	this.host = host;	}
	public void setDatabaseName(String databaseName)	{	this.databaseName = databaseName;	}
	public void setUsername(String username)	{	this.username = username;	}
	public void setPassword(String password)	{	this.password = password;	}
	//public void setIsNewRepository(boolean isNewRepository)	{	this.isNewRepository = new Boolean(isNewRepository);	}
}
