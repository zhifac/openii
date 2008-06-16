// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.schemastore.graph;

/**
 * Class GraphDomain
 * @author MDMORSE
 */

import java.util.ArrayList;

public class GraphDomain extends GraphObject{
	
	ArrayList<GraphEntity> parentEntities;
	ArrayList<GraphDomainValue> gDomains;
	
	ArrayList<GraphDomain> subtypes;
	ArrayList<GraphDomain> supertypes;
	
	public GraphDomain(int id, String name, String description, int base){
		super(id, name, description, base);
		parentEntities = new ArrayList<GraphEntity>();
		gDomains = new ArrayList<GraphDomainValue>();
		subtypes = new ArrayList<GraphDomain>();
		supertypes = new ArrayList<GraphDomain>();
	}
	
	public GraphDomain getSuperType(int n){
		return supertypes.get(n);
	}
	
	public int getNumberOfSuperTypes(){
		return supertypes.size();
	}
	
	public void addSuperType(GraphDomain gD){
		supertypes.add(gD);
	}
	
	public GraphDomain getSubType(int n){
		return subtypes.get(n);
	}
	
	public int getNumberOfSubTypes(){
		return subtypes.size();
	}
	
	public void addSubType(GraphDomain gD){
		subtypes.add(gD);
	}
	
	public GraphEntity getParentEntity(int n){
		return parentEntities.get(n);
	}
	
	public int getNumberOfParentEntities(){
		return parentEntities.size();
	}
	
	public void addParentEntity(GraphEntity gE){
		parentEntities.add(gE);
	}
	
	public GraphDomainValue getDomainValue(int n){
		return gDomains.get(n);
	}
	
	//I think that only 1 domain is ever possible.
	public int getNumberOfDomains(){
		return gDomains.size();
	}
	
	public void addDomainValue(GraphDomainValue gE){
		gDomains.add(gE);
	}
	
}