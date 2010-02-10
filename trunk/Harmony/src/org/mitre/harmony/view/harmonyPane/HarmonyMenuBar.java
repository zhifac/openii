// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.harmonyPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import org.mitre.harmony.Harmony;
import org.mitre.harmony.controllers.ProjectController;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.AboutDialog;
import org.mitre.harmony.view.dialogs.GettingStartedDialog;
import org.mitre.harmony.view.dialogs.SearchDialog;
import org.mitre.harmony.view.dialogs.matcher.MatcherMenu;
import org.mitre.harmony.view.dialogs.porters.ExportProjectDialog;
import org.mitre.harmony.view.dialogs.porters.MappingImporterDialog;
import org.mitre.harmony.view.dialogs.project.ProjectDialog;
import org.mitre.harmony.view.dialogs.projects.LoadProjectDialog;
import org.mitre.harmony.view.dialogs.projects.SaveMappingDialog;

/**
 * Displays all menu bar choices in Harmony
 * @author CWOLF
 */
public class HarmonyMenuBar extends JMenuBar
{	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Drop-down menu found under project menu bar heading */
	private class ProjectMenu extends JMenu implements ActionListener
	{
	    private JMenuItem newProject;		// Option to create a new mapping
		private JMenuItem openProject;		// Option to open a created mapping
		private JMenuItem saveMapping;		// Option to save a created mapping
		private JMenuItem configureProject;	// Option for managing the project configuration
		private JMenuItem importProject;	// Option for importing a mapping
		private JMenuItem exportMapping;	// Option for exporting a mapping
		private JMenuItem exitApp;			// Option to exit the Harmony application
		
		/** Initializes the project drop-down menu */
		private ProjectMenu()
		{
			// Gives the drop-down menu the title of "Project"
			super("Project");
			setMnemonic(KeyEvent.VK_P);

			// Set up menu for stand-alone mode
			if(harmonyModel.getBaseFrame() instanceof Harmony)
			{
				// Initialize project drop-down items
			    newProject = new JMenuItem("New",KeyEvent.VK_N);
				openProject = new JMenuItem("Open...",KeyEvent.VK_O);
				saveMapping = new JMenuItem("Save...",KeyEvent.VK_S);
				configureProject = new JMenuItem("Configure...",KeyEvent.VK_M);
				importProject = new JMenuItem("Import Mapping...",KeyEvent.VK_I);
				exportMapping = new JMenuItem("Export Mapping...",KeyEvent.VK_E);
				exitApp = new JMenuItem("Exit",KeyEvent.VK_X);
	
				// Set accelerator keys for the menu items
				newProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
				openProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
				saveMapping.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
				
				// Attach action listeners to project drop-down items
				newProject.addActionListener(this);
				openProject.addActionListener(this);
				saveMapping.addActionListener(this);
				configureProject.addActionListener(this);
				importProject.addActionListener(this);
				exportMapping.addActionListener(this);
				exitApp.addActionListener(this);
				
				// Add project drop-down items to project drop-down menu
				add(newProject);
				add(openProject);
				add(saveMapping);
				addSeparator();
				add(configureProject);
				addSeparator();
				add(importProject);
				add(exportMapping);
				addSeparator();
			    add(exitApp);
			}

			// Otherwise, set up menu to be embedded in OpenII
			else
			{
				// Create the save mapping menu option
				saveMapping = new JMenuItem("Save",KeyEvent.VK_S);
				saveMapping.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
				saveMapping.addActionListener(this);

				// Create the schema settings menu option
				configureProject = new JMenuItem("Configure...",KeyEvent.VK_P);
				configureProject.addActionListener(this);
				
				// Add project drop-down items to project drop-down menu
				add(saveMapping);
				addSeparator();
				add(configureProject);
			}
		}
		
