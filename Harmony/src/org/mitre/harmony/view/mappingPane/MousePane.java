// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.harmony.view.dialogs.mappingCell.MappingCellDialog;
import org.mitre.schemastore.model.mapfunctions.IdentityFunction;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.MappingSchema;

/**
 * Holds mouse pane which manages all mouse actions
 * 
 * @author CWOLF
 */
class MousePane extends JPanel implements MouseListener, MouseMotionListener
{
	/** Variables used to keep track of mouse actions */
	private Point startPoint = null, endPoint = null;
	private TreePath leftPath = null, rightPath = null;

	/** Stores the mappingPane to which the mouse pane is associated */
	private MappingPane mappingPane;

	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;

	/** Initializes the mouse pane */
	MousePane(MappingPane mappingPane, HarmonyModel harmonyModel)
	{
		// Initialize the mouse pane
		this.mappingPane = mappingPane;
		this.harmonyModel = harmonyModel;
		setOpaque(false);

		// Add mouse listeners
		mappingPane.getTree(MappingSchema.LEFT).addMouseListenr(this);
		mappingPane.getTree(MappingSchema.LEFT).addMouseMotionListenr(this);
		mappingPane.getTree(MappingSchema.RIGHT).addMouseListenr(this);
		mappingPane.getTree(MappingSchema.RIGHT).addMouseMotionListenr(this);
	}

	/** Gets the schema tree path associated with the specified point */
	private void getSelectedRow(Point point)
	{
		TreePath path;
		if(isValidPath(MappingSchema.LEFT, path = mappingPane.getTree(MappingSchema.LEFT).getPathForLoc(point.x, point.y))) leftPath = path;
		if(isValidPath(MappingSchema.RIGHT, path = mappingPane.getTree(MappingSchema.RIGHT).getPathForLoc(point.x, point.y))) rightPath = path;
	}

	/** Determines if the indicated path can be the left or right of gestures. */
	private boolean isValidPath(Integer role, TreePath path)
	{
		// If the path does not exist, or is the root disallow gestures.
		if(path == null) return false;
		if(path.getPathCount() <= 1) return false;

		// Otherwise, the path is valid if the indicated node is visible
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		if(!(node.getUserObject() instanceof Integer)) return false;
		return harmonyModel.getFilters().isVisibleNode(role, node);
	}

	/** Indicates if the specified point is over a node */
	private boolean overlapsNode(Point point)
	{
		if(mappingPane.getTree(MappingSchema.LEFT).getPathForLoc(point.x + 18, point.y) != null) return true;
		if(mappingPane.getTree(MappingSchema.RIGHT).getPathForLoc(point.x + 18, point.y) != null) return true;
		return false;
	}

	/** Adjusts the point to the specified parent component */
	private Point adjustMouseLocation(Point point)
	{
		int x = point.x, y = point.y;
		Component comp = this;
		while(comp != null)
		{
			x += comp.getX();
			y += comp.getY();
			comp = comp.getParent();
		}
		return new Point(x, y);
	}

	/** Handles drawing of new mapping cells and selection of old mapping cells */
	public void mousePressed(MouseEvent e)
	{
		// If left button pressed, find selected node or start bounding box
		if(e.getButton() == MouseEvent.BUTTON1 && !e.isControlDown())
		{
			// Determine if a node was selected
			getSelectedRow(e.getPoint());

			// If no node selected, start bounding box for selecting existent mapping cells
			if(leftPath == null && rightPath == null && !overlapsNode(e.getPoint())) startPoint = e.getPoint();
		}

		// If right button pressed, display mapping cell dialog box
		else if(e.getButton()==MouseEvent.BUTTON3 || e.isControlDown())
		{
			// Determine what mapping cell was selected for showing the dialog box
			Integer mappingCellID = mappingPane.getLines().getClosestMappingCellToPoint(e.getPoint());
			if(mappingCellID != null)
			{
				// Mark the mapping cell as selected (if not already done)
				if(!harmonyModel.getSelectedInfo().isMappingCellSelected(mappingCellID))
				{
					ArrayList<Integer> mappingCellIDs = new ArrayList<Integer>();
					mappingCellIDs.add(mappingCellID);
					harmonyModel.getSelectedInfo().setMappingCells(mappingCellIDs, false);
				}

				// Display the dialog box next to the selected mapping cell
				ArrayList<MappingCell> mappingCells = new ArrayList<MappingCell>();
				for(Integer selectedMappingCellID : harmonyModel.getSelectedInfo().getSelectedMappingCells())
					mappingCells.add(harmonyModel.getMappingCellManager().getMappingCell(selectedMappingCellID));
				MappingCellDialog mappingCellDialog = new MappingCellDialog(mappingCells, harmonyModel);
				mappingCellDialog.setLocation(adjustMouseLocation(e.getPoint()));
				mappingCellDialog.setVisible(true);
			}
		}
	}

