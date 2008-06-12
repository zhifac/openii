package org.mitre.schemastore.graph;

import java.util.ArrayList;

public class GraphEntity extends GraphObject{
	
	//relationships and containments that are my children.
	ArrayList<GraphEntity> childcontainments;
	ArrayList<GraphEntity> childrelationships;
	//relationships and containments that I am a child of - are my parents.
	ArrayList<GraphEntity> parentcontainments;
	ArrayList<GraphEntity> parentrelationships;
	//my attributes
	ArrayList<GraphAttribute> attributes;
	
	public GraphEntity(){
		childcontainments = new ArrayList<GraphEntity>();
		childrelationships = new ArrayList<GraphEntity>();
		parentcontainments = new ArrayList<GraphEntity>();
		parentrelationships = new ArrayList<GraphEntity>();
		attributes = new ArrayList<GraphAttribute>();
	}
	
	public GraphAttribute getAttribute(int n){
		return attributes.get(n);
	}
	
	public int getNumberOfAttributes(){
		return attributes.size();
	}
	
	public void addAttribute(GraphAttribute gE){
		attributes.add(gE);
	}
	
	public GraphEntity getChildContainment(int n){
		return childcontainments.get(n);
	}
	
	public int getNumberOfChildContainments(){
		return childcontainments.size();
	}
	
	public void addChildContainment(GraphEntity gE){
		childcontainments.add(gE);
	}
	
	public GraphEntity getChildRelationship(int n){
		return childrelationships.get(n);
	}
	
	public int getNumberOfChildRelationships(){
		return childrelationships.size();
	}
	
	public void addChildRelationship(GraphEntity gE){
		childrelationships.add(gE);
	}
	// end
	
	public GraphEntity getParentContainment(int n){
		return parentcontainments.get(n);
	}
	
	public int getNumberOfParentContainments(){
		return parentcontainments.size();
	}
	
	public void addParentContainment(GraphEntity gE){
		parentcontainments.add(gE);
	}
	
	public GraphEntity getParentRelationship(int n){
		return parentrelationships.get(n);
	}
	
	public int getNumberOfParentRelationships(){
		return parentrelationships.size();
	}
	
	public void addParentRelationship(GraphEntity gE){
		parentrelationships.add(gE);
	}
	
	
}