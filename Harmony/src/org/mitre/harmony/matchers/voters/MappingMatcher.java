// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.SchemaInfo;

/** Mapping Matcher Class */
public class MappingMatcher extends BagMatcher
{
	/** Stores the SchemaStore client */
	private SchemaStoreClient client = null;

	/** Stores the word bag used for this matcher */
	private HashMap<Integer, WordBag> wordBags = new HashMap<Integer, WordBag>();
	
	/** Initializes the Mapping Matcher class */
	public MappingMatcher(SchemaStoreClient client)
		{ this.client = client; }
	
	/** Returns the name of the match voter */
	public String getName() { return "Mapping"; }

	/** Generates match scores for the specified elements */
	public VoterScores match()
	{				
		// Create word bags for the source elements
		ArrayList<SchemaElement> sourceElements = schema1.getFilteredElements();
		for(SchemaElement sourceElement : sourceElements)
			wordBags.put(sourceElement.getId(), new WordBag(sourceElement));
		
		// Create word bags for the target elements
		ArrayList<SchemaElement> targetElements = schema2.getFilteredElements();
		for(SchemaElement targetElement : targetElements)
			wordBags.put(targetElement.getId(), new WordBag(targetElement));

		// Identify associated mappings
		ArrayList<Mapping> associatedMappings = new ArrayList<Mapping>();
		associatedMappings = getAssociatedMappings(schema1.getSchema().getId());
		associatedMappings = getAssociatedMappings(schema2.getSchema().getId());
		
		// Generate scores (only if associated mappings exist)
		if(associatedMappings.size()>0)
		{
			// Add terms from associated mappings
			for(Mapping associatedMapping : associatedMappings)
				addAssociatedTerms(associatedMapping);
			
			// Generate the match scores
			return computeScores(sourceElements, targetElements, wordBags);
		}
		return new VoterScores(SCORE_CEILING);
	}

	/** Returns the associated mappings */
	private ArrayList<Mapping> getAssociatedMappings(Integer schemaID)
	{
		ArrayList<Mapping> mappings = new ArrayList<Mapping>();
		try {
			for(Project project : client.getProjects())
				for(Mapping mapping : client.getMappings(project.getId()))
					if(mapping.getSourceId().equals(schemaID) || mapping.getTargetId().equals(schemaID))
						mappings.add(mapping);
		} catch(Exception e) {}
		return mappings;
	}
	
	/** Adds associated terms for the associated mapping */
	private void addAssociatedTerms(Mapping mapping)
	{
		try {
			// Identify the associated schema
			boolean isSource = !mapping.getSourceId().equals(schema1) && !mapping.getSourceId().equals(schema2);
			SchemaInfo schema = client.getSchemaInfo(isSource ? mapping.getSourceId() : mapping.getTargetId());

			// Find associated elements
			for(MappingCell mappingCell : client.getMappingCells(mapping.getId()))
				if(mappingCell.isValidated())
				{
					// Get reference IDs
					ArrayList<Integer> referenceIDs = new ArrayList<Integer>();
					if(isSource) referenceIDs.add(mappingCell.getOutput());
					else referenceIDs.addAll(Arrays.asList(mappingCell.getInput()));
					
					// Get the word bags
					ArrayList<WordBag> bags = new ArrayList<WordBag>();
					for(Integer referenceID : referenceIDs)
					{
						WordBag bag = wordBags.get(referenceID);
						if(bag!=null) bags.add(bag);
					}
					if(bags.size()==0) continue;
	
					// Get associated IDs
					ArrayList<Integer> associatedIDs = new ArrayList<Integer>();
					if(isSource) associatedIDs.addAll(Arrays.asList(mappingCell.getInput()));
					else associatedIDs.add(mappingCell.getOutput());
					
					// Add in associated terms
					for(Integer associatedID : associatedIDs)
					{
						SchemaElement element = schema.getElement(associatedID);
						for(WordBag bag : bags) bag.addElement(element);
					}
				}
		} catch(Exception e) {}
	}
}