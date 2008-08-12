package org.openii.schemr;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.mitre.schemastore.client.SchemaStoreClient;
import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Entity;
import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.model.Subtype;
import org.openii.schemr.client.Activator;
import org.openii.schemr.client.PreferenceConstants;

public class SchemaUtility {
	
	private static File _pluginFolder = null;
	@SuppressWarnings("deprecation")
	private static File getPluginFolder() {
		if (_pluginFolder == null) {
			URL url = Platform.getBundle(Activator.PLUGIN_ID).getEntry("/");
			try {
				url = Platform.resolve(url);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			_pluginFolder = new File(url.getPath());
			System.out.println("Schemr Client working directory: "+_pluginFolder.getAbsolutePath());
		}
		return _pluginFolder;
	}
	
	public static final String LOCAL_INDEX_DIR = getPluginFolder().getPath()+File.separator+"index";
	public static final String LOG_FILE_PATH = getPluginFolder().getPath()+File.separator+"schemr.log";
	public static final String SCHEMA_STORE_URL_DEFAULT = "http://localhost:8080/SchemaStore/services/SchemaStore";
	public static String SCHEMA_STORE_URL = SCHEMA_STORE_URL_DEFAULT;
	private static SchemaStoreClient CLIENT = null;

	static {

//			String index = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.PREF_LOCAL_INDEX_PATH);
//			if (index != null && index.equals("")) {
//				LOCAL_INDEX_DIR = index;
//			}
//			Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.PREF_LOG_FILE_PATH);
//			String log = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.PREF_LOG_FILE_PATH);
//			if (log != null && log.equals("")) {
//				LOG_FILE_PATH = log;
//			}
	}

	public static SchemaStoreClient getCLIENT() {
		if (CLIENT == null) {
			// TODO: fix me, this doesn't work right now...
			String url = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.PREF_SCHEMA_STORE_URL);
			if (url != null && url.equals("")) {
				SCHEMA_STORE_URL = url;
				System.out.println("SCHEMA STORE SERVER initialized from preference setting to "+SCHEMA_STORE_URL);
			} else {
				System.out.println("SCHEMA STORE SERVER initialized to default "+SCHEMA_STORE_URL);	
			}		
			CLIENT = new SchemaStoreClient(SCHEMA_STORE_URL);
			System.out.println("SchemaStoreClient created");
		}
		return CLIENT;
	}
	
	public static final String ENTITY = "entity";
	public static final String ATTRIBUTE = "attribute";
	public static final String DOMAIN = "domain";
	public static final String DOMAIN_VALUE = "domain_value";
	public static final String RELATIONSHIP = "relationship";
	public static final String CONTAINMENT = "containment";
	public static final String SUBTYPE = "subtype";
	public static final String ALIAS = "alias";
	public static final String SCHEMA = "schema";

	
	public static String getType(SchemaElement schemaElement) {
		if (schemaElement instanceof Entity) return ENTITY;
		else if (schemaElement instanceof Attribute) return ATTRIBUTE;
		else if (schemaElement instanceof Domain) return DOMAIN;
		else if (schemaElement instanceof DomainValue) return DOMAIN_VALUE;
		else if (schemaElement instanceof Relationship) return RELATIONSHIP;
		else if (schemaElement instanceof Containment) return CONTAINMENT;
		else if (schemaElement instanceof Subtype) return SUBTYPE;
		else if (schemaElement instanceof Alias) return ALIAS;
		else throw new IllegalArgumentException("Unexpected SchemaElement type");
	}

}
