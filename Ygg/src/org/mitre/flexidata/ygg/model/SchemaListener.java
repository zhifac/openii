// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.model;

import org.mitre.schemastore.model.Schema;

/** Listener class for schemas */
public interface SchemaListener
{
	/** Indicates that a schema has been added */
	public void schemaAdded(Schema schema);
	
	/** Indicates that a schema has been removed */
	public void schemaRemoved(Schema schema);
}
