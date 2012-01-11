package org.mitre.affinity.algorithms.clusterers;

import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.DistanceGrid;

/**
 * Interface for linkers used in hierarchical clustering algorithms (e.g., complete linker, single linker).
 * Linkers determine the distance between two clusters.
 * 
 * @author CBONACETO
 *
 * @param <K>
 */
public interface ILinker<K extends Comparable<K>> {
	// return the distance between the two ClusterGroups.
	public double compare(ClusterGroup<K> one, ClusterGroup<K> two, DistanceGrid<K> dg);
}