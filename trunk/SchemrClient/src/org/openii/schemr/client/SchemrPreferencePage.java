package org.openii.schemr.client;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

// TODO hook this up to the app

public class SchemrPreferencePage extends FieldEditorPreferencePage {

	public static final String PREF_SCHEMA_STORE_URL = "SchemaStoreURL";

	@Override
	protected void createFieldEditors() {
		
		StringFieldEditor editor = new StringFieldEditor(PREF_SCHEMA_STORE_URL, "Schema Store URL", getFieldEditorParent());
		addField(editor);
		
	}


}
