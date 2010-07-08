// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.HashSet;

import org.mitre.harmony.matchers.MatcherScore;
import org.mitre.harmony.matchers.MatcherScores;
import org.mitre.schemastore.model.SchemaElement;

/** Exact Structure Matcher Class */
public class ExactStructureMatcher extends MatchVoter {
	/** Returns the name of the match voter */
	public String getName() {
		return "Exact Structure";
	}
	
	/** Generates scores for the specified elements */
	public MatcherScores match() {
		// Get the source elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		
		// Sets the current and total comparisons
		completedComparisons = 0;
		totalComparisons = sourceElements.size();
		
		// Go through all source elements
		MatcherScores scores = new MatcherScores(100.0);
		for (SchemaElement sourceElement : sourceElements) {
			// Retrieve all matching target elements
			HashSet<Integer> targetIDs = new HashSet<Integer>();
			for (ArrayList<SchemaElement> sourcePath : schema1.getPaths(sourceElement.getId())) {
				// Retrieve the source path
				ArrayList<String> path = new ArrayList<String>();
				for (SchemaElement element : sourcePath) {
					path.add(schema1.getDisplayName(element.getId()));
				}
				
				// Identify all target paths
				for (Integer targetID : schema2.getPathIDs(path)) {
					if (schema2.isVisible(targetID)) {
						targetIDs.add(targetID);
					}
				}
			}
	
			// Set scores for the matching target elements
			for (Integer targetID : targetIDs) {
				scores.setScore(sourceElement.getId(), targetID, new MatcherScore(100.0,100.0));
			}

			// Update the completed comparison count
			completedComparisons++;
		}
		return scores;
	}
}