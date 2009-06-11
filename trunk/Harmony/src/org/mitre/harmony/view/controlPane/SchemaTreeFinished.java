// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.controlPane;

import java.util.Enumeration;
import java.util.HashSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.preferences.PreferencesListener;
import org.mitre.harmony.view.schemaTree.SchemaTree;
import org.mitre.harmony.view.schemaTree.SchemaTreeListener;

/**
 * Pane used to display the finished ratio of the schema tree
 */
public class SchemaTreeFinished extends JPanel implements PreferencesListener, SchemaTreeListener
{
	/** Stores the tree to which this pane is associated */
	private SchemaTree tree = null;
	
	/** Stores the Harmony Model */
	private HarmonyModel harmonyModel;
	
	// Variables to keep track of finished versus total elements
	private int finished = 0;
	private int total = 0;

	/** Stores the finished count label */
	private JLabel label = new JLabel("XX/XX"); 
	
	/** This method synchronizes the UI with the schema tree */
	private void refresh()
		{ label.setText(finished + "/" + total); }

	/** Creates a new progress widget tied to the indicated tree */
	public SchemaTreeFinished(SchemaTree tree, HarmonyModel harmonyModel)
	{
		this.tree = tree;
		this.harmonyModel = harmonyModel;
		add(new JLabel("Finished:"));
		add(label);
		schemaStructureModified(tree);
		harmonyModel.getPreferences().addListener(this);
		tree.addSchemaTreeListener(this);
		refresh();
	}

	/** Whenever the schema tree structure is modified, the UI must be refreshed */
	public void schemaStructureModified(SchemaTree tree)
	{
		// Reset the finished and total counts
		finished=0;
		total=0;
		
		// Only count up nodes if schema exists
		if(!(((DefaultMutableTreeNode)tree.root.getChildAt(0)).getUserObject() instanceof String))
		{
			// Update the finished and total counts
			for(int i=0; i<tree.root.getChildCount(); i++)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.root.getChildAt(i);
				Enumeration descendants = node.depthFirstEnumeration();
				while (descendants.hasMoreElements())
				{
					DefaultMutableTreeNode descendantNode = (DefaultMutableTreeNode)descendants.nextElement();
					Object obj = descendantNode.getUserObject();
					if(obj instanceof Integer)
					{
						Integer schemaID = SchemaTree.getSchema(descendantNode);
						Integer elementID = (Integer)obj;
						if(harmonyModel.getPreferences().isFinished(schemaID,elementID)) finished++;
						total++;
					}
				}
			}
		}
		refresh();
	}
	
	/** Increase the finished count */
	public void elementsMarkedAsFinished(Integer schemaID, HashSet<Integer> elementIDs)
		{ if(tree.getSchemas().contains(schemaID)) { finished+=elementIDs.size(); refresh(); } }

	/** Decrease the finished count */
	public void elementsMarkedAsUnfinished(Integer schemaID, HashSet<Integer> elementIDs)
		{ if(tree.getSchemas().contains(schemaID)) { finished-=elementIDs.size(); refresh(); } }
		
	// Unused event listeners
	public void schemaDisplayModified(SchemaTree tree) {}
	public void displayedViewChanged() {}
	public void showSchemaTypesChanged() {}
}