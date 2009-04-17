package org.mitre.harmony.exporters.matchmaker;



public class SchemaElementNode extends Node implements Comparable<SchemaElementNode> {
//	public Integer schemaId; 
	public Integer elementId;
	public String elementName;
	public Integer schemaId;
	
//	public SchemaElementNode(ArrayList<Integer> schemaIds, Integer elementId, String elementName) {
//		super(elementId.toString());
//		this.schemaIds = ( schemaIds == null )?  new ArrayList<Integer> () : schemaIds;
//		this.elementId = elementId;
//		this.elementName = elementName;
//	}
	
	public SchemaElementNode(Integer schemaID, Integer elementId, String elementName) {
		super(elementId.toString());
		this.schemaId = schemaID;
		this.elementId = elementId;
		this.elementName = elementName;
	}

	public int compareTo(SchemaElementNode o) {
		return this.toString().compareToIgnoreCase(o.toString()); 
	}
	
	public String toString(){
		return schemaId + elementName + elementId; 
	}

} // End SchemaElementNode
