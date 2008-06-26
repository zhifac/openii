package org.mitre.schemastore.graph;

import org.mitre.schemastore.model.Relationship;

public class GraphRelationship extends Relationship {

	/** Constructs the containment relationship */
	public GraphRelationship(Integer id, String name, Integer leftID, Integer leftMin, Integer leftMax, Integer rightID, Integer rightMin, Integer rightMax, Integer base)	
		{ super(id, name, leftID, leftMin, leftMax, rightID, rightMin, rightMax, base); }
	
	public GraphRelationship(Relationship r)	
		{ super(r.getId(), r.getName(), r.getLeftID(), r.getLeftMin(), r.getLeftMax(), r.getRightID(), r.getRightMin(), r.getRightMax(), r.getBase()); }

}