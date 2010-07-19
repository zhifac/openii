package org.mitre.openii.model;

import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.mitre.openii.views.manager.ManagerView;
import org.mitre.openii.views.manager.SchemaInProject;
import org.mitre.openii.views.manager.SchemaInTag;
import org.mitre.openii.views.manager.VocabularyInProject;
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
	/** Enumeration of editor types */
	static public enum EditorType {SCHEMA,TAG,PROJECT,MAPPING,VOCABULARY};

	/** Returns the editor ID associated with the selected element */
	static public String getEditorType(Object element)
	{ 
		EditorType type = null;
		if(element instanceof Schema || element instanceof SchemaInTag || element instanceof SchemaInProject) type = EditorType.SCHEMA;
		if((element instanceof String && element.equals(ManagerView.ALL_SCHEMAS_HEADER)) || element instanceof Tag) type = EditorType.TAG;
		if(element instanceof Project) type = EditorType.PROJECT;
		if(element instanceof Mapping) type = EditorType.MAPPING;
		if(element instanceof VocabularyInProject) type = EditorType.VOCABULARY;
		return type==null ? null : "." + type.toString().toLowerCase();
	}
	
	/** Launches the specified editor */
	static public void launchEditor(String editorID, Object element)
	{
		String editorType = getEditorType(element);
		
		// Swap out the "All Schemas" reference for a list of all schemas
		if(element instanceof String && element.equals(ManagerView.ALL_SCHEMAS_HEADER))
			element = OpenIIManager.getSchemaIDs();
		
		// Launch the editor
		try {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			page.openEditor(new EditorInput(editorType, element),editorID);
		}
		catch(Exception e) { System.err.println(e.getMessage()); e.printStackTrace();}
	}
	
	/** Launches the default editor */
	static public void launchDefaultEditor(Object element)
	{
		String editorType = EditorManager.getEditorType(element);
		IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
		IEditorDescriptor editor = registry.getDefaultEditor(editorType);
		if(editor!=null) launchEditor(editor.getId(), element);
	}
}