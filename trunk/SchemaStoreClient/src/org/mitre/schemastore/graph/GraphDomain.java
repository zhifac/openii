// Copyright 2008 The MITRE Corporation. ALL RIGHTS RESERVED.
package org.mitre.schemastore.graph;

/**
 * Class GraphDomain
 * @author MDMORSE
 */

import java.util.ArrayList;
import org.mitre.schemastore.model.*;

public class GraphDomain extends Domain{
	
	// parent entities 
	private ArrayList<GraphEntity> parentEntities;
	
	// containments the domain appears in
	private ArrayList<GraphContainment> parentContainments;
	
	// parent attributes
	private ArrayList<GraphAttribute> parentAttributes;
	
	// child domain Values
	private ArrayList<GraphDomainValue> childDomainValues;
		
	public GraphDomain(Integer id, String name, String description, int base){
		super(id, name, description, base);
		parentEntities = new ArrayList<GraphEntity>();
		parentContainments = new ArrayList<GraphContainment>();
		parentAttributes = new ArrayList<GraphAttribute>();
		childDomainValues = new ArrayList<GraphDomainValue>();	
	}
	
	public GraphDomain(Domain d){
		super(d.getId(), d.getName(), d.getDescription(), d.getBase());	
		parentEntities = new ArrayList<GraphEntity>();
		parentContainments = new ArrayList<GraphContainment>();
		parentAttributes = new ArrayList<GraphAttribute>();
		childDomainValues = new ArrayList<GraphDomainValue>();	
	}
			

	// getters
	ArrayList<GraphEntity> 		getParentEntities()    { return parentEntities; }
	ArrayList<GraphAttribute>   getParentAttributes()  { return parentAttributes; }
	ArrayList<GraphDomainValue> getChildDomainValues() { return childDomainValues; }
	ArrayList<GraphContainment> getParentContainments() { return parentContainments; };
	
	// adders 
	void addParentEntities(GraphEntity e) { parentEntities.add(e); }
	void addParentAttributes(GraphAttribute a) { parentAttributes.add(a); }
	void addChildDomainValues(GraphDomainValue dv) { childDomainValues.add(dv); }
	void addParentContainments(GraphContainment c) {parentContainments.add(c); }
	
}