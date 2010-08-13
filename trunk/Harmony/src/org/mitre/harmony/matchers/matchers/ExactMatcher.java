// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.matchers;

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
public class ExactMatcher extends Matcher
{	
	/** Returns the name of the matcher */
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
	private String getName(HierarchicalSchemaInfo schema, Integer elementID) {
		StringBuffer value = new StringBuffer();

		// use the name to match things if the name option is selected
		if (options.get(NAME).isSelected()) {
			String name = schema.getDisplayName(elementID) + " -> ";

			// trim the edges and collapse spaces in the name to be only one space long
			name = name.replaceAll("\\b\\s{2,}\\b", " ");
			name = name.trim();

			// append our modified name
			value.append(name);
		}

		// use the description of the element to match if the description option is selected
		if (options.get(DESCRIPTION).isSelected()) {
			String description = schema.getElement(elementID).getDescription();

			// trim the edges and collapse spaces in the description to be only one space long
			description = description.replaceAll("\\b\\s{2,}\\b", " ");
			description = description.trim();

			// append our modified description but only if it contains something
			if (description.length() > 0) { value.append(description); }
		}
		return value.toString();
	}
	
	/** Generate scores for the exact matches */
	private MatcherScores getExactMatches() {
		// Get the source and target elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		ArrayList<SchemaElement> targetElements = schema2.getFilteredElements();

		// Sets the current and total comparisons
		completedComparisons = 0;
		totalComparisons = sourceElements.size() + targetElements.size();
		
		// Generate a hash of all target elements
		HashMap<String,ArrayList<Integer>> targetMap = new HashMap<String,ArrayList<Integer>>();
		for (SchemaElement element : schema2.getFilteredElements()) {
			String key = getName(schema2, element.getId());
			ArrayList<Integer> targetIDs = targetMap.get(key);

			if (targetIDs == null) {
				targetMap.put(key, targetIDs = new ArrayList<Integer>());
			}
			targetIDs.add(element.getId());
			completedComparisons++;
		}
		
		// Find all exact matches
		MatcherScores scores = new MatcherScores(100.0);
		for (SchemaElement sourceElement : sourceElements) {
			String key = getName(schema1,sourceElement.getId());
			ArrayList<Integer> targetIDs = targetMap.get(key);

			if (targetIDs != null) {
				for (Integer targetID : targetIDs) {
					scores.setScore(sourceElement.getId(), targetID, new MatcherScore(100.0,100.0));
				}
			}
			completedComparisons++;
		}
		return scores;
	}

	/** Generate scores for the exact structure matches */
	private MatcherScores getExactHierarchicalMatches() {
		// Get the source and target elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();

		// Sets the current and total comparisons
		completedComparisons = 0;
		totalComparisons = sourceElements.size();

		MatcherScores scores = new MatcherScores(100.0);
		for (SchemaElement sourceElement : sourceElements) {
			// Retrieve all matching target elements
			HashSet<Integer> targetIDs = new HashSet<Integer>();
			for (ArrayList<SchemaElement> sourcePath : schema1.getPaths(sourceElement.getId())) {
				// Retrieve the source path
				ArrayList<String> path = new ArrayList<String>();
				for (SchemaElement element : sourcePath) {
					path.add(schema1.getDisplayName(element.getId()));
				}

				// Identify all target paths
				for (Integer targetID : schema2.getPathIDs(path)) {
					if (schema2.isVisible(targetID)) {
						targetIDs.add(targetID);
					}
				}
			}

			// Set scores for the matching target elements
			for (Integer targetID : targetIDs) {
				String schema1name = getName(schema1, sourceElement.getId());
				String schema2name = getName(schema2, targetID);
				if (!options.get(DESCRIPTION).isSelected() || (schema1name != null && schema2name != null && schema1name.equals(schema2name))) {
					scores.setScore(sourceElement.getId(), targetID, new MatcherScore(100.0,100.0));
				}
			}

			// Update the completed comparison count
			completedComparisons++;
		}
		return scores;
	}
	
	/** Generates scores for the specified elements */
	public MatcherScores match() {
		// Don't proceed if neither "name" nor "description" option selected
		if (!options.get(NAME).isSelected() && !options.get(DESCRIPTION).isSelected()) {
			return new MatcherScores(100.0);
		}

		// Generate the matches
		if (options.get(HIERARCHY).isSelected()) {
			return getExactHierarchicalMatches();
		} else {
			return getExactMatches();
		}
	}
}