package org.mitre.harmony.model.filters;

import java.util.ArrayList;

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
	
	/** Stores the list of focused IDs */
	private ArrayList<Integer> focusIDs = new ArrayList<Integer>();
	
	/** Stores the list of hidden IDs */
	private ArrayList<Integer> hiddenIDs = new ArrayList<Integer>();
	
	/** Stores the list of all elements in focus */
	private ArrayList<Integer> elementsInFocus;
	
	/** Returns the list of ancestor IDs for the specified element */
	private ArrayList<Integer> getAncestorIDs(Integer elementID)
	{
		ArrayList<Integer> ancestorIDs = new ArrayList<Integer>();
		if(!hiddenIDs.contains(elementID))
			for(SchemaElement childElement : graph.getChildElements(elementID))
			{
				ancestorIDs.add(childElement.getId());
				ancestorIDs.addAll(getAncestorIDs(childElement.getId()));
			}
		return ancestorIDs;
	}
	
	/** Returns the list of elements in focus */
	private ArrayList<Integer> getFocusedElements()
	{
		if(elementsInFocus==null)
		{
			// Identify the root IDs
			ArrayList<Integer> rootIDs = new ArrayList<Integer>(focusIDs);
			if(rootIDs.size()==0)
				for(SchemaElement element : graph.getRootElements())
					rootIDs.add(element.getId());

			// Generate the elements in focus
			elementsInFocus = new ArrayList<Integer>();
			for(Integer elementID : rootIDs)
			{
				elementsInFocus.add(elementID);
				elementsInFocus.addAll(getAncestorIDs(elementID));
			}
		}
		return elementsInFocus;
	}
	
	/** Constructs the focus object */
	public Focus(Integer schemaID, HarmonyModel harmonyModel)
		{ graph = harmonyModel.getSchemaManager().getGraph(schemaID); }
	
	/** Returns the schema associated with this focus */
	public Integer getSchemaID() { return graph.getSchema().getId(); }
	
	/** Returns the focus IDs */
	public ArrayList<Integer> getFocusedIDs() { return focusIDs; }

	/** Returns the hidden IDs */
	public ArrayList<Integer> getHiddenIDs() { return hiddenIDs; }
	
	/** Adds a focus */
	public void addFocus(Integer elementID)
	{
		// Get ancestors of the specified element
		ArrayList<Integer> descendantIDs = new ArrayList<Integer>();
		for(SchemaElement descendant : graph.getDescendantElements(elementID))
			descendantIDs.add(descendant.getId());
		
		// Remove focus elements which are superseded by the new focus
		for(Integer focusID : new ArrayList<Integer>(focusIDs))
			if(descendantIDs.contains(focusID))
				focusIDs.remove(focusID);
		
		// Adds the focus element
		focusIDs.add(elementID);
		elementsInFocus=null;
	}
	
	/** Removes a focus */
	public void removeFocus(Integer elementID)
		{ focusIDs.remove(elementID); elementsInFocus=null; }
	
	/** Adds a hidden element */
	public void addHiddenElement(Integer elementID)
		{ hiddenIDs.add(elementID); elementsInFocus=null; }
	
	/** Removes a hidden element */
	public void removeHiddenElement(Integer elementID)
		{ hiddenIDs.remove(elementID); elementsInFocus=null; }
	
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
			if(focusIDs.contains(elementID)) return true;
			node = (DefaultMutableTreeNode)node.getParent();
			elementID = node.getUserObject();
		}
		return false;
	}
}