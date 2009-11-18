// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;

/** Documentation Matcher Class */
public class DocumentationMatcher extends BagMatcher
{
	/** Returns the name of the match voter */
	public String getName() { return "Documentation Similarity"; }
	
	/** Indicates a default voter */
	public boolean isDefault() { return true; }
	
	/** Generates scores for the specified elements */
	public VoterScores match()
	{
		HashMap<Integer,WordBag> wordBags = new HashMap<Integer,WordBag>();

		//Create word bags for the source elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		for(SchemaElement sourceElement : sourceElements)
			wordBags.put(sourceElement.getId(), new WordBag(sourceElement));
		
		// Create word bags for the target elements
		ArrayList<SchemaElement> targetElements = schema2.getFilteredElements();
		for (SchemaElement targetElement : targetElements)
			wordBags.put(targetElement.getId(), new WordBag(targetElement));

		// Sets the completed and total comparisons
		completedComparisons = 0;
		totalComparisons = sourceElements.size() * targetElements.size();
		
		// Generate the scores
		VoterScores scores = new VoterScores(SCORE_CEILING);
		for(SchemaElement sourceElement : sourceElements)
			for(SchemaElement targetElement : targetElements)
			{
				if(isAllowableMatch(sourceElement, targetElement))
					if(scores.getScore(sourceElement.getId(), targetElement.getId())==null)
					{
						WordBag sourceBag = wordBags.get(sourceElement.getId());
						WordBag targetBag = wordBags.get(targetElement.getId());
						VoterScore score = computeScore(sourceBag, targetBag);
						if(score != null)
							scores.setScore(sourceElement.getId(), targetElement.getId(), score);
					}
				completedComparisons++;
			}
		return scores;
	}
}