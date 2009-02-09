// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.schemaPane.navigatorPane;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.mitre.galaxy.view.schemaPane.SchemaPane;


/** Class for displaying the navigator pane of Galaxy */
public class NavigatorPane extends JPanel
{	
	/** Stores the navigator display */
	private NavigatorDisplay display = null;
	
	/** Constructs the navigator pane of Galaxy */
	public NavigatorPane(SchemaPane schemaPane)
	{
		// Set up the display
		display = new NavigatorDisplay(schemaPane);
		display.setBackground(new Color((float)1.0,(float)1.0,(float)0.85));
		
		// Set up the display pane
		JPanel displayPane = new JPanel();
		displayPane.setBorder(new LineBorder(Color.gray));
		displayPane.setLayout(new BorderLayout());
		displayPane.add(display);
		
		// Layout the Navigator Pane
		setBounds(5,5,150,150);
		setBorder(new CompoundBorder(new LineBorder(Color.gray),new EmptyBorder(3,3,3,3)));
		setLayout(new BorderLayout());
		add(displayPane,BorderLayout.CENTER);
	}
	
	/** Resets the navigator pane */
	public void reset() { display.reset(); }
}
