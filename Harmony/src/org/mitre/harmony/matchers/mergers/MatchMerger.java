// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.mergers;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.matchers.ElementPair;
import org.mitre.harmony.matchers.MatchScores;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/** Matcher Interface - A match merger merged together the results of multiple match voters */
public abstract class MatchMerger
{
	/** Class defining the type mappings */
	static public class TypeMappings extends HashMap<Class<SchemaElement>,ArrayList<Class<SchemaElement>>>{};
	
	// Stores the match merger schema information
	protected FilteredSchemaInfo schema1, schema2;

	/** Stores the match merger type mapping information */
	protected TypeMappings typeMappings;
	
	/** Return the name of the match merger */
	abstract public String getName();

	/** Initializes the match merger */
	public void initialize(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2, TypeMappings typeMappings)
		{ this.schema1 = schema1; this.schema2 = schema2; this.typeMappings = typeMappings; initialize(); }
	
	/** Initializes the match merger */
	abstract protected void initialize();
	
	/** Feeds voter scores into the merger */
	public void addVoterScores(VoterScores scores)
	{
		// Add all scores to merger if no type mapping is set
		if(typeMappings==null) addVoterScoresToMerger(scores);
		
		// Otherwise, filter out all scores which are not part of the type mapping
		VoterScores filteredScores = new VoterScores(scores.getScoreCeiling());
		for(ElementPair elementPair : scores.getElementPairs())
		{
			// Get the elements being mapped together
			SchemaElement sourceElement = schema1.getElement(elementPair.getSourceElement());
			SchemaElement targetElement = schema2.getElement(elementPair.getTargetElement());

			ArrayList<Class<SchemaElement>> typeMapping = typeMappings.get(sourceElement.getClass());
			if(!typeMapping.contains(targetElement.getClass())) continue;
			filteredScores.setScore(sourceElement.getId(), targetElement.getId(), scores.getScore(elementPair));
		}
		addVoterScoresToMerger(filteredScores);
	}

	/** Feeds voter scores into the merger */
	abstract protected void addVoterScoresToMerger(VoterScores scores);
	
	/** Retrieve match scores from the merger */
	abstract public MatchScores getMatchScores();	
}