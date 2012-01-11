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
import java.util.Iterator;

/** 
 *  In hierarchical cluster algorithm, each step of algorithm merges 2 cluster groups together.
 *  This class maintains a history of these cluster groups, possibly useful for visualizing
 *  the clusters. 
 */
public class ClusterStep<K extends Comparable<K>> implements Iterable<ClusterGroup<K>> {
	
	/** Stores the list of cluster groups */
	private ArrayList<ClusterGroup<K>> clusterGroups;
	
	/** Default constructor */
	public ClusterStep() { 
		clusterGroups = new ArrayList<ClusterGroup<K>>(); 
	}
	
	/** Add a cluster group to this step */
	public void addGroup(ClusterGroup<K> cg) { 
		clusterGroups.add(cg); 
	}
	
	/** Set the groups that are part of this cluster step */
	public void setGroups(ArrayList<ClusterGroup<K>> cGroups) { 
		clusterGroups = cGroups; 
	}
	
	/** Returns the number of cluster groups at this step */
	public int getSize() { 
		return clusterGroups.size(); 
	}
	
	/** Constructs the cluster */
	ClusterStep(ArrayList<ClusterGroup<K>> cGroups) { 
		this.clusterGroups = new ArrayList<ClusterGroup<K>>(cGroups); 
	}
	
	/** Returns the list of cluster groups at the time step*/
	public ArrayList<ClusterGroup<K>> getClusterGroups() { 
		return new ArrayList<ClusterGroup<K>>(clusterGroups); 
	}
	
	/** Outputs the cluster step */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(clusterGroups != null && !clusterGroups.isEmpty()) {
			for(ClusterGroup<K> cg: clusterGroups)
				sb.append(cg.toString() + ", ");
			sb.delete(sb.length()-2, sb.length());
		}
		return sb.toString();
	}

	public Iterator<ClusterGroup<K>> iterator() {
		return clusterGroups.iterator();
	}
}