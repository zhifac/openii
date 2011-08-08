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

package org.mitre.affinity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.mitre.affinity.clusters.ClusterGroup;
//import org.mitre.affinity.clusters.ClusterStep;
import org.mitre.affinity.clusters.ClusterTermVector;
import org.mitre.affinity.clusters.ClustersContainer;
import org.mitre.affinity.clusters.DistanceGrid;
import org.mitre.affinity.clusters.clusterers.Clusterer;
import org.mitre.affinity.clusters.distanceFunctions.DistanceFunction;
import org.mitre.affinity.util.AffinityUtils;

public class AffinityModel {
	private final ISchemaManager schemaManager;
	private DistanceGrid dg;
	
	/** The clusters */
	private ClustersContainer clusters;
	
	/** Caches cluster term vectors for each cluster */
	private Map<ClusterGroup, ClusterTermVector> clusterTermVectors;
	
	public AffinityModel(ISchemaManager schemaManager) {
		this.schemaManager = schemaManager;
	}	

	public ClustersContainer getClusters() {
		return clusters;
	}

	public void setClusters(ClustersContainer clusters) {
		this.clusters = clusters;
	}	
	
	/**
	 * Generates clusters using the given list of schemas, a distance metric, and a clusterer.	
	 * 
	 * @param schemaIDs
	 * @param distanceMetric
	 * @param clusterer
	 */
	public void generateClusters(ArrayList<Integer> schemaIDs, DistanceFunction distanceMetric, Clusterer clusterer) {
		dg = distanceMetric.generateDistanceGrid(schemaIDs, schemaManager);
		
		//System.out.println("Distance grid: " + grid);
		this.clusters = clusterer.generateClusters(schemaIDs, dg);
		this.clusters = AffinityUtils.removeDuplicateClusterGroups(schemaIDs, clusters);
		this.clusterTermVectors = new HashMap<ClusterGroup, ClusterTermVector>();
	}
	
	public DistanceGrid getDistanceGrid(){
		return this.dg;
	}
	
	public ClusterTermVector getClusterTermVector(ClusterGroup cluster) {
		if(!clusterTermVectors.containsKey(cluster)) {
			clusterTermVectors.put(cluster, new ClusterTermVector(clusters, cluster, schemaManager));
		}
		return clusterTermVectors.get(cluster);
	}	

	public ISchemaManager getSchemaManager() {
		return schemaManager;
	}
}
