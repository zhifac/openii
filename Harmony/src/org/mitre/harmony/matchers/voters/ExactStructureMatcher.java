// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.HashSet;

import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.FilteredGraph;

/** Exact Structure Matcher Class */
public class ExactStructureMatcher implements MatchVoter
{
	/** Returns the name of the match voter */
	public String getName()
		{ return "Exact Structure"; }
	
	/** Generates scores for the specified elements */
	public VoterScores match(FilteredGraph sourceSchema, FilteredGraph targetSchema)
	{
		// Go through all source elements
		VoterScores scores = new VoterScores(100.0);
		ArrayList<SchemaElement> sourceElements = sourceSchema.getFilteredElements();
		for(SchemaElement sourceElement : sourceElements)
		{
			// Retrieve all matching target elements
			HashSet<Integer> targetIDs = new HashSet<Integer>();
			for(ArrayList<SchemaElement> sourcePath : sourceSchema.getPaths(sourceElement.getId()))
			{
				ArrayList<String> path = new ArrayList<String>();
				for(SchemaElement element : sourcePath)
					path.add(sourceSchema.getDisplayName(element.getId()));
				targetIDs.addAll(targetSchema.getPathIDs(path));
			}
	
			// Set scores for the matching target elements
			for(Integer targetID : targetIDs)
				scores.setScore(sourceElement.getId(), targetID, new VoterScore(100.0,100.0));								
		}
		return scores;
	}
}