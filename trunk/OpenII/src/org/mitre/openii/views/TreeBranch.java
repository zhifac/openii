package org.mitre.openii.views;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/** Class for displaying a tree branch within the tree */
public class TreeBranch<Item>
{
	/** Stores the list of items displayed in the tree branch */
	ArrayList<Item> items = null;
	
	/** Constructs the tree branch */
	public TreeBranch(Tree tree, String groupName, ArrayList<Item> items)
	{
		// Stores the items to be displayed in this branch
		this.items = items;
		
		// Construct the branch
		TreeItem branch = new TreeItem(tree, 0);
		branch.setText(groupName);
		
		// Construct the branch items
		for(Item item : items)
		{
			TreeItem branchItem = new TreeItem(branch, SWT.NONE);
			branchItem.setText(item.toString());
		}
	}
}