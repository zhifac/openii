// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.groupPane.managePane;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import model.UniversalObjects;

import view.sharedComponents.LinkedTreeNodeRenderer;

/** Class for rendering schema tree components */
class SchemaTreeCellRenderer extends LinkedTreeNodeRenderer
{
	/** Gets the label for the specified node */
	public JLabel getLabel(Object userObject)
	{
		// Determines what name to use
		String name = userObject.toString();
		if(userObject instanceof Integer) name = UniversalObjects.getName((Integer)userObject);
		
		// Constructs the label
		JLabel label = new JLabel(name);
		label.setBorder(new EmptyBorder(0,3,0,3));
		label.setFont(defaultFont);
		return label;
	}
	
	/** Get the icon to use for this component */
	public String getIconName(Object userObject)
	{
		if(userObject instanceof Integer)
			return UniversalObjects.isSchema((Integer)userObject) ? "Schema" : "Group";
		else if(userObject instanceof String && userObject.equals("Schemas")) return "Schemas";
		return null;
	}
	
	/** Gets the background color for the specified node */
	public Color getBackgroundColor(Object userObject)
		{ return Color.white; }
}
