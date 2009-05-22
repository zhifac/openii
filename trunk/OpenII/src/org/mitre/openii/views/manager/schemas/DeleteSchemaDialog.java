package org.mitre.openii.views.manager.schemas;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Schema;

/** Constructs the Delete Schema Dialog class */
public class DeleteSchemaDialog
{
	static public void delete(Shell shell, Schema schema)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete schema '" + schema.getName() + "'?");
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteSchema(schema.getId());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The schema failed to be properly deleted");
		}
	}
}