package org.mitre.openii.views.ygg;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;
import org.mitre.openii.model.OpenIIListener;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.ygg.menu.YggMenuManager;

/** Constructs the Ygg View */
public class YggView extends ViewPart implements OpenIIListener
{	
	/** Stores a reference to the viewer */
	private TreeViewer viewer = null;
	
	/** Constructs the Ygg View */
	public YggView()
		{ OpenIIManager.addListener(this); }
	
	/** Displays the Ygg View */
	public void createPartControl(Composite parent)
	{
		// Create the tree viewer
		viewer = new TreeViewer(parent, SWT.SINGLE);
		viewer.setContentProvider(new YggContentProvider());
		viewer.setLabelProvider(new YggLabelProvider());
		viewer.setInput("");
		
		// Add the context menu
		YggMenuManager menuManager = new YggMenuManager(viewer);
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);	
	}

	// Handles modifications of objects in the Ygg tree
	public void schemaAdded(Integer schemaID) { viewer.refresh(); }
	public void schemaDeleted(Integer schemaID) { viewer.refresh(); }
	public void groupAdded(Integer groupID) { viewer.refresh(); }
	public void groupModified(Integer groupID) { viewer.refresh(); }
	public void groupDeleted(Integer groupID) { viewer.refresh(); }
	public void mappingAdded(Integer mappingID) { viewer.refresh(); }
	public void mappingModified(Integer schemaID) { viewer.refresh(); }
	public void mappingDeleted(Integer mappingID) { viewer.refresh(); }
	
	// Sets the focus in this view
	public void setFocus() {}
}