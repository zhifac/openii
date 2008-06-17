// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.mitre.schemastore.model.Group;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import view.sharedComponents.LinkedTree;
import view.sharedComponents.LinkedTreeNode;
import view.sharedComponents.LinkedTreeNodeRenderer;

import model.Groups;
import model.Schemas;
import model.SelectedObjects;
import model.UniversalObjects;
import model.listeners.GroupsListener;
import model.listeners.SchemasListener;

/** Class for displaying the group tree */
class GroupTree extends LinkedTree implements GroupsListener, SchemasListener, TreeExpansionListener
{	
	/** Stores the parent of this group tree */
	private ManageGroupPane parent;
	
	/** Tracks the location of all groups in the tree */
	private HashMap<Integer,GroupTreeNode> groupHash = new HashMap<Integer,GroupTreeNode>();
	
	/** Generates a group tree node */
	private GroupTreeNode getGroupTreeNode(Object object)
	{
		GroupTreeNode groupNode = new GroupTreeNode(object,parent);
		if(object instanceof Integer) groupHash.put((Integer)object, groupNode);
		return groupNode;
	}
	
	/** Update group schemas */
	private void updateGroupSchemas(GroupTreeNode groupNode)
	{
		// Clears out the schema node
		for(int i=0; i<groupNode.getChildCount(); i++)
		{
			GroupTreeNode childNode = (GroupTreeNode)groupNode.getChildAt(i);
			if(!(childNode.getUserObject() instanceof Integer) || UniversalObjects.isSchema((Integer)childNode.getUserObject()))
				{ groupNode.remove(i); i--; }
		}
		
		// Retrieve the schemas associated with the group node
		Integer groupID = (Integer)groupNode.getUserObject();
		ArrayList<Integer> schemaIDs = Schemas.sortByID(Groups.getGroupSchemas(groupID));

		// Add groups to the schema node
		if(schemaIDs.size()>0)
			for(Integer schemaID : schemaIDs) groupNode.add(getGroupTreeNode(schemaID));
		if(groupNode.getChildCount()==0) groupNode.add(getGroupTreeNode("<No Schemas>"));

		// Informs the tree model that the node structure has changed
		((DefaultTreeModel)getModel()).nodeStructureChanged(groupNode);
	}

	/** Update unassigned schemas */
	private void updateUnassignedSchemas()
	{
		// Get the list of unassigned schemas
		ArrayList<Integer> unassignedSchemas = Schemas.sortByID(Groups.getUnassignedSchemas());
		
		// Get the unassigned schema node
		GroupTreeNode root = (GroupTreeNode)getModel().getRoot();
		GroupTreeNode unassignedNode = null;
		if(root.getChildCount()>0 && ((GroupTreeNode)root.getChildAt(root.getChildCount()-1)).getUserObject() instanceof String)
			unassignedNode = (GroupTreeNode)root.getChildAt(root.getChildCount()-1);
		
		// Clears out the unassigned schema node if no unassigned schemas left
		if(unassignedNode!=null && unassignedSchemas.size()==0)
		{
			root.remove(root.getChildCount()-1);
			int[] removedIndices = {root.getChildCount()-1};
			((DefaultTreeModel)getModel()).nodesWereRemoved(root, removedIndices, null);
		}

		// Add unassigned schema node if needed
		if(unassignedNode==null && unassignedSchemas.size()>0)
		{
			unassignedNode = getGroupTreeNode("Unassigned");
			root.add(unassignedNode);
			int[] insertedIndices = {root.getChildCount()-1};
			((DefaultTreeModel)getModel()).nodesWereInserted(root, insertedIndices);			
		}
		
		// Insert unassigned schemas into the unassigned schema node
		if(unassignedSchemas.size()>0)
		{
			unassignedNode.removeAllChildren();
			for(Integer unassignedSchema : unassignedSchemas)
				unassignedNode.add(getGroupTreeNode(unassignedSchema));
			((DefaultTreeModel)getModel()).nodeStructureChanged(unassignedNode);
		}
	}	
	
	/** Expands the specified group */
	private void expandGroup(GroupTreeNode groupNode)
	{
		// Don't proceed if not a group or already expanded
		boolean isGroup = groupNode.getUserObject() instanceof Integer && UniversalObjects.isGroup((Integer)groupNode.getUserObject());
		if(!isGroup || groupNode.getChildCount()>0) return;
		
		// Retrieve the root group
		Integer groupID = (Integer)groupNode.getUserObject();
		
		// Display the subgroups
		ArrayList<Group> subgroups = Groups.sort(Groups.getSubGroups(groupID));
		for(Group subgroup : subgroups)
		{
			GroupTreeNode subgroupNode = getGroupTreeNode(subgroup.getId());
			groupNode.add(subgroupNode);
		}
		
		// Display the schemas
		updateGroupSchemas(groupNode);
	}
	
