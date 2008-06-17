// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane;

import java.util.ArrayList;

import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;

import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

import view.sharedComponents.LinkedTree;

import model.Groups;
import model.Schemas;
import model.listeners.GroupsListener;
import model.listeners.SchemasListener;

/** Class for displaying the schema tree */
class SchemaTree extends LinkedTree implements GroupsListener, SchemasListener
{
	/** Stores the parent of this schema tree */
	private ManageGroupPane parent;

	/** Stores the root of this schema tree */
	private SchemaTreeNode root = generateSchemaTreeNode("Schemas");
	
	/** Generates a schema tree node */
	private SchemaTreeNode generateSchemaTreeNode(Object object)
		{ return new SchemaTreeNode(object,parent); }
	
	/** Gets the specified schema node */
	private SchemaTreeNode getSchemaNode(Integer schemaID)
	{
		for(int i=0; i<root.getChildCount(); i++)
		{
			SchemaTreeNode schemaNode = (SchemaTreeNode)root.getChildAt(i);
			if(schemaID.equals(schemaNode.getUserObject()))
				return schemaNode;
		}
		return null;
	}
	
	/** Update schema node */
	private void updateSchemaNode(SchemaTreeNode schemaNode)
	{
		// Clears out the schema node
		schemaNode.removeAllChildren();
		
		// Retrieve the groups associated with the schema node
		Integer schemaID = (Integer)schemaNode.getUserObject();
		ArrayList<Integer> groupIDs = Groups.sortByID(Groups.getSchemaGroups(schemaID));
		if(groupIDs.size()>0)
			for(Integer groupID : groupIDs) schemaNode.add(generateSchemaTreeNode(groupID));
		else schemaNode.add(generateSchemaTreeNode("<No Groups>"));			

		// Informs the tree model that the node structure has changed
		((DefaultTreeModel)getModel()).nodeStructureChanged(schemaNode);
	}
	
	/** Constructs the schema tree */
	SchemaTree(ManageGroupPane parent)
	{
		this.parent = parent;

		// Construct the schema tree
		ArrayList<Schema> schemas = Schemas.sort(Schemas.getSchemas());
		for(Schema schema : schemas)
		{
			SchemaTreeNode schemaNode = generateSchemaTreeNode(schema.getId());			
			updateSchemaNode(schemaNode);
			root.add(schemaNode);
		}
			
		// Initialize the group tree
		setBorder(new EmptyBorder(0,3,0,0));
		setModel(new DefaultTreeModel(root));
		setCellRenderer(new SchemaTreeCellRenderer());
	}

	/** Handles the addition of a schema */
	public void schemaAdded(Schema schema)
	{ 
		// Determine where to place the schema in the tree
		int loc = 0;
		for(loc=0; loc<root.getChildCount(); loc++)
		{
			Integer schemaID = (Integer)((SchemaTreeNode)root.getChildAt(loc)).getUserObject();
			if(Schemas.getSchema(schemaID).getName().compareTo(schema.getName())>0) break;
		}
		
		// Insert the schema in the identified tree location
		SchemaTreeNode schemaNode = generateSchemaTreeNode(schema.getId());
		updateSchemaNode(schemaNode);
		root.insert(schemaNode,loc);
		((DefaultTreeModel)getModel()).nodeStructureChanged(root);
	}
	
	/** Handles the removal of a schema */
	public void schemaRemoved(Schema schema)
	{ 
		SchemaTreeNode schemaNode = getSchemaNode(schema.getId());
		if(schemaNode!=null)
			{ root.remove(schemaNode); ((DefaultTreeModel)getModel()).nodeStructureChanged(root); }
	}

	/** Handles the addition of a schema group */
	public void schemaGroupAdded(Integer schemaID, Integer groupID)
	{
		updateSchemaNode(getSchemaNode(schemaID));
		for(Integer descendantID : Schemas.getDescendantSchemas(schemaID))
			updateSchemaNode(getSchemaNode(descendantID));
	}
	
	/** Handles the removal of a schema group */
	public void schemaGroupRemoved(Integer schemaID, Integer groupID)
	{
		updateSchemaNode(getSchemaNode(schemaID));
		for(Integer descendantID : Schemas.getDescendantSchemas(schemaID))
			updateSchemaNode(getSchemaNode(descendantID));
	}
	
	// Unused listener event
	public void schemaUpdated(Schema schema) {}
	public void schemaLocked(Schema schema) {}
	public void schemaElementAdded(SchemaElement schemaElement) {}
	public void schemaElementRemoved(SchemaElement schemaElement) {}
	public void schemaParentsUpdated(Schema schema) {}
	public void groupAdded(Integer groupID) {}
	public void groupUpdated(Integer groupID) {}
	public void groupRemoved(Integer groupID) {}
}