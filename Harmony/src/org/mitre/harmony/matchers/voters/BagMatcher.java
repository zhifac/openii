// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import org.mitre.harmony.matchers.VoterScore;

/** Bag Matcher Class */
abstract public class BagMatcher extends MatchVoter
{
	/** Constant defining the score ceiling */
	public final static double SCORE_CEILING=10;

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