package org.mitre.openii.views.repositories;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.RepositoryListener;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.openii.views.repositories.menu.RepositoryMenuManager;
import org.mitre.openii.widgets.WidgetUtilities;
import org.mitre.schemastore.client.Repository;

/** Constructs the Repository View */
public class RepositoryView extends ViewPart implements RepositoryListener
{	
	/** Stores the tree displayed in the repository view */
	private Tree tree;

	/** Refreshes the displayed repositories */
	private void updateRepositoryItems()
	{
		// Remove all old repository items
		for(TreeItem item : tree.getItem(0).getItems())
			((Button)item.getData()).dispose();
		tree.getItem(0).removeAll();
		
		// Generate the nodes for the available repositories
		for(Repository repository : WidgetUtilities.sortList(RepositoryManager.getRepositories()))
		{
			// Adds a checkbox to the repository tree item
			Button checkbox = new Button(tree, SWT.CHECK);
			checkbox.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			checkbox.setImage(OpenIIActivator.getImage("Repository.gif"));
			checkbox.setText(repository.getName());
			checkbox.setData(repository);
			checkbox.setSelection(true);
			checkbox.setMenu(new RepositoryMenuManager(repository).createContextMenu(tree));
			
			// Places the checkbox next to the tree item
			TreeItem repositoryItem = new TreeItem(tree.getItem(0), SWT.CHECK);
			repositoryItem.setData(checkbox);
			TreeEditor editor = new TreeEditor(tree);
			editor.grabHorizontal = true;
			editor.setEditor(checkbox, repositoryItem);			
		}
	}
	
	/** Constructs the Repository View */
	public RepositoryView()
		{ RepositoryManager.addListener(this); }

	/** Displays the Repository View */
	public void createPartControl(Composite parent)
	{
		// Generate the tree for displaying available repositories
		tree = new Tree(parent, SWT.BORDER);
		RepositoryMenuManager menuManager = new RepositoryMenuManager(null);
		Menu menu = menuManager.createContextMenu(tree);
		tree.setMenu(menu);
		
		// Create the header node for the repository tree
		TreeItem item = new TreeItem(tree, SWT.NONE);
		item.setImage(OpenIIActivator.getImage("Repositories.gif"));
		item.setText("Repositories");
		item.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));

		// Updates the repository items
		updateRepositoryItems();
	}

	/** Adds a repository to the repository list */
	public void repositoryAdded(Repository repository)
		{ updateRepositoryItems(); }
	
	/** Removes a repository from the repository list */
	public void repositoryDeleted(Repository repository)
		{ updateRepositoryItems(); }
	
	// Sets the focus in this view
	public void setFocus() {}
}