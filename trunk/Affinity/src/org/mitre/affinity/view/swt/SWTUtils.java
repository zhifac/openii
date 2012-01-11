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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

//import org.eclipse.jface.resource.FontRegistry;

public class SWTUtils {	
	public static final String TITLE_FONT = "title";
	public static final String SUBTITLE_FONT = "subtitle";
	public static final String BODY_FONT = "body";
	public static final String BOLD_BODY_FONT = "bold_body";
	public static final String NOTATE_FONT = "notate";
	
	public static enum TextSize {Small, Normal, Large};
	
	public static final String LARGE_FONT = "large";
	public static final String LARGE_BOLD_FONT = "large_bold";
	public static final String NORMAL_FONT = "normal";
	public static final String NORMAL_BOLD_FONT = "normal_bold";
	public static final String SMALL_FONT = "small";
	public static final String SMALL_BOLD_FONT = "small_bold";
	
	private static final Map<String, Font> fonts;	
	static {
		fonts = new HashMap<String, Font>();
		fonts.put(TITLE_FONT, new Font(Display.getDefault(), new FontData("Arial", 10, SWT.BOLD)));
		fonts.put(SUBTITLE_FONT, new Font(Display.getDefault(), new FontData("Arial", 8, SWT.BOLD)));
		fonts.put(BODY_FONT, new Font(Display.getDefault(), new FontData("Arial", 8, SWT.NORMAL)));
		fonts.put(BOLD_BODY_FONT, new Font(Display.getDefault(), new FontData("Arial", 8, SWT.BOLD)));
		fonts.put(NOTATE_FONT, new Font(Display.getDefault(), new FontData("Arial", 7, SWT.BOLD)));
		
		fonts.put(SMALL_FONT, new Font(Display.getDefault(), new FontData("Arial", 7, SWT.NORMAL)));
		fonts.put(SMALL_BOLD_FONT, new Font(Display.getDefault(), new FontData("Arial", 9, SWT.BOLD)));
		fonts.put(NORMAL_FONT, new Font(Display.getDefault(), new FontData("Arial", 9, SWT.NORMAL)));
		fonts.put(NORMAL_BOLD_FONT, new Font(Display.getDefault(), new FontData("Arial", 11, SWT.BOLD)));
		fonts.put(LARGE_FONT, new Font(Display.getDefault(), new FontData("Arial", 11, SWT.NORMAL)));
		fonts.put(LARGE_BOLD_FONT, new Font(Display.getDefault(), new FontData("Arial", 13, SWT.BOLD)));
	}
	
	private SWTUtils() {}
	
	public static Font getFont(String fontType) {
		return fonts.get(fontType);
	}
	
	public static Label createTitleLabel(Composite parent, int style, String text) {
		return createLabel(parent, style, text, TITLE_FONT);
	}
	
	public static Label createSubTitleLabel(Composite parent, int style, String text) {
		return createLabel(parent, style, text, SUBTITLE_FONT);
	}
	
	public static Label createNotateLabel(Composite parent, int style, String text) {
		return createLabel(parent, style, text, NOTATE_FONT);
	}
	
	public static Label createLabel(Composite parent, int style, String text, String fontType) {
		Label label = new Label(parent, style);
		label.setText(text);
		Font font = fonts.get(fontType);
		if(font != null)
			label.setFont(font);
		label.setBackground(parent.getBackground());
		return label;
	}
	
	public static Link createLink(Composite parent, int style, String text) {
		return createLink(parent, style, text, BODY_FONT);
	}
	
	public static Link createLink(Composite parent, int style, String text, String fontType) {
		Link link = new Link(parent, style);
		link.setText("<A>" + text + "</A>");
		Font font = fonts.get(fontType);
		if(font != null)
			link.setFont(font);
		link.setBackground(parent.getBackground());
		return link;
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

	@Override
	protected void finalize() throws Throwable {
		for(Font font : fonts.values()) {
			font.dispose();
		}
	}
	
	
}
