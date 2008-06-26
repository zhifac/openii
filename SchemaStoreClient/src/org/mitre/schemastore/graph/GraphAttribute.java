// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.schemastore.graph;

/**
 * Class GraphAttribute 
 * @author MDMORSE
 */

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.SchemaElement;

public class GraphAttribute extends Attribute {
	
	GraphEntity parentEntity;
	GraphDomain domainType;
	
	/** Constructs the attribute */
	public GraphAttribute(Integer id, String name, String description, Integer entityID, Integer domainID, Integer min, Integer max, Integer base){ 
		super(id,name,description,entityID,domainID,min,max,base);
		parentEntity = null; 
		domainType = null;
		} 
	
	public GraphAttribute (Attribute a){ 
		super(a.getId(),a.getName(),a.getDescription(),a.getEntityID(),a.getDomainID(),a.getMin(),a.getMax(),a.getBase());
		parentEntity = null; 
		domainType = null;	
	} 
	
	// handles class getters  
	public GraphEntity getParentEntity()
		{return parentEntity; }
	
	public GraphDomain getDomainType()
		{ return domainType; }
	
	// handles class setters 
	public void setDomainType(GraphDomain gD)
		{ domainType = gD; }
	
	public void setParentEntity(GraphEntity pE)
		{ parentEntity = pE; }

}


