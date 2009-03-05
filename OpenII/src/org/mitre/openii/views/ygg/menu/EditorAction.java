package org.mitre.openii.views.ygg.menu;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.mitre.openii.application.OpenIIActivator;

/** Handles an action related to launching an editor */
public class EditorAction extends Action
{	
	/** Stores the menu manager to which this action is tied */
	private YggMenuManager menuManager;

	/** Stores the editor to display */
	private IEditorDescriptor editor;

	/** Constructs the editor action */
	EditorAction(YggMenuManager menuManager, String title, IEditorDescriptor editor)
	{
		this.menuManager = menuManager; this.editor = editor;
		setText(title);
		if(!title.equals("Open")) setImageDescriptor(OpenIIActivator.getImageDescriptor("icons/Editor.gif"));
	}

	/** Indicates if action is part of radio group */
	public int getStyle()
		{ return getText().equals("Open") ? IAction.AS_UNSPECIFIED : IAction.AS_RADIO_BUTTON; }
	
	/** Launches the editor */
	public void run()
	{
		// Only launch the editor if truly selected
		if(getText().equals("Open") || isChecked())
		{
			// Set the editor as the default editor
			IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
			registry.setDefaultEditor(menuManager.getEditorType(), editor.getId());

			// Display the editor
			try {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = window.getActivePage();
				page.openEditor(new ElementInput(menuManager.getElement()),editor.getId());
			}
			catch(Exception e) { System.out.println(e.getMessage()); }
		}
	}
}
