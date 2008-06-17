// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.extensionsPane;

import java.awt.BasicStroke;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import model.DataSources;
import model.Schemas;
import model.SelectedObjects;
import model.UniversalObjects;
import model.server.ImageManager;

import prefuse.Constants;
import prefuse.render.LabelRenderer;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

/** Class for implementing the extension label renderer */
public class ExtensionLabelRenderer extends LabelRenderer
{
	/** Returns the shape used in the extension label */
	public Shape getShape(VisualItem item)
	{
		Rectangle2D.Double shape = (Rectangle2D.Double)super.getShape(item);
		Integer nodeID = (Integer)((NodeItem)item).get("NodeObject");
		if(UniversalObjects.isSchema(nodeID))
			shape.setRect(shape.getMinX(), shape.getMinY(), shape.getWidth()+20, shape.getHeight());
		return shape;
	}

	/** Returns the text used in the extension label */
	protected String getText(VisualItem item)
	{
		Integer nodeID = (Integer)((NodeItem)item).get("NodeObject");
		if(UniversalObjects.isSchema(nodeID)) return Schemas.getSchema(nodeID).getName();
		return DataSources.getDataSource(nodeID).getName();
	}

	/** Returns the image used in the extension label */
	protected Image getImage(VisualItem item)
	{
		Integer nodeID = (Integer)((NodeItem)item).get("NodeObject");
		if(UniversalObjects.isSchema(nodeID))
		{
			String image = "Schema";
			if(!SelectedObjects.inSelectedGroups(nodeID)) image += "Unavailable";
			else if(nodeID.equals(SelectedObjects.getSelectedSchema())) image += "Selected";
			else if(nodeID.equals(SelectedObjects.getSelectedComparisonSchema())) image += "Comparison";
			return ImageManager.getImage(image);
		}
		return null;
	}
	
	/** Returns the stroke used in the extension label border */
	protected BasicStroke getStroke(VisualItem item)
	{
		Integer nodeID = (Integer)((NodeItem)item).get("NodeObject");
		if(UniversalObjects.isSchema(nodeID) && !Schemas.getSchema(nodeID).getLocked())
			return new BasicStroke(1,0,0,1,new float[] {4,4},0); 
		return super.getStroke(item);
	}
	
	/** Constructs the extension label renderer */
	ExtensionLabelRenderer()
	{
		super("NodeObject");
		setVerticalImageAlignment(Constants.CENTER);
		setHorizontalPadding(3);
		setVerticalPadding(3);
	}
}
