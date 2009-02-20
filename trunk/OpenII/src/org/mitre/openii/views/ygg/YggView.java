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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.openii.views.ygg.dialogs.ExportSchemaDialog;
import org.mitre.openii.views.ygg.dialogs.ImportSchemaDialog;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Constructs the Ygg View */
public class YggView extends ViewPart implements IMenuListener
{
	// Constants defining the various Ygg action types available
	private static final Integer IMPORT_SCHEMA = 0;
	private static final Integer EXPORT_SCHEMA = 1;
	private static final Integer DELETE_SCHEMA = 2;
	private static final Integer NEW_MAPPING = 3;
	private static final Integer EDIT_MAPPING = 4;
	private static final Integer DELETE_MAPPING = 5;
	
	/** Stores a reference to the shell */
	private Shell shell = null;
	
	/** Stores a reference to the viewer */
	private TreeViewer viewer = null;
	
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
	private Action createActionItem(String title, String icon, Integer actionType)
	{
		YggAction action = new YggAction(actionType);
		action.setImageDescriptor(OpenIIActivator.getImageDescriptor("icons/"+icon));
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
		{
			menuManager.add(createActionItem("Import Schema","Import.gif",IMPORT_SCHEMA));
		}
		
		// Display the menu for the "Mappings" header
		if(selection instanceof String && selection.equals("Mappings"))
			menuManager.add(createActionItem("New Mapping","Mapping.gif",NEW_MAPPING));
	
		// Display the menu for a selected schema
		if(selection instanceof Schema)
		{
			menuManager.add(createActionItem("Export Schema","Export.gif",EXPORT_SCHEMA));
			menuManager.add(createActionItem("Delete Schema","Delete.gif",DELETE_SCHEMA));
		}

		// Display the menu for a selected mapping
		if(selection instanceof Mapping)
		{
			menuManager.add(createActionItem("Edit Mapping","Edit.gif",EDIT_MAPPING));
			menuManager.add(createActionItem("Delete Mapping","Delete.gif",DELETE_MAPPING));
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
		}	
	}
	
	// Sets the focus in this view
	public void setFocus() {}
}