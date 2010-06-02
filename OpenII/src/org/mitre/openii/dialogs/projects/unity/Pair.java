package org.mitre.openii.dialogs.projects.unity;

/**
 * Class for storing a pair of elements
 * @author DMALLEN
 */
public class Pair<T>
{
	// Stores the pair of objects
	private T item1, item2;

	/** Constructs the pair */
	public Pair(T item1, T item2)
		{ this.item1 = item1; this.item2 = item2; }

	/** Returns the first item */
	public T getItem1() { return item1; }

	/** Returns the second item */
	public T getItem2() { return item2; }

	/** Indicates that two pairs are equal */
	public boolean equals(Object object)
	{
		if(!(object instanceof Pair<?>)) return false;
		Pair<?> pair = (Pair<?>)object;
		if(pair.getItem1().equals(item1) && pair.getItem2().equals(item2)) return true;
		if(pair.getItem1().equals(item2) && pair.getItem2().equals(item1)) return true;
		return false;
	}
	
	/** Displays the pair of items */
	public String toString()
		{ return new String(item1.toString() + " and " + item2.toString()); }
}
