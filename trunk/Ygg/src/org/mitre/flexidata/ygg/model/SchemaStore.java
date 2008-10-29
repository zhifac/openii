// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.model;

import java.rmi.RemoteException;

import org.mitre.schemastore.client.SchemaStoreClient;

/** Handles access to SchemaStore */
public class SchemaStore
{
	/** Stores the schema repository client */
	static private SchemaStoreClient client = null;

	/** Returns the schema store client */
	static SchemaStoreClient getClient() throws RemoteException
	{
		if(client==null) client = new SchemaStoreClient(ConfigManager.getSchemaStoreLoc());
		return client;
	}
	
	/** Sets the schema store client */
	static public void setClient(SchemaStoreClient clientIn)
		{ client = clientIn; }
}
