package org.mitre.harmony.model.filters;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.view.schemaTree.SchemaTree;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/** Class for storing the focus */
public class Focus
{
	/** Stores the graph being focused on */
	private HierarchicalGraph graph;
	
	/** Stores the focused schema element */
	private Integer elementID;

	/** Stores the list of hidden elements */
	private HashSet<Integer> hiddenElements = new HashSet<Integer>();
	
	/** Stores the list of all elements in focus */
	private ArrayList<Integer> focusedElements;
	
	/** Returns the list of descendant elements of the specified element */
	private ArrayList<Integer> getDescendantElements(Integer elementID)
	{
		ArrayList<Integer> elementIDs = new ArrayList<Integer>();
		for(SchemaElement childElement : graph.getChildElements(elementID))
			if(!hiddenElements.contains(childElement))
			{
				elementIDs.add(childElement.getId());
				elementIDs.addAll(getDescendantElements(childElement.getId()));
			}
		return elementIDs;
	}
	
	/** Returns the list of elements in focus */
	private ArrayList<Integer> getFocusedElements()
	{
		if(focusedElements==null)
		{
			focusedElements = new ArrayList<Integer>();
			focusedElements.add(elementID);
			focusedElements.addAll(getDescendantElements(elementID));
		}
		return focusedElements;
	}
	
	/** Constructs the focus object */
	public Focus(Integer schemaID, Integer elementID, HarmonyModel harmonyModel)
	{
		graph = harmonyModel.getSchemaManager().getGraph(schemaID);
		this.elementID = elementID;
	}
	
	/** Hide an element within focus */
	public void hideElement(Integer elementID)
		{ hiddenElements.add(elementID); focusedElements=null; }
	
	/** Unhide an element within focus */
	public void unhideElement(Integer elementID)
		{ hiddenElements.remove(elementID); focusedElements=null; }
	
	/** Returns the focused schema */
	public Integer getSchemaID() { return graph.getSchema().getId(); }
	
	/** Returns the focused schema element */
	public Integer getElementID() { return elementID; }
	
	/** Returns the list of hidden elements */
	public HashSet<Integer> getHiddenElements()
		{ return hiddenElements; }
	
	/** Indicates if the specified element is within focus */
	public boolean contains(Integer elementID)
		{ return getFocusedElements().contains(elementID); }

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