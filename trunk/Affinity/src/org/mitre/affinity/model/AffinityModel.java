package org.mitre.affinity.model;

import java.util.ArrayList;

import org.mitre.affinity.algorithms.clusterers.Clusterer;
import org.mitre.affinity.algorithms.distance_functions.DistanceFunction;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.model.clusters.DistanceGrid;

/**
 * @author CBONACETO
 *
 * @param <K>
 * @param <V>
 */
public abstract class AffinityModel<K, V> {		
	
	/** The distance grid */
	protected DistanceGrid<K> dg;
	
	/** The clusters */
	protected ClustersContainer<K> clusters;	
	
	public abstract IClusterObjectManager<K, V> getClusterObjectManager();	

	public ClustersContainer<K> getClusters() {
		return clusters;
	}

	public void setClusters(ClustersContainer<K> clusters) {
		this.clusters = clusters;
	}	
	
	/**
	 * Generates clusters using the given list of cluster objects, a distance function, and a clusterer.	
	 * 
	 * @param objectIDs
	 * @param distanceFunction
	 * @param clusterer
	 */
	public void generateClusters(ArrayList<K> objectIDs, DistanceFunction<K, V> distanceFunction, 
			Clusterer<K> clusterer) {
		if(getClusterObjectManager() != null) {
			dg = distanceFunction.generateDistanceGrid(objectIDs, getClusterObjectManager());
			this.clusters = clusterer.generateClusters(objectIDs, dg);
			this.clusters = this.clusters.removeDuplicateClusterGroups(objectIDs, clusters);
		}
	}
	
	public DistanceGrid<K> getDistanceGrid(){
		return this.dg;
	}
}