// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.graph;

/**
 * Class GraphRelationship
 * @author MDMORSE, DBURDICK
 */


import java.util.ArrayList;

import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.SchemaElement;

public class GraphRelationship extends Relationship implements GraphSchemaElement{

	private GraphEntity parent;
	private GraphEntity child;
	
	/** Constructs the containment relationship */
	public GraphRelationship(Integer id, String name, Integer leftID, Integer leftMin, Integer leftMax, Integer rightID, Integer rightMin, Integer rightMax, Integer base)	
		{ super(id, name, leftID, leftMin, leftMax, rightID, rightMin, rightMax, base); }
	
	public GraphRelationship(Relationship r)	
		{ super(r.getId(), r.getName(), r.getLeftID(), r.getLeftMin(), r.getLeftMax(), r.getRightID(), r.getRightMin(), r.getRightMax(), r.getBase()); }
	
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