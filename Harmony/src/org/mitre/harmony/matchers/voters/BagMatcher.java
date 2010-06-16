// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;

/** Bag Matcher Class */
abstract public class BagMatcher extends MatchVoter
{
	/** Constant defining the score ceiling */
	public final static double SCORE_CEILING=10;

	/** Generates the word weights */
	protected HashMap<String,Double> getWordWeights(List<SchemaElement> sourceElements, List<SchemaElement> targetElements, HashMap<Integer,WordBag> wordBags)
	{
		HashMap<String, Integer> corpus = new HashMap<String, Integer>();
		
		// Cycle through all source and target elements
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
		elements.addAll(sourceElements); elements.addAll(targetElements);
		for(SchemaElement element : elements)
		{
			// Store all distinct words in the element to the corpus
			WordBag wordBag = wordBags.get(element.getId());
			for(String word : wordBag.getDistinctWords())
			{
				// Increment the word count in the corpus
				Integer count = corpus.get(word);
				if(count==null) count=0;
				corpus.put(word, count+1);
			}
		}
		
		// Calculate out the word weights
		HashMap<String, Double> wordWeights = new HashMap<String, Double>();
		for(String word : corpus.keySet())
			wordWeights.put(word, Math.min(1.0, 2.0/corpus.get(word)));
		return wordWeights;
	}

	/** Compute the voter score */
	protected static VoterScore computeScore(WordBag sourceBag, WordBag targetBag, HashMap<String,Double> wordWeights)
	{
		// Calculate the source score
		Double sourceScore = 0.0;
		for(String word : sourceBag.getDistinctWords())
			sourceScore += sourceBag.getWordCount(word) * wordWeights.get(word);

		// Calculate the target score
		Double targetScore = 0.0;
		for(String word : targetBag.getDistinctWords())
			targetScore += targetBag.getWordCount(word) * wordWeights.get(word);
		
		// Calculate the intersection score
		Double intersectionScore = 0.0;
		for(String word : sourceBag.getDistinctWords())
		{
			Integer count = Math.min(sourceBag.getWordCount(word), targetBag.getWordCount(word));
			intersectionScore += count * wordWeights.get(word);
		}

		// Return the voter score
		return new VoterScore(intersectionScore, Math.min(sourceScore, targetScore));
	}
	
	/** Compute the voter scores */
	protected VoterScores computeScores(List<SchemaElement> sourceElements, List<SchemaElement> targetElements, HashMap<Integer,WordBag> wordBags)
	{
		// Sets the completed and total comparisons
		completedComparisons = 0;
		totalComparisons = sourceElements.size() * targetElements.size();
		
		// Get the word weights for all words existent in the source and target schemas
		HashMap<String,Double> wordWeights = getWordWeights(sourceElements, targetElements, wordBags);
		
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
						VoterScore score = computeScore(sourceBag, targetBag, wordWeights);
						scores.setScore(sourceElement.getId(), targetElement.getId(), score);
					}
				completedComparisons++;
			}
		return scores;
	}
}