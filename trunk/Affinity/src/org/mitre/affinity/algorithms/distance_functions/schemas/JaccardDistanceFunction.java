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

package org.mitre.affinity.algorithms.distance_functions.schemas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.affinity.algorithms.IProgressMonitor;
import org.mitre.affinity.algorithms.distance_functions.CommonDistanceMetrics;
import org.mitre.affinity.algorithms.distance_functions.DistanceFunction;
import org.mitre.affinity.model.IClusterObjectManager;
import org.mitre.affinity.model.clusters.ClusterObjectPairValues;
import org.mitre.affinity.model.clusters.DistanceGrid;
import org.mitre.affinity.model.schemas.ISchemaManager;
import org.mitre.schemastore.model.Schema;

/**
 * Computes the Jaccard distance for a set of schemas
 * @author CWOLF
 * Created May 26, 2010
 */
public class JaccardDistanceFunction implements DistanceFunction<Integer, Schema> {
	/** Stores the schema term vectors */
	private HashMap<Integer, SchemaTermVector> termVectors = new HashMap<Integer,SchemaTermVector>();
	
	/** Stores the schema pair intersections */
	private ClusterObjectPairValues<Integer, Double> intersections = new ClusterObjectPairValues<Integer, Double>();

	/** Stores the schema pair unions */
	private ClusterObjectPairValues<Integer, Double> unions = new ClusterObjectPairValues<Integer, Double>();
	
	/** Return the name of the distance function */
	public String getName()
		{ return "Jaccard"; }

	/** Stores the schema intersections */
	public ClusterObjectPairValues<Integer, Double> getIntersections()
		{ return intersections; }
	
	/** Stores the schema unions */
	public ClusterObjectPairValues<Integer, Double> getUnions()
		{ return unions; }

	/** Returns the schema term count */
	public Integer getTermCount(Integer schemaID)
		{ return termVectors.get(schemaID).getTerms().size(); }
	
	@Override
	public DistanceGrid<Integer> generateDistanceGrid(Collection<Integer> objectIDs, 
			IClusterObjectManager<Integer, Schema> clusterObjectManager, IProgressMonitor progressMonitor) {
		if(!(clusterObjectManager instanceof ISchemaManager)) {
			throw new IllegalArgumentException("Error using JaccardDistanceFunction: Can only be used with a schema manager.");
		}
		return generateDistanceGrid(objectIDs, (ISchemaManager)clusterObjectManager);
	}
	
	/** Generates a distance grid for the given schemas */
	public DistanceGrid<Integer> generateDistanceGrid(Collection<Integer> schemaIDs, ISchemaManager schemaManager) {
		ArrayList<Integer> ids = new ArrayList<Integer>(schemaIDs);
		// Generate the schema term vectors
		for(Integer schemaID : schemaIDs) {
			SchemaTermVector termVector = new SchemaTermVector(schemaManager.getSchemaInfo(schemaID));
			termVectors.put(schemaID,termVector);
		}

		// Calculate each schema pair's union, intersection
		for(int i=0; i<schemaIDs.size(); i++) {
			for(int j=i+1; j<schemaIDs.size(); j++)
				calculateUnionAndIntersection(ids.get(i),ids.get(j));
		}
		
		// Generate the distance grid
		DistanceGrid<Integer> distances = new DistanceGrid<Integer>();
		for(int i=0; i<schemaIDs.size(); i++) {
			for(int j=i+1; j<schemaIDs.size(); j++) {
				// Retrieve the schema IDs
				Integer schema1ID = ids.get(i);
				Integer schema2ID = ids.get(j);

				// Retrieve the intersection and union values
				double intersection = intersections.get(schema1ID, schema2ID);
				double union = unions.get(schema1ID,schema2ID);

				// Calculate the distance using Jaccard
				//distances.set(schema1ID,schema2ID,1-(union==0 ? 0 : intersection/union));
				distances.set(schema1ID,schema2ID,CommonDistanceMetrics.computeJaccardDistance(union, intersection));
			}
		}
		return distances;
	}

	/** Calculates the union and intersection of two documents */
	public void calculateUnionAndIntersection(Integer schema1ID, Integer schema2ID) {
		double intersection=0, union=0;
		
		// Retrieve the term vectors for the two schemas
		SchemaTermVector termVector1 = termVectors.get(schema1ID);
		SchemaTermVector termVector2 = termVectors.get(schema2ID);
		
		// Generate a merged list of terms to search through
		HashSet<String> terms = new HashSet<String>();
		terms.addAll(termVector1.getTerms());
		terms.addAll(termVector2.getTerms());
		
		// Calculate the intersection and union of the schema pair
		for(String term : terms)
		{
			int count1 = termVector1.getWordCount(term);
			int count2 = termVector2.getWordCount(term);
			intersection += Math.min(count1,count2);
			union += Math.max(count1, count2);
		}

		// Store the intersection and union values
		intersections.set(schema1ID, schema2ID, intersection);
		unions.set(schema1ID, schema2ID, union);
   }
}