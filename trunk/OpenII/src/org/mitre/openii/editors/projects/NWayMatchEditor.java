	package org.mitre.openii.editors.projects;



import org.eclipse.swt.SWT;
	import org.eclipse.swt.widgets.Composite;
	import org.mitre.openii.editors.OpenIIEditor;
	import org.mitre.openii.model.EditorInput;
	import org.mitre.schemastore.model.Project;
import org.mitre.affinity.view.vocab_debug_view.view.swt.SWTVocabDebugView;
	
public class NWayMatchEditor extends OpenIIEditor{
		

		/** Displays the VocabDebugView */ @SuppressWarnings("unchecked")
		public void createPartControl(final Composite parent)
		{
			Object object = ((EditorInput)getEditorInput()).getElement();
			if(object instanceof Project){
				Project proj = (Project)object;
				
				//VocabDebugViewSets vdvs = new VocabDebugViewSets(**fill in data**);
				//view results
				//new SWTVocabDebugView(parent, SWT.NONE, null);
				
			}
				
		}

}


