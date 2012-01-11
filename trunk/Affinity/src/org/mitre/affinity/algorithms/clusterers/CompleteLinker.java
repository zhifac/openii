package org.mitre.affinity.algorithms.clusterers;

import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.DistanceGrid;

/**
 * Perform Complete Linkage, i.e. return the maximum value between 2 cluster groups
 * 
 * @author CBONACETO
 *
 * @param <K>
 */
public class CompleteLinker<K extends Comparable<K>> implements ILinker<K> {
	public double compare(ClusterGroup<K> one, ClusterGroup<K> two, DistanceGrid<K> dg){
		double distance = Double.MIN_VALUE;
		for(K objectA: one.getObjectIDs()) {
			for(K objectB: two.getObjectIDs()){
				Double tempDistance = dg.get(objectA, objectB);
				if(tempDistance != null && distance < tempDistance){
					distance = tempDistance;
				}
				//DEBUG CODE
				if(tempDistance == null) {
					System.out.println("Warning, distance between " + objectA + " and " + objectB + " is null");
				}
				//System.out.println("Distance between " + objectA + " and " + objectB + ": " + tempDistance);
				//END DEBUG CODE
			}
		}
		//System.out.println(distance);
		return distance;
	}
}