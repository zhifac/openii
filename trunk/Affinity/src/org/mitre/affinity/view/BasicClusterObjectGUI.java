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

package org.mitre.affinity.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.mitre.affinity.view.swt.SWTUtils;

/**
 * @author cbonaceto
 *
 * @param <K>
 */
public abstract class BasicClusterObjectGUI<K extends Comparable<K>, V> implements IClusterObjectGUI<K, V> {
	
	/** The cluster object ID */
	protected K objectID; 
	
	/** The cluster object */
	protected V clusterObject;
	
	protected Color background = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
	
	protected Color foreground = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	
	protected Font font = SWTUtils.getFont(SWTUtils.NORMAL_FONT);
	
	protected Font selectedFont = SWTUtils.getFont(SWTUtils.NORMAL_BOLD_FONT);
	
	protected String label;
	
	protected Point location;	
	
	protected boolean showLabel = true;
	
	protected boolean selected = false;
	
	public int compareTo(IClusterObjectGUI<K, V> arg0) {		
		return arg0.getObjectID().compareTo(objectID);
	}
	
	@Override
	public Font getSelectedFont() {
		return this.selectedFont;
	}

	@Override
	public V getClusterObject() {
		return clusterObject;
	}

	@Override
	public void setClusterObject(V clusterObject) {
		this.clusterObject = clusterObject;
	}

	@Override
	public void setSelectedFont(Font selectedFont) {
		this.selectedFont = selectedFont;
	}

	@Override
	public Color getBackground() {
		return this.background;
	}

	@Override
	public Font getFont() {
		return this.font;
	}

	@Override
	public Color getForeground() {
		return this.foreground;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public Point getLocation() {
		return this.location;
	}
	
	@Override
	public K getObjectID() {
		return this.objectID;
	}
	
	@Override
	public boolean isShowLabel() {
		return this.showLabel;
	}
	
	@Override
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	public void setBackground(Color color) {
		this.background = color;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public void setForeground(Color color) {
		this.foreground = color;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public void setLocation(int x, int y) {
		if(this.location == null) 
			this.location = new Point(x, y);
		else {
			this.location.x = x;
			this.location.y = y;
		}
	}
	
	@Override
	public void setLocation(Point location) {
		this.location = location;
	}
	
	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	@Override
	public void setObjectID(K objectID) {
		this.objectID = objectID;
	}
	
	@Override
	public void setShowLabel(boolean showLabel) {
		this.showLabel = showLabel;
	}
}