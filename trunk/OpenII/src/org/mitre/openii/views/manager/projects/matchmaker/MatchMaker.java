package org.mitre.openii.views.manager.projects.matchmaker;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.openii.model.OpenIIManager;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.SchemaElement;

public class MatchMaker {

	private Integer[] schemaIDs = null;
	private ClusterNode cluster;

	// Cluster name to schemaElementNode
	HashMap<String, SynsetTerm> clusterElements;
	// Schema element ID to a list of schema IDs
	HashMap<Integer, ArrayList<Integer>> elementSchemaLookUp;

	private Project project;
	private HashMap<Mapping, ArrayList<MappingCell>> mappings;
	
	public MatchMaker(Project project) {
		this.project = project; 
	}

	/**
	 * Initialize elementSchemaLookUp, which is a hash of elementID to a list of
	 * schemaIDs that it belongs to
	 * 
	 * @throws RemoteException
	 */
	private void initElementSchemaLookUp() {
		// Loop through each schema ID
		for (Integer schemaID : this.schemaIDs) {
			// Loop through each element
			for (SchemaElement element : OpenIIManager.getSchemaInfo(schemaID).getElements(null)) {
				// Hash current schemaID to the list by elementID
				ArrayList<Integer> schemaIDList = lookupSchemaIDs(element.getId());
				schemaIDList.add(schemaID);
			}
		}
	}

	// Returns an array list of schema IDs that the element belongs to. Create a
	// new list if none exists yet.
	private ArrayList<Integer> lookupSchemaIDs(Integer elementID) {
		ArrayList<Integer> schemaIDList = elementSchemaLookUp.get(elementID);
		if (schemaIDList == null) {
			schemaIDList = new ArrayList<Integer>();
			elementSchemaLookUp.put(elementID, schemaIDList);
		}
		return schemaIDList;
	}

	// public FileFilter getFileFilter() {
	// class CsvFilter extends FileFilter {
	// public boolean accept(File file) {
	// if (file.isDirectory()) return true;
	// if (file.toString().endsWith(".xls")) return true;
	// return false;
	// }
	//
	// public String getDescription() {
	// return "MatchMaker (.xls)";
	// }
	// }
	// return new CsvFilter();
	// }

//	public String getFileType() {
//		return ".xls";
//	}

	// Create SynsetTerm used by ClusterNode from MappingCells
	private void clusterMatchResults() {
		// Initialize SchemaElementClusterNodes for each MappingCell that
		// exists.
		for (ArrayList<MappingCell> mcList : mappings.values()) {
			
			System.out.println( " mapping cell list size: " + mcList.size() ); 
			
			for (MappingCell mappingCell : mcList) {
				// Loop through mapping cells input cluster nodes
				for (SynsetTerm inputNode : getClusterNodeList(mappingCell.getInput())) {
					// Loop through mapping cells output cluster nodes
					for (SynsetTerm outputNode : getClusterNode(mappingCell.getOutput())) {
						// Add the nodes and score to each other's score list.
						inputNode.add(outputNode, mappingCell.getScore());
						outputNode.add(inputNode, mappingCell.getScore());
					}
				}
			}
		}

		// Run clustering algorithm
		cluster = new ClusterNode(new ArrayList<SynsetTerm>(clusterElements.values()));
		cluster.cluster(schemaIDs.length, 0);
	}

	// Manages cluster terms: since each element may be associated with multiple
	// schemas, one Schema Element node is created for each schema that the
	// element belongs to
	private ArrayList<SynsetTerm> getClusterNode(Integer elementID) {
		SchemaElement element;
		ArrayList<SynsetTerm> results = new ArrayList<SynsetTerm>();
		
		ArrayList<Integer> schemaIdList = lookupSchemaIDs(elementID);
		element = OpenIIManager.getSchemaInfo(schemaIdList.get(0)).getElement(elementID); 

		SynsetTerm node;
		String elementHashKey;
		for (Integer sid : schemaIdList) {
			elementHashKey = sid + element.getName() + elementID;

			// get node from hashed cluster elements or create a new one
			node = clusterElements.get(elementHashKey);
			if (node == null) {
				node = new SynsetTerm(sid, elementID, element.getName());
				clusterElements.put(elementHashKey, node);
			}

			results.add(node);
		}
		return results;
	}

