// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.galaxy.view.schemaPane.navigatorPane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

import org.mitre.galaxy.view.schemaPane.SchemaPane;

import prefuse.Display;
import prefuse.controls.Control;
import prefuse.visual.VisualItem;

/** Class for managing the navigator display */
public class NavigatorDisplay extends Display implements Control
{				
	/** Holds a reference to the schema pane */
	private SchemaPane schemaPane;
	
	/** Tracks if the display has been centered */
	private boolean reset = false;
	
	/** Stores the bounding box around the schema */
	private Rectangle2D boundingBox = null;
	
	/** Stores the x offset between the mouse and the center of the bounding box */
	private int xOffset = 0;
	
	/** Stores the y offset between the mouse and the center of the bounding box */
	private int yOffset = 0;
	
	/** Stores the x and y buffers around the centered display */
	private double xBuffer;
	private double yBuffer;
	
	/** Generates the bounding box for the schema */
	private Rectangle2D getBoundingBox()
	{
		Display schemaDisplay = schemaPane.display;
		Rectangle2D bounds = schemaPane.vis.getBounds("graph");
		double xMin = (schemaDisplay.getDisplayX()/schemaDisplay.getScale()-bounds.getMinX())*getScale()+xBuffer;
		double yMin = (schemaDisplay.getDisplayY()/schemaDisplay.getScale()-bounds.getMinY())*getScale()+yBuffer;
		double width = schemaDisplay.getWidth()*getScale()/schemaDisplay.getScale();
		double height = schemaDisplay.getHeight()*getScale()/schemaDisplay.getScale();
		return new Rectangle2D.Double(xMin,yMin,width,height);
	}
	
	/** Center the schema at the specified point */
	private void centerSchemaAt(Point point)
	{
		Rectangle2D graphBounds = m_vis.getBounds("graph");
		int xPoint = (int)(graphBounds.getMinX()+graphBounds.getWidth()*((point.x-xBuffer)/(getWidth()-2*xBuffer)));
		int yPoint = (int)(graphBounds.getMinY()+graphBounds.getHeight()*((point.y-yBuffer)/(getHeight()-2*yBuffer)));
		schemaPane.display.panToAbs(new Point(xPoint,yPoint));
		schemaPane.display.repaint();
	}
	
	/** Navigator display constructor */
	NavigatorDisplay(SchemaPane schemaPane)
	{
		super(schemaPane.vis);
		this.schemaPane=schemaPane;
		schemaPane.display.addControlListener(this);
		this.addControlListener(this);
	}

	/** Centered the display when the graphics are first prepared */
	public void paint(Graphics g)
	{
		// Center the display as needed
		if(reset)
		{
			// Determine where the center of the display is situated
			reset = false;
			Rectangle2D graphBounds = m_vis.getBounds("graph");
			Point centerPoint = new Point((int)(graphBounds.getWidth()/2+graphBounds.getMinX()),(int)(graphBounds.getHeight()/2+graphBounds.getMinY()));
			
			// Center and zoom the display to fit within the navigator pane dimensions
			panToAbs(centerPoint);
			double xScale = getBounds().getWidth()/((graphBounds.getWidth()+20)*getScale());
			double yScale = getBounds().getHeight()/((graphBounds.getHeight()+20)*getScale());
			zoomAbs(centerPoint,xScale<yScale?xScale:yScale);
			xBuffer = (getBounds().getWidth() - graphBounds.getWidth()*getScale())/2;
			yBuffer = (getBounds().getHeight() - graphBounds.getHeight()*getScale())/2;
		}
		
		// Paint the graph display
		super.paint(g);
		
		// Draw a bounding box around the display
		g.setColor(Color.red);
		boundingBox = getBoundingBox();
		g.drawRect((int)boundingBox.getMinX(),(int)boundingBox.getY(),(int)boundingBox.getWidth(),(int)boundingBox.getHeight());
	}

	/** Resets the navigator display */
	public void reset()
		{ reset = true; }
	
	/** Indicates that the display needs to be recentered when an item is moved */
	public void itemDragged(VisualItem item, MouseEvent e)
		{ if(e.getSource()==this) mouseDragged(e); else reset = true; }

	/** Center the schema on the spot that was pressed by the mouse */
	public void mousePressed(MouseEvent e)
	{
		// Calculate the offset of the mouse from the center of the bounding box
		Point point = e.getPoint();
		if(boundingBox.contains(point))
			{ xOffset = (int)(boundingBox.getCenterX()-point.x); yOffset = (int)(boundingBox.getCenterY()-point.y); }
		else { xOffset = 0; yOffset = 0; }
		
		// Center the schema based on where the mouse is located
		point.translate(xOffset, yOffset);
		if(e.getSource()==this) centerSchemaAt(point);
	}

	/** Continually center the schema on the spot where the mouse is being dragged */
	public void mouseDragged(MouseEvent e)
	{
		if(e.getSource()==this)	
		{
			Point point = e.getPoint();
			point.translate(xOffset, yOffset);
			if(point.x>=0 && point.x<=getWidth() && point.y>=0 && point.y<=getHeight())
				centerSchemaAt(point);
		}
	}
	
	// Unused listener events
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void itemClicked(VisualItem item, MouseEvent e) {}
	public void itemEntered(VisualItem item, MouseEvent e) {}
	public void itemExited(VisualItem item, MouseEvent e) {}
	public void itemKeyPressed(VisualItem item, KeyEvent e) {}
	public void itemKeyReleased(VisualItem item, KeyEvent e) {}
	public void itemKeyTyped(VisualItem item, KeyEvent e) {}
	public void itemMoved(VisualItem item, MouseEvent e) {}
	public void itemPressed(VisualItem item, MouseEvent e) { mousePressed(e); }
	public void itemReleased(VisualItem item, MouseEvent e) {}
	public void itemWheelMoved(VisualItem item, MouseWheelEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseWheelMoved(MouseWheelEvent e) {}
	public void setEnabled(boolean enabled) {}
}
