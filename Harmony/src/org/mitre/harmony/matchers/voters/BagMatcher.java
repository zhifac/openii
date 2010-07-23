// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mitre.harmony.matchers.MatcherOption;
import org.mitre.harmony.matchers.MatcherScore;
import org.mitre.harmony.matchers.MatcherScores;
import org.mitre.harmony.matchers.MatcherOption.OptionType;
import org.mitre.schemastore.model.SchemaElement;

/** Bag Matcher Class */
abstract public class BagMatcher extends MatchVoter
{
	/** Constant defining the score ceiling */
	public final static double SCORE_CEILING = 10;

	/** Returns the list of options associated with the bag matcher */
	public ArrayList<MatcherOption> getMatcherOptions()
	{
		ArrayList<MatcherOption> options = new ArrayList<MatcherOption>();
		options.add(new MatcherOption(OptionType.CHECKBOX,"UseName","true"));
		options.add(new MatcherOption(OptionType.CHECKBOX,"UseDescription","true"));
		return options;
	}
	
	/** Generates the word weights */
	protected HashMap<String,Double> getWordWeights(List<SchemaElement> sourceElements, List<SchemaElement> targetElements, HashMap<Integer,WordBag> wordBags)
	{
		HashMap<String, Integer> corpus = new HashMap<String, Integer>();
		Integer totalWordCount = 0;
		
		// Cycle through all source and target elements
		ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
		elements.addAll(sourceElements); elements.addAll(targetElements);
		for(SchemaElement element : elements)
		{
			// Extract the word bag for the element
			WordBag wordBag = wordBags.get(element.getId());
			totalWordCount += wordBag.getWords().size();
			
			// Store all distinct words in the element to the corpus
			for(String word : wordBag.getDistinctWords())
			{
				// Increment the word count in the corpus
				Integer count = corpus.get(word);
				if(count == null) { count = 0; }
				corpus.put(word, count+1);
			}
		}

		// Calculate the document weight and inflation constant
		double documentWeight = Math.log(elements.size());
		double inflationConstant = 10.0 * elements.size() / totalWordCount;
		
		// Calculate out the word weights
		HashMap<String, Double> wordWeights = new HashMap<String, Double>();
		for(String word : corpus.keySet())
		{
			double wordWeight = Math.log(corpus.get(word));
			wordWeights.put(word, inflationConstant*(documentWeight-wordWeight)/documentWeight);
		}
		return wordWeights;
	}

	/** Compute the voter score */
	protected static MatcherScore computeScore(WordBag sourceBag, WordBag targetBag, HashMap<String,Double> wordWeights)
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
		if(intersectionScore == 0) { return null; }
		return new MatcherScore(intersectionScore, Math.min(sourceScore, targetScore));
	}

	/** Compute the voter scores */
	protected MatcherScores computeScores(List<SchemaElement> sourceElements, List<SchemaElement> targetElements, HashMap<Integer,WordBag> wordBags)
	{
		// Sets the completed and total comparisons
		completedComparisons = 0;
		totalComparisons = sourceElements.size() * targetElements.size();
		
		// Get the word weights for all words existent in the source and target schemas
		HashMap<String,Double> wordWeights = getWordWeights(sourceElements, targetElements, wordBags);
		
		// Generate the scores
		MatcherScores scores = new MatcherScores(SCORE_CEILING);
		for(SchemaElement sourceElement : sourceElements)
			for(SchemaElement targetElement : targetElements)
			{
				if(isAllowableMatch(sourceElement, targetElement))
					if(scores.getScore(sourceElement.getId(), targetElement.getId()) == null)
					{
						WordBag sourceBag = wordBags.get(sourceElement.getId());
						WordBag targetBag = wordBags.get(targetElement.getId());
						MatcherScore score = computeScore(sourceBag, targetBag, wordWeights);
						if(score != null) { scores.setScore(sourceElement.getId(), targetElement.getId(), score); }
					}
				completedComparisons++;
			}
		return scores;
	}
}