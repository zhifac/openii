// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.wc3.w909.workbench.tools.visualizers.harmony.GUI.HarmonyPane;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.KeyStroke;

import org.mitre.wc3.w909.workbench.tools.visualizers.harmony.GUI.MappingPane.MappingLines;
import org.mitre.wc3.w909.workbench.tools.visualizers.harmony.GUI.dialogs.matcher.MatcherMenu;
import org.mitre.wc3.w909.workbench.tools.visualizers.harmony.GUI.dialogs.AboutDialog;
import org.mitre.wc3.w909.workbench.tools.visualizers.harmony.GUI.dialogs.MappingSelectionDialog;
import org.mitre.wc3.w909.workbench.tools.visualizers.harmony.GUI.dialogs.PropertiesDialog;
import org.mitre.wc3.w909.workbench.tools.visualizers.harmony.Settings.Preferences.Preferences;
import org.mitre.wc3.w909.workbench.tools.visualizers.harmony.Settings.SelectedInfo.SelectedInfo;
import org.mitre.wc3.w909.workbench.model.MappingCellManager;
import org.mitre.wc3.w909.workbench.model.MappingManager;
import org.mitre.wc3.w909.workbench.model.ProjectListener;
import org.mitre.wc3.w909.workbench.model.ProjectManager;

/**
 * Displays all menu bar choices in Harmony
 * @author CWOLF
 */
class HarmonyMenuBar extends JMenuBar
{	
	/** Drop-down menu found under project menu bar heading */
	private class ProjectMenu extends JMenu implements ActionListener, ProjectListener
	{
	    private JMenuItem newProject;		// Option to create a new project
		private JMenuItem openProject;		// Option to open a created project
		private JMenuItem saveProject;		// Option to save a created project
		private JMenuItem importProject;	// Option for importing a project
		private JMenuItem exportProject;	// Option for exporting a project
		private JMenuItem projectProperties;// Option to view project properties
		private JMenuItem exitApp;			// Option to exit the Harmony application
		
		/** Initializes the project drop-down menu */
		private ProjectMenu()
		{
			// Gives the drop-down menu the title of "Project"
			super("Project");
			setMnemonic(KeyEvent.VK_P);

			// Initialize project drop-down items
		    newProject = new JMenuItem("New",KeyEvent.VK_N);
			openProject = new JMenuItem("Open",KeyEvent.VK_O);
			saveProject = new JMenuItem("Save",KeyEvent.VK_S);
			importProject = new JMenuItem("Import...",KeyEvent.VK_I);
			exportProject = new JMenuItem("Export...",KeyEvent.VK_E);
			projectProperties = new JMenuItem("Properties",KeyEvent.VK_P);
			exitApp = new JMenuItem("Exit",KeyEvent.VK_X);

			// Set accelerator keys for the menu items
			newProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
			openProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
			saveProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			
			// Attach action listeners to project drop-down items
			newProject.addActionListener(this);
			openProject.addActionListener(this);
			saveProject.addActionListener(this);
			importProject.addActionListener(this);
			exportProject.addActionListener(this);
			projectProperties.addActionListener(this);
			exitApp.addActionListener(this);
			
			// Add project drop-down items to project drop-down menu
			add(newProject);
			add(openProject);
			add(saveProject);
			addSeparator();
			add(importProject);
			add(exportProject);
			addSeparator();
			add(projectProperties);
			addSeparator();
		    add(exitApp);
		    
		    // Start with save option unavailable
		    saveProject.setEnabled(!ProjectManager.isSavedToRepository());
		    ProjectManager.addListener(this);
		}
		
