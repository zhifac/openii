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
	
	// parent attributes
	private ArrayList<GraphAttribute> parentAttributes;
	
	// child domain Values
	private ArrayList<GraphDomainValue> childDomainValues;
		
	public GraphDomain(Integer id, String name, String description, int base){
		super(id, name, description, base);
		parentEntities = new ArrayList<GraphEntity>();
		childDomainValues = new ArrayList<GraphDomainValue>();	
	}
	
	public GraphDomain(Domain d){
		super(d.getId(), d.getName(), d.getDescription(), d.getBase());
		parentEntities = new ArrayList<GraphEntity>();
		childDomainValues = new ArrayList<GraphDomainValue>();	
	}
			

	// getters
	ArrayList<GraphEntity> getParentEntities() { return parentEntities; }
	ArrayList<GraphAttribute> getParentAttributes() { return parentAttributes; }
	ArrayList<GraphDomainValue> getChildDomainValues() { return childDomainValues; }
	
	// adders 
	void addParentEntities(GraphEntity e) { parentEntities.add(e); }
	void addParentAttributes(GraphAttribute a) { parentAttributes.add(a); }
	void addChildDomainValues(GraphDomainValue dv) { childDomainValues.add(dv); }
	
}