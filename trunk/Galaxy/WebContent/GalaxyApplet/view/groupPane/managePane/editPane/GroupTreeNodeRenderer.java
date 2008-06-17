// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane.editPane;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import model.UniversalObjects;

import view.sharedComponents.LinkedTreeNodeRenderer;

/** Abstract class for rendering group tree nodes */
public class GroupTreeNodeRenderer extends LinkedTreeNodeRenderer
{
	/** Gets the label for the specified node */
	public JLabel getLabel(Object userObject)
	{
		// Get the label text
		String name = userObject.toString();
		if(userObject instanceof Integer) name = UniversalObjects.getName((Integer)userObject);
		
		// Generate the label
		JLabel label = new JLabel(name);
		label.setBorder(new EmptyBorder(0,3,0,3));
		label.setFont(defaultFont);
		return label;
	}
	
	/** Gets the icon for the specified node */
	public String getIconName(Object userObject)
		{ return userObject instanceof Integer ? "Group" : "Groups"; }
	
	/** Gets the background color for the specified node */
	public Color getBackgroundColor(Object userObject)
		{ return Color.white; }
}
