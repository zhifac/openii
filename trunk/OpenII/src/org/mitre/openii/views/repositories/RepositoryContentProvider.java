package org.mitre.openii.views.repositories;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.mitre.openii.model.Repository;
import org.mitre.openii.model.RepositoryManager;

public class RepositoryContentProvider implements ITreeContentProvider
{
	/** Returns the children elements for the specified element */
	public Object[] getChildren(Object element)
	{
		if(element.equals(""))
			return new String[] {"Repositories"};
	    if(element.equals("Repositories"))
	    	return RepositoryManager.getRepositories().toArray(new Repository[0]);
	    return new String[] {};
	}

	/** Return the parent element for the specified element */
	public Object getParent(Object element)
	{
	    if(!element.equals("Repositories"))
	    	return "Repositories";
	    return null;
	}

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
