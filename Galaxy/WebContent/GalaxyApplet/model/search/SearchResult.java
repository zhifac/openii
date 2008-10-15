// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package model.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/** Class for storing a schema's search results */
public class SearchResult
{
	/** Stores the search result schema */
	private Schema schema;

	/** Stores the matches for this search result */
	private ArrayList<Match> matches;
	
	/** Stores hierarchy information about matches */
	private HashMap<Integer,ArrayList<Integer>> hierarchy = new HashMap<Integer,ArrayList<Integer>>();
	
	/** Constructs the search result */
	SearchResult(Schema schema, ArrayList<Match> matches, HierarchicalGraph graph)
	{
		this.schema = schema;
		this.matches = matches;
		
		// Constructs a match reference table
// TODO: Fix search results
		/* HashSet<Integer> matchRef = new HashSet<Integer>();
		for(Match match : matches)
			matchRef.add(match.getElementID());
		
		// Place each match in the correct spot in the hierarchy table
		for(Match match : matches)
		{
			ArrayList<Integer> parentIDs = new ArrayList<Integer>();

			// Find the parent associated with the matched domain
			if(match.getType()==SearchManager.DOMAIN)
			{
				Integer parentID = graph.getElementsForDomain(match.getElementID()).getId();
				if(matchRef.contains(parentID)) parentIDs.add(parentID);
				else parentIDs.add(getMatchParents(parentID,matchRef,graph));
			}
				
			// Find the parent associated with the matched schema element
			if(match.getType()!=SearchManager.SCHEMA)
				parentIDs.add(getMatchParents(match.getElementID(),matchRef,graph));
		}
*/
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
