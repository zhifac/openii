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

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Annotation;
import org.mitre.schemastore.model.terms.VocabularyTerms;

public class SaveListener implements Listener {

    private UnityCanvas unityCanvas;


	public SaveListener(UnityCanvas unity) {
		unityCanvas = unity;
	}
	
	public void handleEvent(Event e) {
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
		for(int a = 0; a < annoList.size(); a++){
			System.err.println("element = " + annoList.get(a).getElementID());
			System.err.println("project = " + annoList.get(a).getGroupID());
			System.err.println("attribute = " + annoList.get(a).getAttribute());
		}
		OpenIIManager.setAnnotations(annoList);
		unityCanvas.needSave(false);
	}
}