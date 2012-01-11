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

package org.mitre.affinity.view.event;

import java.util.Collection;

import org.mitre.affinity.model.clusters.ClusterGroup;

public class SelectionClickedEvent<K extends Comparable<K>> extends SelectionEvent<K> {
	
	public static final int LEFT_BUTTON = 1;
	
	public static final int RIGHT_BUTTON = 3;
	
	/** The number of the mouse button pressed that generated the event */
	public int button;
	
	/** Whether or not the control key was held down when the mouse was pressed 
	 * (used for right clicks on Macs) */
	public boolean controlDown = false;
	
	/** The number of times the button was pressed */
	public int clickCount;
	
	/** x coordinate of the mouse */
	public int x;
	
	/** y coordinate of the mouse */
	public int y;
	
	public SelectionClickedEvent(Object eventSource) {
		super(eventSource);
	}
	
	public SelectionClickedEvent(Object eventSource, Collection<K> selectedClusterObjects, 
			Collection<ClusterGroup<K>> selectedClusters) {
		super(eventSource, selectedClusterObjects, selectedClusters);
	}
	
	public SelectionClickedEvent(Object eventSource, int button, boolean controlDown, int clickCount, int x, int y) {
		this(eventSource, null, null, button, controlDown, clickCount, x, y);
	}
	
	public SelectionClickedEvent(Object eventSource, Collection<K> selectedClusterObjects, 
			Collection<ClusterGroup<K>> selectedClusters,
			int button, boolean controlDown, int clickCount, int x, int y) {
		super(eventSource, selectedClusterObjects, selectedClusters);
		this.button = button;
		this.controlDown = controlDown; 
		this.clickCount = clickCount;
		this.x = x;
		this.y = y;
	}
	
	public int getButton() {
		return button;
	}

	public boolean isControlDown() {
		return controlDown;
	}

	public int getClickCount() {
		return clickCount;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}	
}
