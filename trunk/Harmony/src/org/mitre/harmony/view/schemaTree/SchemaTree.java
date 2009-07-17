// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.schemaTree;

import java.awt.BasicStroke;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.plaf.metal.MetalTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.filters.ElementPath;
import org.mitre.harmony.model.filters.Focus;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.harmony.model.mapping.MappingListener;
import org.mitre.harmony.model.preferences.PreferencesListener;
import org.mitre.harmony.view.dialogs.SchemaModelDialog;
import org.mitre.harmony.view.mappingPane.MappingPane;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;
import org.mitre.schemastore.model.Schema;

/**
 * Creates the source or target schema tree 
 * @author CWOLF
 */
public class SchemaTree extends JTree implements MappingListener, PreferencesListener, MouseListener, MouseMotionListener
{	
	/**
	 * Prevents the root node from being expanded or collapsed
	 */
	class SchemaTreeUI extends MetalTreeUI
	{
		protected void toggleExpandState(TreePath path)
			{ if(!new TreePath(root.getPath()).equals(path)) super.toggleExpandState(path); }
	}
	
	/**
	 * Cache row bounds for quick referencing
	 * @author CWOLF
	 */
	private class RowBounds implements SchemaTreeListener
	{
		private boolean needUpdating = true;	// Indicates if the row bounds currently need updating
		private Rectangle row[];				// Array of all row bounds associated with schema tree

		/**
		 * Update the row bounds associated with the schema tree
		 */
		private void update()
		{
			row = new Rectangle[getRowCount()];
			for(int i=0; i<getRowCount(); i++)
				row[i]=getRowBounds(i);
			needUpdating = false;
		}
		
		/**
		 * Initialize the caching of row bounds
		 */
		private RowBounds()
			{ needUpdating=true; addSchemaTreeListener(this); }

		/**
		 * @return Row bound of specified row
		 */
		Rectangle getRow(int i)
			{ if(needUpdating) update(); return row[i]; } 
		
		//--------------------------------------------------------------------------------
		// Purpose: Listeners for changes in the schema tree in order to update row bounds
		//--------------------------------------------------------------------------------
		public void schemaStructureModified(SchemaTree tree) { needUpdating = true; }
		public void schemaDisplayModified(SchemaTree tree){ needUpdating = true; }
	}

	/**
	 * Cache visible nodes for quick referencing
	 * @author CWOLF
	 */
	private class VisibleNodes implements SchemaTreeListener
	{
		private boolean needUpdating;						// Indicates if the visible nodes need updating
		private HashSet<DefaultMutableTreeNode> visible;	// Hash used to store visible nodes

		/**
		 * Update the visible nodes associated with the schema tree
		 */
		private void update()
		{
			visible = new HashSet<DefaultMutableTreeNode>();
			for(int i=0; i<getRowCount(); i++)
				visible.add((DefaultMutableTreeNode) getPathForRow(i).getLastPathComponent());
			needUpdating = false;
		}
		
		/**
		 * Initialize the caching of visible nodes
		 */
		private VisibleNodes()
			{ needUpdating=true; addSchemaTreeListener(this); }

		/**
		 * @return Indication if node is currently visible
		 */
		private boolean isVisible(DefaultMutableTreeNode node)
			{ if(needUpdating) update(); return visible.contains(node); }
		
		//-----------------------------------------------------------------------------------
		// Purpose: Listeners for changes in the schema tree in order to update visible nodes
		//-----------------------------------------------------------------------------------
		public void schemaStructureModified(SchemaTree tree) { needUpdating = true; }
		public void schemaDisplayModified(SchemaTree tree){ needUpdating = true; }
	}
	
	/**
	 * Cache node rows for quick referencing
	 * @author CWOLF
	 */
	private class NodeRows implements SchemaTreeListener
	{
		private boolean needUpdating;									// Indicates if the node rows need updating
		private Hashtable<DefaultMutableTreeNode, Integer> nodeRows;	// Hash used to store node rows
		
