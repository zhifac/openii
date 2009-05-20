// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.mergers;

import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.VoterScores;

/** Matcher Interface - A match merger merged together the results of multiple match voters */
public interface MatchMerger
{
	/** Return the name of the match merger */
	public String getName();

	/** Initializes the match merger to run */
	public void initialize();
	
	/** Feeds voter scores into the merger */
	public void addVoterScores(VoterScores scores);
	
	/** Retrieve match scores from the merger */
	public MatchScores getMatchScores();	
}