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

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Class for storing cluster object pair values.
 * 
 * @author cbonaceto
 *
 * @param <K>
 * @param <D>
 */
public class ClusterObjectPairValues<K extends Comparable<K>, D> {	
	
	/** Interface used to define how to transform an element */
	public interface Transformation<D> { 
		public D transform(D value); 
	}
	
	/** Stores the cluster object pair values */
	protected HashMap<String, ClusterObjectPairValue<K, D>> values = new HashMap<String, ClusterObjectPairValue<K, D>>();
	
	// Generates the key to retrieve the grid value
	private String getKey(K object1ID, K object2ID) {
		//if(schema1ID > schema2ID) { 
		if(object1ID.hashCode() > object2ID.hashCode()) {
			return object2ID + " " + object1ID;
			//int tempSchemaID = schema1ID; 
			//schema1ID = schema2ID; 
			//schema2ID = tempSchemaID; 
		}
		return object1ID + " " + object2ID;
	}
	
	/** Sets a cluster object pair value */
	public void set(K object1ID, K object2ID, D value) { 
		values.put(getKey(object1ID, object2ID), new ClusterObjectPairValue<K, D>(object1ID, object2ID, value)); 
	}

	/** Returns the value for the given cluster object pair */
	public D get(K object1ID, K object2ID) { 
		try { 
			return values.get(getKey(object1ID, object2ID)).getValue();
		} catch(NullPointerException ex) {
			return null;
		}
	}

	/** Returns all cluster object pair values */
	public ArrayList<ClusterObjectPairValue<K, D>> getValues() { 
		return new ArrayList<ClusterObjectPairValue<K, D>>(values.values()); 
	}
	
	/** Transforms cluster object pair values using the given transformation */
	public void transform(Transformation<D> transformation) {
		for(ClusterObjectPairValue<K, D> value : values.values())
			value.setValue(transformation.transform(value.getValue()));
	}
	
	/** Displays the cluster object pair values */
	public String toString() {
		StringBuffer out = new StringBuffer();
		for(ClusterObjectPairValue<K, D> value : values.values())
			out.append(value + "\n");
		return out.toString();
	}
}