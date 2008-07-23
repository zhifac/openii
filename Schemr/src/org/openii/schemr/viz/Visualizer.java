package org.openii.schemr.viz;

public interface Visualizer {

	public static final String NAME = "name";
	public static final String DESC = "description";
	public static final String TYPE = "type";
	public static final String ID = "id";

	public static final String SCHEMA = "schema";
	public static final String ENTITY = "entity";
	public static final String ATTRIBUTE = "attribute";
	public static final String CONTAINMENT = "containment";
	public static final String RELATIONSHIP = "relationship";
	
	void show();

}
