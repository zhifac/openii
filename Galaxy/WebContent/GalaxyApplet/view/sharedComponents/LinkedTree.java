// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.sharedComponents;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import view.sharedComponents.LinkedTreeNode;

/** Class for displaying the linked tree */
public class LinkedTree extends JTree implements MouseListener, MouseMotionListener
{	
	/** Constructs the schema tree */
	protected LinkedTree()
	{
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/** Handles the selection of a link */
	public void mouseClicked(MouseEvent e)
	{
		TreePath path = getPathForLocation(e.getX(),e.getY());
		if(path!=null)
			if(getCursor().equals(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
				((LinkedTreeNode)path.getLastPathComponent()).executeLinkAction();
	}
	
	/** Displays the mouse hand when hovering over a link */
	public void mouseMoved(MouseEvent e)
	{
		// Identify the path over which the mouse is hovering
		TreePath path = getPathForLocation(e.getX(),e.getY());
		
		// Display a hand cursor if hovering above link
		Cursor cursor = Cursor.getDefaultCursor();
		if(path!=null && ((LinkedTreeNode)path.getLastPathComponent()).containsLink())
		{
			String link = ((LinkedTreeNode)path.getLastPathComponent()).getLinkString();
			Rectangle2D rowBounds = getRowBounds(getRowForPath(path));
			int linkLength = getFontMetrics(LinkedTreeNodeRenderer.linkFont).stringWidth("("+link+")");
			if(e.getX()>rowBounds.getMaxX()-linkLength)
				cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		}
		if(!cursor.equals(getCursor())) setCursor(cursor);
	}
	
	/** Resets the cursor when no longer hovering above link */
	public void mouseExited(MouseEvent e)
		{ setCursor(Cursor.getDefaultCursor()); }

	// Unused listener event
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}