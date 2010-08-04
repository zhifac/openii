package org.mitre.openii.views.manager.menu;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.dialogs.mappings.DeleteMappingDialog;
import org.mitre.openii.dialogs.mappings.ExportMappingDialog;
import org.mitre.openii.dialogs.mappings.importer.ImportMappingDialog;
import org.mitre.openii.dialogs.projects.DeleteProjectDialog;
import org.mitre.openii.dialogs.projects.EditProjectDialog;
import org.mitre.openii.dialogs.projects.ExportProjectDialog;
import org.mitre.openii.dialogs.projects.MergeProjectsDialog;
import org.mitre.openii.dialogs.projects.ReplaceSchemaDialog;
import org.mitre.openii.dialogs.projects.importer.ImportProjectDialog;
import org.mitre.openii.dialogs.projects.unity.GenerateVocabularyWizard;
import org.mitre.openii.dialogs.schemas.CreateDataSourceDialog;
import org.mitre.openii.dialogs.schemas.DeleteDataSourceDialog;
import org.mitre.openii.dialogs.schemas.DeleteSchemaDialog;
import org.mitre.openii.dialogs.schemas.EditSchemaDialog;
import org.mitre.openii.dialogs.schemas.ExportSchemaDialog;
import org.mitre.openii.dialogs.schemas.ExportSchemasDialog;
import org.mitre.openii.dialogs.schemas.ExtendSchemaDialog;
import org.mitre.openii.dialogs.schemas.importer.ImportSchemaDialog;
import org.mitre.openii.dialogs.tags.DeleteTagDialog;
import org.mitre.openii.dialogs.tags.EditTagDialog;
import org.mitre.openii.dialogs.tags.SearchDialog;
import org.mitre.openii.dialogs.vocabulary.DeleteVocabularyDialog;
import org.mitre.openii.dialogs.vocabulary.ExportVocabularyDialog;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.SchemaInProject;
import org.mitre.openii.views.manager.SchemaInTag;
import org.mitre.openii.views.manager.VocabularyInProject;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.ProjectSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;

public class ManagerAction extends Action
{
	// Constants defining the various Manager action types available
	static enum ActionType {IMPORT_SCHEMA, EDIT_SCHEMA, EXTEND_SCHEMA, EXPORT_SCHEMA, DELETE_SCHEMA,
							CREATE_DATA_SOURCE, DELETE_DATA_SOURCE, NEW_TAG, EDIT_TAG, DELETE_TAG,
							KEYWORD_SEARCH, CREATE_PROJECT_FROM_TAG,  EXPORT_SCHEMAS_BY_TAG,
							DELETE_TAG_SCHEMA, NEW_PROJECT, IMPORT_PROJECT , MERGE_PROJECTS, EDIT_PROJECT,
							EXPORT_PROJECT, DELETE_PROJECT, DELETE_PROJECT_SCHEMA, IMPORT_MAPPING,
							REPLACE_SCHEMA, AUTO_GENERATE_MATCHES, EXPORT_MAPPING, DELETE_MAPPING,
							GENERATE_VOCABULARY, DELETE_VOCABULARY, EXPORT_VOCABULARY};
	
	/** Stores the menu manager to which this action is tied */
	private ManagerMenuManager menuManager;
	
	/** Stores the action type */
	private ActionType actionType;
	
