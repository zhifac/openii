package org.mitre.harmony.exporters.matchmaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * The main clustering code, originally written by Michael Morse with lots of
 * modifications for MatchMaker.
 * <p>
 * This code takes as an input a list of SchemaElementNode objects, and its
 * output is a list of sorted clusters. Each cluster is stored as a groupE
 * object.
 * 
 * @author MDMORSE, DMALLEN
 */
public class ClusterNode {
	public ArrayList<groupE> groupEs;

	// I don't know how this really affects the groupE's . But any two groupE
	// with distance (or confidence) less than this number
	// is filtered out.
	public static float MAGIC_THRESHOLD = (float) 0.2;

	public ClusterNode(ArrayList<SchemaElementNode> nodes) {
		groupEs = new ArrayList<groupE>();
		for (SchemaElementNode n : nodes) {
			groupEs.add(new groupE(n));
		}
	}
	
	/**
	 * @param j
	 *            the index of the cluster
	 * @return the jth cluster in the object.
	 */
	public ArrayList<SchemaElementNode> getGroup(int j) {
		return groupEs.get(j).getGroup();
	}

	/**
	 * @return the number of clusters that ClusterNode currently has
	 */
	public int numGroups() {
		return groupEs.size();
	}

	/**
	 * Sort groupEs alphabetically using groupEs comparator and rely on merge
	 * sort implemented for java.utils.Arrays.sort() which is O(n log n )
	 */
	public void sortAlpha() {
		Collections.sort(groupEs);
	}

	public void sortByScore() {
		Collections.sort(groupEs, new  groupEScoreComparator());
	}


	/**
	 * Sort groupE alphabetically by specified particular schema ID.
	 */

	public void sort(Integer schemaId) {
		Collections.sort(groupEs, new groupEComparator(schemaId));
	}

	/**
	 * Run the hierarchical clustering algorithm on the list of matches. The two
	 * arguments are not strictly necessary -- if you don't know them, then pass
	 * values both <= 0. If you do know them, it helps with status reporting to
	 * the user.
	 * 
	 * @param schemaCount
	 *            the total number of schemas being matched
	 * @param maxElements
	 *            the maximum number of elements in any of the schemas.
	 */
	public void cluster(int schemaCount, int maxElements) {
		float max_dist = 0;
		int spot1 = 0;
		int spot2 = 0;

		if (schemaCount < 0) schemaCount = 0;
		if (maxElements < 0) maxElements = 0;

		// We need at most maxElements clusters of schemaCount elements each.
		// With each pass through the while() loop, groupEs gets shorter (adding
		// things to clusters)
		int totalLoops = groupEs.size() - maxElements;
		int pctMod = 1;
		int everyN = (int) Math.ceil((double) totalLoops / (double) 100);
		// One step for every percentage.
		if (everyN == 0) {
			pctMod = 25;
			everyN = totalLoops / 4;
		} // Sometimes you don't have 100 total matches.
		if (everyN == 0) {
			pctMod = 100;
			everyN = totalLoops;
		} // Or even 4!

		int pctStatus = 0;
		int loopCounter = 0;

		// By making the "complete list" a separate array, we avoid a penalty on
		// remove() statements associated with moving completed clusters around
		// in the groupE array. Each time we combine two groups and remove one
		// of them, the array list has to do a lot of work. Say I have the list
		// [A, B, C, D, E] and we remove C, the array list has to shift D and E
		// over. When a cluster is completed, we move it out of the array so
		// moving
		// it never pays a penalty.
		ArrayList<groupE> completedClusters = new ArrayList<groupE>();

		// We'll probably end up needing more than this many slots, but this
		// will reduce the number of times the ArrayList has to reallocate more
		// space. That will save time.
		if (maxElements > 0) completedClusters.ensureCapacity(maxElements);

		while (1 == 1) {
			loopCounter++;

			if (everyN == 0) break;
			else if (loopCounter % everyN == 0) {
				pctStatus += pctMod;
				if (pctStatus >= 100) pctStatus = 100;
			}

			int i = 0, j = 0;
			max_dist = (float) MAGIC_THRESHOLD;

			for (i = 0; i < groupEs.size(); i++) {
				groupE n1 = groupEs.get(i);

				for (j = i + 1; j < groupEs.size(); j++) {
					groupE n2 = groupEs.get(j);

					float temp_dist = n1.simpleLinkage(n2);
					if (temp_dist > max_dist) {
						max_dist = temp_dist;
						spot1 = i;
						spot2 = j;
					}
				}
			}

			System.err.println("Loop " + loopCounter + ": " + groupEs.size() + " clusters");

			// If max distance between two groups is found greater than the
			// magic threshold, combine the two groups.
			if (max_dist > MAGIC_THRESHOLD) {
				if (notInSameSchema(groupEs.get(spot1), groupEs.get(spot2))) {
					groupE growing = groupEs.get(spot1);
					growing.groupEcombine(groupEs.get(spot2));
					removeCluster(spot2);

					if (growing.nodes.size() >= schemaCount) {
						completedClusters.add(growing);
						removeCluster(spot1);
					}
				} else System.err.println("(E)Found groupes in same schema: " + groupEs.get(spot1).leastNode.elementName + " \n\t " + groupEs.get(spot2).leastNode.elementName);
			} else {
				for (int x = groupEs.size() - 1; x >= 0; x--)
					completedClusters.add(groupEs.get(x));
				groupEs = completedClusters;
				
				System.out.println("Clustering completed.");
				return;
			}
		}
	}

