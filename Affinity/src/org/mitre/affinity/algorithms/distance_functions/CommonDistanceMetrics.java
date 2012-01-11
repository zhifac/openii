package org.mitre.affinity.algorithms.distance_functions;

import java.util.Set;
import java.util.TreeSet;

/**
 * Set of static methods for computing several commonly-used distance
 * metrics (e.g., Jaccard distance, Cosine distance). These
 * methods may be used by distance functions in this package.
 * 
 * @author CBONACETO
 *
 */
public class CommonDistanceMetrics {
	
	/**
	 * Compute the Jaccard distance between two sets given the size of their
	 * union and the size of their intersection. Jaccard distance
	 * is 1 - (intersectionSize/unionSize).
	 * 
	 * @param unionSize size of the union of two sets
	 * @param intersectionSize size of the intersection of two sets
	 * @return the Jaccard distance between the sets
	 */
	public static double computeJaccardDistance(double unionSize, double intersectionSize) {
		return 1 - (unionSize==0 ? 0 : intersectionSize/unionSize);
	}
	
	/**
	 * Compute the Jaccard distance between two sets. Jaccard distance
	 * is 1 - (intersectionSize/unionSize).
	 * 
	 * @param s1 Set 1
	 * @param s2 Set 2
	 * @return Jaccard distance between s1 and s2
	 */
	public static <T> double computeJaccardDistance(Set<T> s1, Set<T> s2) {
		double unionSize = union(s1, s2).size();
		if(unionSize == 0) {
			return 1;
		} else {
			//System.out.println("Intersection: " + intersection(s1,s2) + ", Union: " + union(s1,s2));
			return 1 - (intersection(s1, s2).size()/unionSize);
		}		
	}
	
	public static <T> Set<T> union(Set<T> s1, Set<T> s2) {
		Set<T> tmp = new TreeSet<T>(s1);
		tmp.addAll(s2);
		return tmp;
	}

	public static <T> Set<T> intersection(Set<T> s1, Set<T> s2) {
		Set<T> tmp = new TreeSet<T>();
		if(s1 == null || s1.isEmpty() || s2 == null || s2.isEmpty()) {
			return tmp;
		}
		for (T x : s1) {
			if (s2.contains(x)) {
				tmp.add(x);
			}
		}
		return tmp;
	}
}