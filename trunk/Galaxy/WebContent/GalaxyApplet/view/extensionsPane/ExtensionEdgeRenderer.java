// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package view.extensionsPane;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.mitre.schemastore.model.Schema;

import model.Schemas;

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
		Object tObject = ((Node)((EdgeItem)item).getTargetItem()).get("NodeObject");
		if(tObject instanceof Schema)
		{
			Integer tSchemaID = ((Schema)tObject).getId();
			
			// Calculate out the percentage of shared items
			int percentage = 0;
			Object sObject = ((Node)((EdgeItem)item).getSourceItem()).get("NodeObject");
			try { percentage = (int)(100*Schemas.getSchemaElementCount(((Schema)sObject).getId())/Schemas.getSchemaElementCount(tSchemaID)); } catch(Exception e) {}
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
		Object object = ((Node)((EdgeItem)item).getTargetItem()).get("NodeObject");
		m_edgeArrow = object instanceof Schema ? Constants.EDGE_ARROW_FORWARD : Constants.EDGE_ARROW_NONE;
		return super.getRawShape(item);
	}
}
