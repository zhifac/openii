// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import model.Schemas;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;

/** Class for tracking primary matches */
public class PrimaryMatches
{
	/** Private class for handling the comparison of schema objects */
	private class SchemaElementComparator implements Comparator<SchemaElement>
	{
		public int compare(SchemaElement so1, SchemaElement so2)
		{
			if(so1.getClass()==so2.getClass()) return so1.getName().compareTo(so2.getName());
			if(so1.getClass()==Entity.class) return -1; if(so2.getClass()==Entity.class) return 1;
			if(so1.getClass()==Attribute.class) return -1; if(so2.getClass()==Attribute.class) return 1;
			if(so1.getClass()==DomainValue.class) return -1; if(so2.getClass()==DomainValue.class) return 1;
			if(so1.getClass()==Relationship.class) return -1; if(so2.getClass()==Relationship.class) return 1;
			return 1;
		}
	}
	
	/** Object storing match information */
	private ArrayList<SchemaElement> matches = new ArrayList<SchemaElement>();
	
	/** Object storing primary match information */
	private HashMap<SchemaElement,ArrayList<SchemaElement>> primaryMatches = new HashMap<SchemaElement,ArrayList<SchemaElement>>();

	/** Adds a primary match to the primary match mapping */
	private void addPrimaryMatch(SchemaElement primaryMatch, SchemaElement match)
	{
		ArrayList<SchemaElement> matches = primaryMatches.get(primaryMatch);
		if(matches==null) primaryMatches.put(primaryMatch,matches = new ArrayList<SchemaElement>());
		if(!primaryMatch.equals(match)) matches.add(match);
	}
	
	/** Marks all primary matches for the specified match */
	private void markPrimaryMatches(SchemaElement match, Integer schemaID)
	{
		boolean isSubsumed = false;
		
		// Check attributes for an existing entity
		if(match instanceof Attribute)
		{
			Integer entityID = ((Attribute)match).getEntityID();
			SchemaElement entity = Schemas.getSchemaElement(entityID);
			if(matches.contains(entity)) { addPrimaryMatch(entity,match); isSubsumed=true; }
		}
		
		// Check domain values for an existing attribute
		if(match instanceof DomainValue)
		{
			Integer domainID = ((DomainValue)match).getDomainID();
			for(Attribute attribute : Schemas.getGraph(schemaID).getAttributes(domainID))
			{
				Integer entityID = attribute.getEntityID();
				SchemaElement entity = Schemas.getSchemaElement(entityID);
				if(matches.contains(entity)) { addPrimaryMatch(entity,match); isSubsumed=true; }
				else if(matches.contains(attribute)) { addPrimaryMatch(attribute,match); isSubsumed=true; }
			}
		}

		// Check aliases for an existing element
		if(match instanceof Alias)
		{
			SchemaElement element = Schemas.getSchemaElement(((Alias)match).getElementID());
			if(matches.contains(element)) addPrimaryMatch(element,match);
		}
		
		// Otherwise, the element is not subsumed
		if(!isSubsumed) addPrimaryMatch(match,match);
	}
	
	/** PrimaryMatches constructor */
	PrimaryMatches(Integer schemaID, Collection<Object> matchesIn)
	{
		// Identify all schema object matches in the full list of matches
		for(Object match : matchesIn)
			if(match instanceof SchemaElement)
				matches.add((SchemaElement)match);
		
		// Mark all primary matches
		for(SchemaElement match : matches)
			markPrimaryMatches(match,schemaID);
	}
	
	/** Returns the primary matches */
	public ArrayList<SchemaElement> getPrimaryMatches()
	{
		ArrayList<SchemaElement> primaryMatchList = new ArrayList<SchemaElement>(primaryMatches.keySet());
		Collections.sort(primaryMatchList,new SchemaElementComparator());
		return primaryMatchList;
	}
	
	/** Returns the subsumptions for the specified primary match */
	public ArrayList<SchemaElement> getSubsumedMatches(SchemaElement primaryMatch)
	{
		ArrayList<SchemaElement> subsumedMatches = primaryMatches.get(primaryMatch);
		Collections.sort(subsumedMatches,new SchemaElementComparator());
		return subsumedMatches;
	}
}