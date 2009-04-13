// © The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.harmony.model.mapping.MappingCellManager;
import org.mitre.harmony.view.dialogs.ConfidenceDialog;
import org.mitre.harmony.view.dialogs.MappingCellDialog;
import org.mitre.schemastore.model.MappingCell;

/**
 * Holds mouse pane which manages all mouse actions
 * 
 * @author CWOLF
 */
class MousePane extends JPanel implements MouseListener, MouseMotionListener
{
	/** Variables used to keep track of mouse actions */
	private Point startPoint = null, endPoint = null, pausePoint = null;
	private TreePath leftPath = null, rightPath = null;

	/** Stores the mappingPane to which the mouse pane is associated */
	private MappingPane mappingPane;

	/** Stores the Harmony model */
	private HarmonyModel harmonyModel;

	/** Stores the confidence dialog when visible */
	private ConfidenceDialog confidenceDialog = null;

	/** Confidence dialog timer */
	private Timer cdTimer = null;
	private int CONFIDENCE_DIALOG_POP_UP_TIMER = 500;

	/** Initializes the mouse pane */
	MousePane(MappingPane mappingPane, HarmonyModel harmonyModel)
	{
		// Initialize the mouse pane
		this.mappingPane = mappingPane;
		this.harmonyModel = harmonyModel;
		setOpaque(false);

		// Add mouse listeners
		mappingPane.getTree(HarmonyConsts.LEFT).addMouseListenr(this);
		mappingPane.getTree(HarmonyConsts.LEFT).addMouseMotionListenr(this);
		mappingPane.getTree(HarmonyConsts.RIGHT).addMouseListenr(this);
		mappingPane.getTree(HarmonyConsts.RIGHT).addMouseMotionListenr(this);
	}

	/** Gets the schema tree path associated with the specified point */
	private void getSelectedRow(Point point)
	{
		TreePath path;
		if(isValidPath(HarmonyConsts.LEFT, path = mappingPane.getTree(HarmonyConsts.LEFT).getPathForLoc(point.x, point.y))) leftPath = path;
		if(isValidPath(HarmonyConsts.RIGHT, path = mappingPane.getTree(HarmonyConsts.RIGHT).getPathForLoc(point.x, point.y))) rightPath = path;
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
		if(mappingPane.getTree(HarmonyConsts.LEFT).getPathForLoc(point.x + 18, point.y) != null) return true;
		if(mappingPane.getTree(HarmonyConsts.RIGHT).getPathForLoc(point.x + 18, point.y) != null) return true;
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
		// Release any temporarily selected mapping cell
		if(confidenceDialog != null)
		{
			confidenceDialog.dispose();
			confidenceDialog = null;
			if(harmonyModel.getSelectedInfo().getSelectedMappingCells().size() > 0) harmonyModel.getSelectedInfo().setMappingCells(new ArrayList<Integer>(), false);
		}

		// If left button pressed, find selected node or start bounding box
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			// Determine if a node was selected
			getSelectedRow(e.getPoint());

			// If no node selected, start bounding box for selecting existent
			// mapping cells
			if(leftPath == null && rightPath == null && !overlapsNode(e.getPoint())) startPoint = e.getPoint();
		}

		// If right button pressed, display mapping cell dialog box
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			// Determine what mapping cell was selected for showing the dialog
			// box
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

