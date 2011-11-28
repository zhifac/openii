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

import org.mitre.affinity.model.clusters.ClusterGroup;

public abstract class AffinityListenerAdapter<K> implements AffinityListener<K> {
	@Override
	public void clusterObjectSelected(K objectID, Object source) {}

	@Override
	public void clusterObjectUnselected(K objectID, Object source) {}

	@Override
	public void clusterObjectDoubleClicked(K objectID, Object source) {}

	@Override
	public void clusterSelected(ClusterGroup<K> cluster, Object source) {}

	@Override
	public void clusterUnselected(ClusterGroup<K> cluster, Object source) {}

	@Override
	public void clusterDoubleClicked(ClusterGroup<K> cluster, Object source) {}
}
