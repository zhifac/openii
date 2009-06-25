package org.mitre.openii.views.repositories.menu;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.mitre.openii.views.manager.SchemaInGroup;
import org.mitre.openii.views.manager.SchemaInMapping;
import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Schema;

/** Handles the displaying of the Repository popup menu */
public class RepositoryMenuManager extends MenuManager implements IMenuListener
{
	/** Stores reference to the tree */
	private Tree tree = null;

	/** Stores reference to the currently selected element */
	private Object element = null;
	
	/** Constructs the Repository Menu Manager */
	public RepositoryMenuManager(Tree tree)
	{
		this.tree = tree;
		setRemoveAllWhenShown(true);
		addMenuListener(this);
	}
	
	/** Returns the selected element */
	public Object getElement()
		{ return element; }
	
	/** Returns the id associated with the selected element */
	public Integer getElementID()
	{
		if(element instanceof Schema) return ((Schema)element).getId();
		if(element instanceof SchemaInGroup) return ((SchemaInGroup)element).getSchema().getId();
		if(element instanceof SchemaInMapping) return ((SchemaInMapping)element).getSchema().getId();
		if(element instanceof Group) return ((Group)element).getId();
		if(element instanceof Mapping) return ((Mapping)element).getId();
		return null;
	}

	/** Generates the action menu */
	private void getManagerMenu(IMenuManager menuManager)
	{
		// Display the menu for the "Repositories" header
		if(element instanceof String && element.equals("Repositories"))
			menuManager.add(new RepositoryAction(this,"Add Repository",RepositoryAction.ADD_REPOSITORY));
			
		// Display the menu for a selected repository
		else
		{
			menuManager.add(new RepositoryAction(this,"Edit Repository",RepositoryAction.EDIT_REPOSITORY));
			menuManager.add(new RepositoryAction(this,"Delete Repository",RepositoryAction.DELETE_REPOSITORY));
		}
	}
	
	/** Generate the context menu before being displayed */
	public void menuAboutToShow(IMenuManager menuManager)
	{		
	//	element = ((StructuredSelection)tree.getSelection()).getFirstElement();
		getManagerMenu(menuManager);		
	}
}