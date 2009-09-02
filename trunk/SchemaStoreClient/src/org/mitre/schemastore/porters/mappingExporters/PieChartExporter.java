// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.porters.mappingExporters;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Class for exporting projects to a pie chart showing schemas' matched percentage
 * @author CWOLF
 */
public class PieChartExporter extends MappingExporter
{
	/** Returns the exporter name */
	public String getName()
		{ return "Pie Chart Exporter"; }
	
	/** Returns the exporter description */
	public String getDescription()
		{ return "This exporter is used to export pie charts illustrating how matched the various schemas are"; }

	/** Returns the file types associated with this converter */
	public String getFileType()
		{ return ".zip"; }

	/** Copies the specified file with the specified name to the specified zip output stream */
	private void copyFileToZip(File file, String name, ZipOutputStream out)
	{
		try {
			int byteCount;
			byte[] data = new byte[4096];
			FileInputStream in = new FileInputStream(file);
			ZipEntry entry = new ZipEntry(name);
			out.putNextEntry(entry);
			while((byteCount = in.read(data)) != -1)
				out.write(data, 0, byteCount);
			in.close();
		} catch(Exception e) {}
	}
	
	/** Generates pie charts showing the matched ratio for all schemas in this project */
	public void exportMapping(Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException
	{
		// Initialize the output stream for the zip file
	    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));

		// Store the schema pie charts to the zip file
	    for(MappingSchema schema : mapping.getSchemas())
	    {
	    	String fileName = schema.getName() + "(" + schema.getId() + ").jpg";
	    	copyFileToZip(generateFile(schema,mappingCells),fileName,out);
		}
		
		// Close the zip file
		out.close();
	}
	
	/** Return a file containing a pie chart showing the matched ratio for the specified schema */
	private File generateFile(MappingSchema schema, ArrayList<MappingCell> mappingCells) throws IOException
	{
		// Counts for good, weak, and no link nodes
		int goodCount = 0;
		int weakCount = 0;
		int noCount = 0;
		
		// Cycles through all tree nodes to identify good, weak, and no links
		HierarchicalSchemaInfo schemaInfo = new HierarchicalSchemaInfo(client.getSchemaInfo(schema.getId()),schema.geetSchemaModel());
		for(SchemaElement element : schemaInfo.getHierarchicalElements())
		{
			double maxScore = Double.MIN_VALUE;
   			for(MappingCell mappingCell : getMappingCellsByElement(element.getId(), mappingCells))
   			{
   				double score = mappingCell.getScore();
   				if(score>maxScore) maxScore=score;
   			}
   			if(maxScore>0.75) goodCount++;
   			else if(maxScore>0.25) weakCount++;
   			else noCount++;
		}

		// If links exist, calculate the percentages of each type of match
		if(goodCount+weakCount+noCount==0) noCount++;

		// Generate the percentages for nodes containing good, weak, and no links
		File tempFile = File.createTempFile("PieChart",".jpg");
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("Good Match",1.0*goodCount/(goodCount+weakCount+noCount));
		data.setValue("Weak Match",1.0*weakCount/(goodCount+weakCount+noCount));
		data.setValue("No Match",1.0*noCount/(goodCount+weakCount+noCount));
		
		// Generate a chart containing a pie plot for this data
        JFreeChart chart = ChartFactory.createPieChart("Matched Nodes for Schema "+schema.getName(),data,true,true,false);

        // Modify the pie plot settings
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        plot.setSectionPaint(0,Color.GREEN);
        plot.setSectionPaint(1,Color.YELLOW);
        plot.setSectionPaint(2,Color.RED);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}"));
        
        // Save the generated chart as a JPEG
        ChartUtilities.saveChartAsJPEG(tempFile,chart,500,300);
		return tempFile;
	}
}