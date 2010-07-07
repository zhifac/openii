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
import org.mitre.harmony.view.dialogs.matcher.wizard.Wizard;
import org.mitre.harmony.view.dialogs.matcher.wizard.WizardPanel;

/** Constructs the match voter pane for the matcher wizard */
public class SelectMatchersPanel extends WizardPanel implements ActionListener {
    // set of checkboxes containing the match voter selection
	private JPanel checkboxPane = null;

	// checkbox that stores the advanced mode setting
	private JCheckBox advancedCheckbox = null;

	/** Constructs the match voter pane */
    public SelectMatchersPanel(Wizard wizard, ArrayList<MatchVoter> matchers) {
    	super.setWizard(wizard);

    	// create the advanced mode checkbox
    	advancedCheckbox = new JCheckBox("Select advanced matcher options.", true);
    	advancedCheckbox.addActionListener(this);

    	// Create pane for storing all match voters
		checkboxPane = new JPanel();
		checkboxPane.setBackground(Color.white);
		checkboxPane.setLayout(new BoxLayout(checkboxPane,BoxLayout.Y_AXIS));

		// populate the matchers into the checklist
		for (MatchVoter matcher : MatcherManager.getVisibleMatchers()) {
			MatchersCheckBox checkbox = new MatchersCheckBox(matcher);
			checkbox.setSelected(matchers.contains(matcher));
			checkbox.addActionListener(this);
			checkboxPane.add(checkbox);
		}

		// Place list of match voters in center of pane
		JPanel panel = getPanel();
		panel.setBorder(new EmptyBorder(20,20,20,20));
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("Please select which match voters to use:"),BorderLayout.NORTH);
		panel.add(new JScrollPane(checkboxPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		panel.add(advancedCheckbox, BorderLayout.SOUTH);
    }

	/** Class for storing match voter checkbox items */
	private class MatchersCheckBox extends JCheckBox {
		// Stores the voter associated with this checkbox
		private MatchVoter voter;

		/** initializes the match voter check box */
		MatchersCheckBox(MatchVoter voter) {
			this.voter = voter;
			setText(voter.getName());
			setBackground(Color.white);
			setFocusable(false);
		}
	}

	/** retrieves the selected list of match voters */
	private ArrayList<MatchVoter> getSelectedMatchers() {
		// Retrieves all selected match voters from the dialog
		ArrayList<MatchVoter> selectedMatchers = new ArrayList<MatchVoter>();
		for (Component checkbox : checkboxPane.getComponents()) {
			if (((MatchersCheckBox)checkbox).isSelected()) {
				selectedMatchers.add(((MatchersCheckBox)checkbox).voter);
			}
		}
		return selectedMatchers;
	}

	/** updates the 'next' button whenever the checkboxes are changed */
	public void actionPerformed(ActionEvent e) {
    	getWizard().setAdvancedMode(advancedCheckbox.isSelected());
    	getWizard().setSelectedMatchers(getSelectedMatchers());
		getWizard().setNextButtonEnabled(getSelectedMatchers().size() > 0);
	}

    /** handles the hiding of the match pane */
    public void aboutToHidePanel() {}

    /** called right before this pane is displayed */
	public void aboutToDisplayPanel() {}

	/** called when this pane is displayed */
	public void displayingPanel() {}
}