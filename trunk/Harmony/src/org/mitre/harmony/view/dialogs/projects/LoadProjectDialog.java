// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.projects;

import java.awt.BorderLayout;
import java.util.HashSet;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.mitre.harmony.controllers.ProjectController;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.project.ProjectMapping;
import org.mitre.harmony.view.dialogs.AbstractButtonPane;
import org.mitre.harmony.view.dialogs.project.ProjectDialog;
import org.mitre.schemastore.model.Project;

/**
 * Class used for the loading of a mapping
 * @author CWOLF
 */
public class LoadProjectDialog extends JDialog implements ListSelectionListener
{	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores the project pane */
	private ProjectPane projectPane = null;
	
	/** Stores the info pane */
	private InfoPane infoPane = null;

	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		public ButtonPane()
			{ super(new String[]{"OK", "Cancel"},1,2); }

		/** Handles selection of button */
		protected void buttonPressed(String label)
		{
			if(label.equals("OK"))
			{
				// Loads in the selected project
				Project project = projectPane.getProject();
				if(project==null) ProjectController.newProject(harmonyModel);
				else ProjectController.loadProject(harmonyModel,project.getId());
				dispose();

				// Checks to see if all mappings can be displayed
				boolean displayAll = true;
				HashSet<Integer> sourceIDs = new HashSet<Integer>();
				HashSet<Integer> targetIDs = new HashSet<Integer>();
				for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
				{
					sourceIDs.add(mapping.getSourceId());
					targetIDs.add(mapping.getTargetId());
					if(sourceIDs.contains(mapping.getTargetId()) || targetIDs.contains(mapping.getSourceId()))
						{ displayAll = false; break; }
				}
				
				// Select all if possible or otherwise launch configuration dialog
				if(displayAll)
					for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
						mapping.setVisibility(true);
				else new ProjectDialog(harmonyModel);
			}			
			else dispose();
		}
	}
	
	/** Generates the main pane of the dialog for loading projects */
	private JPanel getMainPane()
	{
		// Initialize the mapping pane
		projectPane = new ProjectPane(false,harmonyModel);
		projectPane.addListSelectionListener(this);

		//Initialize the info pane
		infoPane = new InfoPane(false,harmonyModel);
		infoPane.setInfo(projectPane.getProject());
		
		// Creates the main pane
		JPanel mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(0,0,4,0));
		mainPane.setLayout(new BorderLayout());
		mainPane.add(projectPane,BorderLayout.WEST);
		mainPane.add(infoPane,BorderLayout.CENTER);
	    
		// Place list of roots in center of project pane
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(15,15,10,15));
		pane.setLayout(new BorderLayout());
		pane.add(mainPane,BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		return pane;
	}

	/** Constructs the dialog for loading projects */
	public LoadProjectDialog(HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		this.harmonyModel = harmonyModel;
		setTitle("Select Project");
		setModal(true);
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(getMainPane());
		pack();
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		setVisible(true);
	}

	/** Handles a change to the selected project list */
	public void valueChanged(ListSelectionEvent e)
		{ infoPane.setInfo(projectPane.getProject()); }
}