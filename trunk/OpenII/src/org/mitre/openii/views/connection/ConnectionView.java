package org.mitre.openii.views.connection;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/** Constructs the Connection View */
public class ConnectionView extends ViewPart
{
	/** Displays the Connection View */
	public void createPartControl(Composite parent)
	{
		TreeViewer viewer = new TreeViewer(parent);
		viewer.setContentProvider(new ConnectionContentProvider());
		viewer.setLabelProvider(new ConnectionLabelProvider());
		viewer.setInput("");
	//	viewer.getTree().addMouseListener(this);
	}
	
	// Sets the focus in this view
	public void setFocus() {}
}