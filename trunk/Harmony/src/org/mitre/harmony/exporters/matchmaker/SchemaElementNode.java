package org.mitre.harmony.exporters.matchmaker;

import java.util.ArrayList;


public class SchemaElementNode extends Node implements Comparable<SchemaElementNode> {
//	public Integer schemaId; 
	public Integer elementId;
	public String elementName;
	public ArrayList<Integer> schemaIds; 
	
	public SchemaElementNode(ArrayList<Integer> schemaIds, Integer elementId, String elementName) {
		super(elementId.toString());
		this.schemaIds = ( schemaIds == null )?  new ArrayList<Integer> () : schemaIds;
		this.elementId = elementId;
		this.elementName = elementName;
	}

//	public SchemaElementNode(Integer schemaId, Integer elementId, String elementName) {
//		super(elementId.toString());
//		this.schemaId = schemaId;
//		this.elementId = elementId;
//		this.elementName = elementName;
//	}

	public int compareTo(SchemaElementNode o) {
		return this.toString().compareToIgnoreCase(o.toString()); 
	}
	
	public String toString(){
		return elementName + elementId; 
	}

} // End SchemaElementNode
