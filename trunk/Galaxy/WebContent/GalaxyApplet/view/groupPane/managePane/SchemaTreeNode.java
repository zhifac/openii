// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane;

import model.UniversalObjects;
import view.groupPane.managePane.editPane.GroupEditPane;
import view.sharedComponents.LinkedTreeNode;

/** Class for representing schema tree nodes */
public class SchemaTreeNode extends LinkedTreeNode
{
	// Link constants
	private static final Integer MANAGE_GROUPS = 1;
	private static final Integer MANAGE_SCHEMAS = 2;
	
	/** Stores a link to the group pane */
	private ManageGroupPane groupPane = null;
	
	/** Constructs a schema tree node */
	SchemaTreeNode(Object object, ManageGroupPane groupPane)
	{
		// Store the schema node object
		super(object);
		this.groupPane = groupPane;
		
		// Determine what link exists for this node
		if(object instanceof Integer)
			link = UniversalObjects.isGroup((Integer)object) ? MANAGE_SCHEMAS : MANAGE_GROUPS;
	}
	
	/** Returns the link string associated with the schema tree node */
	public String getLinkString()
		{ return link==MANAGE_GROUPS ? "Set Groups" : link==MANAGE_SCHEMAS ? "Set Schemas": ""; }
	
	/** Performs the link action */
	public void executeLinkAction()
	{
		if(link.equals(MANAGE_GROUPS))
			groupPane.editInfo(GroupEditPane.MANAGE_GROUPS,(Integer)getUserObject()); 
		else if(link.equals(MANAGE_SCHEMAS))
			groupPane.editInfo(GroupEditPane.MANAGE_SCHEMAS,(Integer)getUserObject());
	}
}
