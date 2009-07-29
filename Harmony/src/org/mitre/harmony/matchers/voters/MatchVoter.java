// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.graph.FilteredGraph;

/** MatchVoter Interface - A match voter scores source-target linkages based on a specific algorithm */	
public interface MatchVoter
{
	/** Return the name of the match voter */
	public String getName();
	
	/** Generates scores for the specified graphs */
	public VoterScores match(FilteredGraph sourceSchema, FilteredGraph targetSchema);
}