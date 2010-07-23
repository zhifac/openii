package org.mitre.harmony.view.dialogs.matcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.view.dialogs.matcher.wizard.Wizard;
import org.mitre.harmony.view.dialogs.matcher.wizard.WizardPanel;

/** Constructs the match voter pane for the matcher wizard */
public class MatcherSelectionPane extends WizardPanel
{
	/** Class for storing match voter check box items */
	private class MatchersCheckBox extends JCheckBox 
	{
		/** Stores the voter associated with this check box */
		private MatchVoter voter;

		/** initializes the match voter check box */
		private MatchersCheckBox(MatchVoter voter)
		{
			this.voter = voter;
			setText(voter.getName());
			setBackground(Color.white);
			setFocusable(false);
		}
	}
	
	/** Pane which displays all of the matcher check boxes */
	private JPanel checkboxPane = null;

	/** Check box indicating if advanced mode should be run */
	private JCheckBox advancedCheckbox = null;

	/** Constructs the match voter pane */
    public MatcherSelectionPane(Wizard wizard, ArrayList<MatchVoter> matchers)
    {
    	super(wizard);

    	// Create pane for storing all match voters
		checkboxPane = new JPanel();
		checkboxPane.setBackground(Color.white);
		checkboxPane.setLayout(new BoxLayout(checkboxPane,BoxLayout.Y_AXIS));

		// Populate the matchers into the check list
		for (MatchVoter matcher : MatcherManager.getVisibleMatchers())
		{
			MatchersCheckBox checkbox = new MatchersCheckBox(matcher);
			checkbox.setSelected(matchers.contains(matcher));
			checkboxPane.add(checkbox);
		}

    	// Create the advanced mode check box
    	advancedCheckbox = new JCheckBox("Select advanced matcher options");
    	advancedCheckbox.setFocusable(false);
    	advancedCheckbox.setBorder(new EmptyBorder(8,0,0,0));
		
		// Place list of match voters in center of pane
		setBorder(new EmptyBorder(20,20,20,20));
		setLayout(new BorderLayout());
		add(new JLabel("Please select which match voters to use:"),BorderLayout.NORTH);
		add(new JScrollPane(checkboxPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		add(advancedCheckbox, BorderLayout.SOUTH);
    }

	/** Returns the list of match voters */
	public ArrayList<MatchVoter> getSelectedMatchers()
	{
		ArrayList<MatchVoter> selectedMatchers = new ArrayList<MatchVoter>();
		for(Component checkbox : checkboxPane.getComponents())
			if(((MatchersCheckBox)checkbox).isSelected())
				selectedMatchers.add(((MatchersCheckBox)checkbox).voter);
		return selectedMatchers;
	}

	/** Indicates if running in advanced mode */
	public boolean inAdvancedMode()
		{ return advancedCheckbox.isSelected(); }
}