package org.mitre.openii.views.repositories;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.RepositoryManager;
public class RepositoryAction extends Action
{
	// Constants defining the various Manager action types available
	static final int ADD_REPOSITORY = 0;
	static final int EDIT_REPOSITORY = 1;
	static final int REMOVE_REPOSITORY = 2;
	static final int COMPRESS_REPOSITORY = 3;
	
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
			case REMOVE_REPOSITORY: icon = "Delete.gif"; break;
			case COMPRESS_REPOSITORY: icon = "Compress.gif"; break;
		}		
		setImageDescriptor(OpenIIActivator.getImageDescriptor("icons/"+icon));
	}
	
	/** Runs the specified Manager action */
	public void run()
	{
		// Determine which tree element was selected
		Shell shell = menuManager.getMenu().getShell();
		
		/** Handles the adding of a repository */
		if(actionType == ADD_REPOSITORY)
			new EditRepositoryDialog(shell,null).open();
		
		/** Handles the editing of a repository */
		if(actionType == EDIT_REPOSITORY)
			new EditRepositoryDialog(shell,menuManager.getRepository()).open();
		
		/** Handles the deleting of a repository */
		if(actionType == REMOVE_REPOSITORY)
			DeleteRepositoryDialog.delete(shell,menuManager.getRepository());
		
		/** Handles the compressing of a repository */
		if(actionType == COMPRESS_REPOSITORY)
		{
			boolean success = false;
			try { success = RepositoryManager.getClient().compress(); } catch(Exception e) {}
			if(success) MessageDialog.openConfirm(shell, "Repository Compression", "The repository was successfully compressed!");
			else MessageDialog.openError(shell, "Repository Compression Failure", "The repository failed to be compressed!");
		}
	}
}