package org.mitre.openii.dialogs.schemas;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Schema;

/** Constructs the Delete Schema Dialog class */
public class DeleteSchemaDialog
{
	static public void delete(Shell shell, Schema schema)
	{
		// Verify that the schema should be deleted
		String label = schema.getClass().getName().replaceAll(".*\\.","").toLowerCase();		
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete "+label+" '" + schema.getName() + "'?");

		// Delete the schema
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteSchema(schema.getId());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The "+label+" failed to be properly deleted");
		}
	}
}