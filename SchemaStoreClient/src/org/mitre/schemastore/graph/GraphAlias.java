package org.mitre.schemastore.graph;

import java.util.ArrayList;

import org.mitre.schemastore.model.Alias;
import org.mitre.schemastore.model.SchemaElement;


public class GraphAlias extends Alias implements GraphSchemaElement {
	
	public GraphAlias (Alias a)
		{ super(a.getId(),a.getName(),a.getElementID(),a.getBase()); }		
		/** Constructs the alias */
	public GraphAlias (Integer id, String name, Integer elementID, Integer base)
		{  super(id, name, elementID, base);}
		
		
	public ArrayList<SchemaElement> getParents(){
		return new ArrayList<SchemaElement>();
	}
	
	public ArrayList<SchemaElement> getChildren(){
		return new ArrayList<SchemaElement>();
	}
	
}
