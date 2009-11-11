package org.mitre.openii.model;

import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.mitre.openii.views.manager.SchemaInTag;
import org.mitre.openii.views.manager.SchemaInMapping;
import org.mitre.schemastore.model.Tag;
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
		if(element instanceof Schema || element instanceof SchemaInTag || element instanceof SchemaInMapping) return Schema.class;
		if(element instanceof Tag) return Tag.class;
		if(element instanceof Mapping) return Mapping.class;
		return null;
	}
	
	/** Returns the editor type associated with the selected element */
	static public String getEditorType(Object element)
	{
		Class<?> elementType = getElementType(element);
		return elementType==null ? "" : "." + getElementType(element).toString().toLowerCase();
	}

	/** Launches the specified editor */
	static public void launchEditor(String editorID, Object element)
	{
		try {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			page.openEditor(new EditorInput(element),editorID);
		}
		catch(Exception e) { System.err.println(e.getMessage()); e.printStackTrace();}
	}
	
	/** Launches the default editor */
	static public void launchDefaultEditor(Object element)
	{
		IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
		IEditorDescriptor editor = registry.getDefaultEditor(EditorManager.getEditorType(element));
		if(editor!=null) launchEditor(editor.getId(), element);
	}
}