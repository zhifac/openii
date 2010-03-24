package org.mitre.openii.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ExitCommand extends AbstractHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		dispose();
		System.exit(0);
		return null;
	}
}
