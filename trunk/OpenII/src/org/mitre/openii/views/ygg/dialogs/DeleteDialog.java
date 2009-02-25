package org.mitre.openii.views.ygg.dialogs;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Constructs the Delete Schema Dialog class */
public class DeleteDialog
{
	/** Function for deleting the specified schema */
	static public void delete(Shell shell, Schema schema)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete schema '" + schema.getName() + "'?");
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteSchema(schema.getId());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The schema failed to be properly deleted");
		}
	}

	/** Function for deleting the specified group */
	static public void delete(Shell shell, Group group)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete group '" + group.getName() + "' and all subgroups?");
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteGroup(group.getId());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The group failed to be properly deleted");
		}
	}

	/** Function for deleting the specified mapping */
	static public void delete(Shell shell, Mapping mapping)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete mapping '" + mapping.getName() + "'?");
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteMapping(mapping.getId());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The mapping failed to be properly deleted");
		}
	}
}