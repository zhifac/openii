package org.mitre.affinity.algorithms.clusterers;

import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.DistanceGrid;

/**
 * Perform Single Linkage, i.e. return the minimum value between 2 cluster groups.
 * 
 * @author CBONACETO
 *
 * @param <K>
 */
public class SingleLinker<K extends Comparable<K>> implements ILinker<K> {
	public double compare(ClusterGroup<K> one, ClusterGroup<K> two, DistanceGrid<K> dg){
		double distance = Double.MAX_VALUE;
		for(K objectA: one.getObjectIDs()){
			for(K objectB: two.getObjectIDs()){
				//System.out.println(dg);
				Double tempDistance = dg.get(objectA, objectB);				
				//System.out.println("Distance between " + schemaA + " and " + schemaB + ": " + tempDistance);
				if(tempDistance != null && distance > tempDistance){
					distance = tempDistance;
				}
			}
		}
		return distance;
	}
}