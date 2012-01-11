package org.mitre.affinity.model;

public abstract class ClusterObjectProperties<K, V> {
	
	protected V clusterObject;
	
	public abstract String getName();
	
	public abstract K getObjectId();

	public V getClusterObject() {
		return clusterObject;
	}
}