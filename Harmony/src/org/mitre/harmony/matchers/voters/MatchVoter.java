// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.graph.FilteredGraph;

/** MatchVoter Interface - A match voter scores source-target linkages based on a specific algorithm */	
public abstract class MatchVoter
{
	// Stores the completed and total number of comparisons that need to be performed
	protected int completedComparisons=0, totalComparisons=1;
	
	/** Return the name of the match voter */
	abstract public String getName();
	
	/** Generates scores for the specified graphs */
	abstract public VoterScores match(FilteredGraph sourceSchema, FilteredGraph targetSchema);
	
	/** Indicates the completion percentage of the matcher */
	public double getPercentComplete()
		{ return 1.0 * completedComparisons / totalComparisons; }
}