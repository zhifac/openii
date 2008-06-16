// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.schemastore.graph;

/**
 * Class GraphAttribute 
 * @author MDMORSE
 */

import org.mitre.schemastore.model.Attribute;

public class GraphAttribute extends GraphObject{
	
	GraphEntity parentEntity;
	GraphDomain DomainType;
	
	/** Stores the attribute's entity id */
	Integer entityID;
	
	/** Stores the attribute's domain id */
	Integer domainID;
	
	/** Stores the attribute's min cardinality */
	Integer min;
	
	/** Stores the attribute's max cardinality */
	Integer max;
	
	public GraphAttribute(int id,String name, String description, int base){
		super(id, name, description, base);
	}
	
	public GraphEntity getParentEntity(){
		return parentEntity;
	}
	
	public void addParentEntity(GraphEntity gE){
		parentEntity = gE;
	}
	
	public GraphDomain getDomain(){
		return DomainType;
	}
	
	public void addDomain(GraphDomain gD){
		DomainType = gD;
	}
	
	public void setParentEntityID(Integer id){
		entityID = id;
	}
	public void setDomainID(Integer id){
		domainID = id;
	}
	
	public int getParentEntityID(){
		return entityID;
	}
	public int getDomainID(){
		return domainID;
	}
	
	public void setMin(Integer min) { this.min = min; }
	public void setMax(Integer max) { this.max = max; }
	public Integer getMin() { return min; }
	public Integer getMax() { return max; }
	
	public void setAttr(Attribute Attr){
		setMin(Attr.getMin());
		setMax(Attr.getMax());
		setParentEntityID(Attr.getEntityID());
		setDomainID(Attr.getDomainID());
	}
}