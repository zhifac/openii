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

package org.mitre.affinity.view.swt.event;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.mitre.affinity.view.swt.IRenderer;

public class DoubleBufferPaintListener implements PaintListener {
	private final IRenderer renderer;
	private final Composite parent;

	public DoubleBufferPaintListener(IRenderer renderer, Composite parent) {
		this.renderer = renderer;
		this.parent = parent;
	}
	
	public void paintControl(PaintEvent ev) {
		//Render on the GC using the renderer and double buffering		
		
		// Create the image to fill the canvas				
	    Image image = new Image(ev.display, ev.width, ev.height);

	    // Set up the offscreen gc
	    GC bufferGC = new GC(image);
	    
	    //Draw on the buggered image
	    renderer.render(bufferGC, parent);   

	    //Draw the offscreen gc to the screen
	    ev.gc.drawImage(image, 0, 0);			    

	    // Clean up
	    image.dispose();
	    bufferGC.dispose();		
	}
}
