package org.openii.schemr.viz;

import java.awt.Component;

public interface Visualizer {

	public static final String NAME = "name";
	public static final String DESC = "description";
	public static final String TYPE = "type";
	public static final String ID = "id";
	public static final String MATCHED = "matched";
	public static final String SCORE = "score";
	public static final String MATCHED_OBJ = "matchedObject";

	public static final String SCHEMA = "schema";
	public static final String ENTITY = "entity";
	public static final String ATTRIBUTE = "attribute";
	public static final String CONTAINMENT = "containment";
	public static final String RELATIONSHIP = "relationship";

	public Component getDisplayComponent();
	
}
