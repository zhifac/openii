// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.porters.mappingExporters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Class for exporting a data dictionary from the project
 * @author CWOLF
 */
public class DataDictionaryExporter extends MappingExporter
{
	/** Returns the exporter name */
	public String getName()
		{ return "Data Dictionary Exporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This exporter is used to export all pairings of terms within the mapping"; }
	
	/** Returns the file types associated with this converter */
	public String getFileType()
		{ return ".csv"; }
	
	/** Generates a data dictionary for this project */
	public void exportMapping(Integer mappingID, File file) throws IOException
	{
		// Prepare to export source and target node information
		BufferedWriter out = new BufferedWriter(new FileWriter(file));

		// Generate a list of all schema elements and mapping cells
		HashMap<Integer,SchemaElement> elements = getMappingElements(mappingID);
		ArrayList<MappingCell> mappingCells = client.getMappingCells(mappingID);
		
    	// First, output all user selected node pairings
		for(MappingCell mappingCell : mappingCells)
    		if(mappingCell.getAuthor().equals("User") && mappingCell.getScore()>0)
    		{
    			// Gets the elements associated with the mapping cell
    			SchemaElement element1 = elements.get(mappingCell.getElement1());
    			SchemaElement element2 = elements.get(mappingCell.getElement2());
    			
    			// Display the pairing of schema elements
    			String sName = element1.getName();
    			String sDesc = element1.getDescription();
    			String tName = element2.getName();
    			String tDesc = element2.getDescription();
    			out.write(sName+",\""+sDesc+"\","+tName+",\""+tDesc+"\"\n");
    		}

    	// Then output all source nodes with no links
		for(SchemaElement element : elements.values())
			if(getMappingCellsByElement(element.getId(),mappingCells).size()==0)
  				out.write(element.getName()+",\""+element.getDescription().replace('\n',' ').replaceAll("\"","\"\"")+"\",,\n");
 
    	out.close();
	}
}