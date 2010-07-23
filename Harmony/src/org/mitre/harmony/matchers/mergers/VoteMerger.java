// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.mergers;

import java.util.HashMap;

import org.mitre.harmony.matchers.ElementPair;
import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.MatcherScore;
import org.mitre.harmony.matchers.MatcherScores;

/**
 * The basic vote merger for merging together matchers
 * @author CWOLF
 */
public class VoteMerger extends MatchMerger
{
	/** Stores the current summation of matcher scores */
	private HashMap<ElementPair,MatcherScore> summedScores = new HashMap<ElementPair,MatcherScore>();
	
	/** Returns the name associated with this match merger */
	public String getName()
		{ return "Vote Merger"; }

	/** Initializes the vote merger */
	protected void initialize()
		{ summedScores.clear(); }
	
	/** Adds a new set of matcher scores to the vote merger */
	protected void addMatcherScoresToMerger(MatcherScores matcherScores)
	{
		// Retrieve the score ceiling
		Double scoreCeiling = matcherScores.getScoreCeiling();

		// Push all of the matcher scores into the summed scores
		for(ElementPair pair : matcherScores.getElementPairs())
		{
			// Get the matcher score for the specified pair of elements
			MatcherScore matcherScore = matcherScores.getScore(pair);
				
			// Calculate the positive evidence
			double positiveEvidence = Math.min(scoreCeiling, matcherScore.getPositiveEvidence());
			if(matcherScore.getPositiveEvidence() > scoreCeiling)
				positiveEvidence += Math.log(1+matcherScore.getPositiveEvidence()-scoreCeiling)/Math.log(2);

			// Calculate the total evidence
			double totalEvidence = Math.min(scoreCeiling, matcherScore.getTotalEvidence());
			if(matcherScore.getTotalEvidence() > scoreCeiling)
				totalEvidence += Math.log(1+matcherScore.getTotalEvidence()-scoreCeiling)/Math.log(2);
				
			// Generate and store the new summed score
			MatcherScore summedScore = summedScores.get(pair);
			if(summedScore==null) summedScore = new MatcherScore(0.0,0.0);
			Double newPositiveEvidence = summedScore.getPositiveEvidence() + positiveEvidence;
			Double newTotalvidence = summedScore.getTotalEvidence() + totalEvidence;
			MatcherScore newSummedScore = new MatcherScore(newPositiveEvidence,newTotalvidence);
			summedScores.put(pair,newSummedScore);
		}
	}
	
	/** Returns the match scores generated by the vote merger */
	public MatchScores getMatchScores()
	{
		MatchScores matchScores = new MatchScores();
		for(ElementPair pair : summedScores.keySet())
		{
			// Retrieve the specified score
			MatcherScore score = summedScores.get(pair);
			if(score.getTotalEvidence()<=0) continue;
			double positiveEvidence = score.getPositiveEvidence();
			double totalEvidence = score.getTotalEvidence();

			// Calculate the match score
			double evidenceRatio = positiveEvidence / totalEvidence;
			double weightedEvidenceRatio = Math.pow(evidenceRatio, 0.5) * (Math.E - 1) + 1;
			double scaledPositiveEvidence = positiveEvidence * 0.5;
			double evidenceFactor = Math.pow((1 + scaledPositiveEvidence), (1 / scaledPositiveEvidence));
			double matchScore = Math.log(weightedEvidenceRatio / evidenceFactor);
			
			// Stores the match score if positive
			if(matchScore>0) matchScores.setScore(pair.getSourceElement(), pair.getTargetElement(), matchScore);
		}
		return matchScores;
	}
}