	/** Handles the drawing of the new mapping cell or bounding box as the mouse is dragged around */
	public void mouseDragged(MouseEvent e)
	{
		endPoint = e.getPoint();

		// Calculate selected row point
		Rectangle rect = null;
		if(leftPath != null) rect = mappingPane.getTree(MappingSchema.LEFT).getPthBounds(leftPath);
		if(rightPath != null) rect = mappingPane.getTree(MappingSchema.RIGHT).getPthBounds(rightPath);

		// Draw line between selected row and mouse
		if(rect != null || startPoint != null) repaint();
	}

	/** Handles the creation of a new link or selects mapping cells in bounding box when mouse is released */
	public void mouseReleased(MouseEvent e)
	{
		// Only take action if left button is released
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			// Determine if a node was selected
			getSelectedRow(e.getPoint());

			// Ensure that the link was drawn between a left and right element
			if(leftPath != null && rightPath != null)
			{
				// Gather info on what left and right nodes are connected
				Integer leftID = mappingPane.getTree(MappingSchema.LEFT).getElement(leftPath);
				Integer rightID = mappingPane.getTree(MappingSchema.RIGHT).getElement(rightPath);

				// Generate the mapping cell
				MappingCellManager manager = harmonyModel.getMappingCellManager();
				Integer id = manager.getMappingCellID(leftID, rightID);
				Integer mappingID = harmonyModel.getMappingManager().getMapping().getId();
				String author = System.getProperty("user.name");
				Date date = Calendar.getInstance().getTime();
				String function = IdentityFunction.class.getCanonicalName();
				MappingCell mappingCell = MappingCell.createValidatedMappingCell(id, mappingID, new Integer[]{leftID}, rightID, author, date, function, null);
				manager.setMappingCell(mappingCell);
			}
			repaint();

			// Select all mapping cells next to the clicked location or within the bounding box
			if (startPoint != null)
			{
				// Handles the case where a single mapping cell is selected
				if (endPoint == null)
				{
					Integer mappingCell = mappingPane.getLines().getClosestMappingCellToPoint(startPoint);
					ArrayList<Integer> mappingCells = new ArrayList<Integer>();
					if (mappingCell != null) mappingCells.add(mappingCell);
					harmonyModel.getSelectedInfo().setMappingCells(mappingCells, e.isControlDown());
				}

				// Handles the case where a bounding box was drawn around mapping cells to select
				else
				{
					int x1 = startPoint.x < endPoint.x ? startPoint.x : endPoint.x;
					int y1 = startPoint.y < endPoint.y ? startPoint.y : endPoint.y;
					int width = Math.abs(startPoint.x - endPoint.x) + 1;
					int height = Math.abs(startPoint.y - endPoint.y) + 1;
					Rectangle rect = new Rectangle(x1, y1, width, height);
					harmonyModel.getSelectedInfo().setMappingCells(mappingPane.getLines().getMappingCellsInRegion(rect), e.isControlDown());
				}
			}

			// Reinitialize the left and right paths for future use
			startPoint = null;
			endPoint = null;
			leftPath = null;
			rightPath = null;
		}
	}

	/** Draw the selection reference and possible new mapping line */
	public void paint(Graphics g)
	{
		// Draws possible new mapping line
		if((leftPath != null || rightPath != null) && endPoint != null)
		{
			Rectangle rect = null;
			if(leftPath != null) rect = mappingPane.getTree(MappingSchema.LEFT).getPthBounds(leftPath);
			if(rightPath != null) rect = mappingPane.getTree(MappingSchema.RIGHT).getPthBounds(rightPath);
			if(rect != null)
			{
				g.setColor(Color.red);
				g.drawLine((int) rect.getCenterX(), (int) rect.getCenterY(), endPoint.x, endPoint.y);
			}
		}

		// Draws the selection reference
		if (startPoint != null && endPoint != null)
		{
			// Calculate points on rectangle to be drawn
			int x1 = startPoint.x < endPoint.x ? startPoint.x : endPoint.x;
			int y1 = startPoint.y < endPoint.y ? startPoint.y : endPoint.y;
			int width = Math.abs(startPoint.x - endPoint.x);
			int height = Math.abs(startPoint.y - endPoint.y);

			// Draw the selection rectangle
			((Graphics2D) g).setStroke(new BasicStroke((float) 1.0, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, (float) 0.0, new float[] { 5, 5 }, (float) 2.0));
			g.setColor(Color.darkGray);
			g.drawRect(x1, y1, width, height);
		}

		super.paint(g);
	}

	// Unused listener events
	public void mouseClicked(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}