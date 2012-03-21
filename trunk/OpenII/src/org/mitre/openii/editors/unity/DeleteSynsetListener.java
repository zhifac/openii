/*
 *  Copyright 2011 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
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
package org.mitre.openii.editors.unity;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Table;

public class DeleteSynsetListener implements Listener {

    private UnityCanvas unityCanvas;
    private Shell dialog;
    private Control control;
    private Table theTable;


	public DeleteSynsetListener(UnityCanvas unity, Control controlIn, Table table) {
		unityCanvas = unity;
		control = controlIn;
		theTable = table;
	}
	
	public void handleEvent(Event e) {
		unityCanvas.activeTable = theTable;
		if(theTable.getSelectionCount() >= 1){
			TableItem[] rows = theTable.getSelection();
			String name = "selected Synsets";
			if(theTable.getSelectionCount() == 1) {
				for(int i = 0; i< theTable.getColumnCount(); i++){
					if(theTable.getColumn(i).getData("uid").equals(new Integer(-201))){
						name = "\"" + rows[0].getText(i) + "\"";
					}
				}
			}
			dialog = new Shell(unityCanvas.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			GridLayout dialogLayout = new GridLayout(2, true);
			dialog.setLayout(dialogLayout);
			dialog.setText("Delete Synset");
			dialog.setSize(200, 120);
			dialog.setLocation(control.toDisplay(10, 10));
			Label nameLabel = new Label(dialog, SWT.NONE);
			nameLabel.setText("This will permanently delete " + name + " from the Vocabulary.");
			GridData gridData = new GridData();
			gridData.horizontalSpan = 2;
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;
			gridData.grabExcessVerticalSpace = false;
			nameLabel.setLayoutData(gridData);
			Label nameLabel2 = new Label(dialog, SWT.NONE);
			nameLabel2.setText("Are you sure you want to do this?");
			gridData = new GridData();
			gridData.horizontalSpan = 2;
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;
			gridData.grabExcessVerticalSpace = false;
			nameLabel2.setLayoutData(gridData);
			Button ok = new Button(dialog, SWT.PUSH);
			ok.setText(" Yes ");
			ok.setSize(50, 20);
			gridData = new GridData();
			gridData.horizontalAlignment = GridData.END;
			gridData.verticalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = false;
			gridData.grabExcessVerticalSpace = false;
			ok.setLayoutData(gridData);
			Button cancel = new Button(dialog, SWT.PUSH);
			cancel.setText(" No ");
			cancel.setSize(50, 20);
			gridData = new GridData();
			gridData.horizontalAlignment = GridData.BEGINNING;
			gridData.verticalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = false;
			gridData.grabExcessVerticalSpace = false;
			cancel.setLayoutData(gridData);

			ok.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					TableItem items[] = unityCanvas.activeTable.getSelection();
					for(int i = items.length-1; i >= 0 ; i--){
						Integer id = (Integer)items[i].getData("uid");
						unityCanvas.getWorkspace().removeFromWorkspace(id);	
						unityCanvas.getInvertedVocab().removeTerm(unityCanvas.getVocabulary().getTerm(id));
						unityCanvas.getVocabulary().removeTerm(id);
						unityCanvas.getTableView().removeTerm(id);
					}
//					unityCanvas.getTableView().adjustTableSize(0-items.length);
					unityCanvas.getTableView().resetTableView();
					unityCanvas.needSave(true);
					dialog.close();
				}
			});				
			cancel.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					dialog.close();
				}
			});				
			

			dialog.pack(true);
			dialog.layout(true);
			dialog.open();
			
			
		}
	}
}