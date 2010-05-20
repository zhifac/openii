package org.mitre.schemastore.porters.projectExporters.matchmaker;

import java.util.ArrayList;


/**
 * A Synset is just an arraylist of Nodes, that can do a few tricks. This object
 * came from Michael Morse's clustering code, with lots of modifications by
 * David and Maya. The way we actually use it in our code, a Synset is a
 * cluster. The hierarchical clustering code's result is a list of synsets,
 * which are "chains" of pairwise matches that all logically group together.
 * 
 * @author MDMORSE
 * 
 */
public class Synset implements Comparable<Synset> {
	public ArrayList<SynsetTerm> nodes;

	public SynsetTerm leastNode = null;

	public Synset(SynsetTerm n) {
		nodes = new ArrayList<SynsetTerm>();
		add(n);
	}

	public void add(SynsetTerm n) {
		nodes.add(n);
		updateLeastNode(n);
	}

	public void combineSynsets(Synset two) {
		nodes.ensureCapacity(nodes.size() + two.nodes.size());
		nodes.addAll(two.nodes);
		updateLeastNode(two.leastNode);
	}

	private void updateLeastNode(SynsetTerm n) {
		if (leastNode == null || leastNode.compareTo(n) > 0) leastNode = n;
	}

	/**
	 * full linkage. max distance is 1, least is 0 Simple Linkage
	 * 
	 * @param two
	 * @return the largest distance between two Synset nodes
	 */
	float completeLinkage(Synset two) {
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
	public float simpleLinkage(Synset two) {
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

	public void groupEcombine(Synset two) {
		nodes.ensureCapacity(nodes.size() + two.nodes.size());
		nodes.addAll(two.nodes);
		updateLeastNode(two.leastNode);
	}

	public ArrayList<SynsetTerm> getGroup() {
		return nodes;
	}

	public int compareTo(Synset other) {
		return this.leastNode.compareTo(other.leastNode);
	}

	/**
	 * returns the schemaelement node that belongs to the specified schema
	 * 
	 * @param baseSchema
	 * @return SynsetTerm
	 */
	public SynsetTerm getTerm(Integer baseSchema) {
		for (SynsetTerm n : nodes)
			if (n.schemaId == baseSchema) return n;
		return null;
	}
	

}
