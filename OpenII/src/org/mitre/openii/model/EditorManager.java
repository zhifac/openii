package org.mitre.openii.model;

import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.mitre.openii.views.manager.SchemaInProject;
import org.mitre.openii.views.manager.SchemaInTag;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;

/**
 * Manages editor actions
 * @author CWOLF
 */
public class EditorManager
{
	/** Returns the type associated with the selected element */
	static public Class<?> getElementType(Object element)
	{
		if(element instanceof Schema || element instanceof SchemaInTag || element instanceof SchemaInProject) return Schema.class;
		if((element instanceof String && element.equals("All Schemas")) || element instanceof Tag) return Tag.class;
		if(element instanceof Project) return Project.class;
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
		if(element instanceof String && element.equals("All Schemas")) element = OpenIIManager.getSchemaIDs();			
		if(editor!=null) launchEditor(editor.getId(), element);
	}
}