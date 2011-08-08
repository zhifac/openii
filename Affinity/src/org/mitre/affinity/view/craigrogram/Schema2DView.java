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
import org.mitre.affinity.view.swt.IRenderer;
import org.mitre.affinity.view.swt.ZoomPanCanvas;

public class Schema2DView extends ZoomPanCanvas implements IRenderer {	
	/**
	 * Schemata to render
	 */
	protected List<ISchemaGUI> schemata;	
	
	protected boolean schemaNamesVisible = true;
	
	public Schema2DView(Composite parent, int style) {
		this(parent, style, null);
	}
	
	public Schema2DView(final Composite parent, int style, List<ISchemaGUI> schemata) {
		//super(parent, style | SWT.DOUBLE_BUFFERED);
		super(parent, style);
		this.schemata = schemata;
		
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
		for(ISchemaGUI schema : schemata)
			schema.setFont(font);
	}
	
	public void setSelectedFont(Font selectedFont) {		
		for(ISchemaGUI schema : schemata)
			schema.setSelectedFont(selectedFont);
	}
	
	public void setFonts(Font font, Font selectedFont) {
		super.setFont(font);
		for(ISchemaGUI schema : schemata) {
			schema.setFont(font);
			schema.setSelectedFont(selectedFont);
		}
	}	

	/**
	 * Gets the first schema guis that contain the point (x,y)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public ISchemaGUI getFirstSchemaContainsPoint(int x, int y) {
		for(ISchemaGUI s : this.schemata) {
			if(s.containsPoint(x, y))
				return s;
		}
		return null;
	}
	
	/**
	 * Gets all schema guis that contain the point (x,y)
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public List<ISchemaGUI> getAllSchemasContainPoint(int x, int y) {
		List<ISchemaGUI> schemas = new ArrayList<ISchemaGUI>();
		for(ISchemaGUI s : this.schemata) {
			if(s.containsPoint(x, y))
				schemas.add(s);
		}		
		return schemas;
	}

	public List<ISchemaGUI> getSchemata() {
		return schemata;
	}

	public void setSchemata(List<ISchemaGUI> schemata) {
		this.schemata = schemata;		
		//this.redraw();
	}
	
	public boolean getSchemaNamesVisible(){
		return this.schemaNamesVisible;
	}
	
	public void setSchemaNamesVisible(boolean visible) {		
		if(visible != this.schemaNamesVisible) {
			for(ISchemaGUI schema : schemata){				
				schema.setShowLabel(visible);
			}
		}
		this.schemaNamesVisible = visible;		
		//this.redraw();
	}	
	
	//public void redraw(){
	//	super.redraw();
		
	//	this.setSize(getParent().getSize());
	//	System.err.println(getParent().getSize());		
	//}

	public void render(GC gc, Composite parent) {	
		Rectangle bounds = this.getBounds();
		this.drawBackground(gc, bounds.x, bounds.y, bounds.width, bounds.height);
		
		if(this.schemata != null) {
			//System.out.println("Rendering Schemata");
			for(ISchemaGUI s : this.schemata) {
				//System.out.println("Rendering schema " + s.getLabel() + " at position: " + s.getLocation());
				s.render(gc, this);
			}		
		}
	}		
}