		/**
		 * Update the node rows associated with the schema tree
		 */
		private void update()
		{
			nodeRows = new Hashtable<DefaultMutableTreeNode, Integer>();
			Enumeration nodes = ((DefaultMutableTreeNode)getModel().getRoot()).depthFirstEnumeration();
			while(nodes.hasMoreElements())
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)nodes.nextElement();
				TreePath path = new TreePath(node.getPath());
				while(!isVisible(path)) path = path.getParentPath();
				nodeRows.put(node,new Integer(getRowForPath(path)));
			}
			needUpdating = false;
		}
		
		/**
		 * Initialize the caching of node rows
		 */
		private NodeRows()
			{ needUpdating=true; addSchemaTreeListener(this); }

		/**
		 * @return Row where node is located
		 */
		private Integer getRow(DefaultMutableTreeNode node)
			{ if(needUpdating) update(); return nodeRows.get(node); }
		
		//-----------------------------------------------------------------------------------
		// Purpose: Listeners for changes in the schema tree in order to update visible nodes
		//-----------------------------------------------------------------------------------
		public void schemaStructureModified(SchemaTree tree) { needUpdating = true; }
		public void schemaDisplayModified(SchemaTree tree){ needUpdating = true; }
	}
	
	private Integer side;				// Indicates if this is the left or right tree
	SchemaTreeHash schemaTreeHash;		// Holds hash table to access all schema nodes
	private RowBounds rowBounds;		// Holds a cache of schema tree row bounds
	private VisibleNodes visibleNodes;	// Holds a cache of visible nodes
	private NodeRows nodeRows;			// Holds a cache of node rows
	public DefaultMutableTreeNode root;	// Root node to which all schema nodes are attached

	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;
	
	/** Initializes the schema tree */
	public SchemaTree(Integer sideIn, HarmonyModel harmonyModel)
	{
		side = sideIn;
		this.harmonyModel = harmonyModel;
		
		// Initializes tree variables
		root = new DefaultMutableTreeNode(side==MappingSchema.LEFT ? " Mapping Schemas" : " Selected Schema");
		schemaTreeHash = new SchemaTreeHash(this);
		rowBounds = new RowBounds();
		visibleNodes = new VisibleNodes();
		nodeRows = new NodeRows();
		setRowHeight(20);
		
		// Set up various schema tree models
		setCellRenderer(new SchemaTreeRenderer(harmonyModel));
		setModel(new DefaultTreeModel(root));
		setUI(new SchemaTreeUI());
		setSelectionModel(null);

		// Set up the schema tree schemas
		SchemaTreeGenerator.initialize(this);
		for(Integer schemaID : harmonyModel.getMappingManager().getSchemaIDs(side))
			SchemaTreeGenerator.addSchema(this,schemaID,harmonyModel);
			
		// Set up tool tips for the tree items
		ToolTipManager.sharedInstance().registerComponent(this);
		
		// Add mouse and schema listeners to schema tree
		addMouseListener(this);
		addMouseMotionListener(this);
		harmonyModel.getPreferences().addListener(this);
		harmonyModel.getMappingManager().addListener(this);
	}

	/** Returns the side associated with this schema tree */
	public Integer getSide()
		{ return side; }

	/** Returns the schemas associated with this schema tree */
	public ArrayList<Integer> getSchemas()
	{
		ArrayList<Integer> schemas = new ArrayList<Integer>();
		for(int i=0; i<root.getChildCount(); i++)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)root.getChildAt(i);
			if(node.getUserObject() instanceof Schema)
				schemas.add(((Schema)node.getUserObject()).getId());
		}
		return schemas;
	}
	
	/** Returns the schema associated with the specified node */
	static public Integer getSchema(DefaultMutableTreeNode node)
	{
		while(node.getUserObject() instanceof Integer)
			node = (DefaultMutableTreeNode)node.getParent();
		return ((Schema)node.getUserObject()).getId();
	}
	
	/** Returns the node associated with the specified schema */
	public DefaultMutableTreeNode getSchemaNode(Integer schemaID)
	{
		for(int i=0; i<root.getChildCount(); i++)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)root.getChildAt(i);
			Object object = node.getUserObject();
			if(object instanceof Schema && schemaID.equals(((Schema)object).getId()))return node;
		}
		return null;
	}
	
	/** Returns the schema element associated with the tree node */
	static Integer getElement(DefaultMutableTreeNode node)
		{ return (node.getUserObject() instanceof Integer) ? (Integer)(node.getUserObject()) : null; }
	
	/** Returns the schema node associated with the provided path */
	public Integer getElement(TreePath path)
		{ return getElement((DefaultMutableTreeNode)path.getLastPathComponent()); }
	
	/** Returns the element path associated with the tree node */
	static public ElementPath getElementPath(DefaultMutableTreeNode node)
	{
		ArrayList<Integer> elementPath = new ArrayList<Integer>();
		for(TreeNode pathNode : node.getPath())
		{
			Integer elementID = getElement((DefaultMutableTreeNode)pathNode);
			if(elementID!=null) elementPath.add(elementID);
		}
		return new ElementPath(elementPath);
	}
	
	/** Expands the specified tree path */
	public void expandPath(TreePath path)
	{
		super.expandPath(path);
		for(SchemaTreeListener listener : listeners)
			listener.schemaDisplayModified(this);
	}

	/** Expands the specified tree path (expand any hidden parents) */
	public void expandFullPath(TreePath path)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		while(node!=null)
		{
			super.expandPath(new TreePath(node.getPath()));
			node = (DefaultMutableTreeNode)node.getParent();
		}
	}
	
	/** Expands the specified tree node */
	public void expandNode(DefaultMutableTreeNode node)
	{
		// Expand node and all children under it
		super.expandPath(new TreePath(node.getPath()));
		Enumeration childNodes = node.depthFirstEnumeration();
		while(childNodes.hasMoreElements())
		{
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)childNodes.nextElement();
			super.expandPath(new TreePath(childNode.getPath()));
		}
		
		// Inform listeners of change to schema tree
		for(SchemaTreeListener listener : listeners)
			listener.schemaDisplayModified(this);
	}
	
	/** Collapses the specified tree path */
	public void collapsePath(TreePath path)
	{
		if(path.getParentPath()!=null)
		{
			super.collapsePath(path);
			for(SchemaTreeListener listener : listeners)
				listener.schemaDisplayModified(this);
		}
	}
	
	/** Collapses the specified tree node */
	void collapseNode(DefaultMutableTreeNode node)
	{
		// Collapse node and all children under it
		super.collapsePath(new TreePath(node.getPath()));
		Enumeration childNodes = node.depthFirstEnumeration();
		while(childNodes.hasMoreElements())
		{
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)childNodes.nextElement();
			TreePath path = new TreePath(childNode.getPath());
			if(path.getParentPath()!=null)
				super.collapsePath(new TreePath(childNode.getPath()));
		}
		
		// Inform listeners of change to schema tree
		for(SchemaTreeListener listener : listeners)
			listener.schemaDisplayModified(this);
	}
	
	/** This method marks the specified tree node as finished (or not), including its descendants */
	void markNodeAsFinished(DefaultMutableTreeNode node, boolean isFinished)
	{
		// Only allow schema elements to be marked as finished
		Object obj = node.getUserObject();
		if(obj instanceof Integer)
		{
			HashSet<Integer> elementIDs = new HashSet<Integer>();
			
			// Determine the finished state of the specified node
			Integer schemaID = getSchema(node);
			
			// Set finished state of specified node and its descendants
			Enumeration descendants = node.depthFirstEnumeration();
			while(descendants.hasMoreElements())
				elementIDs.add(getElement((DefaultMutableTreeNode)descendants.nextElement()));

			// If node set to unfinished, unmark the path back to the root as well
			if(!isFinished)
			{
				TreeNode[] path = node.getPath();
				for(int i=0; i<path.length; i++)
					elementIDs.add(getElement((DefaultMutableTreeNode)path[i]));					
			}
			
			// Update the preferences
			harmonyModel.getPreferences().setFinished(schemaID,elementIDs,isFinished);			
		}
	}
	
	/**
	 * This method deletes all system-generated links for the indicated node; a
	 * user-specified copy of each visible link is made.
	 * @param node A schema node that has just been marked as completed.
	 */
	private void updateMappingCells(Integer elementID)
	{
		// Mark all visible links as user selected and all others as rejected
		for(Integer mappingCellID : harmonyModel.getMappingCellManager().getMappingCellsByElement(elementID))
		{
			MappingCell mappingCell = harmonyModel.getMappingCellManager().getMappingCell(mappingCellID);
			if(!mappingCell.getValidated())
			{
				mappingCell.setScore(harmonyModel.getFilters().isVisibleMappingCell(mappingCellID)?MappingCellManager.MAX_CONFIDENCE:MappingCellManager.MIN_CONFIDENCE);
				mappingCell.setAuthor(System.getProperty("user.name"));
				mappingCell.setValidated(true);
				harmonyModel.getMappingCellManager().setMappingCell(mappingCell);
			}
		}
	}

	/** Returns the specified row's bounds */
	public Rectangle getBufferedRowBounds(int row)
		{ return rowBounds.getRow(row); }

	/** Returns the specified node's bounds */
	public Rectangle getBufferedRowBounds(DefaultMutableTreeNode node)
		{ return rowBounds.getRow(getNodeRow(node)); }

	/** Returns if specified node is visible */
	public boolean isVisible(DefaultMutableTreeNode node)
		{ return visibleNodes.isVisible(node); }

	/** Returns the specified node's row */
	public Integer getNodeRow(DefaultMutableTreeNode node)
		{ return nodeRows.getRow(node); }
	
	/** Returns vector of all schema node tree locations */
	public ArrayList<DefaultMutableTreeNode> getComponentNodes(Integer node)
		{ return schemaTreeHash.get(node); }
	
	/** Handles the addition to the specified schema */
	public void schemaAdded(Integer schemaID)
	{
		if(harmonyModel.getMappingManager().getSchemaIDs(side).contains(schemaID))
			SchemaTreeGenerator.addSchema(this, schemaID, harmonyModel);			
	}

	/** Handles the removal of the specified schema */
	public void schemaRemoved(Integer schemaID)
		{ SchemaTreeGenerator.removeSchema(this, schemaID); }
	
	/** Handles changes to the specified schema */
	public void schemaModified(Integer schemaID)
	{
		SchemaTreeGenerator.removeSchema(this, schemaID);
		if(harmonyModel.getMappingManager().getSchemaIDs(side).contains(schemaID))
			SchemaTreeGenerator.addSchema(this, schemaID, harmonyModel);
	}
	
	/** Handles mouse clicks on the schema tree */
	public void mouseClicked(MouseEvent e)
	{
		// Get clicked-on node
		TreePath path = getPathForLocation(e.getX(),e.getY());
		if(path==null) return;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();

		// Handle mouse clicks on the root node
		if(node.isRoot() && (e.getButton()==MouseEvent.BUTTON3 || e.isControlDown()))
		{
			SchemaTreeNodeMenu menu = new SchemaTreeNodeMenu(this,node,harmonyModel);
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
		
		// Handles mouse clicks on all other nodes
		else if(!(node.getUserObject() instanceof String))
		{
			if(e.getButton()==MouseEvent.BUTTON1 && !e.isControlDown())
			{
				Object object = node.getUserObject();
				
				// Launches the dialog to change the schema model
				if(object instanceof Schema)
				{
					Container mappingPane = getParent();
					while(!(mappingPane instanceof MappingPane))
						mappingPane = mappingPane.getParent();
					if(mappingPane.getCursor()==Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
						new SchemaModelDialog(((Schema)object).getId(),harmonyModel);
				}
					
				// Allows the selection of the clicked on node
				else if(object instanceof Integer)
				{
					Integer elementID = (Integer)node.getUserObject();
					if(harmonyModel.getFilters().isVisibleNode(side,node))
						harmonyModel.getSelectedInfo().setElement(elementID,side,e.isControlDown());
				}
			}
				
			// If the right mouse button pressed, display the drop-down menu
			else if(e.getButton()==MouseEvent.BUTTON3 || e.isControlDown())
			{
				SchemaTreeNodeMenu menu = new SchemaTreeNodeMenu(this,node,harmonyModel);
				menu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}
	
	/** Handles mouse movements on the schema tree */
	public void mouseMoved(MouseEvent e)
	{		
		// Gets the mapping pane to which this schema pane is associated
		Container mappingPane = getParent();
		while(!(mappingPane instanceof MappingPane))
			mappingPane = mappingPane.getParent();
		
		// Determine which tree path has been selected
		TreePath path = getPathForLocation(e.getX(),e.getY());
		if(path!=null && path.getPathCount()==2)
		{
			String schemaName = path.getLastPathComponent().toString();
			if(e.getX()>getFontMetrics(new JLabel().getFont()).stringWidth(schemaName)+45)
				if(e.getX()<rowBounds.getRow(getRowForPath(path)).getMaxX())
				{
					if(mappingPane.getCursor()!=Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
						mappingPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					return;
				}
		}
			
		// If not over the "Add Schemas" link, switch cursor back to default
		if(mappingPane.getCursor()!=Cursor.getDefaultCursor())
			mappingPane.setCursor(Cursor.getDefaultCursor());
	}
	
	/** Handles preference changes */
	public void showSchemaTypesChanged()
	{
		// Update all nodes which might be affected by showing node type
		DefaultMutableTreeNode leaf = root.getFirstLeaf();
		while(leaf!=null) {
			((DefaultTreeModel)getModel()).nodeChanged(leaf);
			leaf = leaf.getNextLeaf();
		}
		
		// Inform listeners that schema tree nodes have changed
		for(SchemaTreeListener listener : listeners) {
			listener.schemaDisplayModified(this);
		}
	}
	
	/** Handles the marking of a element as finished */
	public void elementsMarkedAsFinished(Integer schemaID, HashSet<Integer> elementIDs)
	{
		for(Integer elementID : elementIDs) updateMappingCells(elementID);
		DefaultMutableTreeNode node = getSchemaNode(schemaID);
		if(node!=null) repaint(computeFocusRectangle(node));
	}

	/** Handles the marking of a element as unfinished */
	public void elementsMarkedAsUnfinished(Integer schemaID, HashSet<Integer> elementIDs)
	{
		DefaultMutableTreeNode node = getSchemaNode(schemaID);
		if(node!=null) repaint(computeFocusRectangle(node));
	}
	
	// Unused listener events
	public void mappingModified() {}
	public void displayedViewChanged() {}
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	
	// Allows classes to listen for schema tree changes
	private ArrayList<SchemaTreeListener> listeners = new ArrayList<SchemaTreeListener>();
	public void addSchemaTreeListener(SchemaTreeListener obj) { listeners.add(obj); }	
	public void removeSchemaTreeListener(SchemaTreeListener obj) { listeners.remove(obj); }
	public ArrayList<SchemaTreeListener> getSchemaTreeListeners()
		{ return new ArrayList<SchemaTreeListener>(listeners); }
	
	// This stroke uses the default values for everything except the dash pattern
	private static final float SPACE = 4.0f;
	private static final float DASH = 2*SPACE;
	private static final BasicStroke DASHED_LINE = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
			  10.0f, new float[] {DASH, SPACE}, 0.0f);
	
	/** Paints a dashed line around the nodes in focus (if any) */
	public void paint(Graphics g)
	{
		super.paint(g);

		// Paint a dashed line only if the focus has been defined.
		for(Focus focus : harmonyModel.getFilters().getFoci(side))
			for(ElementPath focusPath : focus.getFocusedPaths())
			{
				Graphics2D g2d = (Graphics2D) g;
				
				// Temporarily use a dashed line.
				Stroke s = g2d.getStroke();
				g2d.setStroke(DASHED_LINE);
				
				// Draw a box around each node in focus.
				for(DefaultMutableTreeNode node : getComponentNodes(focusPath.getElementID()))
					if(getSchema(node).equals(focus.getSchemaID()))
						if(focusPath.equals(SchemaTree.getElementPath(node)))
						{
							Rectangle r = computeFocusRectangle(node);
							g2d.drawRect(r.x, r.y, r.width, r.height);
						}
				g2d.setStroke(s);
			}
	}
	
	/** Calculates the drawing of a rectangle around focused items */
	public Rectangle computeFocusRectangle(DefaultMutableTreeNode node)
	{
		Rectangle result = (Rectangle) getBufferedRowBounds(node).clone();
		Enumeration childNodes = node.depthFirstEnumeration();
		// Visit each child...
		while(childNodes.hasMoreElements()) {
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)childNodes.nextElement();
			Rectangle childRect = getBufferedRowBounds(childNode);
			// And expand the rectangle as needed.
			if (childRect.x + childRect.width > result.x + result.width) {
				result.width = childRect.x + childRect.width - result.x;
			}
			if (childRect.y + childRect.height > result.y + result.height) {
				result.height = childRect.y + childRect.height - result.y;
			}
		}
		// Nudge the horizontal distance for a less cluttered appearance.
		result.x -= SPACE;
		result.width += 2*SPACE;
		return result;
	}
}