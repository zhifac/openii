// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.harmonyPane;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.KeyStroke;

import org.mitre.harmony.exporters.ExporterManager;
import org.mitre.harmony.model.MappingCellManager;
import org.mitre.harmony.model.MappingManager;
import org.mitre.harmony.model.ProjectManager;
import org.mitre.harmony.model.preferences.Preferences;
import org.mitre.harmony.model.selectedInfo.SelectedInfo;
import org.mitre.harmony.view.dialogs.AboutDialog;
import org.mitre.harmony.view.dialogs.mappings.LoadMappingDialog;
import org.mitre.harmony.view.dialogs.mappings.SaveMappingDialog;
import org.mitre.harmony.view.dialogs.matcher.MatcherMenu;
import org.mitre.harmony.view.dialogs.schemas.SchemaDialog;
import org.mitre.harmony.view.mappingPane.MappingLines;
import org.mitre.harmony.view.heatmap.HeatMapDialog;

/**
 * Displays all menu bar choices in Harmony
 * @author CWOLF
 */
public class HarmonyMenuBar extends JMenuBar
{	
	/** Drop-down menu found under project menu bar heading */
	private class ProjectMenu extends JMenu implements ActionListener
	{
	    private JMenuItem newMapping;		// Option to create a new mapping
		private JMenuItem openMapping;		// Option to open a created mapping
		private JMenuItem saveMapping;		// Option to save a created mapping
		private JMenuItem exportMapping;	// Option for exporting a mapping
		private JMenuItem exitApp;			// Option to exit the Harmony application
		
		/** Initializes the project drop-down menu */
		private ProjectMenu()
		{
			// Gives the drop-down menu the title of "Mapping"
			super("Mapping");
			setMnemonic(KeyEvent.VK_P);

			// Initialize project drop-down items
		    newMapping = new JMenuItem("New",KeyEvent.VK_N);
			openMapping = new JMenuItem("Open...",KeyEvent.VK_O);
			saveMapping = new JMenuItem("Save...",KeyEvent.VK_S);
			exportMapping = new JMenuItem("Export...",KeyEvent.VK_E);
			exitApp = new JMenuItem("Exit",KeyEvent.VK_X);

			// Set accelerator keys for the menu items
			newMapping.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
			openMapping.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
			saveMapping.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			
			// Attach action listeners to project drop-down items
			newMapping.addActionListener(this);
			openMapping.addActionListener(this);
			saveMapping.addActionListener(this);
			exportMapping.addActionListener(this);
			exitApp.addActionListener(this);
			
			// Add project drop-down items to project drop-down menu
			add(newMapping);
			add(openMapping);
			add(saveMapping);
			addSeparator();
			add(exportMapping);
			addSeparator();
		    add(exitApp);
		}
		
		/** Handles the project drop-down action selected by the user */
	    public void actionPerformed(ActionEvent e)
	    {
	    	Object source = e.getSource();
	    	
	    	// If action overwrites old data, ask user to save old data first
	    	if(source==newMapping || source==openMapping)
	    	{
	    		int option = 1;
	    		if(!ProjectManager.isSavedToRepository())
	        		option = JOptionPane.showConfirmDialog(HarmonyFrame.harmonyFrame,
	        			"This project has been modified.  Do you want to save changes?",
						"Save Mapping", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
	    		if(option==2) return;
	    		if(option==0) MappingManager.saveMapping();
	    	}
	    		
	    	// Create a new project
	    	if(source==newMapping)
	    		{ MappingManager.newMapping(); new SchemaDialog(); }
	    	
	    	// Open a project
	    	else if(source==openMapping)
	    		{ new LoadMappingDialog(); }
	    	
	    	// Save a project
	    	else if(source==saveMapping)
	    		{ new SaveMappingDialog(); }
	    	
	    	// Export project
	    	else if(source==exportMapping)
	    		{ ExporterManager.exportMapping(); }
	    	
	    	// Exit Harmony
	    	else if(source==exitApp)
	    		{ HarmonyFrame.harmonyFrame.exitApp(); }
	    }
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
	    		{ SelectedInfo.setMappingCells(MappingLines.mappingLines.getMappingCellsInRegion(null),true); }
	    	
	    	// Removes all links currently loaded into Harmony
	    	if(e.getSource() == removeLinks)
	    	{
	    		for(Integer schemaID : MappingManager.getSchemas())
	    		{
	    			HashSet<Integer> finishedElements = new HashSet<Integer>(Preferences.getFinishedElements(schemaID));
	    			Preferences.setFinished(schemaID, finishedElements, false);
	    		}
	    		MappingCellManager.deleteMappingCells();
	    	}
	    	
	    	// Handles the "show types" preference option
	    	if(e.getSource() == showTypes)
	    		{ Preferences.setShowSchemaTypes(showTypes.isSelected()); }
	    }
	}

	/**
	 * View Menu added by MDMORSE
	 * This menu can invoke the heatmap view.  Also possibly a future
	 * place to add other views.
	 */
	private class ViewMenu extends JMenu implements ActionListener
	{
		private JMenuItem viewHeatmap;		// Option to view heat map
		
		/** Initializes the view drop-down menu */
		private ViewMenu()
		{
			super("Views");
			setMnemonic(KeyEvent.VK_V);

			// Initialize view drop-down items
		    viewHeatmap = new JMenuItem("View Heatmap",KeyEvent.VK_V);

			// Attach action listeners to view drop-down items
		    viewHeatmap.addActionListener(this);

			// Add view drop-down items to view drop-down menu
		    add(viewHeatmap);
		}
		
		/** Handles the view mapping heat map selected by the user */
	    public void actionPerformed(ActionEvent e)
	    {
	    	Object source = e.getSource();
	    	if(source==viewHeatmap)
	    		{ new HeatMapDialog(); }
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
	public HarmonyMenuBar()
	{
	    add(new ProjectMenu());	
	    add(new EditMenu());
	    add(new MatcherMenu());
//	    add(new ViewMenu());
	    add(new HelpMenu());
	}
}
