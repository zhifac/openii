// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mitre.harmony.matchers.mergers.MatchMerger;
import org.mitre.harmony.matchers.voters.MatchVoter;
import org.mitre.schemastore.model.schemaInfo.FilteredSchemaInfo;
import org.mitre.schemastore.porters.PorterList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Manages the Harmony Matchers
 * @author CWOLF
 */
public class MatcherManager {
	/** Stores a listing of all match voters */
	static private ArrayList<MatchVoter> voters = new ArrayList<MatchVoter>();

	/** Stores a listing of all match mergers */
	static private ArrayList<MatchMerger> mergers = new ArrayList<MatchMerger>();
	
	/** Initializes the matcher manager with all defined match voters and mergers */
	static {
		try {
			// using factory get an instance of document builder
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get dom representation of the xml file
			Document dom = db.parse(PorterList.class.getResourceAsStream("/matchers.xml"));

			// get the root element
			Element root = dom.getDocumentElement();

			// get all voters
			{
				NodeList nodes = root.getElementsByTagName("voter");
				if (nodes != null && nodes.getLength() > 0) {
					for (int i = 0; i < nodes.getLength(); i++) {
						// get the element and its attribute
						Element node = (Element)nodes.item(i);

						try {
							// create a new voter from this class id
							Class voterClass = Class.forName(node.getAttribute("id"));
							MatchVoter voter = (MatchVoter)voterClass.newInstance();
	
							// see if our voter is hidden or default
							String attrHidden = node.getAttribute("hidden");
							if (attrHidden.toLowerCase().equals("true")) { System.err.println("hidden is true"); voter.setHidden(true); }
							String attrDefault = node.getAttribute("default");
							if (attrDefault.toLowerCase().equals("true")) { System.err.println("default is true"); voter.setDefault(true); }

							// add the voter to our master list
							voters.add(voter);
						} catch (Exception ee) {
							System.err.println("(E) MatchManager - Failed to locate voter class " + node.getAttribute("id"));
						}
					}
				}
			}

			// get all merger nodes
			{
				NodeList nodes = root.getElementsByTagName("merger");
				if (nodes != null && nodes.getLength() > 0) {
					for (int i = 0; i < nodes.getLength(); i++) {
						// get the element and its attribute
						Element node = (Element)nodes.item(i);

						try {
							// create a new voter from this class id
							Class mergerClass = Class.forName(node.getAttribute("id"));
							MatchMerger merger = (MatchMerger)mergerClass.newInstance();
	
							// add the voter to our master list
							mergers.add(merger);
						} catch (Exception ee) {
							System.err.println("(E) MatchManager - Failed to locate merger class " + node.getAttribute("id"));
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println("(E) MatcherManager - matchers.xml has failed to load! " + e.getMessage());
		}
	}

	/** Returns the list of match voters */
	static public ArrayList<MatchVoter> getVoters() {
		return voters;
	}

	/** Returns the list of default match voters */
	static public ArrayList<MatchVoter> getDefaultVoters() {
		ArrayList<MatchVoter> defaultVoters = new ArrayList<MatchVoter>();
		for(MatchVoter voter : voters) {
			if (voter.isDefault()) {
				defaultVoters.add(voter);
			}
		}
		return defaultVoters;
	}

	/** Returns the list of visible match voters */
	static public ArrayList<MatchVoter> getVisibleVoters() {
		ArrayList<MatchVoter> visibleVoters = new ArrayList<MatchVoter>();
		for (MatchVoter voter : voters) {
			if (!voter.isHidden()) {
				visibleVoters.add(voter);
			}
		}
		return visibleVoters;
	}
	
	/** Returns the list of match mergers */
	static public ArrayList<MatchMerger> getMergers() {
		return mergers;
	}

	/** Run the matchers to calculate match scores */
	static public MatchScores getScores(FilteredSchemaInfo schemaInfo1, FilteredSchemaInfo schemaInfo2, ArrayList<MatchVoter> voters, MatchMerger merger) {
		return getScores(schemaInfo1, schemaInfo2, voters, merger, null);
	}
	
	/** Run the matchers to calculate match scores */
	static public MatchScores getScores(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2, ArrayList<MatchVoter> voters, MatchMerger merger, MatchTypeMappings typeMappings) {
		merger.initialize(schema1, schema2, typeMappings);
		for (MatchVoter voter : voters) {
			voter.initialize(schema1, schema2, typeMappings);
			merger.addVoterScores(voter.match());
		}
		return merger.getMatchScores();
	}
}