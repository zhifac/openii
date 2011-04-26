package org.mitre.openii.views.manager;

import java.util.ArrayList;

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
	/** Stores constants for various tree labels */
	static public final String SCHEMAS_HEADER = "Schemas";
	static public final String ALL_SCHEMAS_HEADER = "All Schemas";
	static public final String THESAURI_HEADER = "Thesauri";
	static public final String PROJECTS_HEADER = "Projects";
	
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
		{ EditorManager.launchDefaultEditor(((TreeSelection)e.getSelection()).getFirstElement()); }
	
	// Handles modifications of objects in the Manager tree
	public void repositoryReset() { viewer.refresh(); }
	public void schemaAdded(Integer schemaID) { viewer.refresh(); }
	public void schemaModified(Integer schemaID) { viewer.refresh(); }
	public void schemaDeleted(Integer schemaID) { viewer.refresh(); }
	public void dataSourceAdded(Integer dataSourceID) { viewer.refresh(); }
	public void dataSourceDeleted(Integer dataSourceID) { viewer.refresh(); }
	public void tagAdded(Integer tagID) { viewer.refresh(); }
	public void tagModified(Integer tagID) { viewer.refresh(); }
	public void tagDeleted(Integer tagID) { viewer.refresh(); }
	public void projectAdded(Integer mappingID) { viewer.refresh(); }
	public void projectModified(Integer schemaID) { viewer.refresh(); }
	public void projectDeleted(Integer mappingID) { viewer.refresh(); }
	public void projectsMerged(ArrayList<Integer> projectIDs, Integer mergedProjectID) { viewer.refresh(); }
	public void mappingAdded(Integer mappingID) { viewer.refresh(); }
	public void mappingDeleted(Integer mappingID) { viewer.refresh(); }
	public void mappingModified(Integer mappingID) { viewer.refresh(); }
	public void vocabularyAdded(Integer projectID) { viewer.refresh(); }
	public void vocabularyModified(Integer projectID) { viewer.refresh(); }
	public void vocabularyDeleted(Integer projectID) { viewer.refresh(); }
	
	// Sets the focus in this view
	public void setFocus() {}
}