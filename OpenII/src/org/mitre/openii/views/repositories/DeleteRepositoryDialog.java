package org.mitre.openii.views.repositories;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.client.Repository;

/** Constructs the Delete Repository Dialog class */
public class DeleteRepositoryDialog
{
	static public void delete(Shell shell, Repository repository)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete repository '" + repository.getName() + "'?");
		if(confirmed) RepositoryManager.deleteRepository(repository);
	}
}