	/** Constructs the Manager action */
	ManagerAction(ManagerMenuManager menuManager, String title, ActionType actionType)
	{
		// Set the title and action type
		this.menuManager = menuManager;
		this.actionType = actionType;
		setText(title);

		// Set the action icon
		String icon = null;
		switch(actionType)
		{
			case IMPORT_SCHEMA: icon = "Import.gif"; break;
			case EDIT_SCHEMA: icon = "Edit.gif"; break;
			case EXTEND_SCHEMA: icon = "Schema.gif"; break;
			case EXPORT_SCHEMA: icon = "Export.gif"; break;
			case DELETE_SCHEMA: icon = "Delete.gif"; break;
			case CREATE_DATA_SOURCE: icon = "DataSource.gif"; break;
			case DELETE_DATA_SOURCE: icon = "Delete.gif"; break;
			case NEW_TAG: icon = "NewSchemaGroup.gif"; break;
			case EDIT_TAG: icon = "Edit.gif"; break;
			case DELETE_TAG: icon = "Delete.gif"; break;
			case KEYWORD_SEARCH: icon = "Search.gif"; break;
			case CREATE_PROJECT_FROM_TAG: icon = "Project.gif"; break;
			case EXPORT_SCHEMAS_BY_TAG: icon = "Export.gif"; break;
			case DELETE_TAG_SCHEMA: icon = "Delete.gif"; break;
			case NEW_PROJECT: icon = "Project.gif"; break;
			case IMPORT_PROJECT: icon = "Import.gif"; break;
			case MERGE_PROJECTS: icon = "Merge.gif"; break;
			case EDIT_PROJECT: icon = "Edit.gif"; break;
			case EXPORT_PROJECT: icon = "Export.gif"; break;
			case DELETE_PROJECT: icon = "Delete.gif"; break;
			case DELETE_PROJECT_SCHEMA: icon = "Delete.gif"; break;
			case IMPORT_MAPPING: icon = "Import.gif"; break;
			case REPLACE_SCHEMA: icon = "ReplaceSchema.gif"; break;
			case AUTO_GENERATE_MATCHES : icon = "Mapping.gif"; break;
			case EXPORT_MAPPING: icon = "Export.gif"; break;
			case DELETE_MAPPING: icon = "Delete.gif"; break;
			case DELETE_VOCABULARY: icon = "Delete.gif"; break;
			case EXPORT_VOCABULARY: icon = "Export.gif"; break;
		}		
		setImageDescriptor(OpenIIActivator.getImageDescriptor("icons/"+icon));
	}
	
