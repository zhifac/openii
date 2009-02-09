// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.mitre.harmony.model.MappingCellManager;
import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Displays the dialog which allows links to be accepted/rejected
 * @author CWOLF
 */
public class SchemaStatisticsDialog extends JDialog
{
	/** Schema for which the statistics are to be displayed */
	private Integer schemaID;
	
	/** Class for displaying the matched node statistics */
	private class MatchedNodeStatistics extends JPanel
	{
		/** Constructs the pane displaying the matched nodes */
		MatchedNodeStatistics()
			{ setPreferredSize(new Dimension(500,300)); } 
		
		/** Draws the matched node statistics */
		public void paint(Graphics g)
		{
			// Counts for good, weak, and no link nodes
			int goodCount = 0;
			int weakCount = 0;
			int noCount = 0;
		
			// Cycles through all tree nodes to identify good, weak, and no links
			for(SchemaElement schemaElement : SchemaManager.getSchemaElements(schemaID,null))
			{
				double maxConf = Double.MIN_VALUE;
				for(Integer mappingCellID : MappingCellManager.getMappingCellsByElement(schemaElement.getId()))
				{
					double conf = MappingCellManager.getMappingCell(mappingCellID).getScore();
					if(conf>maxConf) maxConf=conf;
				}
				if(maxConf>0.75) goodCount++;
				else if(maxConf>0.25) weakCount++;
				else noCount++;
			}

			// If links exist, calculate the percentages of each type of match
			if(goodCount+weakCount+noCount==0) noCount++;
		
			// Generate the percentages for nodes containing good, weak, and no links
			DefaultPieDataset data = new DefaultPieDataset();
			data.setValue("Good Match",1.0*goodCount/(goodCount+weakCount+noCount));
			data.setValue("Weak Match",1.0*weakCount/(goodCount+weakCount+noCount));
			data.setValue("No Match",1.0*noCount/(goodCount+weakCount+noCount));
			
			// Generate a chart containing a pie plot for this data
			String schemaName = SchemaManager.getSchema(schemaID).getName();
	        JFreeChart chart = ChartFactory.createPieChart("Matched Nodes for Schema "+schemaName,data,true,true,false);

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
	        
	        chart.draw((Graphics2D)g,new Rectangle(500,300));
		}
	}
	
	/** Initializes the link dialog */
	public SchemaStatisticsDialog(Integer schemaID)
	{
		super(HarmonyFrame.harmonyFrame.getFrame());
		
		// Initialize the selected schema
		this.schemaID = schemaID;
		
		// Set up the main dialog pane
		JPanel pane = new JPanel();
		pane.setBorder(new LineBorder(Color.black));
		pane.setLayout(new BorderLayout());
		pane.add(new MatchedNodeStatistics(),BorderLayout.CENTER);
		
		// Initialize the dialog parameters
		setTitle("Statistics for Schema "+SchemaManager.getSchema(schemaID).getName());
		setModal(true);
		setResizable(false);
		setContentPane(pane);
		pack();
		setVisible(true);
	}
}
