// Copyright (C) The MITRE Corporation 2008
// ALL RIGHTS RESERVED

package org.mitre.schemastore.porters.mappingExporters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.porters.xml.ConvertToXML;
import org.w3c.dom.Document;

/**
 * Class for moving SchemaStore mappings between SchemaStore repositories
 * @author CWOLf
 */
public class M3MappingExporter extends MappingExporter
{	
	/** Returns the exporter name */
	public String getName()
		{ return "M3 Mapping Exporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This exporter can be used to export the M3 format of a mapping"; }
	
	/** Returns the exporter file type */
	public String getFileType()
		{ return ".m3m"; }
	
	/** Generates the XML document */
	private Document generateXMLDocument(Mapping mapping, ArrayList<MappingCell> mappingCells) throws Exception
	{
		// Initialize the XML document
		Document document = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		document = db.newDocument();

		// Create the XML document
		ArrayList<HierarchicalSchemaInfo> schemaInfoList = new ArrayList<HierarchicalSchemaInfo>();
		for(MappingSchema mappingSchema : mapping.getSchemas())
			schemaInfoList.add(new HierarchicalSchemaInfo(client.getSchemaInfo(mappingSchema.getId()),mappingSchema.geetSchemaModel()));
		document.appendChild(ConvertToXML.generate(mapping, mappingCells, schemaInfoList, document));

		// Returns the generated document
		return document;
	}
	
	/** Exports the mapping to a SchemaStore archive file */
	public void exportMapping(Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException
	{
		try {				
			// Generates the XML document
			Document document = generateXMLDocument(mapping, mappingCells);

			// Save the XML document to a temporary file
			File tempFile = File.createTempFile("M3M", ".xml");
			FileWriter out = new FileWriter(tempFile);
			OutputFormat format = new OutputFormat(document);
			format.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);
				
			// Generate a zip file for the specified file
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(file));
			FileInputStream in = new FileInputStream(tempFile);
			byte data[] = new byte[100000]; int count;
			zipOut.putNextEntry(new ZipEntry("mapping.xml"));
			while((count = in.read(data)) > 0)
				zipOut.write(data, 0, count);
			zipOut.closeEntry();
			zipOut.close();
			in.close();
			
			// Remove the temporary file
			tempFile.delete();			
		}
		catch(Exception e) { System.out.println("(E)M3MappingExporter.exportMapping - " + e.getMessage()); }
	}
}