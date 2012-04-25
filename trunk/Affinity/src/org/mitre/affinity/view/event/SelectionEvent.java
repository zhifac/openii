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

import java.util.Collection;

import org.mitre.affinity.model.clusters.ClusterGroup;

public class SelectionEvent<K extends Comparable<K>> extends AffinityEvent {
	/** The object that generated the event */
	//public final Object eventSource;
	
	/** Current selection of cluster objects */
	public final Collection<K> selectedClusterObjects;	
	
	/** Current seleciton of clusters */
	public final Collection<ClusterGroup<K>> selectedClusters;
	
	public SelectionEvent(Object eventSource) {
		this(eventSource, null, null);
	}
	
	public SelectionEvent(Object eventSource, Collection<K> selectedClusterObjects, 
			Collection<ClusterGroup<K>> selectedClusters) {
		super(eventSource);
		//this.eventSource = eventSource;
		this.selectedClusterObjects = selectedClusterObjects;
		this.selectedClusters = selectedClusters;
	}
	
	public Object getEventSource() {
		return source;
	}

	public Collection<K> getSelectedClusterObjects() {
		return selectedClusterObjects;
	}

	public Collection<ClusterGroup<K>> getSelectedClusters() {
		return selectedClusters;
	}
}