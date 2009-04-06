// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.schemaTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.FilterManager;
import org.mitre.harmony.model.filters.Focus;
import org.mitre.harmony.view.dialogs.SchemaStatisticsDialog;
import org.mitre.schemastore.model.Schema;
import org.mitre.schemastore.model.SchemaElement;

/**
 * Constructs a popup menu to appear when schema nodes are right clicked
 * @author CWOLF
 */
class SchemaTreeNodeMenu extends JPopupMenu implements ActionListener
{
	// Objects associated with the schema tree node menu
	private SchemaTree tree;				// Tree associated with selected node
	private DefaultMutableTreeNode node;	// Node which was right-clicked

	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	// Menu items associated with the schema tree node menu
	private JMenuItem expand;			// Menu option for expanding selected node
	private JMenuItem collapse;			// Menu option to collapse selected node
	private JMenuItem setFocus;			// Menu option to set the current focus
	private JMenuItem clearFocus;		// Menu option to clear the current focus
	private JMenuItem clearAllFoci;		// Menu option to clear all foci
	private JMenuItem markUnfinished;	// Menu option to mark selected node (and children) as unfinished
	private JMenuItem markFinished;		// Menu option to mark selected node (and children) as finished
	private JMenuItem statistics;		// Menu option for viewing schema statistics
	
	/** Initialize the popup menu when a tree node is selected */
	SchemaTreeNodeMenu(SchemaTree tree, DefaultMutableTreeNode node, HarmonyModel harmonyModel)
	{
		this.tree = tree;
		this.node = node;
		this.harmonyModel = harmonyModel;
		
		// Show collapse and expand menu options only if not leaf node
		if(!tree.getModel().isLeaf(node))
		{
			expand = new JMenuItem("Expand All");
			expand.addActionListener(this);
			add(expand);
			collapse = new JMenuItem("Collapse All");
			collapse.addActionListener(this);
			add(collapse);
		}

		// Show focus and mark/unmark menu options as needed
		Object obj = node.getUserObject();
		if(obj instanceof Integer)
		{
			// Get the schema and element associated with this menu
			Integer schemaID = SchemaTree.getSchema(node);
			Integer elementID = (Integer)obj;
			
			// Determine if the element is currently in focus
			Focus focus = harmonyModel.getFilters().getFocus(tree.getSide(), schemaID);
			ArrayList<Focus> foci = harmonyModel.getFilters().getFoci(tree.getSide());
			boolean inFocus = harmonyModel.getFilters().inFocus(tree.getSide(),schemaID,elementID);
			
			// Show menu option for allowing focus to be set and cleared
			if(getComponentCount()>0) add(new JSeparator());
			if(!inFocus || foci.size()==0 || (focus!=null && focus.getFocusedIDs().size()==0))
				{ add(setFocus=new JMenuItem("Set Focus")); setFocus.addActionListener(this); }
			if(focus!=null && focus.getFocusedIDs().contains(elementID))
				{ add(clearFocus=new JMenuItem("Clear Focus")); clearFocus.addActionListener(this); }
			add(clearAllFoci=new JMenuItem("Clear All Foci")); clearAllFoci.addActionListener(this);
			
			// Show menu option to mark/unmark items as finished
			if(inFocus)
			{
				// Determine if the node and descendants are finished
				boolean isUnfinished = !harmonyModel.getPreferences().isFinished(schemaID,elementID);
				boolean isFinished = harmonyModel.getPreferences().isFinished(schemaID,elementID);
				for(SchemaElement descendant : harmonyModel.getSchemaManager().getDescendantElements(schemaID, elementID))
				{
					boolean isElementFinished = harmonyModel.getPreferences().isFinished(schemaID,descendant.getId());
					isUnfinished &= !isElementFinished;
					isFinished &= isElementFinished;
				}
				
				// Display options to mark the node and descendants as finished/unfinished
				add(new JSeparator());
				if(!isUnfinished)
				{
					add(markUnfinished=new JMenuItem("Mark as Unfinished"));
					markUnfinished.addActionListener(this);
				}
				if(!isFinished)
				{
					add(markFinished=new JMenuItem("Mark as Finished"));
					markFinished.addActionListener(this);
				}
			}
		}

		// Allow "matched" statistics to be viewed if node is schema
		if(obj instanceof Schema)
		{
			// Place menu option for editing the schema
			statistics = new JMenuItem("View Statistics");
			statistics.addActionListener(this);
			add(new JSeparator());
			add(statistics);
		}
	}
	
	/**
	 * Reacts to selection of various menu options
	 */
	public void actionPerformed(ActionEvent e)
	{
		// Generate easy reference to managers
		FilterManager filters = harmonyModel.getFilters();
		
		// Retrieve various attributes associated with this node
		Integer side = tree.getSide();
		Integer schemaID = SchemaTree.getSchema(node);
		Integer elementID = node.getUserObject() instanceof Integer ? (Integer)node.getUserObject() : null;
		
		// Handles manipulation of schema tree
		if(e.getSource()==expand) tree.expandNode(node);
		if(e.getSource()==collapse) tree.collapseNode(node);
		if(e.getSource()==markUnfinished) tree.markNodeAsFinished(node,false);
		if(e.getSource()==markFinished) tree.markNodeAsFinished(node,true);

		// Handles the clearing of focus on a node
		if(e.getSource()==clearFocus)
			filters.removeFocus(side, schemaID, elementID);
		
		// Handles the clearing of all foci
		if(e.getSource()==clearAllFoci)
			filters.removeFoci(side);
		
		// Handles the setting of focus on a node
		if(e.getSource()==setFocus)
			filters.addFocus(side,schemaID,elementID);
		
		// Handles the viewing of schema statistics
		if(e.getSource()==statistics)
		{
			Schema schema = (Schema)node.getUserObject();
			new SchemaStatisticsDialog(schema.getId(),harmonyModel);
		}
	}
}