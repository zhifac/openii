// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.search;

import java.util.HashMap;
import java.util.List;

import org.mitre.harmony.model.AbstractManager;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.project.MappingListener;
import org.mitre.schemastore.model.MappingCell;
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

	/** Indicates if highlights should be shown across whole schema */
	private boolean highlightAll = true;
	
	/** Stores the left matches */
	private HashMap<Integer,SearchResult> leftMatches = new HashMap<Integer,SearchResult>();

	/** Stores the right matches */
	private HashMap<Integer,SearchResult> rightMatches = new HashMap<Integer,SearchResult>();

	/** Constructor used to initialize the selected info */
	public SearchManager(HarmonyModel harmonyModel)
		{ super(harmonyModel); }
	
	/** Returns the query for the specified side */
	public String getQuery(Integer side)
		{ return side.equals(HarmonyConsts.LEFT) ? leftQuery : rightQuery; }
	
	/** Returns the result for the specified element and side */
	public SearchResult getResult(Integer elementID, Integer side)
		{ return getMatches(side).get(elementID); }
	
	/** Returns the matches for the specified side */
	public HashMap<Integer,SearchResult> getMatches(Integer side)
		{ return side.equals(HarmonyConsts.LEFT) ? leftMatches : rightMatches; }
	
	/** Runs the specified query */
	public void runQuery(Integer side, String query)
	{
		// Set the query
		if(side.equals(HarmonyConsts.LEFT)) leftQuery = query; else rightQuery = query;
		
		// Retrieve the list of matches
		HashMap<Integer,SearchResult> matches = side==HarmonyConsts.LEFT ? leftMatches : rightMatches;
		matches.clear();
		
		// Only proceed with finding matches if keyword given
		if(!query.equals(""))
		{
			// Allows query to exist in the middle of words
			String searchTerm = ".*" + query + ".*";
			
			// Determines if the query should be case sensitive
			boolean caseSensitive = !query.toLowerCase().equals(query);
			if(!caseSensitive) searchTerm = "(?i)" + searchTerm;
				
			// Determine what elements match search criteria
			for(SchemaElement element : getModel().getProjectManager().getSchemaElements(side))
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
	
	// ------------- Indicates if the whole schema should be highlighted ------------

	/** Returns the preference for the highlighted area */
	public boolean getHighlightAll()
		{ return highlightAll; }
	
	/** Set preference for highlighted area */
	public void setHighlightAll(boolean highlightAll)
	{
		// Only set preference if changed from original
		if(highlightAll!=this.highlightAll)
		{
			this.highlightAll = highlightAll;
			for(SearchListener listener : getListeners())
				listener.highlightSettingChanged();
		}
	}
	
	//------------ Updates the selected information based on the occurrence of events ------------

	/** Reruns the queries if any mappings have been added */
	public void mappingAdded(Integer mappingID)
		{ runQuery(HarmonyConsts.LEFT, leftQuery); runQuery(HarmonyConsts.RIGHT, rightQuery); }

	/** Reruns the queries if any mappings have been removed */
	public void mappingRemoved(Integer mappingID)
		{ runQuery(HarmonyConsts.LEFT, leftQuery); runQuery(HarmonyConsts.RIGHT, rightQuery); }

	// Unused action events
	public void mappingVisibilityChanged(Integer mappingID) {}
	public void mappingCellsAdded(Integer mappingID, List<MappingCell> mappingCells) {}
	public void mappingCellsModified(Integer mappingID, List<MappingCell> oldMappingCells, List<MappingCell> newMappingCells) {}
	public void mappingCellsRemoved(Integer mappingID, List<MappingCell> mappingCells) {}
}
