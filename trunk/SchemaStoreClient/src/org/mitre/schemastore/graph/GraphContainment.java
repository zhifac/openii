// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.graph;

/**
 * Class GraphContainment
 * @author MDMORSE, DBURDICK
 */


import java.util.ArrayList;

import org.mitre.schemastore.model.Containment;
import org.mitre.schemastore.model.SchemaElement;

public class GraphContainment extends Containment implements GraphSchemaElement{

	private GraphEntity parent;
	private SchemaElement child;
	private GraphAlias alias;

	/** Constructs the containment relationship */
	public GraphContainment(Integer id, String name, String description, Integer parentID, Integer childID, Integer min, Integer max, Integer base)
		{ super(id,name,description,parentID,childID,min,max,base); }

	public GraphContainment(Containment c)
		{ super(c.getId(),c.getName(),c.getDescription(),c.getParentID(),c.getChildID(),c.getMin(),c.getMax(),c.getBase()); }
	
	public void setAlias(GraphAlias a){
		alias = a;
	}
	public GraphAlias getAlias(){
		return alias;
	}
	
	public GraphEntity getParent(){
		return parent;
	}
	public SchemaElement getChild(){
		return child;
	}
	public void setChild(SchemaElement passedChild){
		child = passedChild;
	}
	public void setParent(GraphEntity passedParent){
		parent = passedParent;
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
