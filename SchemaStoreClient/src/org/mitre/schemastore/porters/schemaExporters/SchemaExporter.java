// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaExporters;

import java.util.ArrayList;

import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.Porter;

/** Abstract Schema Exporter class */
public abstract class SchemaExporter extends Porter
{
	/** Returns the exporter file type */
	abstract public String getFileType();
	
	/** Exports the specified schema*/
	abstract public StringBuffer exportSchema(Integer schemaID, ArrayList<SchemaElement> schemaElements);
}
