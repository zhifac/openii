// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane.editPane;

/** Abstract class for representing a group tree nodes */
public class GroupTreeNode extends view.sharedComponents.LinkedTreeNode
{
	// Link constant
	private static final Integer EDIT_STRUCTURE = 1;
	
	/** Stores a link to the edit pane */
	private EditGroupStructuresPane editPane = null;
	
	/** Constructs a group tree node */
	protected GroupTreeNode(Object object, EditGroupStructuresPane editPane)
	{
		super(object);
		this.editPane = editPane;
		link = EDIT_STRUCTURE;
	}

	/** Returns the link string associated with the tree node */
	public String getLinkString()
		{ return "Edit Groups"; }
	
	/** Performs the link action */
	public void executeLinkAction()
	{
		Integer groupID = getUserObject() instanceof Integer ? (Integer)getUserObject() : null;
		editPane.displaySecondaryPane(groupID);
	}
}
