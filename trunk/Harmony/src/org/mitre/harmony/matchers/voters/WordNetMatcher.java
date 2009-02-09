// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Iterator;

import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.FilteredGraph;

/** WordNet Matcher 
 *  Author: MDMORSE
 *  Date:   Jan 22, 2009
 *  A thesaurus matcher that uses the wordnet dictionary
 */
public class WordNetMatcher extends BagMatcher
{
	/** Stores the word bag used for this matcher */
	private HashMap<Integer, WordBag> wordBags = new HashMap<Integer, WordBag>();
	
	/** Returns the name of the match voter */
	public String getName()
		{ return "WordNet Thesaurus"; }
	
	/** Generates scores for the specified elements */
	public VoterScores match(FilteredGraph schema1, FilteredGraph schema2)
	{		
		// Create word bags for the source elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		for(SchemaElement sourceElement : sourceElements)
			wordBags.put(sourceElement.getId(), new WordBag(sourceElement.getName(), sourceElement.getDescription()));
		
		// Create word bags for the target elements
		ArrayList<SchemaElement> targetElements = schema2.getFilteredElements();
		for(SchemaElement targetElement : targetElements)
			wordBags.put(targetElement.getId(), new WordBag(targetElement.getName(), targetElement.getDescription()));

		// Get the thesaurus and acronym dictionaries
		URL thesaurusFile = getClass().getResource("wordnet.txt");
		HashMap<String, ArrayList<Integer>> thesaurus = getDictionary(thesaurusFile);

		// Generate the match scores
		VoterScores scores = new VoterScores(SCORE_CEILING);
		for(SchemaElement sourceElement : sourceElements)
			for(SchemaElement targetElement : targetElements)
				if(scores.getScore(sourceElement.getId(), targetElement.getId())==null)
				{
					VoterScore score = findVoterScore(sourceElement,targetElement,thesaurus);
					scores.setScore(sourceElement.getId(), targetElement.getId(), score);
				}
		return scores;
	}
	
	/** Get the specified dictionary from file */
	private HashMap<String, ArrayList<Integer>> getDictionary(URL dictionaryFile)
	{
		HashMap<String,ArrayList<Integer>> regIndex = new HashMap<String,ArrayList<Integer>>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(dictionaryFile.openStream()));
			String line;
			while((line = br.readLine()) != null)
			{
				StringTokenizer scan = new StringTokenizer(line);

				//the word
				String word = scan.nextToken();
				WordBag wb = new WordBag();
				wb.addWords(word);
				ArrayList<Integer> numList = new ArrayList<Integer>();
				while(scan.hasMoreTokens()){
				  int val = new Integer(scan.nextToken());

				  numList.add(val);
				}
				for(String text: wb.getWords()){
					if(regIndex.containsKey(text)){
						regIndex.get(text).addAll(numList);
					}else{
						regIndex.put(text,numList);
					}
				}
				
	        } 
		}
		catch (java.io.IOException e) { System.out.println(e); e.printStackTrace(); }
		return regIndex;
	}
	
	/** Evaluate the score between the source and target element using the wordnet thesaurs/senset paradigm.
	 * @param source - source schema element
	 * @param target - target schema element
	 * @param thesaurus - map between words and lists of sensets
	 * @return VoterScore
	 */
	private VoterScore findVoterScore(SchemaElement source, SchemaElement target, HashMap<String,ArrayList<Integer>> thesaurus)
	{
		//compile sets of words contained in both source and target elements
		WordBag wbS = new WordBag(source.getName(),source.getDescription());
		WordBag wbT = new WordBag(target.getName(),target.getDescription());
		
		//Get text in both.
		ArrayList<String> textS = wbS.getWords();
		ArrayList<String> textT = wbT.getWords();
		
		//Now, create lists of the sensets (word senses) for each.
		ArrayList<Integer> sensetS = new ArrayList<Integer>();
		ArrayList<Integer> sensetT = new ArrayList<Integer>();
		
		//compile the senset lists
		for(String wordS : textS)
			if(thesaurus.containsKey(wordS))
				sensetS.addAll(thesaurus.get(wordS));
		for(String wordT : textT)
			if(thesaurus.containsKey(wordT))
				sensetT.addAll(thesaurus.get(wordT));
		
		//Now, do set intersection of each list.
		// sensetS \insertsect sensetT
		Collections.sort(sensetS);
		Collections.sort(sensetT);
		Iterator<Integer> iterS = sensetS.listIterator();
		Iterator<Integer> iterT = sensetT.listIterator();
		double numIntersects = 0.0;
		int senS = -1; //we want to initialize these inside loop, in case where no senset ids exist.
		int senT = -2;	//hence, this somewhat odd initialization
		while(true){
			if(senS == senT){
				numIntersects+=1.0;
				if(iterS.hasNext())
					senS=iterS.next();
				else break;
				if(iterT.hasNext())
					senT = iterT.next();
				else break;
			} else if(senS < senT){
				if(iterS.hasNext())
					senS = iterS.next();
				else break;
			} else{
				if(iterT.hasNext())
					senT = iterT.next();
				else break;
			}
		}
		
		double directTotalEvidence = new Double(Math.max(sensetS.size(),sensetT.size()));		
		return new VoterScore(numIntersects, directTotalEvidence);
	}
}
