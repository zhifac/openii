// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.harmonyPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.mitre.harmony.Harmony;
import org.mitre.harmony.controllers.ProjectController;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.project.ProjectMapping;
import org.mitre.harmony.view.dialogs.AboutDialog;
import org.mitre.harmony.view.dialogs.GettingStartedDialog;
import org.mitre.harmony.view.dialogs.SearchDialog;
import org.mitre.harmony.view.dialogs.SelectionDialog;
import org.mitre.harmony.view.dialogs.matcher.MatcherMenu;
import org.mitre.harmony.view.dialogs.porters.MappingExporterDialog;
import org.mitre.harmony.view.dialogs.porters.ProjectExporterDialog;
import org.mitre.harmony.view.dialogs.porters.ProjectImporterDialog;
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

	/** Create a abstract menu class */
	private abstract class AbstractMenu extends JMenu
	{
		/** Constructs the menu */
		private AbstractMenu(String label)
			{ super(label); }
		
		/** Creates a menu item */
		protected JMenuItem createMenuItem(String name, int mnemonic, Action action)
		{
			JMenuItem item = new JMenuItem(action);
			item.setText(name);
			item.setMnemonic(mnemonic);
			return item;
		}
		
		/** Creates a checkbox menu item */
		protected JMenuItem createCheckboxItem(String name, boolean selected, Action action)
		{
			JMenuItem item = new JCheckBoxMenuItem(name,selected);
			item.setAction(action);
			return item;
		}
	}	
	
	/** Drop-down menu found under project menu bar heading */
	private class ProjectMenu extends AbstractMenu implements MenuListener
	{
		/** Stores a mapping menu item */
		class MappingMenuItem extends JMenuItem implements ActionListener
		{
			private ProjectMapping mapping = null;
			
			/** Constructs the mapping menu item */
			private MappingMenuItem(ProjectMapping mapping)
				{ super(mapping.getName()); this.mapping = mapping; addActionListener(this); }

			/** Handles the selection of a mapping menu item */
			public void actionPerformed(ActionEvent e)
				{ MappingExporterDialog.exportMapping(harmonyModel,mapping); }
		}
		
		private JMenu exportMappingMenu;	// Option for exporting a mapping
		
		/** Initializes the project drop-down menu */
		private ProjectMenu()
		{
			// Gives the drop-down menu the title of "Project"
			super("Project");
			setMnemonic(KeyEvent.VK_P);
			
			// Set up menu for stand-alone mode
			if(harmonyModel.getBaseFrame() instanceof Harmony)
			{	
				// Constructs the new, open, and save menu items
				JMenuItem newProject = createMenuItem("New", KeyEvent.VK_N, new CreateProjectAction());
				JMenuItem openProject = createMenuItem("Open...", KeyEvent.VK_O, new OpenProjectAction());
				JMenuItem saveProject = createMenuItem("Save...", KeyEvent.VK_S, new SaveProjectAction());
				
				// Initialize the import menu
				JMenu importMenu = new JMenu("Import");
				importMenu.setMnemonic(KeyEvent.VK_I);
				importMenu.add(createMenuItem("Import Project", KeyEvent.VK_P, new ImportProjectAction()));
				importMenu.add(createMenuItem("Import Mapping", KeyEvent.VK_M, new ImportMappingAction()));
				
				// Initialize the export mapping menu
				exportMappingMenu = new JMenu("Export Mapping");
				exportMappingMenu.setMnemonic(KeyEvent.VK_X);
				exportMappingMenu.addMenuListener(this);
	
				// Initialize the export menu
				JMenu exportMenu = new JMenu("Export");
				exportMenu.setMnemonic(KeyEvent.VK_E);
				exportMenu.add(createMenuItem("Export Project", KeyEvent.VK_P, new ExportProjectAction()));
				exportMenu.add(exportMappingMenu);
				
				// Add project drop-down items to project drop-down menu
				add(newProject);
				add(openProject);
				add(saveProject);
				addSeparator();
				add(createMenuItem("Configure...", KeyEvent.VK_C, new ConfigurationAction()));
				addSeparator();
				add(importMenu);
				add(exportMenu);
				addSeparator();
			    add(createMenuItem("Exit", KeyEvent.VK_X, new ExitAction()));
			    
				// Set accelerator keys for the menu items
				newProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
				openProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
				saveProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			}

			// Otherwise, set up menu to be embedded in OpenII
			else
			{
				// Constructs the save menu item
				JMenuItem saveProject = createMenuItem("Save", KeyEvent.VK_S, new SaveProjectAction());
				
				// Add project drop-down items to project drop-down menu
				add(saveProject);
				addSeparator();
				add(createMenuItem("Configure...", KeyEvent.VK_P, new ConfigurationAction()));

				// Set accelerator keys for the save menu item
				saveProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			}
		}

		/** Generates the sub-menu on the fly */
		public void menuSelected(MenuEvent e)
		{
			exportMappingMenu.removeAll();
			for(ProjectMapping mapping : harmonyModel.getMappingManager().getMappings())
				exportMappingMenu.add(new MappingMenuItem(mapping));
			exportMappingMenu.revalidate();
		}
	    
	    /** Attempts to save old projects before opening new projects */
	    private boolean saveOldProject()
	    {
    		int option = 1;
    		if(harmonyModel.getProjectManager().isModified())
        		option = JOptionPane.showConfirmDialog(harmonyModel.getBaseFrame(),
        			"This mapping has been modified.  Do you want to save changes?",
					"Save Mapping", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
    		if(option==2) return false;
    		if(option==0) new SaveMappingDialog(harmonyModel);
    		return true;
	    }
	    
		/** Action for creating a new project */
		private class CreateProjectAction extends AbstractAction
		{
			public void actionPerformed(ActionEvent e)
			{
				if(saveOldProject())
					{ ProjectController.newProject(harmonyModel); new ProjectDialog(harmonyModel); }
			}
		}
	    
		/** Action for opening a project */
		private class OpenProjectAction extends AbstractAction
		{
			public void actionPerformed(ActionEvent e)
				{ if(saveOldProject()) new LoadProjectDialog(harmonyModel); }
		}
	    
		/** Action for saving the project */
		private class SaveProjectAction extends AbstractAction
		{
			public void actionPerformed(ActionEvent e)
			{
	    		if(harmonyModel.getBaseFrame() instanceof Harmony)
	    			new SaveMappingDialog(harmonyModel);
	    		else ProjectController.saveProject(harmonyModel,harmonyModel.getProjectManager().getProject());
			}
		}
	    
		/** Action for launching the "Import Project" dialog */
		private class ImportProjectAction extends AbstractAction
		{
			public void actionPerformed(ActionEvent e)
			{
	    		ProjectImporterDialog dialog = new ProjectImporterDialog(harmonyModel.getBaseFrame(),harmonyModel);
	    		while(dialog.isDisplayable()) try { Thread.sleep(500); } catch(Exception e2) {}
	    		ProjectController.selectMappings(harmonyModel);
			}
		}
	    
		/** Action for launching the "Export Project" dialog */
		private class ExportProjectAction extends AbstractAction
			{ public void actionPerformed(ActionEvent e) { ProjectExporterDialog.exportProject(harmonyModel); } }
	    
		/** Action for launching the "Import Mapping" dialog */
		private class ImportMappingAction extends AbstractAction
			{ public void actionPerformed(ActionEvent e) { }}//MappingImporterDialog.importMapping(harmonyModel); } }
	    
		/** Action for launching the configuration dialog */
		private class ConfigurationAction extends AbstractAction
			{ public void actionPerformed(ActionEvent e) { new ProjectDialog(harmonyModel); } }
	    
		/** Action for exiting Harmony */
		private class ExitAction extends AbstractAction
			{ public void actionPerformed(ActionEvent e) { harmonyModel.getBaseFrame().dispose(); } }
	    
		// Unused action listener
		public void menuCanceled(MenuEvent e) {}
		public void menuDeselected(MenuEvent e) {}
	}

	/** Drop-down menu found under edit menu bar heading */
	private class EditMenu extends AbstractMenu
	{
		/** Initializes the edit drop-down menu */
		private EditMenu()
		{
			// Gives the drop-down menu the title of "Edit"
			super("Edit");
		    setMnemonic(KeyEvent.VK_E);
			
			// Add edit drop-down items to edit drop-down menu
			add(createMenuItem("Select Links...", KeyEvent.VK_S, new SelectLinksAction()));
			add(createMenuItem("Remove Links...", KeyEvent.VK_R, new RemoveLinksAction()));
		}
	    
		/** Action for selecting links */
		private class SelectLinksAction extends AbstractAction
			{ public void actionPerformed(ActionEvent e) { new SelectionDialog(harmonyModel,SelectionDialog.SELECT); } }
	    
		/** Action for removing links */
		private class RemoveLinksAction extends AbstractAction
			{ public void actionPerformed(ActionEvent e) { new SelectionDialog(harmonyModel,SelectionDialog.DELETE); } }
	}

	/** Drop-down menu found under view menu bar heading */
	private class ViewMenu extends AbstractMenu
	{
		private JRadioButtonMenuItem mappingView;	// Option to view schema mapping
		private JRadioButtonMenuItem heatmapView;	// Option to view heat map
		
		/** Initializes the view drop-down menu */
		private ViewMenu()
		{
			super("View");
			setMnemonic(KeyEvent.VK_V);
			
			// Groups the radio buttons together
			ButtonGroup group = new ButtonGroup();
			group.add(mappingView = new JRadioButtonMenuItem("Mapping View",true));
			group.add(heatmapView = new JRadioButtonMenuItem("Heatmap View"));
			
			// Set the displayed view
			switch(harmonyModel.getPreferences().getViewToDisplay())
				{ case HarmonyConsts.HEATMAP_VIEW: heatmapView.setSelected(true); }			

			// Add view drop-down items to view drop-down menu
		    add(mappingView);
		    add(heatmapView);
		    addSeparator();
		    add(createCheckboxItem("Alphabetize", harmonyModel.getPreferences().getAlphabetized(), new AlphabetizeAction()));
		    add(createCheckboxItem("Show Types", harmonyModel.getPreferences().getShowSchemaTypes(), new ShowTypesAction()));
		}
		
		/** Handles the selection of a view */
	    public void actionPerformed(ActionEvent e)
	    {
	    	Object source = e.getSource();
	    	if(source==mappingView) harmonyModel.getPreferences().setViewToDisplay(HarmonyConsts.MAPPING_VIEW);
	    	if(source==heatmapView) harmonyModel.getPreferences().setViewToDisplay(HarmonyConsts.HEATMAP_VIEW);
	    }
	    
		/** Action for alphabetizing sibling elements */
		private class AlphabetizeAction extends AbstractAction
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean isSelected = ((JCheckBoxMenuItem)(e.getSource())).isSelected();
				harmonyModel.getPreferences().setAlphabetized(isSelected);
			}
		}

		/** Action for showing types */
		private class ShowTypesAction extends AbstractAction
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean isSelected = ((JCheckBoxMenuItem)(e.getSource())).isSelected();			
				harmonyModel.getPreferences().setShowSchemaTypes(isSelected);
			}
		}
	}

	/** Drop-down menu found under search menu bar heading */
	private class SearchMenu extends AbstractMenu implements ActionListener
	{
		private JRadioButtonMenuItem highlightFocusArea;	// Option to highlight focus area
		private JRadioButtonMenuItem highlightAll;			// Option to highlight all
		
		/** Initializes the search drop-down menu */
		private SearchMenu()
		{
			super("Search");
			setMnemonic(KeyEvent.VK_V);

			// Initialize view drop-down items
			highlightFocusArea = new JRadioButtonMenuItem("Highlight Focus Area",true);
			highlightAll = new JRadioButtonMenuItem("Highlight All");
			
			// Groups the radio buttons together
			ButtonGroup group = new ButtonGroup();
			group.add(highlightFocusArea);
			group.add(highlightAll);
			highlightAll.setSelected(true);
			
			// Attach action listeners to view drop-down items
			highlightFocusArea.addActionListener(this);
			highlightAll.addActionListener(this);

			// Add view drop-down items to view drop-down menu
			add(createMenuItem("Clear Results", KeyEvent.VK_C, new ClearResultsAction()));
			add(createMenuItem("Search...", KeyEvent.VK_S, new SearchAction()));
			addSeparator();
		    add(highlightFocusArea);
		    add(highlightAll);
		}
		
		/** Handles the selection of a highlight area */
	    public void actionPerformed(ActionEvent e)
	    {
	    	Object source = e.getSource();
	    	if(source==highlightFocusArea || source==highlightAll)
	    		harmonyModel.getSearchManager().setHighlightAll(source==highlightAll);
	    }
		
		/** Action for clearing the search results */
		private class ClearResultsAction extends AbstractAction
		{
			public void actionPerformed(ActionEvent e)
			{
	    		harmonyModel.getSearchManager().runQuery(HarmonyConsts.LEFT, "");
	    		harmonyModel.getSearchManager().runQuery(HarmonyConsts.RIGHT, "");
			}
		}
		
		/** Action for displaying the "Search" dialog */
		private class SearchAction extends AbstractAction
			{ public void actionPerformed(ActionEvent e) { new SearchDialog(harmonyModel); } }
	}
	
	/** Drop-down menu found under help menu bar heading */
	private class HelpMenu extends AbstractMenu
	{
		/** Initializes the help drop-down menu */
		private HelpMenu()
		{
			// Gives the drop-down menu the title of "Help"
			super("Help");
		    setMnemonic(KeyEvent.VK_H);
			
			// Add help drop-down items to help drop-down menu
		    add(createMenuItem("About Harmony", KeyEvent.VK_A, new AboutAction()));
		    add(createMenuItem("Getting Started", KeyEvent.VK_G, new GettingStartedAction()));
		}
		
		/** Action for displaying the "About" dialog */
		private class AboutAction extends AbstractAction
			{ public void actionPerformed(ActionEvent e) { new AboutDialog(harmonyModel); } }

		/** Action for displaying the "Getting Started" dialog */
		private class GettingStartedAction extends AbstractAction
			{ public void actionPerformed(ActionEvent e) { new GettingStartedDialog(harmonyModel); } }
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
