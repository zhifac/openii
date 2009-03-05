// � The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.view.mappingPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.mitre.harmony.model.HarmonyConsts;
import org.mitre.harmony.model.MappingCellManager;
import org.mitre.harmony.model.filters.Filters;
import org.mitre.harmony.model.selectedInfo.SelectedInfo;
import org.mitre.harmony.view.dialogs.LinkDialog;

/**
 * Holds link pane which manages viewing of all links between schemas
 * 
 * @author CWOLF
 */
class MousePane extends JPanel implements MouseListener, MouseMotionListener {
	/** Variables used to keep track of mouse actions */
	private Point startPoint = null, endPoint = null;
	private TreePath leftPath = null, rightPath = null;

	/** Stores the left and right schema trees for local reference */
	private SchemaTreeImp leftTree = null, rightTree = null;

	/** Mapping Line tool tips **/
	private static LineToolTip toolTip = LineToolTip.instance();
	/** Indicate if user has set focused on a link. MouseOver is disabled if true. **/ 
	private boolean userSetFocus = false;

	/** Initializes the link pane */
	MousePane(SchemaTreeImp leftTree, SchemaTreeImp rightTree) {
		this.leftTree = leftTree;
		this.rightTree = rightTree;

		setOpaque(false);

		// Add mouse listeners
		leftTree.addMouseListenr(this);
		leftTree.addMouseMotionListenr(this);
		rightTree.addMouseListenr(this);
		rightTree.addMouseMotionListenr(this);

		// add tool tips for links
		add(toolTip);
	}

	/** Gets the schema tree path associated with the specified point */
	private void getSelectedRow(Point point) {
		// Update selected left and right row
		TreePath path;
		if (isValidPath(HarmonyConsts.LEFT, path = leftTree.getPathForLoc(point.x, point.y))) leftPath = path;
		if (isValidPath(HarmonyConsts.RIGHT, path = rightTree.getPathForLoc(point.x, point.y))) rightPath = path;
	}

	/** Determines if the indicated path can be the left or right of gestures. */
	private boolean isValidPath(Integer role, TreePath path) {
		// If the path does not exist, or is the root disallow gestures.
		if (path == null) return false;
		if (path.getPathCount() <= 1) return false;

		// Otherwise, the path is valid if the indicated node is visible
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		if (!(node.getUserObject() instanceof Integer)) return false;
		return Filters.visibleNode(role, node);
	}

	/** Indicates if the specified point is over a node */
	private boolean overlapsNode(Point point) {
		if (leftTree.getPathForLoc(point.x + 18, point.y) != null) return true;
		if (rightTree.getPathForLoc(point.x + 18, point.y) != null) return true;
		return false;
	}

	/** Handles drawing of new links and selection of old links */
	public void mousePressed(MouseEvent e) {
		// If left button pressed, find selected node or link
		if (e.getButton() == MouseEvent.BUTTON1) {
			// Determine if a node was selected
			getSelectedRow(e.getPoint());

			// If no node selected, find what old link might have been selected
			if (leftPath == null && rightPath == null && !overlapsNode(e.getPoint())) startPoint = e.getPoint();
		}

		// If right button pressed, display link dialog box
		else if (e.getButton() == MouseEvent.BUTTON3) {
			// Determine what link was selected for showing the dialog box
			Integer mappingCellID = MappingLines.mappingLines.getClosestMappingCellToPoint(e.getPoint());
			if (mappingCellID != null) {
				// Mark the link as selected (if not already done)
				if (!SelectedInfo.isMappingCellSelected(mappingCellID)) {
					ArrayList<Integer> mappingCellIDs = new ArrayList<Integer>();
					mappingCellIDs.add(mappingCellID);
					SelectedInfo.setMappingCells(mappingCellIDs, false);
				}

				// Determine the current mouse position
				int x = e.getX(), y = e.getY();
				Component comp = this;
				while (comp != null) {
					x += comp.getX();
					y += comp.getY();
					comp = comp.getParent();
				}

				// Display the link dialog box next to the selected link
				LinkDialog linkDialog = new LinkDialog(SelectedInfo.getSelectedMappingCells());
				linkDialog.setLocation(x, y);
				linkDialog.setVisible(true);
			}
		}
	}

	/**
	 * When mouse moves over MappingCellLinks, display SelectedInfo for the mapped cells and
	 * confidence values of the link.
	 */
	public void mouseMoved(MouseEvent e) {
		if (userSetFocus) return;

		// Determine what link was selected for showing the dialog box
		Integer mappingCellID = MappingLines.mappingLines.getClosestMappingCellToPoint(e.getPoint());
		if (mappingCellID != null) {
			// Mark the link as selected (if not already done)
			if (!SelectedInfo.isMappingCellSelected(mappingCellID)) {
				ArrayList<Integer> mappingCellIDs = new ArrayList<Integer>();
				mappingCellIDs.add(mappingCellID);
				SelectedInfo.setMappingCells(mappingCellIDs, false);
			}

			// Determine the current mouse position
			int x = e.getX(), y = e.getY();
			Component comp = this;
			while (comp != null) {
				x += comp.getX();
				y += comp.getY();
				comp = comp.getParent();
			}

			// Display the link dialog box next to the selected link
			toolTip.setMappingCell(mappingCellID);
			toolTip.setLocation(e.getX() + 20, e.getY() + 20);
			toolTip.setVisible(true);
		} else {
			// clears selection 
			toolTip.setVisible(false);
			if (SelectedInfo.getMappingCells().size() > 0) SelectedInfo.setMappingCells(new ArrayList<Integer>(), false);
		}
	}

