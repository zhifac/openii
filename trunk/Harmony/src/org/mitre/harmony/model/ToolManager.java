// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages all of the configuration settings available within Harmony
 * @author CWOLF
 */
public class ToolManager
{
	// Patterns used to extract configuration information
	static private Pattern toolPattern = Pattern.compile("<tools>(.*?)</tools>");
	static private Pattern parmPattern = Pattern.compile("<(.*?)>(.*?)</\\1>");
	
	/** Private class for managing tools */
	static private class Tools
	{
		/** Stores all of the tools */
		private HashMap<String,ArrayList<String>> tools = new HashMap<String,ArrayList<String>>();
		
		/** Extracts tool */
		private void extractTool(String toolString)
		{
			Matcher parmMatcher = parmPattern.matcher(toolString);
			while(parmMatcher.find())
			{
				String toolType = parmMatcher.group(1);
				String tool = parmMatcher.group(2);
				ArrayList<String> toolList = tools.get(toolType);
				if(toolList==null) tools.put(toolType,toolList = new ArrayList<String>());
				toolList.add(tool);
			}
		}
		
		/** Constructs the tool class */
		private Tools(String tools)
		{
			Matcher parmMatcher = parmPattern.matcher(tools);
			while(parmMatcher.find())
				extractTool(parmMatcher.group(2));				
		}
		
		/** Returns the list of tools for the specified tool type */
		private ArrayList<String> get(String toolType)
			{ return tools.get(toolType); }
		
		/** Displays the tools in XML format */
		public String toString()
		{
			StringBuffer output = new StringBuffer();
			for(String toolType : tools.keySet())
			{
				output.append("  <"+toolType+"s>\n");
				for(String tool : tools.get(toolType))
					output.append("    <"+toolType+">"+tool+"</"+toolType+">");
				output.append("  </"+toolType+"s>\n");				
			}
			return output.toString();
		}
	}

	/** Stores a listing of tools */
	static private Tools tools = null;
	
	/** Initializes the tool manager with all defined tools */
	static
	{		
		// Load tool information from file
		try {
			// Pull the entire file into a string
			InputStream configStream = new ToolManager().getClass().getResourceAsStream("/harmony.xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(configStream));	
			StringBuffer buffer = new StringBuffer("");
			String line; while((line=in.readLine())!=null) buffer.append(line);
			in.close();

			// Parse out the tools
			java.util.regex.Matcher toolMatcher = toolPattern.matcher(buffer);
			String toolString = toolMatcher.find() ? toolMatcher.group(1) : "";
			tools = new Tools(toolString);
		}
		catch(IOException e)
			{ System.out.println("(E)configManager - The configuration settings have failed to load!\n"+e.getMessage()); }
	}
	
	/** Returns the list of tools for the specified tool type */
	static public ArrayList<String> getTools(String toolType)
		{ return tools.get(toolType); }
}