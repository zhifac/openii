// Copyright (C) The MITRE Corporation 2008
// ALL RIGHTS RESERVED

package org.mitre.schemastore.porters.mappingExporters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.mitre.schemastore.model.Function;
import org.mitre.schemastore.model.FunctionImp;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.porters.xml.ConvertToXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
	
	/** Retrieve the schema info for the specified schema */
	private HierarchicalSchemaInfo getSchemaInfo(Project project, Integer schemaID) throws Exception
	{
		for(ProjectSchema schema : project.getSchemas())
			if(schema.getId().equals(schemaID))
				return new HierarchicalSchemaInfo(client.getSchemaInfo(schemaID),schema.geetSchemaModel());
		return null;
	}
	
	/** Retrieve the functions used by the mapping cells */
	private HashMap<Function,ArrayList<FunctionImp>> getFunctions(ArrayList<MappingCell> mappingCells) throws Exception
	{
		// Identify all function IDs referenced by the mapping
		HashSet<Integer> functionIDHash = new HashSet<Integer>();
		for(MappingCell mappingCell : mappingCells)
			if(mappingCell.getFunctionID()!=null)
				functionIDHash.add(mappingCell.getFunctionID());

		// Generate a list of all functions referenced (directly and indirectly) by the mapping
		HashSet<Function> functionHash = new HashSet<Function>();
		for(Integer functionID : functionIDHash)
		{
			functionHash.add(client.getFunction(functionID));
			functionHash.addAll(client.getReferencedFunctions(functionID));
		}
		
		// Create the array of functions and their defined implementations
		HashMap<Function,ArrayList<FunctionImp>> functions = new HashMap<Function,ArrayList<FunctionImp>>();
		for(Function function : functionHash)
			functions.put(function,client.getFunctionImps(function.getId()));
		return functions;
	}
	
	/** Generates the XML document */
	private Document generateXMLDocument(Project project, Mapping mapping, ArrayList<MappingCell> mappingCells) throws Exception
	{
		// Initialize the XML document
		Document document = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		document = db.newDocument();

		// Retrieve the source and target schema info
		HierarchicalSchemaInfo sourceInfo = getSchemaInfo(project, mapping.getSourceId());
		HierarchicalSchemaInfo targetInfo = getSchemaInfo(project, mapping.getTargetId());
		
		// Retrieve the functions used in this mapping
		HashMap<Function,ArrayList<FunctionImp>> functions = getFunctions(mappingCells);
		
		// Create XML for the mapping
		Element mappingXMLElement = ConvertToXML.generate(mapping, sourceInfo, targetInfo, document);
		for(Function function : functions.keySet())
			mappingXMLElement.appendChild(ConvertToXML.generate(function, functions.get(function), document));
		for(MappingCell mappingCell : mappingCells)
			mappingXMLElement.appendChild(ConvertToXML.generate(mappingCell, sourceInfo, targetInfo, document));
		
		// Returns the generated XML document
		document.appendChild(mappingXMLElement);
		return document;
	}
	
	/** Exports the mapping to a SchemaStore archive file */
	public void exportMapping(Project project, Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException
	{
		try {				
			// Generates the XML document
			Document document = generateXMLDocument(project, mapping, mappingCells);

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