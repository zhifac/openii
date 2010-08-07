package org.mitre.openii.dialogs.projects.unity;

import java.util.ArrayList;


/**
 * The idea behind the permuter is that you give it a set of objects (a
 * directory of files or a list of files) and it will produce a set of pairs.
 * For example, you give it a directory containing A, B, C and it will return:
 * A, B A, C B, C This should be a minimal list of all possible combinations
 * (not counting mirrored combinations)
 * 
 * <p>
 * This will be useful for feeding APIMatch with the next grouping it needs to
 * execute.
 * 
 * @author DMALLEN
 * 
 */
public class Permuter<T> implements java.util.Enumeration<Pair<T>> {
	/** Stores the list of items in need of permutation */
	private ArrayList<T> items = new ArrayList<T>();

	// Tracks the current pair of items being examined
	private int loc1 = 0, loc2 = 0;

	/** Stores list of excluded permutations */
//	private ArrayList<Pair<T>> excludedPairs = new ArrayList<Pair<T>>();

	/** Constructs the permuter */
	public Permuter(ArrayList<T> items) {
		this.items = items;
	}

//	/** Add a blocked pair */
//	public void addExcludedPair(Pair<T> pair) {
//		excludedPairs.add(pair);
//	}

	/** Determines if there are any more permutations */
	// public boolean hasMoreElements()
	// { return items.size()>1 && (loc2<items.size()-1 || loc1<loc2-1); }

	public boolean hasMoreElements() {
		return size() >= 1 && (loc2 < items.size() - 1 || loc1 < loc2 - 1);
	}

	/** Returns the permutation count */
	public int size() {
		return (items.size() * (items.size() - 1)) / 2 ; // - excludedPairs.size();
	}

	/** Returns the next pair of elements in the permutation */
	public Pair<T> nextElement() {
		// Determines if there are any more pairs left in the permutation
		if (!hasMoreElements()) return null;

		// Increment the positions in the permutation
		loc2++;
		if (loc2 >= items.size()) {
			loc1++;
			loc2 = loc1 + 1;
		}

		// Return the next pair in this permutation
		Pair<T> pair = new Pair<T>(items.get(loc1), items.get(loc2));
//		if (excludedPairs.contains(pair)) return nextElement();
		return pair;
	}
}
