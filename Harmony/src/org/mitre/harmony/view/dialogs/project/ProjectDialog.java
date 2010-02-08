// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.project;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.AbstractButtonPane;
import org.mitre.harmony.view.dialogs.project.mappings.MappingPane;
import org.mitre.harmony.view.dialogs.project.schemas.SchemaPane;
import org.mitre.schemastore.model.Mapping;

/**
 * Displays the project dialog
 * @author CWOLF
 */
public class ProjectDialog extends JDialog
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	// Stores the various panes used in this pane
	private SchemaPane schemaPane = null;
	private MappingPane mappingPane = null;
	
	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		public ButtonPane()
			{ super(new String[]{"OK", "Cancel"},1,2); }

		/** Handles selection of button */
		protected void buttonPressed(String label)
			{ if(label.equals("OK")) save(); dispose(); }
	}
	
	/** Initializes the project dialog */
	public ProjectDialog(HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		this.harmonyModel = harmonyModel;
		
		// Constructs the tabbed panes
		JPanel infoPane = new JPanel();
		infoPane.setLayout(new GridLayout(2,1));
		infoPane.add(schemaPane = new SchemaPane(harmonyModel));
		infoPane.add(mappingPane = new MappingPane(this));
		
		// Constructs the content pane 
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(10,10,0,10));
		pane.setLayout(new BorderLayout());
		pane.add(infoPane,BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		
		// Set up loader dialog layout and contents
		setTitle("Project Configuration");
		setModal(true);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(pane);
		pack();
		setSize(550,getHeight());
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		setVisible(true);
   	}
	
	/** Returns the harmony model */
	public HarmonyModel getHarmonyModel()
		{ return harmonyModel; }
	
	/** Returns the schema pane */
	public SchemaPane getSchemaPane()
		{ return schemaPane; }

	/** Returns the mapping pane */
	public MappingPane getMappingPane()
		{ return mappingPane; }
	
	/** Adds a schema */
	public void addSchema(Integer schemaID)
		{ schemaPane.selectSchema(schemaID); }
	
	/** Adds a mapping */
	public void addMapping(Mapping mapping)
		{ mappingPane.addMapping(mapping); }
	
	/** Saves the project */
	void save()
		{ schemaPane.save(); mappingPane.save(); }
}
