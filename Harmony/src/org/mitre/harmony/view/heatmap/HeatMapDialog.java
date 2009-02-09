// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.heatmap;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.model.MappingCellManager;
import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.model.MappingManager;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/**
 * Displays the heatmap for Harmony
 * @author MDMORSE
 */
public class HeatMapDialog extends JDialog implements MouseListener, MouseMotionListener
{
	
	/** Initializes About pane within About dialog */
	private JPanel aboutPane()
	{		
		JPanel aboutPane = new JPanel();
		
		//init match variables.
		//ArrayList<MappingCell> cells = MappingCellManager.getMappingCells();
		ArrayList<Integer> schemas = MappingManager.getSchemas();
		//Assume only 2 schemas being matched.
		Schema schema1 = SchemaManager.getSchema(schemas.get(0));
		Schema schema2 = SchemaManager.getSchema(schemas.get(1));
		/*HierarchicalGraph graph1 = SchemaManager.getGraph(schema1.getId());
		HierarchicalGraph graph2 = SchemaManager.getGraph(schema2.getId());
		
		ArrayList<SchemaElement> elements1 = graph1.getGraphElements();
		ArrayList<SchemaElement> elements2 = graph2.getGraphElements();
		
		int x1 = elements1.size();
		int x2 = elements2.size();
		
		//create grid with matcher scores in it.
		double[][] data = new double[x1][x2];
		
		int SpotX = 0;
		int SpotY = 0;
		for(SchemaElement se1: elements1){
			for(SchemaElement se2: elements2){
				Integer id = MappingCellManager.getMappingCellID(se1.getId(), se2.getId());
				if(id == null){
					SpotY++;
					continue;
				}
				MappingCell mc = MappingCellManager.getMappingCell(id);
				data[SpotX][SpotY++] = mc.getScore();
			}
			SpotX++;
			SpotY=0;
		}
		
		//get labels.
		String[] LabelsX = new String[x1];
		String[] LabelsY = new String[x2];
		SpotX=0;
		for(SchemaElement se:elements1){
			LabelsX[SpotX++] = se.getName();
		}
		SpotX=0;
		for(SchemaElement se:elements2){
			LabelsY[SpotX++] = se.getName();
		}*/
		
		//HeatMap hm = new HeatMap(data,HeatMap.GRADIENT_RED_TO_GREEN);
		HeatMap hm = new HeatMap();
		hm.setUp(schema1, schema2);
		hm.setFocusable(true);
		hm.addKeyListener(hm);
		hm.addMouseListener(hm);
		hm.addMouseMotionListener(hm);
		aboutPane.setBorder(new EmptyBorder(10,10,10,10));
		aboutPane.setLayout(new BorderLayout());
		aboutPane.add(hm);
		return aboutPane;
	}
	
	/** Initializes About dialog */
	public HeatMapDialog()
	{
		super(HarmonyFrame.harmonyFrame.getFrame());
		
		// Initialize all settings for the project dialog
		setTitle("Harmony Heat Map Visualization");
		setSize(600,375);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(aboutPane());
    	setVisible(true);
   	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
