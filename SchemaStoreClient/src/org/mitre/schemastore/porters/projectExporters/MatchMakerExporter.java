package org.mitre.schemastore.porters.projectExporters;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.Project;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.projectExporters.matchmaker.ClusterNode;
import org.mitre.schemastore.porters.projectExporters.matchmaker.ClusterRenderer;
import org.mitre.schemastore.porters.projectExporters.matchmaker.SchemaElementClusterNode;
import org.mitre.schemastore.porters.projectExporters.matchmaker.groupE;

public class MatchMakerExporter extends ProjectExporter {

	private Integer[] schemaIDs = null;
	private ClusterNode cluster;

	// Cluster name to schemaElementNode
	HashMap<String, SchemaElementClusterNode> clusterElements;
	// Schema element ID to a list of schema IDs
	HashMap<Integer, ArrayList<Integer>> elementSchemaLookUp;

	private Project project;
	private HashMap<Mapping, ArrayList<MappingCell>> mappings;

	/**
	 * Initialize elementSchemaLookUp, which is a hash of elementID to a list of
	 * schemaIDs that it belongs to
	 * 
	 * @throws RemoteException
	 */
	private void initElementSchemaLookUp() throws RemoteException {
		// Loop through each schema ID
		for (Integer schemaID : this.schemaIDs) {
			// Loop through each element
			for (SchemaElement element : client.getSchemaInfo(schemaID).getElements(null)) {
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

	public String getFileType() {
		return ".xls";
	}

	// Create SchemaElementClusterNode used by ClusterNode from MappingCells
	private void clusterMatchResults() {
		// Loop through all mapping cell ArrayLists
		Iterator<ArrayList<MappingCell>> mcListItr = this.mappings.values().iterator(); 
		while (mcListItr.hasNext()){
			ArrayList<MappingCell> mcList = mcListItr.next(); 
			// Loop through all mapping cells
			for ( MappingCell mappingCell : mcList ){
				// Loop through mapping cells input cluster nodes
				for (SchemaElementClusterNode inputNode : getClusterNodeList(mappingCell.getInput())) {
					// Loop through mapping cells output cluster nodes
					for (SchemaElementClusterNode outputNode : getClusterNode(mappingCell.getOutput())) {
						// Add the nodes and score to each other's score list.
						inputNode.add(outputNode, mappingCell.getScore());
						outputNode.add(inputNode, mappingCell.getScore());
					}
				}
			}
		}
		
		// Run clustering algorithm
		cluster = new ClusterNode(new ArrayList<SchemaElementClusterNode>(clusterElements.values()));
		cluster.cluster(schemaIDs.length, 0);
	}

	// Manages cluster nodes: since each element may be associated with multiple
	// schemas, one Schema Element node is created for each schema that the
	// element belongs to
	private ArrayList<SchemaElementClusterNode> getClusterNode(Integer elementID) {
		SchemaElement element;
		ArrayList<SchemaElementClusterNode> results = new ArrayList<SchemaElementClusterNode>();
		try {
			element = client.getSchemaElement(elementID);
			ArrayList<Integer> schemaIdList = lookupSchemaIDs(elementID);

			SchemaElementClusterNode node;
			String elementHashKey;
			for (Integer sid : schemaIdList) {
				elementHashKey = sid + element.getName() + elementID;

				// get node from hashed cluster elements or create a new one
				node = clusterElements.get(elementHashKey);
				if (node == null) {
					node = new SchemaElementClusterNode(sid, elementID, element.getName());
					clusterElements.put(elementHashKey, node);
				}

				results.add(node);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}

	// Given a set of element IDs, for each, create its cluster node list. Return all.  
	private ArrayList<SchemaElementClusterNode> getClusterNodeList(Integer[] elementIDs) {
		ArrayList<SchemaElementClusterNode> masterList = new ArrayList<SchemaElementClusterNode>();
		for (Integer i : elementIDs)
			masterList.addAll(getClusterNode(i));
		return masterList;
	}

	// Sort cluster's groupEs by number of elements in each and each groupE's
	// representing lowest alpha element
	private void sortByParticipation() {
		Collections.sort(cluster.groupEs, new groupEParticipationComparator());
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
		// new groupE into into the cluster
		SchemaElementAlphaComparator elementComparator = new SchemaElementAlphaComparator();

		for (Integer schemaID : schemaIDs) {
			ArrayList<SchemaElement> refList;
			int groupEIDX = 0, allIDX = 0;
			SchemaElementClusterNode groupENode;
			SchemaElement refNode;
			int compareResult;

			// sort groupEs with respective to one schema
			cluster.sort(schemaID);

			// sort schema elements in the schema
			try {
				refList = client.getSchemaInfo(schemaID).getElements(null);

				Collections.sort(refList, elementComparator);

				// compare sorted groupEs with sorted complete schema elements
				while (groupEIDX < cluster.groupEs.size() && allIDX < refList.size()) {
					groupENode = cluster.groupEs.get(groupEIDX).getNode(schemaID);
					refNode = refList.get(allIDX);

					// skip goupE if it doesn't have an element for this schema
					if (groupENode == null) {
						groupEIDX++;
						continue;
					}

					// skip nodes that are Domains or DomainValus or with no
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

					// create a new groupE for graphNode that doesn't exist
					if (compareResult < 0) {
						for (SchemaElementClusterNode newNode : getClusterNode(refNode.getId())) {
							cluster.groupEs.add(groupEIDX, new groupE(newNode));
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
						for (SchemaElementClusterNode newNode : getClusterNode(refNode.getId())) {
							cluster.groupEs.add(groupEIDX++, new groupE(newNode));
							System.out.println("Insert node " + newNode.elementName + " (" + refNode.getId() + ")");
						}
						allIDX++;
					}
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	 * Compare two groups by the number of participating nodes. And then compare
	 * alphabetically
	 * 
	 * @author HAOLI
	 * 
	 */
	class groupEParticipationComparator implements Comparator<groupE> {
		public groupEParticipationComparator() {}

		public int compare(groupE g1, groupE g2) {

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
	// this.clusterElements = new HashMap<String, SchemaElementClusterNode>();
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

	public String getDescription() {
		return "Export N-way match results to an Excel spreadsheet.";
	}

	@Override
	public String getName() {
		return "Match Maker";
	}

	@Override
	public void exportProject(Project project, HashMap<Mapping, ArrayList<MappingCell>> mappings, File file) throws IOException {
		this.project = project;
		this.mappings = mappings;
		this.elementSchemaLookUp = new HashMap<Integer, ArrayList<Integer>>();
		this.clusterElements = new HashMap<String, SchemaElementClusterNode>();

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
		ClusterRenderer clusterRenderer = new ClusterRenderer(cluster, client, schemaIDs);
		clusterRenderer.print(file);
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
