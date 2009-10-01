// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;

/** Bag Matcher Class */
public class BagMatcher extends MatchVoter
{
	/** Constant defining the score ceiling */
	public final static double SCORE_CEILING=10;

	/** Returns the name of the match voter */
	public String getName()
		{ return "Documentation Similarity"; }
	
	/** Generates scores for the specified elements */
	public VoterScores match()
	{
		HashMap<Integer,WordBag> wordBags = new HashMap<Integer,WordBag>();

		//Create word bags for the source elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		for(SchemaElement sourceElement : sourceElements)
			wordBags.put(sourceElement.getId(), new WordBag(sourceElement.getName(), sourceElement.getDescription()));
		
		// Create word bags for the target elements
		ArrayList<SchemaElement> targetElements = schema2.getFilteredElements();
		for (SchemaElement targetElement : targetElements)
			wordBags.put(targetElement.getId(), new WordBag(targetElement.getName(), targetElement.getDescription()));

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

	/** Compute the voter score */
	protected static VoterScore computeScore(WordBag sourceBag, WordBag targetBag)
	{
		double directPositiveEvidence = computeIntersectionScore(sourceBag, targetBag);
		double directTotalEvidence = Math.min(sourceBag.getBagWeight(), targetBag.getBagWeight());
		if(directPositiveEvidence ==0) return null;
		return new VoterScore(directPositiveEvidence, directTotalEvidence);
	}

	/** Compute the intersection score */
	private static double computeIntersectionScore(WordBag sourceBag, WordBag targetBag)
	{
		// Calculate score based on intersection of 
		double score = 0;
		for(String word : sourceBag.getDistinctWords())
		{
			Double sourceWordCount = new Double(sourceBag.getWordCount(word));
			Double targetWordCount = new Double(targetBag.getWordCount(word)==null?0:targetBag.getWordCount(word));
			if(targetWordCount!=null)
			{
				// Calculate the minimum word count
				double minWordCount = sourceWordCount;
				if(targetWordCount<minWordCount)
					minWordCount = targetWordCount;
			
				// Calculate the score
				score += minWordCount*2;
			}
		}
		return score;
	}
}