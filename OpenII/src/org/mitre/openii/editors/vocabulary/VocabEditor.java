package org.mitre.openii.editors.vocabulary;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.mitre.openii.editors.OpenIIEditor;
import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Vocabulary;

/** Constructs the Harmony View */
public class VocabEditor extends OpenIIEditor {

	/** Displays the VocabView */
	public void createPartControl(final Composite parent) {
		try {
			Vocabulary vocabulary = OpenIIManager.getVocabulary(getElementID());
			new VocabViewCanvas(parent, SWT.NONE, vocabulary);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
