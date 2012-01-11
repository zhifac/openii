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

import java.util.ArrayList;
import java.util.Collection;

import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.affinity.model.clusters.DistanceGrid;

/** Distance Metric Interface - A distance metric fills in the distance grid between various schemas */	
public class RandomDistanceFunction<K extends Comparable<K>, V> implements DistanceFunction<K, V> {
	/** Return the name of the distance metric */
	public String getName(){ return "Random Distance Function"; }

	/** Generates a distance grid for the given schemas */
	public DistanceGrid<K> generateDistanceGrid(Collection<K> objectIDs, IClusterObjectManager<K, V> clusterObjectManager,
			IProgressMonitor progressMonitor) {
		DistanceGrid<K> dm = new DistanceGrid<K>();
		ArrayList<K> ids = new ArrayList<K>(objectIDs);
		for(int j =0; j < objectIDs.size(); j++){
			for(int k = j+1; k < objectIDs.size(); k++){
				dm.set(ids.get(j), ids.get(k), Math.random());
			}
		}
		return dm;
	}
}