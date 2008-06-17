// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import model.Groups;
import model.UniversalObjects;

import view.sharedComponents.LinkedTreeNodeRenderer;

/** Class for rendering group tree components */
class GroupTreeNodeRenderer extends LinkedTreeNodeRenderer
{
	/** Gets the label for the specified node */
	public JLabel getLabel(Object userObject)
	{
		// Determines what name to use
		String name = userObject.toString();
		if(userObject instanceof Integer) name = UniversalObjects.getName((Integer)userObject);
		
		// Determines the counts for groups
		Integer count = null;
		if(userObject instanceof Integer && UniversalObjects.isGroup((Integer)userObject))
			count = Groups.getGroupSchemaCount((Integer)userObject);
		
		// Constructs the label
		JLabel label = new JLabel(name + (count==null||count.equals(0)?"":"("+count+")"));
		label.setBorder(new EmptyBorder(0,3,0,3));
		label.setFont(defaultFont);
		return label;
	}
	
	/** Get the icon to use for this component */
	public String getIconName(Object userObject)
	{
		String iconName = null;
		if(userObject instanceof String)
		{
			if(userObject.equals("Groups")) iconName = "Groups";
			else if(userObject.equals("Schemas")) iconName = "Schemas";
			else if(userObject.equals("Unassigned")) iconName = "Unassigned";
		}
		else iconName = UniversalObjects.isGroup((Integer)userObject) ? "Group" : "Schema";
		return iconName;
	}
	
	/** Gets the background color for the specified node */
	public Color getBackgroundColor(Object userObject)
		{ return Color.white; }
}
