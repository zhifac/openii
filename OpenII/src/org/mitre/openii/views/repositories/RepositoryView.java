package org.mitre.openii.views.repositories;

import javax.swing.CellEditor;

import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;
import org.mitre.galaxy.model.server.SchemaStoreManager;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.RepositoryListener;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.views.repositories.menu.RepositoryMenuManager;
import org.mitre.schemastore.client.Repository;

/** Constructs the Repository View */
public class RepositoryView extends ViewPart implements RepositoryListener
{	
	/** Stores a reference to the viewer */
	private TreeViewer viewer = null;
	
	/** Constructs the Repository View */
	public RepositoryView()
		{ RepositoryManager.addListener(this); }
	
	/** Displays the Repository View */
	public void createPartControl(Composite parent)
	{
		// Create the tree viewer
/*		viewer = new TreeViewer(parent, SWT.CHECK);
		viewer = new TreeViewer(parent, SWT.NONE);
		viewer.setContentProvider(new RepositoryContentProvider());
		viewer.setLabelProvider(new RepositoryLabelProvider());
		viewer.setInput("");
		
		TreeItem item = viewer.getTree().getItem(0);
		TreeEditor editor = new TreeEditor(viewer.getTree());
		Text text = new Text(viewer.getTree(),SWT.NONE);
		text.setText("Text");
		editor.setEditor(text,item);
		
		// Add the context menu
		RepositoryMenuManager menuManager = new RepositoryMenuManager(viewer);
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
*/
		
		
		// Generate the tree for displaying available repositories
		final Tree tree = new Tree(parent, SWT.BORDER);
		RepositoryMenuManager menuManager = new RepositoryMenuManager(tree);
		Menu menu = menuManager.createContextMenu(tree);
		tree.setMenu(menu);
		
		// Create the header node for the repository tree
		TreeItem item = new TreeItem(tree, SWT.NONE);
		item.setImage(OpenIIActivator.getImage("Repositories.gif"));
		item.setText("Repositories");
		item.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		// Generate the nodes for the available repositories
		for(Repository repository : RepositoryManager.getRepositories())
		{
			TreeItem subItem = new TreeItem(item, SWT.CHECK);
			Button combo = new Button(tree, SWT.CHECK);
			combo.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			combo.setImage(OpenIIActivator.getImage("Repository.gif"));
			combo.setText(repository.getName());
			combo.setSelection(true);

			TreeEditor editor = new TreeEditor(tree); // tree_tasks in my tree
			editor.grabHorizontal = false;
editor.horizontalAlignment = SWT.LEFT;
			editor.setEditor(combo, subItem);
		}
	}
	
	// Sets the focus in this view
	public void setFocus() {}

	// Handles modifications of objects in the Repository tree
	public void repositoryAdded(Repository repository) { viewer.refresh(); }
	public void repositoryDeleted(Repository repository) { viewer.refresh(); }
	public void repositoryModified(Repository repository) { viewer.refresh(); }
}