// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.search;

import java.util.HashMap;

import org.mitre.harmony.model.AbstractManager;
import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.selectedInfo.SelectedInfoListener;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Manages searches run within Harmony
 */
public class SearchManager extends AbstractManager<SearchListener> implements SelectedInfoListener
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
	
	/** Returns the result for the specified element */
	public SearchResult getResult(Integer elementID, Integer side)
		{ return getMatches(side).get(elementID); }
	
	/** Returns the matches for the specified side */
	public HashMap<Integer,SearchResult> getMatches(Integer side)
		{ return side.equals(HarmonyConsts.LEFT) ? leftMatches : rightMatches; }
	
	/** Runs the specified query */
	public void runQuery(Integer side, String query)
	{
		// Retrieve the list of matches
		HashMap<Integer,SearchResult> matches = side==HarmonyConsts.LEFT ? leftMatches : rightMatches;
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
				
			// Determine what elements match search criteria
			for(SchemaElement element : getModel().getSelectedInfo().getSchemaElements(side))
			{				
				// Check to see if element name matches search criteria
				String name = element.getName();
				if(!caseSensitive) name = name.toLowerCase();
				boolean nameMatched = name.matches(searchTerm);
				
				// Check to see if element description matches search criteria
				String description = element.getDescription();
				if(!caseSensitive) description = description.toLowerCase();
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
	public void selectedSchemasModified()
		{ runQuery(HarmonyConsts.LEFT, leftQuery); runQuery(HarmonyConsts.RIGHT, rightQuery); }

	// Unused action events
	public void displayedElementModified(Integer role) {}
	public void selectedElementsModified(Integer role) {}
	public void selectedMappingCellsModified() {}
}
