// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.schemastore.graph;

/**
 * Class GraphEntity
 * @author MDMORSE
 */

import java.util.ArrayList;

public class GraphEntity extends GraphObject{
	
	//relationships and containments that are my children.
	ArrayList<GraphObject> childcontainments;
	ArrayList<GraphEntity> childsubtypes;
	//relationships and containments that I am a child of - are my parents.
	ArrayList<GraphObject> parentcontainments;
	ArrayList<GraphEntity> parentsubtypes;
	//relationships
	ArrayList<GraphEntity> outgoingrelationships;
	//my attributes
	ArrayList<GraphAttribute> attributes;
	
	public GraphEntity(int id, String name, String description, int base){
		super(id, name, description, base);
		childcontainments = new ArrayList<GraphObject>();
		outgoingrelationships = new ArrayList<GraphEntity>();
		childsubtypes = new ArrayList<GraphEntity>();
		parentcontainments = new ArrayList<GraphObject>();
		parentsubtypes = new ArrayList<GraphEntity>();
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
	
	public GraphObject getChildContainment(int n){
		return childcontainments.get(n);
	}
	
	public int getNumberOfChildContainments(){
		return childcontainments.size();
	}
	
	public void addChildContainment(GraphObject gE){
		childcontainments.add(gE);
	}
	
	public GraphEntity getOutgoingRelationship(int n){
		return outgoingrelationships.get(n);
	}
	
	public int getNumberOfOutgoingRelationships(){
		return outgoingrelationships.size();
	}
	
	public void addOutgoingRelationship(GraphEntity gE){
		outgoingrelationships.add(gE);
	}
	
	public GraphEntity getChildSubtype(int n){
		return childsubtypes.get(n);
	}
	
	public int getNumberOfChildSubtypes(){
		return childsubtypes.size();
	}
	
	public void addChildSubtype(GraphEntity gE){
		childsubtypes.add(gE);
	}
	// end
	
	public GraphObject getParentContainment(int n){
		return parentcontainments.get(n);
	}
	
	public int getNumberOfParentContainments(){
		return parentcontainments.size();
	}
	
	public void addParentContainment(GraphObject gE){
		parentcontainments.add(gE);
	}
	
	public GraphEntity getParentSubtype(int n){
		return parentsubtypes.get(n);
	}
	
	public int getNumberOfParentSubtypes(){
		return parentsubtypes.size();
	}
	
	public void addParentSubtype(GraphEntity gE){
		parentsubtypes.add(gE);
	}
	
}