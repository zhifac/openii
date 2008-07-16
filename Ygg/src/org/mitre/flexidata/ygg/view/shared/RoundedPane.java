// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.shared;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/** Class for displaying a pane with a rounded border */
public class RoundedPane extends JPanel
{	
	/** Stores the interior pane */
	private JPanel interiorPane = new JPanel();

	/** Constructs a pane with a rounded border */
	public RoundedPane()
	{
		// Constructs a pane for the interior pane
		interiorPane.setOpaque(false);
		interiorPane.setBorder(new EmptyBorder(5,5,5,5));
		interiorPane.setLayout(new BorderLayout());
		
		// Construct the rounded pane
		setOpaque(false);
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		add(interiorPane,BorderLayout.CENTER);
	}

	/** Sets the view for the rounded pane */
	public void setView(JPanel pane)
	{
		interiorPane.removeAll();
		interiorPane.add(pane,BorderLayout.CENTER);
		revalidate();
	}
	
	/** Paints the rounded border around pane */
	public void paint(Graphics g)
	{
		// Paint the rounded border
		JPanel interiorPane = (JPanel)this.getComponent(0);
		g.setColor(getBackground());
		g.fillRoundRect(interiorPane.getX(), interiorPane.getY(), interiorPane.getWidth()-1, interiorPane.getHeight()-1, 20, 20);
		g.setColor(Color.BLACK);
		g.drawRoundRect(interiorPane.getX(), interiorPane.getY(), interiorPane.getWidth()-1, interiorPane.getHeight()-1, 20, 20);

		// Paint the interior pane
		super.paint(g);
	}
}