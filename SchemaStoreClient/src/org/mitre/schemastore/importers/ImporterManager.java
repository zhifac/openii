/*
 *  Copyright 2008 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.schemastore.importers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mitre.schemastore.client.SchemaStoreClient;

/** API for dealing with importers */
public class ImporterManager
{
	// Patterns used to extract importer information
	static private Pattern importersPattern = Pattern.compile("<importers>(.*?)</importers>");
	static private Pattern importerPattern = Pattern.compile("<importer>(.*?)</importer>");
	
	/** Stores a listing of importers */
	private ArrayList<Importer> importers = new ArrayList<Importer>();

	/** Initializes the available importers */
	private void initializeImporters(SchemaStoreClient client)
	{		
		// Load importers from file
		try {			
			// Pull the entire file into a string
			InputStream configStream = ImporterManager.class.getResourceAsStream("/importers.xml");
			System.out.println("configStream " + String.valueOf(configStream));
			BufferedReader in = new BufferedReader(new InputStreamReader(configStream));
			StringBuffer buffer = new StringBuffer("");
			String line; while((line=in.readLine())!=null) buffer.append(line);
			in.close();
			
			// Parse out the importers
			Matcher importersMatcher = importersPattern.matcher(buffer);
			while(importersMatcher.find())
			{
				Matcher importerMatcher = importerPattern.matcher(importersMatcher.group(1));
				while(importerMatcher.find())
					try {
						Importer importer = (Importer)Class.forName(importerMatcher.group(1)).newInstance();
						importer.setClient(client);
						importers.add(importer);
					} catch(Exception e) {}
			}
		}
		catch(IOException e)
			{ System.out.println("(E)Importers - schemastoreclient.xml has failed to load!\n"+e.getMessage()); }
	}
	
	/** Constructs the importers */
	public ImporterManager(SchemaStoreClient client)
		{ initializeImporters(client); }
	
	/** Returns the list of importers */
	public ArrayList<Importer> getImporters(String fileType)
	{
		ArrayList<Importer> fileImporters = new ArrayList<Importer>();
		for(Importer importer : importers)
			if(fileType==null || importer.getFileTypes().contains(fileType))
				fileImporters.add(importer);
		return fileImporters;
	}
	
	/** Returns the specified importer */
	public Importer getImporter(Class<?> type)
	{
		for(Importer importer : importers)
			if(importer.getClass().equals(type))
				return importer;
		return null;
	}
	
	/** Returns the specified importer - the argument must match the self-defined name of the Importer exactly */
	public Importer getImporter(String type)
	{
		for(Importer importer : importers)
			if(importer.getName().equals(type))
				return importer;
		return null;
	}
}