package org.mitre.harmony.matchers.metrics;

public class PerformanceMetrics 
{
	/**
	 * An instance of this class stores all parameters needed to calculate precision and recall
	 * REQUIRES:	true
	 * EFFECTS:		stores and gets parameters such as number of schema element pairs matched by Algorithm, 
	 * 				by Ground Truths file and number of correct results (i.e. (declared to be matched by Algorithm) 
	 * 				AND (declared to be Ground Truths)).  Also, calculates values of precision and recall  
	 * @author STANDON
	 */
	
	//stores the number of schema element pairs that Algorithm declares to be matched (i.e. score > threshold)
	private static int AlgorithmMatched = 0;
	
	//stores the number of schema element pairs that Ground Truth declares to be matched (i.e. score == 1 or declared "True")
	private static int GroundTruths = 0;
	
	//stores number of correct matched results for a particular threshold
	// 		= (declared to be matched by Algorithm) AND (declared to be Ground Truths)
	private static int CorrectResults = 0;
	
	public PerformanceMetrics()
	{
		AlgorithmMatched = 0;
		GroundTruths = 0;
		CorrectResults = 0;
	}
	
	//all increment methods
	public void incrementGroundTruths()	{	GroundTruths++;	}
	public void incrementAlgorithmMatched()	{	AlgorithmMatched++;	}
	public void incrementCorrectResults()	{	CorrectResults++;	}
	
	//all get methods
	public int getAlgorithmMatched()	{	return AlgorithmMatched;	}
	public int getGroundTruths()	{	return GroundTruths;	}
	public int getCorrectResults()	{	return CorrectResults;	}
	public Double getPrecision()	
	{
		Double p = null;
		if(AlgorithmMatched == 0)
			p = new Double(1.0);
		else
			p = new Double(((double)CorrectResults)/AlgorithmMatched);
		return p;
	}
	public Double getRecall()	
	{	
		if(GroundTruths == 0)
			return new Double(1.0);
		return new Double(((double)CorrectResults/GroundTruths));	
	}
	
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		s.append("Number of algorithm matches = " + AlgorithmMatched + "\n");
		s.append("Number of ground truths = " + GroundTruths + "\n");
		s.append("Number of correct results = " + CorrectResults + "\n");
		s.append("Precision = " + getPrecision().toString() + "\n");
		s.append("Recall = " + getRecall().toString() + "\n");
		return s.toString();
	}
}
