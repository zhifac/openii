package org.mitre.harmony.matchers;

/** Class for storing a match score */
public class VoterScore
{
	/** Stores the positive evidence */
	private Double positiveEvidence;

	/** Stores the total evidence */
	private Double totalEvidence;
	
	/** Constructs the voter score */
	public VoterScore(Double score)
		{ this.positiveEvidence = score; this.totalEvidence = 1.0; }	
	
	/** Constructs the voter score */
	public VoterScore(Double positiveEvidence, Double totalEvidence)
		{ this.positiveEvidence = positiveEvidence; this.totalEvidence = totalEvidence; }
	
	/** Returns the positive evidence */
	public Double getPositiveEvidence()
		{ return positiveEvidence; }

	/** Returns the total evidence */
	public Double getTotalEvidence()
		{ return totalEvidence; }
}