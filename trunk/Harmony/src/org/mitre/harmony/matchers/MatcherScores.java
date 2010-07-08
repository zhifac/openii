package org.mitre.harmony.matchers;

import java.util.HashMap;
import java.util.Set;

import org.mitre.schemastore.model.SchemaElement;

/** Class for storing voter scores */
public class MatcherScores
{	
	/** Stores the score ceiling being used for these voter scores */
	private Double scoreCeiling = null;
	
	/** Hash map for storing the voter scores */
	private HashMap<ElementPair,MatcherScore> scores = new HashMap<ElementPair,MatcherScore>();
	
	/** Constructs the voter scores object */
	public MatcherScores(Double scoreCeiling)
		{ this.scoreCeiling = scoreCeiling; }
	
	/** Sets the specified voter score */
	public void setScore(Integer element1ID, Integer element2ID, MatcherScore score)
		{ if(score!=null) scores.put(new ElementPair(element1ID,element2ID),score); }

	/** Sets the specified voter score */
	public void setScore(SchemaElement element1, SchemaElement element2, MatcherScore score)
		{ setScore(element1.getId(),element2.getId(),score); }
	
	/** Returns the score ceiling provided for these voter scores */
	public Double getScoreCeiling()
		{ return scoreCeiling; }

	/** Returns the list of element pairs containing scores */
	public Set<ElementPair> getElementPairs()
		{ return scores.keySet(); }
	
	/** Returns the requested voter score */
	public MatcherScore getScore(ElementPair elementPair)
		{ return scores.get(elementPair); }

	/** Returns the requested voter score */
	public MatcherScore getScore(Integer element1ID, Integer element2ID)
		{ return getScore(new ElementPair(element1ID,element2ID)); }

	/** Returns the requested voter score */
	public MatcherScore getScore(SchemaElement element1, SchemaElement element2)
		{ return getScore(element1.getId(), element2.getId()); }
}