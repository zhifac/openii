// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.harmony.matchers.MatcherOption;
import org.mitre.harmony.matchers.MatcherScore;
import org.mitre.harmony.matchers.MatcherScores;
import org.mitre.harmony.matchers.MatcherOption.OptionType;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/** Exact Matcher Class */
public class ExactMatcher extends MatchVoter
{
	// Constants for the option names
	private static final String NAME = "UseName";
	private static final String DESCRIPTION = "UseDescription";
	private static final String HIERARCHY = "UseHierarchy";
	
	/** Returns the name of the match voter */
	public String getName()
		{ return "Exact Matcher"; }

	/** Returns the list of options associated with the bag matcher */
	public ArrayList<MatcherOption> getMatcherOptions()
	{
		ArrayList<MatcherOption> options = new ArrayList<MatcherOption>();
		options.add(new MatcherOption(OptionType.CHECKBOX,NAME,"true"));
		options.add(new MatcherOption(OptionType.CHECKBOX,DESCRIPTION,"false"));
		options.add(new MatcherOption(OptionType.CHECKBOX,HIERARCHY,"true"));
		return options;
	}

	/** Returns the element name and/or description */
	private String getName(HierarchicalSchemaInfo schema, Integer elementID)
	{
		StringBuffer name = new StringBuffer();
		if(options.get(NAME).isSelected()) name.append(schema.getDisplayName(elementID) + " -> ");
		if(options.get(DESCRIPTION).isSelected()) name.append(schema.getElement(elementID).getDescription());
		return name.toString();
	}
	
	/** Generate scores for the exact matches */
	private MatcherScores getExactMatches()
	{
		// Get the source and target elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		ArrayList<SchemaElement> targetElements = schema2.getFilteredElements();

		// Sets the current and total comparisons
		completedComparisons = 0;
		totalComparisons = sourceElements.size() + targetElements.size();
		
		// Generate a hash of all target elements
		HashMap<String,ArrayList<Integer>> targetMap = new HashMap<String,ArrayList<Integer>>();
		for(SchemaElement element : schema2.getFilteredElements())
		{
			String key = getName(schema2,element.getId());
			ArrayList<Integer> targetIDs = targetMap.get(key);
			if(targetIDs==null) targetMap.put(key, targetIDs = new ArrayList<Integer>());
			targetIDs.add(element.getId());
			completedComparisons++;
		}
		
		// Find all exact matches
		MatcherScores scores = new MatcherScores(100.0);
		for(SchemaElement sourceElement : sourceElements)
		{
			ArrayList<Integer> targetIDs = targetMap.get(getName(schema1,sourceElement.getId()));
			if(targetIDs!=null)
				for(Integer targetID : targetIDs)
					scores.setScore(sourceElement.getId(), targetID, new MatcherScore(100.0,100.0));					
			completedComparisons++;
		}
		return scores;
	}

	/** Generate scores for the exact structure matches */
	private MatcherScores getExactHierarchicalMatches()
	{
		// Get the source and target elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();

		// Sets the current and total comparisons
		completedComparisons = 0;
		totalComparisons = sourceElements.size();

		MatcherScores scores = new MatcherScores(100.0);
		for(SchemaElement sourceElement : sourceElements)
		{
			// Retrieve all matching target elements
			HashSet<Integer> targetIDs = new HashSet<Integer>();
			for(ArrayList<SchemaElement> sourcePath : schema1.getPaths(sourceElement.getId()))
			{
				// Retrieve the source path
				ArrayList<String> path = new ArrayList<String>();
				for(SchemaElement element : sourcePath)
					path.add(schema1.getDisplayName(element.getId()));

				// Identify all target paths
				for(Integer targetID : schema2.getPathIDs(path))
					if(schema2.isVisible(targetID))
						targetIDs.add(targetID);
			}

			// Set scores for the matching target elements
			for(Integer targetID : targetIDs)
				if(!options.get(DESCRIPTION).isSelected() || getName(schema1,sourceElement.getId()).equals(getName(schema2,targetID)))
					scores.setScore(sourceElement.getId(), targetID, new MatcherScore(100.0,100.0));

			// Update the completed comparison count
			completedComparisons++;
		}
		return scores;
	}
	
	/** Generates scores for the specified elements */
	public MatcherScores match()
	{
		// Don't proceed if neither "name" nor "description" option selected
		if(!options.get(NAME).isSelected() && !options.get(DESCRIPTION).isSelected())
			return new MatcherScores(100.0);
		// Generate the matches
		if(options.get(HIERARCHY).isSelected())
			return getExactHierarchicalMatches();
		else return getExactMatches();
	}
}