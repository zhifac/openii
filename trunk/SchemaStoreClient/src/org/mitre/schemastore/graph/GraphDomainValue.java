package org.mitre.schemastore.graph;

/**
 * Class GraphDomainValue
 * @author MDMORSE
 */

import java.util.ArrayList;

public class GraphDomainValue extends GraphObject{
	
	GraphDomain parentDomain;
	
	int domainID;
	
	public GraphDomainValue(int id, String name, String description, int base){
		super(id, name, description, base);
	}
	
	public GraphDomain getDomain(){
		return parentDomain;
	}
	
	public void addDomain(GraphDomain parent){
		parentDomain = parent;
	}
	
	public int getDomainID(){
		return domainID;
	}
	
	public void setDomainID(int d){
		domainID = d;
	}
	
}