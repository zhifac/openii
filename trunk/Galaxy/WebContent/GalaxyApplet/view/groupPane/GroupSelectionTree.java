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

import org.mitre.schemastore.model.Group;

/** Constructs the group selection tree */
public class GroupSelectionTree extends JTree implements MouseListener
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
	
    // Unused listener events
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}