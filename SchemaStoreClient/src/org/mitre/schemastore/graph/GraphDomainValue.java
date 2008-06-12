package org.mitre.schemastore.graph;

import java.util.ArrayList;

public class GraphDomainValue extends GraphObject{
	
	ArrayList<GraphDomain> parentDomains;
	
	public GraphDomainValue(GraphDomain parent){
		parentDomains = new ArrayList<GraphDomain>();
		addDomain(parent);
	}
	
	public GraphDomain getDomain(int n){
		return parentDomains.get(n);
	}
	
	public int getNumberOfDomains(){
		return parentDomains.size();
	}
	
	public void addDomain(GraphDomain parent){
		parentDomains.add(parent);
	}
	
}