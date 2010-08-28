package org.mitre.openii.dialogs.projects.unity;

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
	public ArrayList<SynsetTerm> terms;
	public SynsetTerm leastNode = null;

	public Synset() {
		terms = new ArrayList<SynsetTerm>();
	}

	public Synset(SynsetTerm n) {
		this();
		add(n);
	}

	public void add(SynsetTerm n) {
		if (!terms.contains(n))
			terms.add(n);
		updateLeastNode(n);
	}

	private void updateLeastNode(SynsetTerm n) {
		if (leastNode == null || leastNode.compareTo(n) > 0)
			leastNode = n;
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
			SynsetTerm t_node = this.terms.get(i);
			for (j = 0; j < two.terms.size(); j++) {
				SynsetTerm s_node = two.terms.get(j);
				int m = t_node.pointers.indexOf(s_node);
				if (m == -1)
					continue;
				// return 0;
				float st_dist = t_node.distances.get(m).floatValue();
				if (maxDist < st_dist) {
					maxDist = st_dist;
				}
			}
		}
		return maxDist;
	}

	

	public ArrayList<SynsetTerm> getGroup() {
		return terms;
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
		for (SynsetTerm n : terms)
			if (n.schemaId == baseSchema)
				return n;
		return null;
	}

	/**
	 * Make a deep copy of the synset from the schemastore exporter synset to
	 * the current one. This is a very temporary fix until we are done comparing
	 * old unity using hierarchical clustering vs disjoint set forest
	 * 
	 * @param oldSynset
	 * @return
	 */
	static public Synset copy(org.mitre.schemastore.porters.projectExporters.matchmaker.Synset oldSynset) {
		Synset newSynset = new Synset();
		for (org.mitre.schemastore.porters.projectExporters.matchmaker.SynsetTerm oldTerm : oldSynset.terms) {
			SynsetTerm newTerm = new SynsetTerm(oldTerm.schemaId, oldTerm.elementId, oldTerm.elementName);
			newSynset.add(newTerm);
			if (oldTerm.equals(oldSynset.leastNode))
				newSynset.leastNode = newTerm;
		}
		return newSynset;
	}

}
