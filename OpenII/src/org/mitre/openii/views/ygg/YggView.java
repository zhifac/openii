package org.mitre.openii.views.ygg;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIListener;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.openii.views.ygg.dialogs.DeleteGroupDialog;
import org.mitre.openii.views.ygg.dialogs.DeleteSchemaDialog;
import org.mitre.openii.views.ygg.dialogs.EditGroupDialog;
import org.mitre.openii.views.ygg.dialogs.ExportSchemaDialog;
import org.mitre.openii.views.ygg.dialogs.ImportSchemaDialog;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Constructs the Ygg View */
public class YggView extends ViewPart implements IMenuListener, OpenIIListener
{
	// Constants defining the various Ygg action types available
	private static final Integer IMPORT_SCHEMA = 0;
	private static final Integer EXPORT_SCHEMA = 1;
	private static final Integer DELETE_SCHEMA = 2;
	private static final Integer NEW_GROUP = 3;
	private static final Integer EDIT_GROUP = 4;
	private static final Integer DELETE_GROUP = 5;
	private static final Integer DELETE_GROUP_SCHEMA = 6;
	private static final Integer NEW_MAPPING = 7;
	private static final Integer EDIT_MAPPING = 8;
	private static final Integer DELETE_MAPPING = 9;
	
	/** Stores a reference to the shell */
	private Shell shell = null;
	
	/** Stores a reference to the viewer */
	private TreeViewer viewer = null;
	
	/** Constructs the Ygg View */
	public YggView()
		{ OpenIIManager.addListener(this); }
	
	/** Displays the Ygg View */
	public void createPartControl(Composite parent)
	{
		shell = parent.getShell();
		
		// Create the tree viewer
		viewer = new TreeViewer(parent, SWT.SINGLE);
		viewer.setContentProvider(new YggContentProvider());
		viewer.setLabelProvider(new YggLabelProvider());
		viewer.setInput("");
		
		// Add the context menu
		MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(this);
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);	
	}

	/** Generates a menu action item */
	private Action createActionItem(String title, String icon, String disabledIcon, Integer actionType)
	{
		YggAction action = new YggAction(actionType);
		action.setImageDescriptor(OpenIIActivator.getImageDescriptor("icons/"+icon));
		if(disabledIcon!=null)
			action.setDisabledImageDescriptor(OpenIIActivator.getImageDescriptor("icons/"+disabledIcon));
		action.setText(title);
		return action;
	}
	
	/** Generate the context menu before being displayed */
	public void menuAboutToShow(IMenuManager menuManager)
	{
		// Determine which tree element was selected
		Object selection = ((StructuredSelection)viewer.getSelection()).getFirstElement();

		// Display the menu for the "Schemas" header
		if(selection instanceof String && selection.equals("Schemas"))
			menuManager.add(createActionItem("Import Schema","Import.gif",null,IMPORT_SCHEMA));
		
		// Display the menu for a selected schema
		if(selection instanceof Schema)
		{
			menuManager.add(createActionItem("Export Schema","Export.gif",null,EXPORT_SCHEMA));
			Action deleteAction = createActionItem("Delete Schema","Delete.gif","DisabledDelete.gif",DELETE_SCHEMA);
			if(!OpenIIManager.isDeletable(((Schema)selection).getId())) deleteAction.setEnabled(false);
			menuManager.add(deleteAction);
		}
		
		// Display the menu for the "Groups" header
		if(selection instanceof String && selection.equals("Groups"))
			menuManager.add(createActionItem("New Group","Group.gif",null,NEW_GROUP));
		
		// Display the menu for a selected group
		if(selection instanceof Group)
		{
			menuManager.add(createActionItem("Add Subgroup","Group.gif",null,NEW_GROUP));
			menuManager.add(createActionItem("Edit Group","Edit.gif",null,EDIT_GROUP));
			menuManager.add(createActionItem("Delete Group","Delete.gif",null,DELETE_GROUP));
		}
		
		// Display the menu for a selection group schema
		if(selection instanceof GroupSchema)
			menuManager.add(createActionItem("Remove Schema from Group","Delete.gif",null,DELETE_GROUP_SCHEMA));
			
		// Display the menu for the "Mappings" header
		if(selection instanceof String && selection.equals("Mappings"))
			menuManager.add(createActionItem("New Mapping","Mapping.gif",null,NEW_MAPPING));

		// Display the menu for a selected mapping
		if(selection instanceof Mapping)
		{
			menuManager.add(createActionItem("Edit Mapping","Edit.gif",null,EDIT_MAPPING));
			menuManager.add(createActionItem("Delete Mapping","Delete.gif",null,DELETE_MAPPING));
		}
	}

	/** Handles the various Ygg actions */
	private class YggAction extends Action
	{
		/** Stores the action type */
		private Integer actionType;
		
		/** Constructs the Ygg action */
		private YggAction(Integer actionType)
			{ this.actionType = actionType; }
		
		/** Runs the specified Ygg action */
		public void run()
		{
			// Determine which tree element was selected
			Object selection = ((StructuredSelection)viewer.getSelection()).getFirstElement();
			
			/** Handles the importing of a schema */
			if(actionType == IMPORT_SCHEMA)
				new ImportSchemaDialog(shell).open();
			
			/** Handles the exporting of a schema */
			if(actionType == EXPORT_SCHEMA)
				ExportSchemaDialog.export(shell,(Schema)selection);

			/** Handles the deletion of a schema */
			if(actionType == DELETE_SCHEMA)
				DeleteSchemaDialog.delete(shell,(Schema)selection);
			
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
				DeleteGroupDialog.delete(shell,(Group)selection);
			
			/** Handles the deletion of a group schema */
			if(actionType == DELETE_GROUP_SCHEMA)
			{
				GroupSchema groupSchema = (GroupSchema)selection;
				ArrayList<Integer> schemaIDs = OpenIIManager.getGroupSchemas(groupSchema.getGroupID());
				schemaIDs.remove(groupSchema.getSchema().getId());
				OpenIIManager.setGroupSchemas(groupSchema.getGroupID(), schemaIDs);
			}
		}
	}

	// Handles modifications of objects in the Ygg tree
	public void schemaAdded(Integer schemaID) { viewer.refresh(); }
	public void schemaDeleted(Integer schemaID) { viewer.refresh(); }
	public void groupAdded(Integer groupID) { viewer.refresh(); }
	public void groupModified(Integer groupID) { viewer.refresh(); }
	public void groupDeleted(Integer groupID) { viewer.refresh(); }
	public void mappingAdded(Integer mappingID) { viewer.refresh(); }
	public void mappingRemoved(Integer mappingID) { viewer.refresh(); }
	
	// Sets the focus in this view
	public void setFocus() {}

	// Unused listener events
	public void mappingModified(Integer schemaID) {}
}