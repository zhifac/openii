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
import org.mitre.openii.views.manager.VocabularyInProject;
import org.mitre.openii.views.manager.menu.ManagerAction.ActionType;
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
	
	/** Generates the editor menu */
	private void getEditorMenu(IMenuManager menuManager)
	{
		// Retrieve the editor registry
		IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
		
		// Get the editor type for the specified element
		String editorType = EditorManager.getEditorType(element);
		if(editorType!=null)
		{
			// Retrieve the list of available editors for the specified type
			IEditorDescriptor editors[] = registry.getEditors(editorType);
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
	}
	
	/** Generates the action menu */
	private void getManagerMenu(IMenuManager menuManager)
	{
		// Display the menu for the "Schemas" header
		if(element.equals(ManagerView.SCHEMAS_HEADER))
		{
			menuManager.add(new ManagerAction(this,"Import Schema",ActionType.IMPORT_SCHEMA));
			menuManager.add(new ManagerAction(this,"New Tag",ActionType.NEW_TAG));
		}
		
		// Display the menu for the "All Schemas" header
		if(element.equals(ManagerView.ALL_SCHEMAS_HEADER))
		{
			menuManager.add(new ManagerAction(this,"Keyword Search",ActionType.KEYWORD_SEARCH));			
		}
		
		// Display the menu for a selected schema
		if(element instanceof Schema)
		{
			// Display schema options
			menuManager.add(new ManagerAction(this,"Edit Schema Properties",ActionType.EDIT_SCHEMA));
			menuManager.add(new ManagerAction(this,"Extend Schema",ActionType.EXTEND_SCHEMA));
			menuManager.add(new ManagerAction(this,"Export Schema",ActionType.EXPORT_SCHEMA));
			Action deleteAction = new ManagerAction(this,"Delete Schema",ActionType.DELETE_SCHEMA);
			if(!OpenIIManager.isDeletable(((Schema)element).getId())) deleteAction.setEnabled(false);
			menuManager.add(deleteAction);

			// Display options based on schema type
			String type = ((Schema)element).getType();
			if(type.equals("Spreadsheet Importer") || type.equals("Taxonomy"))
			{
				menuManager.add(new Separator());
				if(type.equals("Spreadsheet Importer"))
					menuManager.add(new ManagerAction(this,"Create Instance Database",ActionType.CREATE_DATA_SOURCE));
				if(type.equals("Taxonomy"))
					menuManager.add(new ManagerAction(this,"Display Manifest",ActionType.DISPLAY_MANIFEST));				
			}
		}
		
		// Display the menu for a selected tag
		if(element instanceof Tag)
		{
			// Display tag options
			menuManager.add(new ManagerAction(this,"Add Subcategory",ActionType.NEW_TAG));
			menuManager.add(new ManagerAction(this,"Edit Tag",ActionType.EDIT_TAG));
			menuManager.add(new ManagerAction(this,"Delete Tag",ActionType.DELETE_TAG));
	
			menuManager.add(new Separator());
			
			// Display option to create project
			menuManager.add(new ManagerAction(this,"Keyword Search",ActionType.KEYWORD_SEARCH));
			menuManager.add(new ManagerAction(this,"Create Project",ActionType.CREATE_PROJECT_FROM_TAG));
			menuManager.add(new ManagerAction(this,"Export Schemas",ActionType.EXPORT_SCHEMAS_BY_TAG));
		}
		
		// Display the menu for a selection tag schema
		if(element instanceof SchemaInTag)
		{
			menuManager.add(new ManagerAction(this,"Edit Schema Properties",ActionType.EDIT_SCHEMA));
			menuManager.add(new ManagerAction(this,"Export Schema",ActionType.EXPORT_SCHEMA));
			menuManager.add(new ManagerAction(this,"Remove Schema from Tag",ActionType.DELETE_TAG_SCHEMA));
		}
		
		// Display the menu for the "Projects" header
		if(element.equals(ManagerView.PROJECTS_HEADER))
		{
			menuManager.add(new ManagerAction(this,"New Project",ActionType.NEW_PROJECT));
			menuManager.add(new ManagerAction(this,"Import Project",ActionType.IMPORT_PROJECT));
			menuManager.add(new ManagerAction(this,"Merge Projects",ActionType.MERGE_PROJECTS));
		}

		// Display the menu for a selected project
		if(element instanceof Project)
		{
			menuManager.add(new ManagerAction(this,"Edit Project",ActionType.EDIT_PROJECT));
			menuManager.add(new ManagerAction(this,"Export Project",ActionType.EXPORT_PROJECT));
			menuManager.add(new ManagerAction(this,"Delete Project",ActionType.DELETE_PROJECT));
			menuManager.add(new Separator());
			menuManager.add(new ManagerAction(this, "Batch Mapping", ActionType.BATCH_MATCH));
			menuManager.add(new ManagerAction(this,"Import Mapping",ActionType.IMPORT_MAPPING));
			menuManager.add(new ManagerAction(this,"Generate Vocabulary (DSF-new)",ActionType.GENERATE_VOCABULARY));
			menuManager.add(new ManagerAction(this,"Generate Vocabulary (HC-old)",ActionType.GENERATE_VOCABULARY_WIZARD));
		}
		
		// Display the menu for a selected mapping
		if(element instanceof Mapping)
		{
			menuManager.add(new ManagerAction(this,"Export Mapping",ActionType.EXPORT_MAPPING));
			menuManager.add(new ManagerAction(this,"Delete Mapping",ActionType.DELETE_MAPPING));
		}
		
		// Display the menu for a selected project schema
		if(element instanceof SchemaInProject)
		{
			menuManager.add(new ManagerAction(this,"Edit Schema Properties",ActionType.EDIT_SCHEMA));
			menuManager.add(new ManagerAction(this,"Replace Schema",ActionType.REPLACE_SCHEMA));
			menuManager.add(new ManagerAction(this,"Export Schema",ActionType.EXPORT_SCHEMA));
			if(((SchemaInProject)element).isDeletable())
				menuManager.add(new ManagerAction(this,"Remove Schema from Project",ActionType.DELETE_PROJECT_SCHEMA));
		}
		
		// Display the menu for a selected project vocabulary
		if(element instanceof VocabularyInProject) {
			menuManager.add(new ManagerAction(this, "Export Vocabulary", ActionType.EXPORT_VOCABULARY)); 
			menuManager.add(new ManagerAction(this,"Delete Vocabulary",ActionType.DELETE_VOCABULARY));
		}
		
		// Display the menu for a selected data source
		if(element instanceof DataSource)
			menuManager.add(new ManagerAction(this,"Delete Data Source",ActionType.DELETE_DATA_SOURCE));
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