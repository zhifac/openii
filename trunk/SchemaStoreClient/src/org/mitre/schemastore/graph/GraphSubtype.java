// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.graph;

import org.mitre.schemastore.model.*;
import java.util.*;

public class GraphSubtype extends Subtype implements GraphSchemaElement{

	/** Constructs the subtype relationship */
	public GraphSubtype(Integer id, Integer parentID, Integer childID, Integer base)
		{ super(id, parentID, childID, base); }
	
	public GraphSubtype(Subtype s)
		{ super(s.getId(), s.getParentID(), s.getChildID(), s.getBase()); }
	
	public ArrayList<SchemaElement> getParents(){
		return new ArrayList<SchemaElement>();
	}
	
	public ArrayList<SchemaElement> getChildren(){
		return new ArrayList<SchemaElement>();
	}
	
}
