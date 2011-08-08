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

//import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class AffinityConstants {
	public static ImageRegistry imageRegistry = null;
	
	/** Array containing possible zoom levels */
	public static final int[] ZOOM_LEVELS = 
		new int[] {10, 25, 50, 75, 100, 125, 150, 200, 400, 800, 1600, 2400, 3200, 6400};
	
	public static enum IconType {PAN_ICON, PANNING_ICON, CURSOR_ICON, ZOOM_IN_ICON, ZOOM_OUT_ICON}; 
	
	/*public static final String[] ICON_NAMES = {"arrow_out.png", "arrow_inout.png", "cursor.png", 
		"zoom_in.png", "zoom_out.png"};*/
	public static final String[] ICON_NAMES = {"hand.png", "grab_hand.png", "selection.png", 
	"zoom_in.png", "zoom_out.png"};
	
	private static final Map<IconType, Image> AFFINITY_ICONS = new HashMap<IconType, Image>();
	
	public static enum CursorType {PAN_CURSOR, PANNING_CURSOR, SELECT_MULTIPLE_SCHEMAS_CURSOR};
	
	private static final Map<CursorType, Cursor> AFFINITY_CURSORS = new HashMap<CursorType, Cursor>();
	
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
				//cursor = new Cursor(Display.getCurrent(), getAffinityIcon(IconType.PAN_ICON).getImageData(), 8, 0);		
				//cursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_HAND);				
				cursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_HAND);
				break;
			case PANNING_CURSOR:
				cursor = new Cursor(Display.getCurrent(), getAffinityIcon(IconType.PANNING_ICON).getImageData(), 8, 8);;				
				break;
			case SELECT_MULTIPLE_SCHEMAS_CURSOR:
				cursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW);
				break;
			}
			AFFINITY_CURSORS.put(cursorType, cursor);
		}
		return cursor;
	}
}
