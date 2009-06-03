package org.mitre.openii.views.repositories.menu;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.Repository;
import org.mitre.openii.model.RepositoryManager;

/** Constructs the Delete Repository Dialog class */
public class DeleteRepositoryDialog
{
	static public void delete(Shell shell, Repository repository)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete repository '" + repository.getName() + "'?");
		if(confirmed) RepositoryManager.deleteRepository(repository);
	}
}