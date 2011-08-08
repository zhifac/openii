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

//import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * An implementation of a schema GUI that displays the schema as an SWT label.
 * 
 * @author CBONACETO
 *
 */
public class LabelSchemaGUI implements ISchemaGUI {
	private Label label;
	private String labelText;
	private boolean showLabel;
	private boolean selected;
	private Integer schemaID;
	private boolean isMouseOver;
	
	private Font font;
	private Font selectedFont;
	
	public LabelSchemaGUI(Composite parent, int style) {
		this(parent, style, null);
		this.isMouseOver = false;
	}
	
	public LabelSchemaGUI(Composite parent, int style, Integer schemaID) {
		this.label = new Label(parent, style);
		this.label.setAlignment(SWT.CENTER);
		this.font = label.getFont();
		this.selectedFont = label.getFont();
		setSchemaID(schemaID);
		this.isMouseOver = false;
	}
	
	public int compareTo(ISchemaGUI arg0) {		
		return arg0.getSchemaID().compareTo(schemaID);
	}
	
	public Rectangle getBounds() {
		return this.label.getBounds();
	}
	
	/* (non-Javadoc)
	 * @see org.mitre.affinity.view.swt.ISchemaGUI#containsPoint(int, int)
	 */
	public boolean containsPoint(int x, int y) {
		Point size = getSize();
		Point location = getLocation();
		int startX = location.x - (size.x / 2); 
		int startY = location.y - (size.y / 2);

		if((x >= startX && x <= startX + size.x) && (y >= startY && y <= startY + size.y))						
			return true;
		return false;
	}

	public Color getBackground() {
		return label.getBackground();
	}

	public String getLabel() {
		return this.labelText;
	}

	public Font getFont() {
		return this.font;
	}
	
	public Font getSelectedFont() {
		return this.selectedFont;
	}

	public Color getForeground() {
		return label.getForeground();
	}

	public Point getLocation() {
		return label.getLocation();
	}
	
	public Integer getSchemaID() {
		return this.schemaID;
	}

	public Point getSize() {
		return label.getSize();
	}

	public String getToolTipText() {
		return label.getToolTipText();
	}
	
	public boolean isShowLabel() {
		return this.showLabel;
	}

	public boolean isSelected() {
		return this.selected;
	}
	
	public void render(GC gc, Composite parent) {
		//System.out.println("Rendering Schema: " + this.schemaGUI.getName());		
		if(label.getParent() != parent) { 
			label.setParent(parent);
		}
		label.redraw();
	}	
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		if(selected)
			label.setFont(selectedFont);
		else
			label.setFont(font);
	}

	public void setBackground(Color color) {
		label.setBackground(color);
	}

	public void setLabel(String label) {
		this.labelText = label;
		if(this.showLabel) {
			this.setLabelText(label);
		}
	}
	
	private void setLabelText(String labelText) {
		this.label.setText(labelText);
		this.label.pack();
		this.label.setSize(this.label.getSize().x + 2, this.label.getSize().y + 2);
	}

	public void setForeground(Color color) {
		label.setForeground(color);	
	}
	
	public Color getLabelColor() {
		return label.getForeground();
	}

	public void setLabelColor(Color color) {
		label.setForeground(color);	
	}

	public void setLocation(int x, int y) {
		label.setLocation(x, y);
	}

	public void setLocation(Point location) {
		label.setLocation(location);	
	}

	public void setShowLabel(boolean showLabel) {
		this.showLabel = showLabel;
		if(showLabel) {
			this.setLabelText(labelText);
		}
		else {
			this.setLabelText(" ");
		}
	}
	
	public void setFont(Font font) {
		this.font = font;
		if(!isSelected())
			label.setFont(font);
	}	

	public void setSelectedFont(Font selectedFont) {
		this.selectedFont = selectedFont;
		if(isSelected())
			label.setFont(selectedFont);
	}

	public void setToolTipText(String text) {
		label.setToolTipText(text);		
	}
	
	public void setSchemaID(Integer schemaID) {
		this.schemaID = schemaID;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		this.label.dispose();
	}

	public boolean isMouseOver() {	
		return this.isMouseOver;
	}

	public void setMouseOver(boolean value) {	
		this.isMouseOver = value;
	}	
}