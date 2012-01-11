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

package org.mitre.affinity.view.drill_down.schemas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.schemas.AffinitySchemaModel;
import org.mitre.affinity.view.swt.SWTUtils;

/**
 * @author CBONACETO
 *
 */
public class SchemaClusterDetailsDlg {
	
	private final AffinitySchemaModel affinityModel;
	
	private ClusterGroup<Integer> cluster;	
		
	Composite parent;
	
	Shell dialogShell;
	
	int style;
	
	// Dialog components
	private SchemaClusterDetailsPane clusterDetailsPane;
	
	public SchemaClusterDetailsDlg(Composite parent, int style, AffinitySchemaModel affinityModel, 
			ClusterGroup<Integer> cluster) {
		this.parent = parent;
		this.style = style;
		this.affinityModel = affinityModel;
		this.cluster = cluster;
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
			SWTUtils.centerShellOnControl(dialogShell, parent.getShell());
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
		this.dialogShell = new Shell(style | SWT.DIALOG_TRIM);
		this.dialogShell.setParent(parent);
		
		dialogShell.setText("Terms in Cluster");
		dialogShell.setLayout(new GridLayout(1, false));
		
		this.clusterDetailsPane = new SchemaClusterDetailsPane(dialogShell, SWT.NONE, affinityModel, cluster);
		clusterDetailsPane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Button closeButton = new Button(dialogShell, SWT.PUSH);
		closeButton.setText("Close");
		closeButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		closeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dialogShell.dispose();
			}
		});
	
		//clusterDetailsPane.setSize(clusterDetailsPane.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, 400);
		dialogShell.setSize(clusterDetailsPane.computeSize(SWT.DEFAULT, SWT.DEFAULT).x + 20, 400);		
		//dialogShell.pack();		
	}
}
