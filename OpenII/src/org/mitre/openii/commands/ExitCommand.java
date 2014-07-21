package org.mitre.openii.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;

public class ExitCommand extends AbstractHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		dispose();
		PlatformUI.getWorkbench().close();
		System.exit(0);
		return null;
	}	
}
