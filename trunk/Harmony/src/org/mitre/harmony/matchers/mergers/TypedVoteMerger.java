package org.mitre.harmony.matchers.mergers;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.matchers.ElementPair;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.FilteredGraph;

/**
 * Vote merger which only merges together elements types the user specifies
 * @author JKORB
 */
public class TypedVoteMerger extends VoteMerger
{
	/** Class defining the type mappings */
	static public class TypeMappings extends HashMap<Class<SchemaElement>,ArrayList<Class<SchemaElement>>>{};
	
	/** Stores the schemas between which votes are being merged */
	private FilteredGraph sourceSchema, targetSchema; 
	
	/** Stores the mapping of allowable types */
	private TypeMappings typeMappings = null;

	/** Returns the name associated with this match merger */
	public String getName()
		{ return "Typed Vote Merger"; }

	/** Initializes the typed vote merger */
	public void initialize(FilteredGraph sourceSchema, FilteredGraph targetSchema)
	{
		super.initialize(sourceSchema, targetSchema);
		this.sourceSchema = sourceSchema; this.targetSchema = targetSchema;
	}
	
	/** Sets the allowable types that can be mapped together */
	public void setTypes(TypeMappings typeMappings)
		{ this.typeMappings = typeMappings; }
	
	/** Adds voter scores to the vote merger with only same-type (e.g. attribute to attribute) matches */
	public void addVoterScores(VoterScores voterScores)
	{
		// Filter out voter scores between elements of different types
		VoterScores filteredVoterScores = new VoterScores(voterScores.getScoreCeiling());
		for(ElementPair elementPair : voterScores.getElementPairs())
		{
			// Get the elements being mapped together
			SchemaElement sourceElement = sourceSchema.getElement(elementPair.getSourceElement());
			SchemaElement targetElement = targetSchema.getElement(elementPair.getTargetElement());

			// Throw out any pairing which are not defined in the type mapping
			if(typeMappings!=null)
			{
				ArrayList<Class<SchemaElement>> typeMapping = typeMappings.get(sourceElement.getClass());
				if(typeMapping==null || !typeMapping.contains(targetElement.getClass())) continue;
			}	
			else if(!sourceElement.getClass().equals(targetElement.getClass())) continue;
			
			// Store the voter score
			filteredVoterScores.setScore(sourceElement.getId(), targetElement.getId(), voterScores.getScore(elementPair));
		}
		
		// Adds voter scores to the vote merger
		super.addVoterScores(filteredVoterScores);
	}
}