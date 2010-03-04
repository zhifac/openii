package org.mitre.openii.views.manager.projects.matchmaker;

import java.util.ArrayList;

/**
 * This is an extension of a node element. The difference is that it keeps track
 * of the schema ID, which is needed to avoid constant look up during
 * clustering.
 * 
 * @author HAOLI
 * 
 */

public class Term implements Comparable<Term> {
	public String elementName;
	public Integer elementId;
	public Integer schemaId;
	
	public ArrayList<Term> pointers;
	public ArrayList<Double> distances;

	public Term(Integer schemaID, Integer elementId, String elementName) {
		pointers = new ArrayList<Term>();
		distances = new ArrayList<Double>();

		this.schemaId = schemaID;
		this.elementId = elementId;
		this.elementName = elementName;
	}

	public void add(Term t, Double d) {
		pointers.add(t);
		distances.add(new Double(d));
	}
	
	public int compareTo(Term o) {
		return this.toString().compareToIgnoreCase(o.toString());
	}

	public String toString() {
		return schemaId + elementName + elementId;
	}

} // End Term