	/**
	 * Faster way of removing a cluster at a given index. In big lists, it's
	 * very bad to remove something in the middle of the list, because you have
	 * to shift everything after it to the left. Instead, swap the item you want
	 * to remove with the last one in the list, and remove only the last one.
	 * 
	 * @param idx
	 */
	public void removeCluster(int idx) {
		int lastIdx = groupEs.size() - 1;
		groupE swap = groupEs.get(lastIdx);
		groupEs.set(idx, swap);
		groupEs.remove(lastIdx);
	}

	
	
	/**
	 * Test if nodes in groupE1 are contained in the same schema as groupE2
	 * 
	 * @param groupE1
	 * @param groupE2
	 * @return
	 */
	private boolean notInSameSchema(groupE groupE1, groupE groupE2) {
		for (Node n1 : groupE1.getGroup()) {
			if (!(n1 instanceof SchemaElementNode))
				continue;
			for (Node n2 : groupE2.getGroup()) {
				if (!(n2 instanceof SchemaElementNode))
					continue;
				if (((SchemaElementNode) n1).schemaId.equals(((SchemaElementNode) n2).schemaId))
					return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Compares groupEs by a groupE's average scores, rank from low to high.
	 * 
	 * @author HAOLI
	 * 
	 */
	class groupEScoreComparator implements Comparator<groupE> {
		groupEScoreComparator() {}

		public int compare(groupE g1, groupE g2) {
			return -(getAverageScore(g1).compareTo(getAverageScore(g2)));
		}

		private Double getAverageScore(groupE g) {
			Double score = 0.0;
			int numNodes = 0;
			for (SchemaElementNode node : g.getGroup()) {
				for (Double s : node.distances) {
					score += s;
					numNodes++;
				}
			}
			if (numNodes == 0) return 0.0;
			else return score / numNodes;
		}
	}

	

	/**
	 * sort by a selected schema node in two groupE
	 * 
	 * @author HAOLI
	 * 
	 */
	public class groupEComparator implements Comparator<groupE> {
		Integer baseSchema;

		groupEComparator(Integer schemaId) {

			this.baseSchema = schemaId;
		}

		public int compare(groupE g1, groupE g2) {

			SchemaElementNode n1 = g1.getNode(baseSchema);
			SchemaElementNode n2 = g2.getNode(baseSchema);

			if (n1 == null && n2 == null) return 0;
			else if (n1 == null) return -1;
			else if (n2 == null) return 1;
			else return n1.elementName.compareToIgnoreCase(n2.elementName);
		}
	}


	
}