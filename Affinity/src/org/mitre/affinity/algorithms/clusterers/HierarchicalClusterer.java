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

import java.util.ArrayList;
import java.util.Collection;

import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.algorithms.distance_functions.RandomDistanceFunction;
import org.mitre.affinity.model.clusters.ClusterGroup;
import org.mitre.affinity.model.clusters.ClusterStep;
import org.mitre.affinity.model.clusters.ClustersContainer;
import org.mitre.affinity.model.clusters.DistanceGrid;
import org.mitre.affinity.model.schemas.AffinitySchemaManager;
import org.mitre.schemastore.model.Schema;
	
/**
 *  HierarchicalClusterer performs hierachical clustering using a configurable linker. The 
 *  default linker is the complete-linkage linker. 
 * 
 * @author CBONACETO
 *
 * @param <K>
 */
public class HierarchicalClusterer<K extends Comparable<K>> implements Clusterer<K> {
	
	private ILinker<K> linker;
	
	/** Default constructor creates a HierarchicalClusterer using the complete-linkage linker */
	public HierarchicalClusterer() {
		this(new CompleteLinker<K>());
	}
	
	/**
	 * Constructor that takes the linker to use.
	 * 
	 * @param linker
	 */
	public HierarchicalClusterer(ILinker<K> linker) {
		this.linker = linker;
	}	
	
	public ILinker<K> getLinker() {
		return linker;
	}

	public void setLinker(ILinker<K> linker) {
		this.linker = linker;
	}

	public ClustersContainer<K> generateClusters(Collection<K> objectIDs, DistanceGrid<K> grid, 
			IProgressMonitor progressMonitor){
		//System.out.println("num objects: " + objectIDs.size());
		ClustersContainer<K> cc = new ClustersContainer<K>(objectIDs, grid);
		cluster(cc, progressMonitor);
		return cc;
	}
	
	/** Generates a set of clusters based on the distance grid */
	public void cluster(ClustersContainer<K> cc) {
		cluster(cc, null);
	}
	
	/** Generates a set of clusters based on the distance grid */
	public void cluster(ClustersContainer<K> cc, IProgressMonitor progressMonitor) {
		//each clusters container is initialized with 1 cluster group per cluster object.  I.e., each cluster object
		//is initially in its own cluster group at step 0.
		
		if(progressMonitor != null) {
			progressMonitor.setMinimum(0);
			progressMonitor.setMaximum(100);
			progressMonitor.setProgress(0);			
		}
		float progressPerIteration = 100f/cc.getObjectIDs().size();
		//System.err.println(cc.getObjectIDs().size());
		for(int counter=0; cc.getClusterStep(counter).getSize() > 1; counter++) {
			//get initial cluster step.
			ClusterStep<K> cs1 = cc.getClusterStep(counter);
			ArrayList<ClusterGroup<K>> cgs = new ArrayList<ClusterGroup<K>>(cs1.getClusterGroups());
			//initialize working variables.
			double bestDist = Double.MAX_VALUE;
			int bestM=0;
			int bestN=0;
			//compare the distances of the elements of each clusterGroup.
			for(int m=0; m < cgs.size(); m++){
				for(int n=m+1; n < cgs.size(); n++){
					double tempDist = linker.compare(cgs.get(m), cgs.get(n), cc.getDistanceGrid());
					//if this distance is the best so far, record its location.
					if(tempDist < bestDist){
						bestDist = tempDist;
						bestM = m;
						bestN = n;
					}
				}
			}
			//now, merge the closest clusters.
			ClusterGroup<K> one = cgs.get(bestM);
			ClusterGroup<K> two = cgs.get(bestN);
			//remove largest first.
			cgs.remove(bestN);
			cgs.remove(bestM);
			//make a new clusterGroup
			ClusterGroup<K> three = new ClusterGroup<K>();
			three.combineClusterGroups(one);
			three.combineClusterGroups(two);
			three.setDistance(bestDist);
			//add it to the clustergroups.
			cgs.add(three);
			ClusterStep<K> cs2 = new ClusterStep<K>();
			cs2.setGroups(cgs);
			cc.addClusterStep(cs2);
			if(progressMonitor != null) {
				progressMonitor.setProgress((int)(counter * progressPerIteration));				
			}
		}
		if(progressMonitor != null) {
			progressMonitor.setProgress(100);
		}
	}
	
	/** Return the name of the clusterer */
	public String getName(){
		return "Hierarchical Clusterer";
	}
	
	/** try me out */
	public static void main(String[] args){
		HierarchicalClusterer<Integer> hc = new HierarchicalClusterer<Integer>();
		DistanceGrid<Integer> dm = new DistanceGrid<Integer>();
		ArrayList<Integer> schemaAs = new ArrayList<Integer>();
		schemaAs.add(1); schemaAs.add(2); schemaAs.add(3); schemaAs.add(4);schemaAs.add(5);		
		dm = new RandomDistanceFunction<Integer, Schema>().generateDistanceGrid(
				schemaAs, new AffinitySchemaManager(), null);
		
		ClustersContainer<Integer> cc = new ClustersContainer<Integer>(schemaAs, dm);
		hc.cluster(cc);
		System.out.println(cc.toString());
	}
}