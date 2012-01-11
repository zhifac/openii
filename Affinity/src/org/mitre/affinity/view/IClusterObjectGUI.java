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

import org.eclipse.swt.graphics.Color;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.mitre.affinity.view.swt.IRenderer;

/**
 * Interface for implementations that render cluster objects.
 * 
 * @author cbonaceto
 *
 */
public interface IClusterObjectGUI<K extends Comparable<K>, V> extends IRenderer, Comparable<IClusterObjectGUI<K, V>> {	
	
	public K getObjectID();	
	public void setObjectID(K objectID);
	
	public V getClusterObject();
	public void setClusterObject(V clusterObject);
	
	public String getLabel();
	public void setLabel(String label);
	
	public boolean isShowLabel();
	public void setShowLabel(boolean showLabel);
	
	public void setLabelColor(Color color);
	public Color getLabelColor();	
	
	public boolean isSelected();
	public void setSelected(boolean selected);
	
	public Point getLocation();
	public void setLocation(Point location);
	public void setLocation(int x, int y);
	
	public Rectangle getBounds();
	
	public Point getSize();
	
	/**
	 * Returns true if the schema gui contains the point (x, y)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean containsPoint(int x, int y);
	
	public Font getFont();
	public void setFont(Font font);
	
	public Font getSelectedFont();
	public void setSelectedFont(Font selectedFont);
	
	public String getToolTipText();
	public void setToolTipText(String text);
	
	public Color getForeground();
	public void setForeground(Color color); 
	
	public Color getBackground();
	public void setBackground(Color color);
	
	public boolean isMouseOver();
	public void setMouseOver(boolean value);
}