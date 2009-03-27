package org.mitre.harmony.exporters.matchmaker;


public class SchemaElementNode extends Node implements Comparable<SchemaElementNode> {
	public Integer schemaId; // this becomes Node.name field
	public Integer elementId;
	public String elementName;

	public SchemaElementNode(Integer schemaId, Integer elementId, String elementName) {
		super(elementId.toString());
		this.schemaId = schemaId;
		this.elementId = elementId;
		this.elementName = elementName;
	}

	public int compareTo(SchemaElementNode o) {
		return this.toString().compareToIgnoreCase(o.toString()); 
	}
	
	public String toString(){
		return elementName + elementId; 
	}

} // End SchemaElementNode
