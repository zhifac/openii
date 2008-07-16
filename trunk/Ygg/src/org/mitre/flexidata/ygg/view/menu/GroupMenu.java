// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.view.menu;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/** Class for displaying the group menu pane */
public class GroupMenu extends JPanel
{	
	/** Constructs the group menu pane */
	public GroupMenu()
	{	
		// Generate the group menu pane
		setOpaque(false);
		setLayout(new BorderLayout());
		add(new JLabel("Group Menu Pane"),BorderLayout.CENTER);
	}
}