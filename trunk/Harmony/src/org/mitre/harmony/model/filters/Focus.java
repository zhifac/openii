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
	
	/** Stores the list of focused IDs */
	private ArrayList<ElementPath> focusPaths = new ArrayList<ElementPath>();
	
	/** Stores the list of hidden IDs */
	private ArrayList<Integer> hiddenIDs = new ArrayList<Integer>();
	
	/** Stores the list of all elements in focus */
	private HashSet<Integer> elementsInFocus;
	
	/** Stores the list of all hidden elements */
	private HashSet<Integer> hiddenElements;
	
	/** Returns the list of descendant IDs for the specified element */
	private ArrayList<Integer> getDescendantIDs(Integer elementID)
	{
		ArrayList<Integer> ancestorIDs = new ArrayList<Integer>();
		if(!hiddenIDs.contains(elementID))
			for(SchemaElement childElement : graph.getChildElements(elementID))
			{
				ancestorIDs.add(childElement.getId());
				ancestorIDs.addAll(getDescendantIDs(childElement.getId()));
			}
		return ancestorIDs;
	}
	
	/** Constructs the focus object */
	public Focus(Integer schemaID, HarmonyModel harmonyModel)
		{ graph = harmonyModel.getSchemaManager().getGraph(schemaID); }
	
	/** Returns the schema associated with this focus */
	public Integer getSchemaID() { return graph.getSchema().getId(); }
	
	/** Returns the focus paths */
	public ArrayList<ElementPath> getFocusedPaths() { return focusPaths; }

	/** Returns the focus IDs */
	public ArrayList<Integer> getFocusedIDs()
	{
		ArrayList<Integer> focusIDs = new ArrayList<Integer>();
		for(ElementPath focusPath : getFocusedPaths())
			focusIDs.add(focusPath.getElementID());
		return focusIDs;
	}
	
	/** Returns the hidden IDs */
	public ArrayList<Integer> getHiddenIDs() { return hiddenIDs; }
	
	/** Adds a focus */
	public void addFocus(ElementPath elementPath)
	{
		// Get descendants of the specified element
		ArrayList<Integer> descendantIDs = new ArrayList<Integer>();
		for(SchemaElement descendant : graph.getDescendantElements(elementPath.getElementID()))
			descendantIDs.add(descendant.getId());
		
		// Remove focus elements which are superseded of is a sub-item of the new focus
		for(ElementPath focusPath : new ArrayList<ElementPath>(focusPaths))
			if(elementPath.contains(focusPath) || descendantIDs.contains(focusPath.getElementID()))
				focusPaths.remove(focusPath);
			
		// Adds the focus element
		focusPaths.add(elementPath);
		elementsInFocus=null;
		hiddenElements=null;
	}
	
	/** Removes a focus */
	public void removeFocus(ElementPath elementPath)
		{ focusPaths.remove(elementPath); elementsInFocus=null; hiddenElements=null; }
	
	/** Removes all foci */
	public void removeAllFoci()
		{ focusPaths.clear(); elementsInFocus=null; hiddenElements=null; }
	
	/** Hides the specified element */
	public void hideElement(Integer elementID)
	{
		// Get descendants of the specified element
		ArrayList<Integer> descendantIDs = new ArrayList<Integer>();
		for(SchemaElement descendant : graph.getDescendantElements(elementID))
			descendantIDs.add(descendant.getId());
		
		// Remove focus elements which are superseded by the hidden element
		for(ElementPath focusPath : new ArrayList<ElementPath>(focusPaths))
			if(descendantIDs.contains(focusPath.getElementID()))
				focusPaths.remove(focusPath);		
		
		// Adds the hidden element
		hiddenIDs.add(elementID);
		elementsInFocus=null;
		hiddenElements=null;
	}
	
	/** Unhides the specified element */
	public void unhideElement(Integer elementID)
		{ hiddenIDs.remove(elementID); elementsInFocus=null; hiddenElements=null; }

	/** Returns the list of elements in focus */
	private HashSet<Integer> getFocusedElements()
	{
		if(elementsInFocus==null)
		{
			// Identify the root IDs
			ArrayList<Integer> rootIDs = new ArrayList<Integer>();
			for(ElementPath focusPath : focusPaths)
				rootIDs.add(focusPath.getElementID());
			if(rootIDs.size()==0)
				for(SchemaElement element : graph.getRootElements())
					rootIDs.add(element.getId());

			// Generate the elements in focus
			elementsInFocus = new HashSet<Integer>();
			for(Integer elementID : rootIDs)
			{
				elementsInFocus.add(elementID);
				elementsInFocus.addAll(getDescendantIDs(elementID));
			}
		}
		return elementsInFocus;
	}
	
	/** Returns the list of hidden elements */
	public HashSet<Integer> getHiddenElements()
	{
		if(hiddenElements==null)
		{
			hiddenElements = new HashSet<Integer>();
			for(Integer hiddenID : hiddenIDs)
				for(SchemaElement descendant : graph.getDescendantElements(hiddenID))
					if(!contains(descendant.getId()))
						hiddenElements.add(descendant.getId());
		}
		return hiddenElements;
	}
	
	/** Indicates if the specified element is within focus */
	public boolean contains(Integer elementID)
		{ return getFocusedElements().contains(elementID); }

	/** Indicates if the specified node is within focus */
	public boolean contains(DefaultMutableTreeNode node)
	{
		// First verify that the schema is being focused on
		Integer schemaID = SchemaTree.getSchema(node);
		if(!getSchemaID().equals(schemaID)) return false;
		
		// Check to make sure the node is focused on
		Object elementID = node.getUserObject();
		if(!(elementID instanceof Integer)) return false;
		if(!contains((Integer)elementID)) return false;
		
		// Make sure that the node is on the selected path
		if(focusPaths.size()==0) return true;
		ElementPath elementPath = SchemaTree.getElementPath(node);
		for(ElementPath focusPath : focusPaths)
			if(elementPath.contains(focusPath)) return true;
		return false;
	}
}