	// Given a set of element IDs, for each, create its cluster node list.
	// Return all.
	private ArrayList<SynsetTerm> getClusterNodeList(Integer[] elementIDs) {
		ArrayList<SynsetTerm> masterList = new ArrayList<SynsetTerm>();
		for (Integer i : elementIDs)
			masterList.addAll(getClusterNode(i));
		return masterList;
	}

	// Sort cluster's synsets by number of elements in each and each Synset's
	// representing lowest alpha element
	private void sortByParticipation() {
		Collections.sort(cluster.getSynsets(), new SynsetParticipationComparator());
	}

	/**
	 * Our results must include all elements in all input schemas. Sometimes a
	 * few are missing from the clustering results because there weren't
	 * appropriate matches. This sweeps up the remainder and ensures that
	 * they're in the output set.
	 */
	private void ensureCompleteness() {
		// for each schema
		// 1) sort clusterNode by that schema
		// 2) sort all schema elements in the schema
		// 3) iterate down the two lists together, insert missing elements as a
		// new Synset into into the cluster
		SchemaElementAlphaComparator elementComparator = new SchemaElementAlphaComparator();

		for (Integer schemaID : schemaIDs) {
			ArrayList<SchemaElement> refList;
			int groupEIDX = 0, allIDX = 0;
			SynsetTerm groupENode;
			SchemaElement refNode;
			int compareResult;

			// sort synsets with respective to one schema
			cluster.sortBySchema(schemaID);

			// sort schema elements in the schema
			refList = OpenIIManager.getSchemaInfo(schemaID).getElements(null);

			Collections.sort(refList, elementComparator);

			// compare sorted synsets with sorted complete schema elements
			while (groupEIDX < cluster.getSynsets().size() && allIDX < refList.size()) {
				Synset synset =cluster.getSynsets().get(groupEIDX); 
				groupENode = synset.getTerm(schemaID);
				refNode = refList.get(allIDX);

				// skip goupE if it doesn't have an element for this schema
				if (groupENode == null) {
					groupEIDX++;
					continue;
				}

				// skip terms that are Domains or DomainValus or with no
				// name /* !INCLUDE_DOMAIN_IN_OUTPUT && */
				if (refNode instanceof Domain || refNode instanceof DomainValue) {
					allIDX++;
					continue;
				}
				// Do not include elements with no element name
				if (refNode.getName().length() == 0) {
					allIDX++;
					continue;
				}

				// compare by ID
				compareResult = refNode.getName().compareToIgnoreCase(groupENode.elementName);

				// create a new Synset for graphNode that doesn't exist
				if (compareResult < 0) {
					for (SynsetTerm newNode : getClusterNode(refNode.getId())) {
						cluster.getSynsets().add(groupEIDX, new Synset(newNode));
						System.out.println("Insert node " + newNode.elementName + " (" + refNode.getId() + ")");
					}
				}

				groupEIDX++;
				allIDX++;
			}

			// In case we have not included all that's in the reference List
			if (allIDX < refList.size()) {
				System.err.println(schemaID + " has total of " + refList.size() + " elements; but allIDX=" + allIDX);
				while (allIDX < refList.size()) {
					refNode = refList.get(allIDX);
					for (SynsetTerm newNode : getClusterNode(refNode.getId())) {
						cluster.getSynsets().add(groupEIDX++, new Synset(newNode));
						System.out.println("Insert node " + newNode.elementName + " (" + refNode.getId() + ")");
					}
					allIDX++;
				}
			}
		}

	}

