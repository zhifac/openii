package org.mitre.harmony.matchers;

/** Class for storing a match score */
public class AffinityScore
{
	/** Stores the Affinity */
	private Double affineScore;

	/** Constructs the voter score */
	public AffinityScore(Double score)
		{ this.affineScore = score; }	
	
	/** Returns the affine score */
	public Double getAffineScore()
		{ return affineScore; }
}