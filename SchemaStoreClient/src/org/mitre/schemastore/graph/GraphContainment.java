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

	/** Constructs the containment relationship */
	public GraphContainment(Integer id, String name, String description, Integer parentID, Integer childID, Integer min, Integer max, Integer base)
		{ super(id,name,description,parentID,childID,min,max,base); }

	public GraphContainment(Containment c)
		{ super(c.getId(),c.getName(),c.getDescription(),c.getParentID(),c.getChildID(),c.getMin(),c.getMax(),c.getBase()); }
	
	public ArrayList<SchemaElement> getParents(){
		return new ArrayList<SchemaElement>();
	}
	
	public ArrayList<SchemaElement> getChildren(){
		return new ArrayList<SchemaElement>();
	}
}
