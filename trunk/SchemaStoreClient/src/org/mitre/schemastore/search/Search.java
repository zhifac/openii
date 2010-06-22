// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.search;

import java.util.HashMap;

import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.schemaInfo.HierarchicalSchemaInfo;

/**
 * Manages searches run within Harmony
 */
public class Search
{
	/** Runs the query on the specified schema */
	public static HashMap<Integer,SearchResult> runQuery(String query, HierarchicalSchemaInfo schema)
	{		
		HashMap<Integer,SearchResult> results = new HashMap<Integer,SearchResult>();
		
		// Only proceed with finding matches if keyword given
		if(!query.equals(""))
		{
			// Allows query to exist in the middle of words
			String searchTerm = ".*" + query + ".*";
			
			// Determines if the query should be case sensitive
			boolean caseSensitive = !query.toLowerCase().equals(query);
			if(!caseSensitive) searchTerm = "(?i)" + searchTerm;
				
			// Determine what elements match search criteria
			for(SchemaElement element : schema.getHierarchicalElements())
			{				
				// Check to see if element name matches search criteria
				String name = element.getName();
				boolean nameMatched = name.matches(searchTerm);
				
				// Check to see if element description matches search criteria
				String description = element.getDescription();
				boolean descriptionMatched = description.matches(searchTerm);

				// Stores a new search result if needed
				if(nameMatched || descriptionMatched)
					results.put(element.getId(), new SearchResult(nameMatched,descriptionMatched));
			}
		}

		return results;
	}	
}
