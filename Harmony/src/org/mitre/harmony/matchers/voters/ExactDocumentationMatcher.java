// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;

import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;

/** Edit Distance Matcher Class */
public class ExactDocumentationMatcher extends MatchVoter
{
	/** Max value that this matcher can return.  A scaling factor */
	public static final double SCORE_CEILING = 10.0;
	
	/** Returns the name of the match voter */
	public String getName()
		{ return "Exact Documentation Matcher"; }
	
	/** Generates scores for the specified elements */
	public VoterScores match()
	{
		// Get the source and target elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		ArrayList<SchemaElement> targetElements = schema2.getFilteredElements();

		// Sets the completed and total comparisons
		completedComparisons = 0;
		totalComparisons = sourceElements.size() * targetElements.size();
		
		// Generate the scores
		VoterScores scores = new VoterScores(SCORE_CEILING);		
		for(SchemaElement sourceElement : sourceElements)
			for(SchemaElement targetElement : targetElements)
				if(isAllowableMatch(sourceElement, targetElement))
				{
					if(scores.getScore(sourceElement.getId(), targetElement.getId())==null)
					{
						VoterScore score = matchElements(sourceElement, targetElement);
						if(score != null) scores.setScore(sourceElement.getId(), targetElement.getId(), score);
					}
					completedComparisons++;
				}
		return scores;
	}

	/** Matches a single pair of elements */
	private static VoterScore matchElements(SchemaElement sourceElement, SchemaElement targetElement)
		{ return matchStrings(sourceElement.getDescription(), targetElement.getDescription()); }
	
	/** Returns a score for the matched strings */
	private static VoterScore matchStrings(String sourceString, String targetString)
	{
		// Return no score if the strings don't match
		if(!sourceString.equalsIgnoreCase(targetString)) return null;

		// Otherwise, return a score equivalent to the total length of the string
		Double totalLength = 2.0*sourceString.length();
		return new VoterScore(totalLength, totalLength);
	}
	
}