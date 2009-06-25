// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

import java.io.Serializable;

import org.mitre.schemastore.model.graph.HierarchicalGraph;
import org.mitre.schemastore.model.graph.model.GraphModel;

/**
 * Class for storing a mapping schema
 * @author CWOLF
 */
public class MappingSchema implements Serializable
{
	// Constants for storing the schema side
	static public final Integer NONE = 0;
	static public final Integer LEFT = 1;
	static public final Integer RIGHT = 2;
	
	/** Stores the schema id */
	private Integer id;
	
	/** Stores the schema model */
	private String model;
	
	/** Stores the schema side */
	private Integer side;
	
	/** Constructs a default mapping schema */
	public MappingSchema() {}
	
	/** Constructs a mapping schema */
	public MappingSchema(Integer id, String model, Integer side)
		{ this.id = id; this.model = model; this.side = side; }
	
	/** Copies the mapping schema */
	public MappingSchema copy()
		{ return new MappingSchema(getId(),getModel(),getSide()); }
	
	// Handles all mapping schema getters
	public Integer getId() { return id; }
	public String getModel() { return model; }
	public Integer getSide() { return side; }
	
	// Handles all mapping schema setters
	public void setId(Integer id) { this.id = id; }
	public void setModel(String model) { this.model = model; }
	public void setSide(Integer side) { this.side = side; }
	
	/** Retrieves the graph model */
	public GraphModel retrieveGraphModel()
	{
		for(GraphModel graphModel : HierarchicalGraph.getGraphModels())
			if(graphModel.getClass().getName().equals(model)) return graphModel;
		return null;
	}
	
	/** Stores the graph model */
	public void storeGraphModel(GraphModel graphModel)
		{ model = graphModel.getClass().getName(); }
}