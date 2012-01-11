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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.view.swt.SWTUtils;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSets;
import org.mitre.affinity.view.venn_diagram.model.VennDiagramSetsMatrix;

/**
 * Creates an SWT dialog for either a VennDiagramPane or a VennDiagramMatrix
 * 
 * @author CBONACETO
 *
 */
public class VennDiagramDlg extends Dialog {
	private VennDiagramSetsMatrix matrix;
	private VennDiagramSets sets;
	private boolean showMinMaxSlider;
		
	Shell dialogShell;
	Point dialogSize;
	
	public VennDiagramDlg(Shell parent, int style, VennDiagramSetsMatrix matrix, boolean showMinMaxSlider) {
		super(parent, style);	
		this.matrix = matrix;
		this.dialogSize = new Point(400, 500);
		this.showMinMaxSlider = showMinMaxSlider;
	}	
	
	public VennDiagramDlg(Shell parent, int style, VennDiagramSets sets, boolean showMinMaxSlider) {
		super(parent, style);	
		this.sets = sets;
		this.dialogSize = new Point(400, 500);
		this.showMinMaxSlider = showMinMaxSlider;
	}
	
	public void setSize(int width, int height) {
		dialogSize.x = width; 
		dialogSize.y = height;
		if(dialogShell != null) {
			dialogShell.setSize(dialogSize);
		}
	}

	public boolean isVisible() {
		if(dialogShell != null && !dialogShell.isDisposed())
			return dialogShell.isVisible();
		return false;
	}	

	public void setVisible(boolean visible) {
		boolean dlgVisible = this.isVisible();
		if(visible && !dlgVisible) {
			//Open the dialog
			if(dialogShell == null || dialogShell.isDisposed()) {
				createDialogShell();				
			}			
			SWTUtils.centerShellOnControl(dialogShell, getParent());
			//SWTUtils.centerShellOnParent(parent.getShell(), dialogShell);	
			dialogShell.open();			
			//dialogShell.setVisible(true);			
			
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if(display.readAndDispatch()){
					display.sleep();
				}
			}
			dialogShell.dispose();
		}
		else if(!visible && dlgVisible) {
			//Close the dialog
			dialogShell.dispose();
		}
	}
	
	private void createDialogShell() {
		this.dialogShell = new Shell(getParent(), getStyle() | SWT.DIALOG_TRIM);
		
		dialogShell.setText("Schema Relatedness");
		dialogShell.setLayout(new GridLayout(1, false));
		
		Composite vennDiagramPane = null;
		if(matrix != null)
			vennDiagramPane = new SWTVennDiagramMatrix(dialogShell, SWT.NONE, matrix, showMinMaxSlider);
		else
			vennDiagramPane = new SWTVennDiagram(dialogShell, SWT.NONE, sets, showMinMaxSlider);
		vennDiagramPane.setBackground(dialogShell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		vennDiagramPane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Button closeButton = new Button(dialogShell, SWT.PUSH);
		closeButton.setText("Close");
		closeButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		closeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dialogShell.dispose();
			}
		});		
		dialogShell.setSize(dialogSize);
		//dialogShell.pack();
	}
}
