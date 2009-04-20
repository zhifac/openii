// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import org.mitre.schemastore.model.SchemaElement;

import java.util.ArrayList;

/** Importer for extending schemas within the repository */
public class ExtendSchemaImporter extends SchemaImporter
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
	
	/** Initializes the importer for the specified URI */
	protected void initialize() throws SchemaImporterException {}
	
	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> getExtendedSchemaIDs()
	{
		ArrayList<Integer> extendedSchemaIDs = new ArrayList<Integer>();
		extendedSchemaIDs.add(Integer.parseInt(uri.toString()));
		return extendedSchemaIDs;
	}
	
	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> getSchemaElements() throws SchemaImporterException
		{ return new ArrayList<SchemaElement>(); }
}