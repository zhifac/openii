// Copyright (C) The MITRE Corporation 2008
// ALL RIGHTS RESERVED

package org.mitre.schemastore.porters.schemaExporters;

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
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.xml.ConvertToXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class for moving SchemaStore format between SchemaStore Instances
 * Given schemaID, exporter finds parent types, and other schema dependencies and exports these too.
 *  
 * @author MDMORSE
 */
public class M3SchemaExporter extends SchemaExporter
{	
	/** Returns the exporter name */
	public String getName()
		{ return "M3 Exporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This exporter can be used to export the M3 format of a schema"; }
	
	/** Returns the exporter file type */
	public String getFileType()
		{ return ".m3s"; }
	
	/** Generates the XML document */
	private Document generateXMLDocument(Integer schemaID) throws Exception
	{
		// Generate the list of all schemas that must be exported to completely define the specified schema
		ArrayList<Integer> schemaIDs = new ArrayList<Integer>(); 
		schemaIDs.add(schemaID);
		schemaIDs.addAll(client.getAncestorSchemas(schemaID));

		// Initialize the XML document
		Document document = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		document = db.newDocument();

		// Create the XML document
		Element rootXMLElement = document.createElement("Schemas");
		for(Integer currSchemaID : schemaIDs)
		{
			// Generate XML for the schema
			Schema currSchema = client.getSchema(currSchemaID);
			ArrayList<Integer> parentSchemaIDs = client.getParentSchemas(currSchemaID);
			Element schemaXMLElement = ConvertToXML.generate(currSchema, parentSchemaIDs, document);

			// Generate XML for the various schema elements
			for(SchemaElement schemaElement : client.getSchemaInfo(currSchemaID).getBaseElements(null))
			{
				Element schemaElementXMLElement = ConvertToXML.generate(schemaElement, document);
				schemaXMLElement.appendChild(schemaElementXMLElement);
			}
			rootXMLElement.appendChild(schemaXMLElement);
		}
		document.appendChild(rootXMLElement);

		// Returns the generated document
		return document;
	}
	
	/** Exports the schema and schema elements to an SchemaStore archive file */
	public void exportSchema(Schema schema, ArrayList<SchemaElement> schemaElements, File file) throws IOException
	{
		try {
			// Generates the XML document
			Document document = generateXMLDocument(schema.getId());

			// Save the XML document to a temporary file
			File tempFile = File.createTempFile("M3S", ".xml");
			FileWriter out = new FileWriter(tempFile);
			OutputFormat format = new OutputFormat(document);
			format.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);
			out.close();
			
			// Generate a zip file for the specified file
			ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(file));
			FileInputStream in = new FileInputStream(tempFile);
			byte data[] = new byte[100000]; int count;
			zipOut.putNextEntry(new ZipEntry("schemas.xml"));
			while((count = in.read(data)) > 0)
				zipOut.write(data, 0, count);
			zipOut.closeEntry();
			zipOut.close();
			in.close();
			
			// Remove the temporary file
			tempFile.delete();
		}
		catch(Exception e) { System.out.println("(E)M3SchemaExporter.exportSchema - " + e.getMessage()); }
	}
}