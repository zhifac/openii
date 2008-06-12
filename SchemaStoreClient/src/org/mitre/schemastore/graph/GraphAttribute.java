package org.mitre.schemastore.graph;

import java.util.ArrayList;

public class GraphAttribute extends GraphObject{
	
	ArrayList<GraphEntity> parentEntities;
	ArrayList<GraphDomain> gDomains;
	
	public GraphAttribute(GraphEntity parent, GraphDomain myType){
		parentEntities = new ArrayList<GraphEntity>();
		gDomains = new ArrayList<GraphDomain>();
		addDomain(myType);
		addEntity(parent);
	}
	
	public GraphEntity getEntity(int n){
		return parentEntities.get(n);
	}
	
	//I think that only 1 entity is ever possible.
	public int getNumberOfEntities(){
		return parentEntities.size();
	}
	
	public void addEntity(GraphEntity gE){
		parentEntities.add(gE);
	}
	
	public GraphDomain getDomain(int n){
		return gDomains.get(n);
	}
	
	//I think that only 1 domain is ever possible.
	public int getNumberOfDomains(){
		return gDomains.size();
	}
	
	public void addDomain(GraphDomain gE){
		gDomains.add(gE);
	}
	
}