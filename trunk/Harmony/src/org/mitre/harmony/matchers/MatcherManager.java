// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mitre.harmony.matchers.matchers.Matcher;
import org.mitre.harmony.matchers.mergers.MatchMerger;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.porters.PorterList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Manages the Harmony Matchers
 * @author CWOLF
 */
public class MatcherManager
{
	/** Stores a listing of all match */
	static private ArrayList<Matcher> matchers = new ArrayList<Matcher>();

	/** Stores a listing of all match mergers */
	static private ArrayList<MatchMerger> mergers = new ArrayList<MatchMerger>();

	/** Initializes the matcher manager with all defined matchers and mergers */
	static
	{
		try {
			// using factory get an instance of document builder
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get dom representation of the xml file
			Document dom = db.parse(PorterList.class.getResourceAsStream("/matchers.xml"));

			// get the root element
			Element root = dom.getDocumentElement();

			// get all matchers
			NodeList nodes = root.getElementsByTagName("matcher");
			if(nodes != null && nodes.getLength() > 0)
			{
				for(int i = 0; i < nodes.getLength(); i++)
				{
					// get the element and its attribute
					Element node = (Element)nodes.item(i);
					try {
						// create a new matcher from this class id
						Class matcherClass = Class.forName(node.getAttribute("id"));
						Matcher matcher = (Matcher)matcherClass.newInstance();

						// see if our matcher is hidden or default
						String attrHidden = node.getAttribute("hidden");
						if(attrHidden.toLowerCase().equals("true")) matcher.setHidden(true);
						String attrDefault = node.getAttribute("default");
						if(attrDefault.toLowerCase().equals("true")) matcher.setDefault(true);

						// Retrieve the options
						NodeList optionNodes = node.getElementsByTagName("option");
						if(optionNodes != null && optionNodes.getLength() > 0)
						{
							for(int j = 0; j < optionNodes.getLength(); j++)
							{
								Element optionNode = (Element)optionNodes.item(j);
								String name = optionNode.getAttribute("name");
								String value = optionNode.getAttribute("value");
								matcher.setOption(name, value);
							}
						}

						// Store the matcher
						matchers.add(matcher);
					}
					catch (Exception ee) { System.err.println("(E) MatcherManager - Failed to locate matcher class: " + ee.getMessage()); }
				}
			}

			// get all merger nodes
			nodes = root.getElementsByTagName("merger");
			if(nodes != null && nodes.getLength() > 0)
				for(int i = 0; i < nodes.getLength(); i++)
				{
					// get the element and its attribute
					Element node = (Element)nodes.item(i);

					try {
						// create a new merger from this class id
						Class mergerClass = Class.forName(node.getAttribute("id"));
						MatchMerger merger = (MatchMerger)mergerClass.newInstance();

						// add the merger to our master list
						mergers.add(merger);
					}
					catch (Exception ee) { System.err.println("(E) MatcherManager - Failed to locate merger class " + node.getAttribute("id")); }
				}
		}
		catch (Exception e) { System.err.println("(E) MatcherManager - matchers.xml has failed to load! " + e.getMessage()); }
	}

	/** Returns the list of match matchers */
	static public ArrayList<Matcher> getMatchers()
		{ return matchers; }

	/** Returns the list of default matchers */
	static public ArrayList<Matcher> getDefaultMatchers()
	{
		ArrayList<Matcher> defaultMatchers = new ArrayList<Matcher>();
		for(Matcher matcher : matchers)
			if(matcher.isDefault()) defaultMatchers.add(matcher);
		return defaultMatchers;
	}

	/** Returns the list of visible matchers */
	static public ArrayList<Matcher> getVisibleMatchers()
	{
		ArrayList<Matcher> visibleMatchers = new ArrayList<Matcher>();
		for(Matcher matcher : matchers)
			if (!matcher.isHidden()) visibleMatchers.add(matcher);
		return visibleMatchers;
	}
	
	/** Returns the list of match mergers */
	static public ArrayList<MatchMerger> getMergers()
		{ return mergers; }

	/** Return the details about one matcher */
	static public Matcher getMatcher(String id)
	{
		for(int i = 0; i < matchers.size(); i++)
			if(matchers.get(i).getClass().getName().equals(id))
				return matchers.get(i);
		return null;
	}

	/** Run the matchers to calculate match scores */
	static public MatchScores getScores(FilteredSchemaInfo schemaInfo1, FilteredSchemaInfo schemaInfo2, ArrayList<Matcher> matchers, MatchMerger merger)
		{ return getScores(schemaInfo1, schemaInfo2, matchers, merger, null); }
	
	/** Run the matchers to calculate match scores */
	static public MatchScores getScores(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2, ArrayList<Matcher> matchers, MatchMerger merger, MatchTypeMappings typeMappings)
	{
		merger.initialize(schema1, schema2, typeMappings);
		for(Matcher matcher : matchers)
		{
			matcher.initialize(schema1, schema2, typeMappings);
			merger.addMatcherScores(matcher.match());
		}
		return merger.getMatchScores();
	}
}