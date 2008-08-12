package org.openii.schemr.client;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

// TODO hook this up to the app

public class SchemrPreferencePage extends FieldEditorPreferencePage 
//implements IWorkbenchPreferencePage 
{


	@Override
	protected void createFieldEditors() {
		
		StringFieldEditor editor = new StringFieldEditor(PreferenceConstants.PREF_SCHEMA_STORE_URL, "Schema Store URL", getFieldEditorParent());
		addField(editor);
		
		PathEditor logEditor = new PathEditor(PreferenceConstants.PREF_LOG_FILE_PATH, "Log file path", "Log file path", getFieldEditorParent());
		addField(logEditor);
		
		PathEditor indexEditor = new PathEditor(PreferenceConstants.PREF_LOCAL_INDEX_PATH, "Index directory", "Index directory", getFieldEditorParent());
		addField(indexEditor);
	}

	public SchemrPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Schemr preferences");
	}
	
	public void init(IWorkbench workbench) {
		System.out.println("SchemrPreferencePage init call");
	}

}
