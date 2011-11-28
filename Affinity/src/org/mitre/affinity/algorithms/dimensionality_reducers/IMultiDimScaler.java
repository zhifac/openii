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

package org.mitre.affinity.algorithms.dimensionality_reducers;

import java.util.ArrayList;

import org.mitre.affinity.model.PositionGrid;
import org.mitre.affinity.model.clusters.DistanceGrid;

/**
 * 
 * Interface definition for multidimensional scaling implementations. 
 * 
 * @author CBONACETO
 *
 */
public interface IMultiDimScaler<K> {
	/**
	 * @param dg: The distance grid
	 * @param isSymmetric: Whether or not the distance grid is symmetric
	 * @param isMetric: Whether or not the function used to compute the distance grid is metric
	 * @param numDimensions: The number of dimensions to scale to 
	 * @param metric: Whether or not to use metric or non-metric MDS
	 * @return: A position grid that plots each schema in numDimensions-dimensional space
	 */
	public PositionGrid<K> scaleDimensions(DistanceGrid<K> dg, boolean isSymmetric, boolean isMetric, 
			int numDimensions, boolean metric);
	
	/**
	 * Get the cluster object IDs.
	 * 
	 * @return
	 */
	public ArrayList<K> getObjectIDs();
	
	/**
	 * Set the cluster object IDs.
	 * 
	 * @param objectIDs
	 */
	public void setObjectIDs(ArrayList<K> objectIDs);
}