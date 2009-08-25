package org.mitre.openii.views.manager.schemas;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.DataSource;

/** Constructs the Delete Data Source Dialog class */
public class DeleteDataSourceDialog
{
	static public void delete(Shell shell, DataSource dataSource)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete data source '" + dataSource.getName() + "'?");
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteDataSource(dataSource.getId());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The data source failed to be properly deleted");
		}
	}
}