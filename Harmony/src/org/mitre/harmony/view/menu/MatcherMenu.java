// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.mergers.MatchMerger;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.project.ProjectManager;
import org.mitre.harmony.view.dialogs.matcher.MatchingStatusPane;
import org.mitre.harmony.view.dialogs.matcher.SelectMatchersPane;
import org.mitre.harmony.view.dialogs.matcher.SelectMatchOptionsPane;
import org.mitre.harmony.view.dialogs.matcher.SelectMatchTypePane;
import org.mitre.harmony.view.dialogs.matcher.wizard.Wizard;
import org.mitre.harmony.view.dialogs.matcher.wizard.WizardPanel;

/** Menu to display all available matchers */
public class MatcherMenu extends JMenu {
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;

	/** Initializes the matcher menu to display all installed matchers */
	public MatcherMenu(HarmonyModel harmonyModel) {
		super("Matchers");
		this.harmonyModel = harmonyModel;
		setMnemonic(KeyEvent.VK_M);

		// Place all matchers into menu
		for (MatchMerger merger : MatcherManager.getMergers()) {
			add(new MatcherMenuItem(merger));
		}

		// Place all match voters into menu
		for (MatchVoter matchVoter : MatcherManager.getVisibleVoters()) {
			add(new MatchVoterMenuItem(matchVoter));
		}
	}

	/** Class for handling match voter menu items */
	private class MatchVoterMenuItem extends JMenuItem implements ActionListener {
		// Stores the voter associated with this menu item
		private MatchVoter voter;

		/** Initializes the match voter menu item */
		MatchVoterMenuItem(MatchVoter voter) {
			this.voter = voter;
			setText(voter.getName());
			addActionListener(this);
		}

		/** Handles the selection of this match voter */
		public void actionPerformed(ActionEvent e) {
			ArrayList<MatchVoter> voters = new ArrayList<MatchVoter>();
			voters.add(voter);
			launchMatchWizard(voters,new VoteMerger(),false);
		}
	}

	/** Class for handling matcher menu */
	private class MatcherMenuItem extends JMenu implements ActionListener {
		// Stores the voter associated with this menu
		private MatchMerger merger;

		// Stores the menu items which may be selected
		JMenuItem fullMatch = new JMenuItem("Run All Matchers");
		JMenuItem customMatch = new JMenuItem("Run Advanced Matchers...");

		/** Initializes the matcher menu */
		MatcherMenuItem(MatchMerger merger) {
			this.merger = merger;
			setText(merger.getName());

			// Creates a full match menu item
			fullMatch.addActionListener(this);
			add(fullMatch);

			// Creates a custom match menu item
			customMatch.addActionListener(this);
			add(customMatch);
		}

		/** Handles the selection of this match voter */
		public void actionPerformed(ActionEvent e) {
			// No need to proceed if no schemas exist on a specific side
			ProjectManager projectManager = harmonyModel.getProjectManager();
			Integer leftSchemas = projectManager.getSchemaElements(HarmonyConsts.LEFT).size();
			Integer rightSchemas = projectManager.getSchemaElements(HarmonyConsts.RIGHT).size();
			if (leftSchemas.equals(0) || rightSchemas.equals(0)) { return; }

			// Run the matcher (through the matcher wizard)
			if (e.getSource() == fullMatch) {
				launchMatchWizard(MatcherManager.getDefaultVoters(), merger, false);
			} else if (e.getSource() == customMatch) {
				launchMatchWizard(MatcherManager.getDefaultVoters(), merger, true);
			}
		}
	}

	/** Launches the matcher wizard */
	private void launchMatchWizard(ArrayList<MatchVoter> voters, MatchMerger merger, boolean custom) {
		// Generate the match wizard
		Wizard wizard = new Wizard(harmonyModel.getBaseFrame());
        wizard.getDialog().setTitle("Run Schema Matchers");

        SelectMatchersPane     selectMatchersPane     = new SelectMatchersPane(Wizard.SELECT_MATCHERS_PANEL, voters);
        SelectMatchTypePane    selectMatchTypePane    = new SelectMatchTypePane(Wizard.SELECT_MATCH_TYPE_PANEL, harmonyModel);
        SelectMatchOptionsPane selectMatchOptionsPane = new SelectMatchOptionsPane(Wizard.SELECT_MATCH_OPTIONS_PANEL);
        MatchingStatusPane     matchingStatusPane     = new MatchingStatusPane(Wizard.MATCHING_STATUS_PANEL, harmonyModel, merger);

        // create instances of all of our panes and add them to the wizard
        wizard.registerWizardPanel((WizardPanel)selectMatchersPane);
        wizard.registerWizardPanel((WizardPanel)selectMatchTypePane);
        wizard.registerWizardPanel((WizardPanel)selectMatchOptionsPane);
        wizard.registerWizardPanel((WizardPanel)matchingStatusPane);

        // Specify the screen to be displayed
        wizard.setCurrentPanel(custom ? selectMatchersPane.getPanelId() : matchingStatusPane.getPanelId());
        wizard.showDialog();
	}
}