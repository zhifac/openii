package org.mitre.openii.views.manager.projects.matchmaker;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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
	HashMap<String, Term> clusterElements;
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

	// Create Term used by ClusterNode from MappingCells
	private void clusterMatchResults() {
		// Loop through all mapping cell ArrayLists to build the node matrix
		Iterator<ArrayList<MappingCell>> mcListItr = this.mappings.values().iterator();
		while (mcListItr.hasNext()) {
			ArrayList<MappingCell> mcList = mcListItr.next();
			// Loop through all mapping cells
			for (MappingCell mappingCell : mcList) {
				// Loop through mapping cells input cluster terms
				for (Term inputNode : getClusterNodeList(mappingCell.getInput())) {
					// Loop through mapping cells output cluster terms
					for (Term outputNode : getClusterNode(mappingCell.getOutput())) {
						// Add the terms and score to each other's score list.
						inputNode.add(outputNode, mappingCell.getScore());
						outputNode.add(inputNode, mappingCell.getScore());
					}
				}
			}
		}

		// Run clustering algorithm
		cluster = new ClusterNode(new ArrayList<Term>(clusterElements.values()));
		cluster.cluster(schemaIDs.length, 0);
	}

	// Manages cluster terms: since each element may be associated with multiple
	// schemas, one Schema Element node is created for each schema that the
	// element belongs to
	private ArrayList<Term> getClusterNode(Integer elementID) {
		SchemaElement element;
		ArrayList<Term> results = new ArrayList<Term>();
		
		ArrayList<Integer> schemaIdList = lookupSchemaIDs(elementID);
		element = OpenIIManager.getSchemaInfo(schemaIdList.get(0)).getElement(elementID); 

		Term node;
		String elementHashKey;
		for (Integer sid : schemaIdList) {
			elementHashKey = sid + element.getName() + elementID;

			// get node from hashed cluster elements or create a new one
			node = clusterElements.get(elementHashKey);
			if (node == null) {
				node = new Term(sid, elementID, element.getName());
				clusterElements.put(elementHashKey, node);
			}

			results.add(node);
		}
		return results;
	}

	// Given a set of element IDs, for each, create its cluster node list.
	// Return all.
	private ArrayList<Term> getClusterNodeList(Integer[] elementIDs) {
		ArrayList<Term> masterList = new ArrayList<Term>();
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
			Term groupENode;
			SchemaElement refNode;
			int compareResult;

			// sort synsets with respective to one schema
			cluster.sortBySchema(schemaID);

			// sort schema elements in the schema
			refList = OpenIIManager.getSchemaInfo(schemaID).getElements(null);

			Collections.sort(refList, elementComparator);

			// compare sorted synsets with sorted complete schema elements
			while (groupEIDX < cluster.getSynsets().size() && allIDX < refList.size()) {
				groupENode = cluster.getSynsets().get(groupEIDX).getTerm(schemaID);
				refNode = refList.get(allIDX);

				// skip goupE if it doesn't have an element for this schema
				if (groupENode == null) {
					groupEIDX++;
					continue;
				}

				// skip terms that are Domains or DomainValus or with no
				// name
				if (/* !INCLUDE_DOMAIN_IN_OUTPUT && */
				(refNode instanceof Domain || refNode instanceof DomainValue)) {
					allIDX++;
					continue;
				}
				if (refNode.getName().length() == 0) {
					allIDX++;
					continue;
				}

				// compare by ID
				compareResult = refNode.getName().compareToIgnoreCase(groupENode.elementName);

				// create a new Synset for graphNode that doesn't exist
				if (compareResult < 0) {
					for (Term newNode : getClusterNode(refNode.getId())) {
						cluster.getSynsets().add(groupEIDX, new Synset(newNode));
						System.out.println("Insert node " + newNode.elementName + " (" + refNode.getId() + ")");
					}
				}

				groupEIDX++;
				allIDX++;
			}

			// In case we have not included all that's in the reference List
			if (allIDX < refList.size()) {
				System.err.println(schemaID + " has total of " + refList.size() + " but allIDX=" + allIDX);
				while (allIDX < refList.size()) {
					refNode = refList.get(allIDX);
					for (Term newNode : getClusterNode(refNode.getId())) {
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
	// this.clusterElements = new HashMap<String, Term>();
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
		this.clusterElements = new HashMap<String, Term>();
		this.mappings = new HashMap<Mapping, ArrayList<MappingCell>>();

		// Initialize mapping-mappingcell
		for (Mapping map : OpenIIManager.getMappings(project.getId()))
			this.mappings.put(map, OpenIIManager.getMappingCells(map.getId()));

		// Initialize schema IDs used in all of the mappings.
		initSchemaIDs();

		// Create look up for elementIDs to SchemaIDs
		initElementSchemaLookUp();

		// Cluster results
		clusterMatchResults();

		// Ensure all schema element are included in the result
		ensureCompleteness();

		// Sort result rows by the number of participating schemas
		sortByParticipation();

		// Render clustered results
		try {
			File output = new File("MatchMakerAutoGen.xls");
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
