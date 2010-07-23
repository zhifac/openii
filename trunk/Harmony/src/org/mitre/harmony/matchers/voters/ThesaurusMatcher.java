// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.mitre.harmony.matchers.MatcherScores;
import org.mitre.schemastore.model.SchemaElement;

/** Thesaurus Matcher Class */
public class ThesaurusMatcher extends BagMatcher
{
	/** Stores the word bag used for this matcher */
	private HashMap<Integer, WordBag> wordBags = new HashMap<Integer, WordBag>();
	
	/** Returns the name of the match voter */
	public String getName()
		{ return "Documentation + Synonyms"; }

	/** Generates match scores for the specified elements */
	public MatcherScores match()
	{
		// Determine if the name and description should be used
		boolean useName = options.get("UseName").isSelected();
		boolean useDescription = options.get("UseDescription").isSelected();

		// Create word bags for the source elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		for(SchemaElement sourceElement : sourceElements)
			wordBags.put(sourceElement.getId(), new WordBag(sourceElement, useName, useDescription));
		
		// Create word bags for the target elements
		ArrayList<SchemaElement> targetElements = schema2.getFilteredElements();
		for(SchemaElement targetElement : targetElements)
			wordBags.put(targetElement.getId(), new WordBag(targetElement, useName, useDescription));

		// Get the thesaurus and acronym dictionaries
		URL thesaurusFile = getClass().getResource("dictionary.txt");
		HashMap<String, ArrayList<String>> thesaurus = getDictionary(thesaurusFile);
		URL acronymFile = getClass().getResource("acronyms.txt");
		HashMap<String, ArrayList<String>> acronyms = getDictionary(acronymFile);

		// Add synonyms to the word bags
		addSynonyms(sourceElements, acronyms, true); 
		addSynonyms(targetElements, acronyms, true); 
		addSynonyms(sourceElements, thesaurus, false);
		
		// Generate the match scores
		return computeScores(sourceElements, targetElements, wordBags);
	}
	
	/** Get the specified dictionary from file */
	private HashMap<String, ArrayList<String>> getDictionary(URL dictionaryFile)
	{
		HashMap<String, ArrayList<String>> dictionary = new HashMap<String, ArrayList<String>>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(dictionaryFile.openStream()));
			String line;
			while((line = br.readLine()) != null)
			{
				String word = line.replaceAll(":.*","");
				String synonyms[] = line.replaceAll("^[^:]+:","").split(": ");
				dictionary.put(word, new ArrayList<String>(Arrays.asList(synonyms)));
	        } 
		}
		catch (java.io.IOException e) { System.out.println("(E) ThesaurusMatcher.getDictionary - " + e); }
		return dictionary;
	}

	/** Adds synonyms to the list of elements */
	private void addSynonyms(ArrayList<SchemaElement> elements, HashMap<String, ArrayList<String>> dictionary, boolean isAbbreviation)
	{	
		// Cycle through all elements
		for(SchemaElement element : elements)
		{
			WordBag wordBag = wordBags.get(element.getId());
			for(String word : wordBag.getWords())
				if(dictionary.containsKey(word))
				{
					wordBag.addWords(dictionary.get(word));
					if(isAbbreviation) wordBag.removeWord(word);
				}
		}
	}
}