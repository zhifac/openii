package org.mitre.openii.views.manager.menu;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorDescriptor;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.EditorManager;

/** Handles an action related to launching an editor */
public class EditorAction extends Action
{	
	/** Stores the menu manager to which this action is tied */
	private ManagerMenuManager menuManager;

	/** Stores the editor to display */
	private IEditorDescriptor editor;

	/** Constructs the editor action */
	EditorAction(ManagerMenuManager menuManager, String title, IEditorDescriptor editor)
	{
		this.menuManager = menuManager; this.editor = editor;
		setText(title);
		if(!title.equals("Open")) setImageDescriptor(OpenIIActivator.getImageDescriptor("icons/Editor.gif"));
	}

	/** Indicates if action is part of radio tag */
	public int getStyle()
		{ return getText().equals("Open") ? IAction.AS_UNSPECIFIED : IAction.AS_RADIO_BUTTON; }
	
	/** Launches the editor */
	public void run()
	{
		if(getText().equals("Open") || isChecked())
			EditorManager.launchEditor(editor.getId(), menuManager.getElement());
	}
}
