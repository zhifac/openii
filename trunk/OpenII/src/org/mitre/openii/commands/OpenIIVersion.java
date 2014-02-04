package org.mitre.openii.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;


public class OpenIIVersion extends AbstractHandler{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// customized MessageDialog with configured buttons
		MessageDialog.openInformation(null, "OpenII Version",
				"OpenII Version: " + Platform.getProduct().getDefiningBundle().getVersion().toString());
		return null;
	}
}
