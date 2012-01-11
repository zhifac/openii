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

package org.mitre.affinity.model.clusters;

/**
 * Stores a cluster object pair value
 * 
 * @author cbonaceto
 *
 * @param <K>
 * @param <D>
 */
public class ClusterObjectPairValue<K extends Comparable<K>, D> {
	
	/** Stores the cluster object IDs */
	private K object1ID, object2ID;

	/** Stores the cluster object pair value */
	private D value;
		
	/** Constructs the cluster object pair value */
	public ClusterObjectPairValue(K object1ID, K object2ID, D value) { 
		this.object1ID = object1ID; 
		this.object2ID = object2ID; 
		this.value = value; 
	}	

	/** Returns the first object ID */
	public K getObject1ID() {
		return object1ID;
	}	

	/** Returns the second object ID */
	public K getObject2ID() {
		return object2ID;
	}
	
	/** Returns the value */
	public D getValue() { 
		return value; 
	}

	/** Allows the value to be reset */
	public void setValue(D value) { 
		this.value = value; 
	}

	/** Displays the schema pair value */
	public String toString() { 
		return "[" + object1ID + "," + object2ID + "] -> " + value; 
	}
}