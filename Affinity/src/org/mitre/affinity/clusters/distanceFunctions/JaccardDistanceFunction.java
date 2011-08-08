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

package org.mitre.affinity.clusters.distanceFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.mitre.affinity.clusters.DistanceGrid;
import org.mitre.affinity.clusters.SchemaPairValues;
import org.mitre.affinity.model.ISchemaManager;

/**
 * Computes the Jaccard distance for a set of schemas
 * @author CWOLF
 * Created May 26, 2010
 */
public class JaccardDistanceFunction implements DistanceFunction
{
	/** Stores the schema term vectors */
	private HashMap<Integer,SchemaTermVector> termVectors = new HashMap<Integer,SchemaTermVector>();
	
	/** Stores the schema pair intersections */
	private SchemaPairValues<Integer> intersections = new SchemaPairValues<Integer>();

	/** Stores the schema pair unions */
	private SchemaPairValues<Integer> unions = new SchemaPairValues<Integer>();
	
	/** Return the name of the distance function */
	public String getName()
		{ return "Jaccard"; }

	/** Stores the schema intersections */
	public SchemaPairValues<Integer> getIntersections()
		{ return intersections; }
	
	/** Stores the schema unions */
	public SchemaPairValues<Integer> getUnions()
		{ return unions; }

	/** Returns the schema term count */
	public Integer getTermCount(Integer schemaID)
		{ return termVectors.get(schemaID).getTerms().size(); }
	
	/** Generates a distance grid for the given schemas */
	public DistanceGrid generateDistanceGrid(ArrayList<Integer> schemaIDs, ISchemaManager schemaManager)
	{
		// Generate the schema term vectors
		for(Integer schemaID : schemaIDs)
		{
			SchemaTermVector termVector = new SchemaTermVector(schemaManager.getSchemaInfo(schemaID));
			termVectors.put(schemaID,termVector);
		}

		// Calculate each schema pair's union, intersection
		for(int i=0; i<schemaIDs.size(); i++)
			for(int j=i+1; j<schemaIDs.size(); j++)
				calculateUnionAndIntersection(schemaIDs.get(i),schemaIDs.get(j));
		
		// Generate the distance grid
		DistanceGrid distances = new DistanceGrid();
		for(int i=0; i<schemaIDs.size(); i++)
			for(int j=i+1; j<schemaIDs.size(); j++)
			{
				// Retrieve the schema IDs
				Integer schema1ID = schemaIDs.get(i);
				Integer schema2ID = schemaIDs.get(j);

				// Retrieve the intersection and union values
				double intersection = intersections.get(schema1ID, schema2ID);
				double union = unions.get(schema1ID,schema2ID);

				// Calculate the distances
				distances.set(schema1ID,schema2ID,1-(union==0 ? 0 : intersection/union));
			}
		return distances;
	}

	/** Calculates the union and intersection of two documents */
	public void calculateUnionAndIntersection(Integer schema1ID, Integer schema2ID)
	{
		int intersection=0, union=0;
		
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