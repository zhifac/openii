// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane;

import model.UniversalObjects;
import view.groupPane.managePane.editPane.GroupEditPane;
import view.sharedComponents.LinkedTreeNode;

/** Class for representing group tree nodes */
public class GroupTreeNode extends LinkedTreeNode
{
	// Link constants
	private static final Integer EDIT_STRUCTURE = 1;
	private static final Integer MANAGE_SCHEMAS = 2;
	private static final Integer MANAGE_GROUPS = 3;
	
	/** Stores a link to the group pane */
	private ManageGroupPane groupPane = null;
	
	/** Constructs a group tree node */
	GroupTreeNode(Object object, ManageGroupPane groupPane)
	{
		// Store the group node object
		super(object);
		this.groupPane = groupPane;
		
		// Determine what link exists for this node
		if(object instanceof Integer)
			link = UniversalObjects.isGroup((Integer)object) ? MANAGE_SCHEMAS : MANAGE_GROUPS;
		else if(object instanceof String && (object.equals("Groups"))) link = EDIT_STRUCTURE;
	}
	
	/** Returns the link string associated with the group tree node */
	public String getLinkString()
	{
		if(link==EDIT_STRUCTURE) return "Edit Structure";
		else if(link==MANAGE_SCHEMAS) return "Set Schemas";
		else if(link==MANAGE_GROUPS) return "Set Groups";
		return "";
	}
	
	/** Performs the link action */
	public void executeLinkAction()
	{
		if(link.equals(EDIT_STRUCTURE))
			groupPane.editInfo(GroupEditPane.EDIT_STRUCTURE,null);
		else if(link.equals(MANAGE_SCHEMAS))
			groupPane.editInfo(GroupEditPane.MANAGE_SCHEMAS,(Integer)getUserObject());
		else if(link.equals(MANAGE_GROUPS))
			groupPane.editInfo(GroupEditPane.MANAGE_GROUPS,(Integer)getUserObject());
	}
}
