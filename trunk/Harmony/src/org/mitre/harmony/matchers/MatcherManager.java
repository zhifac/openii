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
	/** Stores a listing of all match */
	static private ArrayList<MatchVoter> matchers = new ArrayList<MatchVoter>();

	/** Stores a listing of all match mergers */
	static private ArrayList<MatchMerger> mergers = new ArrayList<MatchMerger>();

	/** Stores a listing of all matcher options */
	static private HashMap<String, HashMap<String, MatcherOption>> matcherOptions = new HashMap<String, HashMap<String, MatcherOption>>();

	/** Initializes the matcher manager with all defined matchers and mergers */
	static {
		try {
			// using factory get an instance of document builder
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get dom representation of the xml file
			Document dom = db.parse(PorterList.class.getResourceAsStream("/matchers.xml"));

			// get the root element
			Element root = dom.getDocumentElement();

			// get all matchers
			{
				NodeList nodes = root.getElementsByTagName("matcher");
				if (nodes != null && nodes.getLength() > 0) {
					for (int i = 0; i < nodes.getLength(); i++) {
						// get the element and its attribute
						Element node = (Element)nodes.item(i);

						MatchVoter matcher = null;
						try {
							// create a new matcher from this class id
							Class matcherClass = Class.forName(node.getAttribute("id"));
							matcher = (MatchVoter)matcherClass.newInstance();
	
							// see if our matcher is hidden or default
							String attrHidden = node.getAttribute("hidden");
							if (attrHidden.toLowerCase().equals("true")) { matcher.setHidden(true); }
							String attrDefault = node.getAttribute("default");
							if (attrDefault.toLowerCase().equals("true")) { matcher.setDefault(true); }

						} catch (Exception ee) {
							System.err.println("(E) MatchManager - Failed to locate matcher class: " + ee.getMessage());
						}

						HashMap<String, MatcherOption> options = new HashMap<String, MatcherOption>();
						try {
							// get all the options for this matcher
							NodeList optionNodes = node.getElementsByTagName("option");
							if (optionNodes != null && optionNodes.getLength() > 0) {
								for (int j = 0; j < optionNodes.getLength(); j++) {
									Element optionNode = (Element)optionNodes.item(j);

									// get all attributes (we will test to see if they actually have data later)
									String name = optionNode.getTextContent();
									String id = optionNode.getAttribute("id");
									String type = optionNode.getAttribute("type");

									// get some of the different fields that may contain values
									String optionValue = null;
									String selected = optionNode.getAttribute("selected");
									if (selected != null) { optionValue = optionNode.getAttribute("selected"); }

									// make sure our required options are defined
									if (id == null || id.equals("")) { throw new Exception("No id defined for this option."); }
									if (type == null || type.equals("")) { throw new Exception("No type defined for this option."); }
									if (name == null || name.equals("")) { throw new Exception("No name defined for this option."); }

									// add this option to our temporary options list
									options.put(id, new MatcherOption(id, type, name, optionValue));
									matcher.setConfigurable(true); // we have options!
								}
							}
						} catch (Exception ee) {
							System.err.println("(E) MatchManager - Failed to load matcher options: " + ee.getMessage());
						}

						// if the matcher has options, add them to the options list
						if (options.size() > 0) { matcherOptions.put(matcher.getClass().getName(), options); }

						// add the matcher to our master list
						if (matcher != null) { matchers.add(matcher); }
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
							// create a new merger from this class id
							Class mergerClass = Class.forName(node.getAttribute("id"));
							MatchMerger merger = (MatchMerger)mergerClass.newInstance();
	
							// add the merger to our master list
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

	/** Returns the list of match matchers */
	static public ArrayList<MatchVoter> getMatchers() {
		return matchers;
	}

	/** Returns the list of default matchers */
	static public ArrayList<MatchVoter> getDefaultMatchers() {
		ArrayList<MatchVoter> defaultMatchers = new ArrayList<MatchVoter>();
		for(MatchVoter matcher : matchers) {
			if (matcher.isDefault()) {
				defaultMatchers.add(matcher);
			}
		}
		return defaultMatchers;
	}

	/** Returns the list of visible matchers */
	static public ArrayList<MatchVoter> getVisibleMatchers() {
		ArrayList<MatchVoter> visibleMatchers = new ArrayList<MatchVoter>();
		for (MatchVoter matcher : matchers) {
			if (!matcher.isHidden()) {
				visibleMatchers.add(matcher);
			}
		}
		return visibleMatchers;
	}
	
	/** Returns the list of match mergers */
	static public ArrayList<MatchMerger> getMergers() {
		return mergers;
	}

	/** Return the list of options for a given matcher */
	static public HashMap<String, MatcherOption> getMatcherOptions(String id) {
		// our "id" is the class name of the matcher
		if (matcherOptions.containsKey(id)) {
			return matcherOptions.get(id);
		} else {
			return null;
		}
	}

	/** Return the specific matcher option for the given matcher */
	static public MatcherOption getMatcherOption(String id, String option) {
		if (matcherOptions.containsKey(id)) {
			if (matcherOptions.get(id).containsKey(option)) {
				return matcherOptions.get(id).get(option);
			}
		}
		return null;
	}

	/** Return the details about one matcher */
	static public MatchVoter getMatcher(String id) {
		for (int i = 0; i < matchers.size(); i++) {
			if (matchers.get(i).getClass().getName().equals(id)) {
				return matchers.get(i);
			}
		}
		return null;
	}

	/** Run the matchers to calculate match scores */
	static public MatchScores getScores(FilteredSchemaInfo schemaInfo1, FilteredSchemaInfo schemaInfo2, ArrayList<MatchVoter> matchers, MatchMerger merger) {
		return getScores(schemaInfo1, schemaInfo2, matchers, merger, null);
	}
	
	/** Run the matchers to calculate match scores */
	static public MatchScores getScores(FilteredSchemaInfo schema1, FilteredSchemaInfo schema2, ArrayList<MatchVoter> matchers, MatchMerger merger, MatchTypeMappings typeMappings) {
		merger.initialize(schema1, schema2, typeMappings);
		for (MatchVoter matcher : matchers) {
			matcher.initialize(schema1, schema2, typeMappings, getMatcherOptions(matcher.getClass().getName()));
			merger.addMatcherScores(matcher.match());
		}
		return merger.getMatchScores();
	}
}