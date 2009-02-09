// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.extensionsPane;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import prefuse.Display;
import prefuse.Visualization;

/** Class for managing the extension display */
public class ExtensionPaneDisplay extends Display
{
	/** Tracks if the display has been centered */
	public boolean isCentered = false;
	
	/** Schema display constructor */
	ExtensionPaneDisplay(Visualization vis)
		{ super(vis); }
	
	/** Centered the display when the graphics are first prepared */
	public void paint(Graphics g)
	{
		if(!isCentered)
		{
			// Determine where the center of the display is situated
			isCentered = true;
			Rectangle2D graphBounds = m_vis.getBounds("graph");
			Point centerPoint = new Point((int)(graphBounds.getWidth()/2+graphBounds.getMinX()),(int)(graphBounds.getHeight()/2+graphBounds.getMinY()));
			
			// Center and zoom the display to fit within the navigator pane dimensions
			panToAbs(centerPoint);
			double xScale = getBounds().getWidth()/((graphBounds.getWidth()+40)*getScale());
			double yScale = getBounds().getHeight()/((graphBounds.getHeight()+40)*getScale());
			zoomAbs(centerPoint,xScale<yScale?xScale:yScale);
			
			// Force display to be recentered in certain circumstances
			if(centerPoint.getY()==0.0) { isCentered=false; repaint(); }
		}
		super.paint(g);
	}
}
