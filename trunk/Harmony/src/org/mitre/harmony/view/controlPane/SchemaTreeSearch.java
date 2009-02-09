// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.controlPane;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;

import org.mitre.harmony.model.SchemaManager;
import org.mitre.harmony.view.schemaTree.SchemaTree;
import org.mitre.harmony.view.schemaTree.SchemaTreeListener;

/**
 * Handles all keyword searching done of schema tree
 * @author CWOLF
 */
public class SchemaTreeSearch extends JPanel implements KeyListener, SchemaTreeListener
{
	/**
	 * Holds one tree node element (both name and tree path)
	 * @author CWOLF
	 */
	private class TreeItem implements Comparable<TreeItem>
	{
		private String name;	// Tree node name
		private TreePath path;	// Tree node path
		
		/**
		 * Initialize both name and path of given tree node
		 * @param node Node to store name and path info on
		 */
		TreeItem(DefaultMutableTreeNode node)
		{
			name = SchemaManager.getSchemaElement((Integer)(node.getUserObject())).getName();
			path = new TreePath(node.getPath());
		}
		
		/**
		 * Return the path associated with the tree node
		 * @return Path associated with the tree node
		 */
		TreePath getPath()
			{ return path; }
		
		/**
		 * Return the name associated with this string item
		 * @return Name associated with this string item
		 */
		public String toString()
			{ return name; }		
		
		/**
		 * Sort the tree node with another tree node (sort by name)
		 * @param item Tree item to compare to this tree item
		 * @return Indicates if other tree item is less than, equal, or greater to this tree item
		 */
		public int compareTo(TreeItem item)
			{ return name.compareTo(item.name); }
	}
	
	private SchemaTree tree;				// Tree on which searches should be performed
	private Vector<TreeItem> schemaNodes;	// List of schema nodes found in tree
	private JTextField searchField;			// Search field for user to enter searches
	
	/**
	 * Gathers and sorts all nodes found in tree
	 */
	private void collectNodes()
	{
		// Initialize schema node list
		schemaNodes.clear();
		
		// Gather up all schema nodes found in tree
		Enumeration subNodes = tree.root.depthFirstEnumeration();
		while(subNodes.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)subNodes.nextElement();
			if(!node.isRoot() && node.getUserObject() instanceof Integer)
				schemaNodes.addElement(new TreeItem(node));
		}
		
		// Sort schema nodes in alphabetical order
		Collections.sort(schemaNodes);		
	}
	
	/**
	 * Updates the nodes which match the current search keyword
	 */
	private void updateMatches()
	{	
		// Keep track of all matched tree paths
		Vector<TreePath> selPaths = new Vector<TreePath>();

		// Only proceed with finding matches if keyword given
		if(!searchField.getText().equals(""))
		{
			boolean caseSensitive = false;
			
			// Modify search term to computer format
			String searchTerm = "*" + searchField.getText() + "*";
			for(int i=0; i<searchTerm.length(); i++) {
				if(searchTerm.charAt(i)=='*') {
					searchTerm = ((i>0)?searchTerm.substring(0,i):"") + ".*" +
						((i<searchTerm.length()-1)?searchTerm.substring(i+1,searchTerm.length()):"");
					i++;
				}
				else if(Character.isUpperCase(searchTerm.charAt(i)))
					caseSensitive = true;
			}
				
			// Determine what tree nodes match search criteria
			for(TreeItem item : schemaNodes) {
				String itemString = item.toString();
				if(!caseSensitive) itemString = itemString.toLowerCase();
				if(itemString.matches(searchTerm)) {
					selPaths.add(item.getPath());
					tree.expandFullPath(item.getPath());
				}
			}
		}
		
		// Select all tree nodes which match search criteria
		tree.selectSchemaTreeNodes(selPaths.toArray(new TreePath[0]));
	}
	
	/**
	 * Constructs the schema tree search field
	 * @param tree Tree for which seach field should reference
	 */
	public SchemaTreeSearch(SchemaTree tree)
	{
		this.tree = tree;
		schemaNodes = new Vector<TreeItem>();
		searchField = new JTextField();
		
		// Lay out search box
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		add(new JLabel(" Search: "));
		add(searchField);

		// Collect all nodes in current schema tree
		collectNodes();
		
		// Add listeners
		tree.addSchemaTreeListener(this);
		searchField.addKeyListener(this);
	}
	
	/** Update matches every time the schema tree structure is modified */
	public void schemaStructureModified(SchemaTree tree)
		{ collectNodes(); updateMatches(); }

	/** Update matches every time a new search keyword is entered */
	public void keyTyped(KeyEvent e)
		{ if(e.getKeyChar() == KeyEvent.VK_ENTER) updateMatches(); }
	
	/** Unused listener actions */
	public void schemaDisplayModified(SchemaTree tree) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
