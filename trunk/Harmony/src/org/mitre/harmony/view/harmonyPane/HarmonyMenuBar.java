// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.harmonyPane;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.AboutDialog;
import org.mitre.harmony.view.dialogs.ExportMappingDialog;
import org.mitre.harmony.view.dialogs.mappings.LoadMappingDialog;
import org.mitre.harmony.view.dialogs.mappings.SaveMappingDialog;
import org.mitre.harmony.view.dialogs.matcher.MatcherMenu;
import org.mitre.harmony.view.dialogs.schemas.SchemaDialog;
import org.mitre.schemastore.model.MappingCell;

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
	    		if(harmonyModel.getMappingManager().isModified())
	        		option = JOptionPane.showConfirmDialog(harmonyModel.getBaseFrame(),
	        			"This mapping has been modified.  Do you want to save changes?",
						"Save Mapping", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
	    		if(option==2) return;
	    		if(option==0) new SaveMappingDialog(harmonyModel);
	    	}
	    		
	    	// Create a new project
	    	if(source==newMapping)
	    		{ harmonyModel.getMappingManager().newMapping(); new SchemaDialog(harmonyModel); }
	    	
	    	// Open a project
	    	else if(source==openMapping)
	    		{ new LoadMappingDialog(harmonyModel); }
	    	
	    	// Save a project
	    	else if(source==saveMapping)
	    		{ new SaveMappingDialog(harmonyModel); }
	    	
	    	// Export project
	    	else if(source==exportMapping)
	    		{ ExportMappingDialog.exportMapping(harmonyModel); }
	    	
	    	// Exit Harmony
	    	else if(source==exitApp)
	    		{ harmonyModel.getBaseFrame().dispose(); }
	    }
	}

	/** Drop-down menu found under edit menu bar heading */
	private class EditMenu extends JMenu implements ActionListener
	{
		private JMenuItem editSchemas;	// Option to manage the schemas
		private JMenuItem selectLinks;		// Option to select all links
		private JMenuItem removeLinks;		// Option to remove all current links
		
		/**
		 * Initializes the edit drop-down menu
		 */
		private EditMenu()
		{
			// Gives the drop-down menu the title of "Edit"
			super("Edit");
		    setMnemonic(KeyEvent.VK_E);
		    
			// Initialize project drop-down items
		    editSchemas = new JMenuItem("Edit Schemas");
		    selectLinks = new JMenuItem("Select All Links");
			removeLinks = new JMenuItem("Remove All Links");
			
			// Attach action listeners to edit drop-down items
			editSchemas.addActionListener(this);
			selectLinks.addActionListener(this);
			removeLinks.addActionListener(this);
			
			// Add edit drop-down items to edit drop-down menu
			add(editSchemas);
			add(selectLinks);
		    add(removeLinks);
		}
		
		/** Handles the edit drop-down action selected by the user */
	    public void actionPerformed(ActionEvent e)
	    {	    	
	    	// Handles the editing of mapping schemas
	    	if(e.getSource() == editSchemas)
	    		new SchemaDialog(harmonyModel);
	    	
	    	// Selects all links currently displayed in Harmony
	    	if(e.getSource() == selectLinks)
	    	{
	    		ArrayList<Integer> mappingCellIDs = new ArrayList<Integer>();
	    		for(MappingCell mappingCell : harmonyModel.getMappingCellManager().getMappingCells())
	    			mappingCellIDs.add(mappingCell.getId());
	    		harmonyModel.getSelectedInfo().setMappingCells(mappingCellIDs, true);
	    	}

	    	// Removes all links currently loaded into Harmony
	    	if(e.getSource() == removeLinks)
	    	{
	    		for(Integer schemaID : harmonyModel.getMappingManager().getSchemaIDs())
	    		{
	    			HashSet<Integer> finishedElements = new HashSet<Integer>(harmonyModel.getPreferences().getFinishedElements(schemaID));
	    			harmonyModel.getPreferences().setFinished(schemaID, finishedElements, false);
	    		}
	    		harmonyModel.getMappingCellManager().deleteMappingCells();
	    	}
	    }
	}

	/** Drop-down menu found under view menu bar heading */
	private class ViewMenu extends JMenu implements ActionListener
	{
		private JRadioButtonMenuItem mappingView;	// Option to view schema mapping
		private JRadioButtonMenuItem tableView;		// Option to view schema table
		private JRadioButtonMenuItem heatmapView;	// Option to view heat map
		private JMenuItem showTypes;				// Option to show schema data types
		
		/** Initializes the view drop-down menu */
		private ViewMenu()
		{
			super("View");
			setMnemonic(KeyEvent.VK_V);

			// Initialize view drop-down items
			mappingView = new JRadioButtonMenuItem("Mapping View",true);
			tableView = new JRadioButtonMenuItem("Table View");
			heatmapView = new JRadioButtonMenuItem("Heatmap View");
			showTypes = new JCheckBoxMenuItem("Show Types");
			
			// Groups the radio buttons together
			ButtonGroup group = new ButtonGroup();
			group.add(mappingView);
			group.add(tableView);
			group.add(heatmapView);
			
			// Set the displayed view
			switch(harmonyModel.getPreferences().getViewToDisplay())
			{
				case HarmonyConsts.TABLE_VIEW: tableView.setSelected(true);
				case HarmonyConsts.HEATMAP_VIEW: heatmapView.setSelected(true);
			}			
			
			// Attach action listeners to view drop-down items
			mappingView.addActionListener(this);
			tableView.addActionListener(this);
			heatmapView.addActionListener(this);
			showTypes.addActionListener(this);

			// Add view drop-down items to view drop-down menu
		    add(mappingView);
		    add(tableView);
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
	    	if(source==tableView) harmonyModel.getPreferences().setViewToDisplay(HarmonyConsts.TABLE_VIEW);
	    	if(source==heatmapView) harmonyModel.getPreferences().setViewToDisplay(HarmonyConsts.HEATMAP_VIEW);
	    	
	    	// Handles the "show types" preference option
	    	if(source == showTypes)
	    		{ harmonyModel.getPreferences().setShowSchemaTypes(showTypes.isSelected()); }
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
	    	{ new AboutDialog(harmonyModel); }
	}
	
	/** Initializes the Harmony menu bar */
	public HarmonyMenuBar(HarmonyModel harmonyModel)
	{
		this.harmonyModel = harmonyModel;
	    add(new ProjectMenu());	
	    add(new EditMenu());
	    add(new MatcherMenu(harmonyModel));
	    add(new ViewMenu());
	    add(new HelpMenu());
	}
}
