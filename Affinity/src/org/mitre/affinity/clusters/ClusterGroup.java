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

package org.mitre.affinity.clusters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/** Class for storing a cluster */
public class ClusterGroup implements Iterable<Integer>
{
	/** Stores the list of schemas associated with the cluster */
	private SortedSet<Integer> schemaIDs;
	
	/**
	 * The "distance" score for this cluster group computed by the clustering algorithm that generated it.
	 */
	private Double distance = 0D;	
	
	private Color dendroColor = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	
	/** Default Constructor */
	public ClusterGroup()
		{ schemaIDs = new TreeSet<Integer>(); }
	
	/** Add a schema to this cluster group */
	public void addSchema(Integer schemaId)
		{ schemaIDs.add(schemaId); }
	
	/**
	 * Add a collection of schemas to this cluster group
	 */
	public void addSchemas(Collection<Integer> schemaIds) {
		this.schemaIDs.addAll(schemaIds);
	}
	
	/** combine another cluster group with this one */
	public void combineClusterGroups(ClusterGroup cg)
		{ schemaIDs.addAll(cg.getSchemaIDs()); }
	
	/** Constructs the cluster */
	public ClusterGroup(Collection<Integer> schemaIDs)
		{ this.schemaIDs = new TreeSet<Integer>(schemaIDs); }
	
	/** Returns the list of schemas associated with the cluster */
	public ArrayList<Integer> getSchemaIDs()
		{ return new ArrayList<Integer>(schemaIDs); }

	/** Indicates if two clusters are equal to one another */
	public boolean equals(Object obj)
		{ return (obj instanceof ClusterGroup) ? schemaIDs.equals(((ClusterGroup)obj).schemaIDs) : false; }
	
	/**
	 * @param cg: A cluster group that may be a subset of this cluster group
	 * @return whether or not cg is a subset of this cluster group
	 */
	public boolean contains(ClusterGroup cg) {
		return this.schemaIDs.containsAll(cg.schemaIDs);
	}
	
	public boolean contains(Collection<Integer> schemaIDs) {
		return this.schemaIDs.containsAll(schemaIDs);
	}
	
	public int getNumSchemas() {
		return this.schemaIDs.size();
	}
	
	public Iterator<Integer> iterator() {
		return this.schemaIDs.iterator();
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
		for(Integer sId: schemaIDs)
			sb.append(sId + ", ");
		sb.delete(sb.length()-2,sb.length());
		sb.append("}");
		return sb.toString();
	}

	
	//Beth added for dendrogram
	/**
	 * Add a collection of schemas to REMOVE from this cluster group
	 */
	public void removeSchemas(Collection<Integer> schemaIds) {
		this.schemaIDs.removeAll(schemaIds);
	}
	
}