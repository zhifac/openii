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

package org.mitre.affinity.view.venn_diagram.view.event;

import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;

public class VennDiagramEvent {
	/** sets that were clicked */
	private final VennDiagramSets sets;
	
	/** mouse x position */
	private final int mouseX;
	
	/** mouse y position */
	private final int mouseY;
	
	/** The number of the mouse button pressed that generated the event */
	private final int mouseButton;
	
	/** Whether or not the control key was held down when the mouse was pressed 
	 * (used for right clicks on Macs) */
	private final boolean controlDown;
	
	public VennDiagramEvent(VennDiagramSets sets, int mouseX, int mouseY, int mouseButton) {
		this(sets, mouseX, mouseY, mouseButton, false);
	}
	
	public VennDiagramEvent(VennDiagramSets sets, int mouseX, int mouseY, int mouseButton, boolean controlDown) {
		this.sets = sets;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.mouseButton = mouseButton;
		this.controlDown = controlDown;
	}

	public VennDiagramSets getSets() {
		return sets;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getMouseButton() {
		return mouseButton;
	}

	public boolean isControlDown() {
		return controlDown;
	}
}
