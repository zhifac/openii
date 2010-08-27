package org.mitre.openii.views.repositories;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.RepositoryManager;
import org.mitre.schemastore.client.Repository;

/** Constructs the Disconnect Repository Dialog class */
public class DisconnectRepositoryDialog
{
	static public void disconnect(Shell shell, Repository repository)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Disconnection", "Are you sure you want to disconnect repository '" + repository.getName() + "'?");
		if(confirmed) RepositoryManager.disconnectRepository(repository);
	}
}