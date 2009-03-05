package org.mitre.openii.views.ygg.menu;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.ygg.GroupSchema;
import org.mitre.openii.views.ygg.MappingSchema;
import org.mitre.openii.views.ygg.dialogs.DeleteDialog;
import org.mitre.openii.views.ygg.dialogs.EditGroupDialog;
import org.mitre.openii.views.ygg.dialogs.EditMappingDialog;
import org.mitre.openii.views.ygg.dialogs.ExportSchemaDialog;
import org.mitre.openii.views.ygg.dialogs.ImportSchemaDialog;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

public class YggAction extends Action
{
	// Constants defining the various Ygg action types available
	static final int IMPORT_SCHEMA = 0;
	static final int EXPORT_SCHEMA = 1;
	static final int DELETE_SCHEMA = 2;
	static final int NEW_GROUP = 3;
	static final int EDIT_GROUP = 4;
	static final int DELETE_GROUP = 5;
	static final int DELETE_GROUP_SCHEMA = 6;
	static final int NEW_MAPPING = 7;
	static final int EDIT_MAPPING = 8;
	static final int DELETE_MAPPING = 9;
	static final int DELETE_MAPPING_SCHEMA = 10;
	
	/** Stores the menu manager to which this action is tied */
	private YggMenuManager menuManager;
	
	/** Stores the action type */
	private Integer actionType;
	
	/** Constructs the Ygg action */
	YggAction(YggMenuManager menuManager, String title, Integer actionType)
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
			case EXPORT_SCHEMA: icon = "Export.gif"; break;
			case DELETE_SCHEMA: icon = "Delete.gif"; break;
			case NEW_GROUP: icon = "Group.gif"; break;
			case EDIT_GROUP: icon = "Edit.gif"; break;
			case DELETE_GROUP: icon = "Delete.gif"; break;
			case DELETE_GROUP_SCHEMA: icon = "Delete.gif"; break;
			case NEW_MAPPING: icon = "Mapping.gif"; break;
			case EDIT_MAPPING: icon = "Edit.gif"; break;
			case DELETE_MAPPING: icon = "Delete.gif"; break;
			case DELETE_MAPPING_SCHEMA: icon = "Delete.gif"; break;
		}		
		setImageDescriptor(OpenIIActivator.getImageDescriptor("icons/"+icon));
	
		// Set the disabled action icon
		if(actionType.equals(YggAction.DELETE_SCHEMA))
			setDisabledImageDescriptor(OpenIIActivator.getImageDescriptor("icons/DisableDelete.gif"));
	}
	
	/** Runs the specified Ygg action */
	public void run()
	{
		// Determine which tree element was selected
		Object selection = menuManager.getElement();
		Shell shell = menuManager.getViewer().getControl().getShell();
		
		// ----------------- Schema Actions ------------------
		
		/** Handles the importing of a schema */
		if(actionType == IMPORT_SCHEMA)
			new ImportSchemaDialog(shell).open();
		
		/** Handles the exporting of a schema */
		if(actionType == EXPORT_SCHEMA)
			ExportSchemaDialog.export(shell,(Schema)selection);

		/** Handles the deletion of a schema */
		if(actionType == DELETE_SCHEMA)
			DeleteDialog.delete(shell,(Schema)selection);
		
		// ----------------- Group Actions ------------------
		
		/** Handles the addition of a group */
		if(actionType == NEW_GROUP)
		{
			Integer parentID = null;
			if(selection instanceof Group) parentID = ((Group)selection).getId();
			new EditGroupDialog(shell,null,parentID).open();
		}
			
		/** Handles the editing of a group */
		if(actionType == EDIT_GROUP)
			new EditGroupDialog(shell,(Group)selection,((Group)selection).getParentId()).open();
		
		/** Handles the deletion of a group */
		if(actionType == DELETE_GROUP)
			DeleteDialog.delete(shell,(Group)selection);
		
		/** Handles the deletion of a group schema */
		if(actionType == DELETE_GROUP_SCHEMA)
		{
			GroupSchema groupSchema = (GroupSchema)selection;
			ArrayList<Integer> schemaIDs = OpenIIManager.getGroupSchemas(groupSchema.getGroupID());
			schemaIDs.remove(groupSchema.getSchema().getId());
			OpenIIManager.setGroupSchemas(groupSchema.getGroupID(), schemaIDs);
		}

		// ----------------- Mapping Actions ------------------
		
		/** Handles the addition of a mapping */
		if(actionType == NEW_MAPPING)
			new EditMappingDialog(shell,null).open();
			
		/** Handles the editing of a mapping */
		if(actionType == EDIT_MAPPING)
			new EditMappingDialog(shell,(Mapping)selection).open();
		
		/** Handles the deletion of a mapping */
		if(actionType == DELETE_MAPPING)
			DeleteDialog.delete(shell,(Mapping)selection);
		
		/** Handles the deletion of a mapping schema */
		if(actionType == DELETE_MAPPING_SCHEMA)
		{
			MappingSchema mappingSchema = (MappingSchema)selection;
			Mapping mapping = OpenIIManager.getMapping(mappingSchema.getMappingID());
			ArrayList<Integer> schemaIDs = new ArrayList<Integer>(Arrays.asList(mapping.getSchemas()));
			schemaIDs.remove(mappingSchema.getSchema().getId());
			mapping.setSchemas(schemaIDs.toArray(new Integer[0]));
			OpenIIManager.updateMapping(mapping);
		}
	}
}