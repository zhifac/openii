package org.mitre.openii.views.repositories;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
public class RepositoryAction extends Action
{
	// Constants defining the various Manager action types available
	static final int ADD_REPOSITORY = 0;
	static final int EDIT_REPOSITORY = 1;
	static final int DELETE_REPOSITORY = 2;
	
	/** Stores the menu manager to which this action is tied */
	private RepositoryMenuManager menuManager;
	
	/** Stores the action type */
	private Integer actionType;
	
	/** Constructs the Manager action */
	RepositoryAction(RepositoryMenuManager menuManager, String title, Integer actionType)
	{
		// Set the title and action type
		this.menuManager = menuManager;
		this.actionType = actionType;
		setText(title);

		// Set the action icon
		String icon = null;
		switch(actionType)
		{
			case ADD_REPOSITORY: icon = "Repository.gif"; break;
			case EDIT_REPOSITORY: icon = "Edit.gif"; break;
			case DELETE_REPOSITORY: icon = "Delete.gif"; break;
		}		
		setImageDescriptor(OpenIIActivator.getImageDescriptor("icons/"+icon));
	}
	
	/** Runs the specified Manager action */
	public void run()
	{
		// Determine which tree element was selected
		Shell shell = menuManager.getMenu().getShell();
		
		/** Handles the importing of a schema */
		if(actionType == ADD_REPOSITORY)
			new EditRepositoryDialog(shell,null).open();
		
		/** Handles the extending of a schema */
		if(actionType == EDIT_REPOSITORY)
			new EditRepositoryDialog(shell,menuManager.getRepository()).open();
		
		/** Handles the extending of a schema */
		if(actionType == DELETE_REPOSITORY)
			DeleteRepositoryDialog.delete(shell,menuManager.getRepository());
	}
}