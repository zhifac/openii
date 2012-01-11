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

package org.mitre.affinity.algorithms.distance_functions;

import java.util.Collection;

import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.affinity.model.clusters.DistanceGrid;
	
/**
 * Interface for distance function implementations. A distance metric computes the similarity distance
 * between cluster objects and populates these distances in a distance grid.
 * 
 * @author CBONACETO
 *
 * @param <K>
 * @param <V>
 */
public interface DistanceFunction<K extends Comparable<K>, V> {
	/** Return the name of the distance metric */
	public String getName();

	/** Generates a distance grid for the given cluster objects */
	public DistanceGrid<K> generateDistanceGrid(Collection<K> objectIDs, 
			IClusterObjectManager<K, V> clusterObjectManager, 
			IProgressMonitor progressMonitor);
}