// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model.graph;

import org.mitre.schemastore.model.SchemaElement;

/** Listener class for changes to the graph */
public interface GraphListener
{
	/** Indicates that a schema element has been added */
	public void schemaElementAdded(SchemaElement schemaElement);
	
	/** Indicates that a schema element has been removed */
	public void schemaElementRemoved(SchemaElement schemaElement);
}
