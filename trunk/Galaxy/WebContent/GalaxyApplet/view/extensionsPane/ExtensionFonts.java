// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.extensionsPane;

import java.awt.Font;

import org.mitre.schemastore.model.Schema;

import prefuse.action.ActionList;
import prefuse.action.assignment.FontAction;
import prefuse.util.FontLib;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

/** Class for displaying the fonts associated with the extensions graph */
public class ExtensionFonts
{
	/** Private class for handling node fonts */
	static private class NodeFontAction extends FontAction
	{
		/** Constructs the node font action */
		private NodeFontAction() { super("graph.nodes",null); }
		
		/** Returns the font associated with the specified node */
		public Font getFont(VisualItem item)
		{
			// Define various node fonts
			final Font schemaFont = FontLib.getFont("SansSerif",Font.BOLD,12);
			final Font dataSourceFont = FontLib.getFont("SansSerif",Font.PLAIN,10);
			
			// Determine which color the node should be
			Object object = ((NodeItem)item).get("NodeObject");
			if(object instanceof Schema) return schemaFont;
			else return dataSourceFont;
		}
	}
	
	/** Returns the fonts used with the extension graph */
	static ActionList getFonts()
	{
		ActionList fontList = new ActionList();
		fontList.add(new NodeFontAction());
		return fontList;
	}
}
