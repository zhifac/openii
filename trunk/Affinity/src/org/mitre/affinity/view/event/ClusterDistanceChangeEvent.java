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

package org.mitre.affinity.view.event;

/**
 * @author CBONACETO
 *
 */
public class ClusterDistanceChangeEvent extends AffinityEvent {
	
	public final double newMinDistance;
	
	public final double newMaxDistance;
	
	public ClusterDistanceChangeEvent(Object source, double newMinDistance, double newMaxDistance) {
		super(source);
		this.newMinDistance = newMinDistance;
		this.newMaxDistance = newMaxDistance;		
	}

	public double getNewMinDistance() {
		return newMinDistance;
	}

	public double getNewMaxDistance() {
		return newMaxDistance;
	}
}