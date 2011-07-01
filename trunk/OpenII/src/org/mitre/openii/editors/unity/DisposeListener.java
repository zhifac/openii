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

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Annotation;
import org.mitre.schemastore.model.terms.VocabularyTerms;

public class DisposeListener implements Listener {

    private UnityCanvas unityCanvas;
    private Shell dialog;


	public DisposeListener(UnityCanvas unity) {
		unityCanvas = unity;
	}
	
	public void handleEvent(Event e) {
		//event.doit = false; SWT bugged
  		if(unityCanvas.needSave()){  			
			GridLayout dialogLayout = new GridLayout(2, true);
			dialog = new Shell(unityCanvas.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);			
			dialog.setLayout(dialogLayout);
			dialog.setText("Exiting Unity");
			dialog.setSize(200, 120);
			dialog.setLocation(unityCanvas.getCenter());
			Label nameLabel = new Label(dialog, SWT.NONE);
			nameLabel.setText("Vocabulary has been changed since last save");
			GridData gridData = new GridData();
			gridData.horizontalSpan = 2;
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace = true;
			gridData.grabExcessVerticalSpace = false;
			nameLabel.setLayoutData(gridData);
			Label nameLabel2 = new Label(dialog, SWT.NONE);
			nameLabel2.setText("Would you like to save?");
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
					VocabularyTerms oldVocab = unityCanvas.getVocabulary();
					unityCanvas.setVocabulary(OpenIIManager.saveVocabularyTerms(unityCanvas.getVocabulary()));
					//go through each item and update stuff if ID changed
					Integer oldID = 0;
					Integer newID = 0;
					for(int i = 0; i < unityCanvas.getVocabulary().getTerms().length;i++)
					{
						oldID = oldVocab.getTerms()[i].getId();
						newID = unityCanvas.getVocabulary().getTerms()[i].getId();
						if(unityCanvas.maxSafeID <= newID) unityCanvas.maxSafeID = newID+1;

						if(newID != oldID) {
							if(unityCanvas.getCheckStatus().containsKey(oldID)) {
								Annotation anno = unityCanvas.getCheckStatus().remove(oldID);
								anno.setElementID(newID);
								unityCanvas.getCheckStatus().put(newID, anno);
							}
							if(unityCanvas.draggedRow == oldID)
							{
								unityCanvas.draggedRow = newID;
							}
							if(unityCanvas.getEvidencePane().evidenceSID == oldID)
							{
								unityCanvas.getEvidencePane().evidenceSID = newID;
							}
							if(unityCanvas.getCloseMatchPane().closeMatchSID == oldID)
							{
								unityCanvas.getCloseMatchPane().closeMatchSID = newID;
							}
							if(unityCanvas.getContextPane().contextSID == oldID)
							{
								unityCanvas.getContextPane().contextSID = newID;
							}
							if(unityCanvas.detailSID == oldID)
							{
								unityCanvas.detailSID = newID;
							}
						}
					}
					OpenIIManager.clearAnnotations(unityCanvas.getVocabulary().getProjectID(), "checked");
					ArrayList<Annotation> annoList = new ArrayList<Annotation>(unityCanvas.getCheckStatus().values());
					OpenIIManager.setAnnotations(annoList);
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