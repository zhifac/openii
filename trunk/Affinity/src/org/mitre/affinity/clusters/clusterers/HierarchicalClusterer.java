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

package org.mitre.affinity.clusters.clusterers;

import java.util.ArrayList;

import org.mitre.affinity.clusters.*;
import org.mitre.affinity.clusters.distanceFunctions.RandomDistanceFunction;
import org.mitre.affinity.model.AffinitySchemaManager;

/** HierarchicalClusterer Class - will perform hierarchical clustering (configurable linkage Todo) */	
public class HierarchicalClusterer implements Clusterer
{
	private Linker link;
	
	/** Default constructor */
	public HierarchicalClusterer(){
		link = new CompleteLinker();
	}
	
	public ClustersContainer generateClusters(ArrayList<Integer> schemaIDs, DistanceGrid grid){
		ClustersContainer cc = new ClustersContainer(schemaIDs, grid);
		cluster(cc);
		return cc;
	}
	
	/** Generates a set of clusters based on the distance grid */
	public void cluster(ClustersContainer cc){
		//each cc is initialized with 1 clustergroup per schema.  I.e. each schema
		//is initially in its own cluster group at step 0.
		
		for(int counter=0; cc.getClusterStep(counter).getSize() > 1; counter++){
			//get initial cluster step.
			ClusterStep cs1 = cc.getClusterStep(counter);
			ArrayList<ClusterGroup> cgs = new ArrayList<ClusterGroup>(cs1.getClusterGroups());
			//initialize working variables.
			double bestDist = Double.MAX_VALUE;
			int bestM=0;
			int bestN=0;
			//compare the distances of the elements of each clusterGroup.
			for(int m=0; m < cgs.size(); m++){
				for(int n=m+1; n< cgs.size(); n++){
					double tempDist = link.compare(cgs.get(m), cgs.get(n), cc.getDistanceGrid());
					//if this distance is the best so far, record its location.
					if(tempDist < bestDist){
						bestDist = tempDist;
						bestM = m;
						bestN = n;
					}
				}
			}
			//now, merge the closest clusters.
			ClusterGroup one = cgs.get(bestM);
			ClusterGroup two = cgs.get(bestN);
			//remove largest first.
			cgs.remove(bestN);
			cgs.remove(bestM);
			//make a new clusterGroup
			ClusterGroup three = new ClusterGroup();
			three.combineClusterGroups(one);
			three.combineClusterGroups(two);
			three.setDistance(bestDist);
			//add it to the clustergroups.
			cgs.add(three);
			ClusterStep cs2 = new ClusterStep();
			cs2.setGroups(cgs);
			cc.addClusterStep(cs2);
		}
	}
	
	/** Return the name of the clusterer */
	public String getName(){
		return "Hierarchical Clusterer";
	}
	
	/** try me out */
	public static void main(String[] args){
		HierarchicalClusterer hc = new HierarchicalClusterer();
		DistanceGrid dm = new DistanceGrid();
		ArrayList<Integer> schemaAs = new ArrayList<Integer>();
		schemaAs.add(1); schemaAs.add(2); schemaAs.add(3); schemaAs.add(4);schemaAs.add(5);		
		dm = new RandomDistanceFunction().generateDistanceGrid(schemaAs, new AffinitySchemaManager());
		
		ClustersContainer cc = new ClustersContainer(schemaAs, dm);
		hc.cluster(cc);
		System.out.println(cc.toString());
	}
}

/** Perform the comparison between two ClusterGroups */
interface Linker{
	// return the distance between the two ClusterGroups.
	public double compare(ClusterGroup one, ClusterGroup two, DistanceGrid dg);
}

/** Perform Complete Linkage, i.e. return the maximum value between 2 cluster groups */
class CompleteLinker implements Linker{
	public double compare(ClusterGroup one, ClusterGroup two, DistanceGrid dg){
		double distance = Double.MIN_VALUE;
		for(Integer schemaA: one.getSchemaIDs()){
			for(Integer schemaB: two.getSchemaIDs()){
				//System.out.println(dg);
				Double tempDistance = dg.get(schemaA, schemaB);				
				//System.out.println("Distance between " + schemaA + " and " + schemaB + ": " + tempDistance);				
				
				if(distance < tempDistance){
					distance = tempDistance;
				}
			}
		}
		return distance;
	}
}

// other linker methods ...
/*
class SimpleLinker implements Linker{
	public double compare(ClusterGroup one, ClusterGroup two, DistanceGrid dg){
		...
	}
}
*/