// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.

package org.mitre.schemastore.model;

/**
 * Class for storing an attribute
 * @author CWOLF
 */
public class Attribute extends SchemaElement
{
	/** Stores the attribute's entity id */
	private Integer entityID;
	
	/** Stores the attribute's domain id */
	private Integer domainID;
	
	/** Stores the attribute's min cardinality */
	private Integer min;
	
	/** Stores the attribute's max cardinality */
	private Integer max;
	
	/** Constructs a default attribute */
	public Attribute() {}
	
	/** Constructs the attribute */
	public Attribute(Integer id, String name, String description, Integer entityID, Integer domainID, Integer min, Integer max, Integer base)
		{ super(id,name,description,base); this.entityID=entityID; this.domainID=domainID; this.min=min; this.max=max; }
	
	// Handles all attribute getters
	public Integer getEntityID() { return entityID; }
	public Integer getDomainID() { return domainID; }
	public Integer getMin() { return min; }
	public Integer getMax() { return max; }
	
	// Handles all attribute setters
	public void setEntityID(Integer entityID) { this.entityID = entityID; }
	public void setDomainID(Integer domainID) { this.domainID = domainID; }
	public void setMin(Integer min) { this.min = min; }
	public void setMax(Integer max) { this.max = max; }
}
