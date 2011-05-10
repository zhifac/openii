package org.mitre.openii.editors.unity;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Vocabulary;
import org.mitre.schemastore.model.VocabularyTerms;

/** Constructs the Harmony View */
public class UnityEditor extends OpenIIEditor {

	/** Displays the VocabView */
	public void createPartControl(final Composite parent) {
		try {
			VocabularyTerms terms = OpenIIManager.getVocabularyTerms(getElementID());
			new UnityCanvas(parent, SWT.NONE, terms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
