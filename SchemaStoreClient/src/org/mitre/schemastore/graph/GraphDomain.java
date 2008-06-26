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
	
	// containments the domain appears in AS A CHILD
	private ArrayList<GraphContainment> childContainments;
	
	// parent attributes
	private ArrayList<GraphAttribute> parentAttributes;
	
	// child domain Values
	private ArrayList<GraphDomainValue> childDomainValues;
		
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
	public ArrayList<GraphEntity> 		getParentEntities()    { return parentEntities; }
	public ArrayList<GraphAttribute>   getParentAttributes()  { return parentAttributes; }
	public ArrayList<GraphDomainValue> getChildDomainValues() { return childDomainValues; }
	public ArrayList<GraphContainment> getChildContainments() { return childContainments; };
	
	// adders 
	public void addParentEntities(GraphEntity e) { parentEntities.add(e); }
	public void addParentAttributes(GraphAttribute a) { parentAttributes.add(a); }
	public void addChildDomainValues(GraphDomainValue dv) { childDomainValues.add(dv); }
	public void addChildContainments(GraphContainment c) {childContainments.add(c); }
	

	
	
}