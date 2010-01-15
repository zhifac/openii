// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.projectImporters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.mappingImporters.M3MappingImporter;
import org.mitre.schemastore.porters.schemaImporters.M3SchemaImporter;

/** Importer for copying projects from other repositories */
public class M3ProjectImporter extends ProjectImporter
{	
	/** Stores the schemas associated this project */
	private ArrayList<ProjectSchema> schemas = null;
	
	/** Stores the mappings associated with this project */
	private HashMap<Mapping,ArrayList<MappingCell>> mappings = null;
	
	/** Returns the importer name */
	public String getName()
		{ return "M3 Project Importer"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "This importer can be used to download a project in the M3 format"; }
		
	/** Returns the importer URI type */
	public Integer getURIType()
		{ return FILE; }

	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".m3p");
		return fileTypes;
	}

	/** Initialize the importer */
	protected void initialize() throws ImporterException {}

	/** Unzip the next file from the zip input stream */
	private void unzipFile(ZipInputStream zipIn, File file) throws Exception
	{
		FileOutputStream out = new FileOutputStream(file);
		byte data[] = new byte[100000]; int count;
		while((count = zipIn.read(data)) > 0)
			out.write(data, 0, count);		
	}
	
	/** Retrieves the schemas and mappings from file */
	private void retrieveSchemasAndMapping() throws ImporterException
	{
		// Retrieve all schemas and mapping cells		
		try {
			// Open up the zip input stream
			ZipInputStream zipIn = new ZipInputStream(new FileInputStream(new File(uri)));
			File tempFile = File.createTempFile("M3P", ".m3p");
			HashMap<Integer,Integer> schemaIDMap = new HashMap<Integer,Integer>();
			
			ZipEntry entry;
			while((entry=zipIn.getNextEntry())!=null)
			{
				// Retrieve the next file from the zip file
				unzipFile(zipIn,tempFile);
				
				// Load in schema information
				if(entry.getName().endsWith(".m3s"))
				{
					// Extract the schema from the importer
					M3SchemaImporter schemaImporter = new M3SchemaImporter();
					schemaImporter.setClient(client);
					Schema schema = schemaImporter.getSchema(tempFile.toURI());

					// Import the schema
					Integer schemaID = null;
					try {
						String name = schema.getName();
						String author = schema.getAuthor();
						String description = schema.getDescription();
						schemaID = schemaImporter.importSchema(name, author, description, tempFile.toURI());
					}
					catch(ImporterException e)
					{
						if(e.getExceptionType().equals(ImporterException.DUPLICATION_FAILURE))
							schemaID = Integer.valueOf(e.getMessage().replaceAll(".*\\(","").replaceAll("\\).*",""));
					}
					if(schemaID==null) throw new Exception("Failed to import schema " + schema.getName());
					
					// Store the ID associated with the imported schema
					schemaIDMap.put(schema.getId(),schemaID);
				}
				
				// Retrieve the mapping information
				if(entry.getName().endsWith(".m3m"))
				{
					M3MappingImporter mappingImporter = new M3MappingImporter();
					mappingImporter.setClient(client);
					mappingImporter.initialize(tempFile.toURI());
					
					// Align the mapping schemas with imported schemas
					Integer sourceID = mappingImporter.getSourceSchema().getId();
					Integer targetID = mappingImporter.getTargetSchema().getId();
					mappingImporter.setSchemas(schemaIDMap.get(sourceID), schemaIDMap.get(targetID));
					
					// Retrieve the mapping cells
					Mapping mapping = new Mapping(null,null,sourceID,targetID);
					mappings.put(mapping, mappingImporter.getMappingCells());
				}
			}
			
			// Close the zip input stream
			tempFile.delete();
			zipIn.close();
		}
		catch(Exception e) { throw new ImporterException(ImporterException.IMPORT_FAILURE, e.getMessage()); }
	}
	
	/** Returns the schemas associated with this project */
	public ArrayList<ProjectSchema> getSchemas() throws ImporterException
	{
		if(schemas==null) retrieveSchemasAndMapping();
		return schemas;
	}
	
	/** Returns the mappings associated with this project */
	protected HashMap<Mapping,ArrayList<MappingCell>> getMappings() throws ImporterException
	{
		if(mappings==null) retrieveSchemasAndMapping();
		return mappings;
	}
}