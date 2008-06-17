// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane.editPane;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.Groups;
import model.listeners.GroupsListener;

import org.mitre.schemastore.model.Group;

import view.sharedComponents.LinkedTree;

/** Constructs the group tree */
public class GroupTree extends LinkedTree implements GroupsListener
{	
	/** Stores the edit pane */
	private EditGroupStructuresPane editPane = null;
	
	/** Tracks the location of all groups in the tree */
	private HashMap<Integer,GroupTreeNode> groupHash = new HashMap<Integer,GroupTreeNode>();

	/** Get the specified group node */
	private DefaultMutableTreeNode generateGroupNode(Group group)
	{
		GroupTreeNode groupNode = new GroupTreeNode(group.getId(),editPane);
		groupHash.put(group.getId(), groupNode);
		for(Group subgroup : Groups.sort(Groups.getSubGroups(group.getId())))
			groupNode.add(generateGroupNode(subgroup));
		return groupNode;
	}
    
	/** Constructs the group tree */
    public GroupTree(EditGroupStructuresPane editPane)
    {
    	this.editPane = editPane;
    	
 		// Construct the tree
		GroupTreeNode root = new GroupTreeNode("Groups",editPane);
		ArrayList<Group> baseGroups = Groups.sort(Groups.getBaseGroups());
		for(Group group : baseGroups)
			root.add(generateGroupNode(group));
		
    	// Initialize the tree
		setModel(new DefaultTreeModel(root));
		setBorder(new EmptyBorder(0,3,0,0));
    	setCellRenderer(new GroupTreeNodeRenderer());
    }

	/** Handles the addition of a group */
	public void groupAdded(Integer groupID)
	{
		// Get parent tree node
		Group group = Groups.getGroup(groupID);
		Integer parentID = group.getParentId();
		GroupTreeNode parentNode = parentID==null ? (GroupTreeNode)getModel().getRoot() : groupHash.get(parentID);
		
		// Find the location for placing the group
		int loc = 0;
		for(loc=0; loc<parentNode.getChildCount(); loc++)
		{
			Integer currGroupID = (Integer)((GroupTreeNode)parentNode.getChildAt(loc)).getUserObject();
			if(Groups.getGroup(currGroupID).getName().compareTo(group.getName())>0) break;
		}
		
		// Insert the group in the identified tree location
		GroupTreeNode groupNode = new GroupTreeNode(groupID,editPane);
		parentNode.insert(groupNode);
		int[] insertedIndices = {parentNode.getChildCount()-1};
		((DefaultTreeModel)getModel()).nodesWereInserted(parentNode, insertedIndices);			
		groupHash.put(group.getId(), groupNode);
	}
	
	/** Handles the updating of a group */
	public void groupUpdated(Integer groupID)
		{ repaint(); }
	
	/** Handles the removal of a group */
	public void groupRemoved(Integer groupID)
	{
		GroupTreeNode groupNode = groupHash.get(groupID);
		GroupTreeNode parentNode = (GroupTreeNode)groupNode.getParent();
		int[] removedIndices = {parentNode.getIndex(groupNode)};
		parentNode.remove(groupNode);
		((DefaultTreeModel)getModel()).nodesWereRemoved(parentNode, removedIndices, null);
		groupHash.remove(groupID);
	}

	// Unused listener events
	public void schemaGroupAdded(Integer schemaID, Integer groupID) {}
	public void schemaGroupRemoved(Integer schemaID, Integer groupID) {}
}