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
import java.util.Iterator;
import java.util.List;

/** Class for storing clusters */
public class ClustersContainer implements Iterable<ClusterStep>
{
	/** Stores the distance grid used in generating these clusters */
	private final DistanceGrid grid;	
	
	/** Stores the series of clustering steps */
	private final ArrayList<ClusterStep> clusterSteps = new ArrayList<ClusterStep>();
	
	/** Stores list of all schemas in all clusters */
	private final List<Integer> schemaIDs;
	
	/** Clusters Constructor */
	public ClustersContainer(List<Integer> schemaIDs, DistanceGrid grid)
	{
		this.grid = grid;
		this.schemaIDs = schemaIDs;
		
		// Initialize the clusters such that the first step allows each schema to be in its own group
		ClusterStep cs = new ClusterStep();
		for(Integer schemaID : schemaIDs)
		{
			ClusterGroup cg = new ClusterGroup();
			cg.addSchema(schemaID);
			cs.addGroup(cg);
		}
		clusterSteps.add(cs);
	}
	
	/** Gets the distance grid associated with the clusters */
	public DistanceGrid getDistanceGrid()
		{ return grid; }

	/** Gets all of the schemas in all clusters
	 * @return
	 */
	public List<Integer> getSchemaIDs() {
		return schemaIDs;
	}

	/** Add a new cluster step to this container */
	public void addClusterStep(ClusterStep c)
		{ clusterSteps.add(c); }
	
	/** Return a specific cluster step */
	public ClusterStep getClusterStep(int j)
		{ return clusterSteps.get(j); }
	
	/** Return the number of cluster steps */
	public int getNumClusterSteps()
		{ return clusterSteps.size(); }

	/** Iterate through the cluster steps */
	public Iterator<ClusterStep> iterator()
		{ return this.clusterSteps.iterator(); }
	
	/** Outputs the cluster group */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		int j=0;
		for(ClusterStep cs: clusterSteps)
			sb.append("Step "+(j++)+": "+cs.toString()+"\n");
		return sb.toString();
	}
}