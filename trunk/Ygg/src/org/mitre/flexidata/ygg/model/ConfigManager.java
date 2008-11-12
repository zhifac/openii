// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.flexidata.ygg.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages configuration settings
 * @author CWOLF
 */
public class ConfigManager
{
	// Patterns used to extract tool information
	static private Pattern schemaStorePattern = Pattern.compile("<schemastore>(.*?)</schemastore>");
	
	/** Stores the schema store location */
	static private String schemaStoreLoc = null;
	
	/** Initializes the configuration settings */
	static
	{		
		// Load tool information from file
		try {			
			// Pull the entire file into a string
			InputStream configStream = new ConfigManager().getClass().getResourceAsStream("/config.xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(configStream));
			StringBuffer buffer = new StringBuffer("");
			String line; while((line=in.readLine())!=null) buffer.append(line);
			in.close();
			
			// Parse out the schema store location
			Matcher schemaStoreMatcher = schemaStorePattern.matcher(buffer);
			schemaStoreLoc = schemaStoreMatcher.find() ? schemaStoreMatcher.group(1) : "";
		}
		catch(IOException e)
			{ System.out.println("(E)ConfigManager - Config.xml has failed to load!\n"+e.getMessage()); }
	}
	
	/** Returns the schema store location */
	static public String getSchemaStoreLoc()
		{ return schemaStoreLoc; }
}
