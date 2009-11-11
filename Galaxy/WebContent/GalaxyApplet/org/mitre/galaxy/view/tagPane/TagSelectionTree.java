// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.tagPane;

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

import org.mitre.galaxy.model.Tags;
import org.mitre.schemastore.model.Tag;

/** Constructs the tag selection tree */
public class TagSelectionTree extends JTree implements MouseListener
{
	/** Stores the selected tags */
	private SelectedTags selectedTags;
	
	/** Tracks the location of all tags in the tree */
	private HashMap<Integer,DefaultMutableTreeNode> tagHash = new HashMap<Integer,DefaultMutableTreeNode>();

	/** Get the specified tag node */
	private DefaultMutableTreeNode generateTagNode(Tag tag)
	{
		DefaultMutableTreeNode tagNode = new DefaultMutableTreeNode(tag.getId());
		tagHash.put(tag.getId(), tagNode);
		for(Tag subcategory : Tags.sort(Tags.getSubcategories(tag.getId())))
			tagNode.add(generateTagNode(subcategory));
		return tagNode;
	}
	
	/** Return the tag id for the specified path */
	private Integer getTagID(TreePath path)
	{
		if(path==null) return null;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		Object object = node.getUserObject();
		if(!(object instanceof Integer)) return null;
		return (Integer)object;
	}
    
	/** Constructs the tag selection tree */
    public TagSelectionTree(SelectedTags selectedTags)
    {
    	// Stores the selected tags
    	this.selectedTags = selectedTags;
    	
		// Construct the tree
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Tags");
		ArrayList<Tag> baseTags = Tags.sort(Tags.getBaseTags());
		for(Tag tag : baseTags)
			root.add(generateTagNode(tag));
		
    	// Initialize the tree
		setModel(new DefaultTreeModel(root));
		setBorder(new EmptyBorder(0,3,0,0));
    	setCellRenderer(new TagSelectionTreeRenderer());
    	addMouseListener(this);
    }
    
    /** Indicates if the specified path is selected */
    public boolean isPathSelected(TreePath path)
    	{ return selectedTags.isSelected(getTagID(path)); }
    
    /** Indicates if the specified path is inferred */
    public boolean isPathInferred(TreePath path)
    	{ return selectedTags.isInferred(getTagID(path)); }
    
    /** Returns the list of all selected tags */
    public ArrayList<Integer> getSelectedTags()
    	{ return selectedTags.getSelectedTags(); }
    
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
				Integer tagID = getTagID(path);
				if(!isPathSelected(path)) selectedTags.add(tagID);
				else selectedTags.remove(tagID);
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