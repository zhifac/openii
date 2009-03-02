// © The MITRE Corporation 2006
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
import javax.swing.JViewport;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.plaf.metal.MetalScrollBarUI;

import org.mitre.harmony.model.HarmonyConsts;

/**
 * Displays the scroll pane next to each schema tree pane (includes selection marks)
 * @author CWOLF
 */
public class SchemaScrollPane extends JScrollPane implements AdjustmentListener
{
	private SchemaTreeImp tree;	// Schema tree associated with this scroll bar

	/**
	 * Class used to reimplement the scroll track display
	 * @author CWOLF
	 */
	private class SchemaScrollBarUI extends MetalScrollBarUI
	{	
		/**
		 * Redefines the drawing of the scroll track (to mark selections)
		 */
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
		{
			super.paintTrack(g, c, trackBounds);
			
			// Find the selected rows of the associated schema tree
			int rows[] = tree.getSelectionRows();
			int totalRows = tree.getRowCount();
			
			// Mark all positions of selected rows on scroll bar
			if(rows!=null) {
				g.setColor(Color.red);
				int x1=trackBounds.x, x2=trackBounds.width;
				for(int i=0; i<rows.length; i++)
				{
					int y=trackBounds.y+(int)(1.0*trackBounds.height*rows[i]/totalRows);
					g.fillRect(x1,y,x2,2);
				}
			}
		}
	}

	/**
	 * Initializes the schema tree scroll bar
	 * @param tree Schema tree associated with the scroll bar
	 */
	public SchemaScrollPane(SchemaTreeImp tree)
	{
		this.tree=tree;
		
		// Set up scroll panes for the schema trees
		setViewportView(tree);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		getVerticalScrollBar().setUI(new SchemaScrollBarUI());
		
		// Shift scroll bars to mirror image for left schema tree
		if(tree.getRole()==HarmonyConsts.LEFT) {
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
	}
	
	/**
	 * Updates the scroll pane offsets as needed
	 */
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
			if(MappingLines.mappingLines!=null) MappingLines.mappingLines.updateLines();
			oldRect = getViewport().getViewRect();
		}
		
		// Update first and last visible rows of schema tree
		Rectangle rect = getViewport().getViewRect();
		tree.firstVisibleRow = (int)rect.getMinY()/tree.getRowHeight();
		tree.lastVisibleRow = (int)rect.getMaxY()/tree.getRowHeight();
	}
}
