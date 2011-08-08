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

import org.mitre.affinity.clusters.ClusterGroup;

public class SelectionEvent {
	/** The object that generated the event */
	public Object eventSource;
	
	/** Current selection of schemas */
	public Collection<Integer> selectedSchemas;	
	
	/** Current seleciton of clusters */
	public Collection<ClusterGroup> selectedClusters;
	
	public SelectionEvent(Object eventSource) {
		this(eventSource, null, null);
	}
	
	public SelectionEvent(Object eventSource, Collection<Integer> selectedSchemas, Collection<ClusterGroup> selectedClusters) {
		this.eventSource = eventSource;
		this.selectedSchemas = selectedSchemas;
		this.selectedClusters = selectedClusters;
	}
	
	public Object getEventSource() {
		return eventSource;
	}

	public Collection<Integer> getSelectedSchemas() {
		return selectedSchemas;
	}	
	public Collection<ClusterGroup> getSelectedClusters() {
		return selectedClusters;
	}
}
