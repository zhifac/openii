package org.mitre.harmony.matchers;

/** Class for storing a match score */
public class MatchScore
{
	/** Stores element1 */
	private Integer element1;
	
	/** Stores element2 */
	private Integer element2;
	
	/** Stores the score */
	private Double score;
	
	/** Constructs the match score */
	public MatchScore(Integer element1, Integer element2, Double score)
		{ this.element1 = element1; this.element2 = element2; this.score = score;	}
	
	/** Returns element1 */
	public Integer getElement1()
		{ return element1; }
	
	/** Returns element2 */
	public Integer getElement2()
		{ return element2; }
	
	/** Returns the match score */
	public Double getScore()
		{ return score; }
}
