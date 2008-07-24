//Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.api;

import java.util.ArrayList;

import org.mitre.flexidata.ygg.exporters.Exporter;
import org.mitre.flexidata.ygg.importers.Importer;
import org.mitre.flexidata.ygg.model.ConfigManager;

/**
 * The Ygg API
 * @author CWOLF
 */
public class YggAPI
{
	/** Constructs the YggAPI */
	public YggAPI(String schemaStoreLoc)
		{ ConfigManager.setSchemaStoreLoc(schemaStoreLoc); }
	
	/** Returns the list of importers */
	public ArrayList<Importer> getImporters()
		{ return ConfigManager.getImporters(); }
	
	/** Returns the list of exporters */
	public ArrayList<Exporter> getExporters()
		{ return ConfigManager.getExporters(); }
}