	/**
	 * Display SelectedInfo pane and confidence value for mapping cells when
	 * mouse hovering over node
	 */
	public void mouseMoved(MouseEvent e)
	{
		// Don't proceed if mouse is currently being dragged
		if(startPoint != null) return;

		// Don't proceed if selection was not made by mouse movement
		Integer leftCount = harmonyModel.getSelectedInfo().getSelectedElements(HarmonyConsts.LEFT).size();
		Integer rightCount = harmonyModel.getSelectedInfo().getSelectedElements(HarmonyConsts.RIGHT).size();
		if(confidenceDialog == null && (leftCount > 0 || rightCount > 0)) return;

		// Run a pop up timer to display confidence dialog
		pausePoint = e.getPoint();
		if(cdTimer == null)
		{
			cdTimer = new Timer(CONFIDENCE_DIALOG_POP_UP_TIMER, new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
					{ showConfidenceDialog(); }
			});
			cdTimer.start();
			cdTimer.setRepeats(false);
		} 
		else if(confidenceDialog != null) clearConfidenceDialog();
		else cdTimer.restart();
	}

	private void showConfidenceDialog()
	{
		// Display information about the closest mapping cell
		Integer mappingCellID = mappingPane.getLines().getClosestMappingCellToPoint(pausePoint);
		if(mappingCellID != null)
		{
			// Only mark as selected if not already done so
			if(!harmonyModel.getSelectedInfo().isMappingCellSelected(mappingCellID))
			{
				// Mark the mapping cell as selected
				ArrayList<Integer> mappingCellIDs = new ArrayList<Integer>();
				mappingCellIDs.add(mappingCellID);
				harmonyModel.getSelectedInfo().setMappingCells(mappingCellIDs, false);

				// Display the dialog box next to the selected mapping cell
				Point point = adjustMouseLocation(pausePoint);
				point.translate(20, 20);
				if(confidenceDialog == null) confidenceDialog = new ConfidenceDialog(harmonyModel);
				confidenceDialog.setMappingCell(mappingCellID);
				confidenceDialog.setLocation(point);
				confidenceDialog.setVisible(true);
			}
		}

		// Clear information about the displayed mapping cell if no mapping cell selected
		else if (confidenceDialog != null)
			clearConfidenceDialog();
	}

	private void clearConfidenceDialog()
	{
		cdTimer.stop();
		confidenceDialog.dispose();
		confidenceDialog = null;
		if(harmonyModel.getSelectedInfo().getSelectedMappingCells().size() > 0) harmonyModel.getSelectedInfo().setMappingCells(new ArrayList<Integer>(), false);
	}

	/**
	 * Handles the drawing of the new mapping cell or bounding box as the mouse
	 * is dragged around
	 */
	public void mouseDragged(MouseEvent e)
	{
		endPoint = e.getPoint();

		// Calculate selected row point
		Rectangle rect = null;
		if(leftPath != null) rect = mappingPane.getTree(HarmonyConsts.LEFT).getPthBounds(leftPath);
		if(rightPath != null) rect = mappingPane.getTree(HarmonyConsts.RIGHT).getPthBounds(rightPath);

		// Draw line between selected row and mouse
		if(rect != null || startPoint != null) repaint();
	}

	/**
	 * Handles the creation of a new link or selects mapping cells in bounding
	 * box when mouse is released
	 */
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
				Integer leftID = mappingPane.getTree(HarmonyConsts.LEFT).getNode(leftPath);
				Integer rightID = mappingPane.getTree(HarmonyConsts.RIGHT).getNode(rightPath);

				// Set the mapping cell
				MappingCellManager manager = harmonyModel.getMappingCellManager();
				MappingCell mappingCell = new MappingCell();
				mappingCell.setId(manager.getMappingCellID(leftID, rightID));
				mappingCell.setElement1(leftID);
				mappingCell.setElement2(rightID);
				mappingCell.setScore(1.0);
				mappingCell.setAuthor(System.getProperty("user.name"));
				mappingCell.setValidated(true);
				manager.setMappingCell(mappingCell);
			}
			repaint();

			// Select all mapping cells next to the clicked location or within
			// the bounding box
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

				// Handles the case where a bounding box was drawn around
				// mapping cells to select
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
			if(leftPath != null) rect = mappingPane.getTree(HarmonyConsts.LEFT).getPthBounds(leftPath);
			if(rightPath != null) rect = mappingPane.getTree(HarmonyConsts.RIGHT).getPthBounds(rightPath);
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
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}