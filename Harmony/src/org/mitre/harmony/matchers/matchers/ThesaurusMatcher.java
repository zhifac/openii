// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.matchers;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.MatcherScores;
import org.mitre.harmony.matchers.matchers.bagMatcher.BagMatcher;
import org.mitre.harmony.matchers.matchers.bagMatcher.WordBag;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Thesaurus;
import org.mitre.schemastore.model.terms.InvertedTermsByKeyword;
import org.mitre.schemastore.model.terms.ThesaurusTerms;

/** Thesaurus Matcher Class */
public class ThesaurusMatcher extends BagMatcher
{
	/** Stores the word bag used for this matcher */
	private HashMap<Integer, WordBag> wordBags = new HashMap<Integer, WordBag>();
	
	/** Returns the name of the matcher */
	public String getName()
		{ return "Thesaurus Matcher"; }

	/** Indicates that the matcher needs a repository client */
	public boolean needsClient() { return true; }
	
	/** Generates match scores for the specified elements */ @Override
	public MatcherScores generateScores()
	{
		// Only proceed if the client is set (otherwise, the mappings can't be accessed)
		if(MatcherManager.getClient()==null) return new MatcherScores(SCORE_CEILING);
		
		// Create word bags for the source elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		for(SchemaElement sourceElement : sourceElements)
			wordBags.put(sourceElement.getId(), generateWordBag(sourceElement));
		
		// Create word bags for the target elements
		ArrayList<SchemaElement> targetElements = schema2.getFilteredElements();
		for(SchemaElement targetElement : targetElements)
			wordBags.put(targetElement.getId(), generateWordBag(targetElement));
		
		// Use thesauri to generate scores 
		try
		{
			ArrayList<Thesaurus> thesauri = MatcherManager.getClient().getThesauri();
			if(thesauri.size() > 0)
			{
				// Assemble a inverted list of terms
				InvertedTermsByKeyword invertedTerms = new InvertedTermsByKeyword();
				for(Thesaurus thesaurus : thesauri)
				{
					ThesaurusTerms terms = MatcherManager.getClient().getThesaurusTerms(thesaurus.getId());
					invertedTerms.addTerms(terms);
				}
				
				// Add synonyms to the word bag
				addSynonyms(invertedTerms);
				
				// Generate the match scores
				return computeScores(sourceElements, targetElements, wordBags);
			}
		}
		catch(Exception e) {}

		// Return no scores if thesauri don't exist
		return new MatcherScores(SCORE_CEILING);
	}

	/** Adds synonyms to the list of elements */
	private void addSynonyms(InvertedTermsByKeyword invertedTerms)
	{	
		// Cycle through all words in the word bags
		for(WordBag wordBag : wordBags.values())
			for(String word : new ArrayList<String>(wordBag.getWords()))
				for(String phrase : invertedTerms.getPhrases(word))
				{
					// Check to see if phrase exists in bag of words
					boolean exists = true;
					for(String phraseWord : phrase.split(" "))
						if(wordBag.getWordCount(phraseWord)==0) exists=false;
					if(!exists) continue;
					
					// Add the synonyms to the word bag
					ArrayList<String> synonyms = new ArrayList<String>(invertedTerms.getSynonyms(phrase));
					if(synonyms.size()>0) wordBag.addWords(synonyms);					
				}
	}
}