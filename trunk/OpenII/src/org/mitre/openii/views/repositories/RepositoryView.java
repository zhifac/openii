package org.mitre.openii.views.repositories;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/** Constructs the Repository View */
public class RepositoryView extends ViewPart
{
	/** Displays the Repository View */
	public void createPartControl(Composite parent)
	{
		TreeViewer viewer = new TreeViewer(parent);
		viewer.setContentProvider(new RepositoryContentProvider());
		viewer.setLabelProvider(new RepositoryLabelProvider());
		viewer.setInput("");
	//	viewer.getTree().addMouseListener(this);
	}
	
	// Sets the focus in this view
	public void setFocus() {}
}