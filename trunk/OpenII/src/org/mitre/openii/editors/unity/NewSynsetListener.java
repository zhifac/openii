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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.mitre.schemastore.model.terms.Term;

public class NewSynsetListener implements Listener {

    private UnityCanvas unityCanvas;
    private Shell dialog;
    private Text textfield;
    private Label errorLabel;
    private Control control;
	private Color red;


	public NewSynsetListener(UnityCanvas unity, Control controlIn) {
		unityCanvas = unity;
		control = controlIn;
		red =  unityCanvas.getDisplay().getSystemColor(SWT.COLOR_RED);

	}
	
	public void handleEvent(Event e) {
		dialog = new Shell(unityCanvas.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		GridLayout dialogLayout = new GridLayout(2, true);
		dialog.setLayout(dialogLayout);
		dialog.setText("Create a new Synset");
		dialog.setSize(200, 120);
		dialog.setLocation(control.toDisplay(10, 10));
		Label nameLabel = new Label(dialog, SWT.NONE);
		nameLabel.setText("Synset Name:");
		textfield = new Text(dialog, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		textfield.setLayoutData(gridData);
		textfield.setToolTipText("Enter a unique Synset name");
		errorLabel = new Label(dialog, SWT.NONE);
		errorLabel.setText("");
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = GridData.CENTER;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		errorLabel.setLayoutData(gridData);
		errorLabel.setForeground(red);
		Button ok = new Button(dialog, SWT.PUSH);
		ok.setText(" Ok ");
		ok.setSize(50, 20);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.END;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = false;
		gridData.grabExcessVerticalSpace = false;
		ok.setLayoutData(gridData);
		Button cancel = new Button(dialog, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setSize(50, 20);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = false;
		gridData.grabExcessVerticalSpace = false;
		cancel.setLayoutData(gridData);

		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(unityCanvas.doesNameExist(textfield.getText())){
					errorLabel.setText(textfield.getText() + " already exists in vocabulary");
					dialog.pack(true);
					dialog.layout(true);
				} else {
					Term newTerm = new Term(unityCanvas.maxSafeID++,textfield.getText(),"");
					unityCanvas.getVocabulary().addTerm(newTerm);
					unityCanvas.getInvertedVocab().addTerm(newTerm);
					unityCanvas.getTableView().adjustTableSize(1);
					TableItem item = new TableItem(unityCanvas.getWorkspace().getTable(), SWT.NONE, unityCanvas.getWorkspace().getTable().getItemCount()); 
					item.setData("uid", unityCanvas.maxSafeID-1);
					unityCanvas.populateRow(item, unityCanvas.getWorkspace().showTextWorkspace);
					unityCanvas.getWorkspace().createRemoveButton();

					unityCanvas.updateTables(unityCanvas.maxSafeID-1);
					unityCanvas.getWorkspace().stackLayout.topControl = unityCanvas.getWorkspace().getTable();
					unityCanvas.getWorkspace().getTable().getParent().getParent().getParent().layout(true);

					dialog.close();

				}
			}
		});				
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dialog.close();
			}
		});				
		
		textfield.addKeyListener(new KeyListener() {
	        public void keyPressed(KeyEvent e) {
		        }
	        public void keyReleased(KeyEvent e) {
			    if (e.character == SWT.CR)
			    {
					if(unityCanvas.doesNameExist(textfield.getText())){
						errorLabel.setText(textfield.getText() + " already exists in vocabulary");
						dialog.pack(true);
						dialog.layout(true);
					} else {
						Term newTerm = new Term(unityCanvas.maxSafeID++,textfield.getText(),"");
						unityCanvas.getVocabulary().addTerm(newTerm);
						unityCanvas.getInvertedVocab().addTerm(newTerm);
						unityCanvas.getTableView().adjustTableSize(1);
						TableItem item = new TableItem(unityCanvas.getWorkspace().getTable(), SWT.NONE, unityCanvas.getWorkspace().getTable().getItemCount()); 
						item.setData("uid", unityCanvas.maxSafeID-1);
						unityCanvas.populateRow(item, unityCanvas.getWorkspace().showTextWorkspace);
						unityCanvas.getWorkspace().createRemoveButton();

						unityCanvas.updateTables(unityCanvas.maxSafeID-1);
						unityCanvas.getWorkspace().stackLayout.topControl = unityCanvas.getWorkspace().getTable();
						unityCanvas.getWorkspace().getTable().getParent().getParent().getParent().layout(true);

						dialog.close();
					}
		        }
	        }
	      });

		
		dialog.open();
		
	}
}