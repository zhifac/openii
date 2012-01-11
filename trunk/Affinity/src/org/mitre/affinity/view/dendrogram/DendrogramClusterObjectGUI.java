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

package org.mitre.affinity.view.dendrogram;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.mitre.affinity.view.BasicClusterObjectGUI;

public class DendrogramClusterObjectGUI<K extends Comparable<K>, V> extends BasicClusterObjectGUI<K, V> {
	
	/** Bounds of the rendered cluster object in the dendrogram */
	protected Rectangle bounds = null;
	
	protected Color labelColor;
	
	protected String toolTipText;
	
	protected boolean mouseOver;
	
	public DendrogramClusterObjectGUI(K objectID, V clusterObject) {
		this.objectID = objectID;
		this.clusterObject = clusterObject;
	}	

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	@Override
	public void setLabelColor(Color color) {
		this.labelColor = color;
	}

	@Override
	public Color getLabelColor() {
		return labelColor;
	}

	@Override
	public Point getSize() {
		if(bounds != null) {
			return new Point(bounds.width, bounds.height);
		}
		return null;
	}

	@Override
	public boolean containsPoint(int x, int y) {
		if(bounds != null) {
			return bounds.contains(x, y);
		}
		return false;
	}

	@Override
	public String getToolTipText() {
		return toolTipText;
	}

	@Override
	public void setToolTipText(String text) {
		this.toolTipText = text;
	}

	@Override
	public boolean isMouseOver() {
		return this.mouseOver;
	}

	@Override
	public void setMouseOver(boolean value) {
		this.mouseOver = value;
	}

	@Override
	public void render(GC gc, Composite parent) {
		//Does nothing	
	}
}