		/** Handles the project drop-down action selected by the user */
	    public void actionPerformed(ActionEvent e)
	    {
	    	Object source = e.getSource();
	    	
	    	// If action overwrites old data, ask user to save old data first
	    	if(source==newProject || source==openProject)
	    	{
	    		int option = 1;
	    		if(harmonyModel.getProjectManager().isModified())
	        		option = JOptionPane.showConfirmDialog(harmonyModel.getBaseFrame(),
	        			"This mapping has been modified.  Do you want to save changes?",
						"Save Mapping", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
	    		if(option==2) return;
	    		if(option==0) new SaveMappingDialog(harmonyModel);
	    	}
	    		
	    	// Create a new project
	    	if(source==newProject)
	    		{ ProjectController.newProject(harmonyModel); new ProjectDialog(harmonyModel); }
	    	
	    	// Open a project
	    	else if(source==openProject)
	    		{ new LoadProjectDialog(harmonyModel); }
	    	
	    	// Save a project
	    	else if(source==saveMapping)
	    	{
	    		if(harmonyModel.getBaseFrame() instanceof Harmony)
	    			new SaveMappingDialog(harmonyModel);
	    		else ProjectController.saveProject(harmonyModel.getProjectManager().getProject());
	    	}
		    
	    	// Import project
	    	else if(source==importProject)
	    	{
	    		MappingImporterDialog dialog = new MappingImporterDialog(harmonyModel.getBaseFrame(),harmonyModel);
	    		while(dialog.isDisplayable()) try { Thread.sleep(500); } catch(Exception e2) {}
	    		
	    		// Launches the schema dialog window if no schemas displayed
//	    		int displayedSchemas = 0;
//	    		for(ProjectSchema schema : harmonyModel.getProjectManager().getSchemas())
//	    			if(!schema.getSide().equals(MappingSchema.NONE)) displayedSchemas++;
//	    		if(displayedSchemas==0) new SchemaSettingsDialog(harmonyModel);
	    	}
	    
	    	// Export project
	    	else if(source==exportMapping)
	    		{ ExportProjectDialog.exportProject(harmonyModel); }
	    	
	    	// Display the mapping properties
	    	else if(source==configureProject)
	    		new ProjectDialog(harmonyModel);
	    	
	    	// Exit Harmony
	    	else if(source==exitApp)
	    		{ harmonyModel.getBaseFrame().dispose(); }
	    }
	}

	/** Drop-down menu found under edit menu bar heading */
	private class EditMenu extends JMenu implements ActionListener
	{
		private JMenuItem removeHiddenLinks;	// Option to remove hidden links
		private JMenuItem removeLinks;			// Option to remove all current links
		
		/** Initializes the edit drop-down menu */
		private EditMenu()
		{
			// Gives the drop-down menu the title of "Edit"
			super("Edit");
		    setMnemonic(KeyEvent.VK_E);
		    
			// Initialize project drop-down items
		    removeHiddenLinks = new JMenuItem("Remove Hidden Links");
			removeLinks = new JMenuItem("Remove All Links");
			
			// Attach action listeners to edit drop-down items
			removeHiddenLinks.addActionListener(this);
			removeLinks.addActionListener(this);
			
			// Add edit drop-down items to edit drop-down menu
			add(removeHiddenLinks);
		    add(removeLinks);
		}
		
		/** Handles the edit drop-down action selected by the user */
	    public void actionPerformed(ActionEvent e)
	    {
	    	// Removed hidden links from the currently focused area of Harmony
	    	if(e.getSource() == removeHiddenLinks)
	    		harmonyModel.getMappingManager().deleteHiddenMappingCells();
	    	
	    	// Removes all links from the currently focused area of Harmony
	    	if(e.getSource() == removeLinks)
	    		harmonyModel.getMappingManager().deleteAllMappingCells();
	    }
	}

	/** Drop-down menu found under view menu bar heading */
	private class ViewMenu extends JMenu implements ActionListener
	{
		private JRadioButtonMenuItem mappingView;	// Option to view schema mapping
		private JRadioButtonMenuItem heatmapView;	// Option to view heat map
		private JMenuItem showTypes;				// Option to show schema data types
		
		/** Initializes the view drop-down menu */
		private ViewMenu()
		{
			super("View");
			setMnemonic(KeyEvent.VK_V);

			// Initialize view drop-down items
			mappingView = new JRadioButtonMenuItem("Mapping View",true);
			heatmapView = new JRadioButtonMenuItem("Heatmap View");
			showTypes = new JCheckBoxMenuItem("Show Types");
			
			// Groups the radio buttons together
			ButtonGroup group = new ButtonGroup();
			group.add(mappingView);
			group.add(heatmapView);
			
			// Set the displayed view
			switch(harmonyModel.getPreferences().getViewToDisplay())
				{ case HarmonyConsts.HEATMAP_VIEW: heatmapView.setSelected(true); }			
			
			// Attach action listeners to view drop-down items
			mappingView.addActionListener(this);
			heatmapView.addActionListener(this);
			showTypes.addActionListener(this);

			// Add view drop-down items to view drop-down menu
		    add(mappingView);
		    add(heatmapView);
		    addSeparator();
		    add(showTypes);
			
			// Initialize preference menu options
			showTypes.setSelected(harmonyModel.getPreferences().getShowSchemaTypes());
		}
		
