// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.search;

import java.util.HashMap;

import org.mitre.harmony.model.AbstractManager;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.mapping.MappingListener;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Manages searches run within Harmony
 */
public class SearchManager extends AbstractManager<SearchListener> implements MappingListener
{
	/** Stores the left query */
	private String leftQuery = "";
	
	/** Stores the right query */
	private String rightQuery = "";
	
	/** Stores the left matches */
	private HashMap<Integer,SearchResult> leftMatches = new HashMap<Integer,SearchResult>();

	/** Stores the right matches */
	private HashMap<Integer,SearchResult> rightMatches = new HashMap<Integer,SearchResult>();

	/** Constructor used to initialize the selected info */
	public SearchManager(HarmonyModel harmonyModel)
		{ super(harmonyModel); }
	
	/** Returns the query for the specified side */
	public String getQuery(Integer side)
		{ return side.equals(MappingSchema.LEFT) ? leftQuery : rightQuery; }
	
	/** Returns the result for the specified element and side */
	public SearchResult getResult(Integer elementID, Integer side)
		{ return getMatches(side).get(elementID); }
	
	/** Returns the matches for the specified side */
	public HashMap<Integer,SearchResult> getMatches(Integer side)
		{ return side.equals(MappingSchema.LEFT) ? leftMatches : rightMatches; }
	
	/** Runs the specified query */
	public void runQuery(Integer side, String query)
	{
		// Set the query
		if(side.equals(MappingSchema.LEFT)) leftQuery = query; else rightQuery = query;
		
		// Retrieve the list of matches
		HashMap<Integer,SearchResult> matches = side==MappingSchema.LEFT ? leftMatches : rightMatches;
		matches.clear();
		
		// Only proceed with finding matches if keyword given
		if(!query.equals(""))
		{
			boolean caseSensitive = false;
			
			// Modify search term to computer format
			String searchTerm = "*" + query + "*";
			for(int i=0; i<searchTerm.length(); i++)
			{
				if(searchTerm.charAt(i)=='*')
				{
					searchTerm = ((i>0)?searchTerm.substring(0,i):"") + ".*" +
						((i<searchTerm.length()-1)?searchTerm.substring(i+1,searchTerm.length()):"");
					i++;
				}
				else if(Character.isUpperCase(searchTerm.charAt(i)))
					caseSensitive = true;
			}
			if(!caseSensitive) searchTerm = "(?i)" + searchTerm;
				
			// Determine what elements match search criteria
			for(SchemaElement element : getModel().getMappingManager().getSchemaElements(side))
			{				
				// Check to see if element name matches search criteria
				String name = element.getName();
				boolean nameMatched = name.matches(searchTerm);
				
				// Check to see if element description matches search criteria
				String description = element.getDescription();
				boolean descriptionMatched = description.matches(searchTerm);

				// Stores a new search result if needed
				if(nameMatched || descriptionMatched)
					matches.put(element.getId(), new SearchResult(nameMatched,descriptionMatched));
			}
		}
		
		// Select all tree nodes which match search criteria
		for(SearchListener listener : getListeners())
			listener.searchResultsModified(side);
	}
	
	//------------ Updates the selected information based on the occurrence of events ------------

	/** Reruns the queries if the selected schemas have been modified */
	public void mappingModified() {}
		{ runQuery(MappingSchema.LEFT, leftQuery); runQuery(MappingSchema.RIGHT, rightQuery); }

	// Unused action events
	public void schemaAdded(Integer schemaID) {}
	public void schemaModified(Integer schemaID) {}
	public void schemaRemoved(Integer schemaID) {}
}
