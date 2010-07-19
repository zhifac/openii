package org.mitre.openii.dialogs.vocabulary;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.VocabularyInProject;

/** Constructs the Delete Vocabulary Dialog class */
public class DeleteVocabularyDialog
{
	static public void delete(Shell shell, VocabularyInProject vocabulary)
	{
		boolean confirmed = MessageDialog.openConfirm(shell, "Confirm Delete", "Are you sure you want to delete vocabulary?");
		if(confirmed)
		{
			boolean success = OpenIIManager.deleteVocabulary(vocabulary.getProjectID());
			if(!success) MessageDialog.openError(shell, "Deletion Failure", "The vocabulary failed to be properly deleted");
		}
	}
}