package org.mitre.openii.views.ygg;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
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
	static final Integer IMPORT_SCHEMA = 0;
	static final Integer EXPORT_SCHEMA = 1;
	static final Integer DELETE_SCHEMA = 2;
	static final Integer NEW_GROUP = 3;
	static final Integer EDIT_GROUP = 4;
	static final Integer DELETE_GROUP = 5;
	static final Integer DELETE_GROUP_SCHEMA = 6;
	static final Integer NEW_MAPPING = 7;
	static final Integer EDIT_MAPPING = 8;
	static final Integer DELETE_MAPPING = 9;
	static final Integer DELETE_MAPPING_SCHEMA = 10;
	
	/** Stores the tree viewer to which this action is tied */
	private TreeViewer treeViewer;
	
	/** Stores the action type */
	private Integer actionType;
	
	/** Constructs the Ygg action */
	YggAction(TreeViewer treeViewer, Integer actionType)
		{ this.treeViewer = treeViewer; this.actionType = actionType; }
	
	/** Runs the specified Ygg action */
	public void run()
	{
		// Determine which tree element was selected
		Object selection = ((StructuredSelection)treeViewer.getSelection()).getFirstElement();
		Shell shell = treeViewer.getControl().getShell();
		
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
