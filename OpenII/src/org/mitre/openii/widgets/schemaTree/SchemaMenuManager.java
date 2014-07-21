package org.mitre.openii.widgets.schemaTree;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;

/** Handles the displaying of the schema popup menu */
public class SchemaMenuManager extends MenuManager implements IMenuListener
{
	/** Defines a private class defining the "Expand All" action */
	private class ExpandAllAction extends Action
	{
		/** Constructs the "Expand All" action */
		ExpandAllAction() { setText("Expand All"); }
		
		/** Expands the currently selected node in the tree */
		public void run()
		{ 
			if (((SchemaElementContentProvider)viewer.getContentProvider()).getSchemaView().getSchema().shouldExpandAll())
				viewer.expandToLevel(((TreeSelection)viewer.getSelection()).getPaths()[0], TreeViewer.ALL_LEVELS);

		}
		
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
	
	/** Stores reference to the tree viewer */
	private TreeViewer viewer = null;
	
	/** Constructs the Schema Menu Manager */
	public SchemaMenuManager(TreeViewer viewer)
	{
		this.viewer = viewer;
		setRemoveAllWhenShown(true);
		addMenuListener(this);
	}
	
	/** Generate the context menu before being displayed */
	public void menuAboutToShow(IMenuManager menuManager)
	{
		if (((SchemaElementContentProvider)viewer.getContentProvider()).getSchemaView().getSchema().shouldExpandAll())
			menuManager.add(new ExpandAllAction());
		menuManager.add(new CollapseAllAction());
	}
}