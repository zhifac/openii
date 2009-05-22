package org.mitre.openii.views.manager.groups;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Group;

/** Constructs the Delete Group Dialog class */
public class DeleteGroupDialog
{
	static public void delete(Shell shell, Group group)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete group '" + group.getName() + "' and all subgroups?");
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteGroup(group.getId());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The group failed to be properly deleted");
		}
	}
}