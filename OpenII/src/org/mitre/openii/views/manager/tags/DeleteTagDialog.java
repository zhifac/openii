package org.mitre.openii.views.manager.tags;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Tag;

/** Constructs the Delete Group Dialog class */
public class DeleteTagDialog
{
	static public void delete(Shell shell, Tag tag)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete group '" + tag.getName() + "' and all subgroups?");
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteTag(tag.getId());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The group failed to be properly deleted");
		}
	}
}