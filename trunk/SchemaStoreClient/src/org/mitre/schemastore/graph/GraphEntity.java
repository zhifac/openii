// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.schemastore.graph;

/**
 * Class GraphEntity
 * @author MDMORSE, DBURDICK
 */

import java.util.ArrayList;
import org.mitre.schemastore.model.*;

public class GraphEntity extends Entity implements GraphSchemaElement{
	
	// calculate the path from unnamed entity to either first named entity OR root
	private String path;

	// parents and child types (supertype and subtype)
	private ArrayList<GraphEntity> subtypeEntities;
	private ArrayList<GraphEntity> supertypeEntities;
	
	// child domains and other entities contained or related
	private ArrayList<GraphDomain> childDomains;
	private ArrayList<GraphEntity> childEnititiesContained;
	private ArrayList<GraphEntity> leftEntitiesRelated;

	// child attributes
	private ArrayList<GraphAttribute> childAttributes;

	// parent entities which are contained or related
	private ArrayList<GraphEntity> parentEnitiesContained;
	private ArrayList<GraphEntity> rightEntitiesRelated;
	
	// store containments, relationships, and subtypeEntities
	private ArrayList<GraphContainment> parentContainments; // containments where this entity IS PARENT
	private ArrayList<GraphContainment> childContainments;  // containments where this entity IS CHILD
	private ArrayList<GraphRelationship> relationships;
	private ArrayList<GraphSubtype> subtypes;
	private ArrayList<GraphSubtype> supertypes;
	
	private GraphAlias alias;
	
	public GraphEntity(Integer id, String name, String description, int base){
		super(id, name, description, base);
		path = new String();
		subtypes	  = new ArrayList<GraphSubtype>();
		supertypes	  = new ArrayList<GraphSubtype>();
		subtypeEntities  = new ArrayList<GraphEntity>();
		supertypeEntities = new ArrayList<GraphEntity>();
		childDomains   = new ArrayList<GraphDomain>();
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
		subtypes	  = new ArrayList<GraphSubtype>();
		subtypeEntities  = new ArrayList<GraphEntity>();
		supertypeEntities = new ArrayList<GraphEntity>();
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
	
	
	public void setAlias(GraphAlias a){alias = a;}
	public GraphAlias getAlias(){return alias;}
	
	public void setPath(String name){ path = name;}
	public String getPath() { return path;} 
	
	public String getName(){
		if (name == null || name.length() == 0)
			return path;
		else
			return name;
		
	}
	
	
	// class getters
	public ArrayList<GraphSubtype> getSubtypes() { return subtypes;}
	public ArrayList<GraphSubtype> getSupertypes() { return subtypes;}
	public ArrayList<GraphEntity> getSubtypeEntity() { return subtypeEntities;}
	public ArrayList<GraphEntity> getSupertypeEntity() { return supertypeEntities; }
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
	public void addSubtype(GraphSubtype s) { subtypes.add(s);}
	public void addSupertype(GraphSubtype s) { supertypes.add(s);}
	public void addSubtypeEntity(GraphEntity e) { subtypeEntities.add(e);}
	public void addSupertypeEntity(GraphEntity e) { supertypeEntities.add(e); }
	public void addChildDomain(GraphDomain e) { childDomains.add(e); }
	public void addChildEnitityContained(GraphEntity e) { childEnititiesContained.add(e); }
	public void addLeftEntitiesRelated(GraphEntity e) { leftEntitiesRelated.add(e); }
	public void addChildAttribute(GraphAttribute a) { childAttributes.add(a); }
	public void addParentEnitityContained(GraphEntity e) { parentEnitiesContained.add(e); }
	public void addRightEntityRelated(GraphEntity e) { rightEntitiesRelated.add(e); }
	public void addParentContainment(GraphContainment c) { parentContainments.add(c); }
	public void addChildContainment(GraphContainment c) { childContainments.add(c); }
	public void addRelationship(GraphRelationship r) { relationships.add(r); }
	
//	 class removers
	public void removeSubtype(Integer id) { 
		for (int i=0;i<subtypes.size();i++){
			if (subtypes.get(i).getId().equals(id)) subtypes.remove(i);
		}
	}
	public void removeSupertype(Integer id) { 
		for (int i=0;i<supertypes.size();i++){
			if (supertypes.get(i).getId().equals(id)) supertypes.remove(i);
		}
	}
	public void removeSubtypeEntity(Integer id) { 
		for (int i=0;i<subtypeEntities.size();i++)
			{if (subtypeEntities.get(i).getId().equals(id)) subtypeEntities.remove(i);}
	}
	public void removeSupertypeEntity(Integer id){ 
		for (int i=0;i<supertypeEntities.size();i++)
			if (supertypeEntities.get(i).getId().equals(id)) supertypeEntities.remove(i);
	}
	public void removeChildDomain(Integer id) { 
		for (int i=0;i<childDomains.size();i++)
			if (childDomains.get(i).getId().equals(id)) childDomains.remove(i);
	}
	public void removeChildEntityContained(Integer id) {
		for (int i=0;i<childEnititiesContained.size();i++)
			if (childEnititiesContained.get(i).getId().equals(id)) childEnititiesContained.remove(i);
	}
	public void removeLeftEntityRelated(Integer id) { 
		for (int i=0;i<leftEntitiesRelated.size();i++)
			if (leftEntitiesRelated.get(i).getId().equals(id)) leftEntitiesRelated.remove(i);	
	}
	public void removeChildAttribute(Integer id) { 
		for (int i=0;i<childAttributes.size();i++)
			if (childAttributes.get(i).getId().equals(id)) childAttributes.remove(i);
	}
	public void removeParentEnitityContained(Integer id) { 
		for (int i=0;i<parentEnitiesContained.size();i++)
			if (parentEnitiesContained.get(i).getId().equals(id)) parentEnitiesContained.remove(i);	
	}
	public void removeRightEntityRelated(Integer id) { 
		for (int i=0;i<rightEntitiesRelated.size();i++)
			if (rightEntitiesRelated.get(i).getId().equals(id)) rightEntitiesRelated.remove(i);	
	}
	public void removeParentContainment(Integer id) { 
		for (int i=0;i<parentContainments.size();i++)
			if (parentContainments.get(i).getId().equals(id)) parentContainments.remove(i);	
	}
	public void removeChildContainment(Integer id) { 
		for (int i=0;i<childContainments.size();i++)
			if (childContainments.get(i).getId().equals(id)) childContainments.remove(i);	
	}
	public void removeRelationship(Integer id) { 
		for (int i=0;i<relationships.size();i++)
			if (relationships.get(i).getId().equals(id)) relationships.remove(i);	
	}
	
	
	public ArrayList<SchemaElement> getParents(){
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		retVal.addAll(getParentEnitiesContained()); 
		retVal.addAll(getLeftEntitiesRelated());
		return retVal;
	}
	
	public ArrayList<SchemaElement> getChildren() {
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		retVal.addAll(getChildAttributes());
		retVal.addAll(getChildDomains());
		retVal.addAll(getChildEnititiesContained());
		retVal.addAll(getRightEntitiesRelated());
		return retVal;
	}
	
	
}