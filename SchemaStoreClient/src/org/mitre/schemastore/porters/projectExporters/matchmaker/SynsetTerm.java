package org.mitre.schemastore.porters.projectExporters.matchmaker;

/**
 * This is an extension of a node element. The difference is that it keeps track
 * of the schema ID, which is needed to avoid constant look up during
 * clustering.
 * 
 * @author HAOLI
 * 
 */

public class SynsetTerm extends Node implements Comparable<SynsetTerm> {
	public Integer elementId;
	public String elementName;
	public Integer schemaId;

	public SynsetTerm(Integer schemaID, Integer elementId, String elementName) {
		super(elementId.toString());
		this.schemaId = schemaID;
		this.elementId = elementId;
		this.elementName = elementName;
	}

	public int compareTo(SynsetTerm o) {
		return this.toString().compareTo(o.toString());
	}

	public String toString() {
		return schemaId + elementName + elementId;
	}

} // End SynsetTerm
