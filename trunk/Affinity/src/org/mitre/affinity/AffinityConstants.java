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

package org.mitre.affinity;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

/**
 * Constants class. Contains default icons, fonts, cursors, colors, etc.
 *  
 * @author CBONACETO
 *
 */
public class AffinityConstants {	
	public static ImageRegistry imageRegistry = null;
	
	/** Deafult diameter/size of cluster objects in the Craigrogram (pixels) */
	public static final int CLUSTER_OBJECT_DIAMETER = 10;
	
	/** Default cluster radius increment in the Craigrogram (pixels) */
	public static final int CLUSTER_RADIUS_INCREMENT = 5;
	
	/** Array containing possible zoom levels */
	public static final int[] ZOOM_LEVELS = 
		new int[] {10, 25, 50, 75, 100, 125, 150, 200, 400, 800, 1600, 2400, 3200, 6400};
	
	
	/**** Icons ****/
	public static enum IconType {PAN_ICON, PANNING_ICON, CURSOR_ICON, ZOOM_IN_ICON, ZOOM_OUT_ICON};
	
	public static final String[] ICON_NAMES = {"hand.png", "grab_hand.png", "selection.png", 
	"zoom_in.png", "zoom_out.png"};	
	
	private static final Map<IconType, Image> AFFINITY_ICONS = new HashMap<IconType, Image>();
	/**** Icons ****/
	
	
	/**** Cursors ****/	
	public static enum CursorType {PAN_CURSOR, PANNING_CURSOR, SELECT_MULTIPLE_OBJECTS_CURSOR};
	
	private static final Map<CursorType, Cursor> AFFINITY_CURSORS = new HashMap<CursorType, Cursor>();
	/**** Cursors ****/	
	
	
	/**** Fonts ****/
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
	//private Font selectedFont = new Font(Display.getDefault(), new FontData("Times", 18, SWT.ITALIC));
	
