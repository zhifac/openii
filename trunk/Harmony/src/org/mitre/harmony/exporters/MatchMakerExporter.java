package org.mitre.harmony.exporters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.filechooser.FileFilter;

import org.mitre.harmony.exporters.matchmaker.ClusterNode;
import org.mitre.harmony.exporters.matchmaker.ClusterRenderer;
import org.mitre.harmony.exporters.matchmaker.SchemaElementNode;
import org.mitre.harmony.exporters.matchmaker.groupE;
import org.mitre.harmony.model.HarmonyModel;
import org.mitre.schemastore.model.Domain;
import org.mitre.schemastore.model.DomainValue;
import org.mitre.schemastore.model.MappingCell;
import org.mitre.schemastore.model.SchemaElement;

public class MatchMakerExporter extends Exporter {

	private Integer[] schemaIDs = null;
	private ClusterNode cluster;
	HashMap<String, SchemaElementNode> clusterElements;
	HashMap<Integer, ArrayList<Integer>> elementSchemaLookUp;

	public MatchMakerExporter(HarmonyModel harmonyModel) {
		super(harmonyModel);
		schemaIDs = getModel().getMappingManager().getMapping().getSchemas();
		clusterElements = new HashMap<String, SchemaElementNode>();
		elementSchemaLookUp = new HashMap<Integer, ArrayList<Integer>>();
	}

	public void exportTo(File f) throws IOException {
		// Create look up for elementIDs to SchemaIDs
		initElementSchemaLookUp();

		// Cluster results
		clusterMatchResults();

		// ensure all schema element
		ensureCompleteness();
		sortByParticipation();

		// Render clustered results
		ClusterRenderer clusterRenderer = new ClusterRenderer(cluster, getModel(), schemaIDs);
		try {
			clusterRenderer.print(f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize elementSchemaLookUp, which is a hash of elementID to a list of
	 * schemaIDs that it belongs to
	 */
	private void initElementSchemaLookUp() {
		Integer elementID;

		// Loop through each schemaID
		for (Integer schemaID : getModel().getSchemaManager().getSchemaIDs()) {
			// Loop through each element
			for (SchemaElement element : getModel().getSchemaManager().getSchemaElements(schemaID, null)) {
				elementID = element.getId();
				
				// hash current schemaID to the list by elementID
				ArrayList<Integer> schemaIDRecord = lookupSchemaIDs(elementID); 
				schemaIDRecord.add(schemaID);
			}
		}
	}
	
	private ArrayList<Integer> lookupSchemaIDs ( Integer elementID ) {
		ArrayList<Integer> sIDs = elementSchemaLookUp.get(elementID); 
		 if ( sIDs == null ) { 
			sIDs = new ArrayList<Integer> () ;
			elementSchemaLookUp.put(elementID, sIDs); 
		 }
		 return sIDs;
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
		return "xls";
	}

	private void clusterMatchResults() {
		// Create SchemaElementNode used by ClusterNode from MappingCells
		for (MappingCell mappingCell : getModel().getMappingCellManager().getMappingCells()) {
			SchemaElementNode node1 = getNode(mappingCell.getElement1());
			SchemaElementNode node2 = getNode(mappingCell.getElement2());

			// Add the nodes and score to each other's score list.
			node1.add(node2, mappingCell.getScore());
			node2.add(node1, mappingCell.getScore());
		}

		cluster = new ClusterNode(new ArrayList<SchemaElementNode>(clusterElements.values()));
		cluster.cluster(schemaIDs.length, 0);
	}

	private SchemaElementNode getNode(Integer elementID) {
		SchemaElement element = getModel().getSchemaManager().getSchemaElement(elementID);
		SchemaElementNode node = clusterElements.get(element.getName() + elementID);
		if (node == null) {
			node = new SchemaElementNode(lookupSchemaIDs(elementID), elementID, element.getName());
			clusterElements.put(node.toString(), node);
		}
		return node;
	}

	public void sortByParticipation() {
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
			int numGroupE;
			int numElements;

			// sort groupEs with respective to one schema
			cluster.sort(schemaID);
			// sort schema elements in the schema
			refList = getModel().getSchemaManager().getGraph(schemaID).getElements(null);
			Collections.sort(refList, elementComparator);

			// compare sorted groupEs with sorted complete schema elements
			numGroupE = cluster.groupEs.size();
			numElements = refList.size();
			while (groupEIDX < numGroupE && allIDX < numElements) {
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
					SchemaElementNode newNode = new SchemaElementNode(lookupSchemaIDs(refNode.getId()), refNode.getId(), refNode.getName());
					cluster.groupEs.add(groupEIDX, new groupE(newNode));
					System.out.println("Insert node " + newNode.elementName + " (" + refNode.getId() + ")");
				}

				groupEIDX++;
				allIDX++;
			}

			System.out.println("groupE size " + cluster.groupEs.size());
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

}
