// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.sharedComponents;

import javax.swing.tree.DefaultMutableTreeNode;

import model.AliasedSchemaElement;
import model.UniversalObjects;

/** Abstract class for representing a linked tree nodes */
abstract public class LinkedTreeNode extends DefaultMutableTreeNode
{
	/** Stores any link associated with this node */
	protected Integer link = null;
	
	/** Constructs a schema tree node */
	protected LinkedTreeNode(Object object)
		{ super(object); }

	/** Retrieves the specified child node */
	public LinkedTreeNode getChildNode(Object object)
	{
		for(int i=0; i<getChildCount(); i++)
			if(((LinkedTreeNode)getChildAt(i)).contains(object))
				return (LinkedTreeNode)getChildAt(i);
		return null;
	}
	
	/** Adds the specified object to the tree node */
	public void insert(LinkedTreeNode childNode)
	{
		for(int i=0; i<getChildCount(); i++)
			if(((LinkedTreeNode)getChildAt(i)).toString().compareTo(childNode.toString())>=0)
				{ insert(childNode,i); return; }
		insert(childNode,getChildCount());
	}
	
	/** Deletes the specified object from the tree node */
	public void delete(Object object)
	{
		for(int i=0; i<getChildCount(); i++)
			if(((LinkedTreeNode)getChildAt(i)).contains(object))
				{ remove(i); break; }
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
	
	/** Indicates if the tree node contains a link */
	public Boolean containsLink()
		{ return link!=null; }

	/** Returns the link string associated with the tree node */
	abstract public String getLinkString();
	
	/** Performs the link action */
	abstract public void executeLinkAction();

	/** Returns the string representation of this node */
	public String toString()
	{
		if(userObject instanceof Integer) return UniversalObjects.getName((Integer)userObject);
		if(userObject instanceof AliasedSchemaElement) return ((AliasedSchemaElement)userObject).getName();
		return userObject.toString();
	}
}
