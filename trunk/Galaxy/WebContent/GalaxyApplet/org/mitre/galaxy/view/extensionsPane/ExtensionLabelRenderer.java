// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.extensionsPane;

import java.awt.BasicStroke;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import org.mitre.galaxy.model.server.ImageManager;
import org.mitre.schemastore.model.DataSource;
import org.mitre.schemastore.model.Schema;

import prefuse.Constants;
import prefuse.render.LabelRenderer;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

/** Class for implementing the extension label renderer */
public class ExtensionLabelRenderer extends LabelRenderer
{
	/** Stores reference to the extensions pane */
	private ExtensionsPane pane = null;
	
	/** Returns the shape used in the extension label */
	public Shape getShape(VisualItem item)
	{
		Rectangle2D.Double shape = (Rectangle2D.Double)super.getShape(item);
		Object object = ((NodeItem)item).get("NodeObject");
		if(object instanceof Schema)
			shape.setRect(shape.getMinX(), shape.getMinY(), shape.getWidth()+20, shape.getHeight());
		return shape;
	}

	/** Returns the text used in the extension label */
	protected String getText(VisualItem item)
	{
		Object object = ((NodeItem)item).get("NodeObject");
		if(object instanceof Schema) return ((Schema)object).getName();
		return ((DataSource)object).getName();
	}

	/** Returns the image used in the extension label */
	protected Image getImage(VisualItem item)
	{
		Object object = ((NodeItem)item).get("NodeObject");
		if(object instanceof Schema)
		{
			Integer schemaID = ((Schema)object).getId();
			String image = "Schema";
			if(!pane.inSelectedTags(schemaID)) image += "Unavailable";
			else if(schemaID.equals(pane.getSchemaID())) image += "Selected";
			else if(schemaID.equals(pane.getComparisonSchemaID())) image += "Comparison";
			return ImageManager.getImage(image);
		}
		return null;
	}
	
	/** Returns the stroke used in the extension label border */
	protected BasicStroke getStroke(VisualItem item)
	{
		Object object = ((NodeItem)item).get("NodeObject");
		if(object instanceof Schema && !((Schema)object).getLocked())
			return new BasicStroke(1,0,0,1,new float[] {4,4},0); 
		return super.getStroke(item);
	}
	
	/** Constructs the extension label renderer */
	ExtensionLabelRenderer(ExtensionsPane pane)
	{
		super("NodeObject");
		this.pane = pane;
		setVerticalImageAlignment(Constants.CENTER);
		setHorizontalPadding(3);
		setVerticalPadding(3);
	}
}