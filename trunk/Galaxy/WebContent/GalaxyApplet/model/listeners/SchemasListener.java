// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.listeners;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

/** Listener class for schemas */
public interface SchemasListener
{
	/** Indicates that a schema has been added */
	public void schemaAdded(Schema schema);
	
	/** Indicates that a schema has been removed */
	public void schemaRemoved(Schema schema);
	
	/** Indicates that a schema has been updated */
	public void schemaUpdated(Schema schema);
	
	/** Indicates that a schema has been locked */
	public void schemaLocked(Schema schema);
	
	/** Indicates that parent schemas have been updated */
	public void schemaParentsUpdated(Schema schema);
	
	/** Indicates that a schema element has been added */
	public void schemaElementAdded(SchemaElement schemaElement);

	/** Indicates that a schema element has been removed */
	public void schemaElementRemoved(SchemaElement schemaElement);
}
