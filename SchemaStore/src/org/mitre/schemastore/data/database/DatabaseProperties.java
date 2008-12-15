// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.schemastore.data.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages the database properties in Schema Store
 * @author CWOLF
 */
public class DatabaseProperties
{
	// Patterns used to extract the database properties information
	static private Pattern serverPattern = Pattern.compile("<databaseServer>(.*?)</databaseServer>");
	static private Pattern namePattern = Pattern.compile("<databaseName>(.*?)</databaseName>");
	static private Pattern userPattern = Pattern.compile("<databaseUser>(.*?)</databaseUser>");
	static private Pattern passwordPattern = Pattern.compile("<databasePassword>(.*?)</databasePassword>");

	// Stores the database properties
	static private String databaseServer = null;
	static private String databaseName = null;
	static private String databaseUser = null;
	static private String databasePassword = null;

	/** Extracts database properties */
	static
	{
		// Load database properties from file
		try {
			// Pull the entire file into a string
			InputStream configStream = DatabaseProperties.class.getResourceAsStream("/schemastore.xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(configStream));
			StringBuffer buffer = new StringBuffer("");
			String line; while((line=in.readLine())!=null) buffer.append(line);
			in.close();

			// Parse out the database server
			Matcher serverMatcher = serverPattern.matcher(buffer);
			if(serverMatcher.find())
				databaseServer = serverMatcher.group(1);

			// Parse out the database name
			Matcher nameMatcher = namePattern.matcher(buffer);
			if(nameMatcher.find())
				databaseName = nameMatcher.group(1);

			// Parse out the database user
			Matcher userMatcher = userPattern.matcher(buffer);
			if(userMatcher.find())
				databaseUser = userMatcher.group(1);

			// Parse out the database password
			Matcher passwordMatcher = passwordPattern.matcher(buffer);
			if(passwordMatcher.find())
				databasePassword = passwordMatcher.group(1);
		}
		catch(IOException e)
			{ System.out.println("(E)DatabaseProperties - schemastore.xml has failed to load!\n"+e.getMessage()); }
	}

	/** Returns the database server */
	static public String getServer()
		{ return databaseServer; }

	/** Returns the database name */
	static public String getName()
		{ return databaseName; }

	/** Returns the database user */
	static public String getUser()
		{ return databaseUser; }

	/** Returns the database password */
	static public String getPassword()
		{ return databasePassword; }
}