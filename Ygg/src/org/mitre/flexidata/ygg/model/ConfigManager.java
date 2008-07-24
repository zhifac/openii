// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mitre.flexidata.ygg.exporters.Exporter;
import org.mitre.flexidata.ygg.importers.Importer;

/**
 * Manages configuration settings
 * @author CWOLF
 */
public class ConfigManager
{
	// Patterns used to extract tool information
	static private Pattern schemaStorePattern = Pattern.compile("<schemastore>(.*?)</schemastore>");
	static private Pattern importersPattern = Pattern.compile("<importers>(.*?)</importers>");
	static private Pattern importerPattern = Pattern.compile("<importer>(.*?)</importer>");
	static private Pattern exportersPattern = Pattern.compile("<exporters>(.*?)</exporters>");
	static private Pattern exporterPattern = Pattern.compile("<exporter>(.*?)</exporter>");
	
	/** Stores the schema store location */
	static private String schemaStoreLoc = null;
	
	/** Stores a listing of importers */
	static private ArrayList<Importer> importers = new ArrayList<Importer>();
	
	/** Stores a listing of exporters */
	static private ArrayList<Exporter> exporters = new ArrayList<Exporter>();
	
	/** Initializes the configuration settings */
	static
	{		
		// Load tool information from file
		try {			
			// Pull the entire file into a string
			InputStream configStream = new ConfigManager().getClass().getResourceAsStream("config.xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(configStream));
			StringBuffer buffer = new StringBuffer("");
			String line; while((line=in.readLine())!=null) buffer.append(line);
			in.close();
			
			// Parse out the schema store location
			Matcher schemaStoreMatcher = schemaStorePattern.matcher(buffer);
			schemaStoreLoc = schemaStoreMatcher.find() ? schemaStoreMatcher.group(1) : "";
			
			// Parse out the importers
			Matcher importersMatcher = importersPattern.matcher(buffer);
			while(importersMatcher.find())
			{
				Matcher importerMatcher = importerPattern.matcher(importersMatcher.group(1));
				while(importerMatcher.find())
					try {
						importers.add((Importer)Class.forName(importerMatcher.group(1)).newInstance());
					} catch(Exception e) {}
			}
			
			// Parse out the exporters
			Matcher exportersMatcher = exportersPattern.matcher(buffer);
			while(exportersMatcher.find())
			{
				Matcher exporterMatcher = exporterPattern.matcher(exportersMatcher.group(1));
				while(exporterMatcher.find())
					try {
						exporters.add((Exporter)Class.forName(exporterMatcher.group(1)).newInstance());
					} catch(Exception e) {}
			}
		}
		catch(IOException e)
			{ System.out.println("(E)ConfigManager - Config.xml has failed to load!\n"+e.getMessage()); }
	}
	
	/** Returns the schema store location */
	static public String getSchemaStoreLoc()
		{ return schemaStoreLoc; }
	
	/** Returns the list of importers */
	static public ArrayList<Importer> getImporters()
		{ return importers; }
	
	/** Returns the list of exporters */
	static public ArrayList<Exporter> getExporters()
		{ return exporters; }
	
	/** Sets the schema store location */
	static public void setSchemaStoreLoc(String schemaStoreLocIn)
		{ schemaStoreLoc = schemaStoreLocIn; }
}
