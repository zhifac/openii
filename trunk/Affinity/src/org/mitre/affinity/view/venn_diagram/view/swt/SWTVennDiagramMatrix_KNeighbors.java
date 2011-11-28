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

package org.mitre.affinity.view.venn_diagram.view.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSetsMatrix;
import org.mitre.affinity.view.venn_diagram.view.swing.VennDiagramKNearestsMatrixPane;

public class SWTVennDiagramMatrix_KNeighbors extends Canvas{
	private final VennDiagramKNearestsMatrixPane vennDiagramMatrix;
	
	public SWTVennDiagramMatrix_KNeighbors(Composite parent, int style, VennDiagramSetsMatrix matrix, boolean showMinMaxSlider) {		
		super(parent, style | SWT.EMBEDDED);
		
		java.awt.Frame frame = SWT_AWT.new_Frame(this);		
		java.awt.Panel panel = new java.awt.Panel(new java.awt.BorderLayout());
		
		this.vennDiagramMatrix = new VennDiagramKNearestsMatrixPane(matrix, showMinMaxSlider);
		panel.add(vennDiagramMatrix);	
		frame.add(panel);
	}

	public VennDiagramKNearestsMatrixPane getVennDiagramMatrix() {
		return vennDiagramMatrix;
	}	
}