// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.mappingImporters;

import org.mitre.schemastore.model.*;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.xml.ConvertFromXML;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** Importer for copying schemas from other repositories */
public class M3MappingImporter extends MappingImporter
{	
	/** Stores the list of schemas associated with the mapping */
	private ArrayList<MappingSchema> mappingSchemas = new ArrayList<MappingSchema>();

	/** Stores the list of mapping cells associated with the mapping */
	private ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
	
	/** Returns the importer name */
	public String getName()
		{ return "M3 Importer"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "This importer can be used to download a schema in the M3 format"; }
		
	/** Returns the importer URI type */
	public Integer getURIType()
		{ return FILE; }

	/** Returns the importer URI file types */
	public ArrayList<String> getFileTypes()
	{
		ArrayList<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".m3m");
		return fileTypes;
	}

	/** Initialize the importer */
	protected void initialize() throws ImporterException
	{	
		try {
			// Open up the XML document from reading
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(uri));
			Element element = document.getDocumentElement();
			
			// Gather up the mapping schemas from the XML document
			Mapping mapping = ConvertFromXML.getMapping(element);
			mappingSchemas = new ArrayList<MappingSchema>(Arrays.asList(mapping.getSchemas()));
			ArrayList<HierarchicalGraph> graphs = new ArrayList<HierarchicalGraph>();
			for(MappingSchema schema : mappingSchemas)
				graphs.add(new HierarchicalGraph(client.getGraph(schema.getId()),schema.geetGraphModel()));
			
			// Extract mapping cells from the element
			mappingCells.clear();
			NodeList mappingCellNodeList = element.getElementsByTagName("MappingCell");
			if(mappingCellNodeList != null)
				for(int j=0; j<mappingCellNodeList.getLength(); j++)
				{
					Node node = mappingCellNodeList.item(j);
					if(node instanceof Element)
					{
						MappingCell mappingCell = ConvertFromXML.getMappingCell((Element)node, graphs);
						if(mappingCell!=null) mappingCells.add(mappingCell);
					}
				}
		} catch(Exception e) { throw new ImporterException(ImporterException.IMPORT_FAILURE, e.getMessage()); }
	}

	/** Returns the imported mapping schemas */
	protected ArrayList<MappingSchema> getSchemas() throws ImporterException
		{ return mappingSchemas; }

	/** Returns the imported mapping cells */
	protected ArrayList<MappingCell> getMappingCells() throws ImporterException
		{ return mappingCells; }
}