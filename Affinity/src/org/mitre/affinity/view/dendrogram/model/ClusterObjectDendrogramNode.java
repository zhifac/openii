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

package org.mitre.affinity.view.dendrogram.model;

import org.mitre.affinity.model.clusters.ClusterGroup;

import edu.iu.iv.visualization.dendrogram.model.BasicDendrogramNode;

public class ClusterObjectDendrogramNode<K extends Comparable<K>> extends BasicDendrogramNode {
	
	private ClusterGroup<K> children;
	
	private K objectID;
	
	public ClusterObjectDendrogramNode(float value, String label, K objectID) {
		super(value, label);
		this.objectID = objectID;
	}
	
	public ClusterObjectDendrogramNode(float value, ClusterObjectDendrogramNode<K> leftChild, ClusterObjectDendrogramNode<K> rightChild) {
		super(value, leftChild, rightChild);
	}

	public ClusterGroup<K> getChildren() {
		return children;
	}

	public void setChildren(ClusterGroup<K> children) {
		this.children = children;
	}

	public K getObjectID() {
		return objectID;
	}

	public void setObjectID(K objectID) {
		this.objectID = objectID;
	}
}