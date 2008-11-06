// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import model.Schemas;

import org.mitre.schemastore.model.Schema;

/** Class for storing a schema's search results */
public class SearchResult
{
	/** Stores the search result schema */
	private Schema schema;

	/** Stores the matches for this search result */
	private ArrayList<Match> matches;
	
	/** Constructs the search result */
	SearchResult(Integer schemaID, ArrayList<Match> matches)
	{
		this.schema = Schemas.getSchema(schemaID);
		this.matches = matches;
		
		// Constructs a match reference table
		HashSet<Integer> matchRef = new HashSet<Integer>();
		for(Match match : matches)
			matchRef.add(match.getElementID());
	}
	
	/** Returns the search result schema */
	public Schema getSchema()
		{ return schema; }
	
	/** Returns the search result matches */
	public Collection<Match> getMatches()
		{ return new ArrayList<Match>(matches); }
	
	/** Returns the primary match object */
	public PrimaryMatches getPrimaryMatches()
		{ return new PrimaryMatches(schema.getId(),getMatches()); }
}
