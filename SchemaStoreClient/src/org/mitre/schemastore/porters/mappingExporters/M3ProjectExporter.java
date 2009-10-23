// Copyright (C) The MITRE Corporation 2008
// ALL RIGHTS RESERVED

package org.mitre.schemastore.porters.mappingExporters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.schemaExporters.M3SchemaExporter;

/**
 * Class for moving SchemaStore projects between SchemaStore repositories
 * @author CWOLf
 */
public class M3ProjectExporter extends MappingExporter
{	
	/** Returns the exporter name */
	public String getName()
		{ return "M3 Project Exporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This exporter can be used to export the M3 files associated with a project"; }
	
	/** Returns the exporter file type */
	public String getFileType()
		{ return ".m3p"; }

	/** Zip up the specified file into the zip output stream */
	private void zipUpFile(ZipOutputStream zipOut, String filename, File file) throws Exception
	{
		FileInputStream in = new FileInputStream(file);
		byte data[] = new byte[100000]; int count;
		zipOut.putNextEntry(new ZipEntry(filename.replaceAll("/", "//")));
		while((count = in.read(data)) > 0)
			zipOut.write(data, 0, count);
		zipOut.closeEntry();
		in.close();
	}
	
	/** Exports the mapping to a SchemaStore archive file */
	public void exportMapping(Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException
	{
		try {				
				
			// Generate a zip file for the specified file
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(file));
			File tempFile = File.createTempFile("M3P", ".m3p");
			
			// Store the schema information for the project
			M3SchemaExporter schemaExporter = new M3SchemaExporter();
			schemaExporter.setClient(client);
			for(Integer schemaID : mapping.getSchemaIDs())
			{
				Schema schema = client.getSchema(schemaID);
				ArrayList<SchemaElement> schemaElements = client.getSchemaInfo(schemaID).getElements(null);
				schemaExporter.exportSchema(schema, schemaElements, tempFile);
				zipUpFile(zipOut,schema.getName()+".m3s",tempFile);
			}
			
			// Store the mapping information for the project
			M3MappingExporter mappingExporter = new M3MappingExporter();
			mappingExporter.setClient(client);
			mappingExporter.exportMapping(mapping, mappingCells, tempFile);
			zipUpFile(zipOut,mapping.getName()+".m3m",tempFile);
			
			// Close the generated zip file
			tempFile.delete();
			zipOut.close();
		}
		catch(Exception e) { System.out.println("(E)M3MappingExporter.exportMapping - " + e.getMessage()); }
	}
}