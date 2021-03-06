// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.extensionsPane;

import java.awt.Color;

import org.mitre.schemastore.model.Schema;

import prefuse.action.ActionList;
import prefuse.action.assignment.ColorAction;
import prefuse.util.ColorLib;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

/** Class for displaying the colors associated with the extensions graph */
public class ExtensionColors
{
	/** Stores reference to the extensions pane */
	private ExtensionsPane pane = null;
	
	/** Constructs the SchemaColors object */
	ExtensionColors(ExtensionsPane pane)
		{ this.pane = pane; }
	
	/** Private class for handling node fill colors */
	private class NodeFillColorAction extends ColorAction
	{
		/** Constructs the node fill color action */
		private NodeFillColorAction() { super("graph.nodes",VisualItem.FILLCOLOR,0); }
		
		/** Returns the fill color associated with the specified node */
		public int getColor(VisualItem item)
		{
			// Define various node fill colors
			final int schemaColor = ColorLib.color(Color.white);
			final int unavailableTagColor = ColorLib.color(Color.lightGray);
			final int selectedSchemaColor = ColorLib.color(Color.yellow);
			final int comparisonSchemaColor = ColorLib.color(Color.orange);
			final int dataSourceColor = ColorLib.color(new Color((float)0.8,(float)0.8,(float)1.0));
			
			// Determine which fill color the node should be
			Object object = ((NodeItem)item).get("NodeObject");
			if(object instanceof Schema)
			{
				Integer schemaID = ((Schema)object).getId();
				if(!pane.inSelectedTags(schemaID)) return unavailableTagColor;
				else if(schemaID.equals(pane.getSchemaID())) return selectedSchemaColor;
				else if(schemaID.equals(pane.getComparisonSchemaID())) return comparisonSchemaColor;
				return schemaColor;
			}
			else return dataSourceColor;
		}
	}
	
	/** Private class for handling node text colors */
	private class NodeTextColorAction extends ColorAction
	{
		/** Constructs the node text color action */
		private NodeTextColorAction() { super("graph.nodes",VisualItem.TEXTCOLOR,0); }
		
		/** Returns the color associated with the specified node */
		public int getColor(VisualItem item)
		{
			// Define various node text colors
			final int schemaColor = ColorLib.gray(100);
			final int dataSourceColor = ColorLib.color(Color.blue);
			
			// Determine which color the node text should be
			Object object = ((NodeItem)item).get("NodeObject");
			return object instanceof Schema ? schemaColor : dataSourceColor;
		}
	}
	
	/** Returns the colors used with the extension graph */
	ActionList getColors()
	{
		ActionList colorList = new ActionList();
		colorList.add(new NodeFillColorAction());
		colorList.add(new NodeTextColorAction());
		colorList.add(new ColorAction("graph.nodes",VisualItem.STROKECOLOR, ColorLib.gray(100)));
		colorList.add(new ColorAction("graph.edges",VisualItem.STROKECOLOR, ColorLib.gray(100)));
		colorList.add(new ColorAction("graph.edges",VisualItem.FILLCOLOR, ColorLib.gray(100)));
		return colorList;
	}
}
