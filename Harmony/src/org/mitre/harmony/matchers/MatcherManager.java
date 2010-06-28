// (c) The MITRE Corporation 2006
// ALL RIGHTS RESERVED
package org.mitre.harmony.matchers;

import java.util.ArrayList;
import java.util.HashMap;

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

	/** Stores a listing of all match voter options */
	static private HashMap<String, ArrayList<MatcherOption>> voterOptions = new HashMap<String, ArrayList<MatcherOption>>();

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

						MatchVoter voter = null;
						try {
							// create a new voter from this class id
							Class voterClass = Class.forName(node.getAttribute("id"));
							voter = (MatchVoter)voterClass.newInstance();
	
							// see if our voter is hidden or default
							String attrHidden = node.getAttribute("hidden");
							if (attrHidden.toLowerCase().equals("true")) { voter.setHidden(true); }
							String attrDefault = node.getAttribute("default");
							if (attrDefault.toLowerCase().equals("true")) { voter.setDefault(true); }

						} catch (Exception ee) {
							System.err.println("(E) MatchManager - Failed to locate voter class: " + ee.getMessage());
						}

						ArrayList<MatcherOption> options = new ArrayList<MatcherOption>();
						try {
							// get all the options for this matcher
							NodeList optionNodes = node.getElementsByTagName("option");
							if (optionNodes != null && optionNodes.getLength() > 0) {
								voter.setConfigurable(true); // we have options!

								for (int j = 0; j < optionNodes.getLength(); j++) {
									Element optionNode = (Element)optionNodes.item(j);

									// get all attributes (we will test to see if they actually have data later)
									String name = optionNode.getTextContent();
									String id = optionNode.getAttribute("id");
									String type = optionNode.getAttribute("type");
									Boolean selected = (optionNode.getAttribute("selected") == "true" ? true : false);

									// make sure our required options are defined
									if (id == null || id.equals("")) { throw new Exception("No id defined for this option."); }
									if (type == null || type.equals("")) { throw new Exception("No type defined for this option."); }
									if (name == null || name.equals("")) { throw new Exception("No name defined for this option."); }

									// add this option to our temporary options list
									options.add(new MatcherOption(id, type, name, selected));									
								}
							}
						} catch (Exception ee) {
							System.err.println("(E) MatchManager - Failed to load voter options: " + ee.getMessage());
						}

						// add the voter to our master list
						if (voter != null) { voters.add(voter); }
						if (options.size() > 0) { voterOptions.put(voter.getClass().getName(), options); }
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

	/** Return the list of options for a given voter */
	static public ArrayList<MatcherOption> getVoterOptions(String id) {
		// our "id" is the class name of the voter
		if (voterOptions.containsKey(id)) {
			return voterOptions.get(id);
		} else {
			return null;
		}
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