package org.mitre.openii.views.ygg;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.model.OpenIIListener;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Constructs the Ygg View */
public class YggView extends ViewPart implements IMenuListener, OpenIIListener
{	
	/** Stores a reference to the viewer */
	private TreeViewer viewer = null;
	
	/** Constructs the Ygg View */
	public YggView()
		{ OpenIIManager.addListener(this); }
	
	/** Displays the Ygg View */
	public void createPartControl(Composite parent)
	{
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
		YggAction action = new YggAction(viewer,actionType);
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
			menuManager.add(createActionItem("Import Schema","Import.gif",null,YggAction.IMPORT_SCHEMA));
		
		// Display the menu for a selected schema
		if(selection instanceof Schema)
		{
			menuManager.add(createActionItem("Export Schema","Export.gif",null,YggAction.EXPORT_SCHEMA));
			Action deleteAction = createActionItem("Delete Schema","Delete.gif","DisabledDelete.gif",YggAction.DELETE_SCHEMA);
			if(!OpenIIManager.isDeletable(((Schema)selection).getId())) deleteAction.setEnabled(false);
			menuManager.add(deleteAction);
		}
		
		// Display the menu for the "Groups" header
		if(selection instanceof String && selection.equals("Groups"))
			menuManager.add(createActionItem("New Group","Group.gif",null,YggAction.NEW_GROUP));
		
		// Display the menu for a selected group
		if(selection instanceof Group)
		{
			menuManager.add(createActionItem("Add Subgroup","Group.gif",null,YggAction.NEW_GROUP));
			menuManager.add(createActionItem("Edit Group","Edit.gif",null,YggAction.EDIT_GROUP));
			menuManager.add(createActionItem("Delete Group","Delete.gif",null,YggAction.DELETE_GROUP));
		}
		
		// Display the menu for a selection group schema
		if(selection instanceof GroupSchema)
			menuManager.add(createActionItem("Remove Schema from Group","Delete.gif",null,YggAction.DELETE_GROUP_SCHEMA));
			
		// Display the menu for the "Mappings" header
		if(selection instanceof String && selection.equals("Mappings"))
			menuManager.add(createActionItem("New Mapping","Mapping.gif",null,YggAction.NEW_MAPPING));

		// Display the menu for a selected mapping
		if(selection instanceof Mapping)
		{
			menuManager.add(createActionItem("Edit Mapping","Edit.gif",null,YggAction.EDIT_MAPPING));
			menuManager.add(createActionItem("Delete Mapping","Delete.gif",null,YggAction.DELETE_MAPPING));
		}
		
		// Display the menu for a selection mapping schema
		if(selection instanceof MappingSchema)
			menuManager.add(createActionItem("Remove Schema from Mapping","Delete.gif",null,YggAction.DELETE_MAPPING_SCHEMA));
	}

	// Handles modifications of objects in the Ygg tree
	public void schemaAdded(Integer schemaID) { viewer.refresh(); }
	public void schemaDeleted(Integer schemaID) { viewer.refresh(); }
	public void groupAdded(Integer groupID) { viewer.refresh(); }
	public void groupModified(Integer groupID) { viewer.refresh(); }
	public void groupDeleted(Integer groupID) { viewer.refresh(); }
	public void mappingAdded(Integer mappingID) { viewer.refresh(); }
	public void mappingModified(Integer schemaID) { viewer.refresh(); }
	public void mappingDeleted(Integer mappingID) { viewer.refresh(); }
	
	// Sets the focus in this view
	public void setFocus() {}
}