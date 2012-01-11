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
 * A distance grid contains the distances between all cluster objects, which
 * is computed using a distance function.
 * 
 * @author cbonaceto
 *
 */
public class DistanceGrid<K extends Comparable<K>> extends ClusterObjectPairValues<K, Double> {	
	
	/** Rescales the values in the distance grid such that they are in the range [newMin newMax] */
	public void rescale(double newMin, double newMax) {
		// Determine the current min and max values
		double oldMax = Double.NEGATIVE_INFINITY;
		double oldMin = Double.POSITIVE_INFINITY;
		for(ClusterObjectPairValue<K, Double> distance : values.values()) {
			if(distance.getValue() > oldMax) oldMax = distance.getValue();
			if(distance.getValue() < oldMin) oldMin = distance.getValue();	
		}
		
		// Rescales the distance values
		if(oldMax != oldMin) {
			transform(new RescaleTransform(oldMin,oldMax,newMin,newMax));
		} else {
			//only one value so don't do any transforming
			System.out.println("not transforming because old max equalled old min");
		}
	}
	
	/** Handles the rescaling of values */
	private class RescaleTransform implements Transformation<Double> {
		/** Stores the old and new minimums */
		private double oldMin, newMin;
		
		/** Stores the scaling value */
		private double scalingValue;
		
		/** Constructs the transform */
		private RescaleTransform(double oldMin, double oldMax, double newMin, double newMax) {
			this.oldMin = oldMin;
			this.newMin = newMin;
			scalingValue = (newMax-newMin) / (oldMax-oldMin);
		}
		
		/** Rescales a value */
		public Double transform(Double value) { 
			return (value-oldMin)*scalingValue + newMin; 
		}
	}
}