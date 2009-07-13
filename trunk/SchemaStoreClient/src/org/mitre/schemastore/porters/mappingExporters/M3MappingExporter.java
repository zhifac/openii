// Copyright (C) The MITRE Corporation 2008
// ALL RIGHTS RESERVED

package org.mitre.schemastore.porters.mappingExporters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.mitre.schemastore.porters.xml.ConvertToXML;
import org.w3c.dom.Document;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xml.serialize.OutputFormat;

/**
 * Class for moving SchemaStore format between SchemaStore Instances
 * Given schemaID, exporter finds parent types, and other schema dependencies and exports these too.
 *  
 * @author MDMORSE
 */
public class M3MappingExporter extends MappingExporter
{	
	/** Returns the exporter name */
	public String getName()
		{ return "M3 Exporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This exporter can be used to export the M3 format of a mapping"; }
	
	/** Returns the exporter file type */
	public String getFileType()
		{ return ".m3m"; }
	
	/** Exports the schema and schema elements to an SchemaStore archive file */
	public void exportMapping(Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException
	{
		// Prepare to export source and target node information
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		
		try {				
			// Initialize the XML document
			Document dom = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();

			// Create the XML document
			ArrayList<HierarchicalGraph> graphs = new ArrayList<HierarchicalGraph>();
			for(MappingSchema mappingSchema : mapping.getSchemas())
				graphs.add(new HierarchicalGraph(client.getGraph(mappingSchema.getId()),mappingSchema.geetGraphModel()));
			dom.appendChild(ConvertToXML.generate(mapping, mappingCells, graphs, dom));

			// Output the XML document to a string buffer
			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(dom);
		}
		catch(Exception e) { System.out.println("(E)M3MappingExporter.exportSchema - " + e.getMessage()); }

		out.close();
	}
}