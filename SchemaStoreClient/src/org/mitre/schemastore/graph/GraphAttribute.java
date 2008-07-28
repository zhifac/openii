// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.schemastore.graph;

/**
 * Class GraphAttribute 
 * @author MDMORSE, DBURDICK
 */

import org.mitre.schemastore.model.Attribute;
import org.mitre.schemastore.model.SchemaElement;
import java.util.*;

public class GraphAttribute extends Attribute implements GraphSchemaElement{
	
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
	public void setDomainType(GraphDomain domain)
		{ domainType = domain; }
	
	public void setParentEntity(GraphEntity entity)
		{ parentEntity = entity; }

	public String toString(){
		return new String(" NAME: " + super.getName() + " ENTITY-ID: " +  super.getEntityID() + 
				" DOMAIN-ID: " + super.getDomainID());
	}
	
	public ArrayList<SchemaElement> getParents(){
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		retVal.add(parentEntity);
		return retVal;
	}
	
	public ArrayList<SchemaElement> getChildren(){
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		retVal.add(parentEntity);
		return retVal;
	}	
	
}


