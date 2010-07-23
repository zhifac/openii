// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers.voters;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitre.harmony.matchers.MatcherScores;
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
	public MatcherScores match()
	{
		EntityMap sourceEntities = getEntities(schema1);
		EntityMap targetEntities = getEntities(schema2);		
		return match(sourceEntities, targetEntities);
	}
	
	/** Generates scores for the specified source and target entities */
	protected MatcherScores match(EntityMap sourceEntities, EntityMap targetEntities)
	{
		// Generate the word bags for each entity
		HashMap<Integer,WordBag> wordBags = new HashMap<Integer,WordBag>();
		for(EntityMap entities : new EntityMap[]{sourceEntities,targetEntities})
			for(SchemaElement entity : entities.keySet())
				wordBags.put(entity.getId(), generateWordBag(entity,entities.get(entity)));
		
		// Generate the scores
		return computeScores(new ArrayList<SchemaElement>(sourceEntities.keySet()), new ArrayList<SchemaElement>(targetEntities.keySet()), wordBags);
	}
	
	/** Returns the entities for the specified schema */
	protected EntityMap getEntities(FilteredSchemaInfo schema)
	{
		EntityMap entityMap = new EntityMap();
		for(SchemaElement entity : schema.getElements(Entity.class))
		{
			// Retrieve the elements associated with the entity
			ArrayList<SchemaElement> elements = new ArrayList<SchemaElement>();
			for(SchemaElement element : schema.getReferencingElements(entity.getId()))
			{
				if(element instanceof Containment && ((Containment)element).getChildID().equals(entity.getId())) { continue; }
				if(element instanceof Subtype && ((Subtype)element).getChildID().equals(entity.getId())) { continue; }
				elements.add(element);
			}

			// Add the elements to the entity map if falls within filter
			boolean visible = false;
			visible |= schema.isVisible(entity.getId());
			for(SchemaElement element : elements)
				if(schema.isVisible(element.getId()))
					{ visible = true; break; }
			if(visible) entityMap.put(entity,elements);
		}
		return entityMap;
	}
	
	/** Generates a word bag for the given entity */
	private WordBag generateWordBag(SchemaElement entity, ArrayList<SchemaElement> elements)
	{
		// Determine if the name and description should be used
		boolean useName = options.get("UseName").isSelected();
		boolean useDescription = options.get("UseDescription").isSelected();

		// Generate the word bag
		WordBag wordBag = new WordBag(entity, useName, useDescription);
		for(SchemaElement element : elements)
			wordBag.addElement(element, useName, useDescription);
		return wordBag;
	}
}