	class SchemaElementAlphaComparator implements Comparator<SchemaElement> {
		public int compare(SchemaElement o1, SchemaElement o2) {
			String o1Str = o1.getName() + o1.getId();
			String o2Str = o2.getName() + o2.getId();
			return o1Str.compareToIgnoreCase(o2Str);
		}
	}

	/**
	 * Compare two groups by the number of participating terms. And then compare
	 * alphabetically
	 * 
	 * @author HAOLI
	 * 
	 */
	class SynsetParticipationComparator implements Comparator<Synset> {
		public SynsetParticipationComparator() {}

		public int compare(Synset g1, Synset g2) {

			int g1Size = g1.getGroup().size();
			int g2Size = g2.getGroup().size();
			int diff = g2Size - g1Size;

			if (diff == 0) return g1.compareTo(g2);
			else return diff;
		}
	}

	// public void exportMapping(Project mapping, ArrayList<MappingCell>
	// mappingCells, File file) throws IOException {
	// this.mapping = mapping;
	// this.mappingCells = mappingCells;
	// this.elementSchemaLookUp = new HashMap<Integer, ArrayList<Integer>>();
	// this.clusterElements = new HashMap<String, SynsetTerm>();
	// this.schemaIDs = mapping.getSchemaIDs();
	//		
	// // Create look up for elementIDs to SchemaIDs
	// initElementSchemaLookUp();
	//
	// // Cluster results
	// clusterMatchResults();
	//
	// // ensure all schema element
	// ensureCompleteness();
	// sortByParticipation();
	//
	// // Render clustered results
	// ClusterRenderer clusterRenderer = new ClusterRenderer(cluster, client,
	// schemaIDs);
	// clusterRenderer.print(file);
	// }

//	public String getDescription() {
//		return "Export N-way match results to an Excel spreadsheet.";
//	}

	/** 
	 * Run hierarchically clustering algorithm on a set of mapping cells
	 */
	public ClusterNode cluster() {
		System.out.println( " Start Match Maker ..."); 

		this.elementSchemaLookUp = new HashMap<Integer, ArrayList<Integer>>();
		this.clusterElements = new HashMap<String, SynsetTerm>();
		this.mappings = new HashMap<Mapping, ArrayList<MappingCell>>();

		// Initialize mapping-mappingcell
		for (Mapping map : OpenIIManager.getMappings(project.getId()))
			this.mappings.put(map, OpenIIManager.getMappingCells(map.getId()));

		System.out.println( " init schema Ids ");
		// Initialize schema IDs used in all of the mappings.
		initSchemaIDs();

		System.out.println( " init element schema lookup ");
		// Create look up for elementIDs to SchemaIDs
		initElementSchemaLookUp();

		System.out.println( " cluster match results ");
		// Cluster results
		clusterMatchResults();

		System.out.println( " ensurecompleteness " ) ;
		// Ensure all schema element are included in the result
		ensureCompleteness();

		// Sort result rows by the number of participating schemas
		sortByParticipation();

		// Render clustered results
		try {
			File output = new File("C://Documents and Settings//haoli//workspace//OpenII//MatchMakerAutoGen.xls");
			ClusterRenderer clusterRenderer = new ClusterRenderer(cluster, schemaIDs);
			clusterRenderer.print(output);
			System.out.println( " Match Maker writing to " + output.getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println( " Match Maker done.");
		return cluster;
	}
	

	// Populate the schemaID lists from schemas that participate in all of the
	// project's mappings.
	private void initSchemaIDs() {
		HashSet<Integer> idList = new HashSet<Integer>();
		Mapping[] mappingObjects = mappings.keySet().toArray(new Mapping[0]);

		for (Mapping m : mappingObjects) {
			idList.add(m.getSourceId());
			idList.add(m.getTargetId());
		}

		this.schemaIDs = idList.toArray(new Integer[0]);

	}

}
