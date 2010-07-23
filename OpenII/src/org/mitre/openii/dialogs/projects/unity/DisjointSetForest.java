package org.mitre.openii.dialogs.projects.unity;

import java.util.Arrays;
import java.util.BitSet;

/**
 * @author PMORK
 * This class implements the disjoint-set-forest algorithm for the union-find
 * problem.  It allows arbitrary comparable objects as the things to be merged
 * using the merge operation. 
 * @param <V> Any type that implements the comparable interface so the objects can be sorted.
 */
public class DisjointSetForest <V extends Comparable<V>> {
	
	/**
	 * @author PMORK
	 * This interface retrieves tells the algorithm to which container each
	 * object belongs.  The merge operation will not merge two objects that
	 * belong to the same container unless the <code>allowNxN</code> boolean
	 * has been set.  Otherwise, the merge operation will ensure that two objects
	 * in the same container are never merged.
	 * @param <V>
	 */
	public interface ContainerMethod <V> {
		/**
		 * @param v An object in the array passed to the DisjointSetForest constructor.
		 * @return An integer in the range 0..numContainers-1 indicating the id of the
		 * container that owns this object.
		 */
		public int getContainerFor(V v);
	}

	private final V[] data;
	private final int[] parents;
	private final int[] ranks;
	private final BitSet[] bitMaps;
	
	/**
	 * Constructs a new forest in which every object in <code>data</code> is placed
	 * in its own subset.
	 * @param data A set of objects.
	 * @param containerMethod A method that indicates the container for each object in <code>data</code>.
	 * @param numContainers The number of containers that own the objects in <code>data</code>.
	 */
	public DisjointSetForest(V[] data, ContainerMethod<V> containerMethod, int numContainers) {
		this.data = data;
		this.bitMaps = new BitSet[data.length];
		parents = new int[data.length];
		ranks = new int[data.length];
		for (int i = 0; i < parents.length; i++) {
			// Each object begins in its own set, so the initial depth of each tree is 1.
			parents[i] = i;
			ranks[i] = 0;
			// Because each object begins in its own subset, every bitmap sets the bit for its container.
			int container = containerMethod.getContainerFor(data[i]);
			bitMaps[i] = new BitSet(numContainers);
			bitMaps[i].set(container);
		}
	}
	/**
	 * Tries to merge the two objects into the same set.  This operation will
	 * silently fail if the two objects cannot be merged because the <code>allowNxN</code>
	 * variable hasn't been set and two objects from the same container would be merged.
	 * @param v1 Any object from the initial array.
	 * @param v2 Any object from the initial array.
	 * @param allowNxN If set, two objects from the same container can be merged.
	 */
	public void merge(V v1, V v2, boolean allowNxN) {
		int v1Root = find(v1);
		int v2Root = find(v2);
		BitSet bm1 = getBitMap(v1Root);
		BitSet bm2 = getBitMap(v2Root);
		if (allowNxN || !bm1.intersects(bm2)) {
			merge(v1Root, v2Root);
			bm1.or(bm2);
			int newRoot = find(v1);
			bitMaps[newRoot] = bm1;
		}
	}
	/**
	 * @param v Any object from the initial array.
	 * @return The set into which this object has been placed; if find(v1) == find(v2)
	 * then v1 and v2 are in the same set.
	 */
	public int find(V v) {
		return find(lookup(v));
	}
	/**
	 * Utility function that prints the forest to System.out.
	 */
	public void printAll() {
		for (V datum : data) {
			int root = find(datum);
			BitSet bitMap = getBitMap(root);
			System.out.print(datum + ":" + root + "(" + bitMap + "),");
		}
		System.out.println();
	}
	private void merge(int v1Root, int v2Root) {
		if (ranks[v1Root] > ranks[v2Root]) parents[v2Root] = v1Root;
		else if (ranks[v1Root] < ranks[v2Root]) parents[v1Root] = v2Root;
		else if (v1Root != v2Root) {
			parents[v2Root] = v1Root;
			ranks[v1Root]++;
		}
	}
	private int find(int index) {
		if (parents[index] == index) return index;
		parents[index] = find(parents[index]);
		return parents[index];
	}
	private int lookup(V v) {
		return Arrays.binarySearch(data, v);
	}
	private BitSet getBitMap(int root) {
		return bitMaps[root];
	}
	public static void main(String[] args) {
		final String[] data = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
		ContainerMethod<String> method = new ContainerMethod<String>() {
			public int getContainerFor(String v) {
				return Arrays.binarySearch(data, v) / 3;
			}
		};
		DisjointSetForest<String> test = new DisjointSetForest<String>(data,method,3);
		test.printAll();
		test.merge("a", "d", false);
		System.out.println("A/D merged");
		test.printAll();
		test.merge("a", "g", true);
		System.out.println("A/D/G merged");
		test.printAll();
		test.merge("a", "h", true);
		System.out.println("A/D/G/H merged");
		test.printAll();
		test.merge("d", "i", false);
		System.out.println("A/D/G/H merged, but I is not merged");
		test.printAll();
		test.merge("c", "i", false);
		System.out.println("C/I merged");
		test.printAll();
		System.out.println("Done!");
	}
	
}