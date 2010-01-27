package org.mitre.openii.views.manager.mappings;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Mapping;

/** Constructs the Delete Mapping Dialog class */
public class DeleteMappingDialog
{
	static public void delete(Shell shell, Mapping mapping)
	{
		String source = OpenIIManager.getSchema(mapping.getSourceId()).getName();
		String target = OpenIIManager.getSchema(mapping.getTargetId()).getName();
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete mapping between '" + source + "' and '" + target + "'?");
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteMapping(mapping.getId());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The mapping failed to be properly deleted");
		}
	}
}