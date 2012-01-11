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

package org.mitre.affinity.model.schemas;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.algorithms.clusterers.Clusterer;
import org.mitre.affinity.algorithms.distance_functions.DistanceFunction;
import org.mitre.affinity.model.AffinityModel;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.model.clusters.schema_clusters.ClusterTermVector;
import org.mitre.schemastore.model.Schema;

/**
 * @author CBONACETO
 *
 */
public class AffinitySchemaModel extends AffinityModel<Integer, Schema> {
	
	protected final ISchemaManager schemaManager;
	
	/** Caches cluster term vectors for each cluster */
	protected Map<ClusterGroup<Integer>, ClusterTermVector> clusterTermVectors;
	
	public AffinitySchemaModel(ISchemaManager schemaManager) {
		this.schemaManager = schemaManager;
	}	
	
	@Override
	public IClusterObjectManager<Integer, Schema> getClusterObjectManager() {
		return schemaManager;
	}	

	public ISchemaManager getSchemaManager() {
		return schemaManager;
	}

	public ClustersContainer<Integer> getClusters() {
		return clusters;
	}

	public void setClusters(ClustersContainer<Integer> clusters) {
		this.clusters = clusters;
	}
	
	@Override
	public void generateClusters(Collection<Integer> objectIDs, DistanceFunction<Integer, Schema> distanceFunction,
			Clusterer<Integer> clusterer, IProgressMonitor progressMonitor) {
		super.generateClusters(objectIDs, distanceFunction, clusterer, progressMonitor);
		this.clusterTermVectors = new HashMap<ClusterGroup<Integer>, ClusterTermVector>();
	}	
	
	public ClusterTermVector getClusterTermVector(ClusterGroup<Integer> cluster) {
		if(!clusterTermVectors.containsKey(cluster)) {
			clusterTermVectors.put(cluster, new ClusterTermVector(clusters, cluster, schemaManager));
		}
		return clusterTermVectors.get(cluster);
	}	
}