	private static final Map<String, Font> FONTS;	
	private static final Map<String, java.awt.Font> FONTS_AWT;
	public static final String FONT_NAME = "Arial";
	static {
		FONTS = new HashMap<String, Font>();
		FONTS_AWT = new HashMap<String, java.awt.Font>();
		
		FONTS.put(TITLE_FONT, new Font(Display.getDefault(), new FontData(FONT_NAME, 10, SWT.BOLD)));
		FONTS_AWT.put(TITLE_FONT, new java.awt.Font(FONT_NAME, java.awt.Font.BOLD, 10));
		
		FONTS.put(SUBTITLE_FONT, new Font(Display.getDefault(), new FontData(FONT_NAME, 8, SWT.BOLD)));
		FONTS_AWT.put(SUBTITLE_FONT, new java.awt.Font(FONT_NAME, java.awt.Font.BOLD, 8));
		
		FONTS.put(BODY_FONT, new Font(Display.getDefault(), new FontData(FONT_NAME, 8, SWT.NORMAL)));
		FONTS_AWT.put(BODY_FONT, new java.awt.Font(FONT_NAME, java.awt.Font.PLAIN, 8));
		
		FONTS.put(BOLD_BODY_FONT, new Font(Display.getDefault(), new FontData(FONT_NAME, 8, SWT.BOLD)));
		FONTS_AWT.put(BODY_FONT, new java.awt.Font(FONT_NAME, java.awt.Font.PLAIN, 8));
		
		FONTS.put(NOTATE_FONT, new Font(Display.getDefault(), new FontData(FONT_NAME, 7, SWT.BOLD)));
		FONTS_AWT.put(NOTATE_FONT, new java.awt.Font(FONT_NAME, java.awt.Font.BOLD, 7));
		
		FONTS.put(SMALL_FONT, new Font(Display.getDefault(), new FontData(FONT_NAME, 7, SWT.NORMAL)));
		FONTS_AWT.put(SMALL_FONT, new java.awt.Font(FONT_NAME, java.awt.Font.PLAIN, 7));
		
		FONTS.put(SMALL_BOLD_FONT, new Font(Display.getDefault(), new FontData(FONT_NAME, 9, SWT.BOLD)));
		FONTS_AWT.put(SMALL_BOLD_FONT, new java.awt.Font(FONT_NAME, java.awt.Font.BOLD, 9));
		
		FONTS.put(NORMAL_FONT, new Font(Display.getDefault(), new FontData(FONT_NAME, 9, SWT.NORMAL)));
		FONTS_AWT.put(NORMAL_FONT, new java.awt.Font(FONT_NAME, java.awt.Font.PLAIN, 9));
		
		FONTS.put(NORMAL_BOLD_FONT, new Font(Display.getDefault(), new FontData(FONT_NAME, 11, SWT.BOLD)));
		FONTS_AWT.put(NORMAL_BOLD_FONT, new java.awt.Font(FONT_NAME, java.awt.Font.BOLD, 11));
		
		FONTS.put(LARGE_FONT, new Font(Display.getDefault(), new FontData(FONT_NAME, 11, SWT.NORMAL)));
		FONTS_AWT.put(LARGE_FONT, new java.awt.Font(FONT_NAME, java.awt.Font.PLAIN, 11));
		
		FONTS.put(LARGE_BOLD_FONT, new Font(Display.getDefault(), new FontData(FONT_NAME, 13, SWT.BOLD)));
		FONTS_AWT.put(LARGE_BOLD_FONT, new java.awt.Font(FONT_NAME, java.awt.Font.BOLD, 13));
	}
	/**** Fonts ****/
	
	
	/**** Colors ****/		
	public static final Color COLOR_WHITE = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
	public static final Color COLOR_BLACK = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	public static final Color COLOR_GRAY = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
	public static final Color COLOR_LIGHT_GRAY_1 = new Color(Display.getDefault(), 238, 238, 238);
	public static final Color COLOR_LIGHT_GRAY_2 = new Color(Display.getDefault(), 150, 150, 150);
	public static final Color COLOR_MEDIUM_GRAY =new Color(Display.getDefault(), 100, 100, 100);
	public static final Color COLOR_GREEN = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
	public static final Color COLOR_BLUE = Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
	public static final Color COLOR_RED = Display.getDefault().getSystemColor(SWT.COLOR_RED);	
			
	public static final Color COLOR_FOREGROUND = COLOR_BLACK;
	
	public static final Color COLOR_BACKGROUND = COLOR_WHITE;
	
	public static final Color COLOR_HIGHLIGHT = COLOR_GREEN;
	public static final java.awt.Color COLOR_HIGHLIGHT_AWT = java.awt.Color.GREEN;
	
	public static final Color COLOR_CLUSTER_OBJECT_HIGHLIGHT_LINE = COLOR_MEDIUM_GRAY;
	public static final java.awt.Color COLOR_CLUSTER_OBJECT_HIGHLIGHT_LINE_AWT = 
			new java.awt.Color(COLOR_MEDIUM_GRAY.getRed(), COLOR_MEDIUM_GRAY.getGreen(), COLOR_MEDIUM_GRAY.getBlue()) ;
	
	public static final Color COLOR_CLUSTER_OBJECT_HIGHLIGHT_FILL = COLOR_HIGHLIGHT; //BLACK
	public static final java.awt.Color COLOR_CLUSTER_OBJECT_HIGHLIGHT_FILL_AWT = COLOR_HIGHLIGHT_AWT;
	
	public static final Color COLOR_CLUSTER_OBJECT_LINE = COLOR_LIGHT_GRAY_2;
	public static final java.awt.Color COLOR_CLUSTER_OBJECT_LINE_AWT = 
			new java.awt.Color(COLOR_LIGHT_GRAY_2.getRed(), COLOR_LIGHT_GRAY_2.getGreen(), COLOR_LIGHT_GRAY_2.getBlue()) ;;
	
