package org.mitre.openii.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.mitre.openii.model.EditorManager;

/** Class for implementing the AboutOpenII command */
public class AboutOpenIICommand extends AbstractHandler
{
	/** Executes the AboutOpenII command by displaying the AboutOpenII Editor */
	public Object execute(ExecutionEvent event) throws ExecutionException
		{ EditorManager.launchEditor("AboutOpenIIEditor", null); return null; }
}
