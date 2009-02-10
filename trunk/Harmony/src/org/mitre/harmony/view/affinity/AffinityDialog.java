// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.affinity;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.model.MappingManager;
import org.mitre.harmony.matchers.AffinityScore;
import org.mitre.harmony.view.harmonyPane.HarmonyFrame;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.mitre.schemastore.model.graph.FilteredGraph;
import org.mitre.harmony.matchers.affinity.EditDistanceAffiner;
import org.mitre.harmony.matchers.affinity.BagAffiner;
import javax.swing.JTextArea;


/**
 * Displays the affinity info for matchers for 2 schemas
 * @author MDMORSE
 */
public class AffinityDialog extends JDialog implements MouseListener, MouseMotionListener
{
	
	/** Generates description pane */
	private JPanel descriptionPane(AffinityScore aS, AffinityScore aS2, String heading)
	{
		// Sets up the description in a text box
		JTextArea descPane = new JTextArea();
		descPane.setBorder(new EmptyBorder(10,0,0,0));
		descPane.setBackground(null);
		descPane.setLineWrap(true);
		descPane.setWrapStyleWord(true);
		descPane.setEditable(false);
		descPane.setText(heading+ "\nEdit Distance "+aS.getAffineScore()+"\nBag Matcher "+aS2.getAffineScore());
		
		// Place description into a pane
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(descPane,BorderLayout.CENTER);
		return pane;
	}
	
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
		
		EditDistanceAffiner eda = new EditDistanceAffiner();
		BagAffiner baga = new BagAffiner();
		
		//run affinity on 1st schema.
		HierarchicalGraph graph1 = SchemaManager.getGraph(schema1.getId());
		AffinityScore aS1 = eda.getAffinity(new FilteredGraph(graph1));
		AffinityScore bagS1 = baga.getAffinity(new FilteredGraph(graph1));
		//run affinity on 2nd schema.
		HierarchicalGraph graph2 = SchemaManager.getGraph(schema2.getId());
		AffinityScore aS2 = eda.getAffinity(new FilteredGraph(graph2));
		AffinityScore bagS2 = baga.getAffinity(new FilteredGraph(graph2));
		
		aboutPane.setBorder(new EmptyBorder(10,10,10,10));
		aboutPane.setLayout(new BorderLayout());
		aboutPane.add(descriptionPane(aS1,bagS1, "Left Schema"),BorderLayout.WEST);
		aboutPane.add(descriptionPane(aS2,bagS2, "Right Schema"),BorderLayout.EAST);
		return aboutPane;
	}
		
	/** Initializes About dialog */
	public AffinityDialog()
	{
		super(HarmonyFrame.harmonyFrame.getFrame());
		
		// Initialize all settings for the project dialog
		setTitle("Harmony Matcher Schema Affinities");
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