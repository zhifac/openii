package org.mitre.harmony.view.dialogs.matcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.harmony.matchers.MatcherOption;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.view.dialogs.matcher.options.CheckBox;
import org.mitre.harmony.view.dialogs.matcher.wizard.Wizard;
import org.mitre.harmony.view.dialogs.matcher.wizard.WizardPanel;

public class SelectMatchOptionsPanel extends WizardPanel {
	private ArrayList<MatcherOption> options = null;

	/** Constructs the type pane */
    public SelectMatchOptionsPanel(Wizard wizard, String id) {
    	super.setWizard(wizard);
    	super.id = id;

		// get the options for this matcher
		options = MatcherManager.getMatcherOptions(id);

    	// Initialize the layout of the options pane
		JPanel panel = getPanel();
		panel.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new CompoundBorder(new LineBorder(Color.lightGray),new EmptyBorder(5,5,5,5))));
		panel.setLayout(new BorderLayout());
		//panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// this panel labels what the options are for
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new EmptyBorder(1,0,0,2));
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		JLabel titleLabel = new JLabel(MatcherManager.getMatcher(id).getName() + " Options");
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titlePanel.add(titleLabel);
		panel.add(titlePanel, BorderLayout.NORTH);

		// this panel stores all the options
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

		// try to read each option and create a component for it
		for (int i = 0; i < options.size(); i++) {
			try {
				MatcherOption option = options.get(i);

				// checkbox
				if (option.getType().equals("checkbox")) {
					optionsPanel.add(new CheckBox(option));
				}

				// input field
				//else if (option.getType().equals("text")) {
				//}

				// other option types may follow at a later date
				//else if (option.getType().equals("")) {
				//}

				else {
					throw new Exception("Option type '" + option.getType() + "' specified on " + option.getId() + " in configuration for " + id + " not defined.");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(getPanel(), e.getMessage(), "Error Parsing Matcher Options", JOptionPane.ERROR_MESSAGE);
			}
		}

		// add the options to the main panel
		panel.add(optionsPanel, BorderLayout.CENTER);
    }

	public void aboutToHidePanel() {
		// set the matcher options back in the wizard
		getWizard().setSelectedMatcherOptions(id, options);
	}
	
	public void aboutToDisplayPanel() {}
	public void displayingPanel() {}
}
