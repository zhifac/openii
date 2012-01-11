/*
 *  Copyright 2010 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.affinity.view.swt;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.mitre.affinity.view.swt.event.ZoomPanListener;

/**
 * A canvas that can be zoomed and panned.  Pan by clicking left mouse button and dragging mouse.  Zoom
 * with scroll wheel or with +/- keys.
 * 
 * @author CBONACETO
 *
 */
public abstract class ZoomPanCanvas extends Canvas implements IRenderer, MouseListener, MouseMoveListener, MouseWheelListener, KeyListener {
	/** Current zoom level */
	protected float zoom;
	
	/** Controls how fast zooming occurs */
	protected float zoomFactor = 0.25f;
	
	/** Whether or not fonts should be scaled when zooming */
	protected boolean zoomFont = false;
	
	/** The ratio of the size of the overview area to the detail area */
	protected float overviewToDetailRatio = 0.15f;
	
	/** Current pan x/y offsets */
	private float dx, dy;
	
	//Used in panning to store the last position of the mouse
	private int lastX, lastY;
	
	//The x/y offset to re-center after zooming
	private float zoomDx, zoomDy;	
	
	/** Whether or not the canvas will pan when the user drags the mouse */
	private boolean panningEnabled = true;
	
	//Whether or not the user is currently panning in the detail or in the overview
	protected boolean panning = false;	
	protected boolean panningOverview = false;
	
	protected boolean mouseMoved = false;
	
	//Cursor to use when panning
	protected Cursor panCursor; //= Display.getDefault().getSystemCursor(SWT.CURSOR_SIZEALL);
	
	//The normal cursor
	protected Cursor normalCursor;// = Display.getDefault().getSystemCursor(SWT.CURSOR_ARROW);
	
	//protected Image bufferedImage;
	
	/** Whether or not the overview is shown */
	private boolean showOverview = true;
	
	/** Whether or not the overview is currently being rendered */
	protected boolean renderingOverview = false;
	
	/** The x, y, width, and height of the overview */
	private Rectangle overviewBounds = new Rectangle(8, 8, 100, 100);	
	
	private float overviewScaleFactor;
	
	private Image overviewImage;
	
	protected Composite parent;
	
	protected Transform zoomPanTransform;
	
	/** List of listeners that have registered to be notified of zoom/pan events */
	private List<ZoomPanListener> zoomPanListeners;
	
