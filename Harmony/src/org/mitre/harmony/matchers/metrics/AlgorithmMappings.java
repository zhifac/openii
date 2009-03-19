package org.mitre.harmony.matchers.metrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.MatcherManager;
import org.mitre.harmony.matchers.mergers.*;
import org.mitre.harmony.matchers.voters.*;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.graph.FilteredGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgorithmMappings 
{
	/**
	 * This class generates algorithm mappings for all schema pairs matched in the given ground truths file
	 * 
	 * REQUIRES:	One ground truths file in the specified XML format, a list of voters and one merger 
	 * EFFECTS:		Elements of the schemas matched in the given ground truths file are matched using the 
	 * 				specified Voter algorithms.  Scores are then merged using the given Merger algorithm.
	 * 				For each pair of schema, precision and recall values are calculated at various threshold
	 * 				values ranging from 0 to 1.  Then, at each threshold, an average of precision and recall
	 * 				values of all schemas pairs is calculated.  This average is the output of this class. 
	 * @author STANDON
	 */
	
	private StringBuffer buffer = null;
	private BufferedReader br = null;
	
	private Pattern resultsPattern = Pattern.compile("<results>(.*?)</results>");
	private Pattern mappingPattern = Pattern.compile("<mapping>(.*?)</mapping>");
	private Pattern schemaOnePattern = Pattern.compile("<sourceSchema schema1=\"(.*?)\"/>");
	private Pattern schemaTwoPattern = Pattern.compile("<targetSchema schema2=\"(.*?)\"/>");
	
	// Number of schema matches (=number of schema matches found in Ground Truths file)
	private int groundTruthSchemaMatches = 0;
	
	// Stores average precision and recall values for a given Ground Truths file for various threshold values
	// i.e. <threshold, average precision> and <threshold, average recall>
	private HashMap<Double,Double> precision = new HashMap<Double,Double>();
	private HashMap<Double,Double> recall = new HashMap<Double,Double>();
	
	// Stores sum of precision and recall values for a given Ground Truths file for various threshold values
	private HashMap<Double,Double> sumAllPrecision = new HashMap<Double,Double>();
	private HashMap<Double,Double> sumAllRecall = new HashMap<Double,Double>();
	
	// Function that does the main calculations for this class 
	private void metrics(File XMLFile, ArrayList<MatchVoter> selectedVoters, MatchMerger merger, HarmonyModel harmonyModel) 
												throws IllegalFileFormatException
	{
		// Pull the entire file into a string
		try
		{
			br = new BufferedReader(new FileReader(XMLFile));
			buffer = new StringBuffer("");
			String line; 
			while((line=br.readLine())!=null) buffer.append(line);
			br.close();
		}
		catch(FileNotFoundException e)
		{System.out.println("File " + XMLFile.toString() + " could not be found!\n" + e.getMessage());}
		catch(IOException e)
		{System.out.println("File " + XMLFile.toString() + " failed to load!\n" + e.getMessage());}
		
		//initializing sum of precision and recall
		double resolution = 0.01;
		for(double threshold = 0.0; threshold <= (1.0+resolution); threshold += resolution)
		{
			sumAllPrecision.put(threshold, 0.0);
			sumAllRecall.put(threshold, 0.0);
		}

		//Parse out all the results in ground truths file
		Matcher resultsMatcher = resultsPattern.matcher(buffer);
		if(!resultsMatcher.find())
			throw new IllegalFileFormatException("Your Ground Truths file is not in the required format - <results> tag not found");

		resultsMatcher.reset();
		String resultsString = resultsMatcher.find() ? resultsMatcher.group(1) : "";
		
		//Parse out each mapping
		Matcher mappingMatcher = mappingPattern.matcher(resultsString);
		if(!mappingMatcher.find())
			throw new IllegalFileFormatException("Your Ground Truths file is not in the required format - <mapping> tag not found");

		mappingMatcher.reset();
		while(mappingMatcher.find())
		{
			//obtain the mapping string
			String mappingString = null;
			mappingString = mappingMatcher.group(1);
			
			// Get the sourceSchema schema1 
			String schemaOneString = null;
			Matcher schemaOneMatcher = schemaOnePattern.matcher(mappingString);
			if(schemaOneMatcher.find()) schemaOneString = schemaOneMatcher.group(1);
			
			// Get the targetSchema schema2
			String schemaTwoString = null;
			Matcher schemaTwoMatcher = schemaTwoPattern.matcher(mappingString);
			if(schemaTwoMatcher.find()) schemaTwoString = schemaTwoMatcher.group(1);
			
			// Find if the schemas exist in database and get their references
			Schema schemaOne = null;
			Schema schemaTwo = null;
			try
			{
				schemaOne = getSchemaInstance(schemaOneString, harmonyModel);
				schemaTwo = getSchemaInstance(schemaTwoString, harmonyModel);
			}
			catch(SchemaDoesNotExistException e)
			{System.out.println(e.toString());}
			
			//populate the variable used for calculating average of all precision and recall values
			groundTruthSchemaMatches++;
			
			//store these schema references
			ArrayList<Schema> currentMatchedSchemas = new ArrayList<Schema>();
			currentMatchedSchemas.add(schemaOne);
			currentMatchedSchemas.add(schemaTwo);
		
			//Setting up the schema for using the matcher
			FilteredGraph schema1 = new FilteredGraph(harmonyModel.getSchemaManager().getGraph(schemaOne.getId()));
			FilteredGraph schema2 = new FilteredGraph(harmonyModel.getSchemaManager().getGraph(schemaTwo.getId()));
			
			//Obtain merged algorithm scores for this pair of schemas found in ground truths file
			MatchScores schemaPairMergedScores = MatcherManager.getScores(schema1, schema2, selectedVoters, merger);
			
			//Calculate precision and recall for this pair of schema for all threshold values
			//Add these to the database storing the sum of all values of precision and recall
			for(double threshold = 0.0; threshold <= (1.0+resolution); threshold += resolution)
			{
				ArrayList<Double> oneSchemaPairThresholdMetricsArray = null;
				Double sumPrecision = null;
				Double sumRecall = null;
				
				oneSchemaPairThresholdMetricsArray = CalculatePerformance.metricsCalculator(schemaPairMergedScores, mappingString, currentMatchedSchemas, threshold, harmonyModel);
				
				sumPrecision = sumAllPrecision.get(threshold) + oneSchemaPairThresholdMetricsArray.get(0);
				sumAllPrecision.put(threshold, sumPrecision);
				sumRecall = sumAllRecall.get(threshold) + oneSchemaPairThresholdMetricsArray.get(1);
				sumAllRecall.put(threshold, sumRecall);
			}
		}//end of mappingMatcher while loop
		
		for(double threshold = 0.0; threshold <= (1.0+resolution); threshold += resolution)
		{
			Double avgPrecisionOneThreshold = null;
			Double avgRecallOneThreshold = null;
			avgPrecisionOneThreshold = sumAllPrecision.get(threshold)/groundTruthSchemaMatches;
			precision.put(threshold, avgPrecisionOneThreshold);
			avgRecallOneThreshold = sumAllRecall.get(threshold)/groundTruthSchemaMatches;
			recall.put(threshold, avgRecallOneThreshold);
		}
	}//end of metrics method
	
	//obtains schema instance - given a schema name
	private Schema getSchemaInstance(String schemaName, HarmonyModel harmonyModel) throws SchemaDoesNotExistException 
	{
		Schema schemaFound = null;
		for(Schema s : harmonyModel.getSchemaManager().getSchemas())
			if(schemaName.equals(s.getName()))
			{
				schemaFound = s;
				break;
			}
		if(schemaFound == null)
			throw new SchemaDoesNotExistException("Schema " + schemaName + " does not exist");
		else
			return schemaFound;
	}
	
	//inner class for throwing exception
	private class SchemaDoesNotExistException extends Exception 
	{
        public SchemaDoesNotExistException(String message) 
        {
            // Constructor.  Create a SchemaDoesNotExistException object containing
            // the given message as its error message.
         super(message);
        }
	}
	
	public ArrayList<HashMap<Double,Double>> getMetrics(File XMLFile, ArrayList<MatchVoter> selectedVoters, MatchMerger merger, HarmonyModel harmonyModel) throws IllegalFileFormatException
	{
		metrics(XMLFile, selectedVoters, merger, harmonyModel);
		ArrayList<HashMap<Double,Double>> metricsArray = new ArrayList<HashMap<Double,Double>>();
		metricsArray.add(precision);
		metricsArray.add(recall);
		return metricsArray;
	}
}//end of class AlgorithmMappings

