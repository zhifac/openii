package org.mitre.openii.views.repositories;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.mitre.openii.application.OpenIIActivator;

public class RepositoryLabelProvider implements ILabelProvider
{
	/** Returns the image associated with the specified element */
	public Image getImage(Object element)
	{
		if(element.equals("Repositories")) return OpenIIActivator.getImage("Repositories.gif");
		else return OpenIIActivator.getImage("Repository.gif");
	}

	/** Returns the name associated with the specified element */
	public String getText(Object element)
		{ return element.toString(); }

	/** Indicates that the label is not influenced by an element property */
	public boolean isLabelProperty(Object element, String property) { return false; }
	
	// Unused functions
	public void addListener(ILabelProviderListener listener) {}
	public void dispose() {}
	public void removeListener(ILabelProviderListener listener) {}
}
