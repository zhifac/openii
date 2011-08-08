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

package org.mitre.affinity.view.craigrogram;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;

/**
 * An implementation of a schema GUI that displays the schema as an oval.
 * 
 * @author CBONACETO
 *
 */
public class OvalSchemaGUI extends BasicSchemaGUI {
	private Point size;
	private Point labelSize;
	private Color labelColor = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	private ToolTip toolTip;	
	private boolean isMouseOver;
	
	private int selectedLineWidth = 1;
	//private Color selectedLineColor = labelColor;
	private Color selectedLineColor = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
	private Color selectedCircleColor = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	private Rectangle bounds;
	
	public OvalSchemaGUI(Shell parent, Integer schemaID) {
		this(parent, schemaID, 10, 10);
	}
	
	public OvalSchemaGUI(Shell parent, Integer schemaID, int width, int height) {
		super.setSchemaID(schemaID);
		this.size = new Point(width, height);
		this.bounds = new Rectangle(0, 0, width, height);
		this.toolTip = new ToolTip(parent, SWT.BALLOON);
		this.isMouseOver = false;
	}
	
	public void setSize(Point size) {
		this.size = size;
	}
	
	public void setSize(int width, int height) {
		if(this.size == null)
			this.size = new Point(width, height);
		else {
			this.size.x = width;
			this.size.y = height;
		}
		this.bounds.width = width;
		this.bounds.height = height;
	}
	
	@Override
	public void setLocation(int x, int y) {	
		super.setLocation(x, y);		
		this.bounds.x = x - size.x/2;
		this.bounds.y = y - size.y/2;
	}

	@Override
	public void setLocation(Point location) {		
		super.setLocation(location);
		this.bounds.x = location.x - size.x/2;
		this.bounds.y = location.y - size.y/2;
	}

	public Rectangle getBounds() {
		return this.bounds;
	}
	
	/* (non-Javadoc)
	 * @see org.mitre.affinity.view.swt.ISchemaGUI#containsPoint(int, int)
	 */
	public boolean containsPoint(int x, int y) {
		int startX = location.x - (size.x / 2); 
		int startY = location.y - (size.y / 2);

		if((x >= startX && x <= startX + size.x) && (y >= startY && y <= startY + size.y))						
			return true;
		return false;
	}
	
	public void setToolTipText(String text) {		
		this.toolTip.setText(text);
	}
	
	public Point getSize() {
		return this.size;
	}
	
	public String getToolTipText() {
		return this.toolTip.getText();
	}
	
	/**
	 * Adjusts the size of the oval to fit the label text with the specified horizontal and vertical
	 * spacing around the text.  If label text is not set the oval will be sized to
	 * be horizontal spacer wide by verticalSpacer high.  If makeCircle is true, the oval will always
	 * be made a circle (and the horizontal or vertical spacer may be ignored).
	 * 
	 * @param horizontalSpacer: spacing to left and right of label
	 * @param verticalSpacer: spacing to top and bottom of label
	 */
	public void resizeToFitText(int horizontalSpacer, int verticalSpacer, boolean makeCircle) {
		if(label != null && label.length() > 0) {
			GC gc = new GC(Display.getDefault());
			this.labelSize = gc.stringExtent(super.label);
			if(makeCircle) {
				if((this.labelSize.x + horizontalSpacer*2) > (this.labelSize.y + verticalSpacer*2)) {
					this.size.x = labelSize.x + horizontalSpacer*2;
					this.size.y = this.size.x;
				}
				else {
					this.size.y = (this.labelSize.y + verticalSpacer*2);
					this.size.x = this.size.y;
				}
			} else {
				this.size.x = this.labelSize.x + horizontalSpacer * 2;
				this.size.y = this.labelSize.y + verticalSpacer * 2;
			}
			gc.dispose();
		}
		else {
			this.size.x = horizontalSpacer;
			this.size.y = verticalSpacer;
		}
	}	
	
	@Override
	public void render(GC gc, Composite parent) {		
		// convert coordinate to absolute position
		//Point plotPoint = Utils.convertToAbsolutePosition(i.getLocation(), shell);
		
		//Draw the oval

			//System.out.println();
			//System.out.println("is selected " + selected);
			//System.out.println("isShowLabel() " + isShowLabel());
			//System.out.println("super.showLabel" + super.showLabel);
			
			
		gc.setBackground(super.background);							
		boolean labels = super.isShowLabel();
		if(!labels){
			//System.out.println("not showing labels");
			if(selected){
				gc.setBackground(selectedCircleColor);	
				//System.out.println("should be black");
			}else{
				//System.out.println("should be gray");
			}
		}else{
			//System.out.println("should be gray");
		}
	
		gc.setAntialias(SWT.ON);
		//gc.setForeground(super.foreground);
		gc.fillOval(location.x - size.x/2, location.y - size.y/2, size.x, size.y);		
		gc.setForeground(super.foreground);

		if(!labels){
			//System.out.println("not showing labels");
			if(selected){
				gc.setForeground(this.selectedCircleColor);
				gc.setLineWidth(this.selectedLineWidth);
				//System.out.println("should be black");
			}else{
				//System.out.println("should be gray");
			}
		}else{
			//System.out.println("should be gray");
		}

		gc.drawOval(location.x - size.x/2, location.y - size.y/2, size.x, size.y);
		
		// Draw the label
		if(super.showLabel) {			
			if(selected) {
				gc.setFont(selectedFont);
			}
			else {
				gc.setFont(font);
				gc.setForeground(this.labelColor);
			}
			this.labelSize = gc.stringExtent(super.label);			
			gc.drawString(super.label, location.x - Math.round(labelSize.x/2F) + 1, 
					location.y - Math.round(labelSize.y/2F) + 1, true);
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.toolTip.dispose();
	}

	public boolean isMouseOver() {		
		return isMouseOver;
	}

	public void setMouseOver(boolean value) {
		//We show the schema name tool tip when the mouse is over the schema  if it's not already shown
		if(value && !this.showLabel && !this.toolTip.isVisible()) {
			//this.showLabel = value;
			this.toolTip.setVisible(true);
		}
		else if(!value && !this.showLabel && this.toolTip.isVisible()) {
			this.toolTip.setVisible(false);
		}
		
		this.isMouseOver = value;
	}

	public Color getLabelColor() {
		return this.labelColor;
	}

	public void setLabelColor(Color color) {
		this.labelColor = color;
	}

	public int getSelectedLineWidth() {
		return selectedLineWidth;
	}

	public void setSelectedLineWidth(int selectedLineWidth) {
		this.selectedLineWidth = selectedLineWidth;
	}

	public Color getSelectedLineColor() {
		return selectedLineColor;
	}

	public void setSelectedLineColor(Color selectedLineColor) {
		this.selectedLineColor = selectedLineColor;
	}	
}
