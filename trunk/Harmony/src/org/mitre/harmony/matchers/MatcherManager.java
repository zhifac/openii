// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers;

import java.util.ArrayList;

import org.mitre.harmony.matchers.mergers.MatchMerger;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.harmony.model.ToolManager;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/**
 * Manages the Harmony Matchers
 * @author CWOLF
 */
public class MatcherManager
{
	/** Stores a listing of all match voters */
	static private ArrayList<MatchVoter> voters = new ArrayList<MatchVoter>();

	/** Stores a listing of all match mergers */
	static private ArrayList<MatchMerger> mergers = new ArrayList<MatchMerger>();
	
	/** Initializes the matcher manager with all defined match voters and mergers */
	static
	{
		// Retrieve the match voters
		for(String voterString : ToolManager.getTools("voter"))
			try {
				Class voterClass = Class.forName(voterString);
				voters.add((MatchVoter)voterClass.newInstance());
			}
		    catch(Exception e) { System.out.println("(E)MatcherManager - Failed to locate voter class "+voterString); }

		// Retrieve match mergers
		for(String mergerString : ToolManager.getTools("merger"))
			try {
				Class mergerClass = Class.forName(mergerString);
				mergers.add((MatchMerger)mergerClass.newInstance());
			}
		    catch(Exception e) { System.out.println("(E)MatcherManager - Failed to locate merger class "+mergerString); }
	}

	/** Returns the list of match voters */
	static public ArrayList<MatchVoter> getVoters()
		{ return voters; }
	
	/** Returns the list of match mergers */
	static public ArrayList<MatchMerger> getMergers()
		{ return mergers; }
	
	/** Run the matchers to calculate match scores */
	static public MatchScores getScores(FilteredSchemaInfo schemaInfo1, FilteredSchemaInfo schemaInfo2, ArrayList<MatchVoter> voters, MatchMerger merger)
	{
		merger.initialize(schemaInfo1, schemaInfo2);
		for(MatchVoter voter : voters)
			merger.addVoterScores(voter.match(schemaInfo1, schemaInfo2));
		return merger.getMatchScores();
	}
}
