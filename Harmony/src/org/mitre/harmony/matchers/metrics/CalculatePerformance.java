package org.mitre.harmony.matchers.metrics;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mitre.harmony.matchers.MatchScore;
import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.model.SchemaManager;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Containment;

public class CalculatePerformance 
{
	/**
	 * Calculates metrics for one schema pair matched in Ground Truths file and for one threshold
	 * 
	 * REQUIRES:	MatchScores object containing all match scores obtained by the algorithms
	 * 				String object containing all the ground truth mappings of a given pair of schemas
	 * 				ArrayList object containing two schema instances being matched
	 * 				Double object specifying one threshold value
	 * EFFECTS:		Calculates precision and recall for this schema pair and for the given threshold
	 * @author STANDON
	 */
	
	//variables used to obtain schema element pairs
	static private Pattern linkPattern = Pattern.compile("<link>(.*?)</link>");
	static private Pattern sourceNodePattern = Pattern.compile("<sourceNode>(.*?)</sourceNode>");
	static private Pattern targetNodePattern = Pattern.compile("<targetNode>(.*?)</targetNode>");
	//static private Pattern confidencePattern = Pattern.compile("<confidence>(.*?)</confidence>");
	//static private Pattern assertedByPattern = Pattern.compile("<assertedBy>(.*?)</assertedBy>");
	
	/*	Based on one Threshold value, calculates precision and recall values 
		for each pair of schemas matched in Ground Truths file
	*/
	public static ArrayList<Double> metricsCalculator(MatchScores schemaPairAlgorithmMatchScores, 
			String mappingString, ArrayList<Schema> currentMatchedSchemas, Double threshold)
				throws IllegalFileFormatException
	{
		/*	stores all parameters needed for calculating precision and recall
			for this pair of schemas and for the given threshold 
		*/
		PerformanceMetrics metrics = new PerformanceMetrics();
			
		/*	Based on the threshold value, find which score is considered to be an algorithm match.
			store the number of such matches in the PerformanceMetrics object
		*/
		for(MatchScore m : schemaPairAlgorithmMatchScores.getScores())
		{
			Double algScore = m.getScore();
			if(algScore == null)
				algScore = new Double(-1.0);
			if(algScore.compareTo(threshold) >= 0)
				metrics.incrementAlgorithmMatched();
		}
			
		/*	Get each pair of elements mentioned in Ground Truths file for this pair of schemas.
			Store the number of ground truth matches in the PerformanceMetrics object.
			Based on the threshold value, store the number of correct results in the PerformanceMetrics object.
		*/
		Matcher linkMatcher = linkPattern.matcher(mappingString);
		if(!linkMatcher.find())
			throw new IllegalFileFormatException("Your Ground Truths file is not in the required format - <link> tag not found");

		linkMatcher.reset();
		while(linkMatcher.find())
		{
			SchemaElement elementOne = null;
			SchemaElement elementTwo = null;
			String linkString = linkMatcher.group(1);
			
			// Get the sourceNode
			String sourceNodeString = "";
			Matcher sourceNodeMatcher = sourceNodePattern.matcher(linkString);
			if(sourceNodeMatcher.find()) sourceNodeString = sourceNodeMatcher.group(1);
				
			// Get the targetNode
			String targetNodeString = "";
			Matcher targetNodeMatcher = targetNodePattern.matcher(linkString);
			if(targetNodeMatcher.find()) targetNodeString = targetNodeMatcher.group(1);
				
			// Find if the souceNode element exists in source schema and get its reference
			// Find if the targetNode element exists in target schema and get its reference
			try
			{
				elementOne = getElementInstance(currentMatchedSchemas.get(0), sourceNodeString);
				elementTwo = getElementInstance(currentMatchedSchemas.get(1), targetNodeString);
			}
			catch(ElementDoesNotExistException e)
			{System.out.println(e.toString());}
			
			/*
			// Get the ground truth score
			String scoreString = null;
			Matcher confidenceMatcher = confidencePattern.matcher(linkString);
			if(confidenceMatcher.find()) scoreString = confidenceMatcher.group(1);
				
			// Get the scorer
			String scorerString = null;
			Matcher assertedByMatcher = assertedByPattern.matcher(linkString);
			if(assertedByMatcher.find()) scorerString = assertedByMatcher.group(1);
			*/
						
			//increment the number of ground truths by one
			metrics.incrementGroundTruths();
				
			/*	Find the algorithm match score for these two elements.
				Based on the threshold value, find if this was an algorithm match or not, 
				and hence, if this is a correct result or not
			*/
			Double algorithmScore = null;
			algorithmScore = schemaPairAlgorithmMatchScores.getScore(elementOne.getId(), elementTwo.getId());
			if(algorithmScore == null)
				algorithmScore = new Double(-1.0);
			if(algorithmScore.compareTo(threshold) >= 0)
				metrics.incrementCorrectResults();
		}//end of linkMatcher while loop
			
		/*	Calculate precision and recall for this pair of schemas for the given threshold	*/
		ArrayList<Double> oneSchemaPairThresholdMetricsArray = new ArrayList<Double>();
		Double p = metrics.getPrecision();
		oneSchemaPairThresholdMetricsArray.add(p);
		Double r = metrics.getRecall();
		oneSchemaPairThresholdMetricsArray.add(r);
			
		return oneSchemaPairThresholdMetricsArray;
	}//end of metricsCalculator method
	
