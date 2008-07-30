// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.schemastore.graph;

/**
 * Class GraphDomain
 * @author MDMORSE, DBURDICK
 */

import java.util.ArrayList;
import org.mitre.schemastore.model.*;

public class GraphDomain extends Domain implements GraphSchemaElement{
	
	// parent entities 
	private ArrayList<GraphEntity> parentEntities;
	
	// containments the domain appears in AS A CHILD
	private ArrayList<GraphContainment> childContainments;
	
	// parent attributes
	private ArrayList<GraphAttribute> parentAttributes;
	
	// child domain Values
	private ArrayList<GraphDomainValue> childDomainValues;
	private GraphAlias alias;
	
	void setAlias(GraphAlias a){
		alias = a;
	}
	GraphAlias getAlias(){
		return alias;
	}
	
	
	public GraphDomain(Integer id, String name, String description, int base){
		super(id, name, description, base);
		parentEntities = new ArrayList<GraphEntity>();
		childContainments = new ArrayList<GraphContainment>();
		parentAttributes = new ArrayList<GraphAttribute>();
		childDomainValues = new ArrayList<GraphDomainValue>();	
	}
	
	public GraphDomain(Domain d){
		super(d.getId(), d.getName(), d.getDescription(), d.getBase());	
		parentEntities = new ArrayList<GraphEntity>();
		childContainments = new ArrayList<GraphContainment>();
		parentAttributes = new ArrayList<GraphAttribute>();
		childDomainValues = new ArrayList<GraphDomainValue>();	
	}
			

	// getters
	public ArrayList<GraphEntity> 	   getParentEntities()    { return parentEntities; }
	public ArrayList<GraphAttribute>   getParentAttributes()  { return parentAttributes; }
	public ArrayList<GraphDomainValue> getChildDomainValues() { return childDomainValues; }
	public ArrayList<GraphContainment> getChildContainments() { return childContainments; };
	
	// adders 
	public void addParentEntity(GraphEntity e) { parentEntities.add(e); }
	public void addParentAttribute(GraphAttribute a) { parentAttributes.add(a); }
	public void addChildDomainValue(GraphDomainValue dv) { childDomainValues.add(dv); }
	public void addChildContainment(GraphContainment c) {childContainments.add(c); }
	
	// removers
	public void removeParentEntity(Integer id) { 
		for (int i=0;i<this.parentEntities.size();i++)
			if (parentEntities.get(i).getId().equals(id)) parentEntities.remove(i);
	}
	public void removeParentAttribute(Integer id) { 
		for (int i=0;i<this.parentAttributes.size();i++)
			if (parentAttributes.get(i).getId().equals(id)) parentAttributes.remove(i);
	}
	public void removeChildDomainValue(Integer id) { 
		for (int i=0;i<childDomainValues.size();i++)
			if (childDomainValues.get(i).getId().equals(id)) childDomainValues.remove(i);
	}
	public void removeChildContainment(Integer id) {
		for (int i=0;i<childContainments.size();i++)
			if (childContainments.get(i).getId().equals(id)) childContainments.remove(i);
	}
	
	public ArrayList<SchemaElement> getParents(){
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		retVal.addAll(getParentEntities()); 
		retVal.addAll(getParentAttributes());
		return retVal;
	}
	
	public ArrayList<SchemaElement> getChildren() {
		ArrayList<SchemaElement> retVal = new ArrayList<SchemaElement>();
		retVal.addAll(getChildDomainValues()); 
		return retVal;
	}
	
	
}