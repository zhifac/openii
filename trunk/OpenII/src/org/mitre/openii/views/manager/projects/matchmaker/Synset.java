package org.mitre.openii.views.manager.projects.matchmaker;

import java.util.ArrayList;

/**
 * A Synset is an arraylist of terms. 
 * 
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
	public ArrayList<Term> terms;

	public Term leastNode = null;

	public Synset(Term n) {
		terms = new ArrayList<Term>();
		add(n);
	}

	void add(Term n) {
		terms.add(n);
		updateLeastNode(n);
	}

	private void updateLeastNode(Term n) {
		if (leastNode == null || leastNode.compareTo(n) > 0) leastNode = n;
	}

	/**
	 * full linkage. max distance is 1, least is 0 Simple Linkage
	 * 
	 * @param two
	 * @return the largest distance between two Synset terms
	 */
	float completeLinkage(Synset two) {
		int i;
		int j;
		float maxDist = 0;
		for (i = 0; i < this.terms.size(); i++) {
			Term t_node = this.terms.get(i);
			for (j = 0; j < two.terms.size(); j++) {
				Term s_node = two.terms.get(j);
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

	// Returns minimum distance between two synsets
	public float simpleLinkage(Synset two) {
		int i, j;
		float minDist = 1;
		for (i = 0; i < this.terms.size(); i++) {
			Term t_node = this.terms.get(i);
			for (j = 0; j < two.terms.size(); j++) {
				Term s_node = two.terms.get(j);
				int m = t_node.pointers.indexOf(s_node);
				if (m == -1) return 0;
				float st_dist = t_node.distances.get(m).floatValue();
				if (minDist > st_dist) minDist = st_dist;
			}
		}
		return minDist;
	}

	public void combineSynsets(Synset two) {
		terms.ensureCapacity(terms.size() + two.terms.size());
		terms.addAll(two.terms);
		updateLeastNode(two.leastNode);
	}

	public ArrayList<Term> getGroup() {
		return terms;
	}

	public int compareTo(Synset other) {
		return this.leastNode.compareTo(other.leastNode);
	}

	/**
	 * returns the schemaelement node that belongs to the specified schema
	 * 
	 * @param baseSchema
	 * @return Term
	 */
	public Term getTerm(Integer baseSchema) {
		for (Term n : terms)
			if (n.schemaId == baseSchema) return n;
		return null;
	}

}
