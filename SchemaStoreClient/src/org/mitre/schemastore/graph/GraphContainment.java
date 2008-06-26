package org.mitre.schemastore.graph;

import org.mitre.schemastore.model.Containment;

public class GraphContainment extends Containment {

	/** Constructs the containment relationship */
	public GraphContainment(Integer id, String name, String description, Integer parentID, Integer childID, Integer min, Integer max, Integer base)
		{ super(id,name,description,parentID,childID,min,max,base); }

	public GraphContainment(Containment c)
		{ super(c.getId(),c.getName(),c.getDescription(),c.getParentID(),c.getChildID(),c.getMin(),c.getMax(),c.getBase()); }
	
}
