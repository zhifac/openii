package org.mitre.openii.views.manager.menu;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.manager.SchemaInMapping;
import org.mitre.openii.views.manager.SchemaInTag;
import org.mitre.openii.views.manager.mappings.AutoMappingDialog;
import org.mitre.openii.views.manager.mappings.DeleteMappingDialog;
import org.mitre.openii.views.manager.mappings.EditMappingDialog;
import org.mitre.openii.views.manager.mappings.ExportMappingDialog;
import org.mitre.openii.views.manager.mappings.MergeMappingDialog;
import org.mitre.openii.views.manager.mappings.importer.ImportMappingWizard;
import org.mitre.openii.views.manager.schemas.CreateDataSourceDialog;
import org.mitre.openii.views.manager.schemas.DeleteDataSourceDialog;
import org.mitre.openii.views.manager.schemas.DeleteSchemaDialog;
import org.mitre.openii.views.manager.schemas.EditSchemaDialog;
import org.mitre.openii.views.manager.schemas.ExportSchemaDialog;
import org.mitre.openii.views.manager.schemas.ExtendSchemaDialog;
import org.mitre.openii.views.manager.schemas.importer.ImportSchemaDialog;
import org.mitre.openii.views.manager.tags.DeleteTagDialog;
import org.mitre.openii.views.manager.tags.EditTagDialog;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.Tag;

public class ManagerAction extends Action
{
	// Constants defining the various Manager action types available
	static final int IMPORT_SCHEMA = 0;
	static final int EDIT_SCHEMA = 1;
	static final int EXTEND_SCHEMA = 2;
	static final int EXPORT_SCHEMA = 3;
	static final int DELETE_SCHEMA = 4;
	static final int CREATE_DATA_SOURCE= 5;
	static final int DELETE_DATA_SOURCE = 6;
	static final int NEW_TAG = 7;
	static final int EDIT_TAG = 8;
	static final int DELETE_TAG = 9;
	static final int DELETE_TAG_SCHEMA = 10;
	static final int NEW_MAPPING = 11;
	static final int IMPORT_MAPPING = 12;
	static final int MERGE_MAPPINGS = 13;
	static final int EDIT_MAPPING = 14;
	static final int EXPORT_MAPPING = 15;
	static final int DELETE_MAPPING = 16;
	static final int DELETE_MAPPING_SCHEMA = 17;
	static final int AUTO_GENERATE_MATCHES = 18;
	
	/** Stores the menu manager to which this action is tied */
	private ManagerMenuManager menuManager;
	
	/** Stores the action type */
	private Integer actionType;
	
	/** Constructs the Manager action */
	ManagerAction(ManagerMenuManager menuManager, String title, Integer actionType)
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
			case NEW_TAG: icon = "Group.gif"; break;
			case EDIT_TAG: icon = "Edit.gif"; break;
			case DELETE_TAG: icon = "Delete.gif"; break;
			case DELETE_TAG_SCHEMA: icon = "Delete.gif"; break;
			case NEW_MAPPING: icon = "Mapping.gif"; break;
			case IMPORT_MAPPING: icon = "Import.gif"; break;
			case MERGE_MAPPINGS: icon = "Merge.gif"; break;
			case EDIT_MAPPING: icon = "Edit.gif"; break;
			case EXPORT_MAPPING: icon = "Export.gif"; break;
			case DELETE_MAPPING: icon = "Delete.gif"; break;
			case DELETE_MAPPING_SCHEMA: icon = "Delete.gif"; break;
			case AUTO_GENERATE_MATCHES : icon = "Mapping.gif"; break;
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
		if(actionType == IMPORT_SCHEMA)
			new ImportSchemaDialog(shell).open();
		
		/** Handles the extending of a schema */
		if(actionType == EDIT_SCHEMA)
			new EditSchemaDialog(shell,(Schema)selection).open();
		
		/** Handles the extending of a schema */
		if(actionType == EXTEND_SCHEMA)
			new ExtendSchemaDialog(shell,(Schema)selection).open();
		
		/** Handles the exporting of a schema */
		if(actionType == EXPORT_SCHEMA)
			ExportSchemaDialog.export(shell,(Schema)selection);

		/** Handles the deletion of a schema */
		if(actionType == DELETE_SCHEMA)
			DeleteSchemaDialog.delete(shell,(Schema)selection);
		
		/** Handles the creation of instance database */
		if(actionType == CREATE_DATA_SOURCE)
			new CreateDataSourceDialog(shell,(Schema)selection).open();

		/** Handles the deletion of a data source*/
		if(actionType == DELETE_DATA_SOURCE)
			DeleteDataSourceDialog.delete(shell,(DataSource)selection);
		
		// ----------------- Tag Actions ------------------
		
		/** Handles the addition of a tag */
		if(actionType == NEW_TAG)
		{
			Integer parentID = null;
			if(selection instanceof Tag) parentID = ((Tag)selection).getId();
			new EditTagDialog(shell,null,parentID).open();
		}
			
		/** Handles the editing of a tag */
		if(actionType == EDIT_TAG)
			new EditTagDialog(shell,(Tag)selection,((Tag)selection).getParentId()).open();
		
		/** Handles the deletion of a tag */
		if(actionType == DELETE_TAG)
			DeleteTagDialog.delete(shell,(Tag)selection);
		
		/** Handles the deletion of a tag schema */
		if(actionType == DELETE_TAG_SCHEMA)
		{
			SchemaInTag tagSchema = (SchemaInTag)selection;
			ArrayList<Integer> schemaIDs = OpenIIManager.getTagSchemas(tagSchema.getTagID());
			schemaIDs.remove(tagSchema.getSchema().getId());
			OpenIIManager.setTagSchemas(tagSchema.getTagID(), schemaIDs);
		}
		
		// ----------------- Mapping Actions ------------------
		
		/** Handles the addition of a mapping */
		if(actionType == NEW_MAPPING)
			new EditMappingDialog(shell,null).open();
		
		/** Handles the import of a mapping */
		if(actionType == IMPORT_MAPPING)
			new WizardDialog(shell,new ImportMappingWizard()).open();

		/** Handles the merging of mappings */
		if(actionType == MERGE_MAPPINGS)
			new MergeMappingDialog(shell).open();
		
		/** Handles the editing of a mapping */
		if(actionType == EDIT_MAPPING)
			new EditMappingDialog(shell,(Mapping)selection).open();
		
		/** Handles the exporting of a mapping */
		if(actionType == EXPORT_MAPPING)
			ExportMappingDialog.export(shell,(Mapping)selection);
		
		/** Handles the deletion of a mapping */
		if(actionType == DELETE_MAPPING)
			DeleteMappingDialog.delete(shell,(Mapping)selection);
		
		/** Handles the deletion of a mapping schema */
		if(actionType == DELETE_MAPPING_SCHEMA)
		{
			SchemaInMapping mappingSchema = (SchemaInMapping)selection;
			Mapping mapping = OpenIIManager.getMapping(mappingSchema.getMappingID());
			ArrayList<MappingSchema> schemas = new ArrayList<MappingSchema>(Arrays.asList(mapping.getSchemas()));
			for(MappingSchema schema : schemas)
				if(schema.getId().equals(mappingSchema.getSchema().getId()))
					schemas.remove(schema);
			mapping.setSchemas(schemas.toArray(new MappingSchema[0]));
			OpenIIManager.updateMapping(mapping);
		}
		
		/** Handles the auto-generation of a mapping's matches */
		if(actionType == AUTO_GENERATE_MATCHES) 
			new AutoMappingDialog( shell, (Mapping)selection).open();
	}
}