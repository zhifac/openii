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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.mitre.affinity.util.SWTUtils;

public abstract class BasicSchemaGUI implements ISchemaGUI {
	protected Color background = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
	protected Color foreground = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	protected Font font = SWTUtils.getFont(SWTUtils.NORMAL_FONT);
	protected Font selectedFont = SWTUtils.getFont(SWTUtils.NORMAL_BOLD_FONT);
	protected String label;
	protected Point location;
	protected Integer schemaID;
	protected boolean showLabel = true;
	protected boolean selected = false;
	
	public int compareTo(ISchemaGUI arg0) {		
		return arg0.getSchemaID().compareTo(schemaID);
	}

	public Font getSelectedFont() {
		return this.selectedFont;
	}

	public void setSelectedFont(Font selectedFont) {
		this.selectedFont = selectedFont;
	}

	public void render(GC gc, Composite parent) {
		// TODO Auto-generated method stub
		
	}

	public Color getBackground() {
		return this.background;
	}

	public Font getFont() {
		return this.font;
	}

	public Color getForeground() {
		return this.foreground;
	}

	public String getLabel() {
		return this.label;
	}

	public Point getLocation() {
		return this.location;
	}
	
	/*
	 * @Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	public Integer getSchemaID() {
		return this.schemaID;
	}
	
	public boolean isShowLabel() {
		return this.showLabel;
	}
	
	public boolean isSelected() {
		return this.selected;
	}

	public void setBackground(Color color) {
		this.background = color;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setForeground(Color color) {
		this.foreground = color;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setLocation(int x, int y) {
		if(this.location == null) 
			this.location = new Point(x, y);
		else {
			this.location.x = x;
			this.location.y = y;
		}
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/*
	@Override
	public void setToolTipText(String text) {
		// TODO Auto-generated method stub		
	}
	*/
	
	public void setSchemaID(Integer schemaID) {
		this.schemaID = schemaID;
	}
	
	public void setShowLabel(boolean showLabel) {
		this.showLabel = showLabel;
	}
}