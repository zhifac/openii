// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.exporters;

import java.util.ArrayList;

import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.SchemaElement;

/** Abstract Exporter class */
public abstract class Exporter
{
	/** Returns the exporter name */
	abstract public String getName();
	
	/** Returns the exporter description */
	abstract public String getDescription();
	
	/** Returns the exporter file type */
	abstract public String getFileType();
	
	/** Stores the schema store client to which this schema is being imported */
	protected SchemaStoreClient client = null;
	
	/** Exports the specified schema*/
	abstract public StringBuffer exportSchema(Integer schemaID, ArrayList<SchemaElement> schemaElements);
	
	/** Outputs the exporter name */
	public String toString()
		{ return getName(); }
	
	/** Set the schema store client */
	void setClient(SchemaStoreClient client)
		{ this.client = client; }
	
}
