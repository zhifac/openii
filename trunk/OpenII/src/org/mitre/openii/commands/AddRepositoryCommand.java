package org.mitre.openii.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.views.repositories.EditRepositoryDialog;

public class AddRepositoryCommand extends AbstractHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		new EditRepositoryDialog(null,null).open();
		return null;
	}

}
