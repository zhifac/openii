package org.mitre.openii.views.connection;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ConnectionContentProvider implements ITreeContentProvider
{
	/** Returns the children elements for the specified element */
	public Object[] getChildren(Object element)
	{
		if(element.equals(""))
			return new String[] {"Connections"};
	    if(element.equals("Connections"))
	    	return new String[] {"Connection1","Connection2","Connection3"};
	    return new String[] {};
	}

	/** Return the parent element for the specified element */
	public Object getParent(Object element)
	{
	    if(!element.equals("Connections"))
	    	return "Connections";
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
