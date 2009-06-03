package org.mitre.openii.views.repositories;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;
import org.mitre.openii.views.repositories.menu.RepositoryMenuManager;

/** Constructs the Repository View */
public class RepositoryView extends ViewPart
{
	/** Displays the Repository View */
	public void createPartControl(Composite parent)
	{
		// Create the tree viewer
		TreeViewer viewer = new TreeViewer(parent, SWT.CHECK);
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

		
		
	/*	// Generate the tree for displaying available repositories
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
		for(String connection : SchemaStoreManager.getConnections())
		{
			TreeItem subItem = new TreeItem(item, SWT.CHECK);
			TreeEditor editor = new TreeEditor(tree); // tree_tasks in my tree
			Button combo = new Button(tree, SWT.CHECK);
			combo.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			combo.setImage(OpenIIActivator.getImage("Repository.gif"));
			combo.setText(connection);
			combo.setSelection(true);
			editor.grabHorizontal = true;
			editor.setEditor(combo, subItem, 0);
		}*/
	}
	
	// Sets the focus in this view
	public void setFocus() {}
}