//Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.exporters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** API for dealing with exporters */
public class Exporters
{
	// Patterns used to extract exporter information
	static private Pattern exportersPattern = Pattern.compile("<exporters>(.*?)</exporters>");
	static private Pattern exporterPattern = Pattern.compile("<exporter>(.*?)</exporter>");
	
	/** Stores a listing of exporters */
	private ArrayList<Exporter> exporters = new ArrayList<Exporter>();

	/** Initializes the available exporters */
	private void initializeExporters()
	{		
		// Load exporters from file
		try {			
			// Pull the entire file into a string
			InputStream configStream = Exporters.class.getResourceAsStream("/porters.xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(configStream));
			StringBuffer buffer = new StringBuffer("");
			String line; while((line=in.readLine())!=null) buffer.append(line);
			in.close();
			
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
			{ System.out.println("(E)Exporters - config.xml has failed to load!\n"+e.getMessage()); }
	}
	
	/** Constructs the exporters */
	public Exporters()
		{ initializeExporters(); }
	
	/** Returns the list of exporters */
	public ArrayList<Exporter> getExporters(String fileType)
	{
		ArrayList<Exporter> fileExporters = new ArrayList<Exporter>();
		for(Exporter exporter : exporters)
			if(fileType==null || exporter.getFileType().equals(fileType))
				fileExporters.add(exporter);
		return fileExporters;
	}
}