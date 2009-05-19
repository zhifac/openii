// � The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model.search;

/**
 * Interface used by all Harmony search listeners
 * @author CWOLF
 */
public interface SearchListener
{
	/** Indicates that the search results have been modified */
	public void searchResultsModified(Integer side);
}
