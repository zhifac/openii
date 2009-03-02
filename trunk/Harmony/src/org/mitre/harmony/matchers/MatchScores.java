package org.mitre.harmony.matchers;

import java.util.ArrayList;
import java.util.HashMap;

/** Class for storing match scores */
public class MatchScores
{	
	/** Hash map storing the match scores */
	private HashMap<ElementPair,Double> scores = new HashMap<ElementPair,Double>();
	
	/** Sets a match score */
	public void setScore(Integer element1, Integer element2, Double score)
		{ if(score!=null) scores.put(new ElementPair(element1,element2),score); }

	/** Gets a match score */
	public Double getScore(Integer element1ID, Integer element2ID)
		{ return scores.get(new ElementPair(element1ID,element2ID)); }
	
	/** Get match scores */
	public ArrayList<MatchScore> getScores()
	{
		ArrayList<MatchScore> matchScores = new ArrayList<MatchScore>();
		for(ElementPair pair : scores.keySet())
			matchScores.add(new MatchScore(pair.getElement1(),pair.getElement2(),scores.get(pair)));
		return matchScores;
	}
}
