// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.schemaTree;

import java.util.HashSet;

import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.graph.HierarchicalGraph;

/** Used to generate all nodes within the schema tree */
class SchemaTreeGenerator
{	
	/** Adds a schema element */
	static private DefaultMutableTreeNode addNode(HierarchicalGraph graph, Integer elementID, HashSet<Integer> parentElements)
	{
		DefaultMutableTreeNode node = null;

		// Don't add element if already in path		
		if(!parentElements.contains(elementID))
		{
			// Add element to list of parent nodes
			parentElements.add(elementID);

			// Create element node
			node = new DefaultMutableTreeNode(elementID);
			for(SchemaElement childElement : graph.getChildElements(elementID))
			{
				DefaultMutableTreeNode childNode = addNode(graph, childElement.getId(), parentElements);
				if(childNode!=null) node.add(childNode);				
			}

			// Remove element from list of parent nodes
			parentElements.remove(elementID);
		}
		
		return node;
	}
	
	/** Initializes schema tree when no schemas are present */
	static void initialize(SchemaTree tree)
	{
		// Initialize tree by only indicating the no schemas are available
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("<No Schemas Available>");
		((DefaultTreeModel)tree.getModel()).insertNodeInto(node,tree.root,0);
        tree.scrollPathToVisible(new TreePath(node.getPath()));
	}
	
	/** Adds a schema to the schema tree */
	static void addSchema(SchemaTree tree, Integer schemaID, HarmonyModel harmonyModel)
	{
		// Get the schema associated with the schema ID
		Schema schema = harmonyModel.getSchemaManager().getSchema(schemaID);
		
		// If first schema, eliminate "no schema" placeholder
		if(((DefaultMutableTreeNode)tree.root.getChildAt(0)).getUserObject() instanceof String)
			((DefaultTreeModel)tree.getModel()).removeNodeFromParent((DefaultMutableTreeNode)tree.root.getChildAt(0));
		
		// Determine placement for new schema
		int loc=0;
		while(loc<tree.root.getChildCount())
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.root.getChildAt(loc);
			if(((Schema)node.getUserObject()).getName().compareTo(schema.getName())>=0) break;
			loc++;
		}
		
		// Install tree branch for specific schema
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(schema);
		((DefaultTreeModel)tree.getModel()).insertNodeInto(node,tree.root,loc);
		tree.expandPath(new TreePath(((DefaultMutableTreeNode)node.getParent()).getPath()));

		// Add root elements to the schema
		HierarchicalGraph graph = harmonyModel.getSchemaManager().getGraph(schemaID);
		HashSet<Integer> parentElements = new HashSet<Integer>();
		for(SchemaElement element : graph.getRootElements())
		{
			DefaultMutableTreeNode entityNode = addNode(graph, element.getId(), parentElements);
			node.add(entityNode);
			tree.expandPath(new TreePath(((DefaultMutableTreeNode)entityNode.getParent()).getPath()));
		}
		
		// Indicate the schema tree has changed
		for(SchemaTreeListener treeListener : tree.treeListeners)
		{
			treeListener.schemaStructureModified(tree);
			treeListener.schemaDisplayModified(tree);
		}	

		// Ensure that the added nodes are properly displayed
		((DefaultTreeModel)tree.getModel()).nodeStructureChanged(node);		
	}
	
	/** Removes a schema from the schema tree */
	static void removeSchema(SchemaTree tree, Integer schemaID)
	{
		// Remove the schema from the schema tree
		DefaultMutableTreeNode node = tree.getSchemaNode(schemaID);
		if(node!=null) ((DefaultTreeModel)tree.getModel()).removeNodeFromParent(node);
		
		// Insert placeholder if "no schemas"
		if(tree.root.getChildCount()==0)
			initialize(tree);
		
		// Indicate that schema tree has changed
		for(SchemaTreeListener treeListener : tree.treeListeners)
		{
			treeListener.schemaStructureModified(tree);
			treeListener.schemaDisplayModified(tree);
		}
	}
}
