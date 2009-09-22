package org.mitre.harmony.view.dialogs.matcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.dialogs.matcher.wizard.WizardPanel;

/** Constructs the match voter pane for the matcher wizard */
public class MatchVoterPane extends WizardPanel implements ActionListener
{    
	// Defines the identifier for the match voter pane
	static public final String IDENTIFIER = "MATCH_VOTER_PANEL";
	
	// Set of checkboxes containing the match voter selection
	private JPanel checkboxPane = null;
	
	/** Class for storing match voter checkbox items */
	private class MatchVoterCheckBox extends JCheckBox
	{
		// Stores the voter associated with this checkbox
		private MatchVoter voter;
		
		/** Initializes the match voter check box */
		MatchVoterCheckBox(MatchVoter voter)
		{
			this.voter = voter;
			setText(voter.getName());
			setBackground(Color.white);
			setFocusable(false);
		}
	}

    /** Constructs the match voter pane */
    public MatchVoterPane(HarmonyModel harmonyModel, ArrayList<MatchVoter> matchVoters)
    {
		// Create pane for storing all match voters
		checkboxPane = new JPanel();
		checkboxPane.setBackground(Color.white);
		checkboxPane.setLayout(new BoxLayout(checkboxPane,BoxLayout.Y_AXIS));
		
		// Populate the match voters
		for(MatchVoter matchVoter : MatcherManager.getVoters())
		{
			MatchVoterCheckBox checkbox = new MatchVoterCheckBox(matchVoter);
			checkbox.setSelected(matchVoters.contains(matchVoter));
			checkbox.addActionListener(this);
			checkboxPane.add(checkbox);
		}
	    
		// Place list of match voters in center of pane
		JPanel pane = getPanel();
		pane.setBorder(new EmptyBorder(20,20,20,20));
		pane.setLayout(new BorderLayout());
		pane.add(new JLabel("Please select which match voters to use"),BorderLayout.NORTH);
		pane.add(new JScrollPane(checkboxPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
    }
    
    /** Describes the next pane to display */
    public String getNextPanelDescriptor()
    	{ return TypePane.IDENTIFIER; }
    
    /** Describes the previous pane that was displayed */
    public String getBackPanelDescriptor()
    	{ return null; }

	/** Retrieves the selected list of match voters */
	ArrayList<MatchVoter> getSelectedMatchVoters()
	{
		// Retrieves all selected match voters from the dialog
		ArrayList<MatchVoter> selectedMatchVoters = new ArrayList<MatchVoter>();
		for(Component checkbox : checkboxPane.getComponents())
			if(((MatchVoterCheckBox)checkbox).isSelected())
				selectedMatchVoters.add(((MatchVoterCheckBox)checkbox).voter);
		return selectedMatchVoters;
	}

	/** Updates the 'next' button whenever the checkboxes are changed */
	public void actionPerformed(ActionEvent e)
		{ getWizard().setNextFinishButtonEnabled(getSelectedMatchVoters().size()>0); }
}