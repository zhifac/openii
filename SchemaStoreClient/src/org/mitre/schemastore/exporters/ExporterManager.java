//Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.exporters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mitre.schemastore.client.SchemaStoreClient;

/** API for dealing with exporters */
public class ExporterManager
{
	// Patterns used to extract exporter information
	static private Pattern exportersPattern = Pattern.compile("<exporters>(.*?)</exporters>");
	static private Pattern exporterPattern = Pattern.compile("<exporter>(.*?)</exporter>");
	
	/** Stores a listing of exporters */
	private ArrayList<Exporter> exporters = new ArrayList<Exporter>();

	/** Initializes the available exporters */
	private void initializeExporters(SchemaStoreClient client)
	{		
		// Load exporters from file
		try {			
			// Pull the entire file into a string
			InputStream configStream = ExporterManager.class.getResourceAsStream("/exporters.xml");
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
						Exporter ep = (Exporter)Class.forName(exporterMatcher.group(1)).newInstance();
						ep.setClient(client);
						exporters.add(ep);
					} catch(Exception e) {}
			}
		}
		catch(IOException e)
			{ System.out.println("(E)Exporters - schemastoreclient.xml has failed to load!\n"+e.getMessage()); }
	}
	
	/** Constructs the exporters */
	public ExporterManager(SchemaStoreClient client)
		{ initializeExporters(client); }
	
	/** Returns the list of exporters */
	public ArrayList<Exporter> getExporters(String fileType)
	{
		ArrayList<Exporter> fileExporters = new ArrayList<Exporter>();
		for(Exporter exporter : exporters)
			if(fileType==null || exporter.getFileType().equals(fileType))
				fileExporters.add(exporter);
		return fileExporters;
	}
	
	/** Returns the specified exporter */
	public Exporter getExporter(Class<?> type)
	{
		for(Exporter exporter : exporters)
			if(exporter.getClass().equals(type))
				return exporter;
		return null;
	}
}