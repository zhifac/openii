package org.mitre.harmony.matchers.affinity;

import java.util.ArrayList;

import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.FilteredGraph;
import org.mitre.harmony.matchers.AffinityScore;
import org.mitre.harmony.matchers.voters.BagMatcher;
import org.mitre.harmony.matchers.voters.WordBag;

/**
 * Compute Affinity Score for Edit Distance Matcher 
 * @author MDMORSE
 */
public class WordNetAffiner implements AffinityInterface{
	
	public WordNetAffiner(){
	}
	
	/**
	 * This method takes a graph and produces 1 affinity score - 
	 * combining the affinities of each element contained in the input
	 * graph for the edit distance matcher and averaging these together.
	 * @param schema1
	 * @return - the affinity score
	 */
	public AffinityScore getAffinity(FilteredGraph schema1){
		
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		double aveVal = 0.0;
		for(SchemaElement sElement: sourceElements){
			aveVal += Math.min(new Double(new WordBag(sElement.getName(),sElement.getDescription()).getBagWeight()),BagMatcher.SCORE_CEILING)/BagMatcher.SCORE_CEILING;
		}
		aveVal /= new Double(sourceElements.size());
		AffinityScore aS = new AffinityScore(aveVal);
		return aS;
	}
}