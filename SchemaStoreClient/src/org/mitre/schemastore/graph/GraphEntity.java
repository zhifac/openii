// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.schemastore.graph;

/**
 * Class GraphEntity
 * @author MDMORSE
 */

import java.util.ArrayList;
import org.mitre.schemastore.model.*;

public class GraphEntity extends Entity {
	
	// parents and child types (supertype and subtype)
	private ArrayList<GraphEntity> childSubtypes;
	private ArrayList<GraphEntity> parentSubtypes;
	
	// child domains and other entities contained or related
	private ArrayList<GraphDomain> childDomains;
	private ArrayList<GraphEntity> childEnititiesContained;
	private ArrayList<GraphEntity> leftEntitiesRelated;

	// child attributes
	private ArrayList<GraphAttribute> childAttributes;

	// parent entities which are contained or related
	private ArrayList<GraphEntity> parentEnitiesContained;
	private ArrayList<GraphEntity> rightEntitiesRelated;
	
	// store containments and relationships
	private ArrayList<GraphContainment> parentContainments; // containments where this entity IS PARENT
	private ArrayList<GraphContainment> childContainments;  // containments where this entity IS CHILD
	private ArrayList<GraphRelationship> relationships;
	
	
	public GraphEntity(Integer id, String name, String description, int base){
		super(id, name, description, base);
		childSubtypes  = new ArrayList<GraphEntity>();
		parentSubtypes = new ArrayList<GraphEntity>();
		childDomains   = new ArrayList<GraphDomain>();;
		childEnititiesContained = new ArrayList<GraphEntity>();
		rightEntitiesRelated    = new ArrayList<GraphEntity>();
		childAttributes         = new ArrayList<GraphAttribute>();
		parentEnitiesContained = new ArrayList<GraphEntity>();
		leftEntitiesRelated  = new ArrayList<GraphEntity>();
		parentContainments = new ArrayList<GraphContainment>();
		childContainments =  new ArrayList<GraphContainment>();
		relationships = new ArrayList<GraphRelationship>();
	}
	
	public GraphEntity(Entity e){
		super(e.getId(), e.getName(), e.getDescription(), e.getBase());
		childSubtypes  = new ArrayList<GraphEntity>();
		parentSubtypes = new ArrayList<GraphEntity>();
		childDomains   = new ArrayList<GraphDomain>();;
		childEnititiesContained = new ArrayList<GraphEntity>();
		leftEntitiesRelated    = new ArrayList<GraphEntity>();
		childAttributes         = new ArrayList<GraphAttribute>();
		parentEnitiesContained = new ArrayList<GraphEntity>();
		rightEntitiesRelated  = new ArrayList<GraphEntity>();
		parentContainments = new ArrayList<GraphContainment>();
		childContainments =  new ArrayList<GraphContainment>();
		relationships = new ArrayList<GraphRelationship>();
	}
	
	public GraphEntity(Integer id){
		super(id,"","",0);
		childSubtypes  = new ArrayList<GraphEntity>();
		parentSubtypes = new ArrayList<GraphEntity>();
		childDomains   = new ArrayList<GraphDomain>();
		childEnititiesContained = new ArrayList<GraphEntity>();
		leftEntitiesRelated    = new ArrayList<GraphEntity>();
		childAttributes         = new ArrayList<GraphAttribute>();
		parentEnitiesContained = new ArrayList<GraphEntity>();
		rightEntitiesRelated  = new ArrayList<GraphEntity>();
		parentContainments = new ArrayList<GraphContainment>();
		childContainments =  new ArrayList<GraphContainment>();
		relationships = new ArrayList<GraphRelationship>();
	}
	
	// class getters

	public ArrayList<GraphEntity> getChildSubtypes() { return childSubtypes;}
	public ArrayList<GraphEntity> getParentSubtypes() { return parentSubtypes; }
	public ArrayList<GraphDomain> getChildDomains() { return childDomains; }
	public ArrayList<GraphEntity> getChildEnititiesContained() { return childEnititiesContained; }
	public ArrayList<GraphEntity> getLeftEntitiesRelated() { return leftEntitiesRelated; }
	public ArrayList<GraphAttribute> getChildAttributes() { return childAttributes; }
	public ArrayList<GraphEntity> getParentEnitiesContained() { return parentEnitiesContained; }
	public ArrayList<GraphEntity> getRightEntitiesRelated() { return rightEntitiesRelated; }
	public ArrayList<GraphContainment> getParentContainments() { return parentContainments; }
	public ArrayList<GraphContainment> getChildContainments() { return childContainments; }
	public ArrayList<GraphRelationship> getRelationships() { return relationships; }
	
	// class adders
	public void addChildSubtypes(GraphEntity e) { childSubtypes.add(e);}
	public void addParentSubtypes(GraphEntity e) { parentSubtypes.add(e); }
	public void addChildDomains(GraphDomain e) { childDomains.add(e); }
	public void addChildEnititiesContained(GraphEntity e) { childEnititiesContained.add(e); }
	public void addLeftEntitiesRelated(GraphEntity e) { leftEntitiesRelated.add(e); }
	public void addChildAttributes(GraphAttribute a) { childAttributes.add(a); }
	public void addParentEnitiesContained(GraphEntity e) { parentEnitiesContained.add(e); }
	public void addRightEntitiesRelated(GraphEntity e) { rightEntitiesRelated.add(e); }
	public void addParentContainments(GraphContainment c) { parentContainments.add(c); }
	public void addChildContainments(GraphContainment c) { childContainments.add(c); }
	public void addRelationships(GraphRelationship r) { relationships.add(r); }
	
	
}