	/** Constructs the group tree */
	GroupTree(ManageGroupPane parent)
	{
		this.parent = parent;

		// Construct the base groups
		GroupTreeNode root = getGroupTreeNode("Groups");
		ArrayList<Group> baseGroups = Groups.sort(Groups.getBaseGroups());
		for(Group group : baseGroups)
			root.add(getGroupTreeNode(group.getId()));
			
		// Initialize the group tree
		setBorder(new EmptyBorder(0,3,0,0));
		setModel(new DefaultTreeModel(root));
		setCellRenderer(new GroupTreeNodeRenderer());

		// Listens for tree actions
		addTreeExpansionListener(this);
		
		// Expand out the first level of the group tree
		for(int i=0; i<root.getChildCount(); i++)
			expandGroup((GroupTreeNode)root.getChildAt(i));

		// Display the unassigned schemas
		updateUnassignedSchemas();
	}

	/** Handles the addition of a schema */
	public void schemaAdded(Schema schema)
		{ updateUnassignedSchemas(); }
	
	/** Handles the removal of a schema */
	public void schemaRemoved(Schema schema)
		{ updateUnassignedSchemas(); }

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
			Object object = ((GroupTreeNode)parentNode.getChildAt(loc)).getUserObject();
			if(!(object instanceof Integer) || UniversalObjects.isSchema((Integer)object)) break;
			if(Groups.getGroup((Integer)object).getName().compareTo(group.getName())>0) break;
		}
		
		// Insert the group in the identified tree location
		GroupTreeNode groupNode = getGroupTreeNode(groupID);
		parentNode.insert(groupNode,loc);
		int[] insertedIndices = {loc};
		((DefaultTreeModel)getModel()).nodesWereInserted(parentNode, insertedIndices);			
		groupHash.put(group.getId(), groupNode);
	}

	/** Handles the update of a group */
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

	/** Handles the addition of a schema group */
	public void schemaGroupAdded(Integer schemaID, Integer groupID)
	{
		if(groupHash.get(groupID)!=null)
			updateGroupSchemas(groupHash.get(groupID));
		updateUnassignedSchemas();
	}
	
	/** Handles the removal of a schema group */
	public void schemaGroupRemoved(Integer schemaID, Integer groupID)
	{
		if(groupHash.get(groupID)!=null)
			updateGroupSchemas(groupHash.get(groupID));
		updateUnassignedSchemas();
	}
	
	/** Generate group nodes as tree is expanded outward */
	public void treeExpanded(TreeExpansionEvent e)
	{
		// Only deal with expanding group nodes
		GroupTreeNode groupNode = (GroupTreeNode)e.getPath().getLastPathComponent();
		Object userObject = groupNode.getUserObject();
		if(userObject instanceof Integer && UniversalObjects.isGroup((Integer)userObject))
		{
			// Expand all sub groups
			for(int i=0; i<groupNode.getChildCount(); i++)
				expandGroup((GroupTreeNode)groupNode.getChildAt(i));
		}
	}
	
	/** Handles the selection of a schema */
	public void mouseClicked(MouseEvent e)
	{
		// Handles selection of new schema
		TreePath path = getPathForLocation(e.getX(),e.getY());
		if(path!=null && getCursor().equals(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			// Handles schema selections
			String link = ((LinkedTreeNode)path.getLastPathComponent()).getLinkString();
			Rectangle2D rowBounds = getRowBounds(getRowForPath(path));
			int linkLength = getFontMetrics(LinkedTreeNodeRenderer.linkFont).stringWidth("("+link+")");
			if(e.getX()<rowBounds.getMaxX()-linkLength)
			{
				Object object = ((GroupTreeNode)path.getLastPathComponent()).getUserObject();
				SelectedObjects.setSelectedSchema((Integer)object);
			}
			
			// Handles link selections
			else super.mouseClicked(e);
		}
	}
	
	/** Displays the mouse hand when hovering over a schema */
	public void mouseMoved(MouseEvent e)
	{
		// Identify the path over which the mouse is hovering
		TreePath path = getPathForLocation(e.getX(),e.getY());
		
		// Display a hand cursor if hovering above link
		Cursor cursor = Cursor.getDefaultCursor();
		if(path!=null)
		{
			Object object = ((LinkedTreeNode)path.getLastPathComponent()).getUserObject();
			if(object instanceof Integer && UniversalObjects.isSchema((Integer)object))
			{
				Rectangle2D rowBounds = getRowBounds(getRowForPath(path));
				if(e.getX()>rowBounds.getMinX()+20)
					cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
			}
			else { super.mouseMoved(e); return; }
		}
		if(!cursor.equals(getCursor())) setCursor(cursor);
	}
	
	// Unused listener events
	public void schemaUpdated(Schema schema) {}
	public void schemaLocked(Schema schema) {}
	public void schemaElementAdded(SchemaElement schemaElement) {}
	public void schemaElementRemoved(SchemaElement schemaElement) {}
	public void schemaParentsUpdated(Schema schema) {}
	public void treeCollapsed(TreeExpansionEvent e) {}
}