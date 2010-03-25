// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.project.mappings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.mitre.harmony.Harmony;
import org.mitre.harmony.view.dialogs.project.ProjectDialog;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/**
 * Displays the mapping pane for defining a project
 * @author CWOLF
 */
public class MappingPane extends JPanel implements ActionListener
{
	/** Stores the Project dialog */
	private ProjectDialog projectDialog;
	
	// Stores the various panes used in this pane
	private MappingSelectionPane mappingSelectionPane = null;
	
	/** Initializes the Mapping pane */
	public MappingPane(ProjectDialog projectDialog)
	{
		this.projectDialog = projectDialog;
		boolean standaloneMode = projectDialog.getHarmonyModel().getBaseFrame() instanceof Harmony;	
		
		// Initializes the mapping selection and display panes
		mappingSelectionPane = new MappingSelectionPane(projectDialog.getHarmonyModel());

		// Initializes the selection pane
		mappingSelectionPane.setPreferredSize(new Dimension(300,0));
		
		// Create the import schema button
		JButton button = new JButton("Add Mapping");
		button.setFocusable(false);
		button.setEnabled(standaloneMode);
		button.addActionListener(this);
		
		// Constructs the mapping pane
		setBorder(new CompoundBorder(new TitledBorder("Mappings (select to display)"),new EmptyBorder(5,5,5,5)));
		setLayout(new BorderLayout());
		add(mappingSelectionPane,BorderLayout.CENTER);
		add(button,BorderLayout.SOUTH);
   	}
	
	/** Returns the declared mappings */
	public ArrayList<Mapping> getMappings()
		{ return mappingSelectionPane.getMappings(); }
	
	/** Handles the pressing of the "Add Mapping" button */
	public void actionPerformed(ActionEvent e)
	{
		// Run the dialog to add a mapping
		ArrayList<Schema> schemas = projectDialog.getSchemaPane().getSchemas();
		ArrayList<Mapping> mappings = mappingSelectionPane.getMappings();
		AddMappingDialog dialog = new AddMappingDialog(projectDialog.getHarmonyModel(), schemas, mappings);
		try { while(dialog.isVisible()) Thread.sleep(1000); } catch(Exception e2) {}

		// Add the mapping
		Mapping mapping = dialog.getMapping();
		if(mapping!=null) mappingSelectionPane.addMapping(mapping);
	}
	
	/** Saves the project mappings */
	public void save()
		{ mappingSelectionPane.save(); }
}