		/** Handles the selection of a view */
	    public void actionPerformed(ActionEvent e)
	    {
	    	// Switch to the selected view
	    	Object source = e.getSource();
	    	if(source==mappingView) harmonyModel.getPreferences().setViewToDisplay(HarmonyConsts.MAPPING_VIEW);
	    	if(source==heatmapView) harmonyModel.getPreferences().setViewToDisplay(HarmonyConsts.HEATMAP_VIEW);
	    	
	    	// Handles the "show types" preference option
	    	if(source == showTypes)
	    		{ harmonyModel.getPreferences().setShowSchemaTypes(showTypes.isSelected()); }
	    }
	}

	/** Drop-down menu found under search menu bar heading */
	private class SearchMenu extends JMenu implements ActionListener
	{
	    private JMenuItem clearResults;		// Clears the search results
		private JMenuItem search;			// Launches a search dialog
		private JRadioButtonMenuItem highlightFocusArea;	// Option to highlight focus area
		private JRadioButtonMenuItem highlightAll;			// Option to highlight all
		
		/** Initializes the search drop-down menu */
		private SearchMenu()
		{
			super("Search");
			setMnemonic(KeyEvent.VK_V);

			// Initialize view drop-down items
			clearResults = new JMenuItem("Clear Results");
			search = new JMenuItem("Search...");
			highlightFocusArea = new JRadioButtonMenuItem("Highlight Focus Area",true);
			highlightAll = new JRadioButtonMenuItem("Highlight All");
			
			// Groups the radio buttons together
			ButtonGroup group = new ButtonGroup();
			group.add(highlightFocusArea);
			group.add(highlightAll);
			highlightAll.setSelected(true);
			
			// Attach action listeners to view drop-down items
			clearResults.addActionListener(this);
			search.addActionListener(this);
			highlightFocusArea.addActionListener(this);
			highlightAll.addActionListener(this);

			// Add view drop-down items to view drop-down menu
			add(clearResults);
			add(search);
			addSeparator();
		    add(highlightFocusArea);
		    add(highlightAll);
		}
		
		/** Handles the selection of a highlight area */
	    public void actionPerformed(ActionEvent e)
	    {
	    	Object source = e.getSource();
	    	
	    	// Handle the clearing of search results
	    	if(source==clearResults)
	    	{
	    		harmonyModel.getSearchManager().runQuery(HarmonyConsts.LEFT, "");
	    		harmonyModel.getSearchManager().runQuery(HarmonyConsts.RIGHT, "");
	    	}
	    	
	    	// Handles the launching of the search dialog box
	    	if(source==search)
	    		new SearchDialog(harmonyModel);
	    	
	    	// Switch to the selected view
	    	if(source==highlightFocusArea || source==highlightAll)
	    		harmonyModel.getSearchManager().setHighlightAll(source==highlightAll);
	    }
	}
	
	/** Drop-down menu found under help menu bar heading */
	private class HelpMenu extends JMenu implements ActionListener
	{
		private JMenuItem about;			// Option to view info on Harmony
		private JMenuItem gettingStarted;	// Option to view info on getting started

		/** Initializes the help drop-down menu */
		private HelpMenu()
		{
			// Gives the drop-down menu the title of "Help"
			super("Help");
		    setMnemonic(KeyEvent.VK_H);

			// Initialize project drop-down items
			about = new JMenuItem("About Harmony",KeyEvent.VK_A);
			gettingStarted = new JMenuItem("Getting Started",KeyEvent.VK_G);
			
			// Attach action listeners to help drop-down items
			about.addActionListener(this);
			gettingStarted.addActionListener(this);
			
			// Add help drop-down items to help drop-down menu
		    add(about);
		    add(gettingStarted);
		}
		
		/** Handles the help menu drop-down action selected by the user */
	    public void actionPerformed(ActionEvent e)
	    {
	    	// Shows the "About" dialog
	    	if(e.getSource().equals(about))
	    		new AboutDialog(harmonyModel);

	    	// Shows the "Getting Started" dialog
	    	if(e.getSource().equals(gettingStarted))
	    		new GettingStartedDialog(harmonyModel);
	    }
	}
	
	/** Initializes the Harmony menu bar */
	public HarmonyMenuBar(HarmonyModel harmonyModel)
	{
		this.harmonyModel = harmonyModel;
	    add(new ProjectMenu());	
	    add(new EditMenu());
	    add(new SearchMenu());
	    add(new MatcherMenu(harmonyModel));
	    add(new ViewMenu());
	    add(new HelpMenu());
	}
}