	/*	Obtains element instance - given an element name	*/    
    private static SchemaElement getElementInstance(Schema s, String nodeName) throws ElementDoesNotExistException, IllegalArgumentException
    {
    	SchemaElement elementFound = null;
       	String names[] = nodeName.split("-");
       	ArrayList<Containment> allContainments = new ArrayList<Containment>();
       	ArrayList<Containment> parentContainments = new ArrayList<Containment>();
       	ArrayList<Containment> childContainments = new ArrayList<Containment>();
       	
       	if(names.length == 0)
       		throw new IllegalArgumentException("Node name not found in schema " + s.toString());
       	
       	//Find all containments in this schema
		for(SchemaElement c : SchemaManager.getSchemaElements(s.getId(),Containment.class))
			allContainments.add((Containment)c);
		
		//Find all containments with Name = first part of nodeName
		for(Containment c : allContainments)
			if((c.getName()).equalsIgnoreCase(names[0]))
				parentContainments.add(c);
		
		//If nodeName has only one part, return the first containment found
		if(names.length == 1)
			elementFound = parentContainments.get(0);

		//If nodeName has two parts, find the Containment with Name = second part of nodeName
		if(names.length == 2)
		{
			for(Containment c1 : parentContainments)
				for(Containment c2 : allContainments)
					if(c2.getName().equals(names[1]) && c2.getParentID().equals(c1.getChildID()))
						childContainments.add(c2);
			
			//Find how many descendants with same name - if none found, throw an exception
			if(childContainments.size() == 0)
				throw new ElementDoesNotExistException("Element " + nodeName + " does not exist in schema " + s.toString());
			/**
			//Find how many descendants with same name - if more than one found, throw an exception
			if(childContainments.size() > 1)
				throw new IllegalArgumentException("Duplicates of Element " + nodeName + " found in schema " + s.toString());
			*/
			
			//That is only one descendant is found and it must be returned.  
			elementFound = childContainments.get(0);
		}
		return elementFound;
    }//end of getElementInstance method
    
    //inner class for throwing exception
    private static class ElementDoesNotExistException extends Exception 
    {
        public ElementDoesNotExistException(String message) 
        {
            // Constructor.  Create a ElementDoesNotExistException object containing
            // the given message as its error message.
        super(message);
        }
    } 
}//end of class CalculatePerformance


