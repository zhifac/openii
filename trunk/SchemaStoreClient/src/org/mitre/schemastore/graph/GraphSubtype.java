// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.graph;

import org.mitre.schemastore.model.*;

import java.util.*;

public class GraphSubtype extends Subtype implements GraphSchemaElement{

	private GraphEntity parent;
	private GraphEntity child;
	private GraphAlias alias;
	
	public void setAlias(GraphAlias a){
		alias = a;
	}
	public GraphAlias getAlias(){
		return alias;
	}
	/** Constructs the subtype relationship */
	public GraphSubtype(Integer id, Integer parentID, Integer childID, Integer base)
		{ super(id, parentID, childID, base); }
	
	public GraphSubtype(Subtype s)
		{ super(s.getId(), s.getParentID(), s.getChildID(), s.getBase()); }
	
	public GraphEntity getParent(){
		return parent;
	}
	public GraphEntity getChild(){
		return child;
	}
	public void setChild(GraphEntity passedChild){
		child = passedChild;
	}
	public void setParent(GraphEntity passedParent){
		parent= passedParent;
	}
	
	public ArrayList<SchemaElement> getParents(){
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		retVal.add(parent);
		return retVal;
	}
	
	public ArrayList<SchemaElement> getChildren(){
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		retVal.add(child);
		return retVal;
	}

}
