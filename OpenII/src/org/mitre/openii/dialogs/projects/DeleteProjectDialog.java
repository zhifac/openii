package org.mitre.openii.dialogs.projects;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Project;

/** Constructs the Delete Project Dialog class */
public class DeleteProjectDialog
{
	static public void delete(Shell shell, Project project)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete project '" + project.getName() + "'?");
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteProject(project.getId());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The project failed to be properly deleted");
		}
	}
}