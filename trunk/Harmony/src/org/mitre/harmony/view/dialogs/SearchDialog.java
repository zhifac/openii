// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.controllers.FocusController;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.MappingSchema;

/**
 * Displays the search dialog for search for keywords in schemas
 * @author CWOLF
 */
public class SearchDialog extends JDialog implements KeyListener
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	// Stores the search field
	private JTextField searchField = new JTextField();
	
	// Stores the search dialog options
	private JRadioButton leftButton = new JRadioButton("Left");
	private JRadioButton rightButton = new JRadioButton("Right");	
	private JRadioButton bothButton = new JRadioButton("Both");
	
	/** Private class for defining the button pane */
	private class ButtonPane extends AbstractButtonPane
	{
		/** Constructs the button pane */
		private ButtonPane()
			{ super(new String[]{"Set Focus", "Add to Focus","Search","Cancel"},2,2); }

		/** Handles selection of button */
		protected void buttonPressed(String label)
		{
			// Run a search if "Search" selected
			if(!label.equals("Cancel"))
			{
				// Only operate on selected sides
				for(Integer side : getSelectedSides())
				{
					// Run search on specified query
					harmonyModel.getSearchManager().runQuery(side, searchField.getText());

					// Set focus areas on search results
					if(label.equals("Set Focus") || label.equals("Add to Focus"))
					{
						boolean append = label.equals("Add to Focus");
						FocusController.setFocusOnSearchResults(harmonyModel, side, append);
					}
				}
			}
			
			// Close down the search pane if "Cancel" selected
			if(label.equals("Cancel")) dispose();
		}
	}
		
	/** Generates the info pane */
	private JPanel getInfoPane()
	{
		// Generate the search label
		JLabel searchLabel = new JLabel("Search: ");
		searchLabel.setBorder(new EmptyBorder(0,0,15,0));
		
		// Generate the regular expression label
		JLabel regExpLabel = new JLabel("(regular expressions permitted)");
		regExpLabel.setFont(new Font("Default",Font.PLAIN,9));
		
		// Add a key listener to the search field
		searchField.addKeyListener(this);
		
		// Generates the search field pane
		JPanel searchFieldPane = new JPanel();
		searchFieldPane.setLayout(new BorderLayout());
		searchFieldPane.add(searchField,BorderLayout.NORTH);
		searchFieldPane.add(regExpLabel,BorderLayout.SOUTH);
		
		// Generates the search pane
		JPanel searchPane = new JPanel();
		searchPane.setLayout(new BoxLayout(searchPane,BoxLayout.X_AXIS));
		searchPane.add(searchLabel);
		searchPane.add(searchFieldPane);
		
		// Generates the side selection options
		JPanel sidePane = new JPanel();
		sidePane.setBorder(new EmptyBorder(3,0,0,4));
		sidePane.setLayout(new BoxLayout(sidePane,BoxLayout.X_AXIS));
		sidePane.add(new JLabel("Side: "));
		ButtonGroup buttonGroup = new ButtonGroup();
		for(JRadioButton button : new JRadioButton[]{leftButton,rightButton,bothButton})
		{
			buttonGroup.add(button);
			button.setFont(new Font("Arial",Font.PLAIN,12));
			button.setFocusable(false);
			sidePane.add(button);
		}
		bothButton.setSelected(true);
		
		// Generate the info pane
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(10,10,0,10));
		pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
		pane.add(searchPane);
		pane.add(sidePane);
		return pane;
	}
	
	/** Initializes the search dialog */
	public SearchDialog(HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		this.harmonyModel = harmonyModel;
		
		// Generate the main dialog pane
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createLineBorder(Color.black));
		pane.setLayout(new BorderLayout());
		pane.add(getInfoPane(),BorderLayout.CENTER);
		pane.add(new ButtonPane(),BorderLayout.SOUTH);
		
		// Initialize the dialog parameters
		setTitle("Search");
    	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setContentPane(pane);
		setSize(200,250);
		setResizable(false);
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		pack();
		setVisible(true);
	}
	
	/** Returns the selected sides */
	private ArrayList<Integer> getSelectedSides()
	{
		ArrayList<Integer> sides = new ArrayList<Integer>();
		if(!rightButton.isSelected()) sides.add(MappingSchema.LEFT);
		if(!leftButton.isSelected()) sides.add(MappingSchema.RIGHT);
		return sides;
	}
	
	/** Update matches every time a new search keyword is entered */
	public void keyTyped(KeyEvent e)
	{
		if(e.getKeyChar() == KeyEvent.VK_ENTER)
			for(Integer side : getSelectedSides())
				harmonyModel.getSearchManager().runQuery(side, searchField.getText());
	}
	
	/** Unused listener actions */
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}