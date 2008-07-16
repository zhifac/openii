// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.schema.view;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mitre.flexidata.ygg.view.Consts;

/** Class for handling linked text */
class LinkedText extends JLabel implements MouseListener, MouseMotionListener
{
	/** Stores the type of link */
	private Class<?> type = null;
	
	/** Stores the schema element id if one exists */
	private Integer elementID = null;
	
	/** Constructs the linked text */
	LinkedText(String label, Class<?> type, Integer elementID)
	{
		super(label); this.type = type; this.elementID = elementID;
		setForeground(Consts.LIGHT_BLUE);
		setFont(Consts.OPTION_FONT);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/** Handles the selection of linked text */
	public void mouseClicked(MouseEvent arg0)
	{
		// Find the schema element pane
		JPanel schemaElementPane = (JPanel)getParent();
		while(!(schemaElementPane instanceof SchemaElementPane))
			schemaElementPane = (JPanel)schemaElementPane.getParent();

		// Reset the schema element view
		((SchemaElementPane)schemaElementPane).updateSchemaElementList(type, elementID);
	}
	
	// Display a hand cursor when the mouse is hovering above the linked text
	public void mouseEntered(MouseEvent arg0) { setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
	public void mouseExited(MouseEvent arg0) { setCursor(Cursor.getDefaultCursor()); }
	
	// Unused listener events
	public void mouseDragged(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
