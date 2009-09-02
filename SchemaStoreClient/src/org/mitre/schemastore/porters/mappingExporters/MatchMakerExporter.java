package org.mitre.schemastore.porters.mappingExporters;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.filechooser.FileFilter;

import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.Mapping;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;
import org.mitre.schemastore.porters.mappingExporters.matchmaker.ClusterNode;
import org.mitre.schemastore.porters.mappingExporters.matchmaker.ClusterRenderer;
import org.mitre.schemastore.porters.mappingExporters.matchmaker.SchemaElementNode;
import org.mitre.schemastore.porters.mappingExporters.matchmaker.groupE;

public class MatchMakerExporter extends MappingExporter {

	private Integer[] schemaIDs = null;
	private ClusterNode cluster;

	HashMap<String, SchemaElementNode> clusterElements; // cluster name to schemaElementNode
	HashMap<Integer, ArrayList<Integer>> elementSchemaLookUp; // schema element ID to a list of schema IDs
	
	private Mapping mapping;
	private ArrayList<MappingCell> mappingCells;


	/**
	 * Initialize elementSchemaLookUp, which is a hash of elementID to a list of
	 * schemaIDs that it belongs to
	 * @throws RemoteException 
	 */
	private void initElementSchemaLookUp() throws RemoteException {
		Integer elementID;
		
		// Loop through each schemaID
		for (Integer schemaID : this.mapping.getSchemaIDs()) {
			// Loop through each element
			
			for (SchemaElement element : client.getSchemaInfo(schemaID).getElements(null) ) {
				elementID = element.getId();

				// hash current schemaID to the list by elementID
				ArrayList<Integer> schemaIDList = lookupSchemaIDs(elementID);
				schemaIDList.add(schemaID);
			}
		}
	}

	
	// Returns an array list of schema IDs that the element belongs to. Create a new list if none exists yet. 
	private ArrayList<Integer> lookupSchemaIDs(Integer elementID) {
		ArrayList<Integer> schemaIDList = elementSchemaLookUp.get(elementID);
		if (schemaIDList == null) {
			schemaIDList = new ArrayList<Integer>();
			elementSchemaLookUp.put(elementID, schemaIDList);
		}
		return schemaIDList;
	}

	public FileFilter getFileFilter() {
		class CsvFilter extends FileFilter {
			public boolean accept(File file) {
				if (file.isDirectory()) return true;
				if (file.toString().endsWith(".xls")) return true;
				return false;
			}

			public String getDescription() {
				return "MatchMaker (.xls)";
			}
		}
		return new CsvFilter();
	}

	public String getFileType() {
		return ".xls";
	}

	private void clusterMatchResults() {
		// Create SchemaElementNode used by ClusterNode from MappingCells
		for (MappingCell mappingCell :  this.mappingCells ) {
			for (SchemaElementNode node1 : getNode(mappingCell.getElement1())) {
				for (SchemaElementNode node2 : getNode(mappingCell.getElement2())) {
					// Add the nodes and score to each other's score list.
					node1.add(node2, mappingCell.getScore());
					node2.add(node1, mappingCell.getScore());
				}
			}
		}

		// Run clustering algorithm
		cluster = new ClusterNode(new ArrayList<SchemaElementNode>(clusterElements.values()));
		cluster.cluster(schemaIDs.length, 0);
	}

	// Manages cluster nodes: since each element may be associated with multiple
	// schemas, one Schema Element node is created for each schema that the
	// element belongs to
	private ArrayList<SchemaElementNode> getNode(Integer elementID) {
		SchemaElement element;
		ArrayList<SchemaElementNode> results = new ArrayList<SchemaElementNode>();
		try {
			element = client.getSchemaElement(elementID);
			ArrayList<Integer> schemaIdList = lookupSchemaIDs(elementID);
			
			SchemaElementNode node;
			String elementHashKey;
			for ( Integer sid : schemaIdList ) {
				elementHashKey = sid + element.getName() + elementID; 
				
				// get node from hashed cluster elements or create a new one
				node = clusterElements.get(elementHashKey);
				if ( node == null ) {
					node = new SchemaElementNode(sid, elementID, element.getName());
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
			SchemaElementNode groupENode;
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
						for (SchemaElementNode newNode : getNode(refNode.getId())) {
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
						for (SchemaElementNode newNode : getNode(refNode.getId())) {
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

	@Override
	public void exportMapping(Mapping mapping, ArrayList<MappingCell> mappingCells, File file) throws IOException {
		this.mapping = mapping; 
		this.mappingCells = mappingCells; 
		this.elementSchemaLookUp = new HashMap<Integer, ArrayList<Integer>>(); 
		this.clusterElements = new HashMap<String, SchemaElementNode>(); 
		this.schemaIDs = mapping.getSchemaIDs();
		
		// Create look up for elementIDs to SchemaIDs
		initElementSchemaLookUp();

		// Cluster results
		clusterMatchResults();

		// ensure all schema element
		ensureCompleteness();
		sortByParticipation();

		// Render clustered results
		ClusterRenderer clusterRenderer = new ClusterRenderer(cluster, client, schemaIDs);
		clusterRenderer.print(file);
	}

	@Override
	public String getDescription() {
		return "Export N-way match results to an xls spreadsheet.";
	}

	@Override
	public String getName() {
		return "Match Maker";
	}


}
