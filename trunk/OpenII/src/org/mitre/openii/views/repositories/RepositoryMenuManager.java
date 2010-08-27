package org.mitre.openii.views.repositories;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.mitre.schemastore.client.Repository;

/** Handles the displaying of the Repository pop-up menu */
public class RepositoryMenuManager extends MenuManager implements IMenuListener
{
	/** Stores reference to the currently selected repository */
	private Repository repository = null;
	
	/** Constructs the Repository Menu Manager */
	public RepositoryMenuManager(Repository repository)
	{
		this.repository = repository;
		setRemoveAllWhenShown(true);
		addMenuListener(this);
	}

	/** Returns the selected repository */
	public Repository getRepository()
		{ return repository; }
	
	/** Generates the action menu */
	private void getManagerMenu(IMenuManager menuManager)
	{
		// Display the menu for the "Repositories" header
		if(repository==null)
			menuManager.add(new RepositoryAction(this,"Add Repository",RepositoryAction.ADD_REPOSITORY));
			
		// Display the menu for a selected repository
		else if(repository instanceof Repository)
		{
			menuManager.add(new RepositoryAction(this,"Edit Repository",RepositoryAction.EDIT_REPOSITORY));
			menuManager.add(new RepositoryAction(this,"Disconnect Repository",RepositoryAction.DISCONNECT_REPOSITORY));
			menuManager.add(new Separator());
			menuManager.add(new RepositoryAction(this,"Compress Repository",RepositoryAction.COMPRESS_REPOSITORY));
		}
	}
	
	/** Generate the context menu before being displayed */
	public void menuAboutToShow(IMenuManager menuManager)
		{ getManagerMenu(menuManager); }
}