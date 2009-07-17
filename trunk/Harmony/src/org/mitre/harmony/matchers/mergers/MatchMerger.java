// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.mergers;

import java.util.HashMap;

import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.graph.FilteredGraph;

/** Matcher Interface - A match merger merged together the results of multiple match voters */
public interface MatchMerger
{
	/** Return the name of the match merger */
	public String getName();

	/** Initializes the match merger to run */
	public void initialize(FilteredGraph schema1, FilteredGraph schema2,  HashMap<String, Integer> typeMap);
	
	/** Feeds voter scores into the merger */
	public void addVoterScores(VoterScores scores);
	
	/** Retrieve match scores from the merger */
	public MatchScores getMatchScores();	
}