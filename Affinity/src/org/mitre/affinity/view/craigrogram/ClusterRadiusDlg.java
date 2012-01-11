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

package org.mitre.affinity.view.craigrogram;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.view.swt.SWTUtils;

/**
 * @author cbonaceto
 *
 */
public class ClusterRadiusDlg extends Dialog {	
	
	private Shell parent;
	
	private int style;
	
	private Shell dlgShell;
	
	private DialogModel dialogModel;
	
	//Dialog components
	private Composite dlgPanel;	
	private Combo radiusCombo;
	
	public ClusterRadiusDlg(Shell parent, int style) {
		super(parent, style);
		this.parent = parent;
		this.style = style;
		this.dialogModel = new DialogModel();		
		this.createDlg();
	}
	
	private void createDlg() {
		this.dlgShell = new Shell(parent.getDisplay(), style | SWT.DIALOG_TRIM);
		dlgShell.setText("Set Cluster Spacing");
		dlgShell.setLayout(new FillLayout());
		
		this.dlgPanel = new Composite(this.dlgShell, SWT.NONE);		
		GridLayout gd = new GridLayout(1, false);
		gd.verticalSpacing = 10;
		this.dlgPanel.setLayout(gd);
		
		Composite radiusPane = new Composite(dlgPanel, SWT.NONE);
		radiusPane.setLayoutData(createDefaultGridData(SWT.LEFT));
		radiusPane.setLayout(createDefaultRowLayout());
		Label radiusLabel = new Label(radiusPane, SWT.NONE);
		radiusLabel.setText("Cluster Spacing (pixels): ");
		this.radiusCombo = new Combo(radiusPane, SWT.DROP_DOWN | SWT.READ_ONLY);	
		for(int i=0; i<=30; i++) {
			this.radiusCombo.add(Integer.toString(i));
		}
		
		Composite buttonsPane = new Composite(dlgPanel, SWT.NONE);
		buttonsPane.setLayoutData(createDefaultGridData(SWT.CENTER));
		buttonsPane.setLayout(createDefaultRowLayout());
		Button okButton = new Button(buttonsPane, SWT.PUSH);
		okButton.setText("OK");
		okButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dialogModel.buttonPushed = DialogModel.ButtonType.OK;	
				dialogModel.radius = radiusCombo.getSelectionIndex();
				dlgShell.dispose();
			}
		});		
		Button cancelButton = new Button(buttonsPane, SWT.PUSH);
		cancelButton.setText("Cancel");	
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dialogModel.buttonPushed = DialogModel.ButtonType.CANCEL;
				dlgShell.dispose();
			}
		});
		
		this.dlgShell.pack();
	}
	
	private static RowLayout createDefaultRowLayout() {
		RowLayout rl = new RowLayout(SWT.HORIZONTAL);
		rl.center = true;
		rl.spacing = 10;
		return rl;
	}
	
	private static GridData createDefaultGridData(int horizontalAlignment) {
		return new GridData(horizontalAlignment, SWT.CENTER, true, false);
	}	
	
	public boolean isDisposed() {
		if(dlgShell != null) {
			return dlgShell.isDisposed();
		}
		return true;
	}
	
	public boolean isVisible() {
		if(this.dlgShell != null && !this.dlgShell.isDisposed())
			return this.dlgShell.isVisible();
		return false;
	}	

	public Object setVisible(boolean visible) {
		boolean dlgVisible = this.isVisible();
		if(visible && !dlgVisible) {
			//Open the dialog
			if(dlgShell.isDisposed()) {				
				createDlg();				
			}
			this.initControlsFromModel();
			SWTUtils.centerShellOnControl(dlgShell, parent);
			//SWTUtils.centerShellOnParent(parent, dlgShell);	
			dlgShell.setVisible(true);			
			
			Display display = dlgShell.getDisplay();
			while (!dlgShell.isDisposed()) {
				if(display.readAndDispatch()){
					display.sleep();
				}
			}
			dlgShell.dispose();
			return this.dialogModel;
		}
		else if(!visible && dlgVisible) {
			//Close the dialog
			dlgShell.setVisible(false);
		}
		return null;
	}
	
	/**
	 * Resets the dialog controls to their default settings
	 */
	public void resetDialog() {		
	}
	
	/**
	 * Set the current radius.
	 * 
	 * @param radius
	 */
	public void setRadius(int radius) {
		dialogModel.radius = radius;
		radiusCombo.select(radius);
	}
	
	private void initControlsFromModel() {
		radiusCombo.select(this.dialogModel.radius);		
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if(dlgShell != null && !dlgShell.isDisposed()) {
			dlgShell.dispose();
		}
	}
	
	public static class DialogModel {
		public static enum ButtonType {OK, CANCEL};
		public static enum PlanningMode {MANUAL, AUTOMATED};
		
		public ButtonType buttonPushed;
		public int radius;
	}
}
