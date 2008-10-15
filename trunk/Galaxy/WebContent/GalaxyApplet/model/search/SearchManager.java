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
import org.mitre.schemastore.model.graph.HierarchicalGraph;

import model.Schemas;
import model.SelectedObjects;
import model.listeners.SearchListener;

/** Class for managing schema searches */
public class SearchManager
{	
	/** Enumeration for storing element types */
	static public Integer SCHEMA = 0;
	static public Integer ENTITY = 1;
	static public Integer DOMAIN = 2;
	static public Integer DOMAIN_VALUE = 3;
	
	/** List of keywords */
	static private ArrayList<Keyword> keywords = new ArrayList<Keyword>();
	
	/** List of search results */
	static private ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();

	/** List of all matched objects */
	static private HashSet<Integer> matchedObjects = new HashSet<Integer>();
	
	/** List of listeners monitoring schema searches */
	static private ArrayList<SearchListener> searchListeners = new ArrayList<SearchListener>();
	
	/** Searches for the specified keywords */
	static public void searchFor(String keywordString)
	{
		// Initialize the keywords and search results
		keywords = new ArrayList<Keyword>();
		searchResults = new ArrayList<SearchResult>();
		
		// Only do a keyword search if a keyword was given
		if(keywordString.length()>0)
		{		
			// First, generate the list of keywords
			for(String keyword : keywordString.split("\\s+"))
				keywords.add(new Keyword(keyword));
			
			// Next, identify identified search results
			SchemaLoop: for(Schema schema : Schemas.getSchemas())
			{
				// Don't search through schemas not in the selected groups
				if(!SelectedObjects.inSelectedGroups(schema.getId())) continue;
				HierarchicalGraph graph = Schemas.getTempGraph(schema.getId());
				
				// Generate a search result for the schema
				ArrayList<Match> matches = new ArrayList<Match>();
				for(Keyword keyword : keywords)
				{
					Integer type = keyword.getType();
					boolean matchesFound = false;
					
					// Handle schemas which match keyword
					if(type==null || type==SCHEMA)
						if(keyword.isContainedIn(schema.getName()) || keyword.isContainedIn(schema.getDescription()))
							{ matches.add(new Match(schema,SCHEMA)); matchesFound=true; }
					
					// Handle schema elements which match keyword
					if(type==null || type==ENTITY || type==DOMAIN)
					{
						// First, gather up all elements to be compared against keyword
						ArrayList<Match> possMatches = new ArrayList<Match>();
						for(SchemaElement element : graph.getGraphElements())
						{
							// Store element to match
							if(type==null || type==ENTITY)
								possMatches.add(new Match(element,ENTITY));
								
							// Find all domain/domain values to match
							if(type==null || type==DOMAIN)
							{
								Domain domain = graph.getDomainForElement(element.getId());
								if(domain!=null)
								{
									possMatches.add(new Match(domain,DOMAIN));
									for(DomainValue domainValue : graph.getDomainValuesForDomain(domain.getId()))
										possMatches.add(new Match(domainValue,DOMAIN_VALUE));
								}
							}
						}
						
						// Check to see if elements match keyword
						for(Match possMatch : possMatches)
						{
							SchemaElement element = (SchemaElement)possMatch.getElement();
							if(keyword.isContainedIn(element.getName()) || keyword.isContainedIn(element.getDescription()))
								{ matches.add(possMatch); matchesFound=true; }
						}
					}
					
					// If no matches found for keyword, proceed to next schema
					if(!matchesFound) continue SchemaLoop;
				}
	
				// If search results are unique to this schema, store search results
				boolean unique = false;
				for(Match match : matches)
				{
					if(match.getElement() instanceof Schema) { unique=true; break; }
					else
					{
						SchemaElement element = (SchemaElement)match.getElement();
						if(element.getBase()!=null && element.getBase().equals(schema.getId()))
							{ unique=true; break; }
					}
				}
				if(unique) searchResults.add(new SearchResult(schema,matches,graph));
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
			for(Match match : searchResult.getMatches())
				matchedObjects.add(match.getElementID());
		
		// Indicate that the search results have changed
		fireSearchResultsChangedEvent();
	}
	
	/** Indicates if the search results contains the specified object */
	static public boolean containsObject(Integer elementID)
		{ return matchedObjects.contains(elementID); }
	
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
