package org.mitre.openii.views.manager.mappings.matchmaker;


/**
 * A pair of files. Since all Harmony matching is done between two targets,
 * this is intended to represent a pair of files that either have been matched,
 * or will be matched.
 * 
 * <p>Pair objects are what Permuters will return.  See the GenericPermuter and nextElement() for its relevance.
 * @author DMALLEN
 */
public class Pair {
	public Object a;
	public Object b;

	public Pair(Object a, Object b) {
		this.a = a;
		this.b = b;
	}

	public Object getA() { return a; }
	public Object getB() { return b; }

	public String toString() {
		return new String("Pair: (" + a + ", " + b + ")");
	} // End toString()
} // End FilePair