		/** Handles the project drop-down action selected by the user */
	    public void actionPerformed(ActionEvent e)
	    {
	    	Object source = e.getSource();
	    	
	    	// If action overwrites old data, ask user to save old data first
	    	if(source==newProject || source==openProject)
	    	{
	    		int option = 1;
	    		if(!ProjectManager.isSavedToRepository())
	        		option = JOptionPane.showConfirmDialog(HarmonyPane.harmonyPane,
	        			"This project has been modified.  Do you want to save changes?",
						"Save Mapping", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
	    		if(option==2) return;
	    		if(option==0) MappingManager.saveMapping();
	    	}
	    		
	    	// Create a new project
	    	if(source==newProject)
	    		{ MappingManager.newMapping(); new PropertiesDialog(); }
	    	
	    	// Open a project
	    	else if(source==openProject)
	    		{ new MappingSelectionDialog(); }
	    	
	    	// Save a project
	    	else if(source==saveProject)
	    		{ MappingManager.saveMapping(); }
	    	
	    	// Import project
	    	else if(source==importProject)
	    		{ HarmonyProject.importProject(); }
	    	
	    	// Export project
	    	else if(source==exportProject)
	    		{ HarmonyProject.exportProject(); }
	    	
	    	// View project properties
	    	else if(source==projectProperties)
	    		{ new PropertiesDialog(); }
	    	
	    	// Exit Harmony
	    	else if(source==exitApp)
	    		{ HarmonyPane.harmonyPane.exitApp(); }
	    }
	    
	    /** Handles modifications to the project "saved" status */
	    public void projectSavedStatusChanged()
	    	{ saveProject.setEnabled(!ProjectManager.isSavedToRepository()); }
	}

	/** Drop-down menu found under edit menu bar heading */
	private class EditMenu extends JMenu implements ActionListener
	{
		private JMenuItem selectLinks;	// Option to select all links
		private JMenuItem removeLinks;	// Option to remove all current links
		private JMenuItem showTypes;	// Option to show schema data types
		
		/**
		 * Initializes the edit drop-down menu
		 */
		private EditMenu()
		{
			// Gives the drop-down menu the title of "Edit"
			super("Edit");
		    setMnemonic(KeyEvent.VK_E);
		    
			// Initialize project drop-down items
			selectLinks = new JMenuItem("Select All Links");
			removeLinks = new JMenuItem("Remove All Links");
			showTypes = new JCheckBoxMenuItem("Show Types");
			
			// Attach action listeners to edit drop-down items
			selectLinks.addActionListener(this);
			removeLinks.addActionListener(this);
			showTypes.addActionListener(this);
			
			// Add edit drop-down items to edit drop-down menu
		    add(selectLinks);
		    add(removeLinks);
		    addSeparator();
		    add(showTypes);
			
			// Initialize preference menu options
			showTypes.setSelected(Preferences.getShowSchemaTypes());
		}
		
		/** Handles the edit drop-down action selected by the user */
	    public void actionPerformed(ActionEvent e)
	    {	    	
	    	// Selects all links currently displayed in Harmony
	    	if(e.getSource() == selectLinks)
	    		{ SelectedInfo.setMappingCells(MappingLines.schemaTreeLines.getMappingCellsInRegion(null),true); }
	    	
	    	// Removes all links currently loaded into Harmony
	    	if(e.getSource() == removeLinks)
	    		{ MappingCellManager.deleteMappingCells(); }
	    	
	    	// Handles the "show types" preference option
	    	if(e.getSource() == showTypes)
	    		{ Preferences.setShowSchemaTypes(showTypes.isSelected()); }
	    }
	}

	/** Drop-down menu found under help menu bar heading */
	private class HelpMenu extends JMenu implements ActionListener
	{
		private JMenuItem about;	// Option to view info on Harmony

		/** Initializes the help drop-down menu */
		private HelpMenu()
		{
			// Gives the drop-down menu the title of "Help"
			super("Help");
		    setMnemonic(KeyEvent.VK_H);

			// Initialize project drop-down items
			about = new JMenuItem("About Harmony",KeyEvent.VK_A);
			
			// Attach action listeners to help drop-down items
			about.addActionListener(this);
			
			// Add help drop-down items to help drop-down menu
		    add(about);
		}
		
		/** Handles the help menu drop-down action selected by the user */
	    public void actionPerformed(ActionEvent e)
	    	{ new AboutDialog(); }
	}
	
	/** Initializes the Harmony menu bar */
	HarmonyMenuBar()
	{
	    add(new ProjectMenu());	
	    add(new EditMenu());
	    add(new MatcherMenu());
	    add(new HelpMenu());
	}
}
