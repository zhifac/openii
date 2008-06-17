// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.extensionsPane;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import model.Schemas;
import model.UniversalObjects;

import prefuse.Constants;
import prefuse.data.Node;
import prefuse.render.EdgeRenderer;
import prefuse.visual.EdgeItem;
import prefuse.visual.VisualItem;

/** Class for handling extension edge rendering */
public class ExtensionEdgeRenderer extends EdgeRenderer
{
	@Override
	public void render(Graphics2D g, VisualItem item)
	{
		// Draw the edge
		super.render(g, item);
		
		// Show similarity percentage if schema extension edge
		Integer targetID = (Integer)((Node)((EdgeItem)item).getTargetItem()).get("NodeObject");
		if(UniversalObjects.isSchema(targetID))
		{
			// Calculate out the percentage of shared items
			int percentage = 0;
			Integer sourceID = (Integer)((Node)((EdgeItem)item).getSourceItem()).get("NodeObject");
			try { percentage = (int)(100*Schemas.getSchemaElementCount(sourceID)/Schemas.getSchemaElementCount(targetID)); } catch(Exception e) {}
			String text = Integer.toString(percentage)+"%";
			
			// Find the point centered between the source and target nodes
			Rectangle2D source = ((EdgeItem)item).getSourceItem().getBounds();
			Rectangle2D target = ((EdgeItem)item).getTargetItem().getBounds();
			Point2D center = new Point2D.Double((source.getCenterX()+target.getCenterX())/2,(source.getCenterY()+target.getCenterY())/2);
			
			// Display the similarity percentage
			Rectangle2D bounds = g.getFontMetrics().getStringBounds(text,g);
			g.setColor(new Color((float)1.0,(float)1.0,(float)0.85));
			g.fillRect((int)(center.getX()-bounds.getWidth()/2),(int)(center.getY()-bounds.getHeight()/2),(int)bounds.getWidth(),(int)bounds.getHeight());
			g.setColor(Color.black);
			g.drawString(text,(int)(center.getX()-bounds.getWidth()/2),(int)(center.getY()+bounds.getHeight()/2-3));
		}
	}

	/** Extension edge renderer constructor */
	ExtensionEdgeRenderer()
		{ super(Constants.EDGE_TYPE_LINE); }
	
	/** Overrides the raw shape function to only have schema to schema edges have arrows */
	protected Shape getRawShape(VisualItem item)
	{
		Integer nodeID = (Integer)((Node)((EdgeItem)item).getTargetItem()).get("NodeObject");
		m_edgeArrow = UniversalObjects.isSchema(nodeID) ? Constants.EDGE_ARROW_FORWARD : Constants.EDGE_ARROW_NONE;
		return super.getRawShape(item);
	}
}