	public static final Color COLOR_CLUSTER_OBJECT_FILL = COLOR_LIGHT_GRAY_2;
	public static final java.awt.Color COLOR_CLUSTER_OBJECT_FILL_AWT = COLOR_CLUSTER_OBJECT_LINE_AWT; 
	
	public static final Color COLOR_CLUSTER_OBJECT_LABEL = COLOR_BLACK;
	public static final java.awt.Color COLOR_CLUSTER_OBJECT_LABEL_AWT = java.awt.Color.BLACK;
		
	public static final Color COLOR_CLUSTER_LINE_1 = COLOR_BLUE;	
	public static final Color COLOR_CLUSTER_LINE_2 = COLOR_RED;
	public static final Color COLOR_CLUSTER_FILL_DENDROGRAM = COLOR_LIGHT_GRAY_1;	
	
	/*
	clusterObjectGUI.setLabelColor(black);
	clusterObjectGUI.setForeground(lightGray);
	clusterObjectGUI.setBackground(lightGray);
	clusterObjectGUI.setSelectedLineColor(mediumGray);*/
	/**** Colors ****/	
	
	private AffinityConstants() {}
	
	public static Image getAffinityIcon(IconType iconType) {
		Image image = AFFINITY_ICONS.get(iconType);
		if(image == null && imageRegistry != null) {			
			image = imageRegistry.get(ICON_NAMES[iconType.ordinal()]);
			if(image != null) AFFINITY_ICONS.put(iconType, image);
		}
		if(image == null) {
			image = new Image(Display.getCurrent(), iconType.getClass().getClassLoader().getResourceAsStream("icons/" + ICON_NAMES[iconType.ordinal()]));			
			//image = new Image(Display.getCurrent(), "icons/" + ICON_NAMES[iconType.ordinal()]);
			if(imageRegistry != null)
				imageRegistry.put(ICON_NAMES[iconType.ordinal()], image);
			AFFINITY_ICONS.put(iconType, image);			
		}
		return image;
	}
	
	public static Cursor getAffinityCursor(CursorType cursorType) {
		Cursor cursor = AFFINITY_CURSORS.get(cursorType);
		if(cursor == null) {
			switch(cursorType) {
			case PAN_CURSOR:	
				cursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_HAND);
				break;
			case PANNING_CURSOR:
				cursor = new Cursor(Display.getCurrent(), getAffinityIcon(IconType.PANNING_ICON).getImageData(), 8, 8);;				
				break;
			case SELECT_MULTIPLE_OBJECTS_CURSOR:
				cursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW);
				break;
			}
			AFFINITY_CURSORS.put(cursorType, cursor);
		}
		return cursor;
	}
	
	public static Font getFont(String fontType) {
		return FONTS.get(fontType);
	}
	
	public static java.awt.Font getFont_AWT(String fontType) {
		return FONTS_AWT.get(fontType);
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
		Font font = FONTS.get(fontType);
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
		Font font = FONTS.get(fontType);
		if(font != null)
			link.setFont(font);
		link.setBackground(parent.getBackground());
		return link;
	}
	
	@Override
	protected void finalize() throws Throwable {
		//Dispose of SWT resources
		for(Image image : AFFINITY_ICONS.values()) {
			if(!image.isDisposed()) {
				image.dispose();
			}
		}	
		Cursor panningCursor = AFFINITY_CURSORS.get(CursorType.PANNING_CURSOR);
		if(panningCursor != null && !panningCursor.isDisposed()) {			
			panningCursor.dispose();
		}	
		for(Font font : FONTS.values()) {
			if(!font.isDisposed()) {
				font.dispose();
			}
		}
		COLOR_LIGHT_GRAY_1.dispose();
		COLOR_LIGHT_GRAY_2.dispose();
		COLOR_MEDIUM_GRAY.dispose();
	}	
}