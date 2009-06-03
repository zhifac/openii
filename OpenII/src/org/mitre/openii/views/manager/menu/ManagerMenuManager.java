package org.mitre.openii.views.manager.menu;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.PlatformUI;
import org.mitre.openii.model.EditorManager;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.GroupSchema;
import org.mitre.openii.views.manager.MappingSchema;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Handles the displaying of the Manager popup menu */
public class ManagerMenuManager extends MenuManager implements IMenuListener
{
	/** Stores reference to the tree viewer */
	private TreeViewer viewer = null;

	/** Stores reference to the currently selected element */
	private Object element = null;
	
	/** Constructs the Manager Menu Manager */
	public ManagerMenuManager(TreeViewer viewer)
	{
		this.viewer = viewer;
		setRemoveAllWhenShown(true);
		addMenuListener(this);
	}
	
	/** Returns the selected element */
	public Object getElement()
		{ return element; }
	
	/** Returns the id associated with the selected element */
	public Integer getElementID()
	{
		if(element instanceof Schema) return ((Schema)element).getId();
		if(element instanceof GroupSchema) return ((GroupSchema)element).getSchema().getId();
		if(element instanceof MappingSchema) return ((MappingSchema)element).getSchema().getId();
		if(element instanceof Group) return ((Group)element).getId();
		if(element instanceof Mapping) return ((Mapping)element).getId();
		return null;
	}
	
	/** Generates the editor menu */
	private void getEditorMenu(IMenuManager menuManager)
	{
		// Retrieve the editor registry
		IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
		
		// Retrieve the list of available editors
		IEditorDescriptor editors[] = registry.getEditors(EditorManager.getEditorType(element));
		if(editors.length > 0)
		{
			// Display the menu for the default editor
			IEditorDescriptor defaultEditor = registry.getDefaultEditor(EditorManager.getEditorType(element));			
			menuManager.add(new EditorAction(this,"Open",defaultEditor));
			
			// Display the menu to select an editor
			if(editors.length > 1)
			{
				MenuManager editorMenu = new MenuManager("Open With");
				for(IEditorDescriptor editor : editors)
				{
					EditorAction action = new EditorAction(this,editor.getLabel(),editor);
					if(editor.equals(defaultEditor)) action.setChecked(true);
					editorMenu.add(action);
				}
				menuManager.add(editorMenu);
			}
		}
	}
	
	/** Generates the action menu */
	private void getManagerMenu(IMenuManager menuManager)
	{
		// Display the menu for the "Schemas" header
		if(element instanceof String && element.equals("Schemas"))
			menuManager.add(new ManagerAction(this,"Import Schema",ManagerAction.IMPORT_SCHEMA));
			
		// Display the menu for a selected schema
		if(element instanceof Schema)
		{
			menuManager.add(new ManagerAction(this,"Edit Schema Properties",ManagerAction.EDIT_SCHEMA));
			menuManager.add(new ManagerAction(this,"Extend Schema",ManagerAction.EXTEND_SCHEMA));
			menuManager.add(new ManagerAction(this,"Export Schema",ManagerAction.EXPORT_SCHEMA));
			Action deleteAction = new ManagerAction(this,"Delete Schema",ManagerAction.DELETE_SCHEMA);
			if(!OpenIIManager.isDeletable(((Schema)element).getId())) deleteAction.setEnabled(false);
			menuManager.add(deleteAction);
		}
		
		// Display the menu for the "Groups" header
		if(element instanceof String && element.equals("Groups"))
			menuManager.add(new ManagerAction(this,"New Group",ManagerAction.NEW_GROUP));
		
		// Display the menu for a selected group
		if(element instanceof Group)
		{
			menuManager.add(new ManagerAction(this,"Add Subgroup",ManagerAction.NEW_GROUP));
			menuManager.add(new ManagerAction(this,"Edit Group",ManagerAction.EDIT_GROUP));
			menuManager.add(new ManagerAction(this,"Delete Group",ManagerAction.DELETE_GROUP));
		}
		
		// Display the menu for a selection group schema
		if(element instanceof GroupSchema)
			menuManager.add(new ManagerAction(this,"Remove Schema from Group",ManagerAction.DELETE_GROUP_SCHEMA));
			
		// Display the menu for the "Mappings" header
		if(element instanceof String && element.equals("Mappings"))
		{
			menuManager.add(new ManagerAction(this,"New Mapping",ManagerAction.NEW_MAPPING));
			menuManager.add(new ManagerAction(this,"Import Mapping",ManagerAction.IMPORT_MAPPING));
			menuManager.add(new ManagerAction(this,"Merge Mappings",ManagerAction.MERGE_MAPPINGS));
		}

		// Display the menu for a selected mapping
		if(element instanceof Mapping)
		{
			menuManager.add(new ManagerAction(this,"Edit Mapping",ManagerAction.EDIT_MAPPING));
			menuManager.add(new ManagerAction(this,"Export Mapping",ManagerAction.EXPORT_MAPPING));
			menuManager.add(new ManagerAction(this,"Delete Mapping",ManagerAction.DELETE_MAPPING));
		}
		
		// Display the menu for a selection mapping schema
		if(element instanceof MappingSchema)
			menuManager.add(new ManagerAction(this,"Remove Schema from Mapping",ManagerAction.DELETE_MAPPING_SCHEMA));
	}
	
	/** Generate the context menu before being displayed */
	public void menuAboutToShow(IMenuManager menuManager)
	{		
		element = ((StructuredSelection)viewer.getSelection()).getFirstElement();
		getEditorMenu(menuManager);
		menuManager.add(new Separator());
		getManagerMenu(menuManager);		
	}
}