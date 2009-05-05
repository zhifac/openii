package org.mitre.openii.views.manager;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIListener;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.menu.ManagerMenuManager;

/** Constructs the Manager View */
public class ManagerView extends ViewPart implements OpenIIListener, IDoubleClickListener
{	
	/** Stores a reference to the viewer */
	private TreeViewer viewer = null;
	
	/** Constructs the Manager View */
	public ManagerView()
		{ OpenIIManager.addListener(this); }
	
	/** Displays the Manager View */
	public void createPartControl(Composite parent)
	{
		// Create the tree viewer
		viewer = new TreeViewer(parent, SWT.SINGLE);
		viewer.setContentProvider(new ManagerContentProvider());
		viewer.setLabelProvider(new ManagerLabelProvider());
		viewer.setInput("");
		
		// Add the context menu
		ManagerMenuManager menuManager = new ManagerMenuManager(viewer);
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		viewer.addDoubleClickListener(this);
	}

	/** Handles the double clicking of an element */
	public void doubleClick(DoubleClickEvent e)
	{
		Object element = ((TreeSelection)e.getSelection()).getFirstElement();
		if(element instanceof GroupSchema) element = ((GroupSchema)element).getSchema();
		if(element instanceof MappingSchema) element = ((MappingSchema)element).getSchema();
		EditorManager.launchDefaultEditor(element);
	}
	
	// Handles modifications of objects in the Manager tree
	public void schemaAdded(Integer schemaID) { viewer.refresh(); }
	public void schemaModified(Integer schemaID) { viewer.refresh(); }
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