// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.matchers.VoterScore;
import org.mitre.harmony.matchers.VoterScores;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;

/** Entity Matcher Class */
public class EntityMatcher extends BagMatcher
{
	/** Defines the entity map class */
	protected class EntityMap extends HashMap<SchemaElement,ArrayList<SchemaElement>> {};
	
	/** Returns the name of the match voter */
	public String getName()
		{ return "Entity Only"; }
	
	/** Generates scores for the specified elements */
	public VoterScores match()
	{
		EntityMap sourceEntities = getEntities(schema1);
		EntityMap targetEntities = getEntities(schema2);		
		return match(sourceEntities,targetEntities);
	}
	
	/** Generates scores for the specified source and target entities */
	protected VoterScores match(EntityMap sourceEntities, EntityMap targetEntities)
	{
		HashMap<Integer,WordBag> wordBags = new HashMap<Integer,WordBag>();
		
		// Create word bags for the source entities
		for(SchemaElement sourceEntity : sourceEntities.keySet())
			wordBags.put(sourceEntity.getId(), generateWordBag(sourceEntity,sourceEntities.get(sourceEntity)));

		// Create word bags for the target entities
		for(SchemaElement targetEntity : targetEntities.keySet())
			wordBags.put(targetEntity.getId(), generateWordBag(targetEntity,targetEntities.get(targetEntity)));

		// Sets the completed and total comparisons
		completedComparisons = 0;
		totalComparisons = sourceEntities.size() * targetEntities.size();
		
		// Generate the scores
		VoterScores scores = new VoterScores(SCORE_CEILING);
		for(SchemaElement sourceElement : sourceEntities.keySet())
			for(SchemaElement targetElement : targetEntities.keySet())
			{
				if(scores.getScore(sourceElement.getId(), targetElement.getId())==null)
				{
					WordBag sourceBag = wordBags.get(sourceElement.getId());
					WordBag targetBag = wordBags.get(targetElement.getId());
					VoterScore score = computeScore(sourceBag, targetBag);
					if(score != null)
						scores.setScore(sourceElement.getId(), targetElement.getId(), score);
				}
				completedComparisons++;
			}
		return scores;
	}
	
	/** Returns the entities for the specified schema */
	private EntityMap getEntities(FilteredSchemaInfo schema)
	{
		EntityMap entityMap = new EntityMap();
		for(SchemaElement entity : schema.getElements(Entity.class))
		{
			// Retrieve the elements associated with the entity
			ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
			for(SchemaElement element : schema.getReferencingElements(entity.getId()))
			{
				if(element instanceof Containment && ((Containment)element).getChildID().equals(entity.getId())) continue;
				if(element instanceof Subtype && ((Subtype)element).getChildID().equals(entity.getId())) continue;
				elements.add(element);
			}

			// Add the elements to the entity map if falls within filter
			boolean visible = false;
			visible |= schema.isVisible(entity.getId());
			for(SchemaElement element : elements)
				if(schema.isVisible(element.getId())) { visible = true; break; }
			if(visible) entityMap.put(entity,elements);
		}
		return entityMap;
	}
	
	/** Generates a word bag for the given entity */
	private WordBag generateWordBag(SchemaElement entity, ArrayList<SchemaElement> elements)
	{
		WordBag wordBag = new WordBag(entity);
		for(SchemaElement element : elements)
			wordBag.addElement(element);
		return wordBag;
	}
}