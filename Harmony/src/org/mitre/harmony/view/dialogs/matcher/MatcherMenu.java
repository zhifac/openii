// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.dialogs.matcher;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.mergers.MatchMerger;
import org.mitre.harmony.matchers.mergers.VoteMerger;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.model.HarmonyModel;

/**
 * Menu to display all available matchers
 */
public class MatcherMenu extends JMenu
{
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/**
	 * Class for handling match voter menu items
	 */
	private class MatchVoterMenuItem extends JMenuItem implements ActionListener
	{
		// Stores the voter associated with this menu item
		private MatchVoter voter;
		
		/**
		 * Initializes the match voter menu item
		 * @param voter - Voter associated with this menu item
		 */
		MatchVoterMenuItem(MatchVoter voter)
		{
			this.voter = voter;
			setText(voter.getName());
			addActionListener(this);
		}

		/**
		 * Handles the selection of this match voter
		 */
		public void actionPerformed(ActionEvent e)
		{
			ArrayList<MatchVoter> voters = new ArrayList<MatchVoter>();
			voters.add(voter);
			new MatcherDialog(new VoteMerger(),voters, null, harmonyModel);
		}
	}
	
	/**
	 * Class for handling matcher menu
	 */
	private class MatcherMenuItem extends JMenu implements ActionListener
	{
		// Stores the voter associated with this menu
		private MatchMerger matcher;
		
		// Stores the menu items which may be selected
		JMenuItem fullMatch = new JMenuItem("Full Match");
		JMenuItem customMatch = new JMenuItem("Custom Match...");
		
		/**
		 * Initializes the matcher menu
		 * @param matcher - Matcher associated with this menu
		 */
		MatcherMenuItem(MatchMerger matcher)
		{
			this.matcher = matcher;
			setText(matcher.getName());

			// Creates a full match menu item
			fullMatch.addActionListener(this);
			add(fullMatch);
			
			// Creates a custom match menu item
			customMatch.addActionListener(this);
			add(customMatch);
		}

		/**
		 * Handles the selection of this match voter
		 */
		public void actionPerformed(ActionEvent e)
		{
			HashMap<String, Integer> typeMap = null;
			
			// If we are using the typed vote merger, open the dialog that lets
			// the user select which types to match to which types
			if (matcher.getName().equals("Typed Vote Merger")) {
				typeMap = new TypeDialog(harmonyModel).getTypeMap();				
			}
			
			if(e.getSource()==fullMatch) new MatcherDialog(matcher,MatcherManager.getVoters(), typeMap, harmonyModel);
			else if(e.getSource()==customMatch)
			{
				ArrayList<MatchVoter> voters = new MatchVoterDialog(harmonyModel).getSelectedMatchVoters();
				if(voters != null && voters.size()>0) new MatcherDialog(matcher,voters, typeMap, harmonyModel);
			}
		}
	}
	
	/**
	 * Initializes the matcher menu to display all installed matchers
	 */
	public MatcherMenu(HarmonyModel harmonyModel)
	{
		super("Matchers");
		this.harmonyModel = harmonyModel;
		setMnemonic(KeyEvent.VK_M);

		// Place all matchers into menu
		for(MatchMerger matcher : MatcherManager.getMergers())
			this.add(new MatcherMenuItem(matcher));
		
		// Place all match voters into menu
		for(MatchVoter matchVoter : MatcherManager.getVoters())
			this.add(new MatchVoterMenuItem(matchVoter));
	}
}