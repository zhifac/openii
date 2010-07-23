// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.mergers.MatchMerger;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.project.ProjectManager;
import org.mitre.harmony.view.dialogs.matcher.wizard.Wizard;

/** Menu to display all available matchers */
public class MatcherMenu extends AbstractMenu
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;

	/** Initializes the matcher menu to display all installed matchers */
	public MatcherMenu(HarmonyModel harmonyModel)
	{
		super("Matchers");
		this.harmonyModel = harmonyModel;
		setMnemonic(KeyEvent.VK_M);

		// Place all matchers into menu
		for(MatchMerger merger : MatcherManager.getMergers())
			add(new MatcherMenuItem(merger));

		// Place all match voters into menu
		for(MatchVoter matchVoter : MatcherManager.getVisibleMatchers())
			add(new MatchVoterMenuItem(matchVoter));

		// Add a menu checkbox to specify if user matched elements should be ignored in matching
		addSeparator();
		add(createCheckboxItem("Ignore Matched Elements", harmonyModel.getPreferences().getIgnoreMatchedElements(), new IgnoreMatchedElementsAction()));
	}

	/** Class for handling match voter menu items */
	private class MatchVoterMenuItem extends JMenuItem implements ActionListener
	{
		// Stores the voter associated with this menu item
		private MatchVoter voter;

		/** Initializes the match voter menu item */
		MatchVoterMenuItem(MatchVoter voter)
		{
			this.voter = voter;
			setText(voter.getName());
			addActionListener(this);
		}

		/** Handles the selection of this match voter */
		public void actionPerformed(ActionEvent e)
		{
			if(!checkSchemasExist()) return;

			ArrayList<MatchVoter> voters = new ArrayList<MatchVoter>();
			voters.add(voter);
			launchMatchWizard(voters, new VoteMerger(), false);
		}
	}

	/** Class for handling matcher menu */
	private class MatcherMenuItem extends JMenu implements ActionListener
	{
		// Stores the voter associated with this menu
		private MatchMerger merger;

		// Stores the menu items which may be selected
		JMenuItem fullMatch = new JMenuItem("Run All Matchers");
		JMenuItem customMatch = new JMenuItem("Run Custom Matchers...");

		/** Initializes the matcher menu */
		MatcherMenuItem(MatchMerger merger)
		{
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
		public void actionPerformed(ActionEvent e)
		{
			if(!checkSchemasExist()) { return; }

			// Run the matcher (through the matcher wizard)
			if(e.getSource() == fullMatch)
				launchMatchWizard(MatcherManager.getDefaultMatchers(), merger, false);
			else if (e.getSource() == customMatch)
				launchMatchWizard(MatcherManager.getDefaultMatchers(), merger, true);
		}
	}

	private boolean checkSchemasExist()
	{
		// No need to proceed if no schemas exist on a specific side
		ProjectManager projectManager = harmonyModel.getProjectManager();
		Integer leftSchemas = projectManager.getSchemaElements(HarmonyConsts.LEFT).size();
		Integer rightSchemas = projectManager.getSchemaElements(HarmonyConsts.RIGHT).size();

		// if no schemas are open, tell the user wtf
		if(leftSchemas.equals(0) || rightSchemas.equals(0))
		{
			JOptionPane.showMessageDialog(getParent(), "No schemas are currently open to match.", "Matching Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/** Launches the matcher wizard */
	private void launchMatchWizard(ArrayList<MatchVoter> voters, MatchMerger merger, boolean custom)
		{ new Wizard(voters, merger, custom, harmonyModel); }

	/** Action for ignoring the matched elements */
	private class IgnoreMatchedElementsAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e)
		{
			boolean isSelected = ((JCheckBoxMenuItem)(e.getSource())).isSelected();			
			harmonyModel.getPreferences().setIgnoredMatchedElements(isSelected);
		}
	}
}