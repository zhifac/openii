// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers;

import org.mitre.schemastore.model.SchemaElement;

import java.net.URI;
import java.util.ArrayList;

/** Importer for extending schemas within the repository */
public class ExtendSchemaImporter extends Importer
{
	/** Returns the importer name */
	public String getName()
		{ return "Extend Schema"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "This method allows the extending of a schema in the repository"; }
	
	/** Returns the importer URI type */
	public Integer getURIType()
		{ return SCHEMA; }
	
	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> getExtendedSchemaIDs(URI uri)
	{
		ArrayList<Integer> extendedSchemaIDs = new ArrayList<Integer>();
		extendedSchemaIDs.add(Integer.parseInt(uri.toString()));
		return extendedSchemaIDs;
	}
	
	/** Returns the schema elements from the specified URI */
	protected ArrayList<SchemaElement> getSchemaElements(Integer schemaID, URI uri) throws ImporterException
		{ return new ArrayList<SchemaElement>(); }
}