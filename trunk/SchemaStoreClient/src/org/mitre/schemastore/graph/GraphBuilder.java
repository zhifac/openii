package org.mitre.schemastore.graph;

import java.util.Hashtable;
import java.util.ArrayList;
import org.mitre.schemastore.model.*;

import org.mitre.schemastore.model.SchemaElement;

public class GraphBuilder{
	
	Hashtable<Integer,GraphObject> hashGraph;
	
	public GraphBuilder(){
		
	}
	
	public void build(ArrayList<SchemaElement> schema){
		for(SchemaElement schemaElement : schema){
			if(schemaElement instanceof Attribute){
				
			}
		}
	}
}