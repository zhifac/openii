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
import org.mitre.openii.views.manager.ManagerView;
import org.mitre.openii.views.manager.SchemaInProject;
import org.mitre.openii.views.manager.SchemaInTag;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;

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
		if(element instanceof SchemaInTag) return ((SchemaInTag)element).getSchema().getId();
		if(element instanceof SchemaInProject) return ((SchemaInProject)element).getSchema().getId();
		if(element instanceof Tag) return ((Tag)element).getId();
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
		if(element.equals(ManagerView.SCHEMAS_HEADER))
		{
			menuManager.add(new ManagerAction(this,"Import Schema",ManagerAction.IMPORT_SCHEMA));
			menuManager.add(new ManagerAction(this,"New Tag",ManagerAction.NEW_TAG));
		}
		
		// Display the menu for a selected schema
		if(element instanceof Schema)
		{
			// Display schema options
			menuManager.add(new ManagerAction(this,"Edit Schema Properties",ManagerAction.EDIT_SCHEMA));
			menuManager.add(new ManagerAction(this,"Extend Schema",ManagerAction.EXTEND_SCHEMA));
			menuManager.add(new ManagerAction(this,"Export Schema",ManagerAction.EXPORT_SCHEMA));
			Action deleteAction = new ManagerAction(this,"Delete Schema",ManagerAction.DELETE_SCHEMA);
			if(!OpenIIManager.isDeletable(((Schema)element).getId())) deleteAction.setEnabled(false);
			menuManager.add(deleteAction);

			menuManager.add(new Separator());
			
			// Display option to create instance database
			Action createDataSourceAction = new ManagerAction(this,"Create Instance Database",ManagerAction.CREATE_DATA_SOURCE);
			if(!((((Schema)element).getType()).equals("Spreadsheet Importer"))) createDataSourceAction.setEnabled(false);
			menuManager.add(createDataSourceAction);
		}
		
		// Display the menu for a selected tag
		if(element instanceof Tag)
		{
			// Display tag options
			menuManager.add(new ManagerAction(this,"Add Subcategory",ManagerAction.NEW_TAG));
			menuManager.add(new ManagerAction(this,"Edit Tag",ManagerAction.EDIT_TAG));
			menuManager.add(new ManagerAction(this,"Delete Tag",ManagerAction.DELETE_TAG));
	
			menuManager.add(new Separator());

			// Display option to create project
			menuManager.add(new ManagerAction(this,"Create Project",ManagerAction.CREATE_PROJECT_FROM_TAG));
		}
		
		// Display the menu for a selection tag schema
		if(element instanceof SchemaInTag)
			menuManager.add(new ManagerAction(this,"Remove Schema from Tag",ManagerAction.DELETE_TAG_SCHEMA));
			
		// Display the menu for the "Projects" header
		if(element.equals(ManagerView.PROJECTS_HEADER))
		{
			menuManager.add(new ManagerAction(this,"New Project",ManagerAction.NEW_PROJECT));
			menuManager.add(new ManagerAction(this,"Import Project",ManagerAction.IMPORT_PROJECT));
			menuManager.add(new ManagerAction(this,"Merge Projects",ManagerAction.MERGE_PROJECTS));
		}

		// Display the menu for a selected project
		if(element instanceof Project)
		{
			menuManager.add(new ManagerAction(this,"Edit Project",ManagerAction.EDIT_PROJECT));
			menuManager.add(new ManagerAction(this,"Export Project",ManagerAction.EXPORT_PROJECT));
			menuManager.add(new ManagerAction(this,"Delete Project",ManagerAction.DELETE_PROJECT));
			menuManager.add(new Separator());
			menuManager.add(new ManagerAction(this,"Import Mapping",ManagerAction.IMPORT_MAPPING));
			menuManager.add(new ManagerAction(this,"Generate Vocabulary...",ManagerAction.GENERATE_VOCABULARY));
		}
		
		// Display the menu for a selected mapping
		if(element instanceof Mapping)
		{
			menuManager.add(new ManagerAction(this,"Export Mapping",ManagerAction.EXPORT_MAPPING));
			menuManager.add(new ManagerAction(this,"Delete Mapping",ManagerAction.DELETE_MAPPING));
		}
		
		// Display the menu for a selection project schema
		if(element instanceof SchemaInProject)
		{
			if(((SchemaInProject)element).isDeletable())
				menuManager.add(new ManagerAction(this,"Remove Schema from Project",ManagerAction.DELETE_PROJECT_SCHEMA));
		}
		
		// Display the menu for a selected data source
		if(element instanceof DataSource)
			menuManager.add(new ManagerAction(this,"Delete Data Source",ManagerAction.DELETE_DATA_SOURCE));
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