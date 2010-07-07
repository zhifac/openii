package org.mitre.openii.editors.projects;

import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.EditorInput;
import org.mitre.schemastore.model.Project;
	
public class NWayMatchEditor extends OpenIIEditor{
	public void createPartControl(final Composite parent) {
		Object object = ((EditorInput)getEditorInput()).getElement();
		if(object instanceof Project){
			Project proj = (Project)object;
				
			//VocabDebugViewSets vdvs = new VocabDebugViewSets(**fill in data**);
			//view results
			//new SWTVocabDebugView(parent, SWT.NONE, null);
		}
	}
}


