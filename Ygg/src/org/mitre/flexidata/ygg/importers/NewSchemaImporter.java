// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers;

import org.mitre.schemastore.model.SchemaElement;

import java.util.ArrayList;

/** Importer for generating new schemas */
public class NewSchemaImporter extends Importer
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
	protected void initialize() throws ImporterException {}
	
	/** Returns the list of schemas which this schema extends */
	protected ArrayList<Integer> getExtendedSchemaIDs() throws ImporterException
		{ return new ArrayList<Integer>(); }
	
	/** Returns the schema elements from the specified URI */
	public ArrayList<SchemaElement> getSchemaElements() throws ImporterException
		{ return new ArrayList<SchemaElement>(); }
}