/**
 * <p>
 * The WordBag class.
 * The WordBag is a bag of words created from a single node.  (Being a bag, 
 * it includes duplicates.)
 * </p>
 */

package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;

import org.mitre.schemastore.model.SchemaElement;

/** Class for storing a word bag */
public class WordBag
{	
	/** Stores a list of all stopwords */
	private static HashSet<String> stopwords = null;
	
	/** Initializes the list of stopwords */
	static
	{
		String[] stopwordArray =
			{"a", "i", "the", "of", "and", "that", "for", "by",
			 "as", "be", "or", "this", "then", "we",
			 "which", "with", "at", "from", "under",
			 "such", "there", "other", "if", "is",
			 "it", "can", "now", "an", "to", "but",
			 "upon", "where", "these", "when", "whether", "also",
			 "than", "after", "within", "before", "because",
			 "without", "however", "therefore", "between",
			 "those", "since", "into", "out", "some", "about",
			 "accordingly", "affecting", "affected", "again", "against",
			 "all", "almost", "already", "although", "always",
			 "among", "any", "anyone", "are", "away", "became",
			 "become", "becomes", "been", "being", "both", "briefly",
			 "came", "cannot", "could", "etc", "does", "done",
			 "during", "each", "either", "else", "ever", "every",
			 "following", "found", "further", "gave", "gets", "give",
			 "given", "giving", "gone", "got", "had", "hardly", "has",
			 "have", "having", "here", "how", "itself", "just", "keep",
			 "kept", "like", "made", "mainly", "make", "many", "might",
			 "more", "most", "mostly", "much", "must", "nearly", "necessarily",
			 "neither", "next", "none", "nor", "normally", "not",
			 "noted", "obtain", "obtained", "often", "only", "our",
			 "put", "owing", "particularly", "past", "perhaps", "please",
			 "possible", "possibly", "present", "probably", "prompt",
			 "promptly", "quickly", "quite", "rather", "readily",
			 "really", "their", "theirs", "them", "they",
			 "though", "through", "throughout", "said", "same",
			 "seem", "seen", "shall", "should", "so",
			 "sometime", "somewhat", "too", "toward", "unless",
			 "until", "use", "used", "usefully", "usefulness",
			 "using", "usually", "various", "very", "was",
			 "were", "what", "while", "who", "whose", "why",
			 "widely", "will", "would", "yet", "xsd","comment","string","",
			 "0","1","2","3","4","5","6","7","8","9","10","11", "12", "13", "14", "15", "100", 
			 "16", "17", "18", "19", "20","type"
			};
		stopwords = new HashSet<String>(Arrays.asList(stopwordArray));
	}

	/** Stores a list of all processed words */
	public Hashtable<String, Integer> wordMap = new Hashtable<String, Integer>();	
	
	/** Stores the bag weight */
	private Double bagWeight = null;
	
	/** Splits text into a list of words by camelCase and non-alphanumeric symbols */
	private ArrayList<String> tokenize(String text)
	{
		text = text.replaceAll("([a-z0-9])(A-Z)","$1 $2");
		for(int j=0; j < text.length()-1; j++){
			if(Character.isLowerCase(text.charAt(j)) && Character.isUpperCase(text.charAt(j+1))){
				text = text.substring(0, j+1)+" "+text.substring(j+1);
			}
		}
		return new ArrayList<String>(Arrays.asList(text.split("[^a-zA-Z0-9]+")));
	}
	
	/** Construct the word bag for the given schema element */
	public WordBag(SchemaElement element)
		{ addElement(element); }
	
	/** Adds schema elements to the word bag */
	public void addElement(SchemaElement element)
	{
		String text = element.getName()==null ? "" : element.getName() + " " + element.getName();
		text += element.getDescription()==null ? "" : element.getDescription();
		addWords(tokenize(text));
	}
	
	/** Add words to the word bag */
	public void addWords(ArrayList<String> words)
	{
		// Don't proceed if no words were provided
		if (words == null) return;
		
		// Cycle through all words
		for(String word : words)
		{
			// Make word lower case
			word = word.toLowerCase();
			
			// Don't proceed if stopword
			if(stopwords.contains(word)) continue;
			
			// Stem the word
			Stemmer s = new Stemmer();
			s.add(word.toCharArray(), word.length());
			s.stem();
			word = s.toString();

			// Add word to word map
			Integer count = wordMap.get(word);
			if(count==null) count=1;
			wordMap.put(word, count);
		}
		
		// Resets the bag weight
		bagWeight = null;
	}	

	/** Removes a word from the word bag */
	public void removeWord(String word)
		{ wordMap.remove(word); bagWeight=null; }
	
	/** Returns the list of unique words found in the word bag */
	public ArrayList<String> getDistinctWords()
		{ return new ArrayList<String>(wordMap.keySet()); }
	
	/** Returns the list of words found in the word bag */
	public ArrayList<String> getWords()
	{
		ArrayList<String> words = new ArrayList<String>();
		for(String word : wordMap.keySet())
		{
			Integer count = wordMap.get(word);
			for(int i=0; i<count; i++)
				words.add(word);
		}
		return words;
	}
	
	/** Returns a weight indicating the uniqueness of the specified word */
	public Integer getWordCount(String word)
		{ return wordMap.get(word); }
	
	/** Returns a weight which is the sum of all word weights */
	public Double getBagWeight()
	{ 
		if(bagWeight==null)
		{
			bagWeight = 0.0;
			for(String word : wordMap.keySet())
				bagWeight += 2 / getWordCount(word);
		}
		return bagWeight;
	}
}
