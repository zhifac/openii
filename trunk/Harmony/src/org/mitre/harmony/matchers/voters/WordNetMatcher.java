// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.FilteredGraph;

/**
 * WordNet Matcher 
 * Author: MDMORSE
 * Date:   Jan 22, 2009
 * A thesaurus matcher that uses the wordnet dictionary
 */
public class WordNetMatcher extends BagMatcher
{
	/** Returns the name of the match voter */
	public String getName()
		{ return "WordNet Thesaurus"; }
	
	/** Generates scores for the specified elements */
	public VoterScores match(FilteredGraph sourceSchema, FilteredGraph targetSchema)
	{		
		// Create word bags for the source and target elements
		ArrayList<SchemaElement> sourceElements = sourceSchema.getFilteredElements();
		ArrayList<SchemaElement> targetElements = targetSchema.getFilteredElements();

		// Get the thesaurus and acronym dictionaries
		URL thesaurusFile = getClass().getResource("wordnet.txt");
		HashMap<String, HashSet<Integer>> thesaurus = getThesaurus(thesaurusFile);

		// Generate the match scores
		VoterScores scores = new VoterScores(SCORE_CEILING);
		for(SchemaElement sourceElement : sourceElements)
			for(SchemaElement targetElement : targetElements)
				if(scores.getScore(sourceElement.getId(), targetElement.getId())==null)
				{
					VoterScore score = findVoterScore(sourceElement,targetElement,thesaurus);
					if(score != null) scores.setScore(sourceElement.getId(), targetElement.getId(), score);
				}
		return scores;
	}
	
	/** Get the specified dictionary from file */
	private static HashMap<String, HashSet<Integer>> getThesaurus(URL dictionaryFile)
	{
		HashMap<String,HashSet<Integer>> thesaurus = new HashMap<String,HashSet<Integer>>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(dictionaryFile.openStream()));
			String line;
			while((line = br.readLine()) != null)
			{
				StringTokenizer scan = new StringTokenizer(line);

				// Collect the word and senset
				String word = scan.nextToken();
				HashSet<Integer> synList = new HashSet<Integer>();
				while(scan.hasMoreTokens())
					synList.add(new Integer(scan.nextToken()));
				
				// Add entry to the dictionary
				thesaurus.put(word,synList);
	        } 
		}
		catch (java.io.IOException e) { System.out.println("(E) WordNetMatcher:getThesaurus - " + e.getMessage()); }
		return thesaurus;
	}
	
	/** Evaluate the score between the source and target element using the wordnet thesaurus */
	private VoterScore findVoterScore(SchemaElement source, SchemaElement target, HashMap<String,HashSet<Integer>> thesaurus)
	{
		int matches = 0;
		
		// Compile sets of words contained in both source and target elements
		WordBag sourceWordBag = new WordBag(source.getName(),source.getDescription());
		WordBag targetWordBag = new WordBag(target.getName(),target.getDescription());
		
		// Get text in both.
		ArrayList<String> sourceWords = sourceWordBag.getWords();
		ArrayList<String> targetWords = targetWordBag.getWords();

		// Swap word sets to make sure smaller word set is used in outer loop
		if(sourceWords.size()>targetWords.size())
		{
			ArrayList<String> tempWords = sourceWords;
			sourceWords = targetWords;
			targetWords = tempWords;
		}
		
		// Cycle through each source word
		for(String sourceWord : sourceWords)
		{
			// Retrieve the senset for the  source word
			HashSet<Integer> sourceSenset = thesaurus.get(sourceWord);
			if(sourceSenset==null) continue;
			
			// Cycle through each target word
			for(String targetWord : targetWords)
			{
				// Retrieve the senset for the target word
				HashSet<Integer> targetSenset = thesaurus.get(targetWord);
				if(targetSenset==null) continue;
				
				// Check for match
				boolean match = false;
				for(Integer sourceSen : sourceSenset)
					if(targetSenset.contains(sourceSen))
						{ match = true; break; }

				// If match found, mark as such and continue
				if(match) { matches++; continue; }
			}
		}
		
		// Return the voter score
		if(matches>0)
			return new VoterScore(1.0*matches, 1.0*sourceWords.size());
		return null;
	}
}
