// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.explorerPane;

import javax.swing.tree.DefaultMutableTreeNode;


/** Class for representing schema tree nodes */
public class SchemaTreeNode extends DefaultMutableTreeNode
{
	/** Constructs the schema tree node */
	public SchemaTreeNode(Object object)
		{ super(object); }
	
	/** Retrieves the specified child node */
	public SchemaTreeNode getChildNode(Object object)
	{
		for(int i=0; i<getChildCount(); i++)
			if(((SchemaTreeNode)getChildAt(i)).contains(object))
				return (SchemaTreeNode)getChildAt(i);
		return null;
	}
	
	/** Indicates that this node is contains to the specified object */
	public boolean contains(Object object)
	{
		// First make simple check of equality
		if(userObject.equals(object)) return true;
		
		// Calculates the node id
		Integer nodeID = null;
		if(userObject instanceof Integer) nodeID = (Integer)userObject;
		else if(userObject instanceof AliasedSchemaElement) nodeID = ((AliasedSchemaElement)userObject).getId();
		if(nodeID==null) return false;
		
		// Calculates the object id
		Integer objectID = null;
		if(object instanceof Integer) objectID = (Integer)object;
		else if(object instanceof AliasedSchemaElement) objectID = ((AliasedSchemaElement)object).getId();
		if(objectID==null) return false;
		
		// Indicate if the node contains the specified object
		return nodeID.equals(objectID);
	}
}