package org.mitre.harmony.model.filters;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.schemaTree.SchemaTree;
import org.mitre.schemastore.model.SchemaElement;

/** Class for storing the focus */
public class Focus
{
	/** Stores the focused schema */
	private Integer schemaID;
	
	/** Stores the focused schema element */
	private Integer elementID;
	
	/** Stores the list of all elements in focus */
	private ArrayList<Integer> focusedElements = new ArrayList<Integer>();
	
	/** Constructs the focus object */
	public Focus(Integer schemaID, Integer elementID, HarmonyModel harmonyModel)
	{
		this.schemaID = schemaID;
		this.elementID = elementID;
		focusedElements.add(elementID);
		for(SchemaElement element : harmonyModel.getSchemaManager().getDescendantElements(schemaID, elementID))
			focusedElements.add(element.getId());
	}
	
	/** Returns the focused schema */
	public Integer getSchemaID() { return schemaID; }
	
	/** Returns the focused schema object */
	public Integer getElementID() { return elementID; }
	
	/** Indicates if the specified element is within focus */
	public boolean contains(Integer elementID)
		{ return focusedElements.contains(elementID); }

	/** Indicates if the specified node is within focus */
	public boolean contains(DefaultMutableTreeNode node)
	{
		// First verify that the schema is being focused on
		Integer schemaID = SchemaTree.getSchema(node);
		if(!getSchemaID().equals(schemaID)) return false;
		
		// Check to make sure node exists on path that is focused on
		Object elementID = node.getUserObject();
		while(elementID instanceof Integer && contains((Integer)elementID))
		{
			if(this.elementID.equals(elementID)) return true;
			node = (DefaultMutableTreeNode)node.getParent();
			elementID = node.getUserObject();
		}
		return false;
	}
}

