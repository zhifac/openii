// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.schemaSettings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.AbstractButtonPane;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.schemaInfo.model.SchemaModel;

/**
 * Displays the dialog containing all mapping information
 * @author CWOLF
 */
public class SchemaSettingsDialog extends JDialog
{	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	// Stores the various panes used in this pane
	private JTabbedPane tabbedPane = new JTabbedPane();
	private SchemaSelectionPane selectionPane = null;
	private SchemaDisplayPane displayPane = null;
	private SchemaModelPane modelPane = null;

	/** Stores the list of selected schemas */
	private ArrayList<Integer> selectedSchemas = new ArrayList<Integer>();
	
	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		public ButtonPane()
			{ super(new String[]{"OK", "Cancel"},1,2); }

		/** Handles selection of button */
		protected void buttonPressed(String label)
		{
			if(label.equals("OK")) save();
			dispose();
		}
	}
	
	/** Initializes the properties dialog */
	public SchemaSettingsDialog(HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		this.harmonyModel = harmonyModel;
		
		// Initializes the selection and display panes
		selectionPane = new SchemaSelectionPane(harmonyModel);
		displayPane = new SchemaDisplayPane(harmonyModel);
		modelPane = new SchemaModelPane(harmonyModel);
		
		// Populate panes with schema information
		for(Integer schemaID : harmonyModel.getMappingManager().getSchemaIDs())
			selectSchema(schemaID);

		// Initializes the selection pane
		selectionPane.setPreferredSize(new Dimension(200,0));
		
		// Constructs the tabbed panes
		tabbedPane.setBorder(new EmptyBorder(0,8,1,0));
		tabbedPane.setFocusable(false);
		tabbedPane.addTab("Display",displayPane);
		tabbedPane.addTab("Model",modelPane);
		
		// Initializes the schema pane
		JPanel schemaPane = new JPanel();
		schemaPane.setBorder(new EmptyBorder(10,10,10,10));
		schemaPane.setLayout(new BorderLayout());
		schemaPane.add(selectionPane,BorderLayout.WEST);
		schemaPane.add(tabbedPane,BorderLayout.CENTER);
		
		// Constructs the content pane 
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(15,15,10,15));
		pane.setLayout(new BorderLayout());
		pane.add(schemaPane,BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		
		// Set up loader dialog layout and contents
		setTitle("Mapping Properties");
		setModal(true);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(pane);
		pack();
		setSize(550,getHeight());
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		setVisible(true);
   	}
	
	/** Select a schema */
	void selectSchema(Integer schemaID)
	{
		selectedSchemas.add(schemaID);
		selectionPane.schemaSelected(schemaID, true);
		displayPane.selectSchema(schemaID);
		modelPane.selectSchema(schemaID);
	}
	
	/** Unselect a schema */
	void unselectSchema(Integer schemaID)
	{
		selectedSchemas.remove(schemaID);
		selectionPane.schemaSelected(schemaID, false);
		displayPane.unselectSchema(schemaID);
		modelPane.unselectSchema(schemaID);
	}
	
	/** Saves the mapping schemas */
	void save()
	{
		ArrayList<MappingSchema> schemas = new ArrayList<MappingSchema>();
		for(Integer schemaID : selectedSchemas)
		{
			SchemaModel schemaModel = modelPane.getModel(schemaID);
			Integer side = displayPane.getSide(schemaID);
			Schema schema = harmonyModel.getSchemaManager().getSchema(schemaID);
			schemas.add(new MappingSchema(schemaID,schema.getName(),schemaModel==null?null:schemaModel.getClass().getName(),side));
		}
		harmonyModel.getMappingManager().setSchemas(schemas);
	}
}
