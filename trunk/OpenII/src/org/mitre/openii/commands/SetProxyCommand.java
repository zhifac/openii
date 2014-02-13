package org.mitre.openii.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.mitre.openii.views.manager.SetProxyDialog;

public class SetProxyCommand extends AbstractHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		new SetProxyDialog(null).open();
		return null;
	}

}
