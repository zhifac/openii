// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.controllers.MappingController;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.widgets.AbstractButtonPane;
import org.mitre.harmony.view.dialogs.widgets.OptionPane;
import org.mitre.schemastore.model.MappingCell;

/**
 * Displays the search dialog for search for keywords in schemas
 * @author CWOLF
 */
public class SelectionDialog extends JDialog
{
	// Constants for defining the selection dialog mode
	static public final Integer SELECT = 0;
	static public final Integer DELETE = 1;
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Stores the dialog mode */
	private Integer mode;
	
	// Stores the filters
	private OptionPane typeFilter=null, focusFilter=null, visibilityFilter=null;
	
	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		private ButtonPane()
			{ super(new String[]{"OK","Cancel"},1,2); }

		/** Handles selection of button */
		protected void buttonPressed(String label)
		{
			if(label.equals("OK"))
			{
				// Get the list of mapping cells
				HashSet<MappingCell> mappingCells = new HashSet<MappingCell>();
				String type = typeFilter.getSelectedButton();
				for(MappingCell mappingCell : harmonyModel.getMappingManager().getMappingCells())
				{
					boolean validated = mappingCell.getValidated();
					if(type.equals("All") || (type.equals("System") && !validated) || (type.equals("User") && validated))
						mappingCells.add(mappingCell);
				}
				
				// Filter out mapping cells based on filter settings
				String focus = focusFilter.getSelectedButton();
				if(!focus.equals("All"))
				{
					boolean useFocused = focus.equals("Focused");
					HashSet<MappingCell> focusedMappingCells = harmonyModel.getFilters().getFocusedMappingCells();
					for(MappingCell mappingCell : new ArrayList<MappingCell>(mappingCells))
					{
						boolean isFocused = focusedMappingCells.contains(mappingCell);
						if(useFocused != isFocused) mappingCells.remove(mappingCell);
					}					
				}

				// Filter out mapping cells based on visibility
				String visibility = visibilityFilter.getSelectedButton();
				if(!visibility.equals("All"))
				{
					boolean useVisible = visibility.equals("Visible");
					for(MappingCell mappingCell : new ArrayList<MappingCell>(mappingCells))
					{
						boolean isVisible = harmonyModel.getFilters().isVisibleMappingCell(mappingCell.getId());
						if(useVisible != isVisible) mappingCells.remove(mappingCell);
					}
				}
				
				// Select or delete mapping cells
				if(mode.equals(SELECT))
				{
					ArrayList<Integer> mappingCellIDs = MappingController.getMappingCellIDs(new ArrayList<MappingCell>(mappingCells));
					harmonyModel.getSelectedInfo().setMappingCells(mappingCellIDs, false);
				}
				else harmonyModel.getMappingManager().deleteMappingCells(new ArrayList<MappingCell>(mappingCells));
			}
			dispose();
		}
	}
	
	/** Initializes the search dialog */
	public SelectionDialog(HarmonyModel harmonyModel, Integer mode)
	{
		super(harmonyModel.getBaseFrame());
		this.harmonyModel = harmonyModel;
		this.mode = mode;
		
		// Initialize the type filter
		typeFilter = new OptionPane("Type",new String[]{"All","User","System"});
		typeFilter.setBorder(new EmptyBorder(0,20,0,0));
		typeFilter.setSelectedButton("All");
		
		// Initialize the focus filter
		focusFilter = new OptionPane("Focus",new String[]{"All","Focused","Unfocused"});
		focusFilter.setBorder(new EmptyBorder(0,13,0,0));
		focusFilter.setSelectedButton(mode==SELECT ? "Focused" : "All");
		focusFilter.setEnabled(mode==DELETE);

		// Initialize the visibility filter
		visibilityFilter = new OptionPane("Visibility",new String[]{"All","Visible","Hidden"});		
		visibilityFilter.setSelectedButton(mode==SELECT ? "Visible" : "All");
		visibilityFilter.setEnabled(mode==DELETE);
		
		// Create the info pane
		JPanel infoPane = new JPanel();
		infoPane.setBorder(new CompoundBorder(new EmptyBorder(5,5,0,5),new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(5,5,5,5))));
		infoPane.setLayout(new GridLayout(3,1));
		infoPane.add(typeFilter);
		infoPane.add(focusFilter);
		infoPane.add(visibilityFilter);
		
		// Generate the main dialog pane
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createLineBorder(Color.black));
		pane.setLayout(new BorderLayout());
		pane.add(infoPane,BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		
		// Initialize the dialog parameters
		setTitle((mode==SELECT ? "Select" : "Delete") + " Links");
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(pane);
		setSize(200,250);
		setResizable(false);
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		pack();
		setVisible(true);
	}
}