// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;

/** Documentation Matcher Class */
public class DocumentationMatcher extends BagMatcher
{
	/** Returns the name of the match voter */
	public String getName() { return "Documentation Similarity"; }
	
	/** Generates the word bags for the schema elements */
	protected VoterScores generateVoteScores(boolean useLabels)
	{
		HashMap<Integer,WordBag> wordBags = new HashMap<Integer,WordBag>();

		// Create word bags for the source elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		for(SchemaElement sourceElement : sourceElements)
			wordBags.put(sourceElement.getId(), new WordBag(sourceElement,useLabels));
		
		// Create word bags for the target elements
		ArrayList<SchemaElement> targetElements = schema1.getFilteredElements();
		for (SchemaElement targetElement : targetElements)
			wordBags.put(targetElement.getId(), new WordBag(targetElement,useLabels));

		// Generate the voter scores
		return computeScores(sourceElements, targetElements, wordBags);
	}
	
	/** Generates scores for the specified elements */
	public VoterScores match()
		{ return generateVoteScores(true); }
}