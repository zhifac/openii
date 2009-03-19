// © The MITRE Corporation 2006
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
 * Class used for allowing the selection of match voters
 * @author CWOLF
 */
class MatchVoterDialog extends JDialog implements ActionListener, WindowListener
{		
	// List of selected match voters
	private ArrayList<MatchVoter> selMatchVoters = null;
	
	// Set of checkboxes containing the match voter selection
	private JPanel checkboxPane = null;

	// Buttons used for controlling the dialog
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	
	/**
	 * Class for storing match voter checkbox items
	 */
	private class MatchVoterCheckBox extends JCheckBox
	{
		// Stores the voter associated with this checkbox
		private MatchVoter voter;
		
		/**
		 * Initializes the match voter check box
		 * @param voter - Voter associated with this checkbox
		 */
		MatchVoterCheckBox(MatchVoter voter)
		{
			this.voter = voter;
			setText(voter.getName());
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
		checkboxPane.setLayout(new BoxLayout(checkboxPane,BoxLayout.Y_AXIS));
		
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
		pane.add(new JLabel("Please select which match voters to use"),BorderLayout.NORTH);
		pane.add(checkboxScrollPane,BorderLayout.CENTER);
		pane.add(buttonPane,BorderLayout.SOUTH);
		return pane;
	}

	/**
	 * Constructs the match voter dialog
	 */
	MatchVoterDialog(HarmonyModel harmonyModel)
	{
		super(harmonyModel.getBaseFrame());
		
		// Set up loader dialog layout and contents
		setTitle("Which Match Voters Should Be Used?");
		setModal(true);
    	setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setContentPane(mainPane());
		pack();
		setLocationRelativeTo(harmonyModel.getBaseFrame());
		
		// Listen for window events so window can be gracefully closed
		addWindowListener(this);
	}

	/**
	 * @param matchVoters - List of all match voters available
	 * @return The selected match voters
	 */
	ArrayList<MatchVoter> getSelectedMatchVoters()
	{
		// Initialize the selected match voters
		selMatchVoters = null;
		
		// Display all selected match voters
		checkboxPane.removeAll();
		for(MatchVoter matchVoter : MatcherManager.getVoters())
			checkboxPane.add(new MatchVoterCheckBox(matchVoter));
		setVisible(true);
			
		// Return the selected match voters once selected
		try { while(selMatchVoters==null) Thread.sleep(100); } catch(InterruptedException e) {}
		return selMatchVoters;
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
			ArrayList<MatchVoter> tempSelMatchVoters = new ArrayList<MatchVoter>();
			for(Component checkbox : checkboxPane.getComponents())
				if(((MatchVoterCheckBox)checkbox).isSelected())
					tempSelMatchVoters.add(((MatchVoterCheckBox)checkbox).voter);
			
			// Outputs the selected match voters
			if(tempSelMatchVoters.size()==0)
				{ JOptionPane.showMessageDialog(this,"Please select at least one match voter!"); }
			else
				{ selMatchVoters = tempSelMatchVoters; dispose(); }
		}
		
		// If the cancel button was pressed, return no selected match voters
		if(e.getSource()==cancelButton)
			{ selMatchVoters = new ArrayList<MatchVoter>(); dispose(); }
	}
	
	/**
	 * Forces graceful closing of MatchVoterDialog
	 */
    public void windowClosing(WindowEvent event)
		{ selMatchVoters = new ArrayList<MatchVoter>(); dispose(); }
	
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