package org.mitre.openii.editors.unity;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;
import org.mitre.openii.application.OpenIIActivator;
import org.mitre.schemastore.model.AssociatedElement;

/** Handles the displaying of the schema popup menu */
public class SchemaTreeMenuManager extends MenuManager implements IMenuListener
{
	private UnityCanvas Ucanvas = null;
    private ImageDescriptor tableIcon = ImageDescriptor.createFromImage(OpenIIActivator.getImage("tableicon.png"));
    private ImageDescriptor insertIcon = ImageDescriptor.createFromImage(OpenIIActivator.getImage("insert.png"));
    private ImageDescriptor copyIcon = ImageDescriptor.createFromImage(OpenIIActivator.getImage("copy.png"));

	/** Defines a private class defining the "Expand All" action */
	private class ExpandAllAction extends Action
	{
		/** Constructs the "Expand All" action */
		ExpandAllAction() { setText("Expand All"); }
		
		/** Expands the currently selected node in the tree */
		public void run()
			{ viewer.expandToLevel(((TreeSelection)viewer.getSelection()).getPaths()[0], TreeViewer.ALL_LEVELS); }
	}

	/** Defines a private class defining the "Collapse All" action */
	private class CollapseAllAction extends Action
	{
		/** Constructs the "Collapse All" action */
		CollapseAllAction() { setText("Collapse All"); }
		
		/** Collapses the currently selected node in the tree */
		public void run()
			{ viewer.collapseToLevel(((TreeSelection)viewer.getSelection()).getPaths()[0], TreeViewer.ALL_LEVELS); }
	}
	
	/** Defines a private class defining the "Show in Table" action */
	private class ShowInTable extends Action
	{
		/** Constructs the "Show in Table" action */
		ShowInTable() { 
			setText("Show in Table"); 
			this.setImageDescriptor(tableIcon);
			this.setEnabled(false);

		}
		
		/** Collapses the currently selected node in the tree */
		public void run()
			{ /*do show */ }
	}
	
	/** Defines a private class defining the "copy" action */
	private class CopyTerm extends Action
	{
		/** Constructs the "copy" action */
		CopyTerm() { 
			TreeItem items[] =  viewer.getTree().getSelection();
			setText("Copy " + items[0].getText()); 
			this.setImageDescriptor(copyIcon);
		}
		
		/** Collapses the currently selected node in the tree */
		public void run()
		{ 
			TreeItem items[] =  viewer.getTree().getSelection();
			Ucanvas.copyElementToClipboard(Ucanvas.getTreeSchemaID(),(Integer)(items[0].getData("uid")),items[0].getText(),(String)(items[0].getData("description")));
		}
	}
	
	/** Defines a private class defining the "Add to Workspace" action */
	private class AddToWorkspace extends Action
	{
		/** Constructs the "Add to Workspace" action */
		AddToWorkspace() { 
			setText("Add to Workspace"); 
			this.setImageDescriptor(insertIcon);
		}
		
		/** Collapses the currently selected node in the tree */
		public void run()
		{ 
			TreeItem items[] =  viewer.getTree().getSelection();
			Ucanvas.addAssociatedSynsets((Integer)(items[0].getData("uid")));
		}
	}
	
	/** Stores reference to the tree viewer */
	private TreeViewer viewer = null;
	
	/** Constructs the Schema Menu Manager */
	public SchemaTreeMenuManager(TreeViewer viewer, UnityCanvas thecanvas)
	{
		this.viewer = viewer;
		this.Ucanvas = thecanvas;
		setRemoveAllWhenShown(true);
		addMenuListener(this);
	}
	
	/** Generate the context menu before being displayed */
	public void menuAboutToShow(IMenuManager menuManager)
	{
		menuManager.add(new CopyTerm());
		menuManager.add(new Separator()); 
		menuManager.add(new ShowInTable());
		menuManager.add(new Separator()); 
		menuManager.add(new AddToWorkspace());
		menuManager.add(new Separator()); 
		menuManager.add(new ExpandAllAction());
		menuManager.add(new CollapseAllAction());
		
	}
}