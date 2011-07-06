// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.matchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.harmony.matchers.MatcherScore;
import org.mitre.harmony.matchers.MatcherScores;
import org.mitre.harmony.matchers.options.MatcherCheckboxOption;
import org.mitre.harmony.matchers.options.MatcherOption;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/** Exact Matcher Class */
public class ExactMatcher extends Matcher
{
	// Stores the matcher options
	private MatcherCheckboxOption name = new MatcherCheckboxOption(NAME,true);
	private MatcherCheckboxOption description = new MatcherCheckboxOption(DESCRIPTION,false);
	private MatcherCheckboxOption hierarchy = new MatcherCheckboxOption(HIERARCHY,true);
	
	/** Returns the name of the matcher */
	public String getName()
		{ return "Exact Matcher"; }

	/** Returns the list of options associated with the bag matcher */
	public ArrayList<MatcherOption> getMatcherOptions()
	{
		ArrayList<MatcherOption> options = new ArrayList<MatcherOption>();
		options.add(name);
		options.add(description);
		options.add(hierarchy);
		return options;
	}

	/** Returns the element name and/or description */
	private String getName(HierarchicalSchemaInfo schema, Integer elementID)
	{
		StringBuffer value = new StringBuffer();

		// Retrieve name if the "name" option is set
		if(name.isSelected())
		{
			// Get the name, trimming the edges and collapsing spaces to be one space long
			String name = schema.getDisplayName(elementID) + " -> ";
			name = name.replaceAll("\\b\\s{2,}\\b", " ").trim();
			value.append(name);
		}

		// Retrieve description if the "description" option is set
		if(description.isSelected())
		{
			// Get the description, trimming the edges and collapsing spaces to be one space long
			String description = schema.getElement(elementID).getDescription();
			description = description.replaceAll("\\b\\s{2,}\\b", " ").trim();
			if(description.length() > 0) { value.append(description); }
		}
		
		return value.toString();
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
			String key = getName(schema2, element.getId());
			if(key.length()==0) continue;
			ArrayList<Integer> targetIDs = targetMap.get(key);
			if(targetIDs == null)
				targetMap.put(key, targetIDs = new ArrayList<Integer>());
			targetIDs.add(element.getId());
			completedComparisons++;
		}
		
		// Find all exact matches
		MatcherScores scores = new MatcherScores(100.0);
		for(SchemaElement sourceElement : sourceElements)
		{
			String key = getName(schema1,sourceElement.getId());
			ArrayList<Integer> targetIDs = targetMap.get(key);
			if(targetIDs != null)
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

		// Search for matching hierarchical matches
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
			{
				String name1 = getName(schema1, sourceElement.getId());
				String name2 = getName(schema2, targetID);
				if(name1.length()>0 && name1.equals(name2))
					scores.setScore(sourceElement.getId(), targetID, new MatcherScore(100.0,100.0));
			}

			// Update the completed comparison count
			completedComparisons++;
		}
		return scores;
	}
	
	/** Generates scores for the specified elements */
	public MatcherScores match() {
		// Don't proceed if neither "name" nor "description" option selected
		if (!name.isSelected() && !description.isSelected()) {
			return new MatcherScores(100.0);
		}

		// Generate the matches
		if (hierarchy.isSelected()) {
			return getExactHierarchicalMatches();
		} else {
			return getExactMatches();
		}
	}
}