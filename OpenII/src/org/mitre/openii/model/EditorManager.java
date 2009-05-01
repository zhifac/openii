package org.mitre.openii.model;

import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.mitre.openii.views.manager.GroupSchema;
import org.mitre.openii.views.manager.MappingSchema;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/**
 * Manages editor actions
 * @author CWOLF
 */
public class EditorManager
{
	/** Returns the type associated with the selected element */
	static public Class<?> getElementType(Object element)
	{
		if(element instanceof Schema || element instanceof GroupSchema || element instanceof MappingSchema) return Schema.class;
		if(element instanceof Group) return Group.class;
		if(element instanceof Mapping) return Mapping.class;
		return null;
	}
	
	/** Returns the editor type associated with the selected element */
	static public String getEditorType(Object element)
	{
		Class<?> elementType = getElementType(element);
		return elementType==null ? "" : "." + getElementType(element).toString().toLowerCase();
	}

	/** Launches the editor */
	static public void launchEditor(String editorID, Object element)
	{
		// Set the editor as the default editor
		IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
		registry.setDefaultEditor(getEditorType(element), editorID);

		// Display the editor
		try {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			page.openEditor(new EditorInput(element),editorID);
		}
		catch(Exception e) { System.err.println(e.getMessage()); e.printStackTrace();}
	}
}