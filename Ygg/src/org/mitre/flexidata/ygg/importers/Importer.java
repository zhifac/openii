// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.importers;

import java.io.File;

import org.mitre.flexidata.ygg.model.SchemaManager;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;

/** Abstract Importer class */
public abstract class Importer
{
	// Defines the various types of URIs that may be requested
	public static final Integer NONE = 0;
	public static final Integer SCHEMA = 1;
	public static final Integer REPOSITORY = 2;
	public static final Integer FILE = 3;
	
	// Defines the various base domain types
	public static final String ANY = "Any";
	public static final String INTEGER = "Integer";
	public static final String DOUBLE = "Double";
	public static final String STRING = "String";
	public static final String DATETIME = "DateTime";
	public static final String BOOLEAN = "Boolean";
	
	/** Stores a auto-increment counter for handing out IDs */
	private static Integer autoIncrementedId = 0;
	
	/** Returns the next available ID */
	public static Integer nextId()
		{ return autoIncrementedId++; }

	/** Returns the importer name */
	abstract public String getName();
	
	/** Returns the importer description */
	abstract public String getDescription();
	
	/** Returns the importer URI type */
	abstract public Integer getURIType();
	
	/** Returns the importer URI file types (only needed when URI type is FILE) */
	public ArrayList<String> getURIFileTypes() { return new ArrayList<String>(); }
	
	/** Returns the list of schemas which this schema extends */
	public ArrayList<Integer> getExtendedSchemaIDs(String uri) throws ImporterException
		{ return new ArrayList<Integer>(); }
	
	/** Returns the schema elements from the specified URI */
	abstract public ArrayList<SchemaElement> getSchemaElements(Integer schemaID, String uri) throws ImporterException;
	
	/** Returns the file filter associated with this importer */
	final public FileFilter getFileFilter()
	{
		final class ImporterFileFilter extends FileFilter
		{
			/** Indicates if the file is acceptable */
			public boolean accept(File file)
			{
				if(file.isDirectory()) return true;
				for(String uriFileType : getURIFileTypes())
					if(file.getName().endsWith(uriFileType)) return true;
				return false;
			}
			
			/** Provides a description of what constitutes an acceptable file */
			public String getDescription()
			{
				String description = getName() + "(";
				for(String uriFileType : getURIFileTypes())
					description += uriFileType + ",";
				return description.replaceAll(",$",")");
			}
		}
		return new ImporterFileFilter();
	}
	
	/** Imports the specified URI */
	final public Integer importSchema(String name, String author, String description, String uri) throws ImporterException
	{
		// Generate the schema
		Schema schema = new Schema(nextId(),name,author,uri==null?"":uri,"",description,false);
		ArrayList<Integer> extendedSchemaIDs = getExtendedSchemaIDs(uri);
		ArrayList<SchemaElement> schemaElements = getSchemaElements(schema.getId(),uri);		
		
		// Import the schema into the repository
		if(!SchemaManager.importSchema(schema, extendedSchemaIDs, schemaElements))
			throw new ImporterException(ImporterException.IMPORT_FAILURE,"A failure occurred in transferring the schema to the repository");
		return schema.getId();
	}
	
	/** Outputs the importer name */
	public String toString()
		{ return getName(); }
}
