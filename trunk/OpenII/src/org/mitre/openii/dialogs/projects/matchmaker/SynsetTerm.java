package org.mitre.openii.dialogs.projects.matchmaker;

import java.util.ArrayList;

/**
 * This is an extension of a node element. The difference is that it keeps track
 * of the schema ID, which is needed to avoid constant look up during
 * clustering.
 * 
 * @author HAOLI
 * 
 */

public class SynsetTerm implements Comparable<SynsetTerm> {
	public String elementName;
	public Integer elementId;
	public Integer schemaId;
	
	public ArrayList<SynsetTerm> pointers;
	public ArrayList<Double> distances;

	public SynsetTerm(Integer schemaID, Integer elementId, String elementName) {
		pointers = new ArrayList<SynsetTerm>();
		distances = new ArrayList<Double>();

		this.schemaId = schemaID;
		this.elementId = elementId;
		this.elementName = elementName;
	}

	public void add(SynsetTerm t, Double d) {
		pointers.add(t);
		distances.add(new Double(d));
	}
	
	public int compareTo(SynsetTerm o) {
		return this.toString().compareToIgnoreCase(o.toString());
	}

	public String toString() {
		return schemaId + elementName + elementId;
	}

} // End SynsetTerm
