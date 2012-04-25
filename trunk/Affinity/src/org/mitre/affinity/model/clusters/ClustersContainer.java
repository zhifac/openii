/*
 *  Copyright 2010 The MITRE Corporation (http://www.mitre.org/). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mitre.affinity.model.clusters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Contains the clusters.
 * 
 * @author cbonaceto
 *
 *@param <K>
 *
 */
public class ClustersContainer<K extends Comparable<K>> implements Iterable<ClusterStep<K>> {
	
	/** Stores the distance grid used in generating these clusters */
	private final DistanceGrid<K> grid;	
	
	/** Stores the series of clustering steps */
	private final ArrayList<ClusterStep<K>> clusterSteps = new ArrayList<ClusterStep<K>>();
	
	/** Stores list of all cluster objects in all clusters */
	private final Collection<K> objectIDs;
	
	/** Clusters Constructor */
	public ClustersContainer(Collection<K> objectIDs, DistanceGrid<K> grid) {
		this.grid = grid;
		this.objectIDs = objectIDs;
		
		// Initialize the clusters such that the first step allows each cluster object to be in its own cluster group
		ClusterStep<K> cs = new ClusterStep<K>();
		for(K objectID : objectIDs) {
			ClusterGroup<K> cg = new ClusterGroup<K>();
			cg.addClusterObject(objectID);
			cs.addGroup(cg);
		}
		clusterSteps.add(cs);
	}
	
	/** Gets the distance grid associated with the clusters */
	public DistanceGrid<K> getDistanceGrid() { 
		return grid; 
	}

	/** Gets all of the objects in all clusters
	 * @return
	 */
	public Collection<K> getObjectIDs() {
		return objectIDs;
	}

	/** Add a new cluster step to this container */
	public void addClusterStep(ClusterStep<K> c) { 
		clusterSteps.add(c); 
	}
	
	/** Return a specific cluster step */
	public ClusterStep<K> getClusterStep(int j) { 
		return clusterSteps.get(j); 
	}
	
	/** Return the number of cluster steps */
	public int getNumClusterSteps() { 
		return clusterSteps.size(); 
	}

	/** Iterate through the cluster steps */
	public Iterator<ClusterStep<K>> iterator() { 
		return this.clusterSteps.iterator(); 
	}
	
	/**
	 * Given a set of cluster objects and a set of cluster steps generated using hierarchical clustering,
	 * returns a set of cluster steps where duplicate clusters (i.e., clusters contained in prior steps)
	 * have been removed.  (This essentially means we just leave the last cluster group in each cluster step).
	 * 
	 * @param objectIds - The IDs of each cluster object
	 * @param cc - The clusters container
	 * @return
	 */
	public ClustersContainer<K> removeDuplicateClusterGroups(Collection<K> objectIds, ClustersContainer<K> cc) {
		//System.out.println("Clusters before removing duplicates: ");
		//System.out.println(cc);
		ClustersContainer<K> newCC = new ClustersContainer<K>(objectIds, cc.getDistanceGrid());		
		
		int currStep = 0;
		for(ClusterStep<K> cs : this.clusterSteps) {			
			if(currStep > 0) { 
				ClusterStep<K> newStep = new ClusterStep<K>();
				newStep.addGroup(cs.getClusterGroups().get(cs.getSize() - 1));
				newCC.addClusterStep(newStep);
			}
			currStep++;
		}
		
		return newCC;
	}
	
	/** Outputs the cluster group */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		int j=0;
		for(ClusterStep<K> cs: clusterSteps)
			sb.append("Step "+(j++)+": "+cs.toString()+"\n");
		return sb.toString();
	}
}