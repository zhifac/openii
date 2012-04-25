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
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.swt.graphics.Color;
import org.mitre.affinity.AffinityConstants;

/**
 * Contains a set of objects that have been clustered.
 * 
 * @author cbonaceto
 * 
 * @param <K>
 * 
 */
public class ClusterGroup<K extends Comparable<K>> implements Iterable<K> {
	
	/** Stores the list of objects associated with the cluster */
	private SortedSet<K> objectIDs;
	
	/** The "distance" score for this cluster group computed by the clustering algorithm that generated it. */
	private Double distance = 0D;	
	
	private Color dendroColor = AffinityConstants.COLOR_FOREGROUND;
	
	/** Default Constructor */
	public ClusterGroup() { 
		objectIDs = new TreeSet<K>(); 
	}
	
	/** Constructs a cluster that contains the given cluster objects. */
	public ClusterGroup(Collection<K> objectIDs) { 
		this.objectIDs = new TreeSet<K>(objectIDs); 
	}
	
	/** Add a cluster object to this cluster group */
	public void addClusterObject(K objectID) { 
		objectIDs.add(objectID); 
	}
	
	/**
	 * Add a collection of cluster objects to this cluster group
	 */
	public void addClusterObjects(Collection<K> objectIDs) {
		this.objectIDs.addAll(objectIDs);
	}
	
	/**
	 *  Remove a collection of cluster objects from this cluster group
	 */
	public void removeClusterObjects(Collection<K> objectIds) {
		this.objectIDs.removeAll(objectIds);
	}
	
	/** Combine another cluster group with this one */
	public void combineClusterGroups(ClusterGroup<K> cg) { 
		objectIDs.addAll(cg.getObjectIDs()); 
	}	
	
	/** Returns the list of cluster objects associated with the cluster */
	public ArrayList<K> getObjectIDs() { 
		return new ArrayList<K>(objectIDs); 
	}

	/** Indicates if two clusters are equal to one another */
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj) { 
		return (obj instanceof ClusterGroup) ? objectIDs.equals(((ClusterGroup)obj).objectIDs) : false; 
	}
	
	/**
	 * @param cg: A cluster group that may be a subset of this cluster group
	 * @return whether or not cg is a subset of this cluster group
	 */
	public boolean contains(ClusterGroup<K> cg) {
		return objectIDs.containsAll(cg.objectIDs);
	}	
	
	public boolean contains(Collection<K> objectIDs) {
		return this.objectIDs.containsAll(objectIDs);
	}
	
	public boolean containedBy(Set<K> objectIDs) {
		return objectIDs.containsAll(this.objectIDs);
	}
	
	public int getNumClusterObjects() {
		return objectIDs.size();
	}
	
	public Iterator<K> iterator() {
		return objectIDs.iterator();
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Color getDendroColor() {
		return dendroColor;
	}

	public void setDendroColor(Color dendroColor) {
		this.dendroColor = dendroColor;
	}

	/** Outputs the cluster group */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for(K id: objectIDs)
			sb.append(id + ", ");
		sb.delete(sb.length()-2,sb.length());
		sb.append("}");
		return sb.toString();
	}		
}