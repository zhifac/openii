// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.schemaPane;

import java.awt.Color;

import model.Schemas;
import model.SelectedObjects;
import model.search.SearchManager;

import prefuse.action.ActionList;
import prefuse.action.assignment.ColorAction;
import prefuse.util.ColorLib;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

/** Class for displaying the colors associated with the extensions graph */
public class SchemaColors
{
	/** Private class for handling node fill colors */
	static private class NodeFillColorAction extends ColorAction
	{
		/** Constructs the node fill color action */
		private NodeFillColorAction() { super("graph.nodes",VisualItem.FILLCOLOR,0); }
		
		/** Returns the fill color associated with the specified node */
		public int getColor(VisualItem item)
		{
			// Define various color to use
			final int entityColor = ColorLib.color(new Color((float)0.92,(float)0.92,(float)1.0));
			final int attributeColor = ColorLib.color(new Color((float)0.96,(float)0.96,(float)1.0));
			final int selectedSchemaOnlyColor = ColorLib.color(Color.yellow);
			final int comparisonSchemaOnlyColor = ColorLib.color(Color.orange);
			final int defaultColor = ColorLib.color(Color.white);
			
			// Sets the fill color for the node
			Object object = ((NodeItem)item).get("SchemaObject");
			if(object instanceof SchemaTreeObject)
			{
				SchemaTreeObject schemaObject = (SchemaTreeObject)object;
				if(SelectedObjects.getSelectedComparisonSchema()!=null)
				{
					// Highlights the items only existent in the selected schema
					if(!schemaObject.inComparedGraph())
						return selectedSchemaOnlyColor;
					
					// Highlight the items only existent in the compared schema
					if(!schemaObject.inSelectedGraph())
						return comparisonSchemaOnlyColor;
				}
				return schemaObject.getType()==SchemaTreeObject.ATTRIBUTE ? attributeColor : entityColor;
			}
			return defaultColor;
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
			final int schemaColor = ColorLib.color(Color.black);
			return schemaColor;
		}
	}
	
	/** Private class for handling node stroke colors */
	static private class NodeStrokeColorAction extends ColorAction
	{
		/** Constructs the node text color action */
		private NodeStrokeColorAction() { super("graph.nodes",VisualItem.STROKECOLOR,0); }
		
		/** Returns the color associated with the specified node */
		public int getColor(VisualItem item)
		{
			// Define various color to use
			final int selectedColor = ColorLib.color(Color.red);
			final int entityColor = ColorLib.color(new Color((float)0.0,(float)0.0,(float)0.8));
			final int attributeColor = ColorLib.color(new Color((float)0.3,(float)0.3,(float)1.0));
			final int defaultColor = ColorLib.color(Color.black);
			
			// Draw a colored border around the node
			Object object = ((NodeItem)item).get("SchemaObject");
			if(object instanceof SchemaTreeObject)
			{
				SchemaTreeObject sObject = (SchemaTreeObject)object;
				for(Integer id : sObject.getIDs())
					if(SearchManager.containsObject(Schemas.getSchemaElement(id))) return selectedColor;
				if(sObject.getType()==SchemaTreeObject.ENTITY) return entityColor;
				if(sObject.getType()==SchemaTreeObject.ATTRIBUTE) return attributeColor;
			}
			return defaultColor;
		}
	}
	
	/** Returns the colors used with the extension graph */
	static ActionList getColors()
	{
		ActionList colorList = new ActionList();
		colorList.add(new NodeFillColorAction());
		colorList.add(new NodeTextColorAction());
		colorList.add(new NodeStrokeColorAction());
		colorList.add(new ColorAction("graph.edges",VisualItem.STROKECOLOR, ColorLib.gray(100)));
		colorList.add(new ColorAction("graph.edges",VisualItem.FILLCOLOR, ColorLib.gray(100)));
		return colorList;
	}
}