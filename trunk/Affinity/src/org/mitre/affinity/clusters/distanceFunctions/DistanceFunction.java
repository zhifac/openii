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

import org.mitre.affinity.clusters.DistanceGrid;
import org.mitre.affinity.model.ISchemaManager;

/** Distance Metric Interface - A distance metric fills in the distance grid between various schemas */	
public interface DistanceFunction
{
	/** Return the name of the distance metric */
	public String getName();

	/** Generates a distance grid for the given schemas */
	public DistanceGrid generateDistanceGrid(ArrayList<Integer> schemaIDs, ISchemaManager schemaManager);
}