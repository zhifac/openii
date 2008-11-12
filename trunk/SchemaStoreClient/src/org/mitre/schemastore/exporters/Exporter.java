// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.exporters;

import java.util.ArrayList;

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
	
	/** Exports the specified schema*/
	abstract public StringBuffer exportSchema(Integer schemaID, ArrayList<SchemaElement> schemaElements);
	
	/** Outputs the exporter name */
	public String toString()
		{ return getName(); }
}
