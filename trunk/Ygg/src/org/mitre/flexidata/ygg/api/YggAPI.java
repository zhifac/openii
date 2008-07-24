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
	public ArrayList<Importer> getImporters(String fileType)
	{
		ArrayList<Importer> importers = new ArrayList<Importer>();
		for(Importer importer : ConfigManager.getImporters())
			if(fileType==null || importer.getFileTypes().contains(fileType))
				importers.add(importer);
		return importers;
	}
	
	/** Returns the list of exporters */
	public ArrayList<Exporter> getExporters(String fileType)
	{
		ArrayList<Exporter> exporters = new ArrayList<Exporter>();
		for(Exporter exporter : ConfigManager.getExporters())
			if(fileType==null || exporter.getFileType().equals(fileType))
				exporters.add(exporter);
		return exporters;
	}
}