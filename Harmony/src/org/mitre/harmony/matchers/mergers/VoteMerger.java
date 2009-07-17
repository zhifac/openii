// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.mergers;

import java.util.HashMap;

import org.mitre.harmony.matchers.ElementPair;
import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.graph.FilteredGraph;

/**
 * The basic vote merger for merging together match voters
 * @author CWOLF
 */
public class VoteMerger implements MatchMerger
{
	/** Constant used in calculating the merged score */
	static final double J = 2;
	
	/** Stores the current summation of voter scores */
	private HashMap<ElementPair,VoterScore> summedScores = new HashMap<ElementPair,VoterScore>();
	
	/** Returns the name associated with this match merger */
	public String getName()
		{ return "Vote Merger"; }

	/** Initializes the vote merger */
	public void initialize(FilteredGraph schema1, FilteredGraph schema2,  HashMap<String, Integer> typeMap)
		{ summedScores.clear(); }
	
	/** Adds a new set of voter scores to the vote merger */
	public void addVoterScores(VoterScores voterScores)
	{
		// Retrieve the score ceiling
		Double scoreCeiling = voterScores.getScoreCeiling();

		// Push all of the voter scores into the summed scores
		for(ElementPair pair : voterScores.getElementPairs())
		{
			// Get the voter score for the specified pair of elements
			VoterScore voterScore = voterScores.getScore(pair);
				
			// Calculate the positive evidence
			double positiveEvidence = Math.min(scoreCeiling, voterScore.getPositiveEvidence());
			if(voterScore.getPositiveEvidence() > scoreCeiling)
				positiveEvidence += Math.log(1+voterScore.getPositiveEvidence()-scoreCeiling)/Math.log(2);

			// Calculate the total evidence
			double totalEvidence = Math.min(scoreCeiling, voterScore.getTotalEvidence());
			if(voterScore.getTotalEvidence() > scoreCeiling)
				totalEvidence += Math.log(1+voterScore.getTotalEvidence()-scoreCeiling)/Math.log(2);
				
			// Generate and store the new summed score
			VoterScore summedScore = summedScores.get(pair);
			if(summedScore==null) summedScore = new VoterScore(0.0,0.0);
			Double newPositiveEvidence = summedScore.getPositiveEvidence() + positiveEvidence;
			Double newTotalvidence = summedScore.getTotalEvidence() + totalEvidence;
			VoterScore newSummedScore = new VoterScore(newPositiveEvidence,newTotalvidence);
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
			VoterScore score = summedScores.get(pair);
			double positiveEvidence = score.getPositiveEvidence() + 0.001;
			double totalEvidence = score.getTotalEvidence() + 0.001;

			// Calculate the match score
			double evidenceRatio = positiveEvidence / totalEvidence;
			double weightedEvidenceRatio = Math.pow(evidenceRatio, (1 / J)) * (Math.E - 1) + 1;
			double scaledPositiveEvidence = positiveEvidence / J;
			double evidenceFactor = Math.pow((1 + scaledPositiveEvidence), (1 / scaledPositiveEvidence));
			double matchScore = Math.log(weightedEvidenceRatio / evidenceFactor);
			
			// Stores the match score if positive
			if(matchScore>0) matchScores.setScore(pair.getElement1(), pair.getElement2(), matchScore);
		}
		return matchScores;
	}
}
