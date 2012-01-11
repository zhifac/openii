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

package org.mitre.affinity.algorithms.clusterers;

import java.util.Collection;

import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.model.clusters.DistanceGrid;

/** Clusterer Interface - A clusterer clusters the various schemas based on results of the distance grid */	
public interface Clusterer<K extends Comparable<K>> {
	/** Return the name of the clusterer */
	public String getName();

	/** Generates a set of clusters based on the distance grid */
	public ClustersContainer<K> generateClusters(Collection<K> objectIDs, DistanceGrid<K> grid,
			IProgressMonitor progressMonitor);
}