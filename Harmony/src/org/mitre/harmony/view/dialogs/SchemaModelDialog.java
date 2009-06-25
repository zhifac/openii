// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.mitre.schemastore.model.graph.model.GraphModel;

/**
 * Displays the dialog which allows mapping cells to be accepted/rejected
 * @author CWOLF
 */
public class SchemaModelDialog extends JDialog
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores the schema ID upon which this dialog pertains */
	private Integer schemaID;
	
	/** Stores the list of available models */
	private JList modelList = null;
	
	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		public ButtonPane()
			{ super("OK", "Cancel"); }

		/** Handles selection of okay button */
		protected void button1Action()
		{
			GraphModel model = (GraphModel)modelList.getSelectedValue();
			harmonyModel.getMappingManager().setGraphModel(schemaID, model);
			dispose();
		}

		/** Handles selection of cancel button */
		protected void button2Action()
			{ dispose(); }
	}
		
	/** Initializes the mapping cell dialog */
	public SchemaModelDialog(Integer schemaID, HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		
		// Initialize the selected links
		this.schemaID = schemaID;
		this.harmonyModel = harmonyModel;
		GraphModel model = harmonyModel.getSchemaManager().getGraph(schemaID).getModel();
		
		// Initializes the mapping list
		modelList = new JList(new Vector<GraphModel>(HierarchicalGraph.getGraphModels()));
		modelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		for(int i=0; i<modelList.getModel().getSize(); i++)
			if(modelList.getModel().getElementAt(i).getClass().equals(model.getClass()))
				modelList.setSelectedIndex(i);
		
		// Generate the model list pane
		JPanel modelPane = new JPanel();
		modelPane.setBorder(new EmptyBorder(10,10,0,10));
		modelPane.setLayout(new BorderLayout());
		modelPane.add(new JScrollPane(modelList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),BorderLayout.CENTER);
		
		// Generate the main dialog pane
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createLineBorder(Color.black));
		pane.setLayout(new BorderLayout());
		pane.add(modelPane,BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		
		// Initialize the dialog parameters
		setTitle("Select Schema Model");
		setModal(true);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(pane);
		setSize(200,250);
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		setVisible(true);
	}
}