package org.mitre.openii.views.manager.mappings.matchmaker;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;


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
public class Permuter implements java.util.Enumeration<Pair> {

	private static final long serialVersionUID = 1L;
	ArrayList<Object> items = new ArrayList<Object>();

	boolean started;
	Hashtable<Object, Boolean> permuted;
	int headIDX = 0;
	int tailIDX = 0;

	/**
	 * Create a Permuter with a list of objects to permute.
	 * 
	 * @param files
	 *            the list of files to use as the basis for permutation
	 * @throws Exception
	 *             if the list has < 2 items.
	 */
	public Permuter(ArrayList<?> objList) throws Exception {
		for (Object o : objList)
			this.items.add(o);

		Informant.status(this.items.size() + " items permuted ");
		if (this.items.size() <= 1)
			throw new Exception("Cannot permute empty or singleton list of files!");
	}
	
	/**
	 * Call this method if you want the permuter to start over at the top of the
	 * list and forget all of the combinations it has previously permuted.
	 */
	public void reset() {
		headIDX = 0;
		tailIDX = 0;
	}

	/**
	 * Remove files matching a pattern from the permuter. This is useful if you
	 * don't want the permuter to include certain files in its match
	 * combinations. For example, some files (like common includes) have to be
	 * in the directory, but shouldn't be included in the match. Use this to
	 * filter them out **BEFORE** you call the nextElement() function.
	 * 
	 * @param pattern
	 * @throws Exception
	 *             if you try to call this function after you have already
	 *             fetched one or more permutations.
	 */
	public void exclude(String pattern) throws Exception {
		if (started)
			throw new Exception("Really bad idea to exclude files after you've already started.  Forget about it.");

		ArrayList<Object> filtered = new ArrayList<Object>();

		for (int x = 0; x < items.size(); x++) {
			if (items.get(x).toString().matches(pattern))
				continue;
			filtered.add(items.get(x));
		} // End for

		items = filtered;
	} // End exclude

	/**
	 * @see Enumeration#hasMoreElements()
	 */
	public boolean hasMoreElements() {
		if (tailIDX == (items.size() - 1) && headIDX == (tailIDX - 1))
			return false;

		if (headIDX >= items.size() - 1)
			return false;
		return true;
	} // End hasMoreElements

	public int countTotalPermutations() { 
		int n = items.size();
		return (n*(n-1))/2;
	}
	
	/**
	 * All objects returned from this method are FilePair objects.
	 * 
	 * @see Enumeration#nextElement()
	 */
	public Pair nextElement() {
		started = true;
		if (!hasMoreElements())
			return null;

		// Auto-increment the tail. (It starts at 0, and the first comparison
		// should be (0, 1))
		tailIDX++;

		// If tail has advanced past the end of the list...
		if (tailIDX >= items.size()) {
			// Move the head forward
			headIDX++;
			// And make the tail the very next one.
			tailIDX = headIDX + 1;
		} // End if

		return new Pair(items.get(headIDX), items.get(tailIDX));
	} // End nextElements

	public ArrayList<Object> getElements() {
		return items;
	}
} // End Permuter


