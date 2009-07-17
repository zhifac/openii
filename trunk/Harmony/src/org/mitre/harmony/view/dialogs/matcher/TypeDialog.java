// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.matcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.model.HarmonyModel;

/**
 * Class used for selecting which type(s) to match to which type(s)
 * (e.g. Entities to entities and attributes to attributes.) 
 * @author JKORB
 */
class TypeDialog extends JDialog implements ActionListener, WindowListener
{		
	// List of selected match voters
	private HashMap<String,Integer> selTypePairs = null;
	
	// Set of checkboxes containing the match voter selection
	private JPanel checkboxPane = null;

	// Buttons used for controlling the dialog
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	
	/**
	 * Class for storing type pair checkbox items
	 */
	private class TypePairCheckBox extends JCheckBox
	{
		// Stores the type pair associated with this checkbox
		private String typePair;
		
		/**
		 * Initializes the type pair check box
		 * @param typePair - type pair associated with this checkbox
		 */
		TypePairCheckBox(String typePair)
		{
			this.typePair = typePair;
			setText("");
			setBackground(Color.white);
			setFocusable(false);
		}
	}
	
	/**
	 * @return The main pane for the match voter dialog
	 */
	private JPanel mainPane()
	{
		// Create pane for storing all checkbox items
		checkboxPane = new JPanel();
		checkboxPane.setBackground(Color.white);
		checkboxPane.setLayout(new GridLayout(8, 8));
		
		JScrollPane checkboxScrollPane = new JScrollPane(checkboxPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		checkboxScrollPane.setPreferredSize(new Dimension(250, 300));

	    okButton.addActionListener(this);
	    cancelButton.addActionListener(this);
	    
		// Create button pane
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new GridLayout(1,2));
		buttonPane.add(okButton);
		buttonPane.add(cancelButton);
	    
		// Place list of roots in center of project pane
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(20,20,20,20));
		pane.setLayout(new BorderLayout());
		pane.add(new JLabel("                          Alias   Attribute Containment Domain DomainValue Entity Relationship Subtype     "),BorderLayout.NORTH);
		
		JLabel longLabel = new JLabel();
		longLabel.setText("<html><body>Alias<p><p>" +
				"Attribute<p><p>" +
				"Containment<p><p>" +
				"Domain<p><p><p>" +
				"DomainValue <p><p>" +
				"Entity<p><p>" +
				"Relationship<p><p>" +
				"Subtype<p></body></html>");

		pane.add(longLabel, BorderLayout.WEST);
		
		pane.add(checkboxScrollPane,BorderLayout.CENTER);
		pane.add(buttonPane,BorderLayout.SOUTH);
		return pane;
	}

	/**
	 * Constructs the match voter dialog
	 */
	TypeDialog(HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		
		// Set up loader dialog layout and contents
		setTitle("Which Types Would You Like To Match?");
		setModal(true);
    	setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setContentPane(mainPane());
		pack();
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		
		// Listen for window events so window can be gracefully closed
		addWindowListener(this);
	}

	/**
	 * @param 
	 * @return The selected type mapping
	 */
	HashMap<String, Integer> getTypeMap()
	{
		// Initialize the selected type pairs
		selTypePairs = null;
		
		// Display all selected type pairs
		checkboxPane.removeAll();
		
		String[] types = {
				"org.mitre.schemastore.model.Alias",
				"org.mitre.schemastore.model.Attribute",
				"org.mitre.schemastore.model.Containment",
				"org.mitre.schemastore.model.Domain",
				"org.mitre.schemastore.model.Domainvalue",
				"org.mitre.schemastore.model.Entity",
				"org.mitre.schemastore.model.Relationship",
				"org.mitre.schemastore.model.Subtype"
		};
		
		for(int i = 0; i < types.length; i++) {
			for (int j = 0; j < types.length; j++) {
				TypePairCheckBox cb = new TypePairCheckBox(types[i] + "," + types[j]);
				
				// By default, select all types to match themselves
				if (i == j)
					cb.setSelected(true);
				
				checkboxPane.add(cb);
			}
		}
			
		setVisible(true);
			
		// Return the selected match voters once selected
		try { while(selTypePairs==null) Thread.sleep(100); } catch(InterruptedException e) {}
		
		
		// Types are alias, attribute, containment, domain, domainvalue, 
		// entity, relationship, and subtype
//		HashMap<String, Integer> typeMap = new HashMap<String, Integer>();
		
		
		return selTypePairs;
	}

	/**
	 * Handles user interactions with the match voter selection dialog
	 */
	public void actionPerformed(ActionEvent e)
	{
		// If okay button was pressed, return the selected match voters
		if(e.getSource()==okButton)
		{
			// Retrieves all selected match voters from the dialog
			HashMap<String, Integer> tempSelTypePair = new HashMap<String, Integer>();
			for(Component checkbox : checkboxPane.getComponents())
				if(((TypePairCheckBox)checkbox).isSelected())
					tempSelTypePair.put(((TypePairCheckBox)checkbox).typePair, 1);
			
			// Outputs the selected match voters
			if(tempSelTypePair.size()==0)
				{ JOptionPane.showMessageDialog(this,"Please select at least one type pair!"); }
			else
				{ selTypePairs = tempSelTypePair; dispose(); }
		}
		
		// If the cancel button was pressed, return no selected match voters
		if(e.getSource()==cancelButton)
			{ selTypePairs = new HashMap<String, Integer>(); dispose(); }
	}
	
	/**
	 * Forces graceful closing of MatchVoterDialog
	 */
    public void windowClosing(WindowEvent event)
		{ selTypePairs = new HashMap<String, Integer>(); dispose(); }
	
    //------------------------
    // Unused listener actions
    //------------------------
    public void windowActivated(WindowEvent event) {}
    public void windowClosed(WindowEvent event) {}
    public void windowDeactivated(WindowEvent event) {}
    public void windowDeiconified(WindowEvent event) {}
    public void windowIconified(WindowEvent event) {}
    public void windowOpened(WindowEvent event) {}
}