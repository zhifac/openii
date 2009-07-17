package org.mitre.harmony.matchers.mergers;

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
	/** Stores a cache of all schema element types */
	private HashMap<Integer,String> classMap = new HashMap<Integer,String>();

	private HashMap<String, Integer> typeMap = new HashMap<String,Integer>();

	/** Returns the name associated with this match merger */
	public String getName()
		{ return "Typed Vote Merger"; }

	/** Initializes the typed vote merger */
	public void initialize(FilteredGraph schema1, FilteredGraph schema2, HashMap<String, Integer> typeMap)
	{
		this.typeMap = typeMap;
		super.initialize(schema1,schema2, typeMap);

		// Generate the class map
		for(SchemaElement element : schema1.getFilteredElements())
			classMap.put(element.getId(), element.getClass().getName());
		for(SchemaElement element : schema2.getFilteredElements())
			classMap.put(element.getId(), element.getClass().getName());
	}
	
	/** Adds voter scores to the vote merger with only same-type (e.g. attribute to attribute) matches */
	public void addVoterScores(VoterScores voterScores)
	{

		// Filter out voter scores between elements of different types
		VoterScores filteredVoterScores = new VoterScores(voterScores.getScoreCeiling());
		for(ElementPair elementPair : voterScores.getElementPairs())
		{
			Integer element1 = elementPair.getElement1();
			Integer element2 = elementPair.getElement2();
			String class1 = classMap.get(element1);
			String class2 = classMap.get(element2);
			String key = class1.toString() + "," + class2.toString();
//			if(class1!=null && class1.equals(class2))
			if (typeMap.containsKey(key)) {
				filteredVoterScores.setScore(element1, element2, voterScores.getScore(elementPair));
			}
		}
		// Adds voter scores to the vote merger
		super.addVoterScores(filteredVoterScores);
	}
}