	/** Runs the specified Manager action */
	public void run()
	{
		// Determine which tree element was selected
		Object selection = menuManager.getElement();
		Shell shell = menuManager.getMenu().getShell();
		
		// ----------------- Schema Actions ------------------
		
		/** Handles the importing of a schema */
		if(actionType == ActionType.IMPORT_SCHEMA)
			new ImportSchemaDialog(shell).open();
		
		/** Handles the extending of a schema */
		if(actionType == ActionType.EDIT_SCHEMA)
			new EditSchemaDialog(shell,(Schema)selection).open();
		
		/** Handles the extending of a schema */
		if(actionType == ActionType.EXTEND_SCHEMA)
			new ExtendSchemaDialog(shell,(Schema)selection).open();
		
		/** Handles the exporting of a schema */
		if(actionType == ActionType.EXPORT_SCHEMA)
		{
			Schema schema = null;
			if(selection instanceof Schema) schema = (Schema)selection;
			else if(selection instanceof SchemaInTag) schema = ((SchemaInTag)selection).getSchema();
			else if(selection instanceof SchemaInProject) schema = ((SchemaInProject)selection).getSchema();
			ExportSchemaDialog.export(shell,schema);
		}
		
		/** Handles the deletion of a schema */
		if(actionType == ActionType.DELETE_SCHEMA)
			DeleteSchemaDialog.delete(shell,(Schema)selection);
		
		/** Handles the creation of instance database */
		if(actionType == ActionType.CREATE_DATA_SOURCE)
			new CreateDataSourceDialog(shell,(Schema)selection).open();

		/** Handles the deletion of a data source*/
		if(actionType == ActionType.DELETE_DATA_SOURCE)
			DeleteDataSourceDialog.delete(shell,(DataSource)selection);
		
		// ----------------- Tag Actions ------------------
		
		/** Handles the addition of a tag */
		if(actionType == ActionType.NEW_TAG)
		{
			Integer parentID = null;
			if(selection instanceof Tag) parentID = ((Tag)selection).getId();
			new EditTagDialog(shell,null,parentID).open();
		}
			
		/** Handles the editing of a tag */
		if(actionType == ActionType.EDIT_TAG)
			new EditTagDialog(shell,(Tag)selection,((Tag)selection).getParentId()).open();
		
		/** Handles the deletion of a tag */
		if(actionType == ActionType.DELETE_TAG)
			DeleteTagDialog.delete(shell,(Tag)selection);
		
		/** Launches a keyword search */
		if(actionType == ActionType.KEYWORD_SEARCH)
			new SearchDialog(shell,(selection instanceof Tag ? (Tag)selection : null)).open();
		
		/** Creates a project based on the schemas in a tag */
		if(actionType == ActionType.CREATE_PROJECT_FROM_TAG)
			new EditProjectDialog(shell,(Tag)selection).open();
		
		/** Handles the exporting of a list of schemas */
		if(actionType == ActionType.EXPORT_SCHEMAS_BY_TAG)
			ExportSchemasDialog.export(shell,(Tag)selection);
		
		/** Handles the deletion of a tag schema */
		if(actionType == ActionType.DELETE_TAG_SCHEMA)
		{
			SchemaInTag tagSchema = (SchemaInTag)selection;
			ArrayList<Integer> schemaIDs = OpenIIManager.getTagSchemas(tagSchema.getTagID());
			schemaIDs.remove(tagSchema.getSchema().getId());
			OpenIIManager.setTagSchemas(tagSchema.getTagID(), schemaIDs);
		}
		
		// ----------------- Project Actions ------------------
		
		/** Handles the addition of a project */
		if(actionType == ActionType.NEW_PROJECT)
			new EditProjectDialog(shell).open();
		
		/** Handles the import of a project */
		if(actionType == ActionType.IMPORT_PROJECT)
			new ImportProjectDialog(shell).open();

		/** Handles the merging of project */
		if(actionType == ActionType.MERGE_PROJECTS)
			new MergeProjectsDialog(shell).open();
		
		/** Handles the editing of a project */
		if(actionType == ActionType.EDIT_PROJECT)
			new EditProjectDialog(shell,(Project)selection).open();
		
		/** Handles the exporting of a project */
		if(actionType == ActionType.EXPORT_PROJECT)
			ExportProjectDialog.export(shell,(Project)selection);
		
		/** Handles the deletion of a project */
		if(actionType == ActionType.DELETE_PROJECT)
			DeleteProjectDialog.delete(shell,(Project)selection);
		
		/** Handles the deletion of a project schema */
		if(actionType == ActionType.DELETE_PROJECT_SCHEMA)
		{
			SchemaInProject schemaInProject = (SchemaInProject)selection;
			Project project = OpenIIManager.getProject(schemaInProject.getProjectID());
			ArrayList<ProjectSchema> schemas = new ArrayList<ProjectSchema>(Arrays.asList(project.getSchemas()));
			for(ProjectSchema schema : new ArrayList<ProjectSchema>(schemas))
				if(schema.getId().equals(schemaInProject.getSchema().getId()))
					schemas.remove(schema);
			project.setSchemas(schemas.toArray(new ProjectSchema[0]));
			OpenIIManager.updateProject(project);
		}
		
		/** Handles the importing of a mapping */
		if(actionType == ActionType.IMPORT_MAPPING)
			new ImportMappingDialog(shell,(Project)selection).open();

		/** Handles the auto-generation of a mapping's matches */
		if(actionType == ActionType.GENERATE_VOCABULARY)
			new WizardDialog(shell, new GenerateVocabularyWizard((Project)selection)).open();
	
		/** Handles the deletion of a project's vocabulary */
		if(actionType == ActionType.DELETE_VOCABULARY)
			DeleteVocabularyDialog.delete(shell,(VocabularyInProject)selection);
		
		if (actionType == ActionType.EXPORT_VOCABULARY)
			ExportVocabularyDialog.export(shell, (VocabularyInProject)selection); 

		/** Handles the replacing of a schema from a project */
		if(actionType == ActionType.REPLACE_SCHEMA)
			new ReplaceSchemaDialog(shell,(SchemaInProject)selection).open();
		
		// ----------------- Mapping Actions ----------------------
		
		/** Handles the exporting of a mapping */
		if(actionType == ActionType.EXPORT_MAPPING)
			ExportMappingDialog.export(shell,(Mapping)selection);
		
		/** Handles the deletion of a mapping */
		if(actionType == ActionType.DELETE_MAPPING)
			DeleteMappingDialog.delete(shell,(Mapping)selection);
	}
}