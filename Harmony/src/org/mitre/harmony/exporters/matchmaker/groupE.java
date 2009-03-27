package org.mitre.harmony.exporters.matchmaker;

import java.util.ArrayList;

/**
 * A groupE is just an arraylist of Nodes, that can do a few tricks. This object
 * came from Michael Morse's clustering code, with lots of modifications by
 * David and Maya. The way we actually use it in our code, a groupE is a
 * cluster. The hierarchical clustering code's result is a list of groupEs,
 * which are "chains" of pairwise matches that all logically group together.
 * 
 * @author MDMORSE
 * 
 */
public class groupE implements Comparable<groupE> {
	public ArrayList<SchemaElementNode> nodes;

	public SchemaElementNode leastNode = null;

	public groupE(SchemaElementNode n) {

		nodes = new ArrayList<SchemaElementNode>();
		add(n);
	}

	void add(SchemaElementNode n) {

		nodes.add(n);
		updateLeastNode(n);
	}

	private void updateLeastNode(SchemaElementNode n) {

		if (leastNode == null || leastNode.compareTo(n) > 0) leastNode = n;
	}

	/**
	 * full linkage. max distance is 1, least is 0 Simple Linkage
	 * 
	 * @param two
	 * @return the largest distance between two groupE nodes
	 */
	float completeLinkage(groupE two) {

		int i;
		int j;
		float maxDist = 0;
		for (i = 0; i < this.nodes.size(); i++) {
			Node t_node = this.nodes.get(i);
			for (j = 0; j < two.nodes.size(); j++) {
				Node s_node = two.nodes.get(j);
				int m = t_node.pointers.indexOf(s_node);
				if (m == -1) continue;
				// return 0;
				float st_dist = t_node.distances.get(m).floatValue();
				if (maxDist < st_dist) {
					maxDist = st_dist;
				}
			}
		}
		return maxDist;
	}

	// Returns minimum distance between two nodes
	float simpleLinkage(groupE two) {
		int i, j;
		float minDist = 1;
		for (i = 0; i < this.nodes.size(); i++) {
			Node t_node = this.nodes.get(i);
			for (j = 0; j < two.nodes.size(); j++) {
				Node s_node = two.nodes.get(j);
				int m = t_node.pointers.indexOf(s_node);
				if (m == -1) return 0;
				float st_dist = t_node.distances.get(m).floatValue();
				if (minDist > st_dist) minDist = st_dist;
			}
		}
		return minDist;
	}

	void groupEcombine(groupE two) {

		nodes.ensureCapacity(nodes.size() + two.nodes.size());
		nodes.addAll(two.nodes);
		updateLeastNode(two.leastNode);
	}

	public ArrayList<SchemaElementNode> getGroup() {

		return nodes;
	}

	public int compareTo(groupE other) {

		return this.leastNode.compareTo(other.leastNode);
	}

	/**
	 * returns the schemaelement node that belongs to the specified schema
	 * 
	 * @param baseSchema
	 * @return SchemaElementNode
	 */
	public SchemaElementNode getNode(Integer baseSchema) {

		for (SchemaElementNode n : nodes)
			if (n.schemaId.compareTo(baseSchema) == 0) return n;

		return null;
	}

}
