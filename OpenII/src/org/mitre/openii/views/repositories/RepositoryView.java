package org.mitre.openii.views.repositories;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
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
public class RepositoryView extends ViewPart implements RepositoryListener, SelectionListener
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
			// Generate a repository selector checkbox
			Button repositorySelector = new Button(tree, SWT.CHECK);
			repositorySelector.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			repositorySelector.setImage(OpenIIActivator.getImage("Repository.gif"));
			repositorySelector.setText(repository.getName());
			repositorySelector.setData(repository);
			repositorySelector.setMenu(new RepositoryMenuManager(repository).createContextMenu(tree));
			repositorySelector.addSelectionListener(this);
			
			// Generate a tree item for displaying the repository selector
			TreeItem repositoryItem = new TreeItem(tree.getItem(0), SWT.NONE);
			repositoryItem.setData(repositorySelector);
			
			// Display the repository selector
			TreeEditor editor = new TreeEditor(tree);
			editor.grabHorizontal = true;
			editor.setEditor(repositorySelector, repositoryItem);			
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

		// Generate a repository label
		Composite repositoriesLabel = new Composite(tree, SWT.NONE);
		repositoriesLabel.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2; layout.marginHeight = 0; layout.marginWidth = 0;
		repositoriesLabel.setLayout(layout);

		// Add the image to the repository label
		Label image = new Label(repositoriesLabel, SWT.NONE);
		image.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		image.setImage(OpenIIActivator.getImage("Repositories.gif"));
		image.setMenu(new RepositoryMenuManager(null).createContextMenu(tree));

		// Add the label to the repository label
		Label label = new Label(repositoriesLabel, SWT.NONE);
		label.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		label.setText("Repositories");
		label.setMenu(new RepositoryMenuManager(null).createContextMenu(tree));

		// Create the header node for the repository tree
		TreeEditor editor = new TreeEditor(tree);
		editor.grabHorizontal = true;
		editor.setEditor(repositoriesLabel, new TreeItem(tree, SWT.NONE));			

		// Updates the repository items
		updateRepositoryItems();
	}

	/** Adds a repository to the repository list */
	public void repositoryAdded(Repository repository)
		{ updateRepositoryItems(); }
	
	/** Removes a repository from the repository list */
	public void repositoryDeleted(Repository repository)
		{ updateRepositoryItems(); }

	/** Handles the selection of a repository */
	public void widgetSelected(SelectionEvent e)
	{
		for(TreeItem item : tree.getItem(0).getItems())
		{
			Button checkbox = (Button)item.getData();
			if(!e.getSource().equals(checkbox))
				checkbox.setSelection(false);
		}
	}
	
	// Sets the focus in this view
	public void setFocus() {}

	// Unused event listeners
	public void widgetDefaultSelected(SelectionEvent e) {}
}