	/** Handles the drawing of the new link as it is dragged around */
	public void mouseDragged(MouseEvent e) {
		endPoint = e.getPoint();

		// Calculate selected row point
		Rectangle rect = null;
		if (leftPath != null) rect = leftTree.getPthBounds(leftPath);
		if (rightPath != null) rect = rightTree.getPthBounds(rightPath);

		// Draw line between selected row and mouse
		if (rect != null || startPoint != null) repaint();
	}

	/** Handles the creation of a new link once the mouse is released */
	public void mouseReleased(MouseEvent e) {
		// Only take action if left button is released
		if (e.getButton() == MouseEvent.BUTTON1) {
			// Determine if a node was selected
			getSelectedRow(e.getPoint());

			// Ensure that the link was drawn between a left and right element
			if (leftPath != null && rightPath != null) {
				// Gather info on what left and right nodes are connected
				Integer leftID = leftTree.getNode(leftPath);
				Integer rightID = rightTree.getNode(rightPath);
				Integer mappingCellID = MappingCellManager.getMappingCellID(leftID, rightID);
				if (mappingCellID == null) mappingCellID = MappingCellManager.createMappingCell(leftID, rightID);
				MappingCellManager.modifyMappingCell(mappingCellID, 1.0, System.getProperty("user.name"), true);
			}
			repaint();

			// Select all links within the bounding box
			if (startPoint != null) if (endPoint == null) {
				Integer link = MappingLines.mappingLines.getClosestMappingCellToPoint(startPoint);
				ArrayList<Integer> links = new ArrayList<Integer>();
				if (link != null) links.add(link);
				SelectedInfo.setMappingCells(links, e.isControlDown());
			} else {
				int x1 = startPoint.x < endPoint.x ? startPoint.x : endPoint.x;
				int y1 = startPoint.y < endPoint.y ? startPoint.y : endPoint.y;
				int width = Math.abs(startPoint.x - endPoint.x) + 1;
				int height = Math.abs(startPoint.y - endPoint.y) + 1;
				Rectangle rect = new Rectangle(x1, y1, width, height);
				SelectedInfo.setMappingCells(MappingLines.mappingLines.getMappingCellsInRegion(rect), e.isControlDown());
			}

			// Reinitialize the left and right paths for future use
			startPoint = null;
			endPoint = null;
			leftPath = null;
			rightPath = null;
		}
	}

	/** Draw the selection reference and possible new mapping line */
	public void paint(Graphics g) {
		// Draws possible new mapping line
		if ((leftPath != null || rightPath != null) && endPoint != null) {
			Rectangle rect = null;
			if (leftPath != null) rect = leftTree.getPthBounds(leftPath);
			if (rightPath != null) rect = rightTree.getPthBounds(rightPath);
			if (rect != null) {
				g.setColor(Color.red);
				g.drawLine((int) rect.getCenterX(), (int) rect.getCenterY(), endPoint.x, endPoint.y);
			}
		}

		// Draws the selection reference
		if (startPoint != null && endPoint != null) {
			// Calculate points on rectangle to be drawn
			int x1 = startPoint.x < endPoint.x ? startPoint.x : endPoint.x;
			int y1 = startPoint.y < endPoint.y ? startPoint.y : endPoint.y;
			int width = Math.abs(startPoint.x - endPoint.x);
			int height = Math.abs(startPoint.y - endPoint.y);

			// Draw the selection rectangle
			((Graphics2D) g).setStroke(new BasicStroke((float) 1.0, BasicStroke.CAP_SQUARE,
					BasicStroke.JOIN_BEVEL, (float) 0.0, new float[] { 5, 5 }, (float) 2.0));
			g.setColor(Color.darkGray);
			g.drawRect(x1, y1, width, height);
		}

		super.paint(g);
	}

	// Unused listener events
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		Integer mappingCellID = MappingLines.mappingLines.getClosestMappingCellToPoint(e.getPoint());
		if (mappingCellID != null) {
			if (!SelectedInfo.isMappingCellSelected(mappingCellID)) {
				ArrayList<Integer> mappingCellIDs = new ArrayList<Integer>();
				mappingCellIDs.add(mappingCellID);
				SelectedInfo.setMappingCells(mappingCellIDs, true);
				userSetFocus = true;
			}
		} else {
			SelectedInfo.setMappingCells(new ArrayList<Integer>(), false);
			userSetFocus = false;
		}
	}

}

class LineToolTip extends JPanel {

	private Integer link;
	private JLabel label;
	private static LineToolTip instance;

	private LineToolTip() {
		label = new JLabel("");
		add(label);

		setOpaque(true);
		setSize(new Dimension(200, getFontMetrics(getFont()).getHeight() * 2));
	}

	public static LineToolTip instance() {
		if (instance == null) instance = new LineToolTip();
		return instance;
	}

	public void setMappingCell(Integer mappingCell) {
		link = mappingCell;

		String notes = MappingCellManager.getMappingCellNotes(link);

		// set new text for tool tip
		String display = "<html>";
		display += "Confidence: " + MappingCellManager.getMappingCell(link).getScore() + "<br>";
		display += (notes.equalsIgnoreCase("notes") || notes.length() == 0) ? "" : "Notes: "
				+ notes;
		display += "</html>";
		label.setText(display);
	}

	public void paint(Graphics g) {
		// I don't know why but sometimes mouseMove event sets the location to Y=5
		if (!isVisible() || getLocation().y == 5) return;

		super.paint(g);

		// draw border
		g.setColor(Color.darkGray);
		g.drawRect(0, 0, getSize().width - 2, getSize().height - 2);

		setBackground(UIManager.getColor("ToolTip.background"));
	}

}
