// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.exporters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.filechooser.FileFilter;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Class for exporting a data dictionary from the project
 * @author CWOLF
 */
public class DataDictionaryExporter extends Exporter
{
	/** Constructs the DataDictionary exporter */
	public DataDictionaryExporter(HarmonyModel harmonyModel)
		{ super(harmonyModel); }
	
	/** Returns the file types associated with this converter */
	public String getFileType()
		{ return "csv"; }
	
	/** Returns a file filter for this converter */
	public FileFilter getFileFilter()
	{
		class CsvFilter extends FileFilter
		{
			public boolean accept(File file)
			{
				if(file.isDirectory()) return true;
				if(file.toString().endsWith(".csv")) return true;
				return false;
			}

			public String getDescription()
				{ return "Data Dictionary (.csv)"; }
		}
		return new CsvFilter();
	}
	
	/** Generates a data dictionary for this project */
	public void exportTo(File file) throws IOException
	{
		// Prepare to export source and target node information
		BufferedWriter out = new BufferedWriter(new FileWriter(file));

    	// First, output all user selected node pairings
  		for(MappingCell mappingCell : getModel().getMappingCellManager().getMappingCells())
    		if(mappingCell.getAuthor().equals("User") && mappingCell.getScore()>0)
    		{
    			// Gets the elements associated with the mapping cell
    			SchemaElement element1 = getModel().getSchemaManager().getSchemaElement(mappingCell.getElement1());
    			SchemaElement element2 = getModel().getSchemaManager().getSchemaElement(mappingCell.getElement2());
    			
    			String sName = element1.getName();
    			String sDesc = element1.getDescription();
    			String tName = element2.getName();
    			String tDesc = element2.getDescription();
    			out.write(sName+",\""+sDesc+"\","+tName+",\""+tDesc+"\"\n");
    		}

    	// Next, output all source nodes with no links
  		for(Integer schemaID : getModel().getMappingManager().getSchemas())
  			for(SchemaElement element : getModel().getSchemaManager().getSchemaElements(schemaID, null))
  				if(getModel().getMappingCellManager().getMappingCellsByElement(element.getId()).size()==0)
    				out.write(element.getName()+",\""+element.getDescription().replace('\n',' ').replaceAll("\"","\"\"")+"\",,\n");
 
    	out.close();
	}
}