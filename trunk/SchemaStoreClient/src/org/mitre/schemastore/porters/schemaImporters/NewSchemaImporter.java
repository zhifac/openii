// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.schemaImporters;

import org.mitre.schemastore.model.SchemaElement;

import java.util.ArrayList;

/** Importer for generating new schemas */
public class NewSchemaImporter extends SchemaImporter
{
	/** Returns the importer name */
	public String getName()
		{ return "Create New Schema"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "This method creates a basic schema with no schema elements"; }
	
	/** Returns the importer URI type */
	public Integer getURIType()
		{ return NONE; }
	
	/** Initializes the importer for the specified URI */
	protected void initialize() throws SchemaImporterException {}
	
	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> getExtendedSchemaIDs() throws SchemaImporterException
		{ return new ArrayList<Integer>(); }
	
	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> getSchemaElements() throws SchemaImporterException
		{ return new ArrayList<SchemaElement>(); }
}