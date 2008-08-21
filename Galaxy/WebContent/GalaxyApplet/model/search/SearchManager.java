// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import model.Schemas;
import model.SelectedObjects;
import model.listeners.SearchListener;

/** Class for managing schema searches */
public class SearchManager
{	
	/** List of search results */
	static private ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();

	/** List of all matched objects */
	static private HashSet<Object> matchedObjects = new HashSet<Object>();
	
	/** List of listeners monitoring schema searches */
	static private ArrayList<SearchListener> searchListeners = new ArrayList<SearchListener>();
	
	/** Searches for the specified keywords */
	static public void searchFor(String keywordString)
	{
		// Initialize the search results
		searchResults = new ArrayList<SearchResult>();
		
		// Only do a keyword search if a keyword was given
		if(keywordString.length()>0)
		{		
			// First, generate the list of keywords
			ArrayList<Keyword> keywords = new ArrayList<Keyword>();
			for(String keyword : keywordString.split("\\s+"))
				keywords.add(new Keyword(keyword));
			
			// Next, identify identified search results
			SchemaLoop: for(Schema schema : Schemas.getSchemas())
			{
				// Don't search through schemas not in the selected groups
				if(!SelectedObjects.inSelectedGroups(schema.getId())) continue;
				
				// Generate a search result for the schema
				SearchResult searchResult = new SearchResult(schema);
				for(Keyword keyword : keywords)
				{
					// For schemas, check the name and description
					if(keyword.getType() == null || keyword.getType() == Schema.class)
						if(keyword.isContainedIn(schema.getName()) || keyword.isContainedIn(schema.getDescription()))
							searchResult.addMatch(keyword,schema);
					
					// For schema elements, check the name and description
					for(SchemaElement schemaElement : Schemas.getGraph(schema.getId()).getElements(keyword.getType()))
						if(keyword.isContainedIn(schemaElement.getName()) || keyword.isContainedIn(schemaElement.getDescription()))
							searchResult.addMatch(keyword,schemaElement);
					
					// For domain values, also check domain name and description
					if(keyword.getType() == null || keyword.getType() == DomainValue.class)
						for(SchemaElement schemaElement : Schemas.getGraph(schema.getId()).getElements(Domain.class))
							if(keyword.isContainedIn(schemaElement.getName()) || keyword.isContainedIn(schemaElement.getDescription()))
								searchResult.addMatch(keyword,schemaElement);
					
					// If no matches found for keyword, proceed to next schema
					if(searchResult.getMatches(keyword).isEmpty()) continue SchemaLoop;
				}
	
				// If search results are unique to this schema, store search results
				ParentSchemaLoop: for(Integer parentSchemaID : Schemas.getParentSchemas(schema.getId()))
				{
					for(Object match : searchResult.getMatches())
					{
						if(match instanceof SchemaElement)
							if(!Schemas.getGraph(parentSchemaID).containsElement(((SchemaElement)match).getId()))
								continue ParentSchemaLoop;
						if(match instanceof Schema) break ParentSchemaLoop;
					}
					continue SchemaLoop;
				}
				searchResults.add(searchResult);
			}
			
			// Sort search results
			class SearchResultsComparator implements Comparator<SearchResult>
			{
				public int compare(SearchResult sr1, SearchResult sr2)
					{ return sr1.getSchema().getName().compareTo(sr2.getSchema().getName()); }
			}
			Collections.sort(searchResults,new SearchResultsComparator());
		}
			
		// Updates the list of matched objects
		matchedObjects.clear();
		for(SearchResult searchResult : searchResults)
			for(Object object : searchResult.getMatches())
				matchedObjects.add(object);
		
		// Indicate that the search results have changed
		fireSearchResultsChangedEvent();
	}
	
	/** Indicates if the search results contains the specified object */
	static public boolean containsObject(Object object)
		{ return matchedObjects.contains(object); }
	
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
