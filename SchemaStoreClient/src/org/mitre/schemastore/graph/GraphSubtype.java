package org.mitre.schemastore.graph;

import org.mitre.schemastore.model.Relationship;
import org.mitre.schemastore.model.Subtype;

public class GraphSubtype extends Subtype {

	/** Constructs the subtype relationship */
	public GraphSubtype(Integer id, Integer parentID, Integer childID, Integer base)
		{ super(id, parentID, childID, base); }
	
	public GraphSubtype(Subtype s)
		{ super(s.getId(), s.getParentID(), s.getChildID(), s.getBase()); }
	
	
}
