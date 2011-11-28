package org.mitre.affinity.model;

import java.util.ArrayList;

/**
 * 
 * @author cbonaceto
 *
 * @param <K>
 * @param <V>
 */
public interface IClusterObjectManager<K, V> {	
	
	/** Returns a list of all cluster objects */
	public ArrayList<V> getClusterObjects();
	
	/** Returns the list of all cluster object IDs */
	public ArrayList<K> getClusterObjectIDs();
	
	/** Returns the deletable cluster objects */
	public ArrayList<K> getDeletableClusterObjects();	
	
	/** Returns the specified cluster object */
	public V getClusterObject(K objectID);
	
	/** Returns the name of the specified cluster object */
	public String getClusterObjectName(K objectID);
	
	/** Returns the ID of the cluster object that matches the given identifier */
	public K findClusterObject(String identifier);

	/** Deletes the specified cluster object */
	public boolean deleteClusterObject(K objectID);	
}