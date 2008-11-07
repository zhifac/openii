// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;

import model.Schemas;
import model.SelectedObjects;
import model.listeners.SearchListener;

/** Class for managing schema searches */
public class SearchManager
{	
	/** List of keywords */
	static private ArrayList<Keyword> keywords = new ArrayList<Keyword>();
	
	/** List of search results */
	static private ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();

	/** List of all matched elements */
	static private HashSet<Integer> matchedElements = new HashSet<Integer>();
	
	/** List of listeners monitoring schema searches */
	static private ArrayList<SearchListener> searchListeners = new ArrayList<SearchListener>();
	
	/** Returns the type for the specified element */
	static private Integer getType(SchemaElement element)
	{
		if(element instanceof Entity || element instanceof Attribute) return Keyword.ENTITY;
		if(element instanceof Domain || element instanceof DomainValue) return Keyword.DOMAIN;
		if(element instanceof Relationship || element instanceof Subtype || element instanceof Containment) return Keyword.RELATIONSHIP;
		else if(element instanceof Alias) return getType(Schemas.getSchemaElement(((Alias)element).getElementID()));	
		return null;
	}
	
	/** Searches for the specified keywords */
	static public void searchFor(String keywordString)
	{
		// Initialize the keywords and search results
		keywords = new ArrayList<Keyword>();
		searchResults = new ArrayList<SearchResult>();
		matchedElements = new HashSet<Integer>();
		
		// Only do a keyword search if a keyword was given
		if(keywordString.length()>0)
		{		
			// First, generate the list of keywords
			for(String keyword : keywordString.split("\\s+"))
				keywords.add(new Keyword(keyword));
			
			// Next identify the matches for each keyword
			ArrayList<ArrayList<Match>> keywordMatches = new ArrayList<ArrayList<Match>>();
			for(Keyword keyword : keywords)
			{
				// Get the keyword type and initialize an array to store found matches
				Integer type = keyword.getType();
				ArrayList<Match> matches = new ArrayList<Match>();
				keywordMatches.add(matches);
				
				// Retrieve all matching schemas
				if(type==null || type==Keyword.SCHEMA)
					for(Schema schema : Schemas.getSchemas())
						if(SelectedObjects.inSelectedGroups(schema.getId()))
							if(keyword.isContainedIn(schema.getName()) || keyword.isContainedIn(schema.getDescription()))
								matches.add(new Match(schema));
				
				// Retrieve all matching schema elements
				for(SchemaElement element : Schemas.getElementsForKeyword(keyword.getKeyword(), SelectedObjects.getSelectedGroups()))
					if(type==null || type.equals(getType(element)))
						matches.add(new Match(element));
			}
			
			// Then identify the matched schemas
			HashSet<Integer> matchedSchemas = null;
			for(ArrayList<Match> matches : keywordMatches)
			{
				// Identify the schemas matching a single keyword
				HashSet<Integer> keywordSchemas = new HashSet<Integer>();
				for(Match match : matches)
					keywordSchemas.add(match.getSchema());
				
				// Merge these schemas back into the list of all matched schemas
				if(matchedSchemas==null) matchedSchemas = keywordSchemas;
				else for(Integer matchedSchema : new ArrayList<Integer>(matchedSchemas))
					if(!keywordSchemas.contains(matchedSchema))
						matchedSchemas.remove(matchedSchema);
			}
			
			// Finally construct the search results
			for(Integer schemaID : matchedSchemas)
			{
				// Retrieve the list of schema matches
				ArrayList<Match> schemaMatches = new ArrayList<Match>();
				for(ArrayList<Match> matches : keywordMatches)
					for(Match match : matches)
						if(match.getSchema().equals(schemaID))
						{
							schemaMatches.add(match);
							matchedElements.add(match.getElementID());
						}

				// Store the generated search result
				searchResults.add(new SearchResult(schemaID,schemaMatches));				
			}
			
			// Sort search results
			class SearchResultsComparator implements Comparator<SearchResult>
			{
				public int compare(SearchResult sr1, SearchResult sr2)
					{ return sr1.getSchema().getName().compareTo(sr2.getSchema().getName()); }
			}
			Collections.sort(searchResults,new SearchResultsComparator());
		}
		
		// Indicate that the search results have changed
		fireSearchResultsChangedEvent();
	}
	
	/** Indicates if the search results contains the specified element */
	static public boolean containsElement(Integer elementID)
		{ return matchedElements.contains(elementID); }
	
	/** Returns the list of keywords */
	static public ArrayList<Keyword> getKeywords()
		{ return keywords; }
	
	/** Return the list of search results */
	static public ArrayList<SearchResult> getSearchResults()
		{ return searchResults; }
	
	/** Adds a listener monitoring search events */
	static public void addSearchListener(SearchListener listener)
		{ searchListeners.add(listener); }
	
	/** Removes a listener monitoring search events */
	static public void removeSearchListener(SearchListener listener)
		{ searchListeners.remove(listener); }
	
	/** Inform search listeners that the search results have been updated */
	static private void fireSearchResultsChangedEvent()
		{ for(SearchListener listener : searchListeners) listener.searchResultsChanged(); }
}
