// Copyright (C) The MITRE Corporation 2008
// ALL RIGHTS RESERVED

package org.mitre.schemastore.porters.schemaExporters;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.xml.ConvertToXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xml.serialize.OutputFormat;

/**
 * Class for moving SchemaStore format between SchemaStore Instances
 * Given schemaID, exporter finds parent types, and other schema dependencies and exports these too.
 *  
 * @author MDMORSE
 */
public class M3Exporter extends SchemaExporter
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
	
	/** Exports the schema and schema elements to an SchemaStore archive file */
	public StringBuffer exportSchema(Integer schemaID, ArrayList<SchemaElement> schemaElements)
	{
		StringBuffer out = new StringBuffer();
		
		try {		
			// Generate the list of all schemas that must be exported to completely define the specified schema
			ArrayList<Integer> schemaIDs = new ArrayList<Integer>(); 
			schemaIDs.add(schemaID);
			schemaIDs.addAll(client.getAncestorSchemas(schemaID));
		
			// Initialize the XML document
			Document dom = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();

			// Create the XML document
			Element rootXMLElement = dom.createElement("SchemaStoreM3Export");
			rootXMLElement.appendChild(dom.createTextNode("Schemas:"));
			for(Integer schemaid: schemaIDs)
			{
				// Generate XML for the schema
				Schema schema = client.getSchema(schemaid);
				ArrayList<Integer> parentSchemaIDs = client.getParentSchemas(schemaid);
				Element schemaXMLElement = ConvertToXML.generate(schema, parentSchemaIDs, dom);

				// Generate XML for the various schema elements
				for(SchemaElement schemaElement : client.getGraph(schemaid).getBaseElements(null))
				{
					Element schemaElementXMLElement = ConvertToXML.generate(schemaElement, dom);
					schemaXMLElement.appendChild(schemaElementXMLElement);
				}
				rootXMLElement.appendChild(schemaXMLElement);
			}
			dom.appendChild(rootXMLElement);

			// Output the XML document to a string buffer
			StringWriter sw = new StringWriter();
			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer(sw, format);
			serializer.serialize(dom);
			out = sw.getBuffer();
		}
		catch(Exception e) { System.out.println("(E)M3Exporter.exportSchema - " + e.getMessage()); }

		return out;
	}
}