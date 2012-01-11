package org.mitre.affinity.model;

import java.util.Collection;

/**
 * Interface for implementations that manage the storage and retrieval
 * of cluster objects.
 * 
 * @author cbonaceto
 *
 * @param <K> 
 * @param <V>
 */
public interface IClusterObjectManager<K extends Comparable<K>, V> {	
	
	/** Returns a list of all cluster objects */
	public Collection<V> getClusterObjects();
	
	/** Returns the list of all cluster object IDs */
	public Collection<K > getClusterObjectIDs();
	
	/** Returns the deletable cluster objects */
	public Collection<K> getDeletableClusterObjects();	
	
	/** Returns the specified cluster object */
	public V getClusterObject(K objectID);
	
	/** Returns the name of the specified cluster object */
	public String getClusterObjectName(K objectID);
	
	/** Returns the ID of the cluster object that matches the given identifier */
	public K findClusterObject(String identifier);

	/** Deletes the specified cluster object */
	public boolean deleteClusterObject(K objectID);	
}