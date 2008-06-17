// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.mitre.schemastore.model.Schema;

/** Class for storing a schema's search results */
public class SearchResult
{
	/** Stores the search result schema */
	private Schema schema;
	
	/** Stores the search result matches */
	private HashMap<Keyword,ArrayList<Object>> matches = new HashMap<Keyword,ArrayList<Object>>();
	
	/** Constructs the search result */
	SearchResult(Schema schema)
		{ this.schema = schema; }
	
	/** Adds a match to the search result */
	void addMatch(Keyword keyword, Object match)
	{
		ArrayList<Object> keywordMatches = matches.get(keyword);
		if(keywordMatches==null)
			matches.put(keyword,keywordMatches = new ArrayList<Object>());
		keywordMatches.add(match);
	}
	
	/** Returns the search result schema */
	public Schema getSchema()
		{ return schema; }
	
	/** Returns the search result keywords */
	public Set<Keyword> getKeywords()
		{ return matches.keySet(); }
	
	/** Returns the search result matches for the specified keyword */
	public ArrayList<Object> getMatches(Keyword keyword)
	{
		ArrayList<Object> keywordMatches = matches.get(keyword);
		if(keywordMatches==null) keywordMatches = new ArrayList<Object>();
		return keywordMatches;
	}
	
	/** Returns the search result matches */
	public Collection<Object> getMatches()
	{
		HashSet<Object> allMatches = new HashSet<Object>();
		for(ArrayList<Object> keywordMatches : matches.values())
			allMatches.addAll(keywordMatches);
		return allMatches;
	}
	
	/** Returns the primary match object */
	public PrimaryMatches getPrimaryMatches()
		{ return new PrimaryMatches(schema.getId(),getMatches()); }
}
