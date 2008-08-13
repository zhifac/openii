// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.model;

import org.mitre.schemastore.client.SchemaStoreClient;

/** Handles access to SchemaStore */
public class SchemaStore
{
	/** Stores the schema repository client */
	static private SchemaStoreClient client = new SchemaStoreClient(ConfigManager.getSchemaStoreLoc());

	/** Returns the schema store client */
	static SchemaStoreClient getClient()
		{ return client; }
	
	/** Sets the schema store client */
	static public void setClient(SchemaStoreClient clientIn)
		{ client = clientIn; }
}
