// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mitre.harmony.matchers.mergers.MatchMerger;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.porters.PorterList;

/**
 * Manages the Harmony Matchers
 * @author CWOLF
 */
public class MatcherManager
{
	// Patterns used to extract voter and merger information
	static private Pattern voterPattern = Pattern.compile("<voter( .*?)?>(.*?)</voter>");
	static private Pattern mergerPattern = Pattern.compile("<merger>(.*?)</merger>");
	
	/** Stores a listing of all match voters */
	static private ArrayList<MatchVoter> voters = new ArrayList<MatchVoter>();

	/** Stores a listing of all match mergers */
	static private ArrayList<MatchMerger> mergers = new ArrayList<MatchMerger>();
	
	/** Initializes the matcher manager with all defined match voters and mergers */
	static
	{
		// Retrieve matcher configuration file
		StringBuffer buffer = new StringBuffer("");
		try {
			InputStream configStream = PorterList.class.getResourceAsStream("/matchers.xml");
			BufferedReader in = new BufferedReader(new InputStreamReader(configStream));
			String line; while((line=in.readLine())!=null) buffer.append(line);
			in.close();
		}
		catch(IOException e)
			{ System.out.println("(E)PorterManager - porters.xml has failed to load!\n"+e.getMessage()); }
		
		// Retrieve the match voters
		Matcher voterMatcher = voterPattern.matcher(buffer);
		while(voterMatcher.find())
			try {
				Class voterClass = Class.forName(voterMatcher.group(2));
				MatchVoter voter = (MatchVoter)voterClass.newInstance();
				String attrs = voterMatcher.group(1);
				if(attrs!=null && attrs.contains("default='true'")) voter.setDefault(true);
				if(attrs!=null && attrs.contains("hidden='true'")) voter.setHidden(true);
				voters.add(voter);
			}
		    catch(Exception e) { System.out.println("(E)MatcherManager - Failed to locate voter class "+voterMatcher.group(1)); }

		// Retrieve match mergers
		Matcher mergerMatcher = mergerPattern.matcher(buffer);
		while(mergerMatcher.find())
			try {
				Class mergerClass = Class.forName(mergerMatcher.group(1));
				mergers.add((MatchMerger)mergerClass.newInstance());
			}
		    catch(Exception e) { System.out.println("(E)MatcherManager - Failed to locate merger class "+mergerMatcher.group(1)); }
	}

	/** Returns the list of match voters */
	static public ArrayList<MatchVoter> getVoters()
		{ return voters; }

	/** Returns the list of default match voters */
	static public ArrayList<MatchVoter> getDefaultVoters()
	{
		ArrayList<MatchVoter> defaultVoters = new ArrayList<MatchVoter>();
		for(MatchVoter voter : voters)
			if(voter.isDefault()) defaultVoters.add(voter);
		return defaultVoters;
	}

	/** Returns the list of visible match voters */
	static public ArrayList<MatchVoter> getVisibleVoters()
	{
		ArrayList<MatchVoter> visibleVoters = new ArrayList<MatchVoter>();
		for(MatchVoter voter : voters)
			if(!voter.isHidden()) visibleVoters.add(voter);
		return visibleVoters;
	}
	
	/** Returns the list of match mergers */
	static public ArrayList<MatchMerger> getMergers()
		{ return mergers; }

	/** Run the matchers to calculate match scores */
	static public MatchScores getScores(FilteredSchemaInfo schemaInfo1, FilteredSchemaInfo schemaInfo2, ArrayList<MatchVoter> voters, MatchMerger merger)
		{ return getScores(schemaInfo1,schemaInfo2,voters,merger,null); }
	
	/** Run the matchers to calculate match scores */
	static public MatchScores getScores(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2, ArrayList<MatchVoter> voters, MatchMerger merger, TypeMappings typeMappings)
	{
		merger.initialize(schema1, schema2, typeMappings);
		for(MatchVoter voter : voters)
		{
			voter.initialize(schema1, schema2, typeMappings);
			merger.addVoterScores(voter.match());
		}
		return merger.getMatchScores();
	}
}