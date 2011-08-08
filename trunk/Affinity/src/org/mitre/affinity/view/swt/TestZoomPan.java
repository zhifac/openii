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

//Send questions, comments, bug reports, etc. to the authors:

//Rob Warner (rwarner@interspatial.com)
//Robert Harris (rbrt_harris@yahoo.com)

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;

/**
* This class demonstrates a Sash
*/
public class TestZoomPan extends ZoomPanCanvas {
	
	public TestZoomPan(final Composite parent, int style) {
		super(parent, style);	
		
		//Add repaint listener
		/*
		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				render(e.gc, parent);
			}
		});*/
	}
	
	public void render(GC gc, Composite parent) {
		//System.out.println("Zoom level " + zoom + ", Pan coordinates: (" + xPos + ", " + yPos + ")");
		gc.setAntialias(SWT.ON);
		gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLUE));
		gc.setBackground(gc.getForeground());
		Rectangle bounds = this.getBounds();
		gc.fillOval((bounds.width - 30)/2 + bounds.x, (bounds.height - 30)/2 + bounds.y, 30, 30);		
	}	
	
	/**
	 * Application entry point
	 * 
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Zoom/Pan Test");

		TestZoomPan canvas = new TestZoomPan(shell, SWT.NONE);
		canvas.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		canvas.setSize(300,400);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
