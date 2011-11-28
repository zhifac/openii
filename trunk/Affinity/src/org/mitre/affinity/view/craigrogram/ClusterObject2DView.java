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

import java.util.ArrayList;
import java.util.List;

//import org.eclipse.swt.events.MouseEvent;
//import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.mitre.affinity.view.IClusterObjectGUI;
import org.mitre.affinity.view.swt.IRenderer;
import org.mitre.affinity.view.swt.ZoomPanCanvas;

/**
 * @author cbonaceto
 *
 */
public class ClusterObject2DView<K, V> extends ZoomPanCanvas implements IRenderer {	
	
	/** Cluster objects to render */
	protected List<IClusterObjectGUI<K, V>> clusterObjects;	
	
	/** Whether cluster object names are visible */
	protected boolean clusterObjectNamesVisible = true;
	
	public ClusterObject2DView(Composite parent, int style) {
		this(parent, style, null);
	}
	
	public ClusterObject2DView(final Composite parent, int style, List<IClusterObjectGUI<K, V>> clusterObjects) {		
		super(parent, style);
		this.clusterObjects = clusterObjects;
		
		//Add repaint listener
		/*
		this.addPaintListener(new PaintListener(){
			public void paintControl(PaintEvent e){
				render(e.gc, parent);
			}
		});
		//this.addPaintListener(new DoubleBufferPaintListener(this, this));
		 * 
		 */
		
		/*
		//Add mouse listener to show schema name when the mouse is over a schema
		this.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent event) {
				if(!schemaNamesVisible) {					
					//First we need to transpose the mouse coordinates based on the current zoom/pan settings
					Point translatedPoint = canvasPointToTranslatedPoint(event.x, event.y);
					
					//TODO: Make this more efficient
					for(ISchemaGUI schema : Schema2DView.this.schemata){
						if(schema.containsPoint(translatedPoint.x, translatedPoint.y))						
							schema.setMouseOver(true);
						else 
							schema.setMouseOver(false);
					}
				}
			}			
		});*/
	}	
	
	@Override
	public void setFont(Font font) {		
		super.setFont(font);
		for(IClusterObjectGUI<K, V> object : clusterObjects) {
			object.setFont(font);
		}
	}
	
	public void setSelectedFont(Font selectedFont) {		
		for(IClusterObjectGUI<K, V> object : clusterObjects)
			object.setSelectedFont(selectedFont);
	}
	
	public void setFonts(Font font, Font selectedFont) {
		super.setFont(font);
		for(IClusterObjectGUI<K, V> object : clusterObjects) {
			object.setFont(font);
			object.setSelectedFont(selectedFont);
		}
	}	

	/**
	 * Gets the first cluster object gui that contain the point (x,y)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public IClusterObjectGUI<K, V> getFirstClusterObjectContainsPoint(int x, int y) {
		for(IClusterObjectGUI<K, V> object : clusterObjects) {
			if(object.containsPoint(x, y))
				return object;
		}
		return null;
	}
	
	/**
	 * Gets all cluster object guis that contain the point (x,y)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public List<IClusterObjectGUI<K, V>> getAllClusterObjectsContainPoint(int x, int y) {
		List<IClusterObjectGUI<K, V>> clusterObjects = new ArrayList<IClusterObjectGUI<K, V>>();
		for(IClusterObjectGUI<K, V> object : this.clusterObjects) {
			if(object.containsPoint(x, y))
				clusterObjects.add(object);
		}		
		return clusterObjects;
	}	

	public List<IClusterObjectGUI<K, V>> getClusterObjects() {
		return clusterObjects;
	}

	public void setClusterObjects(List<IClusterObjectGUI<K, V>> clusterObjects) {
		this.clusterObjects = clusterObjects;
	}	
	
	public boolean isClusterObjectNamesVisible() {
		return clusterObjectNamesVisible;
	}

	public void setClusterObjectNamesVisible(boolean clusterObjectNamesVisible) {
		this.clusterObjectNamesVisible = clusterObjectNamesVisible;
	}	

	public void render(GC gc, Composite parent) {	
		Rectangle bounds = this.getBounds();
		this.drawBackground(gc, bounds.x, bounds.y, bounds.width, bounds.height);
		
		if(clusterObjects != null) {
			for(IClusterObjectGUI<K, V> object : clusterObjects) {
				object.render(gc, this);
			}		
		}
	}		
}