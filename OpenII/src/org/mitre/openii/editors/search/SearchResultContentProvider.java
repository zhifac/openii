package org.mitre.openii.editors.search;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.search.RepositorySearchResult;

public class SearchResultContentProvider implements ITreeContentProvider
{
	/** Holds a reference to the search result */
	private RepositorySearchResult result;
	
	/** Constructs the content provider */
	public SearchResultContentProvider(RepositorySearchResult result)
		{ this.result = result; }
	
	/** Returns the children elements for the specified element */
	public Object[] getChildren(Object element)
	{
		// Returns matches subsumed by primary match
		if(element instanceof SchemaElement)
			return result.getPrimaryMatches().getSubsumedMatches((SchemaElement)element).toArray();

		// Return primary matches
		ArrayList<SchemaElement> primaryMatches = result.getPrimaryMatches().getPrimaryMatches();
		if(!element.equals("More..."))
		{
			ArrayList<Object> items = new ArrayList<Object>(primaryMatches);
			if(items.size()>5)
			{
				items = new ArrayList<Object>(items.subList(0,5));
				items.add(new String("More..."));				
			}
			return items.toArray();
		}
		return primaryMatches.subList(5,primaryMatches.size()).toArray();
	}

	/** Return the parent element for the specified element */
	public Object getParent(Object element)
		{ return null; }

	/** Indicates if the specified element has any children */
	public boolean hasChildren(Object element)
		{ return getChildren(element).length > 0; }

	/** Returns the list of elements to display for the specified element */
	public Object[] getElements(Object element)
		{ return getChildren(element); }

	// Unused functions
	public void dispose() {}
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {}
}