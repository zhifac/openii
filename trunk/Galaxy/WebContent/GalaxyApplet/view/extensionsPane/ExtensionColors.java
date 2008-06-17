// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.extensionsPane;

import java.awt.Color;

import model.SelectedObjects;
import model.UniversalObjects;
import prefuse.action.ActionList;
import prefuse.action.assignment.ColorAction;
import prefuse.util.ColorLib;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

/** Class for displaying the colors associated with the extensions graph */
public class ExtensionColors
{
	/** Private class for handling node fill colors */
	static private class NodeFillColorAction extends ColorAction
	{
		/** Constructs the node fill color action */
		private NodeFillColorAction() { super("graph.nodes",VisualItem.FILLCOLOR,0); }
		
		/** Returns the fill color associated with the specified node */
		public int getColor(VisualItem item)
		{
			// Define various node fill colors
			final int schemaColor = ColorLib.color(Color.white);
			final int unavailableGroupColor = ColorLib.color(Color.lightGray);
			final int selectedSchemaColor = ColorLib.color(Color.yellow);
			final int comparisonSchemaColor = ColorLib.color(Color.orange);
			final int dataSourceColor = ColorLib.color(new Color((float)0.8,(float)0.8,(float)1.0));
			
			// Determine which fill color the node should be
			Integer nodeID = (Integer)((NodeItem)item).get("NodeObject");
			if(UniversalObjects.isSchema(nodeID))
			{
				if(!SelectedObjects.inSelectedGroups(nodeID)) return unavailableGroupColor;
				else if(nodeID.equals(SelectedObjects.getSelectedSchema())) return selectedSchemaColor;
				else if(nodeID.equals(SelectedObjects.getSelectedComparisonSchema())) return comparisonSchemaColor;
				return schemaColor;
			}
			else return dataSourceColor;
		}
	}
	
	/** Private class for handling node text colors */
	static private class NodeTextColorAction extends ColorAction
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
			Integer nodeID = (Integer)((NodeItem)item).get("NodeObject");
			return UniversalObjects.isSchema(nodeID) ? schemaColor : dataSourceColor;
		}
	}
	
	/** Returns the colors used with the extension graph */
	static ActionList getColors()
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
