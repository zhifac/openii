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

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * @author CBONACETO
 *
 */
public class SWTUtils {		
	
	private SWTUtils() {}	
	
	/** Get an AWT color given an SWT color */
	public static java.awt.Color getAwtColor(Color color) {
		if(color != null) {
			return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue());
		}
		return null;
	}
	
	public static void centerShellOnMonitor(Monitor monitor, Shell shell) {
		Rectangle screenSize = monitor.getBounds();			
		shell.setLocation((screenSize.width - shell.getSize().x)/2,
				(screenSize.height - shell.getSize().y)/2);			
	}
	
	public static void centerShellOnShell(Shell shell, Shell centerShell) {
		Rectangle bounds = centerShell.getBounds();		
		shell.setLocation(bounds.x + (bounds.width - shell.getSize().x)/2,
				bounds.y + (bounds.height - shell.getSize().y)/2);
		
		//Point centerShellScreenCoords = toShellCoordinates(shell.getLocation(), centerShell);		
		//shell.setLocation(screenCoords.x + (centerShell.getSize().x - shell.getSize().x)/2,
		//		screenCoords.y + (centerShell.getSize().y - shell.getSize().y)/2);
	}
	
	public static void centerShellOnControl(Shell shell, Control centerControl) {
		Point centerControlScreenCoords = centerControl.getDisplay().map(centerControl.getShell(), centerControl, centerControl.getLocation());
		//Point screenCoords = toShellCoordinates(centerControl.getLocation(), centerControl.getShell());	
		//System.out.println(centerControlScreenCoords.x + ", " + centerControlScreenCoords.y);
		//System.out.println(centerControl.getLocation().x + ", " + centerControl.getLocation().y);
		shell.setLocation(centerControlScreenCoords.x + (centerControl.getSize().x - shell.getSize().x)/2,
				centerControlScreenCoords.y + (centerControl.getSize().y - shell.getSize().y)/2);	
	}
	
	// converts coordinate system Point to absolute point on Shell's client area
	public static Point toShellCoordinates(Point location, Shell shell) {		
		Rectangle clientArea = shell.getClientArea();
		Point midpoint = new Point(clientArea.width / 2, clientArea.height / 2);
		
		// get the new x coordinate
		int xPoint;
		if(location.x == 0){
			xPoint = midpoint.x;
		}
		else {
			xPoint = midpoint.x + location.x;
		}
		
		// get the new y coordinate
		int yPoint;
		if(location.y == 0){
			yPoint = midpoint.y;
		}
		else {
			yPoint = midpoint.y - location.y;
		}
		
		return new Point(xPoint, yPoint);
	}	
}