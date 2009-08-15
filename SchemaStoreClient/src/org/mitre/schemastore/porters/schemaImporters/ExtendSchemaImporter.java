// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.ImporterException;

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
	protected void initializeSchemaStructures() throws ImporterException {}
	
	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> generateExtendedSchemaIDs()
	{
		ArrayList<Integer> extendedSchemaIDs = new ArrayList<Integer>();
		extendedSchemaIDs.add(Integer.parseInt(uri.toString()));
		return extendedSchemaIDs;
	}
	
	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> generateSchemaElements() throws ImporterException
		{ return new ArrayList<SchemaElement>(); }
}