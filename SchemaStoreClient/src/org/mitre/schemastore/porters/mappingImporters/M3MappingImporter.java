// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.porters.mappingImporters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;
import org.mitre.schemastore.porters.ImporterException;
import org.mitre.schemastore.porters.xml.ConvertFromXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** Importer for copying mappings from other repositories */
public class M3MappingImporter extends MappingImporter
{	
	/** Stores the M3 document to be imported */
	private Element element;
	
	/** Returns the importer name */
	public String getName()
		{ return "M3 Mapping Importer"; }
	
	/** Returns the importer description */
	public String getDescription()
		{ return "This importer can be used to download a mapping in the M3 format"; }
		
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
			// Uncompresses the file
			ZipInputStream zipIn = new ZipInputStream(new FileInputStream(new File(uri)));
			File tempFile = File.createTempFile("M3M", ".xml");
			FileOutputStream out = new FileOutputStream(tempFile);
			zipIn.getNextEntry();
			byte data[] = new byte[100000]; int count;
			while((count = zipIn.read(data)) > 0)
				out.write(data, 0, count);
			zipIn.close();
			out.close();
			
			// Retrieve the XML document element
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(tempFile);
			element = document.getDocumentElement();
			tempFile.delete();
		}
		catch(Exception e) { throw new ImporterException(ImporterException.IMPORT_FAILURE, e.getMessage()); }
	}

	/** Returns the source schema in the mapping */
	public ProjectSchema getSourceSchema() throws ImporterException
	{
		try { return ConvertFromXML.getSourceSchema(element); }
		catch(Exception e) { throw new ImporterException(ImporterException.IMPORT_FAILURE, e.getMessage()); }
	}

	/** Returns the target schema in the mapping */
	public ProjectSchema getTargetSchema() throws ImporterException
	{
		try { return ConvertFromXML.getTargetSchema(element); }
		catch(Exception e) { throw new ImporterException(ImporterException.IMPORT_FAILURE, e.getMessage()); }
	}

	/** Returns the imported mapping cells */
	public ArrayList<MappingCell> getMappingCells() throws ImporterException
	{
		try {			
			// Retrieve info for the source and target schemas (using original schema names for matching purposes)
			HierarchicalSchemaInfo sourceInfo = new HierarchicalSchemaInfo(client.getSchemaInfo(source.getId()));
			HierarchicalSchemaInfo targetInfo = new HierarchicalSchemaInfo(client.getSchemaInfo(target.getId()));
			
			// Generate the list of mapping cells
			ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
			NodeList mappingCellNodeList = element.getElementsByTagName("MappingCell");
			if(mappingCellNodeList != null)
				for(int j=0; j<mappingCellNodeList.getLength(); j++)
				{
					Node node = mappingCellNodeList.item(j);
					if(node instanceof Element)
					{
						MappingCell mappingCell = ConvertFromXML.getMappingCell((Element)node, sourceInfo, targetInfo);
						if(mappingCell!=null) mappingCells.add(mappingCell);
					}
				}
			return mappingCells;
		} catch(Exception e) { throw new ImporterException(ImporterException.IMPORT_FAILURE, e.getMessage()); }
	}
}