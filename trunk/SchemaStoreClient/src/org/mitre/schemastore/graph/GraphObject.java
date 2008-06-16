package org.mitre.schemastore.graph;

public abstract class GraphObject{
	
	public int id;
	
	/** Stores the schema element name */
	public String name;
	
	/** Stores the schema element description */
	public String description;

	/** Stores the schema element base ID */
	public Integer base;
	
	public GraphObject(int tID, String n, String d, int b){
		id = tID;
		name = n;
		description = d;
		base = b;
	}
	
	public String toString(){
		return name+description;
	}
}