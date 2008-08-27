// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import model.Groups;
import model.listeners.GroupsListener;

import org.mitre.schemastore.model.Group;

/** Constructs the group selection tree */
public class GroupSelectionTree extends JTree implements MouseListener, GroupsListener
{
	/** Stores the selected groups */
	private SelectedGroups selectedGroups;
	
	/** Tracks the location of all groups in the tree */
	private HashMap<Integer,DefaultMutableTreeNode> groupHash = new HashMap<Integer,DefaultMutableTreeNode>();

	/** Get the specified group node */
	private DefaultMutableTreeNode generateGroupNode(Group group)
	{
		DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group.getId());
		groupHash.put(group.getId(), groupNode);
		for(Group subgroup : Groups.sort(Groups.getSubGroups(group.getId())))
			groupNode.add(generateGroupNode(subgroup));
		return groupNode;
	}
	
	/** Return the group id for the specified path */
	private Integer getGroupID(TreePath path)
	{
		if(path==null) return null;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		Object object = node.getUserObject();
		if(!(object instanceof Integer)) return null;
		return (Integer)object;
	}
    
	/** Constructs the group selection tree */
    public GroupSelectionTree(SelectedGroups selectedGroups)
    {
    	// Stores the selected groups
    	this.selectedGroups = selectedGroups;
    	
		// Construct the tree
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Groups");
		ArrayList<Group> baseGroups = Groups.sort(Groups.getBaseGroups());
		for(Group group : baseGroups)
			root.add(generateGroupNode(group));
		
    	// Initialize the tree
		setModel(new DefaultTreeModel(root));
		setBorder(new EmptyBorder(0,3,0,0));
    	setCellRenderer(new GroupSelectionTreeRenderer());
    	addMouseListener(this);
    }
    
    /** Indicates if the specified path is selected */
    public boolean isPathSelected(TreePath path)
    	{ return selectedGroups.isSelected(getGroupID(path)); }
    
    /** Indicates if the specified path is inferred */
    public boolean isPathInferred(TreePath path)
    	{ return selectedGroups.isInferred(getGroupID(path)); }
    
    /** Returns the list of all selected groups */
    public ArrayList<Integer> getSelectedGroups()
    	{ return selectedGroups.getSelectedGroups(); }
    
	/** Checks to see if mouse pressed check box */
	public void mousePressed(MouseEvent e)
	{
		int row = getRowForLocation(e.getX(), e.getY());
		TreePath path = getPathForRow(row);
		if(row>=1 && !isPathInferred(path))
		{
			Rectangle rect = getRowBounds(row);
			if(rect!=null && e.getX()-rect.x<20)
			{
				Integer groupID = getGroupID(path);
				if(!isPathSelected(path)) selectedGroups.add(groupID);
				else selectedGroups.remove(groupID);
			}
			repaint();
		}
	}

	/** Handles the addition of a group */
	public void groupAdded(Integer groupID)
	{
		// Get parent tree node
		Group group = Groups.getGroup(groupID);
		Integer parentID = group.getParentId();
		DefaultMutableTreeNode parentNode = parentID==null ? (DefaultMutableTreeNode)getModel().getRoot() : groupHash.get(parentID);
		
		// Find the location for placing the group
		int loc = 0;
		for(loc=0; loc<parentNode.getChildCount(); loc++)
		{
			Integer currGroupID = (Integer)((DefaultMutableTreeNode)parentNode.getChildAt(loc)).getUserObject();
			if(Groups.getGroup(currGroupID).getName().compareTo(group.getName())>0) break;
		}

		// Insert the group in the identified tree location
		DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(groupID);
		parentNode.insert(groupNode,loc);
		int[] insertedIndices = {loc};
		((DefaultTreeModel)getModel()).nodesWereInserted(parentNode, insertedIndices);			
		groupHash.put(group.getId(), groupNode);
		
		// Refreshes the selected/inferred groups
		selectedGroups.refresh(); repaint();
	}
	
	/** Handles the updating of a group */
	public void groupUpdated(Integer groupID)
		{ repaint(); }
	
	/** Handles the removal of a group */
	public void groupRemoved(Integer groupID)
	{
		// Modify the tree to no longer contain the group
		DefaultMutableTreeNode groupNode = groupHash.get(groupID);
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)groupNode.getParent();
		int[] removedIndices = {parentNode.getIndex(groupNode)};
		parentNode.remove(groupNode);
		((DefaultTreeModel)getModel()).nodesWereRemoved(parentNode, removedIndices, null);
		groupHash.remove(groupID);
		
		// Remove the group from being selected
		if(selectedGroups.isSelected(groupID)) selectedGroups.remove(groupID);
	}
	
    // Unused listener events
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void schemaGroupAdded(Integer schemaID, Integer groupID) {}
	public void schemaGroupRemoved(Integer schemaID, Integer groupID) {}
}