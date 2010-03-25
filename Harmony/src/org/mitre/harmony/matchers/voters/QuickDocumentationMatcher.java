// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.harmony.matchers.ElementPair;
import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;

/** Quick Documentation Matcher Class */
public class QuickDocumentationMatcher extends EntityMatcher
{
	/** Returns the name of the match voter */
	public String getName()
		{ return "Quick Documentation"; }
	
	/** Generates scores for the specified elements */
	public VoterScores match()
	{
		// Retrieve the source and target entities
		EntityMap sourceEntities = getEntities(schema1);
		EntityMap targetEntities = getEntities(schema2);		
	
		// Identify best matches between entities
		VoterScores entityScores = match(sourceEntities, targetEntities);
		HashSet<ElementPair> bestMatches = getBestMatches(entityScores);

		// Sets the completed and total comparisons
		completedComparisons = 0;
		totalComparisons = bestMatches.size();
		
		// Start generation of list of scores
		VoterScores scores = new VoterScores(SCORE_CEILING);
		
		// Generate element scores
		HashMap<Integer,WordBag> wordBags = new HashMap<Integer,WordBag>();
		for(ElementPair pair : bestMatches)
		{
			for(SchemaElement source : sourceEntities.get(schema1.getElement(pair.getSourceElement())))
				if(schema1.isVisible(source.getId()))
					for(SchemaElement target : targetEntities.get(schema2.getElement(pair.getTargetElement())))
						if(schema2.isVisible(target.getId()))
						{
							// Get word bags for source and target
							WordBag sourceBag = wordBags.get(source.getId());
							if(sourceBag==null) wordBags.put(source.getId(), sourceBag = new WordBag(source));
							WordBag targetBag = wordBags.get(target.getId());
							if(targetBag==null) wordBags.put(target.getId(), targetBag = new WordBag(target));
							
							// Set the score
							VoterScore score = computeScore(sourceBag,targetBag);
							if(score!=null) scores.setScore(source.getId(), target.getId(), score);
						}
			completedComparisons++;
		}
		
		// Transfer over entity scores
		for(ElementPair elementPair : entityScores.getElementPairs())
			if(schema1.isVisible(elementPair.getSourceElement()) && schema2.isVisible(elementPair.getTargetElement()))
				scores.setScore(elementPair.getSourceElement(), elementPair.getTargetElement(), entityScores.getScore(elementPair));
		
		// Return the generated scores
		return scores;
	}
	
	/** Returns a list of the best matches */
	private HashSet<ElementPair> getBestMatches(VoterScores scores)
	{
		// Scan through voter scores to identify best matches
		HashMap<Integer,Double> bestScores = new HashMap<Integer,Double>();
		HashMap<Integer,ArrayList<ElementPair>> bestPairs = new HashMap<Integer,ArrayList<ElementPair>>();
		for(ElementPair elementPair : scores.getElementPairs())
		{
			// Calculate rough match score (not as accurate as through merger)
			VoterScore voterScore = scores.getScore(elementPair);
			Double score = voterScore.getPositiveEvidence()/voterScore.getTotalEvidence();
			
			// Determine if the element pair is best match
			for(Integer elementID : new Integer[]{elementPair.getSourceElement(),elementPair.getTargetElement()})
			{
				Double elementScore = bestScores.get(elementID);
				if(elementScore==null || score>=elementScore)
				{
					// Updates the best score
					bestScores.put(elementID, score);
					
					// Updates the best element pairs
					ArrayList<ElementPair> elementPairs = bestPairs.get(elementID);
					if(!score.equals(elementScore)) bestPairs.put(elementID, elementPairs = new ArrayList<ElementPair>());
					elementPairs.add(elementPair);
				}
			}
		}
		
		// Return the best matches
		HashSet<ElementPair> bestMatches = new HashSet<ElementPair>();
		for(ArrayList<ElementPair> elementPairs : bestPairs.values())
			bestMatches.addAll(elementPairs);
		return bestMatches;
	}
}