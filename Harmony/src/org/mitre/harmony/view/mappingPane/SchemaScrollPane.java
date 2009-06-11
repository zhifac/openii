// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JViewport;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.search.SearchListener;
import org.mitre.harmony.model.search.SearchResult;
import org.mitre.harmony.view.schemaTree.SchemaTree;
import org.mitre.harmony.view.schemaTree.SchemaTreeListener;
import org.mitre.schemastore.model.MappingSchema;

/**
 * Displays the scroll pane next to each schema tree pane (includes selection marks)
 * @author CWOLF
 */
public class SchemaScrollPane extends JScrollPane implements AdjustmentListener, SearchListener, SchemaTreeListener
{
	/** Stores the mapping pane to which this scroll pane is associated */
	private MappingPane mappingPane;
	
	/** Stores the schema tree associated with this scroll bar */
	private SchemaTreeImp tree;

	/** Stores the rows which have search results */
	private ArrayList<Integer> searchResultRows = new ArrayList<Integer>();
	
	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;

	/** Class used to reimplement the scroll track display */
	private class SchemaScrollBarUI extends MetalScrollBarUI
	{	
		/** Redefines the drawing of the scroll track (to mark selections) */
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
		{
			super.paintTrack(g, c, trackBounds);
			
			// Find the total number of rows in the associated schema tree
			int totalRows = tree.getRowCount();
			
			// Mark all positions of selected rows on scroll bar
			if(searchResultRows.size()>0)
			{
				g.setColor(Color.red);
				int x1=trackBounds.x, x2=trackBounds.width;
				for(Integer row : searchResultRows)
				{
					int y=trackBounds.y+(int)(1.0*trackBounds.height*row/totalRows);
					g.fillRect(x1,y,x2,2);
				}
			}
		}
	}

	/** Updates the search result rows */
	private void updateSearchResultRows()
	{
		searchResultRows.clear();

		// Gets the matched elements
		HashMap<Integer,SearchResult> matches = harmonyModel.getSearchManager().getMatches(tree.getSide());
		
		// Identify the matched paths
		ArrayList<TreePath> paths = new ArrayList<TreePath>();
		Enumeration subNodes = tree.root.depthFirstEnumeration();
		while(subNodes.hasMoreElements())
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)subNodes.nextElement();
			if(!node.isRoot() && node.getUserObject() instanceof Integer)
				if(matches.containsKey(node.getUserObject()))
					paths.add(new TreePath(node.getPath()));
		}
		
		// Expand the paths and mark the rows
		for(TreePath path : paths) tree.expandFullPath(path);
		for(TreePath path : paths) searchResultRows.add(tree.getRowForPath(path));

		// Inform tree listeners of the modified tree structure
		tree.removeSchemaTreeListener(this);
		for(SchemaTreeListener listener : tree.getSchemaTreeListeners())
			listener.schemaStructureModified(tree);
		tree.addSchemaTreeListener(this);	
		
		// Repaint the scroll bar
		repaint();
	}
	
	/** Initializes the schema tree scroll bar */
	public SchemaScrollPane(MappingPane mappingPane, SchemaTreeImp tree, HarmonyModel harmonyModel)
	{
		this.mappingPane = mappingPane;
		this.tree = tree;
		this.harmonyModel = harmonyModel;
		
		// Set up scroll panes for the schema trees
		setViewportView(tree);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		getVerticalScrollBar().setUI(new SchemaScrollBarUI());
		
		// Shift scroll bars to mirror image for left schema tree
		if(tree.getSide()==MappingSchema.LEFT)
		{
			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			getHorizontalScrollBar().setValue(getHorizontalScrollBar().getMaximum());
		}
		
		// Initialize tree offset and visible row settings
		tree.offset = new Point(0,0);
		tree.firstVisibleRow = 0;
		tree.lastVisibleRow = 20;
		
		// Listen for changes which might affect offsets
		getHorizontalScrollBar().addAdjustmentListener(this);
		getVerticalScrollBar().addAdjustmentListener(this);
		harmonyModel.getSearchManager().addListener(this);
		tree.addSchemaTreeListener(this);
	}
	
	/** Updates the scroll pane offsets as needed */
	private Rectangle oldRect = null;
	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		// Update offset if rectangle has been adjusted
		Rectangle newRect = getViewport().getViewRect();
		if(oldRect==null || !newRect.equals(oldRect))
		{
			// Identify the x and y offsets based on the viewport
			int x = getViewport().getLocation().x-getViewport().getViewPosition().x;
			int y = getViewport().getLocation().y-getViewport().getViewPosition().y;

			// Identify the x and y offsets based on the panes between the viewport and the mapping pane
			Container container = getViewport().getParent();
			while(!(container instanceof MappingPane))
			{
				x += container.getLocation().x; y += container.getLocation().y;
				container = container.getParent();
			}
			
			// Set the adjusted offsets
			tree.offset = new Point(x,y);
			if(mappingPane.getLines()!=null) mappingPane.getLines().updateLines();
			oldRect = getViewport().getViewRect();
		}
		
		// Update first and last visible rows of schema tree
		Rectangle rect = getViewport().getViewRect();
		tree.firstVisibleRow = (int)rect.getMinY()/tree.getRowHeight();
		tree.lastVisibleRow = (int)rect.getMaxY()/tree.getRowHeight();
	}
	
	/** Updates the search result rows when the search results change */
	public void searchResultsModified(Integer side)
		{ if(tree.getSide().equals(side)) updateSearchResultRows(); }
	
	/** Update the search result rows when the schema structure changes */
	public void schemaStructureModified(SchemaTree tree)
		{ updateSearchResultRows(); }

	// Unused event listener
	public void schemaDisplayModified(SchemaTree tree) {}
}
