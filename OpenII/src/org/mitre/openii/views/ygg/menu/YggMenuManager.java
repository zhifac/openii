package org.mitre.openii.views.ygg.menu;

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
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.ygg.GroupSchema;
import org.mitre.openii.views.ygg.MappingSchema;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Handles the displaying of the Ygg popup menu */
public class YggMenuManager extends MenuManager implements IMenuListener
{
	/** Stores reference to the tree viewer */
	private TreeViewer viewer = null;

	/** Stores reference to the currently selected element */
	private Object element = null;
	
	/** Constructs the Ygg Menu Manager */
	public YggMenuManager(TreeViewer viewer)
	{
		this.viewer = viewer;
		setRemoveAllWhenShown(true);
		addMenuListener(this);
	}
	
	/** Returns the viewer associated with this menu manager */
	public TreeViewer getViewer()
		{ return viewer; }
	
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

	/** Returns the type associated with the selected element */
	public Class<?> getElementType()
	{
		if(element instanceof Schema || element instanceof GroupSchema || element instanceof MappingSchema) return Schema.class;
		if(element instanceof Group) return Group.class;
		if(element instanceof Mapping) return Mapping.class;
		return null;
	}
	
	/** Returns the editor type associated with the selected element */
	public String getEditorType()
	{
		Class<?> elementType = getElementType();
		return elementType==null ? "" : "." + getElementType().toString().toLowerCase();
	}
	
	/** Generates the editor menu */
	private void getEditorMenu(IMenuManager menuManager)
	{
		// Retrieve the editor registry
		IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
		
		// Retrieve the list of available editors
		IEditorDescriptor editors[] = registry.getEditors(getEditorType());
		if(editors.length > 0)
		{
			// Display the menu for the default editor
			IEditorDescriptor defaultEditor = registry.getDefaultEditor(getEditorType());			
			menuManager.add(new EditorAction(this,"Open",defaultEditor));
			
			// Display the menu to select an editor
			if(editors.length > 1)
			{
				MenuManager editorMenu = new MenuManager("Open As");
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
	private void getYggMenu(IMenuManager menuManager)
	{
		// Display the menu for the "Schemas" header
		if(element instanceof String && element.equals("Schemas"))
			menuManager.add(new YggAction(this,"Import Schema",YggAction.IMPORT_SCHEMA));
		
		// Display the menu for a selected schema
		if(element instanceof Schema)
		{
			menuManager.add(new YggAction(this,"Export Schema",YggAction.EXPORT_SCHEMA));
			Action deleteAction = new YggAction(this,"Delete Schema",YggAction.DELETE_SCHEMA);
			if(!OpenIIManager.isDeletable(((Schema)element).getId())) deleteAction.setEnabled(false);
			menuManager.add(deleteAction);
		}
		
		// Display the menu for the "Groups" header
		if(element instanceof String && element.equals("Groups"))
			menuManager.add(new YggAction(this,"New Group",YggAction.NEW_GROUP));
		
		// Display the menu for a selected group
		if(element instanceof Group)
		{
			menuManager.add(new YggAction(this,"Add Subgroup",YggAction.NEW_GROUP));
			menuManager.add(new YggAction(this,"Edit Group",YggAction.EDIT_GROUP));
			menuManager.add(new YggAction(this,"Delete Group",YggAction.DELETE_GROUP));
		}
		
		// Display the menu for a selection group schema
		if(element instanceof GroupSchema)
			menuManager.add(new YggAction(this,"Remove Schema from Group",YggAction.DELETE_GROUP_SCHEMA));
			
		// Display the menu for the "Mappings" header
		if(element instanceof String && element.equals("Mappings"))
			menuManager.add(new YggAction(this,"New Mapping",YggAction.NEW_MAPPING));

		// Display the menu for a selected mapping
		if(element instanceof Mapping)
		{
			menuManager.add(new YggAction(this,"Edit Mapping",YggAction.EDIT_MAPPING));
			menuManager.add(new YggAction(this,"Delete Mapping",YggAction.DELETE_MAPPING));
		}
		
		// Display the menu for a selection mapping schema
		if(element instanceof MappingSchema)
			menuManager.add(new YggAction(this,"Remove Schema from Mapping",YggAction.DELETE_MAPPING_SCHEMA));
	}
	
	/** Generate the context menu before being displayed */
	public void menuAboutToShow(IMenuManager menuManager)
	{		
		element = ((StructuredSelection)viewer.getSelection()).getFirstElement();
		getEditorMenu(menuManager);
		menuManager.add(new Separator());
		getYggMenu(menuManager);		
	}
}