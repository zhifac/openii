package org.openii.schemr.client;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import org.openii.schemr.SchemaUtility;
import org.openii.schemr.client.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.PREF_SCHEMA_STORE_URL, SchemaUtility.SCHEMA_STORE_URL);
		store.setDefault(PreferenceConstants.PREF_LOG_FILE_PATH, SchemaUtility.LOG_FILE_PATH);
		store.setDefault(PreferenceConstants.PREF_LOCAL_INDEX_PATH, SchemaUtility.LOCAL_INDEX_DIR);
	}

}