	public ZoomPanCanvas(Composite parent, int style) {
		super(parent, style);
		
		this.panCursor = parent.getDisplay().getSystemCursor(SWT.CURSOR_SIZEALL);
		this.normalCursor =  parent.getDisplay().getSystemCursor(SWT.CURSOR_ARROW);		
		
		this.parent = parent;
		this.dx = 0;
		this.dy = 0;
		this.zoom = 1;
		
		this.addMouseListener(this);
		this.addMouseMoveListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		
		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {				
				renderZoomedPannedImage(e.gc, true);
			}
		});
	}	
	
	protected void renderZoomedPannedImage(GC gc, boolean redrawCanvas) {	
		//TODO: Used the buffered image when panning to improve performance
		/*
		Rectangle bounds = this.getBounds();
		if(bufferedImage == null) {
			bufferedImage = new Image(gc.getDevice(), bounds.width, bounds.height);
		}
		else if(bufferedImage.getBounds().width != bounds.width ||
				bufferedImage.getBounds().height != bounds.height) {
			bufferedImage.dispose();
			bufferedImage = new Image(gc.getDevice(), bounds.width, bounds.height);
		}
		
		if(redrawCanvas) {
			GC bufferedImageGC = new GC(bufferedImage);
			this.render(bufferedImageGC, parent);
			bufferedImageGC.dispose();
		}*/
		
		//Render the background
		Rectangle bounds = getBounds();
		//gc.setBackground(getBackground());
		//gc.fillRectangle(bounds);
		drawBackground(gc, bounds.x, bounds.y, bounds.width, bounds.height);
		
		//Render with zoom/pan transformation
		if(zoomPanTransform != null) {
			zoomPanTransform.dispose();
		}
		zoomPanTransform = createZoomPanTransform();		
		gc.setTransform(zoomPanTransform);				
		/*
		Font font = null;
		if(!this.zoomFont) {			
			FontData fontData = this.getFont().getFontData()[0];
			int newFontHeight = (int)(fontData.getHeight() * 1.f/zoom);
			System.out.println("new font height: " + newFontHeight);
			if(newFontHeight < 1)
				newFontHeight = 1;
			font = new Font(getDisplay(), fontData.getName(), newFontHeight, fontData.getStyle());
			gc.setFont(font);
		}
		*/
		renderingOverview = false;
		render(gc, parent);
		//gc.drawImage(bufferedImage, 0, 0);
		
		//Render the overview
		if(showOverview) {			
			gc.setTransform(null);			
			//We always want to scale fonts when drawing the overview
			//boolean origZoomFont = this.zoomFont;
			//zoomFont = true;			
			renderOverview(gc);			
			//zoomFont = origZoomFont;			
		}
		
		//if(font != null) 
		//	font.dispose();
		//zoomPanTransform.dispose();
	}
	
	/**
	 * Draws the overview image
	 * 
	 * @param gc
	 */
	protected void renderOverview(GC gc) {
		renderingOverview = true;
		Point canvasSize = this.getSize();
		
		int targetOverviewWidth = (int)(canvasSize.x * this.overviewToDetailRatio);
		if(targetOverviewWidth != overviewBounds.width) {
			//Rescale the overview width to match the overviewToDetailRatio
			overviewBounds.width = targetOverviewWidth;
		}	
		if(overviewBounds.width < 30)
			overviewBounds.width = 30;
		float overviewAspectRatio = (float)this.overviewBounds.height/this.overviewBounds.width;
		float canvasAspectRatio = (float)canvasSize.y/canvasSize.x;
		if(overviewAspectRatio != canvasAspectRatio) {
			//Rescale the overview height to match the detail aspect ratio
			overviewBounds.height = (int)(canvasAspectRatio * overviewBounds.width);
		}

		//Create a new overviewImage if necessary
		if(this.overviewImage == null || overviewImage.isDisposed()) {
			this.overviewImage = new Image(getDisplay(), overviewBounds.width, overviewBounds.height);
		} 
		else if(overviewImage.getBounds().width != overviewBounds.width ||
				overviewImage.getBounds().height != overviewBounds.height) {
			this.overviewImage.dispose();
			this.overviewImage = new Image(getDisplay(), overviewBounds.width, overviewBounds.height);
		}
		
		GC overviewGC = new GC(overviewImage);	
		overviewGC.setBackground(getBackground());
		overviewGC.fillRectangle(0, 0, overviewBounds.width, overviewBounds.height);
		
		//Draw overview
		Transform transform = new Transform(getDisplay());
		this.overviewScaleFactor = (float)getSize().x/overviewBounds.width;
		//float scaleFactor = (float)overviewBounds.width/canvasSize.x;
		//transform.scale(1.f/overviewScaleFactor, 1.f/overviewScaleFactor);
		if(zoom < 1) {
			transform.scale(1.f/overviewScaleFactor, 1.f/overviewScaleFactor);
			//transform.scale(1.f/overviewScaleFactor * zoom, 1.f/overviewScaleFactor * zoom);
			//transform.translate((int)(getDx() * 1.f/overviewScaleFactor * zoom),
			//		(int)(getDy() * 1.f/overviewScaleFactor * zoom));
		}
		else {
			transform.scale(1.f/overviewScaleFactor, 1.f/overviewScaleFactor);
		}
		overviewGC.setAntialias(SWT.ON);
		overviewGC.setTransform(transform);		
		this.render(overviewGC, parent);
		
		//Draw rectangle showing current detail location in the overview
		//if(zoom > 1) { 
		overviewGC.setLineWidth(1);
		overviewGC.setForeground(getDisplay().getSystemColor(SWT.COLOR_RED));
		//System.out.println("translated (0,0): " + this.getTranslatedPoint(0, 0));
		//System.out.println("scaled width/height: " + canvasSize.x * (1.f/zoom) + "/" + canvasSize.y * (1.f/zoom));
		Point currentOrigin = this.canvasPointToTranslatedPoint(0, 0);	
		overviewGC.drawRectangle(currentOrigin.x, currentOrigin.y, 
				(int)(canvasSize.x * (1.f/zoom)), (int)(canvasSize.y * (1.f/zoom)));
		//}
		
		//Draw border around overview
		overviewGC.setTransform(null);
		overviewGC.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));	
		overviewGC.drawRectangle(0, 0, overviewBounds.width - 1, overviewBounds.height - 1);		
		
		gc.drawImage(overviewImage, overviewBounds.x, overviewBounds.y);	
		overviewGC.dispose();
		transform.dispose();
		renderingOverview = false;
	}
	
	/** Create pan transform */
	protected Transform createPanTransform() {
		Transform transform = new Transform(this.getDisplay());
		if(zoom == 1) {
			//For some reason, things aren't redrawn when xPos or yPos is -1 and zoom is 1, 
			//so we nudge them a bit
			if(dx == -1)
				dx = -2;
			if(dy == -1)
				dy = -2;
		}	
		//return new Point((int)(x * zoom + getDx()),(int)(y * zoom + getDy()));		
		transform.translate(getDx(), getDy());		
		return transform;
	}
	
	/** Create zoom transform */
	protected Transform createZoomTransform() {
		Transform transform = new Transform(this.getDisplay());
		if(zoom == 1) {
			//For some reason, things aren't redrawn when xPos or yPos is -1 and zoom is 1, 
			//so we nudge them a bit
			if(dx == -1)
				dx = -2;
			if(dy == -1)
				dy = -2;
		}		
		transform.scale(zoom, zoom);
		return transform;
	}
	
	/** Create pan and zoom transform */
	protected Transform createZoomPanTransform() {		
		Transform transform = new Transform(this.getDisplay());
		if(zoom == 1) {
			//For some reason, things aren't redrawn when xPos or yPos is -1 and zoom is 1, 
			//so we nudge them a bit
			if(dx == -1)
				dx = -2;
			if(dy == -1)
				dy = -2;
		}		
		/*
		//dx = xPos;
		//dy = yPos;		
		//transform.translate(dx, dy);		
		//transform.scale(zoom, zoom);
		if(zoom > 1) {
			//Recenter after zooming
			Rectangle bounds = getBounds();
			float scaledWidth = bounds.width * zoom;
			float scaledHeight = bounds.height * zoom;
			dx += -scaledWidth/2.f + bounds.width/2.f;
			dy += -scaledHeight/2.f  + bounds.height/2.f;
		}
		*/				
		//System.out.println("dx = " + dx + ", dy = " + dy + ", zoom = " + zoom);
		transform.translate(dx + zoomDx, dy + zoomDy);		
		transform.scale(zoom, zoom);
		return transform;
	}
	
	public boolean isPanningEnabled() {
		return panningEnabled;
	}

	public void setPanningEnabled(boolean panningEnabled) {
		this.panningEnabled = panningEnabled;
	}

	/**
	 * Resets zoom/pan settings to un-zoomed and un-panned.
	 */
	public void reset() {
		this.zoom = 1.f;
		this.dx = 0;
		this.dy = 0;
		this.zoomDx = 0;
		this.zoomDy = 0;
	}
	
	public void addZoomPanListener(ZoomPanListener listener) {
		if(zoomPanListeners == null)
			zoomPanListeners = new LinkedList<ZoomPanListener>();
		zoomPanListeners.add(listener);
	}
	
	public void removeZoomPanListener(ZoomPanListener listener) {
		zoomPanListeners.remove(listener);
	}
	
	public float getZoomFactor() {
		return zoomFactor;
	}

	public void setZoomFactor(float zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public boolean isZoomFont() {
		return zoomFont;
	}

	public void setZoomFont(boolean zoomFont) {
		this.zoomFont = zoomFont;
	}

	public float getZoom() {
		return this.zoom; 
	}
	
	public void setZoom(float zoom) {
		if(zoom > 0) {
			this.zoom = zoom;
		}
		recenterAfterZoom();	
	}
	
	public void zoomIn(int numLevels) {
		zoom += numLevels * zoomFactor;		
		recenterAfterZoom();	
	}
	
	public void zoomOut(int numLevels) {
		float zoomAmount = numLevels * zoomFactor;
		if(zoom - zoomAmount > 0)
			zoom -= zoomAmount;
		recenterAfterZoom();		
	}
	
	private void recenterAfterZoom() {
		//Re-center after zooming
		if(zoom == 1) {
			zoomDx = 0;
			zoomDy = 0;
		}
		else {
			Rectangle bounds = getBounds();			
			zoomDx = -(bounds.width * zoom)/2.f + bounds.width/2.f;
			zoomDy = -(bounds.height * zoom)/2.f  + bounds.height/2.f;
		}
	}
	
	public float getOverviewToDetailRatio() {
		return overviewToDetailRatio;
	}

	public void setOverviewToDetailRatio(float overviewToDetailRatio) {
		this.overviewToDetailRatio = overviewToDetailRatio;
	}

	public Point getOrigin() {
		return new Point((int)getDx(), (int)getDy());
	}
	
	public boolean isShowOverview() {
		return showOverview;
	}

	public void setShowOverview(boolean showOverview) {
		this.showOverview = showOverview;
	}

	public void setOverviewLocation(int x, int y) {
		this.overviewBounds.x = x;
		this.overviewBounds.y = y;
	}
	
	public void setOverviewWidth(int width) {
		this.overviewBounds.width = width;
	}
	
	/**
	 * Converts a point on the canvas to a translated point given
	 * the current zoom/pan settings.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Point canvasPointToTranslatedPoint(int x, int y) {
		//System.out.println("dx = " + dx + ", dy = " + dy + ", xPos = " + xPos + ", yPos = " + yPos);		
		return new Point((int)((x - getDx())*(1.f/zoom)),(int)((y - getDy())*(1.f/zoom)));		
	}
	
	/**
	 * Converts a point's original location on the canvas to a translated point given
	 * the current zoom/pan settings.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Point originalPointToTranslatedPoint(int x, int y) {
		return new Point((int)(x * zoom + getDx()),(int)(y * zoom + getDy()));		
	}
	
	/**
	 * Converts a bound's original location, width, and height on the canvas to a translated location,
	 * width, and height given the current zoom/pan settings.
	 * 
	 * @param bounds
	 * @return
	 */
	public Rectangle originalBoundsToTranslatedBounds(Rectangle bounds) {
		Point translatedPoint = originalPointToTranslatedPoint(bounds.x, bounds.y);
		Rectangle translatedBounds = new Rectangle(translatedPoint.x, translatedPoint.y,
				(int)(bounds.width * zoom), (int)(bounds.height * zoom));
		return translatedBounds;
	}
	
	/**
	 * Converts a point in the overview area to it's corresponding point in the detail area.
	 * 
	 * @param x
	 * @param y
	 * @return	 */
	public Point overviewPointToDetailPoint(int x, int y) {
		return new Point((int)((x - overviewBounds.x) * overviewScaleFactor * zoom),
				(int)(( y - overviewBounds.y) * overviewScaleFactor * zoom));
	}
	
	private float getDx() {
		return dx + zoomDx;
	}
	
	private float getDy() {
		return dy + zoomDy;
	}
	
	//Mouselistener methods
	
	public void mouseDoubleClick(MouseEvent e) {}

	public void mouseDown(MouseEvent e) {	
		//Pan if the left mouse button is clicked
		mouseMoved = false;
		if(e.button == 1 && e.count == 1) {
			lastX = e.x;
			lastY = e.y;
			
			if(showOverview && overviewBounds.contains(e.x, e.y)) {
				//If mouse was clicked in the overview, pan in the overview
				panning = true;
				panningOverview = true;
				
				//We immediately change the cursor when panning in the overview
				setCursor(panCursor);
				mouseMoved = true;
				
				//Make the point moused to in the overview the new panning center-point
				Point translatedPoint = this.overviewPointToDetailPoint(e.x, e.y);
				dx = this.getSize().x/2 - translatedPoint.x - zoomDx;
				dy = this.getSize().y/2 - translatedPoint.y - zoomDy;	
				
				this.redraw();
			}
			else if(panningEnabled) {
				//Pan in the detail
				panning = true;		
				mouseMoved = false;				
			}
		}		
	}

	public void mouseUp(MouseEvent e) {	
		if(panning) {
			panning = false;
			panningOverview = false;
			//#mouseMoved = false;
			
			//return the cursor to normal		
			setCursor(normalCursor);
		}
	}
	
	public void mouseMove(MouseEvent e) {		
		if(panning) {	
			if(!mouseMoved) {
				setCursor(panCursor);
				mouseMoved = true;
			}
			
			if(panningOverview) {
				//Pan to the new mouse position in the overview
				dx += (lastX - e.x) * overviewScaleFactor * zoom;
				dy += (lastY - e.y) * overviewScaleFactor * zoom;								
			}
			else {
				//Pan to the new mouse position in the detail view 
				//(move the position by how much the mouse has moved since last time)
				dx -= lastX - e.x;
				dy -= lastY - e.y;				
			}
			
			//update lastX/Y for the next update
			lastX = e.x;
			lastY = e.y;
			
			this.redraw();
			//this.update();
		}
	}
	
	protected void fireZoomedEvent() {
		if(zoomPanListeners != null && !zoomPanListeners.isEmpty()) {
			for(ZoomPanListener listener: zoomPanListeners) {
				listener.widgetZoomed(zoom);
			}
		}
	}

	public void mouseScrolled(MouseEvent e) {				
		//Change zoom level if mouse wheel scrolled
		if(e.count > 0) 
			zoomIn(1);
		else
			zoomOut(1);
		
		this.redraw();
		
		//Notify listeners that the zoom level changed
		fireZoomedEvent();
	}

	//Keylistener methods
	
	public void keyPressed(KeyEvent e) {
		//Change zoom level if '+' or '-' key pressed	
		if(e.character == '+' || e.character == '=') {
			//zoom in
			zoomIn(1);
			this.redraw();
			
			//Notify listeners that the zoom level changed
			fireZoomedEvent();
		}
		else if(e.character == '-' || e.character == '_') {
			//zoom out
			zoomOut(1);
			this.redraw();
			
			//Notify listeners that the zoom level changed
			fireZoomedEvent();
		}
	}

	public void keyReleased(KeyEvent e) {}
}
